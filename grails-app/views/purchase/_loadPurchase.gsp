<script src="../js/underscore.js"></script>
<script src="../js/backbone.js"></script>
<script src="../js/handlebars.js"></script>
<script src="../js/globals.js"></script>
<script src="../js/masonry.min.js"></script>

<g:render template="purchaseItemGrid"/>
<g:render template="supportStaffGrids"/>

<style>
    .anniversary .ui-datepicker-year {
        display:none;
    }
</style>

<script type="text/javascript">


    var AssetOption = Backbone.Model.extend();
    var AssetOptions = Backbone.Collection.extend({
        url:'../asset/getAllAssetOptions',
        model:AssetOption,
        initialize: function() {
            this.fetch({
                async:false
            });
        },
        listItems: function(){
            var listString = "0:N/A;";
            this.each(function(asset) {
                listString += asset.id + ":" + asset.attributes.itsId + ";";
            });
            //console.log(listString);
            return listString.substring(0, listString.length-1);
        }
    });

    var assetOptions = new AssetOptions();

    var PersonOption = Backbone.Model.extend();
    var PersonOptions = Backbone.Collection.extend({
        url:'../person/getPersonOptions',
        model:PersonOption,
        initialize: function() {
            this.fetch({
                async:false
            });
        },
        listItems: function(){
            var listString = "";
            this.each(function(person) {
                listString += person.id + ":" + person.attributes.uhName + ";";
            });
            return listString.substring(0, listString.length-1);
        }
    });

    var personOptions = new PersonOptions();

    var PurchaseItemType = Backbone.Model.extend();
    var PurchaseItemTypes = Backbone.Collection.extend({
        url:'../purchase/getPurchaseItemTypes',
        model: PurchaseItemType,
        initialize: function() {
            this.fetch({
                async:false
            });
        },
        listItems: function(){
            var listString = "";
            this.each(function(type) {
                listString += type.attributes.id + ":" + type.attributes.text + ";";
            });
            return listString.substring(0, listString.length-1);
        }
    });
    var purchaseItemTypes = new PurchaseItemTypes();

    var PurchaseType = Backbone.Model.extend();
    var PurchaseTypes = Backbone.Collection.extend({
        url:'../purchase/getPurchaseTypes',
        model: PurchaseType,
        initialize: function() {
            this.fetch({
                async:false
            });
        },
        listItems: function(){
            var listString = "";
            this.each(function(type) {
                listString += type.attributes.abbreviation + ":" + type.attributes.abbreviation + ";";
            });
            return listString.substring(0, listString.length-1);
        }
    });

    var purchaseTypes = new PurchaseTypes();

    var PurchaseStatus = Backbone.Model.extend();
    var PurchaseStatuses = Backbone.Collection.extend({
        url:'../purchase/getPurchaseStatuses',
        model: PurchaseType,
        initialize: function() {
            this.fetch({
                async:false
            });
        },
        listItems: function(){
            var listString = "";
            this.each(function(status) {
                listString += status.attributes.id + ":" + status.attributes.text + ";";
            });
            return listString.substring(0, listString.length-1);
        }
    });

    var purchaseStatuses = new PurchaseStatuses();

    var PaymentType = Backbone.Model.extend();
    var PaymentTypes = Backbone.Collection.extend({
        url:'../purchase/getPaymentTypes',
        model: PaymentType,
        initialize: function() {
            this.fetch({
                async:false
            });
        },
        listItems: function(){
            var listString = "";
            this.each(function(status) {
                listString += status.attributes.id + ":" + status.attributes.text + ";";
            });
            return listString.substring(0, listString.length-1);
        }
    });

    var paymentTypes = new PaymentTypes();

var PurchaseItem = Backbone.Model.extend();

var PurchaseItemList = Backbone.Collection.extend({
    url:'../purchase/getItemsByPurchase',
    model: PurchaseItem
});

