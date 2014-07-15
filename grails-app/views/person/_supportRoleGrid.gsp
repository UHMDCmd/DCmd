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
    listSupportRoleUrl = 'listSupportRoles?personId=${personInstance.id}'

    $(document).ready(function() {

        $("#btnAddSupportRole").click(function(){
            $("#supportRole_list").jqGrid("editGridRow","new",
                    {addCaption:'Create New Support Role Info',
                        width:500, height:200,
//                        afterSubmit:afterSubmitSupportRoleEvent,
                        closeAfterAdd: true,
                        params:{person:${personInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#supportRole_list").jqGrid({

            height:'auto',
            width:'1200',
            caption:'Support Roles List',
            url:listSupportRoleUrl,
            editurl:'editSupportRoles?personId=${personInstance.id}',
            datatype: "json",

        colNames:['', 'Supported Object', 'Object Type', 'Role', 'Support Type', 'Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, search:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                        keys: true, editbutton: false }
                    },
                {name:'supportedObject', width:40, sortable:false, editable:false, edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person",action:"listSupportableObjectsAsList")}?personId=${personInstance.id}&rowId=${rowId}'}},
                {name:'objectType', width:50, editable:false},
                {name:'roleName', width:50, editable:editOption, edittype:'select', editoptions: {value: {'Datebase Administator':'Datebase Administator', 'Executive Lead':'Executive Lead', 'Functional Lead':'Functional Lead', 'Key Stakeholder':'Key Stakeholder', 'System Administrator':'System Administrator', 'Team Lead':'Team Lead', 'Technical Lead':'Technical Lead'}}},
                {name:'roleType', width:50, editable:editOption, edittype:'select', editoptions: {value:{'Functional':'Functional', 'Technical':'Technical', 'Fiscal':'Fiscal'}}},
                {name:'supportRoleNotes',editable:editOption,editrules:{required:false}},
                {name:'id', hidden:true}
            ],

            rowNum: 20,
            rowList: [10, 20, 50, 100, 200],
            viewrecords: true,
            gridview: true,
            autowidth:true,
            cellEdit:editOption,
            cellsubmit: 'remote',
            afterSaveCell: afterSubmitSupportRoleEvent,
            cellurl:'editSupportRoles?personId=${personInstance.id}',
            sortname: 'supportedObject',
            sortorder: 'asc',
            shrinkToFit: true,
            searchOnEnter:true,
            pager: '#supportRolePager',
            headertitles: true,
            scrollOffset:0


//        }).navGrid('#task_list_pager',
//                {add:true,edit:true,del:true,search:false,refresh:true},       // which buttons to show?
//                {closeAfterEdit:true},
//                {addCaption:'Create New Task'}, // add options
//                {})          // delete options
        });

        var info = "this cell has been selected"
        $('#supportRole_List').jqGrid('setGridParam', { onSelectRow: function(id){ alert(info); } } );

        jQuery('#supportRole_list').filterToolbar({id:'supportRole_list', searchOnEnter:true});
        $("#supportRole_list").jqGrid('navGrid','#supportRolePager',{
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

        dynamicGridSize("#supportRole_list");

        jQuery(window).bind('resize', function() {
            dynamicGridSize('#supportRole_list');
        }).trigger('resize');
    });


    /*********************************************************/
    /* EVENT FUNCTIONS */
    /*********************************************************/
    function afterSubmitSupportRoleEvent(rowId, cellname, value, iRow, iCol) {
/*
        var result = [true, ''];

//        var rowId = jQuery("#assignment_list").getGridParam('selrow');
        if(rowId) {
            jQuery.ajax({
                async: false,
                url: 'asset/updateUnassignedByAssignment',
                data: { assetId: ${personInstance.id}, rowId: rowId },
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
        */
    }


</script>
<table id="supportRole_list"></table>
<div id="supportRolePager"></div>

<!--
<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddSupportRole" type="button" value="Add SupportRole Info"/>
    </div>
</g:if>
-->

<div id='message' class="message" style="display:none;"></div>