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

<script type="text/javascript">

function openCreateStripsDialog(node){
    $( "#create-strips-dialog" ).dialog({
        title:"Create Power Strips",
        width:'90%',
        height:450,
        stack:true
    });
    var dialog = document.getElementById('create-strips-dialog');

    dialog.innerHTML = "Number of Power Strips to Create: " +

    "<input type='hidden' id='childCount' />" +
    "<input type='number' name='numStrips' id='numStrips' style='width:40px;' onchange='fillCreateStripsDialog(this);this.oldvalue=this.value;' class='text ui-widget-content ui-corner-all' />" +
    "<table id='PSTable' class='PSTable'></table>" +
    "<input type='button' class='ui-corner-all' id='btnCreateAllStrips' value='Save' />" +
    "<input type='button' class='ui-corner-all' id='btnCancelAllStrips' value='Cancel' />";


    var count = 0;
    node.eachSubnode(function(){
        count++;
    });
    document.getElementById('childCount').value = count;
/*
    var btnSave = document.createElement("INPUT");
    btnSave.typeName='button';
    btnSave.className='ui-corner-all';
    btnSave.value = 'Create';
    btnSave.onclick = function() {createChild(node);};
    dialog.appendChild(btnSave);
*/
    document.getElementById('btnCreateAllStrips').onclick = function() {
        createChild(node);
        $("#create-strips-dialog").dialog("close");
    };
    document.getElementById('btnCancelAllStrips').onclick = function() { $("#create-strips-dialog").dialog("close");};

            $("#create-strips-dialog").dialog("open");

}

function fillCreateStripsDialog(oldVal) {
    console.log(oldVal.value);
    console.log(oldVal.oldvalue);
    var i=1;
    var tempText = "<br>";
    var baseName = "PS";
    var rackList = [];
    var typeList = [];
    var storageList = [];
    var oldName = [];
    var oldCapacity = [];
    var oldIP = [];
    var oldRack = [];
    var oldType = [];
    var oldNotes = [];

    $.ajax({
        async: false,
        url: '../rack/listRacksAsSelect2',
        dataType: 'json',
        type:'GET',
        success: function(data) {
            rackList = data.result;
        }
    });

    $.ajax({
        async: false,
        url: 'listStripTypesAsSelect2',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',

        success: function(data) {
            typeList = data.result;
        }
    });

    $.ajax({
        async: false,
        url: 'listStoredStripsAsSelect2',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',

        success: function(data) {
            storageList = data.result;
        }
    });

    var count = parseInt(document.getElementById('childCount').value);

    for(i=1; i<=oldVal.value; i++) {
        oldName[i] =  baseName+(count+i);
        oldCapacity[i]=1;
        oldIP[i] = "";
        oldRack[i] = 0;
        oldType[i] = "";
        oldNotes[i] = "";
    }

    for(i=1; i<=oldVal.oldvalue; i++) {
        oldName[i] = document.getElementById('dialogName'+i).value;
        oldCapacity[i] = document.getElementById('dialogCapacity'+i).value;
        oldIP[i] = document.getElementById('dialogIP'+i).value;
        oldRack[i] = Number(document.getElementById('dialogRack'+i).value);
        oldType[i] = document.getElementById('dialogType'+i).value;
        oldNotes[i] = document.getElementById('dialogNotes'+i).value;
    }

    console.log(oldRack);


    // Start from 1 for easier labeling
    for(i=1; i<=document.getElementById('numStrips').value; i++) {
        tempText = tempText +
            "<tr class='PStopRow'><td rowspan=2 style='padding-top:30px;'><font size=60>"+i+"</font></td>" +
"<td></td><td>New Strip:   <input type='radio' id='new"+i+"' name='storage"+i+"' value='new' checked /><br>From Storage: <input type='radio' id='storage"+i+"' name='storage"+i+"' value='storage' /></td><td colspan=3 style='text-align:left'>" +
"<label class='dialogLabel' for='dialogName' id='dialogNameLabel'>Name</label>" +
"<input type='text' name='dialogName' id='dialogName"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldName[i]+"' />" +
"<span id='nameSelectSpan"+i+"' style='visibility:hidden; margin-left:-15px;'><input type='hidden' name='nameSelect' id='nameSelect"+i+"' /></span>" +
"</td></tr><tr class='PSsecondRow'><td>" +
"<label class='dialogLabel' for='dialogCapacity' id='dialogCapacityLabel'>Capacity</label>" +
"<input type='text' name='dialogCapacity' id='dialogCapacity"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldCapacity[i]+"'/>" +
"</td><td>" +
"<label class='dialogLabel' >IP Address</label>" +
"<input type='text' name='dialogIP' id='dialogIP"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldIP[i]+"'/>" +
"</td><td>" +
"<label class='dialogLabel' >Rack</label>" +
"<input type='hidden' name='dialogRack' id='dialogRack"+i+"' class='text ui-widget-content ui-corner-all'/>" +
"</td><td>" +
"<label class='dialogLabel' >StripType</label>" +
"<input type='hidden' name='dialogType' id='dialogType"+i+"' class='text ui-widget-content ui-corner-all'/>" +
"<input class='ui-corner-all' id='btnCreateStripType"+i+"' type='button' value='+' style='width:22px; height:25px; padding-bottom:6px; margin-top:3px; background: -moz-linear-gradient(center bottom , #CCCCCC 0%, #EEEEEE 60%) repeat scroll 0 0 #CCCCCC; vertical-align: middle;'/>" +
"</td><td>" +
"<label class='dialogLabel' for='dialogNotes' id='dialogNotesLabel'>Notes</label>" +
"<input type='text' name='dialogNotes' id='dialogNotes"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldNotes[i]+"'/>" +
//"<input class='ui-corner-all' id='btnSave' type='button' onclick='createPanel(node)' value='Create'/>" +
"</td></tr>";

    }
    document.getElementById('PSTable').innerHTML = tempText;
    for(i=1; i<=document.getElementById('numStrips').value; i++) {
        $("#dialogRack"+i).select2({
            maximumInputLength: 20,
            width:150,
            data: rackList
        }).select2('val', oldRack[i]);

        $("#dialogType"+i).select2({
            maximumInputLength: 20,
            width:150,
            data: typeList,
            placeholder:'Please Select'
            //initSelection: function(element, callback) {
            //    var data = {id: 0, text: "Please Select"};
            //    callback(data);
            //}
        }).select2('val', oldType[i]);

        $("#nameSelect"+i).select2({
            maximumInputLength: 20,
            width:150,
            data: storageList,
            placeholder:'Please Select'
        });
        document.getElementById("nameSelect"+i).style.visibility = 'hidden';

        $("#nameSelect"+i).select2({
            maximumInputLength: 20,
            width:150,
            data: storageList,
            placeholder:'Please Select'
        });

        (function() {
            var radioIndex = i;
            $("#storage"+radioIndex).bind('click', function() {
                document.getElementById("nameSelectSpan"+radioIndex).style.visibility = 'visible';
                document.getElementById("dialogName"+radioIndex).style.visibility = 'hidden';
                document.getElementById("dialogName"+radioIndex).style.width=0;
            });
            $("#new"+radioIndex).bind('click', function() {
                document.getElementById("nameSelectSpan"+radioIndex).style.visibility = 'hidden';
                document.getElementById("dialogName"+radioIndex).style.visibility = 'visible';
                document.getElementById("dialogName"+radioIndex).style.width='138px';
            });
        })();


        (function() {
            var index = i;
            $("#btnCreateStripType"+index).bind('click', function(){
                openNewTypeDialog("dialogType"+Number(index));});

            $("#nameSelect"+i).on("change", function(e){
                fillStoredStripData(e, index);
            });

        })();
    }
}

