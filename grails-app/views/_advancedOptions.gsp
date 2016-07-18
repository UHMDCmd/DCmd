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


<script type="text/javascript">

    /***********************************************************************************
     * Functions for HostFilter
    ***********************************************************************************/
    function clearFileField() {
        $("#uploadField").val("");
        var url="${createLink(controller:"${pageType}",action:'clearSupportFileFilter')}";
        jQuery.ajax({
            async: false,
            url: url,
            type:'POST',
            processData:false,
            contentType: false,
            success: function(data) {
                $("${gridId}").trigger("reloadGrid");
            },
            error: function () { alert('Error retrieving elog info'); }
        });
    }

    function addFileFilter() {
        var oData = new FormData(document.forms.namedItem("fileinfo"));

        var url="${createLink(controller:"${pageType}",action:'saveSupportFileFilter')}";
        var fileData;
        jQuery.ajax({
            async: false,
            url: url,
            type:'POST',
            data: oData,
            processData:false,
            contentType: false,
            success: function(data) {
//                console.log(data);
            },
            error: function () { alert('Error retrieving elog info'); }
        });
        $("${gridId}").trigger("reloadGrid");
    }



    function importExcel() {
        //document.getElementById("loadingModal").style.display="inline";

        $("#loadingModal").css('display',"inline");
        var oData = new FormData(document.forms.namedItem("excelfile"));

        var url="${createLink(controller:"${pageType}",action:"${importAction}")}";
        var fileData;
        jQuery.ajax({
            async: true,
            url: url,
            type:'POST',
            data: oData,
            processData:false,
            contentType: false,
            success: function(data) {
                $("#loadingModal").css('display', 'none');

                $("#importReportDialog").empty();
                var statusText = "<h2><center>Import Excel - " + data.status + "</center></h2><br>";
                $("#importReportDialog").append(statusText);

                var warningAccordion;
                if(data.warnings != '') {
                    warningAccordion = "<div id='importWarningsAccordion'><h3><a href='#'>Warnings</a></h3><div><ul>";
                    data.warnings.forEach( function(s) {
                        warningAccordion += "<li>" + s + "</li>";
                    });
                    warningAccordion += "</ul></div></div>";

                    $("#importReportDialog").append(warningAccordion);
                    $("#importWarningsAccordion").accordion({
                        collapsible:true,
                        width:100,
                        active:false,
                        autoHeight:false
                        //heightStyle:'content'
                    });
                }

                if(data.message != '' ) {
                    var detailsAccordion = "<div id='importDetailsAccordion'><h3><a href='#'>Details</a></h3><div><ul>";
                    data.message.forEach(function (s) {
                        detailsAccordion += "<li>" + s + "</li>";
                    });
                    detailsAccordion += "</ul></div></div>";
                    $("#importReportDialog").append(detailsAccordion);
                    $("#importDetailsAccordion").accordion({
                        collapsible: true,
                        width: 100,
                        active: false,
                        autoHeight: false
                        //heightStyle:'content'
                    });
                }

                $( "#importReportDialog").dialog( "open" );
                $("${gridId}").trigger("reloadGrid");
            },
            error: function () {
                alert('Error retrieving elog info');
                $("#loadingModal").css('display', 'none');
            }
        });
    }

    /***********************************************************************************
     * Functions for ElapsingFilter
     ***********************************************************************************/
    function clearElapsingFilter() {
        $("#uploadField").val("");
        var url="${createLink(controller:"${pageType}",action:'clearElapsingFilter')}";
        jQuery.ajax({
            async: false,
            url: url,
            type:'POST',
            processData:false,
            contentType: false,
            success: function(data) {
                $("${gridId}").trigger("reloadGrid");
            },
            error: function () { alert('Error retrieving elog info'); }
        });
    }

    function setElapsingFilter() {
        var oData = new FormData(document.forms.namedItem("fileinfo"));

        var url="${createLink(controller:"${pageType}",action:'setElapsingFilter')}";
        var fileData;
        jQuery.ajax({
            async: false,
            url: url,
            type:'POST',
            data: oData,
            processData:false,
            contentType: false,
            success: function(data) {
            },
            error: function () { alert('Error retrieving elog info'); }
        });
        $("${gridId}").trigger("reloadGrid");
    }

    $( "#btnFilterByHostFile" ).click(function() {
        addFileFilter();
    });

    $(document).ready(function() {

        if(${hostFilter}) {
            clearFileField();
        }

        $( "#fileFilterAccordion").accordion({
            collapsible:true,
            width:100,
            heightStyle: "content",
            active:false
        });

        // Dialog to report results of import function
        $("#importReportDialog").dialog({
            autoOpen: false,
            width: 800,
            height: 400,
            show: {
                effect: "blind",
                duration: 1000
            },
            hide: {
                effect: "blind",
                duration: 1000
            },
            open: function(){
                $("#importDetailsAccordion").accordion("refresh"); // Refresh after opening dialog so height is properly set
                $("#importWarningsAccordion").accordion("refresh");
            }
        });
    });


    /************************************************************************************
     * Functions for Export Functionality
     ************************************************************************************/
    $(document).ready(function() {

        $("#btnExportExcel").click(function(){
            document.getElementById("loadingModal").style.display="inline";
            var postdata = $("${gridId}").jqGrid('getGridParam', 'postData');
            var url="${createLink(controller:"${pageType}",action:"${exportAction}")}";
            var supports = ${support};
            jQuery.ajax({
                async:true,
                url: url,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: postdata,
                beforeSend: function() {
                },
                success: function(data) {
//                    console.log(data);
                    if(supports == true) {
                        JSONToCSVConvertor(data, "${pageType} support list", true);
                    }
                    else if(supports != true){
                        JSONToCSVConvertor(data, "${pageType} list", true);
                    }
                },
                complete: function(data) {
                    document.getElementById("loadingModal").style.display="none";
                },
                error: function () {
                    console.log('Error updating list');
                    $("#loadingModal").css('display', 'none');
                }
            });
        });
    });

