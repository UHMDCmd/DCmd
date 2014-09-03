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


jQuery("#allLocations").jqGrid({

height:'auto',
width:'1000',
caption:'Location List',
url:'listAllLocation',
            datatype: "json",
            colNames:['Location', 'Address', 'Building', 'Room', '# Racks', '# Servers', 'General Notes', 'id'],
            colModel:[
                {name:'locationDescription', width:200, editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
                {name:'addr', width:250, editable:false,title:false, search:false, sortable:false},
                {name:'building', width:120, editable:false,title:false, search:false, sortable:false},
                {name:'roomNum', width:100, editable:false,title:false, search:false, sortable:false},
                {name:'roomNum', width:100, editable:false,title:false, search:false, sortable:false},
                {name:'roomNum', width:100, editable:false,title:false, search:false, sortable:false},
                {name:'generalNote', width:400, editable:false,title:false, search:false, sortable:false},
                {name:'id', hidden:true}
            ],

    rowNum: 20,
    rowList: [5, 10, 20, 50, 100],

    gridview: true,
    viewrecords: true,
    sortname: 'id',
    sortorder: 'desc',
    autowidth:true,
    shrinkToFit: true,
    searchOnEnter:true,
    pager: '#allLocationPager',
    scrollOffset:0,
    gridComplete: function() {
        dynamicListSize("#allLocations");
    }


});
        jQuery('#allLocations').filterToolbar({id:'allLocations', searchOnEnter:true});
        $("#allLocations").jqGrid('navGrid','#allLocationPager',{
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

    setTooltipsOnColumnHeader($("#allLocations"),0,"Physical Location e.g. Building/Room, Region");
    setTooltipsOnColumnHeader($("#allLocations"),1,"Physical Mailing Address");
    setTooltipsOnColumnHeader($("#allLocations"),2,"Physical Building");
    setTooltipsOnColumnHeader($("#allLocations"),3,"Physical Room #");
    setTooltipsOnColumnHeader($("#allLocations"),4,"The Number of Racks at this Location");
    setTooltipsOnColumnHeader($("#allLocations"),5,"The Number of Servers at this Location (including those within Racks");

    
    jQuery(window).bind('resize', function() {
        dynamicListSize('#allLocations');
    }).trigger('resize');

    });

</script>
<table id="allLocations"></table>
<div id="allLocationPager"></div>