function fillStoredStripData(id, index) {
    $.ajax({
        async: false,
        url: 'getStripData',
        dataType: 'json',
        data: {id:id.val},
        contentType: 'application/json; charset=utf-8',
        success: function(data) {
            document.getElementById("dialogCapacity"+index).value = data.capacity;
            document.getElementById("dialogIP"+index).value = data.IP;
            document.getElementById("dialogNotes"+index).value = data.notes;
            $("#dialogType"+index).select2('val', data.stripType);
        }
    });
}

function openNewTypeDialog(name) {
    document.getElementById("stripType-dialog").style.visibility = 'visible';
    //  console.log(document.getElementById('itsId').value);
    $( "#stripType-dialog" ).dialog("open");
    $( "#stripType-dialog" ).focus();


    document.getElementById("connectorsDiv").innerHTML =
            "<tr style='margin-bottom:5px;'><td class='ui-widget' style='width:150px;'>" +
                    "Connector Type</td><td style='width:60px;'>Qty</td><td style='width:50px;'></td></tr>" +
                    "<tr class='dialog-tr'><td><input type='hidden' id='connectorType1'/></td>" +
                    "<td><input type='number' id='connectorNum1' class='text ui-widget-content ui-corner-all' style='width:40px; margin-left:0;'/>" +
                    "</td></tr>";

    document.getElementById("numConnectorRows").value = 1;
    document.getElementById("selectObjectName").value = name;

    $("#connectorType1").select2({
        placeholder:'Please Select...',
        maximumInputLength: 20,
        width:150,
        data: [${genService.listConnectorsAsSelect()}]
    });
}

</script>

<div id="create-strips-dialog" title="Create New Power Strips">
    <form>

    </form>
</div>

<g:render template="newStripTypeDialog"/>