var Purchase = Backbone.Model.extend({
    //url:'../bb/purchase',
    url:'../bb/purchase',

    initialize: function() {
//        this.set('itemList', new PurchaseItemList);
    }
});

var thePurchase = new Purchase();

var template;

var PurchaseView = Backbone.View.extend({

    model: thePurchase,

    events: {
    "click #edit": 'editDialog',
    "click #save": 'saveDialog',
    "click #discard": "discardDialog",
    "change input":"change",
    "change select":"change",
    "change textArea":"change"
    },

    initialize: function () {
        _.bindAll(this, 'render', 'discardDialog', 'loadData', 'editDialog', 'saveDialog', 'renderItemGrid');
        template = Handlebars.compile($("#purchase_template").html());
    },

    render: function() {
    var context = {purchase:this.model.toJSON(), purchaseTypes:purchaseTypes.toJSON(), purchaseStatuses:purchaseStatuses.toJSON(), paymentTypes:paymentTypes.toJSON(), personOptions:personOptions.toJSON()};

    this.$el.html(template(context));

    // Set all dropdowns to SELECT2 and set their values
    $('.value select').each(function() {
        var attribute = $(this).context.id;
        console.log(thePurchase.attributes);
        var selectedVal;
        selectedVal = thePurchase.attributes[attribute];

        $(this).select2({
            width:150
        }).select2('val', selectedVal);
    });

    $('.datePicker').datepicker();
    $('.anniversary').datepicker({
        beforeShow: function (input, inst) {
            inst.dpDiv.addClass('anniversary');
        },
        afterClose: function(dateText, inst){
            inst.dpDiv.removeClass('anniversary');
        },
        dateFormat: 'mm/dd'
    });




    // Set everything to initially locked
        lock(this, $("#itemGrid"));
        lockGrid($("#itemGrid"), $("#itemAddBtn"));
        lockGrid($("#supportGrid"), $("#supportAddBtn"));

        var container = document.querySelector('#purchase_container');
        var msnry = new Masonry( container, {
            // options
            columnWidth: 200,
            itemSelector: '.item'
        });
    return this;
    },

    change: function(event) {
     //   console.log(event);
        var target = event.target;
        var change = {};
        if(target.type == 'checkbox')
            change[target.id] = target.checked;
        else
            change[target.id] = target.value;

        this.model.set(change);
        console.log(this.model);
    },
    editDialog: function(){
        unlock(this, $("#itemGrid"));
        unlockGrid($("#itemGrid"), $("#itemAddBtn"));
        unlockGrid($("#supportGrid"), $("#supportAddBtn"));
    },
    saveDialog: function(){
        lock(this, $("#itemGrid"));
        this.model.save({oper:'pageEdit'}, {success:function() {
            $("#allPurchases").trigger("reloadGrid");
        }});
        lockGrid($("#itemGrid"), $("#itemAddBtn"));
        lockGrid($("#supportGrid"), $("#supportAddBtn"));
//        this.model.update();
//        console.log(this.model);
   //     this.model.save(this.model.attributes);

    },
    discardDialog: function() {
        lock(this, $("#itemGrid"));
        lockGrid($("#itemGrid"), $("#itemAddBtn"));
        lockGrid($("#supportGrid"), $("#supportAddBtn"));
        //this.model.fetch();
        this.render();
    },
    loadData: function(Id) {
        this.model.fetch({data: $.param({Id:Id}), success: this.render});
        this.renderItemGrid(Id);
//    this.model.attributes.itemList.fetch({data: $.param({purchaseId:Id}), success:this.renderItemGrid});
    },
    renderItemGrid: function(Id) {
        loadPurchaseItemGrid(Id);
        jQuery("#itemGrid").jqGrid('hideCol', ["actions", "cb"]);
        loadSupportGrid(Id);
    }
});

var openItem = function(Id) {
var purchaseView = new PurchaseView({ el: $("#purchase_attributes") });
purchaseView.loadData(Id);
$("#purchase_dialog").dialog("open");



//   console.log(test.toJSON());
};
</script>