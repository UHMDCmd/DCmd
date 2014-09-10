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


<script type="text/javascript">

$(document).ready(function() {


jQuery("#allRack").jqGrid({

height:'auto',
width:'1000',
caption:'Rack List',
url:'listAllRack',
            datatype: "json",
            colNames:['ITS Id', 'Location', 'Zone ID', 'Row ID', 'Cabinet', 'Last Updated', 'General Notes', 'id'],
            colModel:[
                {name:'itsId', width:100, editable:false,title:false},
                {name:'location', width:120, editable:false,title:false},
                {name:'zoneId', width:80, editable:false,title:false},
                {name:'rowId', width:80, editable:false,title:false},
                {name:'cabLocation', width:80, editable:false,title:false},
                {name:'lastUpdated', editable:false, width:150,title:false, search:false},
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
    shrinkToFit: true,
    pager: '#allRackPager',
    scrollOffset:0,
    gridComplete: function() {
        dynamicListSize("#allRack");
    }
});
        jQuery('#allRack').filterToolbar({id:'allRack', searchOnEnter:true});
           $("#allRack").jqGrid('navGrid','#allRackPager',{
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

    var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
        var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
        jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
    };

    setTooltipsOnColumnHeader($("#allRack"), 0, "A unique ITS Id given to the Entity");
    setTooltipsOnColumnHeader($("#allRack"),1,"The Physical Location of the Rack");
    setTooltipsOnColumnHeader($("#allRack"),2,"The Zone ID of the Rack within the DataCenter");
    setTooltipsOnColumnHeader($("#allRack"),3,"The Row ID of the Rack within the Zone");
    setTooltipsOnColumnHeader($("#allRack"),3,"The Cabinet ID within the Row");
    setTooltipsOnColumnHeader($("#allRack"),4,"Last update of This Record");



    jQuery(window).bind('resize', function() {
        dynamicListSize('#allRack');
    }).trigger('resize');

    });

</script>
<table id="allRack"></table>
<div id="allRackPager"></div>

