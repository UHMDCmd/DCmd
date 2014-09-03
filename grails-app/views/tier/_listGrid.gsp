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

        jQuery("#allTiers").jqGrid({

            height:'auto',
            width:'1000',
            caption:'Software Instance List',
            url:'listAll',
            datatype: "json",
            colNames:['Software Instance Name', 'Service', 'Service Admin', 'Application', 'App Env.', 'Host', 'Host Admin', 'Load Balanced', 'General Notes', 'id'],
            colModel:[
                {name:'tierName', width: 170,title:false},
                {name:'service', width:100,title:false, search:false, sortable:false},
                {name:'serviceAdmin', width:100,title:false, search:false, sortable:false},
                {name:'app', width:100,title:false},
                {name:'appEnv', width:80,title:false},
                {name:'host', width:100,title:false},
                {name:'hostAdmin', width:100,title:false, search:false, sortable:false},
                {name:'loadBalanced', width:100,title:false},
                {name:'generalNote', width:250,title:false},
                {name:'id', hidden:true}
            ],

            rowNum: 20,
            rowList: [5, 10, 20, 50, 100],

            gridview: true,
            viewrecords: true,

            sortname: 'tierName',
            sortorder: 'asc',

            autowidth:true,
            shrinkToFit: true,
            searchOnEnter:true,
            pager: '#tierAllPager',
            scrollOffset:0,
            gridComplete: function() {
                dynamicListSize("#allTiers");
            }
            /*
            subGrid:true,

                subGridRowExpanded: function(subgrid_id, row_id) {
                    var subgrid_table_id;
                    subgrid_table_id = subgrid_id+"_t";
                    jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
                    jQuery("#"+subgrid_table_id).jqGrid({
                        url:"listAllSubGrid?id="+row_id,
                        datatype: "json",
                        colNames: ['Host Name', 'Environment', 'Status', 'Host SA', 'Physical Server', 'Host Notes'],
                        colModel: [
                            {name:"hostname", width:120, editable:false},
                            {name:"hostEnv", width:80, editable:false},
                            {name:"hostStatus", width:80, editable:false},
                            {name:"primarySA", width:100, editable:false},
                            {name:"asset", width:100, editable:false},
                            {name:"hostNotes", width:300, editable:false}
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
                */
        });
        jQuery('#allTiers').filterToolbar({id:'allTiers', searchOnEnter:true});
        $("#allTiers").jqGrid('navGrid','#tierAllPager',{
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

        setTooltipsOnColumnHeader($("#allTiers"),0,"The Name of the Software Instance (not unique),  name auto-generated based on Service Name");
        setTooltipsOnColumnHeader($("#allTiers"),1,"A Brief Description of the Software Instance");
        setTooltipsOnColumnHeader($("#allTiers"),2,"The Application this Software Instance is associated with");
        setTooltipsOnColumnHeader($("#allTiers"),3,"The Environment Assigned to the Application. e.g. Prod, Test, Train, Dev, etc.");
        setTooltipsOnColumnHeader($("#allTiers"),4,"The Primary System Administrator for the Application");
        setTooltipsOnColumnHeader($("#allTiers"),5,"The Host this Software Instance is running on");
        setTooltipsOnColumnHeader($("#allTiers"),6,"The Primary System Administrator assigned to Host");
        setTooltipsOnColumnHeader($("#allTiers"),7,"Check if the Software Instance is load balanced among other Instances");
        setTooltipsOnColumnHeader($("#allTiers"),8,"The Type of Software Instance");


        jQuery(window).bind('resize', function() {
            dynamicListSize('#allTiers');
        }).trigger('resize');

    });

</script>
<table id="allTiers"></table>
<div id="tierAllPager"></div>