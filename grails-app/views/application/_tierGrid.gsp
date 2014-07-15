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
    listTierUrl = 'listTierGrid?applicationId=${applicationInstance.id}'

    $(document).ready(function() {

        $("#btnAddTier").click(function(){
            $("#tier_list").jqGrid("editGridRow","new",
                    {addCaption:'Create new Service-Host Assignment',
                        width:500, height:200,
                        recreateForm:true,
                        afterComplete: afterSubmitSIEvent,
                        closeAfterAdd: true,
                       // after: function() {
                       //     var name = document.getElementById('tierName');
                       //     name.innerHTML = generateName(document.getElementById('tierService').select2('data').id);
                       //     name.value = generateName(document.getElementById('tierService').select2('data').id);
                       //     return [true, '', ''];
                       // },
                        params:{id:${applicationInstance.id}},
                        savekey:[true,13]}
            );

        });

        jQuery("#tier_list").jqGrid({

            height:'auto',
            caption:'Service-Host Assignment List',
            showPager:'true',
            url:listTierUrl,
            editurl:'editTierGrid?applicationId=${applicationInstance.id}',
            datatype: "json",

        colNames:['', 'Service', 'Host', 'Host Primary SA', 'Load-Balanced', 'Instance Notes','id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:20,
                    formatter: 'actions', hidden:!editOption,
                    formatoptions: {
                        keys: true, editbutton: false,
                        delOptions: {
                            onclickSubmit: function() {
                                setTimeout(function() {
                                    $("#service_list").trigger("reloadGrid");
                                }, 1000);
                            }
                        }
                    }
                },
                {name:"tierService", width:200, editable:editOption ,edittype:'select', editoptions: {dataUrl:"listServicesForAppAsSelect?appId=${applicationInstance.id}",
                    dataInit:function(e){$(e).select2({
                        width: 200
                    })}
                }},
                {name:"host", width:100, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"host",action:"listHostsAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 200
                    })}
                }},
                {name:"hostSA", width:100, editable:false},
                {name:'loadBalanced', width:70, edittype:'checkbox', editable:editOption},
                {name:'generalNote', width:300, editoptions: {size:40}, editable:editOption,editrules:{required:false}},
                {name:'id', hidden:true}
            ],

            rowNum:1000,
            viewrecords: true,
            gridview: true,
            cellEdit:false,
            cellsubmit: 'remote',
//            afterSubmit: afterSubmitSIEvent,
            afterSaveCell: afterSubmitSIEvent,
//            afterChange: afterSubmitSIEvent,
            cellurl:'editTierGrid?applicationId=${applicationInstance.id}',
            autowidth:true,
            scrollOffset:20
//            afterRefresh: afterSubmitSIEvent

        });
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#tier_list');
        }).trigger('resize');
        $('#tier_list').closest('.ui-jqgrid-bdiv').width($('#tier_list').closest('.ui-jqgrid-bdiv').width() + 10);

    });
  /*
    function generateName(serviceId) {
        jQuery.ajax({
            async: false,
            url: '/its/dcmd/service/generateName',
            data: { serviceId: serviceId},
            dataType: 'json',
            contentType: 'service/json; charset=utf-8',
            success: function(data) {
                result = data.generatedName
            },
            error: function (data) { alert(data); }
        });
        return result;
    }
    */
    /*********************************************************/
    /* EVENT FUNCTIONS */
    /*********************************************************/
    function afterSubmitSIEvent(rowId, cellname, value, iRow, iCol) {
        $("#tier_list").trigger("reloadGrid");
        $("#service_list").trigger("reloadGrid");
        return [true, '', ''];
    }
    function afterSubmitHostEvent(rowId, cellname, value, iRow, iCol) {

        var result = [true, ''];

//        var rowId = jQuery("#assignment_list").getGridParam('selrow');
        if(rowId) {
            jQuery.ajax({
                async: false,
                url: 'application/updateUnassignedByAssignment',
                data: { applicationId: ${applicationInstance.id}, rowId: rowId },
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function(data) {
                    $("#capacity_list").setRowData(data.capId, {unassigned: data.unassigned});
                    if(data.retVal) {
                        result = [true, '']
                    } else {
                        result= [false, ' is greater than max expandable'];
                    }
                },
                error: function () { alert('Error trying to validate'); }
            });
        }
        return result;
    }


</script>
<table id="tier_list" ></table>
<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddTier" type="button" value="Create new Service-Host Assignment"/>
    </div>
</g:if>

<div id='message' class="message" style="display:none;"></div>