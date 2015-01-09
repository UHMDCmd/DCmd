function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {
    //If JSONData is not an object then JSON.parse will parse the JSON string in an Object
    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;

    var CSV = '';
    //Set Report title in first row or line

    CSV += ReportTitle + '\r\n\n';

    //This condition will generate the Label/Header
    if (ShowLabel) {
        var row = "";

        //This loop will extract the label from 1st index of on array
        for (var index in arrData[0]) {

            //Now convert each value to string and comma-seprated
            row += index + ',';
        }

        row = row.slice(0, -1);

        //append Label row with line break
        CSV += row + '\r\n';
    }

    //1st loop is to extract each row
    for (var i = 0; i < arrData.length; i++) {
        var row = "";

        //2nd loop will extract each column and convert it in string comma-seprated
        for (var index in arrData[i]) {
            row += '"' + arrData[i][index] + '",';
        }

        row.slice(0, row.length - 1);

        //add a line break after each row
        CSV += row + '\r\n';
    }

    if (CSV == '') {
        alert("Invalid data");
        return;
    }

    //Generate a file name
//    var fileName = "MyReport_";
    //this will remove the blank-spaces from the title and replace it with an underscore
    var fileName = ReportTitle.replace(/ /g, "_");

    //Initialize file format you want csv or xls
    var uri = 'data:text/csv;charset=utf-8,' + escape(CSV);

    // Now the little tricky part.
    // you can use either>> window.open(uri);
    // but this will not work in some browsers
    // or you will not get the correct file extension

    //this trick will generate a temp <a /> tag
    var link = document.createElement("a");
    link.href = uri;

    //set the visibility hidden so it will not effect on your web-layout
    link.style = "visibility:hidden";
    link.download = fileName + ".csv";

    //this part will append the anchor tag and remove it after automatic click
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}


$(function() {
	$( "#tabs" ).tabs();
	var ownerTypes = [
		"Leased",
		"Perpetual"
	];
	$( "#ownerTypes" ).autocomplete({
		source: ownerTypes
	});
});

$(function() {
	$( "#tabs-notes" ).tabs();
});	

$('#addNoteButton').bind('click', function() {
	addNote();
});
/*
function checkHostType(hostType, serverType) {
    if(hostType != null && serverType != null) {
        if(hostType == serverType) {
            return true
        }
        else {
            return confirm("Host Type not compatible with Server Type, change Host to match?");
        }
    }
}
*/

function dynamicGridSize(gridName) {
    // Get width of parent container
//            var width = jQuery('#gridContainer').attr('clientWidth');
    var width = document.getElementById("tabs").clientWidth;

//            var width = $("#gridStyle").width();
    if (width == null || width < 1){
        // For IE, revert to offsetWidth if necessary
        width = document.getElementById("tabs").offsetWidth;
    }
    width = width - 40; // Fudge factor to prevent horizontal scrollbars
    if (width > 0 &&
        // Only resize if new width exceeds a minimal threshold
        // Fixes IE issue with in-place resizing when mousing-over frame bars
        Math.abs(width - jQuery(gridName).width()) > 5)
    {
        jQuery(gridName).setGridWidth(width);
    }
}

function dynamicListSize(gridName) {
    // Get width of parent container
//            var width = jQuery('#gridContainer').attr('clientWidth');
    var width = document.getElementById("outerElement").clientWidth;

//            var width = $("#gridStyle").width();
    if (width == null || width < 1){
        // For IE, revert to offsetWidth if necessary
        width = document.getElementById("outerElement").offsetWidth;
    }
//    width = width - 40; // Fudge factor to prevent horizontal scrollbars

    if (width > 0 &&
        // Only resize if new width exceeds a minimal threshold
        // Fixes IE issue with in-place resizing when mousing-over frame bars
        Math.abs(width - jQuery(gridName).width()) > 5)
    {
        jQuery(gridName).setGridWidth(width);
    }

}

