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
<%@  page import="edu.hawaii.its.dcmd.inf.PowerService" %>

<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
    def powerService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.PowerService').newInstance()
%>

<!-- CSS Files -->
<link type="text/css" href="../js/InfoViz/css/base.css" rel="stylesheet" />
<link type="text/css" href="../js/InfoViz/css/Icicle.css" rel="stylesheet" />
<!-- JIT Library File -->
<script language="javascript" type="text/javascript" src="../js/InfoViz/jit.js"></script>

<script language="javascript" type="text/javascript" src="../js/InfoViz/power_tree/Icicle.js"></script>

<r:require modules='select2' />

<script type="text/javascript">
    $(document).ready(function() {

        init(${assetId});
        $( "#dialog-form" ).dialog({
            autoOpen: false,
            height: 250,
            width: 450,
            modal: true,
            cache:false
        });

        $( "#edit-strip-dialog" ).dialog({
            autoOpen: false,
            height: 450,
            width: '90%',
            modal: true,
            cache:false
        });

        $("#locationSelect").select2({
            placeholder:"Select a Location",
            maximumInputLength: 20,
            width:200,
            data: [${genService.listLocationsAsSelect()}]
        });

        var storageDialog = $("#locationDialog").dialog({
            title:"Store where?",
            autoOpen:false,
            height: 150,
            modal:true
        });

        $("#btnLocationCancel").bind('click', function(){
            $("#locationDialog").dialog("close");
        });
        $("#btnLocationSubmit").bind('click', function(){
        //    console.log($("#locationDialog"));
        //    console.log($("#locationDialog").dialog());
        //    console.log(document.getElementById("locationDialog"));
            unplugStrip(document.getElementById("locationSelect").value, document.getElementById("nodeID").value, $("#locationDialog").dialog());
            $("deleteDialog").dialog("close");
            console.log(document.getElementById("deleteDialog"));

        });

    });

    function openCreateItemsDialog(node, type){
        $( "#dialog-form" ).dialog({
            title:"Create " + type,
            width:'90%',
            height:450,
            stack:true
        });
        var dialog = document.getElementById('dialog-form');
        dialog.innerHTML = "Number of "+type+" to Create: " +

                "<input type='hidden' id='newChildCount' />" +
                "<input type='number' name='numItems' id='numItems' style='width:40px;' onchange='fillCreateItemsDialog(this);this.oldvalue=this.value;' class='text ui-widget-content ui-corner-all' />" +
                "<table id='ItemTable' class='ItemTable'></table>" +
                "<input type='button' class='ui-corner-all' id='btnCreateAllItems' value='Save' />" +
                "<input type='button' class='ui-corner-all' id='btnCancelAllItems' value='Cancel' />";

        var count = 0;
        node.eachSubnode(function(){
            count++;
        });
        document.getElementById('newChildCount').value = count;

        document.getElementById('btnCreateAllItems').onclick = function() {
            createChild(node);
            $("#dialog-form").dialog("close");
        };
        document.getElementById('btnCancelAllItems').onclick = function() { $("#dialog-form").dialog("close");};

        $("#dialog-form").dialog("open");

    }

    //  GET PSU-CREATION WORKING, MOST OF THE CODE THERE BUT MAYBE WORKING... TEST AND GET WORKING!!!

    function fillCreateItemsDialog(oldVal) {
        console.log(oldVal.value);
        console.log(oldVal.oldvalue);
        var i=1;
        var tempText = "<br>";
        var oldName = [];
        var oldCapacity = [];
        var oldNotes = [];

        var count = parseInt(document.getElementById('newChildCount').value);

        for(i=1; i<=oldVal.value; i++) {
            oldName[i] =  "";
            oldCapacity[i]=1;
            oldNotes[i] = "";
        }

        for(i=1; i<=oldVal.oldvalue; i++) {
            oldName[i] = document.getElementById('dialogName'+i).value;
            oldCapacity[i] = document.getElementById('dialogCapacity'+i).value;
            oldNotes[i] = document.getElementById('dialogNotes'+i).value;
        }

        // Start from 1 for easier labeling
        for(i=1; i<=document.getElementById('numItems').value; i++) {
            tempText = tempText +
                    "<tr class='PSUtopRow' style='margin-top:30px;'><td style='padding-top:20px; padding-bottom:10px;'><font size=60>"+i+"</font></td>" +
                    "<td style='padding-top:10px;'><label class='dialogLabel' for='dialogName' id='dialogNameLabel'>Name</label>" +
                    "<input type='text' name='dialogName' id='dialogName"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldName[i]+"' />" +
                    "</td><td style='padding-top:10px;'>" +
                    "<label class='dialogLabel' for='dialogCapacity' id='dialogCapacityLabel'>Capacity</label>" +
                    "<input type='text' name='dialogCapacity' id='dialogCapacity"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldCapacity[i]+"'/>" +
                    "</td><td style='padding-top:10px;'>" +
                    "<label class='dialogLabel' for='dialogNotes' id='dialogNotesLabel'>Notes</label>" +
                    "<input type='text' name='dialogNotes' id='dialogNotes"+i+"' class='text ui-widget-content ui-corner-all' value='"+oldNotes[i]+"'/>" +

                    "</td></tr>";

        }
        document.getElementById('ItemTable').innerHTML = tempText;
    }


    function loadEditFields(node) {
        assetId = node.id;
        nodeType = node.data.type;
        //document.getElementById('idVal').value = node.id;
        document.getElementById('dialogName').value = node.name;
        document.getElementById('dialogCapacity').value = node.data.capacity;
        document.getElementById('dialogNotes').value = node.data.notes;

        switch(nodeType) {
            case 'Bus':
                $("#dialogPSU").select2({
                    maximumInputLength: 20,
                    width:150,
                    data: [${powerService.listPSUsAsSelect()}]
                }).select2('val', node.getParents()[0].id);
                break
            case 'CDU':
                    console.log(node);
                $("#dialogRack").select2({
                    maximumInputLength: 20,
                    width:150,
                    data: [${genService.listRacksAsSelect()}]
                }).select2('val', node.data.rackId);
                $("#dialogBus").select2({
                    maximumInputLength: 20,
                    width:150,
                    data: [${powerService.listBusAsSelect()}]
                }).select2('val', node.getParents()[0].id);
                break
        }
    }



</script>

<input type='hidden' id='label1' value='PSU' />
<input type='hidden' id='label2' value='Bus' />
<input type='hidden' id='label3' value='CDU' />
<input type='hidden' id='label4' value='Strips' />

<div id="container">


    <div id="center-container">
        <div id="infovis"></div>
    </div>

    <div id="right-container">

        <div id="inner-details"></div>

    </div>

    <div id="log"></div>
</div>

<div id="dialog-form">
    <form>

    </form>
</div>

<div id="edit-dialog" title="Edit_panel">

</div>

<div id="edit-strip-dialog" title="Edit">
    <g:render template="editStrip"/>
</div>
<div id='createCDUs'>
    <g:render template="newCDUs"/>
</div>
<div id='locationDialog'>
    Where will the Asset be stored?<br>
    <input type='hidden' id='locationSelect'/>
    <input type='hidden' id='nodeID' />
    <br><br><center>
    <input type='button' id='btnLocationSubmit' value="Submit"/>
    <input type='button' id='btnLocationCancel' value="Cancel"/>
    </center>
</div>