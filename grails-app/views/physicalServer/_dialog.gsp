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

<%@ page import="edu.hawaii.its.dcmd.inf.Rack" %>
<%@  page import="edu.hawaii.its.dcmd.inf.AssetService" %>
<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%@  page import="edu.hawaii.its.dcmd.inf.HostService" %>
<%
    def assetService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.AssetService').newInstance()
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
    def hostService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.HostService').newInstance()
%>

<r:require modules='select2' />

<script language="javascript" type="text/javascript" src="../js/isotope.pkgd.min.js"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function() {

        var $container = $('#container');
        $container.isotope({
            itemSelector:'.item',
            layoutMode:'fitRows'
        });
        $("#clusterSelect").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
                var data = {id: "${physicalServerInstance?.cluster?.id}", text: "${physicalServerInstance?.cluster.toString()}"};
                callback(data);
            },
            data: [${genService.listClustersAsSelect()}]
        }).select2('val', '0');

        $("#serverType").select2({
            width:150,
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            initSelection: function(element, callback) {
                var data = {id: "${physicalServerInstance?.serverType}", text: "${physicalServerInstance?.serverType}"};
                callback(data);
            },
            data: [${genService.listServerTypesAsSelect()}]
        }).select2('val', 'VMWare');

        $("#serverType").on("change", function(e){changeServerType(e);} );
        changeServerType("${physicalServerInstance?.serverType}");

        $("#globalZone").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
            var data = {id: "${physicalServerInstance?.getGlobalZone()?.id}", text: "${physicalServerInstance?.getGlobalZone().toString()}"};
            callback(data);
            },
            data: [${hostService.listHostsAsSelect()}]
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
                url: '../physicalServer/editHosts?assetId=${physicalServerInstance?.id}',
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
        var i;
        var myElements;
        if(type.val == "VMWare" || type == "VMWare") {
            myElements = document.querySelectorAll(".vmOption");

            for (i = 0; i < myElements.length; i++) {
                myElements[i].style.visibility='visible';
//                myElements[i].style.position='relative';
            }
//            document.getElementById("otherOption").style.visibility = 'hidden';
//            document.getElementById("otherOption").style.position = 'absolute';
            if(${action!='create'})
                document.getElementById("vmWarning").style.visibility='visible';
        }
        else {
            myElements = document.querySelectorAll(".vmOption");

            for (i = 0; i < myElements.length; i++) {
                myElements[i].style.visibility='hidden';
//                myElements[i].style.position='absolute';
            }
//            document.getElementById("otherOption").style.visibility = 'visible';
//            document.getElementById("otherOption").style.position = 'relative';
            if(${action!='create'})
                document.getElementById("vmWarning").style.visibility='hidden';
        }
//        var $container = $("#container");
//        $container.isotope('layout');
    }


</script>


<div class="dialog">
<div id="container" class="js-isotope" data-isotope-options='{ "columnWidth":400}' style="min-height:275px">

<div class="item">
<table class="floatTables" style="border:1px solid #CCCCCC;">
<tr><td colspan="2"><center><b>General Information</b></center></td></tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.itsId.label" default="Its Id" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'itsId', 'errors')}">
                <g:textField name="itsId" maxlength="45" value="${physicalServerInstance?.itsId}" title="This field is Required and must be unique."/>
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.id.label" default="Id" /></td>
            <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "id")}</td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="server.serverStatus.label" default="Status" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: physicalServerInstance, field: 'status', 'errors')}">
                <g:render template="../statusSelect" model="[objectInstance:physicalServerInstance]"/>
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name"><label for="serverType"><g:message
                    code="server.serverType.label" default="Server Type" /></label></td>
            <td valign="top"
                class="value ${hasErrors(bean: physicalServerInstance, field: 'serverType', 'errors')}">
                <input type="hidden" name="serverType" id="serverType" />
            </td>
        </tr>

            <tr class="vmOption">
                <td valign="top" class="name" id="clusterLabel">Cluster</td>
                <td valign="top"
                    class="value ${hasErrors(bean: hostInstance, field: 'cluster', 'errors')}">
                    <input type="hidden" name="clusterSelect" id="clusterSelect" />
                </td>
            </tr>

        <tr id="globalDiv">
            <td valign="top" class="name" id='globalTitle'>Server OS (Global Zone, etc.)</td>
            <td valign="top" class="value">
                <input type="hidden" name="globalZone" id="globalZone" />
                <input class="ui-corner-all" id="btnCreateGlobalHost" type="button" value="+" style="width:22px; height:25px; padding-bottom:6px; margin-top:3px; background: -moz-linear-gradient(center bottom , #CCCCCC 0%, #EEEEEE 60%) repeat scroll 0 0 #CCCCCC; vertical-align: middle;"/>
            </td>
        </tr>
    </table>
