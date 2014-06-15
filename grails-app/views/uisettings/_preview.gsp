
<script type="text/javascript">

    $(document).ready(function() {


        jQuery("#gridPreview").jqGrid({
            url: '../js/preview/books.xml',
            datatype: "xml",
            colNames:["Author","Title", "Price", "Published Date"],
            colModel:[
                {name:"Author",index:"Author", width:120, xmlmap:"ItemAttributes>Author"},
                {name:"Title",index:"Titltes>Title"},
                {name:"Price",index:"Manufacturer", width:100, align:"right",xmlmap:"ItemAttributes>Price", sorttype:"float"},
                {name:"DatePub",index:"ProductGroup", width:130,xmlmap:"ItemAttributes>DatePub",sorttype:"date"}
            ],
            height:250,
            rowNum:10,
            rowList:[10,20,30],
            viewrecords: true,
            loadonce: true,
            xmlReader: {
                root : "Items",
                row: "Item",
                repeatitems: false,
                id: "ASIN"
            },
            caption: "Grid P"
        });

        jQuery('#gridPreview').filterToolbar({id:'gridPreview', searchOnEnter:true});
        $("#gridPreview").jqGrid('navGrid','#allAssetPager',{
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


        jQuery(window).bind('resize', function() {
            dynamicListSize('#gridPreview');
        }).trigger('resize');

    });

</script>
<div id="preview" style="position:absolute; display: block" align="center">
<table>
    <td>
    <table id="gridPreview"></table>
    </td>
    <td>
<div id="tabs">
<div id="tabs-notes">
    <ul>

        <li>
            <a href="#notes-general"><g:message code="notes-general.label" default="General" /></a>
        </li>
        <li>
            <a href="#notes-change"><g:message code="notes-change.label" default="Change" /></a>
        </li>
        <li>
            <a href="#notes-planning"><g:message code="notes-planning.label" default="Planning" /></a>
        </li>
    </ul>
    <div id="notes-general">
        <g:render template='previewtabs'/>
    </div>
    <div id="notes-change">
        <g:render template='previewtabs'/>
    </div>
    <div id="notes-planning">
        <g:render template='previewtabs'/>
    </div>
</div>
</div>
</div>
</td>
</table>
</div>
