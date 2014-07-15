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
<%@  page import="edu.hawaii.its.dcmd.inf.ServiceService" %>
<%
    def hostService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.HostService').newInstance()
    def serviceService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.ServiceService').newInstance()
%>

<script type="text/javascript">
    if('${action}'=='edit')
    {
        editOption = true
    }
    else {
        editOption = false
    }
    listHostUrl = 'listServiceDependencies?serviceId=${serviceInstance.id}'

    $(document).ready(function() {

        $("#btnAddService").click(function(){
            $("#service_list").jqGrid("editGridRow","new",
                    {addCaption:'Assign new Service Dependency',
                        width:500, height:200,
                        closeAfterAdd: true,
                        params:{id:${serviceInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#service_list").jqGrid({

            height:'auto',
            caption:'Services Dependencies',
            showPager:'true',
            url:listHostUrl,
            editurl:'editServiceDependencies?serviceId=${serviceInstance.id}',
            datatype: "json",

            colNames:['', 'Service Name', 'Environment', 'Status', 'Application', 'Service Description', 'Service SA', 'Service Notes', 'Dependency Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                    keys: true, editbutton: false }
                },
                {name:"dependsOn", width:120, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"service",action:"listServicesAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 200,
                        placeholder: "-Select a Service-"
                    })}
                }},
                {name:'serviceEnv', width:80, editable:false},
                {name:'serviceStatus',width:80, editable:false},
                {name:'application',width:80, editable:false},
                {name:'serviceDescription',width:120, editable:false},
                {name:'serviceSA', width:100, editable:false},
                {name:'serviceNote',width:200, editable:false},
                {name:'generalNote', width:240, editable:true, editrules:{required:false}},
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
            cellurl:'editServiceDependencies?serviceId=${serviceInstance.id}',
            autowidth:true,
            scrollOffset:20

        });
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#service_list');
        }).trigger('resize');
        $('#service_list').closest('.ui-jqgrid-bdiv').width($('#service_list').closest('.ui-jqgrid-bdiv').width() + 10);

    });

</script>
<table id="service_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddService" type="button" value="Assign new Service Dependency"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>