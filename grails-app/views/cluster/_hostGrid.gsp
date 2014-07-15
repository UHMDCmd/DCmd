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
    if('${action}'=='edit')
    {
        editOption = true
    }
    else {
        editOption = false
    }
    listHostUrl = '../cluster/listHosts?clusterId=${clusterInstance.id}'

    $(document).ready(function() {

        $("#btnAddHost").click(function(){
            $("#host_list").jqGrid("editGridRow","new",
                    {addCaption:'Create new Host for this Server',
                        width:500,
                        closeAfterAdd: true,
                        recreateForm:true,

                        afterShowForm: function() {
                            $("#hostSA").select2({
                                width:160
                            });},
                        params:{clusterId:${clusterInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#host_list").jqGrid({

            height:'auto',
            width:'1000',
            caption:'Virtual Host List',
            url:listHostUrl,
            editurl:'../cluster/editHosts?clusterId=${clusterInstance.id}',
            datatype: "json",

            colNames: ['', 'Host Name', 'Environment', 'Status', 'Host SA', 'Host Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                        keys: true, editbutton: false }
                    },
                {name:"hostname", width:120, editable:editOption, formatter: 'showlink', formatoptions: {showAction:'show', baseLinkUrl:'../host/'}},
                {name:'hostEnv', width:100, editable:editOption,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"environment",action:"listEnvsAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 160
                    })}
                }},
                {name:'status', width:100, editable:editOption,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"status",action:"listStatusAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 160
                    })}
                }},
                {name:'hostSA', width:100, sortable:false, editable:editOption,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person", action:"listAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 160
                    })}
                }},
                {name:'generalNote', edittype:'textarea',width:250, editable:editOption},
                {name:'id', hidden:true}
            ],
            rowNum: 10,
            rowList: [5, 10, 20, 50, 100],
            afterSaveCell: afterSubmitHostEvent,
            sortname: 'hostname',
            sortorder: 'asc',
            searchOnEnter:true,
            headertitles: true,
            scrollOffset:0,
            pager:'#hostListPager',
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
            cellsubmit: 'remote',
            cellurl:'../cluster/editHosts?clusterId=${clusterInstance.id}',
            shrinkToFit: true,
            autowidth: true,


            loadComplete: function () {
                var iCol = getColumnIndexByName ($(this), 'isFixed'), rows = this.rows, i,
                        c = rows.length;

                for (i = 1; i < c; i += 1) {
                    $(rows[i].cells[iCol]).click(function (e) {
                        var id = $(e.target).closest('tr')[0].id;
                        isChecked = $(e.target).is(':checked');
                        jQuery.ajax({
                            async: false,
                            url: 'editHosts',
                            data: { oper:'edit', isFixed: isChecked, clusterId: ${clusterInstance.id}, id: id },
                            dataType: 'json',
                            contentType: 'application/json; charset=utf-8'

                        });
                    })
                }
            }
        });

        jQuery('#host_list').filterToolbar({id:'host_list', searchOnEnter:true});
        $("#host_list").jqGrid('navGrid','#hostListPager',{
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
        dynamicGridSize("#host_list");
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#host_list');
        }).trigger('resize');


    });


    var getColumnIndexByName = function(grid, columnName) {
        var cm = grid.jqGrid('getGridParam', 'colModel'), i, l;
        for (i = 1, l = cm.length; i < l; i += 1) {
            if (cm[i].name === columnName) {
                return i; // return the index
            }
        }
        return -1;
    };



    /*********************************************************/
    /* EVENT FUNCTIONS */
    /*********************************************************/
    function afterSubmitHostEvent(rowId, cellname, value, iRow, iCol) {

        var result = [true, ''];

        $("#host_list").trigger("reloadGrid");
        return result;
    }


</script>
<table id="host_list"></table>
<div id="hostListPager"></div>


<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddHost" type="button" value="Create Host"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>