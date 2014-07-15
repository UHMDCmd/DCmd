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

<r:require modules='select2' />


<script type="text/javascript">
    if('${action}'=='edit')
    {
        editOption = true
    }
    else {
        editOption = false
    }
    listDependencyUrl = 'listServiceGrid?applicationId=${applicationInstance.id}'

    $(document).ready(function() {

        $("#btnAddService").click(function(){
            $("#service_list").jqGrid("editGridRow","new",
                    {addCaption:'Create new Service',
                        width:500, height:250,
                        recreateForm:true,
//                        afterSubmit: afterSubmitServiceEvent,
                        closeAfterAdd: true,
                        params:{id:${applicationInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#service_list").jqGrid({

            height:'auto',
            caption:'Service List',
            showPager:'true',
            url:listDependencyUrl,
            editurl:'editServiceGrid?applicationId=${applicationInstance.id}',
            datatype: "json",

        colNames:['', 'Service Name', 'Environment', 'Description', 'Hosts', 'Service Primary SA', 'Notes','id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                    keys: true, editbutton: false,
                    delOptions: {
                        onclickSubmit: function() {
                            setTimeout(function() {
                                $("#tier_list").trigger("reloadGrid");
                            }, 1000);
                        }
                    }
                }
                },
                {name:'serviceTitle', width:120, editable:editOption, editoptions: {size:40}, formatter: 'showlink', formatoptions: {showAction:'show', baseLinkUrl:'../service/'}},
                {name:"serviceEnv", width:60, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"environment",action:"listEnvsAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 200
                    })}
                }},
                {name:'serviceDescription', width:120, editable:editOption, editoptions: {size:40}},
                {name:'hosts', width:140, editable:false},
                {name:"serviceAdmin", width:100, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person",action:"listAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 200
                    })}
                }},
                    /*
                {name:"serviceAdmin", width:80, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person",action:"listAsSelect")}',
                    //dataEvents: [ {type: 'change', fn: function(e) {$(e).trigger("reloadGrid");}}],
                    dataInit:function(e){$(e).select2({
                        placeholder: 'Please Select...',
                        width: 200
                    }).select2('val', 0)}
                }},
                */
                {name:'generalNote', width:200, editable:editOption,editrules:{required:false}, editoptions: {size:40}},
                {name:'id', hidden:true}
            ],

            rowNum:1000,
            viewrecords: true,
            gridview: true,
            cellEdit:true,
            cellsubmit: 'remote',
            cellurl:'editServiceGrid?applicationId=${applicationInstance.id}',
            autowidth:true,
            afterSaveCell: afterSubmitServiceEvent,
//            afterSubmit: reloadGridValues,
            scrollOffset:20

        });
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#service_list');
        }).trigger('resize');
//        $('#dependency_list').closest('.ui-jqgrid-bdiv').width($('#tier_list').closest('.ui-jqgrid-bdiv').width() + 10);

    });

    /*********************************************************/
    /* EVENT FUNCTIONS */
    /*********************************************************/
    function afterSubmitServiceEvent(rowId, cellname, value, iRow, iCol) {
        $("#service_list").trigger("reloadGrid");
        $("#tier_list").trigger("reloadGrid");
        return [true, '', ''];
    }

    function reloadGridValues(rowId, cellname, value, iRow, iCol) {
        $("#service_list").trigger("reloadGrid");
    }


</script>
<table id="service_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddService" type="button" value="Create new Service"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>