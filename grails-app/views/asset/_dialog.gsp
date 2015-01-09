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

<%@ page import="edu.hawaii.its.dcmd.inf.Host; edu.hawaii.its.dcmd.inf.Location; edu.hawaii.its.dcmd.inf.Rack" %>
<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%@  page import="edu.hawaii.its.dcmd.inf.HostService" %>
<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
    def hostService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.HostService').newInstance()
%>

<script type="text/javascript">



    function calcEndRU(newVal, elementId) {
        if(newVal.value) {
            newVal = parseInt(newVal.value) + parseInt(document.getElementById('RU_size').value)-1;
            console.log(newVal);
            document.getElementById(elementId).innerHTML = "- " + newVal.toString();
        }
        else
        document.getElementById(elementId).innerHTML = "- 0";
    }

    function calcNewSize(newVal) {
        if(newVal.value) {

            if(newVal.value == 45){
                //flash warning message
            }
            else{

            var temp = parseInt(document.getElementById('RU_begin').value)+parseInt(newVal.value)-1;
            document.getElementById('endRU').innerHTML = "- " + temp;
            var temp = parseInt(document.getElementById('RU_planned_begin').value)+parseInt(newVal.value)-1;

            document.getElementById('endPlannedRU').innerHTML = "- " + temp;
            }
        }
    }

</script>
<g:if test="${action != 'create' && assetType == 'Physical Server'}">
<script>

    $(document).ready(function() {

        $("#serverType").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
                var data = {id: "${assetInstance.serverType}", text: "${assetInstance.serverType}"};
                callback(data);
            },
            data: [${genService.listServerTypesAsSelect()}]
        }).select2('val', '0');
        $("#serverType").on("change", function(e){changeServerType(e);} );
        changeServerType("${assetInstance.serverType}");

        $("#globalZone").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
                var data = {id: "${assetInstance.getGlobalZone()?.id}", text: "${assetInstance.getGlobalZone().toString()}"};
                callback(data);
            },
            data: [${hostService.listHostsAsSelect()}]
        }).select2('val', '0');

        $("#cluster").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
                var data = {id: "${assetInstance.cluster?.id}", text: "${assetInstance.cluster.toString()}"};
                callback(data);
            },
            data: [${genService.listClustersAsSelect()}]
        }).select2('val', '0');

        $("#currentRack").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
                var data = {id: "${assetInstance?.getRackAssignmentId()}", text: "${assetInstance?.getRackAssignment()}"};
                callback(data);
            },
            data: [${genService.listRacksAsSelect()}]
        }).select2('val', '0');

        $("#plannedRack").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
                var data = {id: "${assetInstance?.getPlannedRackAssignmentId()}", text: "${assetInstance?.getPlannedRackAssignment()}"};
                callback(data);
            },
            data: [${genService.listRacksAsSelect()}]
        }).select2('val', '0');

        $( "#host-dialog-form" ).dialog({
            autoOpen: false,
            height: 300,
            width: 350,
            modal: true
        });

        $("#btnCreateGlobalHost").bind('click', function() {
            $("#host-dialog-form").dialog("open");
            document.getElementById("dialogHostname").value = document.getElementById('itsId').value;
          //  console.log(document.getElementById('itsId').value);
        });

        $("#btnCreateHostDialog").bind('click', function() {
            var hostname = document.getElementById('dialogHostname');
            var env = document.getElementById('environmentSelect');
            var status = document.getElementById('statusSelect2');
            var primarySA = document.getElementById('personSelect2');
            var hostNotes = document.getElementById('dialogNotes');
            var serverType = document.getElementById('serverType');

            var params = {hostname: hostname.value, hostEnv: env.value, status: status.value, hostSA: primarySA.value, generalNote: hostNotes.value, type:serverType.value, oper: 'add', isGlobal:true};

            $.ajax({
                async: false,
                url: '../physicalServer/editHosts?assetId=${assetInstance.id}',
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
                        data: [${hostService.listHostsAsSelect()}]
                    }).select2('val', '0');
                },
                error: function(){
                    alert('An error occurred, the note was not saved.');
                }
            });

           $("#host-dialog-form").dialog("close");
        });



    });


    function changeServerType(type) {

        if(type.val == "Solaris Global Zone" || type == "Solaris Global Zone") {
            document.getElementById("globalDiv").style.visibility = 'visible';
            document.getElementById("globalTitle").innerHTML = 'Global Zone';
            document.getElementById("clusterDiv").style.visibility = 'hidden';
        }
        else if(type.val == "Standalone" || type == "Standalone" || type.val == "VMware Standalone" || type == "VMware Standalone") {
            document.getElementById("globalDiv").style.visibility = 'visible';
            document.getElementById("globalTitle").innerHTML = 'Standalone Host';
            document.getElementById("clusterDiv").style.visibility = 'hidden';
        }
        else {
            document.getElementById("globalDiv").style.visibility = 'visible';
            document.getElementById("globalTitle").innerHTML = 'VMware OS';
            document.getElementById("clusterDiv").style.visibility = 'visible';
        }
    }




