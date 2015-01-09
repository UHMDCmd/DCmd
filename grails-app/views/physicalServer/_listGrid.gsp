
<script type="text/javascript">

function openItem(serverId) {
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

function unlock() {
    $('.locked').position = 'absolute';
    $('.locked').hide();
    $('.unlocked').position = 'relative';
    $('.unlocked').show();
}

function lock() {
    $('.locked').position = 'relative';
    $('.locked').show();
    $('.unlocked').position = 'absolute';
    $('.unlocked').hide();;
}


$(document).ready(function() {
    $( "#item_dialog" ).dialog({
        autoOpen: false,
        width: 800,
        height: 400,
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
            colNames:['ITS Id', 'Server Type', 'VM Cluster', 'Host OS', 'Status', 'Primary SA', 'RU Size', 'Current Rack', 'Current Position', 'Current Location',
                'Avail. for parts', 'Serial #', 'Manufacturer', 'Model', 'Total Memory', 'Memory Assigned', 'Total Cores', 'Max CPU Assigned', 'General Notes', 'id'],
            colModel:[
                {name:'itsId', width:100, editable:false, frozen:true, title:false},
                {name:'serverType', width:120, editable:false, frozen:true, title:false},
                {name:'cluster', width:120, editable:false, title:false},
                {name:'hostOS', width:120, editable:false, title:false},
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
                {name:'memorySize', width:120, editable:false, title:false},
                {name:'memoryAssigned', width:120, editable:false, title:false},
                {name:'numCores', width:120, editable:false, title:false},
                {name:'cpuAssigned', width:120, editable:false, title:false},
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

<g:render template="../advancedOptions" model="[pageType:'physicalServer', gridId:'#allPhyServer', export:true, exportAction:'exportListAll', hostFilter:true]" />

<table id="allPhyServer"></table>
<div id="allServerPager"></div>


<div id="item_dialog" title="Item Details">
    <g:render template="details"/>
    <br>
    <input type="button" onclick="lock()" value="Lock"/>
    <input type="button" onclick="unlock()" value="Unlock"/>
</div>
