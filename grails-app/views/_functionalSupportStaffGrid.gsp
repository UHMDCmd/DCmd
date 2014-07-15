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

<%@ page import="sun.security.jca.GetInstance" %>
<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
%>

<script type="text/javascript">
    if('${action}'=='edit')
    {
        editOption = true
    }
    else {
        editOption = false
    }
    listFunctionalSupportRoleUrl = "../person/listFunctionalSupport?objectId=${objectId}";

    $(document).ready(function() {

        $("#btnAddFunctionalSupportStaff").click(function(){
            var myData;
            jQuery.ajax({
                async:false,
                url: '../person/listRoleTypesAsSelect',
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                success: function(data) {
                    myData = data.theList;
                },
                error: function () { console.log('Error updating list'); }
            });

            $("#functionalSupportStaff_list").jqGrid("editGridRow","new",
                    {addCaption:'Create New Functional Support Staff Assignment',
                        width:500,
                        recreateForm:true,
                        afterShowForm: function() {
                            $("#roleName").select2({
                                placeholder: 'Please Select...',
                                maximumInputLength: 50,
                                width:200,
                                createSearchChoice:function(term, data) { if ($(data).filter(function() { return this.text.localeCompare(term)===0; }).length===0) {return {id:term, text:term};} },
                                data: myData
                            }).select2('val', '0');
                        },
                        closeAfterAdd: true,
                        editData:{roleNameSelect:function() {return document.getElementById('roleName').value}},
                        savekey:[true,13]}
//                                closeModal()
            );

        });


        function myelem (value, options) {
            var el = document.createElement("input");
            el.type="hidden";
            el.value = value;
            return el;
        }

        function myvalue(elem, operation, value) {
            if(operation === 'get') {
                return $(elem).val();
            } else if(operation === 'set') {
                $('input',elem).val(value);
            }
        }


        jQuery("#functionalSupportStaff_list").jqGrid({

            height:'auto',
            width:'1100',
            caption:'Functional Support Staff List',
            showPager:'true',
            url:listFunctionalSupportRoleUrl,
            editurl:"../person/editFunctionalSupport?objectId=${objectId}",
            datatype: "json",

            colNames:['', 'Role', 'Person', 'E-Mail', 'Phone', 'Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                    keys: true, editbutton: false }
                },
                {name:'roleName', width:50, editable:editOption, edittype:'custom', editoptions: {
                    custom_element: myelem, custom_value:myvalue
                }},
                {name:"person", width:100, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person",action:"listAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 200
                    })}
                }},
                {name:'email', width:50, editable:false},
                {name:'phone', width:50, editable:false},
                {name:'supportRoleNotes',editable:true,editrules:{required:false}, editoptions:{size:80}},
                {name:'id', hidden:true}
            ],

            rowNum:10,
            rowList:[1,2,3,4,5,6,7,8,9,10],
            pager: jQuery('#functionalSupportStaff_list_pager'),
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
            cellsubmit: 'remote',
            afterSaveCell: afterSubmitFunctionalSupportStaffEvent,
            afterSubmit: afterSubmitFunctionalSupportStaffEvent,
            afterEditCell: function(rowid, cellname, value, iRow, iCol) {
                var myData;
                jQuery.ajax({
                    async:false,
                    url: '../person/listRoleTypesAsSelect',
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    success: function(data) {
                        myData = data.theList;
                    },
                    error: function () { console.log('Error updating list'); }
                });

                $("#" + iRow + "_roleName").select2({
                    placeholder: 'Please Select...',
                    maximumInputLength: 50,
                    width:150,
                    createSearchChoice:function(term, data) { if ($(data).filter(function() { return this.text.localeCompare(term)===0; }).length===0) {return {id:term, text:term};} },
                    data: myData
                }).select2('val', '0');
            },
            beforeSubmitCell: function(rowid, cellname, value, iRow, iCol) {
                if(iCol == 1) {
                    return {roleNameSelect: document.getElementById(iRow + "_roleName").value};
                }
            },

            cellurl:'../person/editFunctionalSupport?objectId=${objectId}'

        });

        /*
         $('#functionalSupportStaff_list').jqGrid('setGridParam', { onSelectRow: function(id){
         $(this).jqGrid('viewGridRow', id);
         } } );
         */

        jQuery(window).bind('resize', function() {

            // Get width of parent container
            var width = document.getElementById("tabs").clientWidth;

            if (width == null || width < 1){
                // For IE, revert to offsetWidth if necessary
                width = document.getElementById("tabs").offsetWidth;
            }
            width = width - 40; // Fudge factor to prevent horizontal scrollbars
            if (width > 0 &&
                // Only resize if new width exceeds a minimal threshold
                // Fixes IE issue with in-place resizing when mousing-over frame bars
                    Math.abs(width - jQuery("#functionalSupportStaff_list").width()) > 5)
            {
                jQuery("#functionalSupportStaff_list").setGridWidth(width);
            }

        }).trigger('resize');
    });


    /*********************************************************/
    /* EVENT FUNCTIONS */
    /*********************************************************/
    function afterSubmitFunctionalSupportStaffEvent(rowId, cellname, value, iRow, iCol) {
        $("#functionalSupportStaff_list").trigger("reloadGrid");
    }


</script>
<table id="functionalSupportStaff_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddFunctionalSupportStaff" type="button" value="Add Functional Support Staff Assignment"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>