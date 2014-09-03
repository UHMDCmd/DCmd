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

    $("#btnSubmitStripChanges").click(function(){
        var params = new Object();
        params.itsId = document.getElementById('psName').value;
        params.capacity = document.getElementById('psCapacity').value;
        params.IP = document.getElementById('psIP').value;
        params.rack = document.getElementById('psRack').value;
        params.type = document.getElementById('psType').value;
        params.id = assetId;
        jQuery.ajax({
            async:false,
            url: 'saveStripAttributes',
            datatype:'json',
            contentType: 'application/json; charset=utf-8',
            data:$.param(params),
            success: function(data) {
                $("#edit-strip-dialog").dialog("close");
                reloadTree();
            },
            error: function () { console.log('Error saving PowerStrip.'); }
        });

    });

    $("#btnCancelStripChanges").click(function() {
        $("#edit-strip-dialog").dialog("close");
    });


    function initialLoadPSSelect() {
        var params = new Object();
        if(assetId != null) params.assetId = assetId;

        $.ajax({
            async: false,
            url: 'listStripTypesAsSelect',
            dataType: 'json',
            data:$.param(params),
            contentType: 'application/json; charset=utf-8',

            success: function(data) {
                $("#psType").select2({
                    maximumInputLength: 20,
                    width:150,
                    cache:false,
                    data: data.theList
                }).select2('val', data.id);
            },
            error: function(){
                alert('An error occurred, the note was not saved.');
            }
        });
    }

    function loadStripFields(node) {
        assetId = node.id;
        //document.getElementById('idVal').value = node.id;
        document.getElementById('psName').value = node.name;
        document.getElementById('psCapacity').value = node.data.capacity;
        document.getElementById('psIP').value = node.data.ip;

        $("#psRack").select2({
            maximumInputLength: 20,
            width:150,
            //initSelection: function(element, callback) {
                //var data = {id: node.data.rackId, text:node.data.rackName};
              //  callback(data);
            //},
            data: [${genService.listRacksAsSelect()}]
        }).select2('val', node.data.rackId);

        /*
        $("#psType").select2({
            maximumInputLength: 20,
            width:150,

        }).select2('val', node.data.typeId);
        */
        initialLoadPSSelect(null, assetId);

        loadDeviceGrid(node.id);
    }

</script>

<div class="dialog">

    <input type='hidden' id='idVal' />
    <table class="floatTables" id="floatTable1">
        <tr>
            <td valign="center" class="name">
            <label for='psName' code="ps.name.label" default="Name">Name</label></td>
            <td valign="center" class="value">
            <input type='text' id='psName' class='text ui-widget-content ui-corner-all'/>
            </td>
        </tr>
        <tr>
            <td valign="center" class="name">
                <label for='psCapacity' code="ps.name.label">Capacity</label></td>
            <td valign="center" class="value">
                <input type='text' id='psCapacity' class='text ui-widget-content ui-corner-all'/>
            </td>
        </tr>
        <tr>
            <td valign="center" class="name">
                <label for='psIP' code="ps.name.label">IP Address</label></td>
            <td valign="center" class="value">
                <input type='text' id='psIP' class='text ui-widget-content ui-corner-all'/>
            </td>
        </tr>
        <tr>
            <td valign="center" class="name">
                <label for='psRack' code="ps.name.label">Rack</label></td>
            <td valign="center" class="value">
                <input type='hidden' id='psRack' class='text ui-widget-content ui-corner-all'/>
            </td>
        </tr>

        <tr id="typeDiv">
            <td valign="top" class="name" id='typeLabel'>Strip Type</td>
            <td valign="top" class="value">
                <input type="hidden" name="psType" id="psType" />
                <g:render template="newStripTypeButton" model="[selectName:'psType']"/>
            </td>
        </tr>
    </table>
    <g:render template="newStripTypeDialog" />

</div>

<g:render template="stripTabs" model="[assetId:assetId]" />


