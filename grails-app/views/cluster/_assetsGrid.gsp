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
    listAssetUrl = 'listAssets?clusterId=${clusterInstance.id}'


    function Init() {
        var div = document.getElementById("tabs");
        if (div.addEventListener) {
            div.addEventListener('resize', alert("TEST"), false);
        }
    }

    $(document).ready(function() {

        $("#btnAddServer").click(function(){
            $("#asset_list").jqGrid("editGridRow","new",
                    {addCaption:'Assign PhysicalServer to Cluster',
                        width:500, height:200,
//                        afterSubmit:afterSubmitSupportRoleEvent,
                        closeAfterAdd: true,
                        params:{cluster:${clusterInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });


        jQuery("#asset_list").jqGrid({

            height:'auto',
            width:'1100',
            caption:'Assigned Asset List',
            url:listAssetUrl,
            editurl:'editAssets?clusterId=${clusterInstance.id}',
            datatype: "json",


            colNames:['itsId', 'Primary SA', 'Total Memory', 'Memory Assigned', 'Total Cores', 'Max CPU Assigned', 'Notes', 'id'],
            colModel:[
                {name:'itsId', width:30, editable:false},
                {name:'primarySA', width:50, editable:false},
                {name:'totalMem', width:40, editable:false},
                {name:'assignedMem', width:50, editable:false},
                {name:'totalCores', width:40, editable:false},
                {name:'assignedCPU', width:50, editable:false},
                {name:'serverNotes',editable:false},
                {name:'id', hidden:true}
            ],

            rowNum: 20,
            rowList: [5, 10, 20, 50, 100],
            pager: '#assetListPager',
            viewrecords: true,
            gridview: true,
            cellEdit:false,
            cellsubmit: 'remote',
            afterSaveCell: afterSubmitClusterAssetEvent,
            cellurl:'editAssets?clusterId=${clusterInstance.id}',
            sortname: 'itsId',
            sortorder: 'asc',
            searchOnEnter:true,
            headertitles: true,
            scrollOffset:0,
            shrinkToFit: true,
            autowidth: true

//        }).navGrid('#task_list_pager',
//                {add:true,edit:true,del:true,search:false,refresh:true},       // which buttons to show?
//                {closeAfterEdit:true},
//                {addCaption:'Create New Task'}, // add options
//                {})          // delete options
        });
        jQuery('#asset_list').filterToolbar({id:'asset_list', searchOnEnter:true});
        $("#host_list").jqGrid('navGrid','#assetListPager',{
                    add:false,
                    del:false,
                    edit:false,
                    refresh:false,
                    refreshstate:"current",
                    search:false
                },
                {},//edit options
                {recreateForm:true //since clearAfterAdd is trueby default, recreate the form so we re-establish value for parent id
                });
        dynamicGridSize("#asset_list");
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#asset_list');
        }).trigger('resize');
    });

    /*********************************************************/
    /* EVENT FUNCTIONS */
    /*********************************************************/
    function afterSubmitClusterAssetEvent(rowId, cellname, value, iRow, iCol) {
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
<table id="asset_list"></table>
<div id="assetListPager"></div>

%{--
<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddServer" type="button" value="Assign PhysicalServer"/>
    </div>
</g:if>
--}%
<div id='message' class="message" style="display:none;"></div>