</script>
</g:if>

<div class="dialog">

<div class="show-wrapper">
    <table class="floatTables">
        <tr>
            <td valign="top" class="name"> <span style="color: #FF0000;" >*</span>
                <g:message code="asset.itsId.label" default="Its Id" /></td>


            <g:if test="${session.getValue("cloned") == true}">
                <span style="color: #ff272d;font-size: 14px">Cloned From: ${assetInstance.itsId}</span>
                <br>
                <span style="color: #ff272d; font-size: 14px" >Please Specify a Unique Name For This Entity.</span>
                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'itsId', 'errors')}">
                <g:textField name="itsId" maxlength="45" value="" style="border-color: #ff4400"/>
            </g:if>

            <g:else>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'itsId', 'errors')}">
                <g:textField name="itsId" id="itsId" maxlength="45" value="${assetInstance?.itsId}" title="This field is Required and must be unique."/>
            </td>
            </g:else>

        </tr>

        <tr>
            <td valign="top" class="name"><g:message code="asset.id.label" default="Id" /></td>
            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "id")}</td>
        </tr>
        <g:if test="${action == 'create'}">
            <tr>
                <td valign="top" class="name"><span style="color: #FF0000;" >*</span>
                    <g:message code="asset.assetType.label" default="Asset Type" /></td>
                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'assetType', 'errors')}">
                    <g:select name="assetType.id" from="${edu.hawaii.its.dcmd.inf.AssetType.list()}" optionKey="id" />
                </td>
            </tr>
        </g:if>
        <g:if test="${action == 'edit'}">
            <tr>
                <td valign="top" class="name"><g:message code="asset.assetType.label" default="Asset Type" /></td>
                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'assetType', 'errors')}">
                    ${assetInstance?.assetType?.encodeAsHTML()}
                </td>
            </tr>
        </g:if>
        <tr>
            <td valign="top" class="name"><g:message code="asset.assetStatus.label" default="Asset Status" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'assetStatus', 'errors')}">
                <g:render template="../statusSelect" model="[objectInstance:assetInstance]"/>
        </td>
        </tr>

        <g:if test="${assetType == 'Rack'}">
            <tr>
                <td valign="top" class="name"><g:message code="rack.rowId.label" default="rowId" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "rowId")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.location.label" default="Location" /></td>
                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'location', 'errors')}">
                    <g:select name="location.id" from="${edu.hawaii.its.dcmd.inf.Location.list()}" optionKey="id" value="${assetInstance?.location?.id}" noSelection="['null': '']" />
                </td>
            </tr>
        </g:if>
    <g:if test="${action != 'create'}">

        <g:if test="${assetType == 'Physical Server'}">

             %{--messaging system--}%
            <div id="info_message" class='info_message' style="display: none"></div>
            <div id="warning_message" class='warning_message' style="display: none"></div>


            <tr>
                <td valign="top" class="name">Server Type</td>
                <td valign="top" class="value">
                    <input type="hidden" name="serverType" id="serverType" />
                </td>
            </tr>
            <tr id="globalDiv">
                <td valign="top" class="name" id='globalTitle'>Global Zone</td>
                <td valign="top" class="value">
                    <input type="hidden" name="globalZone" id="globalZone" />
                    <input class="ui-corner-all" id="btnCreateGlobalHost" type="button" value="+" style="width:22px; height:25px; padding-bottom:6px; margin-top:3px; background: -moz-linear-gradient(center bottom , #CCCCCC 0%, #EEEEEE 60%) repeat scroll 0 0 #CCCCCC; vertical-align: middle;"/>
                </td>
            </tr>
            <tr id="clusterDiv">
                <td valign="top" class="name"><g:message code="asset.cluster.label" default="Cluster" /></td>
                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'cluster', 'errors')}">
                    <input type="hidden" name="cluster" id="cluster" />
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">RU (Rack Unit) Size</td>
                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'RU_size', 'errors')}">

                    %{--<g:if test="${assetInstance.RU_size} == 0" >--}%
                        %{--<g:textField name="RU_size" maxlength="2" value="1" onkeyup="calcNewSize(this)"/>--}%
                    %{--</g:if>--}%

                    %{--<g:else>--}%
                        <g:textField name="RU_size" id='RU_size' maxlength="2" value="${assetInstance?.RU_size}" onkeyup="calcNewSize(this)"/>
                    %{--</g:else>--}%

            </tr>

            </table>

            <table class="floatTables" style="border:1px solid #CCCCCC;">
                <tr><td colspan="2"><center><b>Current Rack</b></center></td></tr>
                <tr>
                    <td valign="top" class="name">Rack</td>
                    <td valign="top" class="value">
                        <input type="hidden" name="currentRack" id="currentRack" />
                        <!--<g:select name="currentRack" id="currentRack" from="${edu.hawaii.its.dcmd.inf.Rack.list()}" optionKey="id" value="${assetInstance.getRackAssignmentId()}" />-->
                </tr>
                <tr>
                    <td valign="top" class="name">Rack Location</td>
                    <td valign="top" class="value" id="currentLocation">
                        <a href="../location/show?id=${Rack.get(assetInstance?.getRackAssignmentId())?.location?.id}">
                            ${Rack.get(assetInstance?.getRackAssignmentId())?.location?.encodeAsHTML()}
                        </a>
                    </td>
                </tr>
                <tr>
                    <td valign="top" class="name">Position in Rack</td>
                    <td valign="top" class="value">
                        <g:textField style="width:20px" name="RU_begin" id="RU_begin" value="${assetInstance.getRUBeginPosition()}" onkeyup="calcEndRU(this, 'endRU');"/>

                        <span id="endRU"> - ${assetInstance?.RU_begin}</span>
                    </td>
                </tr>
            </table>
            <br>
            <table class="floatTables" style="border:1px solid #CCCCCC;">

        <tr><td colspan="2"><center><b>Planned Rack</b></center></td></tr>
        <tr>
          <td valign="top" class="name">Rack</td>
          <td valign="top" class="value">
            <input type="hidden" name="plannedRack" id="plannedRack" />

            </td>
            </tr>
            <tr>
                <td valign="top" class="name">Rack Location</td>
                <td valign="top" class="value">
                    <a href="../location/show?id=${Rack.get(assetInstance?.getPlannedRackAssignmentId())?.location?.id}">
                        ${Rack.get(assetInstance?.getPlannedRackAssignmentId())?.location?.encodeAsHTML()}
                    </a>
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">Position in Rack</td>
                <td valign="top" class="value">
                    <g:textField style="width:20px" name="RU_planned_begin" id="RU_planned_begin" value="${assetInstance?.getRUPlannedBeginPosition()}" onkeyup="calcEndRU(this, 'endPlannedRU');"/>
                    <span id="endPlannedRU"> - ${assetInstance?.RU_planned_begin}</span>
                </td>
            </tr>

        </g:if>
    </g:if>

