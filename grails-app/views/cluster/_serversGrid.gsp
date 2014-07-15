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
    listHostUrl = 'listHosts?clusterId=${clusterInstance.id}'

    $(document).ready(function() {

        jQuery("#host_list").jqGrid({

            height:'240',
            width:'1100',
            caption:'Assigned Host List',
            showPager:'true',
            url:listHostUrl,
            editurl:'editHosts?clusterId=${clusterInstance.id}',
            datatype: "json",

        colNames:['Hostname', 'Asset', 'Resource Type', 'Reserved', 'Allocated', 'Unit Type', 'Status', 'Notes', 'id'],
            colModel:[
                {name:'hostname', width:50, editable:false},
                {name:'asset', width:50, editable:false},
                {name:'resourceType', width:50, editable:false},
                {name:'amountReserved', width:50, editable:false},
                {name:'amountAllocated', width:50, editable:false},
                {name:'unitType', width:30, editable:false},
                {name:'status', width:50, editable:true, edittype:'select', editoptions: {value:{'Available':'Available', 'Maintenance':'Maintenance', 'Offline':'Offline', 'Error':'Error'}}},
                {name:'hostNotes',editable:false},
                {name:'id', hidden:true}
            ],

            rowNum:10,
            rowList:[1,2,3,4,5,6,7,8,9,10],
            pager: jQuery('#host_list_pager'),
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
            cellsubmit: 'remote',
            afterSaveCell: afterSubmitClusterHostEvent,
            cellurl:'editHosts?clusterId=${clusterInstance.id}',
            gridComplete: function() {
                dynamicListSize("#supportRole_list");
            }


//        }).navGrid('#task_list_pager',
//                {add:true,edit:true,del:true,search:false,refresh:true},       // which buttons to show?
//                {closeAfterEdit:true},
//                {addCaption:'Create New Task'}, // add options
//                {})          // delete options
        })
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#host_list');
        }).trigger('resize');
    });

    /*********************************************************/
    /* EVENT FUNCTIONS */
    /*********************************************************/
    function afterSubmitClusterHostEvent(rowId, cellname, value, iRow, iCol) {
/*
        var result = [true, ''];

//        var rowId = jQuery("#assignment_list").getGridParam('selrow');
        if(rowId) {
            jQuery.ajax({
                async: false,
                url: 'asset/updateUnassignedByAssignment',
                data: { assetId: ${clusterInstance.id}, rowId: rowId },
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
<table id="host_list"></table>

<div id='message' class="message" style="display:none;"></div>