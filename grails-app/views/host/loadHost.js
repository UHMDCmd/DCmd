

var Host = Backbone.Model.extend({
    url: '../host/getHostDetails'
});

var theHost = new Host();

var template;
var HostView = Backbone.View.extend({
    model: theHost,

    events: {
        "click #unlock": "unlockAll",
        "click #lock": "lockAll",
        "click .discard": "discardChanges"
    },

    initialize: function () {
        _.bindAll(this, 'render', 'unlockAll', 'lockAll', 'discardChanges', 'loadData');
        template = Handlebars.compile($("#host_template").html());
        //    this.model.on("change", this.render);
    },

    render: function() {
        var context = {host:this.model.toJSON()};

        this.$el.html(template(context));

        // Set all dropdowns to SELECT2 and set their values
        /*
        $('.value select').each(function() {
            var attribute = $(this).context.id;
            var selectedVal;
            switch(attribute) {
                case 'cluster':
                    selectedVal = theServer.attributes[attribute].id;
                    break;
                default:
                    selectedVal = theServer.attributes[attribute];
            }
            $(this).select2({
                width:150
            }).select2('val', selectedVal);
        });
         */
        // Set everything to initially locked
        $('.value input[type="text"]').prop("disabled", true);
        $('.value select').prop("disabled", true);

        return this;
    },

    unlockAll: function() {
        this.$el.addClass('editing');
        $('.value input[type="text"]').prop("disabled", false);
        $('.value select').prop("disabled",false);
//                this.render();
    },
    lockAll: function() {
        this.$el.removeClass('editing');
//                console.log($('.value input[type="text"]'));
        $('.value input[type="text"]').prop("disabled", true);
        $('.value select').prop("disabled", true);
        //    console.log(this.$el.('input[type="text"] .edit'));
        //    this.render();
    },
    discardChanges: function() {
    },
    loadData: function(hostId) {
        this.model.fetch({data: $.param({hostId:hostId}), success: this.render});
    }
});

var linkToHost = function(hostId) {
    var hostView = new HostView({ el: $("#server_attributes") });
    serverView.loadData(serverId);
    $("#server_dialog").dialog("open");
    //   console.log(test.toJSON());
};