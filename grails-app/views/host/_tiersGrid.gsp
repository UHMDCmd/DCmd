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
    listTierGridUrl = 'listTiers?hostId=${hostInstance.id}'

    $(document).ready(function() {
        var appData;
        jQuery.ajax({
            async:false,
            url: '../application/listAppsAsSelect',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function(data) {
                appData = data.theList;
            },
            error: function () { console.log('Error updating list'); }
        });
        $("#btnAddTier").click(function(){
            $("#tier_list").jqGrid("editGridRow","new",
                    {addCaption:'Create new Service-Host Assignment',
                        width:500,
                        afterShowForm: function() {
                            $("#application").select2({
                                placeholder: 'Please Select...',
                                width:200,
                                data: appData
                            }).select2('val', '0');
                            $("#application").on('change', function(e) {
                                console.log(e);
                                jQuery.ajax({
                                    async:false,
                                    url: '../service/listServicesOfAppAsSelect',
                                    datatype:'json',
                                    contentType: 'application/json; charset=utf-8',
                                    data:{appId:e.val},
                                    success: function(data) {
                                        $("#service").select2('enable', true);
                                        $("#service").select2({data: function() {
                                            return {results: data.theList};},
                                            placeholder:'Select a Service...',
                                            width:200
                                        });
                                        //appData = data.theList;
                                    },
                                    error: function () { console.log('Error updating list'); }
                                });
                            });

                            $("#service").select2({
                                placeholder: 'Select Application first..',
                                width:200,
                                enable:false,
                                data: appData
                            }).select2('val','0');
                            $("#service").select2('disable', true);
                        },
                        editData:{service:function() {return document.getElementById('service').value}, application: function() {return document.getElementById('application').value}},
                        closeAfterAdd: true,
                        params:{hostId:${hostInstance.id}},
                        savekey:[true,13],
                        afterComplete: function() {
                            $("#technicalSupportStaff_list").trigger("reloadGrid");
                            $("#functionalSupportStaff_list").trigger("reloadGrid");
                        }
                    }
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

        jQuery("#tier_list").jqGrid({

            height:'auto',
            width:'1000',
            caption:'Service-Host Assignment List',
            showPager:'true',
            url:listTierGridUrl,
            editurl:'editTiers?hostId=${hostInstance.id}',
            datatype: "json",

            colNames:['','Application', 'Service', 'Service Primary SA', 'Load Balanced', 'Instance Type', 'Instance Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:20,
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                    keys: true, editbutton: false }
                },
               // {name:"tierName", width:200, editable:editOption, editoptions:{size:40}},
/*                {name:"application", width:160, editable:editOption, edittype:'select', editoptions: {dataUrl:'${createLink(controller:"application",action:"listApplicationsAsSelect")}',
                    dataEvents: [{type:'change', fn: function(e) {
                        var service = $("#service");
                        service.select2('enable', true);
                        service.select2({
                            placeholder: 'Please Select...',
                            maximumInputLength: 20,
                            width:200,
                            data: [${genService.listServicesFromAppAsSelect(e)}]
                        }).select2('val', '0');

                        service.innerHTML = "<select><option id=\'test\' value=\'test\'></select>";
                    }}],
                    dataInit:function(e){$(e).select2({
                        width: 200
                    })}
                }},
*/
                {name:'application', width:160, editable:editOption, edittype:'custom', editoptions: {
                    custom_element: myelem, custom_value:myvalue
                }},
                {name:'service', width:160, editable:editOption, edittype:'custom', editoptions: {
                    custom_element: myelem, custom_value:myvalue
                }},
                {name:'servAdmin', width:100, editable:false},
                {name:'loadBalanced', width:100, edittype:'checkbox', editable:editOption},
                {name:"tierType", width:100, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"tier",action:"listTierTypesAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 200
                    })}
                }},
                {name:'generalNote', width:300, editable:true,  editrules:{required:false}, editoptions:{size:40}},
                {name:'id', hidden:true}
            ],

            rowNum:1000,
          //  pager: jQuery('#application_list_pager'),
            viewrecords: true,
            gridview: true,
            editurl:'editTiers?hostId=${hostId}',
//            cellurl:'editTiers?hostId=${hostId}',
            cellEdit:false,
            cellsubmit: 'remote',
            shrinkToFit: true,
            autowidth: true,
            /*
            afterEditCell: function(rowid, cellname, value, iRow, iCol) {
                var myData;
                jQuery.ajax({
                    async:false,
                    url: '../service/listServicesOfAppAsSelect',
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
//                    createSearchChoice:function(term, data) { if ($(data).filter(function() { return this.text.localeCompare(term)===0; }).length===0) {return {id:term, text:term};} },
                    data: myData
                }).select2('val', '0');
            }
              */
        });

        dynamicGridSize('#tier_list');

        jQuery(window).bind('resize', function() {
            dynamicGridSize('#tier_list');
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
    function afterSubmitApplicationEvent(rowId, cellname, value, iRow, iCol) {
        var result = [true, ''];
    }


</script>
<table id="tier_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddTier" type="button" value="Create new Service-Host Assignment"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>