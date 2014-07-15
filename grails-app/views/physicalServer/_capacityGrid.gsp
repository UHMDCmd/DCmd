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
    listCapacityUrl = '../physicalServer/listCapacities?physicalServerId=${physicalServerId}'

    $(document).ready(function() {

/*********************************************************/
/* ADD BUTTON FUNCTION */
/*********************************************************/
        $("#btnAddCapacity").click(function(){
            $("#capacity_list").jqGrid("editGridRow","new",
                    {addCaption:'Create New Physical Capacity',
                        width:500, height:220,
  //                      afterSubmit:afterSubmitCapacityEvent,
                        closeAfterAdd: true,
                        params:{main_asset:${physicalServerId}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

/*********************************************************/
/* GRID DEFINITION */
/*********************************************************/
        jQuery("#capacity_list").jqGrid({

            height:'auto',
            width:'1200',
            caption:'Capacity List',
            showPager:'true',
            url:listCapacityUrl,
            editurl:'../physicalServer/editCapacities?assetId=${physicalServerId}',
            datatype: "json",

        colNames:['', '<div class="fixGridWidth">Resource Type</div>', 'Total Capacity', 'Reserved', 'Allocated', 'Max Expandable', 'Unit', 'Date Created', 'Notes', 'id'],
            colModel:[

                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                        keys: true, editbutton: false, delbutton: true }
                },

                {name:'resourceType', width:55, editable:true, edittype:'select', editoptions: {value:{'CPU':'CPU', 'Memory':'Memory', 'Disk':'Disk'}}},
                {name:'currentCapacity', width:50, editable:true},
                {name:'reserved', width:50, editable:false},
                {name:'allocated', width:50, editable:false},
                {name:'maxExpandableCapacity', width:50, editable:true, editrules:{number:true} },
                {name:'unitType', width:30, editable:true, edittype:'select', editoptions: {value:{'Threads':'Threads', 'Gigabytes':'Gigabytes', 'Terabtyes':'Terabytes'}}},

                {name:'dateCreated', editable: true, width:60,
                    editoptions:{size:12, dataInit:function(el)
                    {
                        $(el).datepicker({dateFormat:'yy-mm-dd'}); },

                        defaultValue: function()
                        {
                            var currentTime = new Date();
                            var month = parseInt(currentTime.getMonth() + 1);
                            month = month <= 9 ? "0"+month : month;
                            var day = currentTime.getDate();
                            day = day <= 9 ? "0"+day : day;
                            var year = currentTime.getFullYear();
                            return year+"-"+month + "-"+day; }
                    }
                },

               {name:'capacityNotes',editable:true,editrules:{required:false}, width:"150"},
               {name:'id', hidden:true}
            ],

            rowNum:4,
            rowList:[1,2,3,4],
            pager: jQuery('#capacity_list_pager'),
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
//            afterSaveCell: afterSubmitCapacityEvent,
            cellsubmit: 'remote',
            cellurl:'../physicalServer/editCapacities?assetId=${physicalServerId}',
            keys:true
        })

        jQuery(window).bind('resize', function() {
            dynamicGridSize('#capacity_list');
        }).trigger('resize');


    });

/*********************************************************/
/* VALIDATION FUNCTIONS */
/*********************************************************/

    function validateCapExpand(data, value) {
        var result = [true, ''];

        var rowId = jQuery("#capacity_list").getGridParam('selrow');
        if(rowId) {
            jQuery.ajax({
                async: false,
                url: 'asset/validateCapExpand',
                data: { currentCapacity: data, assetId: ${physicalServerId}, rowId: rowId },
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function(data) {
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

/*********************************************************/
/* EVENT FUNCTIONS */
/*********************************************************/
    function afterSubmitCapacityEvent(rowId, cellname, value, iRow, iCol) {

    var result = [true, ''];

    if(rowId) {
        jQuery.ajax({
            async: false,
            url: 'asset/updateUnassigned',
            data: { assetId: ${physicalServerId}, rowId: rowId, cellname: cellname, value: value },
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function(data) {

                $("#capacity_list").setRowData(rowId, {unassigned: data.unassigned});
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
<table id="capacity_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddCapacity" type="button" value="Add Capacity"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>