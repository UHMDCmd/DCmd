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

jQuery("#allCluster").jqGrid({

height:'auto',
width:'1000',
caption:'Cluster List',
url:'listAllCluster',
            datatype: "json",
            colNames:['Cluster Name', '# of Servers', '# of Hosts', 'Last Updated', 'Notes', 'id'],
            colModel:[
                {name:'name', editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
                {name:'numServers', editable:false, width:50,title:false, search:false, sortable:false},
                {name:'numHosts', editable:false,width:50, title:false, search:false, sortable:false},
                {name:'lastUpdated', editable:false, width: 100,title:false, search:false},
                {name:'generalNote', width:'400', editable:false,title:false},
                {name:'id', hidden:true}
            ],

    sortname: 'name',
    sortorder: 'asc',
    rowNum: 50,
    rowList: [10, 20, 50, 100],

    gridview: true,
    viewrecords: true,
    autowidth:true,
    shrinkToFit: true,
    searchOnEnter:true,
    pager: '#allClusterPager',
    scrollOffset:0


});
        jQuery('#allCluster').filterToolbar({id:'allCluster', searchOnEnter:true});
        $("#allCluster").jqGrid('navGrid','#allClusterPager',{
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

    setTooltipsOnColumnHeader($("#allCluster"),0,"Name of Cluster");
    setTooltipsOnColumnHeader($("#allCluster"),1,"Number of Physical Servers making up the Cluster");
    setTooltipsOnColumnHeader($("#allCluster"),2,"Number of Virtual Hosts running on the Cluster");
    setTooltipsOnColumnHeader($("#allCluster"),3,"Last time the Cluster information was updated");
    setTooltipsOnColumnHeader($("#allCluster"),4,"Notes about the Cluster");


    jQuery(window).bind('resize', function() {
        dynamicListSize('#allCluster');
    }).trigger('resize');

    });

</script>
<table id="allCluster"></table>
<div id="allClusterPager"></div>

