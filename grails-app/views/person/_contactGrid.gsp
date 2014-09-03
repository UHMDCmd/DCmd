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
    listContactUrl = 'listContacts?personId=${personInstance.id}'

    $(document).ready(function() {

        $("#btnAddContact").click(function(){
            $("#contact_list").jqGrid("editGridRow","new",
                    {addCaption:'Create New Contact Info',
                        width:500, height:200,
//                        afterSubmit:afterSubmitContactEvent,
                        closeAfterAdd: true,
                        params:{person:${personInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });


        jQuery("#contact_list").jqGrid({

            height:'auto',
            width:'1200',
            caption:'Contact Info List',
            showPager:'true',
            url:listContactUrl,
            editurl:'editContacts?personId=${personInstance.id}',
            datatype: "json",

        colNames:['', 'Email','Phone #'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                        keys: true, editbutton: false }
                    },
                {name:'email', width:50, editable:true},
                {name:'phone', width:50, editable:true}


            ],

            rowNum:100,
            pager: jQuery('#contact_list_pager'),
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
            cellsubmit: 'remote',
            afterSaveCell: afterSubmitContactEvent,
            cellurl:'editContacts?personId=${personInstance.id}',

            loadComplete: function () {
                var iCol = getColumnIndexByName ($(this), 'isPrimary'), rows = this.rows, i,
                        c = rows.length;

                for (i = 1; i < c; i += 1) {
                    $(rows[i].cells[iCol]).click(function (e) {
                        var id = $(e.target).closest('tr')[0].id;
                        isChecked = $(e.target).is(':checked');
                        jQuery.ajax({
                            async: false,
                            url: 'person/editContacts',
                         data: { oper:'edit', isPrimary: isChecked, personId: ${personInstance.id}, id: id },

                          dataType: 'json',
                            contentType: 'application/json; charset=utf-8'

                        });
                    })
                }
            }

//        }).navGrid('#task_list_pager',
//                {add:true,edit:true,del:true,search:false,refresh:true},       // which buttons to show?
//                {closeAfterEdit:true},
//                {addCaption:'Create New Task'}, // add options
//                {})          // delete options
        })
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#contact_list');
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
    function afterSubmitContactEvent(rowId, cellname, value, iRow, iCol) {
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
<table id="contact_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
   %{--     <input class="ui-corner-all" id="btnAddContact" type="button" value="Update Contact Info"/>--}%
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>