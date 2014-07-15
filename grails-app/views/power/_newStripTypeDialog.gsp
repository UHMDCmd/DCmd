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

<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
%>
<r:require modules='select2' />

<script type="text/javascript" >

    $(document).ready(function() {
        $( "#stripType-dialog" ).dialog({
            title:"Create Strip Type",
            width:'50%',
            height:300,
            stack:true,
            autoOpen: false,
            modal:true,
            cache:false
        });
    });

    $("#btnAddConnectorRow").bind('click', function() {
        var numRows = document.getElementById("numConnectorRows").value;
        numRows++;
        document.getElementById("numConnectorRows").value = numRows;

        if(numRows > 2) {
            var removeBtn = document.getElementById("removeBtn" + (numRows-1));
            removeBtn.style.visibility = 'hidden';
        }

        var parentDiv = document.getElementById("connectorsDiv");
        var tr = document.createElement("TR");
        tr.className = 'dialog-tr';
        tr.id = 'connectorTR' + numRows;
        parentDiv.appendChild(tr);
        var td1 = document.createElement("TD");
        tr.appendChild(td1);
        var connectorType = document.createElement("input");
        connectorType.type = 'hidden';
        connectorType.id = "connectorType" + numRows;
        td1.appendChild(connectorType);
        var td2 = document.createElement("TD");
        tr.appendChild(td2);
        var numConnectors = document.createElement("input");
        numConnectors.type = 'number';
        numConnectors.id = "connectorNum" + numRows;
        numConnectors.className ='ui-widget-content ui-corner-all';
        numConnectors.style.width = "40px";
        numConnectors.style.marginTop="7px";
        numConnectors.style.marginBottom="7px";
        td2.appendChild(numConnectors);
        var td3 = document.createElement("TD");
        tr.appendChild(td3);

        var removeRow = document.createElement("INPUT");
        removeRow.type = 'button';
        removeRow.value = "-";
        removeRow.id = 'removeBtn' + numRows;
        removeRow.onclick = function(){removeConnectorRow(numRows)};

        td3.appendChild(removeRow);

        $("#connectorType" + numRows).select2({
            placeholder:'Please Select...',
            maximumInputLength: 20,
            width:150,
            data: [${genService.listConnectorsAsSelect()}]
        });

    });


    function removeConnectorRow(numRows) {
        var theDiv = document.getElementById("connectorsDiv");
        theDiv.removeChild(theDiv.childNodes[numRows]);
        if(numRows > 2) {
            document.getElementById("removeBtn" + (numRows-1)).style.visibility='visible';
        }
        document.getElementById("numConnectorRows").value = numRows-1;

        console.log(document.getElementById("connectorsDiv").children[numRows]);

    }



    function loadPSSelect2(newId, assetId) {
        var params = new Object();
        if(assetId != null) params.assetId = assetId;
        var selectName = document.getElementById('selectObjectName').value;
//        var selectName = "${selectName}";
        console.log(selectName);
        $.ajax({
            async: false,
            url: 'listStripTypesAsSelect',
            dataType: 'json',
            data:$.param(params),
            contentType: 'application/json; charset=utf-8',

            success: function(data) {
                console.log(data);
                if(data.id) newId = data.id;
                $("#"+selectName).select2({
                    maximumInputLength: 20,
                    width:150,
                    cache:false,
                    data: data.theList
                }).select2('val', newId);
            },
            error: function(){
                alert('An error occurred, the note was not saved.');
            }
        });
    }


    $("#btnCreateStripType").bind('click', function() {

        var params = new Object();
        params.connectorTypes = new Array();
        params.connectorQtys = new Array();
        params.typeName = document.getElementById('dialogTypeName').value;
        params.numConnectors = document.getElementById('numConnectorRows').value;

        var i=1;
        for(i=1; i<=params.numConnectors; i++) {
            params.connectorTypes[i] = document.getElementById("connectorType" + i).value;
            params.connectorQtys[i] = document.getElementById("connectorNum" + i).value;
        }

        $.ajax({
            async: false,
            url: 'createStripType',
            data:$.param(params),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            success: function(data) {
//                var newId = Number(document.getElementById('selectObjectName').value);
                loadPSSelect2(data.id, null);
                if(document.getElementById('selectObjectName').value.indexOf("dialogType") !== -1) {
                    var numStrips = document.getElementById("numStrips");
                    numStrips.oldvalue = numStrips.value;
                    fillCreateStripsDialog(numStrips);
                }
            },
            error: function(){
                alert('An error occurred, the note was not saved.');
            }
        });

        $("#stripType-dialog").dialog("close");
    });
</script>

<div id="stripType-dialog" title="Create new Type of Strip" style="visibility:hidden; text-align:left">

    <label class="dialogLabel" for="dialogTypeName">Strip Type Name</label>
    <input type="text" name="dialogTypeName" id="dialogTypeName" class="text ui-widget-content ui-corner-all" /><br>
    %{-- Add function to create a new row via a HTML DOM object.  Pushing the + button adds the new row.
        Use hidden field to pass the number of columns to pass to controller.
    --}%
    <input type='hidden' id='numConnectorRows' value=1 />
    <input type='hidden' id='selectObjectName' value="" />

    <table style='width:50%; margin:0;' >
        <tbody id='connectorsDiv'>

        </tbody>
    </table>

    <input class="ui-corner-all" id="btnAddConnectorRow" type="button" value="+" style="width:22px; height:25px; padding-bottom:6px; margin-top:3px; background: -moz-linear-gradient(center bottom , #CCCCCC 0%, #EEEEEE 60%) repeat scroll 0 0 #CCCCCC; vertical-align: middle;"/>

    <div style="margin-top:5px; text-align:center;">
        <input class="ui-corner-all" id="btnCreateStripType" type="button" value="Create Strip Type"/>
    </div>

</div>