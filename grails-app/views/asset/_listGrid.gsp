
<script type="text/javascript">

$(document).ready(function() {


jQuery("#allAssets").jqGrid({

height:'auto',
width:'1000',
caption:'Asset List',
url:'listAllAsset',
            datatype: "json",
            colNames:['Asset Type', 'ITS Id', 'Status', 'Location', 'Avail. for parts', 'Primary SA', 'Last Updated', 'Serial #', 'General Notes', 'id'],
            colModel:[
               {name:'assetType', width:65, editable:false, title:false},
             //   {name:'itsId', width:50, editable:false, formatter: 'showlink', formatoptions: {showAction:'altShow'},title:false},
                {name:'itsId', width:50, editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
                {name:'assetStatus', width:50, editable:false, edittype:'select', editoptions: {value:{'Available':'Available', 'Maintenance':'Maintenance', 'Problem':'Problem', 'Offline':'Offline'}},title:false},
                {name:'location', width:50, editable:false,title:false, sortable:false},
                {name:'isAvailableForParts', width:55, editable:false, edittype:'checkbox', editoptions: {value:'True:False'},title:false},
                {name:'primarySA', width:50, editable: false, title:false, sortable:false},
                {name:'lastUpdated', width:80, editable: false, title:false, search:false},
                {name:'serialNo', width:80, editable:false,title:false},
                {name:'generalNote',editable:false,title:false},
                {name:'id', hidden:true,title:false}
            ],

    rowNum: 50,
    rowList: [10, 20, 50, 100, 200],

    gridview: true,
    viewrecords: true,
    sortname: 'id',
    sortorder: 'desc',
    autowidth:true,
    shrinkToFit: true,
    searchOnEnter:true,
    headertitles:true,
    cmTemplate: { title: false },
    pager: '#allAssetPager',
    scrollOffset:0,
    gridComplete: function() {
        dynamicListSize("#allAssets");
    }


});

    jQuery('#allAssets').filterToolbar({id:'allAssets', searchOnEnter:true});
        $("#allAssets").jqGrid('navGrid','#allAssetPager',{
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


    var setTooltipsOnColumnHeader = function (grid, iColumn, text) {
        var thd = jQuery("thead:first", grid[0].grid.hDiv)[0];
        jQuery("tr.ui-jqgrid-labels th:eq(" + iColumn + ")", thd).attr("title", text);
    };

    setTooltipsOnColumnHeader($("#allAssets"), 0, "Either Physical Server, Rack, or Misc. Equipment");
    setTooltipsOnColumnHeader($("#allAssets"),1,"A unique ITS Id given to the Entity");
    setTooltipsOnColumnHeader($("#allAssets"),2,"Status e.g. Online, Offline, Standby, etc.");
    setTooltipsOnColumnHeader($("#allAssets"),3,"Location of the Asset");
    setTooltipsOnColumnHeader($("#allAssets"),4,"Parts availibility Status");
    setTooltipsOnColumnHeader($("#allAssets"),5,"Primary Service Administrator for Asset");
    setTooltipsOnColumnHeader($("#allAssets"),6,"Last updated of Record");
    setTooltipsOnColumnHeader($("#allAssets"),7,"Serial # of the Asset");

    jQuery(window).bind('resize', function() {
        dynamicListSize('#allAssets');
    }).trigger('resize');

    });

</script>
<table id="allAssets"></table>
<div id="allAssetPager"></div>
