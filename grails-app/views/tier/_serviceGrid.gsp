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

<r:require modules='select2' />

<script type="text/javascript">
    if('${action}'=='edit')
    {
        editOption = true
    }
    else {
        editOption = false
    }
    listServicesUrl = 'listServices?tierId=${tierInstance.id}'

    $(document).ready(function() {

        $("#btnAddService").click(function(){
            $("#service_list").jqGrid("editGridRow","new",
                    {addCaption:'Assign new Dependent Service',
                        width:500, height:200,
                        closeAfterAdd: true,
                        params:{id:${tierInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#service_list").jqGrid({

            height:'auto',
            caption:'Dependent Services',
            showPager:'true',
            url:listServicesUrl,
            editurl:'editServices?tierId=${tierInstance.id}',
            datatype: "json",

            colNames:['', 'Service Name', 'Environment', 'Status', 'Description', 'Service Admin', 'Service Notes', 'Dependency Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:20,
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                    keys: true, editbutton: false }
                },
                {name:"service", width:120, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"service",action:"listServicesAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 200,
                        placeholder: "-Select a Service-"
//                }).select2('val', "-Select a Tier-")}}},
                    })}
                }},
                {name:'serviceEnv',width:100, editable:false},
                {name:'serviceStatus',width:80, editable:false},
                {name:'serviceDescription',width:120,editable:false},
                {name:'serviceAdmin',width:100,editable:false},
                {name:'serviceNote',width:150,editable:false},
                {name:'generalNote',width:160,editable:editOption},
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
            cellurl:'editServices?tierId=${tierInstance.id}',
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
        <input class="ui-corner-all" id="btnAddService" type="button" value="Assign new Dependent Service"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>