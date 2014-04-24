<script type="text/javascript">
    if('${action}'=='edit')
    {
        editOption = true
    }
    else {

        editOption = false
    }
    listHostUrl = '../physicalServer/listHosts?assetId=${assetInstance.id}'

    $(document).ready(function() {




        $("#btnAddHost").click(function(){
            $("#host_list").jqGrid("editGridRow","new",
                    {addCaption:'Create new Host for this Server',
                        width:500,
                        //afterSubmit:afterSubmitHostEvent,
                        closeAfterAdd: true,
                        params:{assetId:${assetInstance.id}},
                        savekey:[true,13]}
//                                closeModal()
            );
        });

        jQuery("#host_list").jqGrid({

            height:'auto',
            width:'1000',
            caption:'Virtual Host List',
            showPager:'true',
            url:listHostUrl,
            editurl:'../physicalServer/editHosts?assetId=${assetInstance.id}',
            datatype: "json",

            colNames: ['', 'Host Name', 'Host Type', 'Environment', 'Status', 'Host SA', 'Host Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, required:false, sortable:false, width:"20",
                    formatter: 'actions', hidden:!editOption, formatoptions: {
                        keys: true, editbutton: false }
                    },
                {name:"hostname", width:120, editable:editOption, formatter: 'showlink', formatoptions: {showAction:'show', baseLinkUrl:'../host/'}},
                {name:'type', width:100, editable:editOption,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"host",action:"listHostTypesAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 160
                    })}
                }},
                {name:'hostEnv', width:100, editable:editOption,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"environment",action:"listEnvsAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 160
                    })}
                }},
                {name:'status', width:100, editable:editOption,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"status",action:"listStatusAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 160
                    })}
                }},
                {name:'hostSA', width:100, editable:editOption,edittype:'select', editoptions: {dataUrl:'${createLink(controller:"person",action:"listAsSelect")}',
                    dataInit:function(e){$(e).select2({
                        width: 160
                    })}
                }},                {name:'generalNote', width:200, editable:editOption},
                {name:'id', hidden:true}
            ],

            rowNum:1000,
            pager: jQuery('#host_list_pager'),
            viewrecords: true,
            gridview: true,
            cellEdit:editOption,
            cellsubmit: 'remote',
            cellurl:'../physicalServer/editHosts?assetId=${assetInstance.id}',
            shrinkToFit: true,
            autowidth: true,


            loadComplete: function () {
                var iCol = getColumnIndexByName ($(this), 'isFixed'), rows = this.rows, i,
                        c = rows.length;

                for (i = 1; i < c; i += 1) {
                    $(rows[i].cells[iCol]).click(function (e) {
                        var id = $(e.target).closest('tr')[0].id;
                        isChecked = $(e.target).is(':checked');
                        jQuery.ajax({
                            async: false,
                            url: 'editHosts',
                            data: { oper:'edit', isFixed: isChecked, assetId: ${assetInstance.id}, id: id },
                            dataType: 'json',
                            contentType: 'application/json; charset=utf-8'

                        });
                    })
                }
            }
        })
        jQuery(window).bind('resize', function() {
            dynamicGridSize('#host_list');
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
    function afterSubmitServerEvent(rowId, cellname, value, iRow, iCol) {

        var result = [true, ''];

//        var rowId = jQuery("#assignment_list").getGridParam('selrow');
        /*
        if(rowId) {
            jQuery.ajax({
                async: false,
                url: 'asset/updateUnassignedByAssignment',
                data: { assetId: ${assetInstance.id}, rowId: rowId },
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
        $("#capacity_list").trigger("reloadGrid");
    }


</script>
<table id="host_list"></table>

<g:if test="${action=='edit' || action=='create'}">
    <div style="margin-top:5px">
        <input class="ui-corner-all" id="btnAddHost" type="button" value="Create Host"/>
    </div>
</g:if>
<div id='message' class="message" style="display:none;"></div>