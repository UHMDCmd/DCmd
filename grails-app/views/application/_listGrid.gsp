<script type="text/javascript">

$(document).ready(function() {

jQuery("#allApplications").jqGrid({

height:'auto',
width:'1000',
url:'listAll',
            datatype: "json",
            colNames:['App Name', 'Environment', 'Description', 'Status', 'App Admin', 'General Notes', 'id'],
            colModel:[
                {name:'applicationTitle', width: 160, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
                {name:'env', width:100,title:false},
                {name:'applicationDescription', width:280,title:false},
                {name:'status', width:100,title:false},
                {name:'primarySA', width:120,title:false, sortable:false},
                {name:'generalNote', width:400,title:false},
                {name:'id', hidden:true}
            ],

    rowNum: 50,
    rowList: [10, 20, 50, 100, 200],

    gridview: true,
    viewrecords: true,
    sortname: 'applicationTitle',
    headertitles:false,
    //viewrecords: true,
    sortorder: 'asc',
//    autoHeight:true,
    autowidth:true,
    shrinkToFit: true,
    searchOnEnter:true,
    pager: '#applicationAllPager',
    scrollOffset:0,
    subGrid:true,
    gridComplete: function() {
        dynamicListSize("#allApplications");
    },


    subGridRowExpanded: function(subgrid_id, row_id) {
        var subgrid_table_id;
        subgrid_table_id = subgrid_id+"_t";
        jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
        jQuery("#"+subgrid_table_id).jqGrid({
            url:"listAllServiceSubGrid?id="+row_id,
            datatype: "json",
            colNames:['Service Name', 'Environment', 'Description', 'Hosts', 'Status', 'Service Admin', 'Service Notes'],
            colModel: [
                {name:"serviceTitle", width:120, editable:false,title:false},
                {name:"serviceEnv", width:60, editable:false,title:false},
                {name:"serviceDescription", width:120, editable:false,title:false},
                {name:"hosts", width:80, editable:false,title:false},
                {name:"serviceStatus", width:60, editable:false,title:false},
                {name:"serviceAdmin", width:80, editable:false,title:false},
                {name:"generalNote", width:200, editable:false,title:false}
            ],
            rowNum:100,
            width:'100%',
            height:'100%',
            viewrecords: true,
            autowidth:true,
            shrinkToFit: true,
            gridview: true
        });
    }

    });
    jQuery('#allApplications').filterToolbar({id:'allApplications', searchOnEnter:true});
    $("#allApplications").jqGrid('navGrid','#applicationAllPager',{
                add:false,
                del:false,
                edit:false,
                refresh:false,
                refreshstate:"current",
                search:false
            },
            {},//edit options
            //add options
            {recreateForm:true //since clearAfterAdd is trueby default, recreate the form so we re-establish value for parent id
            });

    var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
        var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
        jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
    };

    setTooltipsOnColumnHeader($("#allApplications"),0,"Expand to see Services associated with each Application");
    setTooltipsOnColumnHeader($("#allApplications"),1,"The Name of the Application");
    setTooltipsOnColumnHeader($("#allApplications"),2,"The Environment of the Application  (e.g., Prod, Test, Dev.");
    setTooltipsOnColumnHeader($("#allApplications"),3,"A Brief Description of the function of this Application");
    setTooltipsOnColumnHeader($("#allApplications"),4,"The Status of this Application. e.g . Available, Disabled, etc.");
    setTooltipsOnColumnHeader($("#allApplications"),5,"The Primary System Administrator assigned to this Application");

    jQuery(window).bind('resize', function() {
        dynamicListSize('#allApplications');
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

<g:render template="../advancedOptions" model="[pageType:'application', gridId:'#allApplications', export:true, exportAction:'exportListAll', hostFilter:true]" />

<table id="allApplications"></table>
<div id="applicationAllPager"></div>