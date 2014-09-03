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
    listHostUrl = 'listHosts?tierId=${tierInstance.id}'

    $(document).ready(function() {

        $("#btnAddHost").click(function(){
            $("#host_list").jqGrid("editGridRow","new",
                    {addCaption:'Assign new Host',
                        width:500, height:200,
                        closeAfterAdd: true,
                        params:{id:${tierInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#host_list").jqGrid({

            height:'auto',
            caption:'Host List',
            showPager:'true',
            url:listHostUrl,
            editurl:'editHosts?tierId=${tierInstance.id}',
            datatype: "json",

            colNames: ['', 'Host Name', 'Environment', 'Status', 'Host SA', 'Physical Server', 'Host Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:20,
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                    keys: true, editbutton: false }
                },
                {name:"hostname", width:120, editable:editOption ,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"host",action:"listHostsAsSelect")}',
                        dataInit:function(e){$(e).select2({
                    width: 160
                        })}
                }},
                {name:'hostEnv', width:100, editable:false},
                {name:'status', width:100, editable:false},
                {name:'hostSA', width:100, editable:false},
                {name:'phyServer', width:100, editable:false},
                {name:'generalNote', width:200, editable:false},
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
            cellurl:'editHosts?tierId=${tierInstance.id}',
            autowidth:true,
            scrollOffset:20

        });
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#host_list');
        }).trigger('resize');
        $('#host_list').closest('.ui-jqgrid-bdiv').width($('#host_list').closest('.ui-jqgrid-bdiv').width() + 10);

    });



</script>
<table id="host_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddHost" type="button" value="Assign Host"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>