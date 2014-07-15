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

function openCreateCDUsDialog(node){
    $( "#create-CDUs-dialog" ).dialog({
        title:"Create CDUs",
        width:'90%',
        height:450,
        stack:true
    });
    var dialog = document.getElementById('create-CDUs-dialog');

    dialog.innerHTML = "Number of CDUs to Create: " +

    "<input type='hidden' id='CDUchildCount' />" +
    "<input type='number' name='numCDUs' id='numCDUs' style='width:40px;' onchange='fillCreateCDUsDialog(this);this.oldvalue=this.value;' class='text ui-widget-content ui-corner-all' />" +
    "<table id='CDUTable' class='CDUTable'></table>" +
    "<input type='button' class='ui-corner-all' id='btnCreateAllCDUs' value='Save' />" +
    "<input type='button' class='ui-corner-all' id='btnCancelAllCDUs' value='Cancel' />";


    var count = 0;
    node.eachSubnode(function(){
        count++;
    });
    document.getElementById('CDUchildCount').value = count;
/*
    var btnSave = document.createElement("INPUT");
    btnSave.typeName='button';
    btnSave.className='ui-corner-all';
    btnSave.value = 'Create';
    btnSave.onclick = function() {createChild(node);};
    dialog.appendChild(btnSave);
*/
    document.getElementById('btnCreateAllCDUs').onclick = function() {
        createChild(node);
        $("#create-CDUs-dialog").dialog("close");
    };
    document.getElementById('btnCancelAllCDUs').onclick = function() { $("#create-CDUs-dialog").dialog("close");};

            $("#create-CDUs-dialog").dialog("open");

}

function fillCreateCDUsDialog(oldVal) {
    console.log(oldVal.value);
    console.log(oldVal.oldvalue);
    var i=1;
    var tempText = "<br>";
    var baseName = "CDU";
    var rackList = [];
//    var storageList = [];
    var oldName = [];
    var oldCapacity = [];
    var oldIP = [];
    var oldRack = [];
//    var oldType = [];
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

    var count = parseInt(document.getElementById('CDUchildCount').value);

    for(i=1; i<=oldVal.value; i++) {
        oldName[i] =  baseName+(count+i);
        oldCapacity[i]=1;
        oldIP[i] = "";
        oldRack[i] = 0;
        oldNotes[i] = "";
    }

    for(i=1; i<=oldVal.oldvalue; i++) {
        oldName[i] = document.getElementById('dialogName'+i).value;
        oldCapacity[i] = document.getElementById('dialogCapacity'+i).value;
        oldIP[i] = document.getElementById('dialogIP'+i).value;
        oldRack[i] = Number(document.getElementById('dialogRack'+i).value);
        oldNotes[i] = document.getElementById('dialogNotes'+i).value;
    }

    console.log(oldRack);


    // Start from 1 for easier labeling
    for(i=1; i<=document.getElementById('numCDUs').value; i++) {
        tempText = tempText +
            "<tr class='CDUtopRow'><td rowspan=2 style='padding-top:30px;'><font size=60>"+i+"</font></td>" +
"<td colspan=4 style='text-align:center'><label class='dialogLabel' for='dialogName' id='dialogNameLabel'>Name</label>" +
"<input type='text' name='dialogName' id='dialogName"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldName[i]+"' />" +
"</td></tr><tr class='CDUsecondRow'><td>" +
"<label class='dialogLabel' for='dialogCapacity' id='dialogCapacityLabel'>Capacity</label>" +
"<input type='text' name='dialogCapacity' id='dialogCapacity"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldCapacity[i]+"'/>" +
"</td><td>" +
"<label class='dialogLabel' >IP Address</label>" +
"<input type='text' name='dialogIP' id='dialogIP"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldIP[i]+"'/>" +
"</td><td>" +
"<label class='dialogLabel' >Rack</label>" +
"<input type='hidden' name='dialogRack' id='dialogRack"+i+"' class='text ui-widget-content ui-corner-all'/>" +
"</td><td>" +
"<label class='dialogLabel' for='dialogNotes' id='dialogNotesLabel'>Notes</label>" +
"<input type='text' name='dialogNotes' id='dialogNotes"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldNotes[i]+"'/>" +
//"<input class='ui-corner-all' id='btnSave' type='button' onclick='createPanel(node)' value='Create'/>" +
"</td></tr>";

    }
    document.getElementById('CDUTable').innerHTML = tempText;
    for(i=1; i<=document.getElementById('numCDUs').value; i++) {
        $("#dialogRack"+i).select2({
            maximumInputLength: 20,
            width:150,
            data: rackList
        }).select2('val', oldRack[i]);
    }
}

</script>

<div id="create-CDUs-dialog" title="Create New CDUs">
    <form>

    </form>
</div>