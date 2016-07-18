%{--
 - Copyright (c) 2014 University of Hawaii
 -
 - This file is part of DataCenter metadata (DCmd) project.
 -
 - DCmd is free software: you can redistribute it and/or modify
 - it under the terms of the GNU General Public License as published by
 - the Free Software Foundation, either version 3 of the License, or
 - (at your option) any later version.
 -
 - DCmd is distributed in the hope that it will be useful,
 - but WITHOUT ANY WARRANTY; without even the implied warranty of
 - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 - GNU General Public License for more details.
 -
 - You should have received a copy of the GNU General Public License
 - along with DCmd.  It is contained in the DCmd release as LICENSE.txt
 - If not, see <http://www.gnu.org/licenses/>.
 --}%

<script src="../js/underscore.js"></script>
<script src="../js/backbone.js"></script>
<script src="../js/handlebars.js"></script>


<script type="text/javascript">

   // (function($){

   var ServerHost = Backbone.Model.extend();
   var ServerHosts = Backbone.Collection.extend({
       url:'../host/getHostsByServer',
       model: ServerHost
   });

    var ServerType = Backbone.Model.extend({
        defaults: function() {
            return {
                selected: ""
            };
        }
    });

    var ServerTypes = Backbone.Collection.extend({
        url:'../physicalServer/getServerTypes',
        model: ServerType,
        initialize: function() {
            this.fetch();
        }
    });

    var serverTypes = new ServerTypes();

    var Cluster = Backbone.Model.extend({
       defaults: function() {
           return {
               selected: ""
           };
       }
    });

    var Clusters = Backbone.Collection.extend({
       url:'../physicalServer/getClustersAsSelect',
       model: Cluster,
       initialize: function() {
           this.fetch();
       }
    });

    var clusters = new Clusters();

        var PhyServer = Backbone.Model.extend({
            url:'/its/dcmd/physicalServer/getServerDetails',

            initialize: function() {
                this.set('hostList', new ServerHosts);
            },
            defaults: function() {
                return {
                    itsId: 'empty'
                };
            },
            saveData: function() {

            }
        });
        var theServer = new PhyServer();


        var template;

        var ServerView = Backbone.View.extend({

            model: theServer,

//            template: _.template($("#server_template").html()),
//            el: $("#server_dialog"),

            events: {
                "click #unlock": "unlockAll",
                "click #lock": "lockAll",
                "click .discard": "discardChanges"
            },

            initialize: function () {
                _.bindAll(this, 'render', 'unlockAll', 'lockAll', 'discardChanges', 'loadData', 'renderHostGrid');
                template = Handlebars.compile($("#server_template").html());
            //    this.model.on("change", this.render);
            },

            render: function() {
                var context = {server:this.model.toJSON(),sTypes:serverTypes.toJSON(), clusterList: clusters.toJSON()};

                this.$el.html(template(context));

                // Set all dropdowns to SELECT2 and set their values
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
            loadData: function(serverId) {
                this.model.fetch({data: $.param({serverId:serverId}), success: this.render});
                this.model.attributes.hostList.fetch({data: $.param({serverId:serverId}), success:this.renderHostGrid});
            },
            renderHostGrid: function() {
                console.log(this.model.attributes.hostList.toJSON());
                $("#hostsList").GridUnload();
                jQuery("#hostsList").jqGrid({
                    height:'auto',
                    autowidth:true,
                    datatype: 'local',
                    data:this.model.attributes.hostList.toJSON(),
                    width:'100%',
                    colNames:['Hostname', 'Status', 'Environment', 'Host SA', 'Max Memory', 'Max CPU'],
                    colModel:[  {name:'Hostname', align:'left'},
                                {name:'status', align:'left'},
                                {name:'env', align:'left'},
                                {name:'primarySA', align:'left'},
                                {name:'maxMemory', align:'left'},
                                {name:'maxCPU', align:'left'}],
                    loadComplete : function(data) {
                        //alert('grid loading completed ' + data);
                    },
                    loadError : function(xhr, status, error) {
                        alert('grid loading error' + error);
                    }
                });
            }
        });

   var openItem = function(serverId) {
       var serverView = new ServerView({ el: $("#server_attributes") });
       serverView.loadData(serverId);
       $("#server_dialog").dialog("open");
    //   console.log(test.toJSON());
   };

  //  })(jQuery);
</script>


<script type="text/javascript">
/*
function openItem(serverId) {

    alert(task.get("title"));

    jQuery.ajax({
        async: false,
        url: '/its/dcmd/physicalServer/getServerDetails?serverId='+serverId,
        type:'POST',
        dataType:'json',
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            $("#e_itsId").val(data.server.itsId);
            $("#l_itsId").html(data.server.itsId)
            $("#e_status").val(data.status);
            $("#l_status").html(data.status);
            //alert(data.server.itsId);
            lock();
        },
        error: function () { alert('Error retrieving elog info'); }
    });
    //$( "#item_dialog").dialog({appendTo: ""});
    $("#item_dialog").dialog("open");
}
  */

$(document).ready(function() {
    $( "#server_dialog" ).dialog({
        autoOpen: false,
        width: 800,
        height: 600,
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "blind",
            duration: 1000
        }
    });

    var fixPositionsOfFrozenDivs = function () {
        var grid = this.grid || this;
        var $rows;
        if (typeof grid.fbDiv !== "undefined") {
            $rows = $('>div>table.ui-jqgrid-btable>tbody>tr', grid.bDiv);
            $('>table.ui-jqgrid-btable>tbody>tr', grid.fbDiv).each(function (i) {
                var rowHight = $($rows[i]).height(), rowHightFrozen = $(this).height();
                if ($(this).hasClass("jqgrow")) {
                    $(this).height(rowHight);
                    rowHightFrozen = $(this).height()+0.2;
                    if (rowHight !== rowHightFrozen) {
                        $(this).height(rowHight + (rowHight - rowHightFrozen));
                    }
                }
            });
            $(grid.fbDiv).height(grid.bDiv.clientHeight);
            $(grid.fbDiv).css($(grid.bDiv).position());
        }
        if (typeof grid.fhDiv !== "undefined") {
            $rows = $('>div>table.ui-jqgrid-htable>thead>tr', grid.hDiv);
            $('>table.ui-jqgrid-htable>thead>tr', grid.fhDiv).each(function (i) {
                var rowHight = $($rows[i]).height(), rowHightFrozen = $(this).height();
                $(this).height(rowHight);
                rowHightFrozen = $(this).height();
                if (rowHight !== rowHightFrozen) {
                    $(this).height(rowHight + (rowHight - rowHightFrozen));
                }
            });
            $(grid.fhDiv).height(grid.hDiv.clientHeight);
            $(grid.fhDiv).css($(grid.hDiv).position());
        }
    };

jQuery("#allPhyServer").jqGrid({

height:'auto',
width:'1000',
url:'listAllPhyServer',
            datatype: "json",
            colNames:['ITS Id', 'Server Type', 'VCenter', 'VM Cluster', 'OS Host', 'Status', 'Primary SA', 'RU Size', 'Current Rack', 'Current Position', 'Current Location',
                'Avail. for parts', 'Serial #', 'Vendor', 'Model', 'Total Memory', 'Memory Assigned', 'Total Cores', 'Max CPU Assigned', 'General Notes', 'id'],
            colModel:[
                {name:'itsId', width:100, editable:false, frozen:true, title:false},
                {name:'serverType', width:120, editable:false, frozen:true, title:false},
                {name:'vcenter', width:120, editable:false, title:false},
                {name:'cluster', width:120, editable:false, title:false},
                {name:'hostOS', width:120, editable:false, title:false},
                {name:'assetStatus', width:100, editable:false, title:false},
                {name:'primarySA', width:120, title:false, sortable:false},
                {name:'RU_size', width:80, title:false},
                {name:'rack', width:100, title:false, sortable:false},
                {name:'rackPosition', width:80,title:false, search:false, sortable:false},
                {name:'location', width:120, title:false, search:false, sortable:false},
                {name:'isAvailableForParts', width:120, title:false, sortable:false,
                    searchoptions: { sopt: ['eq'], value:':All;true:True;false:False' }, stype: 'select'},
                //{name:'isAvailableForParts', width:120, title:false, sortable:false},
                {name:'serialNo', width:180, editable:false, title:false},
                {name:'manufacturer', width:120, editable:false, title:false},
                {name:'modelDesignation', width:120, editable:false, title:false},
                {name:'memorySize', width:120, editable:false, title:false, search:false},
                {name:'memoryAssigned', width:150, editable:false, title:false, search:false, sortable:false},
                {name:'numCores', width:120, editable:false, title:false, search:false, sortable:false},
                {name:'cpuAssigned', width:120, editable:false, title:false, search:false, sortable:false},
                {name:'generalNote', width:400,title:false},
                {name:'id', hidden:true}
            ],

    rowNum: 50,
    rowList: [10, 20, 50, 100, 200],

    gridview: true,
    viewrecords: true,
    sortname: 'itsId',
    sortorder: 'asc',
    autowidth:true,
    shrinkToFit: false,
    pager: '#allServerPager',
    headertiltes: false,
    scrollOffset:0,
    toolbar:[true, 'top'],
    gridComplete: function() {
        dynamicListSize("#allPhyServer");
    },
    loadComplete: function() {
        fixPositionsOfFrozenDivs.call(this);
    },
    resizeStop: function() {
        fixPositionsOfFrozenDivs.call(this);
    }

});

    createTopToolbar("#allPhyServer");

    var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
        var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
        jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
    };

    setTooltipsOnColumnHeader($("#allPhyServer"),0,"A unique ITS Id given to the Entity");
    setTooltipsOnColumnHeader($("#allPhyServer"),1,"Virtualization method of Server (e.g., Solaris, VMWare, etc.)");
    setTooltipsOnColumnHeader($("#allPhyServer"),2,"VCenter Instance");
    setTooltipsOnColumnHeader($("#allPhyServer"),3,"VMWare Cluster the Server hardware is assigned to (N/A if not a VMWare server)");
    setTooltipsOnColumnHeader($("#allPhyServer"),4,"Host that is the primary OS of this Server (e.g. Global Zone for Solaris)");
    setTooltipsOnColumnHeader($("#allPhyServer"),5,"Status of the Server (Available, Offline, Retired, etc.)");
    setTooltipsOnColumnHeader($("#allPhyServer"),6,"The Primary System Administrator assigned to this Server");
    setTooltipsOnColumnHeader($("#allPhyServer"),7,"Amount of space the Physical Server occupies on a Rack");
    setTooltipsOnColumnHeader($("#allPhyServer"),8,"The current Physical Rack the Server is mounted on");
    setTooltipsOnColumnHeader($("#allPhyServer"),9,"Current position the Server is mounted in the Rack");
    setTooltipsOnColumnHeader($("#allPhyServer"),10,"The Physical Location of the Rack currently mounted on");
    setTooltipsOnColumnHeader($("#allPhyServer"),11,"Parts Availability");
    setTooltipsOnColumnHeader($("#allPhyServer"),12,"Serial #");
    setTooltipsOnColumnHeader($("#allPhyServer"),13,"Vendor Name");
    setTooltipsOnColumnHeader($("#allPhyServer"),14,"Model Designation");
    setTooltipsOnColumnHeader($("#allPhyServer"),15,"Total RAM Memory provided by this Server in Gigabytes");
    setTooltipsOnColumnHeader($("#allPhyServer"),16,"Percent of Total Memory that is assigned to Hosts running on this Server");
    setTooltipsOnColumnHeader($("#allPhyServer"),17,"Total number of CPU Cores provided by this Server");
    setTooltipsOnColumnHeader($("#allPhyServer"),18,"Percent of Total CPU that is assigned to hosts running on this Server");
    setTooltipsOnColumnHeader($("#allPhyServer"),19,"General notes about this Server");








    jQuery('#allPhyServer').filterToolbar({id:'allPhyServer', searchOnEnter:true});
        jQuery('#allPhyServer').jqGrid('setFrozenColumns');

        jQuery('#allPhyServer').filterToolbar({id:'allPhyServer', searchOnEnter:true});
        $("#allPhyServer").jqGrid('navGrid','#allServerPager',{
                add:false,
                del:false,
                edit:false,
                refresh:false,
                refreshstate:"current",
                search:false
            },
            {},//edit options

            {recreateForm:true //since clearAfterAdd is trueby default, recreate the form so we re-establish value for parent id
            });





    jQuery(window).bind('resize', function() {
        dynamicListSize('#allPhyServer');
    }).trigger('resize');

    });

</script>


<g:render template="../advancedOptions" model="[pageType:'physicalServer',support:false, gridId:'#allPhyServer', export:true, exportAction:'exportListAll', hostFilter:true]" />

<table id="allPhyServer"></table>
<div id="allServerPager"></div>


<g:render template="details"/>
<div id="server_dialog" title="Server Details">
    <div id="server_attributes"></div>
    <g:render template="popup-tabs"/>

</div>
%{--
<script type="text/javascript">
   function testExtern() {
       jQuery.ajax({
           async: false,
           url: '/its/dcmd/physicalServer/testExtern',
           type:'POST',
           dataType:'json',
           contentType: 'application/json; charset=utf-8',
           success: function(data) {
               alert(data);
           },
           error: function () { alert('Error retrieving elog info'); }
       });
   }
</script>

%{--<input type="button" value="test" onclick="testExtern()"/> --}%