</div>

<div class="item">

    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>Capacity Information</b></center></td></tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.memory.label" default="Total Memory" /></td>
            <td valign="top" class="value">
                <g:textField name="memorySize" maxlength="5" value="${physicalServerInstance?.memorySize}" style="width:20%"/> GB
            </td>
        </tr>

        <g:if test="${action!='create'}">
        <tr>
            <td valign="top" class="name"><g:message code="asset.memoryUsed.label" default="Memory Assigned to Hosts" /></td>
            <td valign="top" class="value">${physicalServerInstance.getTotalGBUsed()}</td>
        </tr>
        </g:if>
        <tr>
            <td valign="top" class="name"><g:message code="asset.cpuSpeed.label" default="CPU Speed" /></td>
            <td valign="top" class="value">
                <g:textField name="cpuSpeed" maxlength="5" value="${physicalServerInstance?.cpuSpeed}" style="width:20%"/> GHz
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.numCores.label" default="# Cores" /></td>
            <td valign="top" class="value">
                <g:textField name="numCores" maxlength="5" value="${physicalServerInstance?.numCores}" style="width:20%"/>
            </td>
        </tr>
<g:if test="${action!='create'}">
        <tr>
            <td valign="top" class="name"><g:message code="asset.numCores.label" default="Total CPU" /></td>
            <td valign="top" class="value">${physicalServerInstance.getTotalGhzUsed()}</td>
        </tr>
</g:if>
        <tr>
            <td valign="top" class="name"><g:message code="asset.numThreads.label" default="# Threads" /></td>
            <td valign="top" class="value">
                <g:textField name="numThreads" maxlength="5" value="${physicalServerInstance?.numThreads}" style="width:20%"/> GHz
            </td>
        </tr>

        %{--
        <tr>
            <td valign="top" class="name"><g:message code="asset.numCores.label" default="CPU Usage" /></td>
            <td valign="top" class="value">${(physicalServerInstance.getCpuUsage())} GHz</td>
        </tr>
        --}%

    </table>
</div>
<div class="item">

    %{--
    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>Location Information</b></center></td></tr>
        <tr>
            <td valign="top" class="name">RU (Rack Unit) Size</td>
            <td valign="top" class="value">
                <g:textField name="RU_size" maxlength="5" value="${physicalServerInstance?.RU_size.encodeAsHTML()}" style="width:20%"/>
            </td>
        </tr>

        <tr>
            <td valign="top" class="name">Rack</td>
            <td valign="top" class="value">
                <input type="hidden" name="currentRack" id="currentRack" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Rack Location</td>
            <td valign="top" class="value" id="currentLocation">
                <a href="../location/show?id=${Rack.get(physicalServerInstance?.getRackAssignmentId())?.location?.id}">
                    ${Rack.get(physicalServerInstance?.getRackAssignmentId())?.location?.encodeAsHTML()}
                </a>
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Position in Rack</td>
            <td valign="top" class="value">
                <g:textField style="width:20px" name="RU_begin" id="RU_begin" value="${physicalServerInstance.getRUBeginPosition()}" onkeyup="calcEndRU(this, 'endRU');"/>

                <span id="endRU"> - ${physicalServerInstance?.RU_begin}</span>
            </td>
        </tr>
    </table>
    --}%
    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>Location Information (Read-Only - Pulled from openDCIM)</b></center></td></tr>
        <tr>
            <td valign="top" class="name">RU (Rack Unit) Size</td>
            <td valign="top" class="value">${physicalServerInstance.RU_size.encodeAsHTML()}</td>
        </tr>

        <tr>
            <td valign="top" class="name">Rack</td>
            <td valign="top" class="value">
                <a href="../asset/show?id=${physicalServerInstance.getRackAssignmentId()}">${physicalServerInstance?.getRackAssignment().encodeAsHTML()}</a>
        </tr>
        <tr>
            <td valign="top" class="name">Rack Location</td>
            <td valign="top" class="value">
                <a href="../location/show?id=${Rack.get(physicalServerInstance?.getRackAssignmentId())?.location?.id}">
                    ${Rack.get(physicalServerInstance?.getRackAssignmentId())?.location?.encodeAsHTML()}
                </a>
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Position in Rack</td>
            <td valign="top" class="value">${physicalServerInstance?.position().encodeAsHTML()}</td>
        </tr>
    </table>