</script>
<style>
.loadingModal {
    display:    none;
    position:   fixed;
    z-index:    1000;
    top:        0;
    left:       0;
    height:     100%;
    width:      100%;
    background: rgba( 255, 255, 255, .8 )
    url('http://i.stack.imgur.com/FhHRx.gif')
    50% 50%
    no-repeat;
}
body.loading {
    overflow: hidden;
}
body.loading .modal {
    display: block;
}

</style>


<style>
.theTable{
    display:table;
    white-space:nowrap;
}
.theRow{display:table-row}
.theCell{
    display:table-cell;
    padding-right:15px;
    padding-left: 15px;
    border-right: 1px solid #FFFFFF;
    border-left: 0px solid #FFFFFF;

}
.bigCell{
    width:100%; /* this will shrink other cells */
    border:0;
}​
</style>

<div id="fileFilterAccordion">
    <h3><a href="#">Advanced Options</a></h3>

    <div class="theTable">
        <div class="theRow">

%{-- Host Filter --}%
    <g:if test="${hostFilter}">
    <div class='theCell'>
    <g:form enctype="multipart/form-data" class="upload" name="fileinfo" id="fileinfo" style="padding-top:5px;">
        <center><b>Host List Filter</b></center>
        <p>Upload a <b>newline-delimited</b> list of <b>Hosts</b> to filter by:</p>
        <input name="uploadField" id="uploadField" type="file" style="color:#000000;" value="">
        <input class="ui-corner-all" id="btnFilterByHostFile" type="button" onclick="addFileFilter()" value="Apply Filter"/>
        <input class="ui-corner-all" id="btnClearFilter" type="button" onclick="clearFileField()" value="Clear"/>
    </g:form>
    </div>
    </g:if>
%{-- Export --}%
    <g:if test="${export}">
        <div class='theCell'>
            <center><b>Export</b></center>
            <p>Export <u>all pages</u> of <b>filtered</b> grid data to Excel:</p>
            <center><input class="ui-corner-all" id="btnExportExcel"  type="button" value="Export"/></center>
        </div>
    </g:if>

%{-- Import --}%
    <g:if test="${importOption}">
        <div class='theCell'>
            <g:form enctype="multipart/form-data" class="upload" name="excelfile" id="excelfile" style="padding-top:5px;">
                <center><b>Import Excel Data</b></center>
                <p><b>Import an Excel/CSV</b> file with columns matching the below Grid to update data:</p>
                <input name="excelFile" id="excelFile" type="file" style="color:#000000;" value="">
                <input class="ui-corner-all" id="btnImportExcel" type="button" onclick="importExcel()" value="Update Data"/>
            </g:form>
        </div>
    </g:if>

    %{-- Elapsing Purchases Filter --}%
        <g:if test="${elapsePurchaseFilter}">
            <div class='theCell'>
                <g:form enctype="multipart/form-data" class="upload" name="fileinfo" id="fileinfo" style="padding-top:5px;">
                    <center><b>Elapsing Purchases Filter</b></center>
                    <p>Filter by only Purchases/Contracts that are elapsing within 3 months (9 months for multi-year):</p>
                    <center><input class="ui-corner-all" onclick="setElapsingFilter()"  type="button" value="Apply Filter"/>
                        <input class="ui-corner-all" onclick="clearElapsingFilter()" type="button" value="Clear Filter"/>
                    </center>
                </g:form>
            </div>
        </g:if>
        <div class='theCell bigCell'></div>
        </div>
    </div>
</div>

<div class="importReportDialog" id="importReportDialog" title="Import Report">
    <h2 id="importStatus"></h2>
</div>

<div class="loadingModal" id="loadingModal"></div>


