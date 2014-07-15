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

<%@  page import="edu.hawaii.its.dcmd.inf.PowerService" %>
<%
    def powerService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.PowerService').newInstance()
%>
<r:require modules='select2' />


<script type="text/javascript">



    $(document).ready(function() {

        $( "#powerPanelAccordion").accordion({
            collapsible:true,
            width:100,
            heightStyle: "content",
            active:true
        });

        $("#powerPanel").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
                var data = {id: "${panelInstance?.id}", text: "${panelInstance?.toString()}"};
                callback(data);
            },
            data: [${powerService.listPanelsAsSelect()}]
        }).select2('val', '0');

        $( "#panel-dialog-form" ).dialog({
        autoOpen: false,
        height: 250,
        width: 450,
        modal: true
        });

        // Button to open dialog to create new Power Panel
        $("#btnCreatePowerPanel").bind('click', function() {
        $("#panel-dialog-form").dialog("open");
        //document.getElementById("dialogPanel").value = document.getElementById('itsId').value;
        //  console.log(document.getElementById('itsId').value);
        });

        // Button within the Dialog to create the new Power Panel
        $("#btnCreatePanelDialog").bind('click', function() {
        var panelName = document.getElementById('dialogPanelName');
        var panelCap = document.getElementById('dialogPanelCapacity');
        var panelNotes = document.getElementById('dialogPanelNotes');


        var params = {panelName: panelName.value, panelCap: panelCap.value, panelNotes: panelNotes.value};
                  /*
        $.ajax({
        async: false,
        url: '../physicalServer/editHosts?assetId',
                        data:$.param(params),
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function(data) {
                            $("#globalZone").select2({
                                placeholder: 'Please Select...',
                                maximumInputLength: 20,
                                width:150,
                                initSelection: function(element, callback) {
                                    var selectData = {id: data.id, text: data.text};
                                    callback(selectData);
                                },
                                data: []
                            }).select2('val', '0');
                        },
                        error: function(){
                            alert('An error occurred, the note was not saved.');
                        }
                    });

                   $("#host-dialog-form").dialog("close");
                   */
                });
    });
</script>
<div id="powerPanelAccordion">
    <h3><a href="#">Power Panel</a></h3>

    <table>
<tr id="powerPanelDiv">
    <td valign="top" class="name" id='panelNameLabel'>PowerPanel Name</td>
    <td valign="top" class="value">
        <input type="hidden" name="powerPanel" id="powerPanel" />
        <input class="ui-corner-all" id="btnCreatePowerPanel" type="button" value="+" style="width:22px; height:25px; padding-bottom:6px; margin-top:3px; background: -moz-linear-gradient(center bottom , #CCCCCC 0%, #EEEEEE 60%) repeat scroll 0 0 #CCCCCC; vertical-align: middle;"/>
    </td>
</tr>
<tr>
    <td valign="top" class="name" id='panelCapacityLabel'>PowerPanel Capacity</td>
    <td valign="top" class="value">
        <input type="text" name="panelCapacity" id="panelCapacity" class="text ui-widget-content ui-corner-all" /><br>
    </td>
</tr>
<tr>
    <td valign="top" class="name" id='panelNotesLabel'>PowerPanel Notes</td>
    <td valign="top" class="value">
        <input type="text" name="panelNotes" id="panelNotes" class="text ui-widget-content ui-corner-all" /><br>
    </td>
</tr>
</table>
</div>

<div id="panel-dialog-form" title="Create new PowerPanel">
    <form>
        <label class="dialogLabel" for="dialogPanelName">PowerPanel Name</label>
        <input type="text" name="dialogPanelName" id="dialogPanelName" class="text ui-widget-content ui-corner-all" /><br>
        <label class="dialogLabel" for="dialogPanelCapacity">Total Capacity</label>
        <input type="text" name="dialogPanelCapacity" id="dialogPanelCapacity" class="text ui-widget-content ui-corner-all" /><br>
        <label class="dialogLabel" for="dialogPanelNotes">PowerPanel Notes</label>
        <input type="text" name="dialogPanelNotes" id="dialogPanelNotes" class="text ui-widget-content ui-corner-all" /><br>
        <div style="margin-top:5px">
            <input class="ui-corner-all" id="btnCreatePanelDialog" type="button" value="Create PowerPanel"/>
        </div>
    </form>
</div>