</table>
</div>

<div class="show-wrapper">
    <table class="floatTables">
        <tr>
            <td valign="top" class="name"><g:message code="asset.manufacturer.label" default="Manufacturer" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'manufacturer', 'errors')}">
                <g:render template="../manufacturerSelect" model="[objectInstance:assetInstance]"/>
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.modelDesignation.label" default="Model Designation" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'modelDesignation', 'errors')}">
                <g:textField name="modelDesignation" maxlength="45" value="${assetInstance?.modelDesignation}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.productDescription.label" default="Product Description" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'productDescription', 'errors')}">
                <g:textField name="productDescription" maxlength="45" value="${assetInstance?.productDescription}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.ownershipType.label" default="Ownership Type" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'ownershipType', 'errors')}">
                <g:textField id="ownerTypes" name="ownershipType" maxlength="45" value="${assetInstance?.ownershipType}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.supportLevel.label" default="Support Level" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'supportLevel', 'errors')}">
                <g:textField name="supportLevel" maxlength="45" value="${assetInstance?.supportLevel}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.vendorSupportLevel.label" default="Vendor Support Level" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'vendorSupportLevel', 'errors')}">
                <g:textField name="vendorSupportLevel" maxlength="45" value="${assetInstance?.vendorSupportLevel}" />
            </td>
        </tr>
        <g:if test="${action == 'create'}">
            <tr class="prop">
                <td valign="top" class="name"><label for="primarySA"><g:message
                        code="host.primarySA.label" default="Primary SA" /></label></td>
                <td valign="top"
                    class="value">
                    <g:render template="../personSelect" model="[objectInstance:assetInstance]"/>
                </td>
            </tr>
        </g:if>
    </table>
