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

<%@  page import="edu.hawaii.its.dcmd.inf.HostService" %>
<%@  page import="edu.hawaii.its.dcmd.inf.TierService" %>
<%
    def hostService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.HostService').newInstance()
    def tierService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.TierService').newInstance()
%>

<script type="text/javascript">
    if('${action}'=='edit')
    {
        editOption = true
    }
    else {
        editOption = false
    }
    listSoftwareDependencyUrl = 'listSoftwareDependencies?serviceId=${serviceInstance.id}'

    $(document).ready(function() {

        $("#btnAddDependency").click(function(){
            $("#dependency_list").jqGrid("editGridRow","new",
                    {addCaption:'Assign a Software Dependency',
                        width:500, height:200,
                        closeAfterAdd: true,
                        params:{id:${serviceInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#dependency_list").jqGrid({

            height:'auto',
            caption:'Software Dependency List',
            showPager:'true',
            url:listSoftwareDependencyUrl,
            editurl:'editSoftwareDependencies?serviceId=${serviceInstance.id}',
            datatype: "json",

        colNames:['', 'Software Instance Name', 'Instance App.', 'App Env.',  'Instance Host', 'Load Balanced', 'Instance Type', 'Instance Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:20,
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                    keys: true, editbutton: false }
                },
                {name:"tier", width:200, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"tier",action:"listTiersAsSelect")}',
                        dataInit:function(e){$(e).select2({
                    width: 240
                        })}
                }},
                {name:'application', width:100 ,editable:false},
                {name:'appEnv', width:80 ,editable:false},
                {name:'host', width:80 ,editable:false},
                {name:'loadBalanced', width:100 ,editable:false},
                {name:'tierType', width:100 ,editable:false},
                {name:'generalNote', width:200 , editable:false, editrules:{required:false}},
                {name:'id', hidden:true}
            ],

            gridComplete: function() {
                return true;
            },

            rowNum:1000,
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
            cellsubmit: 'remote',
//            afterSaveCell: afterSubmitHostEvent,
            cellurl:'editSoftwareDependencies?serviceId=${serviceInstance.id}',
            autowidth:true,
            scrollOffset:20

        });
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#dependency_list');
        }).trigger('resize');
        $('#dependency_list').closest('.ui-jqgrid-bdiv').width($('#dependency_list').closest('.ui-jqgrid-bdiv').width() + 10);

    });

    /*********************************************************/
    /* EVENT FUNCTIONS */
    /*********************************************************/
    function afterSubmitHostEvent(rowId, cellname, value, iRow, iCol) {

        var result = [true, ''];

//        var rowId = jQuery("#assignment_list").getGridParam('selrow');
        if(rowId) {
            jQuery.ajax({
                async: false,
                url: 'service/updateUnassignedByAssignment',
                data: { serviceId: ${serviceInstance.id}, rowId: rowId },
                dataType: 'json',
                contentType: 'service/json; charset=utf-8',
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
<table id="dependency_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddDependency" type="button" value="Assign a Software Dependency"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>