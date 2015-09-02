%{-- <script type="text/javascript" src="../js/globals.js"></script> --}%
<g:render template="loadPurchase"/>
 <script type="text/javascript" src="../js/select2/jqgrid-fix.js"></script>

<script type="text/javascript">

    var addCloneAction = function(elm) {
        $("<div>", {
                    title: "Clone Row",
                    mouseover: function () {
                        $(elm).addClass('ui-state-hover');
                    },
                    mouseout: function () {
                        $(elm).removeClass('ui-state-hover');
                    },
                    click: function (e) {
                        cloneRow($(e.target).closest("tr.jqgrow").attr("id"));
                    }
                }
        ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
                .addClass("ui-pg-div ui-inline-custom")
                .append('<span class="ui-icon ui-icon-copy"></span>')
                .prependTo($(elm).children("div"));

    };
    var addDetailsAction = function(elm) {
        $("<div>", {
                    title: "Details",
                    mouseover: function() {
                        $(elm).addClass('ui-state-hover');
                    },
                    mouseout: function() {
                        $(elm).removeClass('ui-state-hover');
                    },
                    click: function(e) {
                        openItem($(e.target).closest("tr.jqgrow").attr("id"));
                    }
                }
        ).css({"margin-right": "5px", float: "left", cursor: "pointer"})
                .addClass("ui-pg-div ui-inline-custom")
                .append('<span class="ui-icon ui-icon-document"></span>')
                .prependTo($(elm).children("div"));
    };

    var fixPositionsOfFrozenDivs = function () {
        var grid = this.grid || this;
        var $rows;
        if (typeof grid.fbDiv !== "undefined") {
            $rows = $('>div>table.ui-jqgrid-btable>tbody>tr', grid.bDiv);
            $('>table.ui-jqgrid-btable>tbody>tr', grid.fbDiv).each(function (i) {
                var rowHight = $($rows[i]).height(), rowHightFrozen = $(this).height();
                if ($(this).hasClass("jqgrow")) {
                    $(this).height(rowHight);
                    rowHightFrozen = $(this).height()+0.2;
                    if (rowHight !== rowHightFrozen) {
                        $(this).height(rowHight + (rowHight - rowHightFrozen));
                    }
                }
            });
            $(grid.fbDiv).height(grid.bDiv.clientHeight);
            $(grid.fbDiv).css($(grid.bDiv).position());
        }
        if (typeof grid.fhDiv !== "undefined") {
            $rows = $('>div>table.ui-jqgrid-htable>thead>tr', grid.hDiv);
            $('>table.ui-jqgrid-htable>thead>tr', grid.fhDiv).each(function (i) {
                var rowHight = $($rows[i]).height(), rowHightFrozen = $(this).height();
                $(this).height(rowHight);
                rowHightFrozen = $(this).height();
                if (rowHight !== rowHightFrozen) {
                    $(this).height(rowHight + (rowHight - rowHightFrozen));
                }
            });
            $(grid.fhDiv).height(grid.hDiv.clientHeight);
            $(grid.fhDiv).css($(grid.hDiv).position());
        }
    };

    function addRowMain() {
        var gridName="allPurchases";
        var purchaseGrid = $("#allPurchases");
        var url="../purchase/editPurchase";
        var defaults = {oper:'add', fiscalYear:2000, uhContractTitle:'newContract'};
        jQuery.ajax({
            async:false,
            url: url,
            dataType: 'json',
            data: defaults,
            contentType: 'application/json; charset=utf-8',
            success: function(data) {
                var newRowId = data.id;
                purchaseGrid.jqGrid('addRowData', newRowId, data.object , 'first');

                addCloneAction(document.getElementById(data.id).cells[1]);
                addDetailsAction(document.getElementById(data.id).cells[1]);

                jQuery("#allPurchases").jqGrid('editRow', newRowId ,true);
                $("table#" + gridName + " tr#" + newRowId + " div.ui-inline-edit, " + "table#" + gridName + " tr#" + newRowId + " div.ui-inline-del").hide();
                $("table#" + gridName + " tr#" + newRowId + " div.ui-inline-save, " + "table#" + gridName + " tr#" + newRowId + " div.ui-inline-cancel").show();
            },
            complete: function(data) {

            },
            error: function () { console.log('Error Adding Row'); }
        });
    }

     function cloneRow(rowId) {
         var gridName = "allPurchases";
         var theGrid = $("#allPurchases");
         var url="../purchase/cloneRow?id="+rowId;

         jQuery.ajax({
             async:false,
             url: url,
             dataType: 'json',
             contentType: 'application/json; charset=utf-8',
             success: function(data) {
                 var newRowId = data.purchase.id;
                 data.purchase.purchaseType = data.purchaseType;
                 theGrid.jqGrid('addRowData', data.purchase.id, data.purchase, 'before', rowId);
                 addCloneAction(document.getElementById(data.purchase.id).cells[1]);
                 addDetailsAction(document.getElementById(data.purchase.id).cells[1]);

                 jQuery("#allPurchases").jqGrid('editRow', newRowId ,true);
                 $("table#" + gridName + " tr#" + newRowId + " div.ui-inline-edit, " + "table#" + gridName + " tr#" + newRowId + " div.ui-inline-del").hide();
                 $("table#" + gridName + " tr#" + newRowId + " div.ui-inline-save, " + "table#" + gridName + " tr#" + newRowId + " div.ui-inline-cancel").show();

             },
             complete: function(data) {
             //    $("#allPurchases").trigger('reloadGrid');
             //    $('#allPurchases').jqGrid('setSelection', data.purchase.id);
             },
             error: function () { console.log('Error cloning row'); }
         });
     }


    $(document).ready(function() {
        $( "#purchase_dialog" ).dialog({
            autoOpen: false,
            width: $(window).width()*0.9,   // 90% of total width
            height: 600,
            zIndex:100,
            show: {
                effect: "blind",
                duration: 500
            },
            hide: {
                effect: "blind",
                duration: 500
            },
            resize: function(event, ui) {
                var container = document.querySelector('#purchase_container');
                var msnry = new Masonry( container, {
                    // options
                    columnWidth: 400,
                    itemSelector: '.item'
                });
                var $targetGrid = $("#itemGrid");
                var jqGridWrapperId = "#gbox_" + $targetGrid.attr('id') //here be dragons, this is     generated by jqGrid.
                $targetGrid.setGridWidth($(jqGridWrapperId).parent().width()-1); //perhaps add padding calculation here?
                $targetGrid = $("#supportGrid");
                jqGridWrapperId = "#gbox_" + $targetGrid.attr('id') //here be dragons, this is     generated by jqGrid.
                $targetGrid.setGridWidth($(jqGridWrapperId).parent().width()-1); //perhaps add padding calculation here?
            }
        });

        jQuery("#allPurchases").jqGrid({
            height:'auto',
            width:'1000',
            url:'listAllPurchases',
            editurl:'editPurchase',
            datatype: "json",
            //datatype:'local',
            colNames:['','F/Y', 'Contract/ID', 'Vendor Name', 'Status', 'Type', 'Payment Type', 'Items', 'Total Tax', 'Total Cost', 'Anniversary', 'Notes', 'id'],
            colModel:[
                {name:'actions', index:'actions', editable:false, search:false, required:false, sortable:false, width:100, fixed:true, frozen:true,
                    formatter: 'actions', hidden:false, formatoptions: {
                    afterSave: function(e) {
                        $("#allPurchases").trigger("reloadGrid");
                        setTimeout(function () {
                                    $('#allPurchases').jqGrid('resetSelection');
                                    $('#allPurchases').jqGrid('setSelection', e);
                                },200
                        );
                    }
                }},
                {name:'fiscalYear', width:50, fixed:true, editable:true, title:false, search:true, align:"center", sort:true},
                {name:'uhContractTitle', width:80, editable:true, title:false},
                {name:'vendorName',width:80, editable:true, title:false},
                {name:'purchaseStatus', width:110, fixed:true, formatter:'select', editable:true, title:false, edittype:'select', unformat: purchaseStatusUnFormat,editoptions: {value:purchaseStatuses.listItems(),
                    dataInit:function(e){
                        //console.log($("#1_purchaseStatus"));
                        $(e).width(110).select2().select2('val', currentPurchaseStatus);
                    }
                }},
                {name:'purchaseType', width:100, fixed:true, editable:true, title:false, edittype:'select', unformat:purchaseTypeUnFormat, editoptions: {value:purchaseTypes.listItems(),
                    dataInit:function(e){
                       $(e).width(100).select2().select2('val', currentPurchaseType);
                    }
                }},
                {name:'paymentType', width:120, fixed:true, editable:true, title:false, edittype:'select', unformat: paymentTypeUnFormat, editoptions: {value:paymentTypes.listItems(),
                     dataInit:(function(e) {
                         $(e).width(120).select2().select2('val', currentPaymentType);
                     })}},
                {name:'totalQuantity', width: 60, editable:false, title:false, fixed:true, search:false, sortable:false},
                {name:'totalTax', width: 100, editable:false, title:false, fixed:true, search:false, sortable:false},
                {name:'totalCost', width: 100, editable:false, title:false, fixed:true, search:false, sortable:false},
                {name:'anniversary', width:80, fixed:true, editable:true, title:false, align:"center",editoptions:{size:20,
                    dataInit: function (el) {
                        $(el).datepicker({
                            beforeShow: function (input, inst) {
                                inst.dpDiv.addClass('anniversary');
                            },
                            afterClose: function(dateText, inst){
                                inst.dpDiv.removeClass('anniversary');
                            },
                            dateFormat: 'mm/dd'
                        });
                    }
                }},
                {name:'generalNote', width:200, editable:true},
                {name:'id', hidden:true}
            ],

            rowNum: 50,
            rowList: [10, 20, 50, 100, 200],
//            cellEdit:true,
//            cellSubmit:'remote',
//            cellurl:'editPurchase',
            gridview: true,
            viewrecords: true,
            sortname: 'uhContractTitle',

            headertitles:false,

            sortorder: 'asc',
            autowidth:true,
            shrinkToFit: true,
            searchOnEnter:true,
            footerrow:true,
            subGrid:true,

            subGridRowExpanded: function(subgrid_id, row_id) {
                var subgrid_table_id;
                subgrid_table_id = subgrid_id+"_t";
                jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
                jQuery("#"+subgrid_table_id).jqGrid({
                    url:"listAllPurchaseSubGrid?id="+row_id,
                    editurl:'editPurchase',
                    datatype: "json",
                    colNames:['','F/Y', 'Status', 'Purchase Type', 'Payment Type', 'Items', 'Total Tax', 'Total Cost', 'Anniversary', 'Notes', 'id'],
                    colModel: [
                        {name:'actions', index:'actions', editable:false, search:false, required:false, sortable:false, width:55, fixed:true, frozen:true,
                            formatter: 'actions', hidden:false, formatoptions: {
                            editbutton:false,
                            afterSave: function(e) {
                                $("#"+subgrid_table_id).trigger('reloadGrid');
                            }
                        }},
                        {name:'fiscalYear', width:50, fixed:true, editable:true, title:false, search:true, align:"center", sort:true},
                        {name:'purchaseStatus', width:110, fixed:true, formatter:'select', editable:true, title:false, edittype:'select', unformat: purchaseStatusUnFormat,editoptions: {value:purchaseStatuses.listItems(),
                            dataInit:function(e){
                                //console.log($("#1_purchaseStatus"));
                                $(e).width(110).select2().select2('val', currentPurchaseStatus);
                            }
                        }},
                        {name:'purchaseType', width:100, fixed:true, editable:true, title:false, edittype:'select', unformat:purchaseTypeUnFormat, editoptions: {value:purchaseTypes.listItems(),
                            dataInit:function(e){
                                $(e).width(100).select2().select2('val', currentPurchaseType);
                            }
                        }},
                        {name:'paymentType', width:120, fixed:true, editable:true, title:false, edittype:'select', unformat: paymentTypeUnFormat, editoptions: {value:paymentTypes.listItems(),
                            dataInit:(function(e) {
                                $(e).width(120).select2().select2('val', currentPaymentType);
                            })}},
                        {name:'totalQuantity', width: 60, editable:false, title:false, fixed:true},
                        {name:'totalTax', width: 100, editable:false, title:false, fixed:true},
                        {name:'totalCost', width: 100, editable:false, title:false, fixed:true},
                        {name:'anniversary', width:80, fixed:true, editable:true, title:false, align:"center",editoptions:{size:20,
                            dataInit: function (el) {
                                $(el).datepicker({
                                    beforeShow: function (input, inst) {
                                        inst.dpDiv.addClass('anniversary');
                                    },
                                    afterClose: function(dateText, inst){
                                        inst.dpDiv.removeClass('anniversary');
                                    },
                                    dateFormat: 'mm/dd'
                                });
                            }
                        }},
                        {name:'generalNote', width:200, editable:true},
                        {name:'id', hidden:true}
                    ],
                    rowNum:100,
                    width:'100%',
                    height:'100%',
                    viewrecords: true,
                    autowidth:true,
                    shrinkToFit: true,
                    gridview: true,
                    footerrow:true,
                    loadComplete: function() {
                        var iCol = 0;
                        $(this).find(">tbody>tr.jqgrow>td:nth-child(" + (iCol + 1) + ")")
                                .each(function() {addDetailsAction(this);});


                        console.log(subgrid_id);
                        var purchaseSubGrid = $("#" + subgrid_table_id);
                        var subTotalQuantity = purchaseSubGrid.jqGrid('getCol', 'totalQuantity', false, 'sum');
                        var subTotalTax = parseFloat(Math.round(purchaseSubGrid.jqGrid('getCol', 'totalTax', false, 'sum') * 100) / 100).toFixed(2);
                        var subTotalCost = parseFloat(Math.round(purchaseSubGrid.jqGrid('getCol', 'totalCost', false, 'sum') * 100) / 100).toFixed(2);
                        purchaseSubGrid.jqGrid('footerData','set', {fiscalYear: 'Total',
                            totalQuantity: subTotalQuantity,
                            totalTax: subTotalTax,
                            totalCost: subTotalCost
                        });
                    }
                });
            },

            pager: '#allPurchasePager',
            scrollOffset:0,
//            toolbar:[true, 'top'],
            gridComplete: function() {
                dynamicListSize("#allPurchases");

   //             $("#allPurchases").jqGrid('hideCol','cb');
            },
            loadComplete: function() {
 //               fixPositionsOfFrozenDivs.call(this); // Fix size of frozen columns

                // Create 'details' and 'Clone' Buttons on each row
                var iCol = 0;
                $(this).find(">tbody>tr.jqgrow>td:nth-child(" + (iCol + 2) + ")")
                        .each(function() {addCloneAction(this); addDetailsAction(this);});

                var purchaseGrid = $("#allPurchases");
                var totalQuantity = purchaseGrid.jqGrid('getCol', 'totalQuantity', false, 'sum');
                var totalTax = parseFloat(Math.round(purchaseGrid.jqGrid('getCol', 'totalTax', false, 'sum') * 100) / 100).toFixed(2);
                var totalCost = parseFloat(Math.round(purchaseGrid.jqGrid('getCol', 'totalCost', false, 'sum') * 100) / 100).toFixed(2);
                purchaseGrid.jqGrid('footerData','set', {fiscalYear: 'Total',
                    totalQuantity: totalQuantity,
                    totalTax: totalTax,
                    totalCost: totalCost
                });
            },
            resizeStop: function() {
 //               fixPositionsOfFrozenDivs.call(this);
            }
        });
        var currentPurchaseType;
        function purchaseTypeUnFormat( cellvalue, options, cell){
            currentPurchaseType = cellvalue;
        }
        var currentPurchaseStatus;
        function purchaseStatusUnFormat( cellvalue, options, cell) {
            currentPurchaseStatus = cellvalue;
        }
        var currentPaymentType;
        function paymentTypeUnFormat( cellvalue, options, cell) {
            currentPaymentType = cellvalue;
        }
        var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
            var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
            jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
        };

        setTooltipsOnColumnHeader($("#allPurchases"),0,"A unique ITS Id given to the Entity");
        setTooltipsOnColumnHeader($("#allPurchases"),1,"Virtualization method of Server (e.g., Solaris, VMWare, etc.)");
        setTooltipsOnColumnHeader($("#allPurchases"),2,"VMWare Cluster the Server hardware is assigned to (N/A if not a VMWare server)");
        setTooltipsOnColumnHeader($("#allPurchases"),3,"Host that is the primary OS of this Server (e.g. Global Zone for Solaris)");
        setTooltipsOnColumnHeader($("#allPurchases"),4,"Status of the Server (Available, Offline, Retired, etc.)");
        setTooltipsOnColumnHeader($("#allPurchases"),5,"The Primary System Administrator assigned to this Server");




        jQuery('#allPurchases').filterToolbar({id:'allPurchases', searchOnEnter:true});

//        jQuery('#allPurchases').jqGrid('setFrozenColumns');
        jQuery("#allPurchases").jqGrid('navGrid',"#allPurchasePager",{edit:false,add:false,del:false, search:false, refresh:false});
        jQuery("#allPurchases").jqGrid('navButtonAdd', '#allPurchasePager', {
            caption: 'Add Row',
            buttonicon: "ui-icon-plus",
            onClickButton: function() {
                addRowMain();
            }
        });


        jQuery(window).bind('resize', function() {
            dynamicListSize('#allPurchases');
        }).trigger('resize');

        if("${purchaseInstance}" != "") {
            openItem(${purchaseInstance?.id});
        }

       // $("#test123").select2();
    });
</script>


<g:render template="../advancedOptions" model="[pageType:'purchase', gridId:'#allPurchases', export:true, elapsePurchaseFilter:true, exportAction:'exportListAll', hostFilter:false]" />


<table id="allPurchases"></table>
<div id="allPurchasePager"></div>
%{--<input type="button" onclick="addRowMain()" value="Add Row"/>--}%
<g:render template="details"/>
<div id="purchase_dialog" title="Purchase Details">
    <div id="purchase_attributes"></div>
    <g:render template="popup-tabs"/>
</div>