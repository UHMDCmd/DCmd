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

        $( "#powerSourceAccordion").accordion({
            collapsible:true,
            width:100,
            heightStyle: "content",
            active:true
        });

        $("#powerSource").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
                var data = {id: "${sourceInstance?.id}", text: "${sourceInstance?.toString()}"};
                callback(data);
            },
            data: [${powerService.listSourcesAsSelect()}]
        }).select2('val', '0');

        $( "#source-dialog-form" ).dialog({
        autoOpen: false,
        height: 250,
        width: 450,
        modal: true
        });

        // Button to open dialog to create new Power Source
        $("#btnCreatePowerSource").bind('click', function() {
        $("#source-dialog-form").dialog("open");
        //document.getElementById("dialogSource").value = document.getElementById('itsId').value;
        //  console.log(document.getElementById('itsId').value);
        });

        // Button within the Dialog to create the new Power Source
        $("#btnCreateSourceDialog").bind('click', function() {
        var sourceName = document.getElementById('dialogSourceName');
        var sourceCap = document.getElementById('dialogSourceCapacity');
        var sourceNotes = document.getElementById('dialogSourceNotes');


        var params = {sourceName: sourceName.value, sourceCap: sourceCap.value, sourceNotes: sourceNotes.value};
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
<div id="powerSourceAccordion">
    <h3><a href="#">Power Source</a></h3>

    <table>
<tr id="powerSourceDiv">
    <td valign="top" class="name" id='sourceNameLabel'>PowerSource Name</td>
    <td valign="top" class="value">
        <input type="hidden" name="powerSource" id="powerSource" />
        <input class="ui-corner-all" id="btnCreatePowerSource" type="button" value="+" style="width:22px; height:25px; padding-bottom:6px; margin-top:3px; background: -moz-linear-gradient(center bottom , #CCCCCC 0%, #EEEEEE 60%) repeat scroll 0 0 #CCCCCC; vertical-align: middle;"/>
    </td>
</tr>
<tr>
    <td valign="top" class="name" id='sourceCapacityLabel'>PowerSource Capacity</td>
    <td valign="top" class="value">
        <input type="text" name="sourceCapacity" id="sourceCapacity" class="text ui-widget-content ui-corner-all" /><br>
    </td>
</tr>
<tr>
    <td valign="top" class="name" id='sourceNotesLabel'>PowerSource Notes</td>
    <td valign="top" class="value">
        <input type="text" name="sourceNotes" id="sourceNotes" class="text ui-widget-content ui-corner-all" /><br>
    </td>
</tr>
</table>
</div>

<div id="source-dialog-form" title="Create new PowerSource">
    <form>
        <label class="dialogLabel" for="dialogSourceName">PowerSource Name</label>
        <input type="text" name="dialogSourceName" id="dialogSourceName" class="text ui-widget-content ui-corner-all" /><br>
        <label class="dialogLabel" for="dialogSourceCapacity">Total Capacity</label>
        <input type="text" name="dialogSourceCapacity" id="dialogSourceCapacity" class="text ui-widget-content ui-corner-all" /><br>
        <label class="dialogLabel" for="dialogSourceNotes">PowerSource Notes</label>
        <input type="text" name="dialogSourceNotes" id="dialogSourceNotes" class="text ui-widget-content ui-corner-all" /><br>
        <div style="margin-top:5px">
            <input class="ui-corner-all" id="btnCreateSourceDialog" type="button" value="Create PowerSource"/>
        </div>
    </form>
</div>
