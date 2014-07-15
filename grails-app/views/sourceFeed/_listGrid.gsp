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


        jQuery("#allSources").jqGrid({

            height:'auto',
            width:'1000',
            caption:'Source List',
            url:'listAllSources',
            datatype: "json",
            colNames:['Source Name', 'Data Center', 'Capacity','IP address', 'SNMP Community', 'Load OID','General Notes', 'id'],
            colModel:[
                {name:'itsId', width:200, editable:false ,title:false, formatter: 'showlink', formatoptions: {showAction:'show_source_panel'},search: false, sortable:false},
                {name:'dataCenter', width:250, editable:false,title:false, search:false, sortable:false},
                {name:'capacity', width:120, editable:false,title:false, search:false, sortable:false},
                {name:'ipAddress', width:100, editable:false,title:false, search:false, sortable:false},
                {name:'SNMP_community', width:100, editable:false,title:false, search:false, sortable:false},
                {name:'load_OID', width:100, editable:false,title:false, search:false, sortable:false},
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
            pager: '#allSourcePager',
            scrollOffset:0,
            gridComplete: function() {
                dynamicListSize("#allSources");
            }


        });
        jQuery('#allSources').filterToolbar({id:'allSources', searchOnEnter:true});
        $("#allSources").jqGrid('navGrid','#allSourcePager',{
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


        jQuery(window).bind('resize', function() {
            dynamicListSize('#allSources');
        }).trigger('resize');

    });

</script>
<table id="allSources"></table>
<div id="allSourcePager"></div>
