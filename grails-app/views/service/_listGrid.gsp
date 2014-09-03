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

        jQuery("#allServices").jqGrid({

            height:'auto',
            width:'1000',
            caption:'Service List',
            url:'listAll',
            datatype: "json",
            colNames:['Service Name', 'Application', 'Environment', 'Service Description', 'Status', 'Service Admin', 'App Admin', 'General Notes', 'id'],
            colModel:[
                {name:'serviceTitle', width: 160, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
                {name:'app', width:120,title:false},
                {name:'serviceEnv', width:100,title:false},
                {name:'serviceDescription', width:280,title:false},
                {name:'status', width:100,title:false},
                {name:'primarySA', width:120,title:false, sortable:false},
                {name:'applicationAdmin', width:100,title:false, sortable:false, search:false},
                {name:'generalNote', width:400,title:false},
                {name:'id', hidden:true}
            ],

            rowNum: 50,
            rowList: [10, 20, 50, 100, 200],

            gridview: true,
            viewrecords: true,

            sortname: 'serviceTitle',
            sortorder: 'asc',

            autowidth:true,
            shrinkToFit: true,
            searchOnEnter:true,
            pager: '#serviceAllPager',
            scrollOffset:0,
            subGrid:false,
            gridComplete: function() {
                dynamicListSize("#allServices");
            },

            subGridRowExpanded: function(subgrid_id, row_id) {
                var subgrid_table_id;
                subgrid_table_id = subgrid_id+"_t";
                jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
                jQuery("#"+subgrid_table_id).jqGrid({
                    width:'800',
                    url:"listAllSubGrid?id="+row_id,
                    datatype: "json",
                    colNames: ['Software Instance Name', 'Instance Description', 'Host', 'Load Balanced', 'Instance Type', 'Instance Notes'],
                    colModel: [
                        {name:"tierName", width:150, editable:false,title:false},
                        {name:"tierDescription", width:150, editable:false,title:false},
                        {name:"host", width:80, editable:false,title:false},
                        {name:"loadBalanced", width:100, editable:false,title:false},
                        {name:"tierType", width:100, editable:false,title:false},
                        {name:"tierNotes", width:240, editable:false,title:false}
                    ],
                    rowNum:100,
                    autowidth:true,
                    shrinkToFit: true,
                    width:'100%',
                    height:'100%',
                    viewrecords: true,
                    gridview: true,

                });

            }


        });
        jQuery('#allServices').filterToolbar({id:'allServices', searchOnEnter:true});
        $("#allServices").jqGrid('navGrid','#serviceAllPager',{
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

        setTooltipsOnColumnHeader($("#allServices"),0,"Expand to see associated Software Instances");
        setTooltipsOnColumnHeader($("#allServices"),1,"The Name of the Service");
        setTooltipsOnColumnHeader($("#allServices"),2,"The Application this Service is associated with");
        setTooltipsOnColumnHeader($("#allServices"),3,"The Environment Assigned to the Application. e.g. Prod, Test, Dev, etc.");
        setTooltipsOnColumnHeader($("#allServices"),4,"A Brief Description of the function of this Application");
        setTooltipsOnColumnHeader($("#allServices"),5,"The Status of this Application. e.g . Available, Disabled, etc.");
        setTooltipsOnColumnHeader($("#allServices"),6,"The Primary System Administrator assigned to this Service");
        setTooltipsOnColumnHeader($("#allServices"),7,"The Primary System Administrator assigned to the Application");
        
        jQuery(window).bind('resize', function() {
            dynamicListSize('#allServices');
        }).trigger('resize');

    });

</script>
<table id="allServices"></table>
<div id="serviceAllPager"></div>