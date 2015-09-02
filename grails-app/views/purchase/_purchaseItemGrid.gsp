<script src="../js/select2/jqgrid-old.js"></script>

<style>
.ui-jqgrid {
    max-width: 100% !important;
    width: auto !important;
}

.ui-jqgrid-view,
.ui-jqgrid-hdiv,
.ui-jqgrid-bdiv {
    width: auto !important;
}
</style>

<script type="text/javascript">
    var addPurchaseItemActions = function(elm) {
        $("<div>", {
                    title: "Clone Row",
                    mouseover: function() {
                        $(elm).addClass('ui-state-hover');
                    },
                    mouseout: function() {
                        $(elm).removeClass('ui-state-hover');
                    },
                    click: function(e) {
                        clonePurchaseItem($(e.target).closest("tr.jqgrow").attr("id"));
                    }
                }
        ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
                .addClass("ui-pg-div ui-inline-custom")
                .append('<span class="ui-icon ui-icon-copy"></span>')
                .prependTo($(elm).children("div"));

    };

    function clonePurchaseItem(rowId) {
        var gridName = "itemGrid";
        var itemGrid = $("#itemGrid");
        var url="../purchase/clonePurchaseItem?id="+rowId;

        jQuery.ajax({
            async:false,
            url: url,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function(data) {
                var newRowId = data.purchaseItem.id;
                data.purchaseItem.assetName = data.assetName;
                itemGrid.jqGrid('addRowData', data.purchaseItem.id, data.purchaseItem, 'before', rowId);
                console.log(data);
                console.log(document.getElementById(data.purchaseItem));
                addPurchaseItemActions(document.getElementById(data.purchaseItem.id).cells[1]);

                itemGrid.jqGrid('editRow', newRowId ,true);
                $("table#" + gridName + " tr#" + newRowId + " div.ui-inline-edit, " + "table#" + gridName + " tr#" + newRowId + " div.ui-inline-del").hide();
                $("table#" + gridName + " tr#" + newRowId + " div.ui-inline-save, " + "table#" + gridName + " tr#" + newRowId + " div.ui-inline-cancel").show();
            },
            complete: function(data) {

            },
            error: function () { console.log('Error cloning row'); }
        });
    }

    function addRowPurchaseItem(purchaseId) {
        var gridName="itemGrid";
        var purchaseItemGrid = $("#itemGrid");
        var url="../purchase/editPurchaseItem";
        var defaults = {oper:'add', description:'New Item', purchase:purchaseId};
        jQuery.ajax({
            async:false,
            url: url,
            dataType: 'json',
            data: defaults,
            contentType: 'application/json; charset=utf-8',
            success: function(data) {
                var newRowId = data.id;
                data.object.beginDate = data.beginDate;
                data.object.endDate = data.endDate;
                purchaseItemGrid.jqGrid('addRowData', newRowId, data.object , 'first');
                addPurchaseItemActions(document.getElementById(data.id).cells[1]);
                purchaseItemGrid.jqGrid('editRow', newRowId ,true);
                $("table#" + gridName + " tr#" + newRowId + " div.ui-inline-edit, " + "table#" + gridName + " tr#" + newRowId + " div.ui-inline-del").hide();
                $("table#" + gridName + " tr#" + newRowId + " div.ui-inline-save, " + "table#" + gridName + " tr#" + newRowId + " div.ui-inline-cancel").show();
            },
            error: function () { console.log('Error Adding Row'); }
        });
    }

    function loadPurchaseItemGrid(Id) {
        $("#itemGrid").GridUnload();
        jQuery("#itemGrid").jqGrid({
            height: 'auto',
            shrinkToFit:true,
            autowidth: true,
//            datatype: 'local',
//            data: view.model.attributes.itemList.toJSON(),
//            editurl: 'clientArray',
            sortname:'description',
            sortorder:'asc',
            datatype:'json',
            url:'listAllPurchaseItems?Id=' + Id,
            editurl:'editPurchaseItem',
            width: '99%',
            pager: '#itemGridPager',
            footerrow:true,
            colNames: ['','Description', 'Type', 'Asset', 'assetId', 'QTY', 'Unit Price', 'Pre-Tax', 'Tax', 'Total Cost', 'Begin Date', 'End Date', 'Notes', 'id'],
            colModel: [
                {name:'actions', index:'actions', editable:false, search:false, required:false, sortable:false, width:70, fixed:true, hidden:false,
                    formatter: 'actions', formatoptions: {
                        afterSave: function(e) {
                            $("#itemGrid").trigger("reloadGrid");
                            setTimeout(function () {
                                        $('#itemGrid').jqGrid('resetSelection');
                                        $('#itemGrid').jqGrid('setSelection', e);
                                    },300
                            );
                        },
                        delOptions:{zIndex:1500}
                    }
                },
                {name: 'description', align: 'left', editable:true, width:200},
                {name:'itemType', formatter:'select', editable:true, width:65, title:false, edittype:'select', unformat: purchaseItemTypeUnFormat,editoptions: {value:purchaseItemTypes.listItems(),
                    dataInit:function(e){
                        $(e).width(120).select2().select2('val', currentPurchaseItemType);
                    }
                }},

                {name: 'assetName', align: 'left', width:65, formatter:assetFormatter, unformat: assetUnFormat, editable:true, edittype:'select', editoptions: {value:assetOptions.listItems(),
                    dataInit:function(e){
                        $(e).width(100).select2().select2('val',selectedServer);
                    //    $(e).width(100).select2();
                    //    setTimeout(function() { $(e).width(100).select2().select2('val',selectedServer)}, 500);
                    }
                }},
                {name:'asset', hidden:true},
                {name: 'quantity', align: 'left', editable:true, width:30, editoptions:{size:'4'}},
                {name: 'itemUnitListPrice', align: 'left', editable:true, width:50},
                {name: 'itemsCostBeforeTax', align: 'left', editable:true, width:50},
                {name: 'itemsCostTaxOnly', align: 'left', editable:true, width:50},
                {name: 'itemsCost', align: 'left', editable:true, width:50},
                {name:'beginDate', width:70, fixed:true, editable:true, title:false, align:"center",editoptions:{size:20,
                    dataInit: function (el) {
                        $(el).datepicker({
                            dateFormat: 'mm/dd/yy'
                        });
                    }
                }},
                {name:'endDate', width:70, fixed:true, editable:true, title:false, align:"center",editoptions:{size:20,
                    dataInit: function (el) {
                        $(el).datepicker({
                            dateFormat: 'mm/dd/yy'
                        });
                    }
                }},
                {name: 'generalNote', align:'left', editable:true, width:130},
                {name: 'id', align: 'left', editable:false, hidden:true}
            ],
            loadComplete: function() {
                var $targetGrid = $("#itemGrid");
                var jqGridWrapperId = "#gbox_" + $targetGrid.attr('id'); //here be dragons, this is     generated by jqGrid.
                $targetGrid.setGridWidth($(jqGridWrapperId).parent().width()-2); //perhaps add padding calculation here?

                var totalQuantity = $targetGrid.jqGrid('getCol', 'quantity', false, 'sum');
                var avgUnitPrice = parseFloat(Math.round($targetGrid.jqGrid('getCol', 'itemUnitListPrice', false, 'avg') * 100) / 100).toFixed(2);
                var totalBeforeTax = parseFloat(Math.round($targetGrid.jqGrid('getCol', 'itemsCostBeforeTax', false, 'sum') * 100) / 100).toFixed(2);
                var totalCostTaxOnly =  parseFloat(Math.round($targetGrid.jqGrid('getCol', 'itemsCostTaxOnly', false, 'sum') * 100) / 100).toFixed(2);
                var totalCost =  parseFloat(Math.round($targetGrid.jqGrid('getCol', 'itemsCost', false, 'sum') * 100) / 100).toFixed(2);
                $targetGrid.jqGrid('footerData','set', {description: 'Total',
                    quantity: totalQuantity,
                    itemUnitListPrice: avgUnitPrice + " (avg)",
                    itemsCostBeforeTax: totalBeforeTax,
                    itemsCostTaxOnly: totalCostTaxOnly,
                    itemsCost: totalCost});

                var iCol = 0;
                $(this).find(">tbody>tr.jqgrow>td:nth-child(" + (iCol + 1) + ")")
                        .each(function() {addPurchaseItemActions(this)});

            },
            loadError: function (xhr, status, error) {
                alert('grid loading error' + error);
            }
        });

        var selectedServer;
        function assetUnFormat( cellvalue, options, cell){
            selectedServer = $("#itemGrid").jqGrid('getCell', options.rowId, 'asset');
        //    console.log(selectedServer);
        }

        function assetFormatter( cellvalue, options, cell){
            if(cell[1] != null) {
                //console.log("1");
                if(cell[2] == 'Server')
                    return "<a href='../physicalServer/show?id=" + cell[4] + "'>" + cell[3] + '</a>';
                else
                    return cell[3]
            }
            else if(cellvalue != null){
                if(cellvalue[0] != '<') {
                    if(cell[2] == 'Server')
                        return "<a href='../physicalServer/show?id=" + $("#itemGrid").jqGrid('getCell', options.rowId, 'asset') + "'>" + cellvalue + '</a>';
                    else
                        return cellvalue
                }
                else {
                    //console.log("3");
                    return cellvalue;
                }
            }
            else
                return "";

        };

        jQuery("#itemGrid").jqGrid('navGrid',"#itemGridPager",{edit:false,add:false,del:false, search:false, refresh:false},{},{},{zIndex:1234},{overlay:false});
        jQuery("#itemGrid").jqGrid('navButtonAdd', '#itemGridPager', {
            id:'itemAddBtn',
            caption: 'Add Row',
            buttonicon: "ui-icon-plus",
            onClickButton: function() {
                addRowPurchaseItem(Id);
            }
        });
        $('#addRowBtn').addClass('ui-state-disabled');

        function purchaseItemTypeUnFormat( cellvalue, options, cell){
            currentPurchaseItemType = cellvalue;
        }
    }
</script>