</div>
<div class="item">

    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>Hardware Information</b></center></td></tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.vendor.label" default="Vendor" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'vendor', 'errors')}">
                <g:textField name="vendor" maxlength="45" value="${physicalServerInstance?.vendor}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.modelDesignation.label" default="Model Designation" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'modelDesignation', 'errors')}">
                <g:textField name="modelDesignation" maxlength="45" value="${physicalServerInstance?.modelDesignation}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.productDescription.label" default="Product Description" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'productDescription', 'errors')}">
                <g:textField name="productDescription" maxlength="45" value="${physicalServerInstance?.productDescription}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.ownershipType.label" default="Ownership Type" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'ownershipType', 'errors')}">
                <g:textField id="ownerTypes" name="ownershipType" maxlength="45" value="${physicalServerInstance?.ownershipType}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.supportLevel.label" default="Support Level" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'supportLevel', 'errors')}">
                <g:textField name="supportLevel" maxlength="45" value="${physicalServerInstance?.supportLevel}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.vendorSupportLevel.label" default="Vendor Support Level" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'vendorSupportLevel', 'errors')}">
                <g:textField name="vendorSupportLevel" maxlength="45" value="${physicalServerInstance?.vendorSupportLevel}" />
            </td>
        </tr>
    </table>
</div>

<div class="item">
    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>Hardware Information</b></center></td></tr>
        <tr>

            <td valign="top" class="name"><g:message code="asset.serialNo.label" default="Serial No" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'serialNo', 'errors')}">
                <g:textField name="serialNo" maxlength="45" value="${physicalServerInstance?.serialNo}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.CSI.label" default="Customer Service ID" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'CSI', 'errors')}">
                <g:textField name="CSI" maxlength="45" value="${physicalServerInstance?.CSI}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.decalNo.label" default="Decal No" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'decalNo', 'errors')}">
                <g:textField name="decalNo" maxlength="45" value="${physicalServerInstance?.decalNo}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.isAvailableForParts.label" default="Is Available For Parts" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'isAvailableForParts', 'errors')}">
                <g:checkBox name="isAvailableForParts" value="${physicalServerInstance?.isAvailableForParts}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.postMigrationStatus.label" default="Post Migration Status" /></td>
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'postMigrationStatus', 'errors')}">
                <g:textField name="postMigrationStatus" maxlength="45" value="${physicalServerInstance?.postMigrationStatus}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.lastUpdated.label" default="Last Updated" /></td>
            <td valign="top" class="value"><g:formatDate date="${physicalServerInstance?.lastUpdated}" /></td>
        </tr>
    </table>
</div>
</div>
<br />
</div>

<style>
.dialogLabel {
    width:100px;
    text-align: left;
    display:inline-block;
    padding-top:10px;
}
</style>

%{--<g:if test="${action != 'create'}"> --}%
    <div id="host-dialog-form" title="Create new Host">
        <form>
            <label class="dialogLabel" for="dialogHostname">Host Name</label>
            <input type="text" name="dialogHostname" id="dialogHostname" class="text ui-widget-content ui-corner-all" /><br>
            <label class="dialogLabel" for="dialogEnv">Environment</label>
            <g:render template="../environmentSelect" />
            <label class="dialogLabel" for="dialogStatus">Status</label>
            <g:render template="../statusSelect" model="[objectInstance:physicalServerInstance, idNum:2]"/>
            <label class="dialogLabel" for="dialogSA">PrimarySA</label>
            <g:render template="../personSelect" model="[objectInstance:physicalServerInstance, idNum:2]"/>
            <label class="dialogLabel" for="dialogNotes">Host Notes</label>
            <input type="text" name="dialogNotes" id="dialogNotes" class="text ui-widget-content ui-corner-all" /><br>
            <div style="margin-top:5px">
                <input class="ui-corner-all" id="btnCreateHostDialog" type="button" value="Create Host"/>
            </div>
        </form>
    </div>
%{--</g:if> --}%
			