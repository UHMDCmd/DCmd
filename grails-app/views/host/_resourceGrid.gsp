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
    listVRUrl = 'listResources?hostId=${hostInstance.id}'

    $(document).ready(function() {

        $("#btnAddResource").click(function(){
            $("#resource_list").jqGrid("editGridRow","new",
                    {addCaption:'Create New Resource Assignment',
                        width:500,
//                        afterSubmit:afterSubmitResourceEvent,
                        closeAfterAdd: true,
                        params:{host:${hostInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#resource_list").jqGrid({

            height:'120',
            autowidth:true,
            caption:'Assigned Resources List',
            showPager:'true',
            url:listVRUrl,
            editurl:'editResources?hostId=${hostInstance.id}',
            datatype: "json",

        colNames:['', 'Cluster', 'Asset', 'Resource Type', 'Reserved', 'Allocated', 'Unit', 'Date Assigned', 'Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                        keys: true, editbutton: false }
                    },
                {name:'cluster', width:65, editable:true, edittype:'select', editoptions: {dataUrl:'${createLink(controller:"host", action:"listClustersAsSelect")}'}},
                {name:'asset', width:50, editable:true, edittype:'select', editoptions: {dataUrl: '${createLink(controller:"host", action:"listAssetsByClusterAsSelect")}'}},
                {name:'resourceType', width:50, editable:true, edittype:'select', editoptions: {value:{'CPU':'CPU', 'Memory':'Memory', 'Disk':'Disk'}}},
                {name:'reservedAmount', width:30, editable:true},
                {name:'allocatedAmount', width:30, editable:true},
                {name:'unitType', width:30, editable:true, edittype:'select', editoptions: {value:{'Threads':'Threads', 'Gigabytes':'Gigabytes', 'Terabtyes':'Terabytes'}}},
                {name:'dateAssigned', editable: false, width:60},
               {name:'allocNotes',editable:true,editrules:{required:false}},
               {name:'id', hidden:true}
            ],

            rowNum:20,
            rowList:[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20],
      //      pager: jQuery('#resource_list_pager'),
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
            cellsubmit: 'remote',
            afterSaveCell: afterSubmitResourceEvent,
            cellurl:'editResources?hostId=${hostInstance.id}'

//        }).navGrid('#task_list_pager',
//                {add:true,edit:true,del:true,search:false,refresh:true},       // which buttons to show?
//                {closeAfterEdit:true},
//                {addCaption:'Create New Task'}, // add options
//                {})          // delete options
        })

        jQuery(window).bind('resize', function() {
            dynamicGridSize('#resource_list');
        }).trigger('resize');
    });

    /*********************************************************/
    /* EVENT FUNCTIONS */
    /*********************************************************/
    function afterSubmitResourceEvent(rowId, cellname, value, iRow, iCol) {
/*
        var result = [true, ''];

//        var rowId = jQuery("#assignment_list").getGridParam('selrow');
        if(rowId) {
            jQuery.ajax({
                async: false,
                url: 'asset/updateUnassignedByAssignment',
                data: { assetId: ${hostInstance.id}, rowId: rowId },
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
<table id="resource_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddResource" type="button" value="Add Resource Assignment"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>