
<script type="text/javascript">

$(document).ready(function() {


jQuery("#allRack").jqGrid({

height:'auto',
width:'1000',
caption:'Rack List',
url:'listAllRack',
            datatype: "json",
            colNames:['ITS Id', 'Location',
                'Avail. for parts', 'Serial #', 'Last Updated', 'General Notes', 'id'],
            colModel:[
                {name:'itsId', width:100, editable:false,title:false},
                {name:'location', width:120, editable:false,title:false},
                {name:'isAvailableForParts', width:120,title:false},
                {name:'serialNo', width:180, editable:false,title:false},
                {name:'lastUpdated', editable:false, width:120,title:false, search:false},
                {name:'generalNote', width:400,title:false},
                {name:'id', hidden:true}
            ],

    rowNum: 50,
    rowList: [10, 20, 50, 100, 200],

    gridview: true,
    viewrecords: true,
    sortname: 'itsId',
    sortorder: 'asc',
    autowidth:true,
    shrinkToFit: true,
    pager: '#allRackPager',
    scrollOffset:0,
    gridComplete: function() {
        dynamicListSize("#allRack");
    }
});
        jQuery('#allRack').filterToolbar({id:'allRack', searchOnEnter:true});
           $("#allRack").jqGrid('navGrid','#allRackPager',{
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

    setTooltipsOnColumnHeader($("#allRack"), 0, "A unique ITS Id given to the Entity");
    setTooltipsOnColumnHeader($("#allRack"),1,"The Physical Location of the Rack");
    setTooltipsOnColumnHeader($("#allRack"),2,"Parts availability Status");
    setTooltipsOnColumnHeader($("#allRack"),3,"Serial #");
    setTooltipsOnColumnHeader($("#allRack"),4,"Last update of This Record");



    jQuery(window).bind('resize', function() {
        dynamicListSize('#allRack');
    }).trigger('resize');

    });

</script>
<table id="allRack"></table>
<div id="allRackPager"></div>

