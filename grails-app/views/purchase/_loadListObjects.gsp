<script src="../js/underscore.js"></script>
<script src="../js/backbone.js"></script>
<script src="../js/handlebars.js"></script>
<script src="../js/globals.js"></script>

<script type="text/javascript">

    var PurchaseType = Backbone.Model.extend();

    var PurchaseTypeList = Backbone.Collection.extend({
        url:'../purchase/getPurchaseTypes',
        model:PurchaseType
        initialize:function() {
            this.fetch();
        }
    });

</script>