</div>
<br/>
<div class="show-wrapper">
    <table class="floatTables">
        <tr>

            <td valign="top" class="name"><g:message code="asset.serialNo.label" default="Serial No" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'serialNo', 'errors')}">
                <g:textField name="serialNo" maxlength="45" value="${assetInstance?.serialNo}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.CSI.label" default="Customer Service ID" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'CSI', 'errors')}">
                <g:textField name="CSI" maxlength="45" value="${assetInstance?.CSI}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.decalNo.label" default="Decal No" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'decalNo', 'errors')}">
                <g:textField name="decalNo" maxlength="45" value="${assetInstance?.decalNo}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.isAvailableForParts.label" default="Is Available For Parts" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'isAvailableForParts', 'errors')}">
                <g:checkBox name="isAvailableForParts" value="${assetInstance?.isAvailableForParts}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.postMigrationStatus.label" default="Post Migration Status" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'postMigrationStatus', 'errors')}">
                <g:textField name="postMigrationStatus" maxlength="45" value="${assetInstance?.postMigrationStatus}" />
            </td>
        </tr>
%{--
        <tr>
            <td valign="top" class="name"><g:message code="asset.purchaseContract.label" default="Purchase Contract" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'purchaseContract', 'errors')}">
                <g:select name="purchaseContract.id" from="${edu.hawaii.its.dcmd.inf.Contract.list()}" optionKey="id" value="${assetInstance?.purchaseContract?.id}" noSelection="['null': '']" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.maintenanceContract.label" default="Maintenance Contract" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'maintenanceContract', 'errors')}">
                <g:select name="maintenanceContract.id" from="${edu.hawaii.its.dcmd.inf.Contract.list()}" optionKey="id" value="${assetInstance?.maintenanceContract?.id}" noSelection="['null': '']" />
            </td>
        </tr>
--}%
        <tr>
            <td valign="top" class="name"><g:message code="asset.dateCreated.label" default="Date Created" /></td>
            <td valign="top" class="value"><g:formatDate date="${assetInstance?.dateCreated}" /></td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.lastUpdated.label" default="Last Updated" /></td>
            <td valign="top" class="value"><g:formatDate date="${assetInstance?.lastUpdated}" /></td>
        </tr>
    </table>
</div>
<br />
    <g:if test="${action == 'create'}">
        <div class="alert_info">
            - Create Asset for more options to become available below -
        </div>
    </g:if>

</div>

<style>
.dialogLabel {
    width:100px;
    text-align: left;
    display:inline-block;
    padding-top:10px;
}
</style>

<g:if test="${action != 'create'}">
<div id="host-dialog-form" title="Create new Host">
    <form>
        <label class="dialogLabel" for="dialogHostname">Host Name</label>
        <input type="text" name="dialogHostname" id="dialogHostname" class="text ui-widget-content ui-corner-all" /><br>
        <label class="dialogLabel" for="dialogEnv">Environment</label>
        <g:render template="../environmentSelect" />
        <label class="dialogLabel" for="dialogStatus">Status</label>
        <g:render template="../statusSelect" model="[objectInstance:assetInstance, idNum:2]"/>
        <label class="dialogLabel" for="dialogSA">PrimarySA</label>
        <g:render template="../personSelect" model="[objectInstance:assetInstance, idNum:2]"/>
        <label class="dialogLabel" for="dialogNotes">Host Notes</label>
        <input type="text" name="dialogNotes" id="dialogNotes" class="text ui-widget-content ui-corner-all" /><br>
        <div style="margin-top:5px">
            <input class="ui-corner-all" id="btnCreateHostDialog" type="button" value="Create Host"/>
        </div>
    </form>
</div>
</g:if>