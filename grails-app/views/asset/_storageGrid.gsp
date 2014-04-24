
<script type="text/javascript">

$(document).ready(function() {


jQuery("#allStoredAssets").jqGrid({

height:'auto',
width:'1000',
caption:'Asset List',
url:'listAllStoredAsset',
            datatype: "json",
            colNames:['Asset Type', 'ITS Id', 'Status', 'Location', 'Avail. for parts', 'Primary SA', 'Last Updated', 'Serial #', 'General Notes', 'id'],
            colModel:[
               {name:'assetType', width:65, editable:false, title:false},
             //   {name:'itsId', width:50, editable:false, formatter: 'showlink', formatoptions: {showAction:'altShow'},title:false},
                {name:'itsId', width:50, editable:false, formatter: 'showlink', formatoptions: {showAction:'show'},title:false},
                {name:'assetStatus', width:50, editable:false, edittype:'select', editoptions: {value:{'Available':'Available', 'Maintenance':'Maintenance', 'Problem':'Problem', 'Offline':'Offline'}},title:false},
                {name:'location', width:80, editable:false},
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
    sortname: 'itsId',
    sortorder: 'desc',
    autowidth:true,
    shrinkToFit: true,
    searchOnEnter:true,
    headertitles:true,
    cmTemplate: { title: false },
    pager: '#allStoredAssetPager',
    scrollOffset:0,
    gridComplete: function() {
        dynamicListSize("#allStoredAssets");
    }


});

    jQuery('#allStoredAssets').filterToolbar({id:'allStoredAssets', searchOnEnter:true});
        $("#allStoredAssets").jqGrid('navGrid','#allStoredAssetPager',{
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

    setTooltipsOnColumnHeader($("#allStoredAssets"), 0, "Either Physical Server, Rack, or Misc. Equipment");
    setTooltipsOnColumnHeader($("#allStoredAssets"),1,"A unique ITS Id given to the Entity");
    setTooltipsOnColumnHeader($("#allStoredAssets"),2,"Status e.g. Online, Offline, Standby, etc.");
    setTooltipsOnColumnHeader($("#allStoredAssets"),3,"The Location of the Asset in Storage");
    setTooltipsOnColumnHeader($("#allStoredAssets"),5,"Parts availability Status");
    setTooltipsOnColumnHeader($("#allStoredAssets"),6,"Primary Service Administrator for Asset");
    setTooltipsOnColumnHeader($("#allStoredAssets"),7,"Last updated of Record");
    setTooltipsOnColumnHeader($("#allStoredAssets"),7,"Serial # of the Asset");

    jQuery(window).bind('resize', function() {
        dynamicListSize('#allStoredAssets');
    }).trigger('resize');

    });

</script>
<table id="allStoredAssets"></table>
<div id="allStoredAssetPager"></div>
