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
    listApplicationUrl = 'listAppServerAssignments?hostId=${hostInstance.id}'

    $(document).ready(function() {




        $("#btnAddAppServerAssignment").click(function(){
            $("#appServerAssignments_list").jqGrid("editGridRow","new",
                    {addCaption:'Assign Resources from this Host to an Application',
                        width:500,
//                        afterSubmit:afterSubmitApplicationEvent,
                        closeAfterAdd: true,
                        params:{hostId:${hostInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#appServerAssignments_list").jqGrid({

            height:'200',
            width:'1000',
            caption:'App Resource Utilization List',
            showPager:'true',
            url:listApplicationUrl,
            editurl:'editAppServerAssignments?hostId=${hostInstance.id}',
            datatype: "json",

            colNames:['','App Name', 'Environment', 'Description', 'Resource Type', 'Utilized', 'Unit', 'Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                    keys: true, editbutton: false }
                },
                {name:'application', width:50, editable:true, edittype:'select', editoptions: {dataUrl:'${createLink(controller:"host",action:"listApplicationsAsSelect")}'}},
                {name:'appEnv', editable:false, width:45},
                {name:'applicationDescription', width:65, editable:false},
                {name:'resourceType', width:40, editable:true, edittype:'select', editoptions: {value:{'CPU':'CPU', 'Memory':'Memory', 'Disk':'Disk'}}},
                {name:'amountReserved', width:30, editable:true},
//                {name:'mountAllocated', width:40, editable:true},
                {name:'unitType', width: 40, editable:true, edittype:'select', editoptions: {value:{'Threads':'Threads', 'Gigabytes':'Gigabytes'}}},
                {name:'assignmentNote', editable:true},
                {name:'id', hidden:true}
            ],

            rowNum:1000,
          //  pager: jQuery('#application_list_pager'),
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
            cellsubmit: 'remote',
            afterSaveCell: afterSubmitApplicationEvent,
            cellurl:'editAppServerAssignments?hostId=${hostInstance.id}',
            shrinkToFit: true,
            autowidth: true,

           /*
            loadComplete: function () {
                var iCol = getColumnIndexByName ($(this), 'isFixed'), rows = this.rows, i,
                        c = rows.length;

                for (i = 1; i < c; i += 1) {
                    $(rows[i].cells[iCol]).click(function (e) {
                        var id = $(e.target).closest('tr')[0].id;
                        isChecked = $(e.target).is(':checked');
                        jQuery.ajax({
                            async: false,
                            url: 'host/editApplications',
                            data: { oper:'edit', isFixed: isChecked, hostId: ${hostInstance.id}, id: id },
                            dataType: 'json',
                            contentType: 'application/json; charset=utf-8'

                        });
                    })
                }
            }
            */
        })
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#appServerAssignments_list');
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
    function afterSubmitApplicationEvent(rowId, cellname, value, iRow, iCol) {

        var result = [true, ''];

//        var rowId = jQuery("#assignment_list").getGridParam('selrow');
        /*
         if(rowId) {
         jQuery.ajax({
         async: false,
         url: 'host/updateUnassignedByAssignment',
         data: { hostId: ${hostInstance.id}, rowId: rowId },
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
  //      $("#resource_list").trigger("reloadGrid");
    }


</script>
<table id="appServerAssignments_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddAppServerAssignment" type="button" value="Assign App Resources"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>