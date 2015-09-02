
    var Purchase = Backbone.Model.extend({
        url:'../purchase/getPurchaseDetails'
    });


    var thePurchase = new Purchase();


    var template;

    var PurchaseView = Backbone.View.extend({

        model: thePurchase,

        events: {
        "click #unlock": "unlockAll",
        "click #lock": "lockAll",
        "click .discard": "discardChanges"
        },

        initialize: function () {
            _.bindAll(this, 'render', 'unlockAll', 'lockAll', 'discardChanges', 'loadData');
            template = Handlebars.compile($("#purchase_template").html());
        },

        render: function() {
        var context = {purchase:this.model.toJSON()};

        this.$el.html(template(context));


        // Set everything to initially locked
        $('.value input[type="text"]').prop("disabled", true);
        $('.value select').prop("disabled", true);

        return this;
        },

        unlockAll: unlockAll(),
        lockAll: lockAll(),
        discardChanges: function() {
        },
        loadData: function(serverId) {
        this.model.fetch({data: $.param({purchaseId:purchaseId}), success: this.render});
//        this.model.attributes.hostList.fetch({data: $.param({serverId:serverId}), success:this.renderHostGrid});
        }
    });

    var openItem = function(purchaseId) {
    var purchaseView = new PurchaseView({ el: $("#purchase_attributes") });
    purchaseView.loadData(purchaseId);
    $("#purchase_dialog").dialog("open");
    //   console.log(test.toJSON());
    };