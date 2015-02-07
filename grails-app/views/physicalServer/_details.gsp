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
def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()

<script language="javascript" type="text/javascript" src="../js/isotope.pkgd.min.js"></script>
<script language="javascript" type="text/javascript">
    function setup(physicalServerInstance) {

        var $container = $('#container');
        $container.isotope({
            itemSelector: '.item',
            layoutMode: 'fitRows'
        });
    }

        $("#serverType").select2({
            width: 150,
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            initSelection: function (element, callback) {
                var data = {id: "{{server.serverType}}", text: "{{server.serverType}}"};
                callback(data);
            }
        }).select2('val', 'VMWare');
    }
        %{--
        $("#clusterSelect").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width: 150,
            initSelection: function (element, callback) {
                var data = {id: "${physicalServerInstance.cluster?.id}", text: "${physicalServerInstance.cluster.toString()}"};
                callback(data);
            },
            data: [${genService.listClustersAsSelect()}]
        }).select2('val', '0');

        $("#serverType").on("change", function (e) {
            changeServerType(e);
        });
        changeServerType("${physicalServerInstance.serverType}");
        /*
         $("#currentRack").select2({
         placeholder: 'Please Select...',
         maximumInputLength: 20,
         width:150,
         initSelection: function(element, callback) {
         var data = {id: "
        ${assetInstance?.getRackAssignmentId()}", text: "
        ${assetInstance?.getRackAssignment()}"};
         callback(data);
         },
         data: [
        ${genService.listRacksAsSelect()}]
         }).select2('val', '0');
         */
    }


    function changeServerType(type) {
        var i;
        var myElements;
        if(type.val == "VMWare" || type == "VMWare") {
            myElements = document.querySelectorAll(".vmOption");

            for (i = 0; i < myElements.length; i++) {
                myElements[i].style.visibility='visible';
                myElements[i].style.position='relative';
            }
//            document.getElementById("otherOption").style.visibility = 'hidden';
//            document.getElementById("otherOption").style.position = 'absolute';
            document.getElementById("vmWarning").style.visibility='visible';
        }
        else {
            myElements = document.querySelectorAll(".vmOption");

            for (i = 0; i < myElements.length; i++) {
                myElements[i].style.visibility='hidden';
                myElements[i].style.position='absolute';
            }
//            document.getElementById("otherOption").style.visibility = 'visible';
//            document.getElementById("otherOption").style.position = 'relative';
            document.getElementById("vmWarning").style.visibility='hidden';
        }
//        var $container = $("#container");
//        $container.isotope('layout');
    }
           --}%

</script>

<script type="text/javascript">

</script>


<style>
.value input[type="text"] {
    background:transparent;
    border: 0px;
    color: white;
}

.editing input[type="text"] {
    background:white;
    border: 1px black;
    color: black;
}
</style>

<script type="text/x-handlebars-template" id="server_template">

<div class="dialog">
<div id="container" class="js-isotope" data-isotope-options='{ "columnWidth":400}' style="min-height:275px">

<input type="button" id="unlock" value="Unlock" /><input type="button" id="lock" value="Lock" />

<div class="item">
    <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>General Information</b></center></td></tr>
        <tr>
            <td valign="top" class="name">itsId</td>
            <td valign="top" class="value">
                <input type='text' value="{{server.itsId}}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name">Status</td>
            <td valign="top"class="value">
                <input type='text' value="{{server.status}}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name">Server Type</td>
            <td valign="top" class="showVal">
                <select id="severTypes">
                    {{#each sTypes}}
                    <option value="{{this.id}}">{{this.text}}</option>
                    {{/each}}
                </select></td>
        </tr>
            %{--
            <td valign="top"
                class="value ${hasErrors(bean: physicalServerInstance, field: 'serverType', 'errors')}">
                <input type="hidden" name="serverType" id="serverType" />
            </td>
        </tr>
        --}%
        %{--
        <g:if test="${physicalServerInstance.serverType == 'VMWare'}">
            <tr class="vmOption">
                <td valign="top" class="name" id="clusterLabel">Cluster</td>
                <td valign="top"
                    class="value ${hasErrors(bean: hostInstance, field: 'cluster', 'errors')}">
                    <input type="hidden" name="clusterSelect" id="clusterSelect" />
                </td>
            </tr>

        </g:if><g:elseif test="${physicalServerInstance.serverType == 'Solaris Global Zone'}">
        <tr>
            <td valign="top" class="name">Global Zone</td>
            <td valign="top" class="value">
                <a href="../host/show?id=${physicalServerInstance.getGlobalZone()?.id}">${physicalServerInstance.getGlobalZone().toString()}</a>
            </td>
        </tr>
    </g:elseif><g:else>
        <tr>
            <td valign="top" class="name">Standalone Host</td>
            <td valign="top" class="value">
                <a href="../host/show?id=${physicalServerInstance.getGlobalZone()?.id}">${physicalServerInstance.getGlobalZone().toString()}</a>
            </td>
        </tr>
    </g:else>

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

        <tr>
            <td valign="top" class="name"><g:message code="asset.memoryUsed.label" default="Memory Assigned to Hosts" /></td>
            <td valign="top" class="value">${(physicalServerInstance.getTotalMemoryUsed()/1000.0)} GB</td>
        </tr>

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
        <tr>
            <td valign="top" class="name"><g:message code="asset.numCores.label" default="Total CPU" /></td>
            <td valign="top" class="value">${(physicalServerInstance.cpuSpeed * physicalServerInstance.numCores*100.0).round()/100.0} GHz</td>
        </tr>
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

    </table>
%{--
<div class="item">

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
            <td valign="top" class="value ${hasErrors(bean: physicalServerInstance, field: 'vendor'', 'errors')}">
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
    --}%
</div>
</div>
<br />
</div>
</script>