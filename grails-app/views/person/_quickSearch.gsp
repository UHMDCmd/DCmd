<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>

<%
    def generalService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
%>

<r:require modules='select2' />

<style>
.quickSearchOption {
    text-align:left;
    font-size:12px;
}
.resultTableCol {
    border: 1px solid #666666;
    border-top:none;
    text-align:left;
    padding-left:5px;

}
.resultTableCol a {
    color: #5D798D;
}

#searchDialog .resultTableCol a {
    color: #FFFFFF;
    margin-bottom:5px;
}

.resultTableCol br {
    margin: 5px 0;
    display:block;
}

.resultTableHeader {
    border: 1px solid #666666;
}
</style>

<script>

    $(document).ready(function() { $("#quickSearchSelect").select2({
        width:400,
        style: "text-align:'left'; font-size:10px;"
    }).select2('val', null);

});

    function doQuickSearch() {
        var newValue = document.getElementById("searchBox").value;

        if(newValue != "") {
            jQuery.ajax({
                async:false,
                url: '../person/doAllQuickSearch',
                datatype:'json',
                contentType: 'application/json; charset=utf-8',
                data:{searchString:newValue},
                success: function(data) {
                    document.getElementById("resultsHosts").innerHTML = data.hostList;
                    document.getElementById("resultsAssets").innerHTML = data.assetList;
                    document.getElementById("resultsApps").innerHTML = data.appList;
                    document.getElementById("resultsServices").innerHTML = data.serviceList;
                    document.getElementById("resultsStaff").innerHTML = data.staffList;
                },
                error: function () { console.log('Error performing search'); }
            });
        }
    }

</script>

%{--        ${generalService.quickSearchSelect()}  <br><br> --}%

Quick Search - Search for anything (substrings welcomed)<br>
<input type="text" name="searchBox" id="searchBox" onchange="doQuickSearch()" />
<input class="ui-corner-all" id="btnSearch" type="button" value="Search"/>

<table>
    <th class='resultTableHeader' width=20%>Hosts</th><th class='resultTableHeader' width=20%>Physical Servers</th><th class='resultTableHeader' width=20%>Applications</th><th class='resultTableHeader' width=20%>Services</th><th class='resultTableHeader' width=20%>Staff</th>
    <tr><td class='resultTableCol' id='resultsHosts'></td><td class='resultTableCol' id='resultsAssets'></td><td class='resultTableCol' id='resultsApps'></td><td class='resultTableCol' id='resultsServices'></td><td class='resultTableCol' id='resultsStaff'></td></tr>
</table>