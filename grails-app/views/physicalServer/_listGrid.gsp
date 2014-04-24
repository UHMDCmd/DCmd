
<script type="text/javascript">

$(document).ready(function() {

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
            colNames:['ITS Id', 'Server Type', 'Status', 'Primary SA', 'RU Size', 'Current Rack', 'Current Position', 'Current Location',
                'Avail. for parts', 'Serial #', 'Manufacturer', 'Model', 'General Notes', 'id'],
            colModel:[
                {name:'itsId', width:100, editable:false, frozen:true, title:false},
                {name:'serverType', width:120, editable:false, frozen:true, title:false},
                {name:'assetStatus', width:100, editable:false, title:false},
                {name:'primarySA', width:120, title:false, sortable:false},
                {name:'RU_size', width:80, title:false},
                {name:'rack', width:100, title:false, sortable:false},
                {name:'rackPosition', width:80,title:false, search:false, sortable:false},
                {name:'location', width:120, title:false, search:false, sortable:false},
                {name:'isAvailableForParts', width:120, title:false, sortable:false},
                {name:'serialNo', width:180, editable:false, title:false},
                {name:'manufacturer', width:120, editable:false, title:false},
                {name:'modelDesignation', width:120, editable:false, title:false},
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

    var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
        var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
        jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
    };

    setTooltipsOnColumnHeader($("#allPhyServer"),0,"A unique ITS Id given to the Entity");
    setTooltipsOnColumnHeader($("#allPhyServer"),1,"Virtualization method of Server (e.g., Solaris, VMware, etc.)");
    setTooltipsOnColumnHeader($("#allPhyServer"),2,"Status e.g. Online, Offline, Standby, etc.");
    setTooltipsOnColumnHeader($("#allPhyServer"),3,"The Primary System Administrator for this Server");
    setTooltipsOnColumnHeader($("#allPhyServer"),4,"Amount of space the Physical Server occupies on a Rack");
    setTooltipsOnColumnHeader($("#allPhyServer"),5,"The current Physical Rack the Server is mounted on");
    setTooltipsOnColumnHeader($("#allPhyServer"),6,"Current position the Server is mounted in the Rack");
    setTooltipsOnColumnHeader($("#allPhyServer"),7,"The Physical Location of the Rack currently mounted on");
    setTooltipsOnColumnHeader($("#allPhyServer"),8,"Parts Availability");
    setTooltipsOnColumnHeader($("#allPhyServer"),9,"Serial #");
    setTooltipsOnColumnHeader($("#allPhyServer"),10,"Manufacturer Name");
    setTooltipsOnColumnHeader($("#allPhyServer"),11,"Model Designation");
    setTooltipsOnColumnHeader($("#allPhyServer"),12,"Last Update of Record");








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
<g:render template="../advancedOptions" model="[pageType:'physicalServer', gridId:'#allPhyServer', export:true, exportAction:'exportListAll', hostFilter:true]" />

<table id="allPhyServer"></table>
<div id="allServerPager"></div>
