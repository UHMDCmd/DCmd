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

    });


    /************************************************************************************
     * Functions for Export Functionality
     ************************************************************************************/
    $(document).ready(function() {

        $("#btnExportExcel").click(function(){
            document.getElementById("loadingModal").style.display="inline";
            var postdata = $("${gridId}").jqGrid('getGridParam', 'postData');
            var url="${createLink(controller:"${pageType}",action:"${exportAction}")}";
            jQuery.ajax({
                async:false,
                url: url,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: postdata,
                beforeSend: function() {
                },
                success: function(data) {
//                    console.log(data);
                    JSONToCSVConvertor(data, "${pageType} list", true);
                },
                complete: function(data) {
                    document.getElementById("loadingModal").style.display="none";
                },
                error: function () { console.log('Error updating list'); }
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
            <p>Export all pages of <b>filtered</b> grid data to Excel:</p>
            <center><input class="ui-corner-all" id="btnExportExcel"  type="button" value="Export"/></center>
        </div>
    </g:if>
        <div class='theCell bigCell'></div>
        </div>
    </div>
</div>

<div class="loadingModal" id="loadingModal"></div>