function resizeColumnHeader() {
    var rowHight, resizeSpanHeight,
    // get the header row which contains
        headerRow = $(this).closest("div.ui-jqgrid-view")
            .find("table.ui-jqgrid-htable>thead>tr.ui-jqgrid-labels");

    // reset column height
    headerRow.find("span.ui-jqgrid-resize").each(function () {
        this.style.height = '';
    });

    // increase the height of the resizing span
    resizeSpanHeight = 'height: ' + headerRow.height() + 'px !important; cursor: col-resize;';
    headerRow.find("span.ui-jqgrid-resize").each(function () {
        this.style.cssText = resizeSpanHeight;
    });

    // set position of the dive with the column header text to the middle
    rowHight = headerRow.height();
    headerRow.find("div.ui-jqgrid-sortable").each(function () {
        var ts = $(this);
        ts.css('top', (rowHight - ts.outerHeight()) / 2 + 'px');
    });
}

function fixPositionsOfFrozenDivs() {
        var $rows;
        if (typeof this.grid.fbDiv !== "undefined") {
            $rows = $('>div>table.ui-jqgrid-btable>tbody>tr', this.grid.bDiv);
            $('>table.ui-jqgrid-btable>tbody>tr', this.grid.fbDiv).each(function (i) {
                var rowHight = $($rows[i]).height(), rowHightFrozen = $(this).height();
                if ($(this).hasClass("jqgrow")) {
                    $(this).height(rowHight);
                    rowHightFrozen = $(this).height();
                    if (rowHight !== rowHightFrozen) {
                        $(this).height(rowHight + (rowHight - rowHightFrozen));
                    }
                }
            });
            $(this.grid.fbDiv).height(this.grid.bDiv.clientHeight);
            $(this.grid.fbDiv).css($(this.grid.bDiv).position());
        }
        if (typeof this.grid.fhDiv !== "undefined") {
            $rows = $('>div>table.ui-jqgrid-htable>thead>tr', this.grid.hDiv);
            $('>table.ui-jqgrid-htable>thead>tr', this.grid.fhDiv).each(function (i) {
                var rowHight = $($rows[i]).height(), rowHightFrozen = $(this).height();
                $(this).height(rowHight);
                rowHightFrozen = $(this).height();
                if (rowHight !== rowHightFrozen) {
                    $(this).height(rowHight + (rowHight - rowHightFrozen));
                }
            });
            $(this.grid.fhDiv).height(this.grid.hDiv.clientHeight);
            $(this.grid.fhDiv).css($(this.grid.hDiv).position());
        }
    }

function fixGboxHeight() {
        var gviewHeight = $("#gview_" + $.jgrid.jqID(this.id)).outerHeight(),
            pagerHeight = $(this.p.pager).outerHeight();

        $("#gbox_" + $.jgrid.jqID(this.id)).height(gviewHeight + pagerHeight);
        gviewHeight = $("#gview_" + $.jgrid.jqID(this.id)).outerHeight();
        pagerHeight = $(this.p.pager).outerHeight();
        $("#gbox_" + $.jgrid.jqID(this.id)).height(gviewHeight + pagerHeight);
}

function createTopToolbar(gridName) {
    var $grid=$(gridName);
    var $gview = $grid.closest(".ui-jqgrid-view"),
        $topToolbar = $gview.find(">.ui-userdata"),
        $bdiv = $grid.closest(".ui-jqgrid-bdiv"),
        resetTopToolbarHeight = function () {
            var scrollbarHeight = 18; // some test value
            $topToolbar.find(">div").height(scrollbarHeight);
            $topToolbar.css("border-top", "0").css("height", "auto");
            scrollbarHeight = $topToolbar.height() - scrollbarHeight;
            $topToolbar.find(">div").height(scrollbarHeight);
            $topToolbar.height(scrollbarHeight);
            fixPositionsOfFrozenDivs.call($grid[0]);
        };
// insert empty div in the top toolbar and make its width
// the same as the width of the grid
    $topToolbar.css({ overflowX: "scroll", overflowY: "hidden"})
        .append($("<div>").width($grid.width()));
// set the height of the div and the height of toolbar
// based on the height of the horizontal scrollbar
    resetTopToolbarHeight();
// detect scrolling of topbar
    $topToolbar.scroll(function () {
        // synchronize the srollbar of the grid
        $bdiv.scrollLeft($(this).scrollLeft());
    });
// detect scrolling of the grid
    $bdiv.scroll(function () {
        // synchronize the srollbar of the toppbar
        $topToolbar.scrollLeft($(this).scrollLeft());
    });
// detect zoop of the page and adjust the
    $(window).on("resize", function () {
        resetTopToolbarHeight();
        fixPositionsOfFrozenDivs.call($grid[0]);
    });
}