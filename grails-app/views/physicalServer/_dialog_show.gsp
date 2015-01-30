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

<script language="javascript" type="text/javascript" src="../js/isotope.pkgd.min.js"></script>
<script language="javascript" type="text/javascript">
    var container = document.querySelector("#container");
    var iso = new Isotope( container, {
        itemSelector:'.item',
        layoutMode:'fitRows'
    });
</script>


<div class="dialog">
<div id="container" class="js-isotope" data-isotope-options='{ "columnWidth":400}'>

    <div class="item">
        <table class="floatTables" style="border:1px solid #CCCCCC;">
            <tr><td colspan="2"><center><b>General Information</b></center></td></tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.itsId.label" default="Its Id" /></td>
            <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "itsId")}</td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.id.label" default="Id" /></td>
            <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "id")}</td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.status.label" default="Status" /></td>
            <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "status")}</td>
        </tr>
        <tr>
            <td valign="top" class="name">Server Type</td>
            <td valign="top" class="value">
                ${fieldValue(bean: physicalServerInstance, field: "serverType")}
            </td>
        </tr>
        <g:if test="${physicalServerInstance.serverType == 'VMWare'}">
            <tr>
                <td valign="top" class="name">Cluster</td>
                <td valign="top" class="value">
                    <a href="../cluster/show?id=${physicalServerInstance.cluster?.id}">${physicalServerInstance.cluster.toString()}</a>
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">VMWare Host OS</td>
                <td valign="top" class="value">
                    ${physicalServerInstance.getHostOSLinkString()}
                </td>
            </tr>

        </g:if><g:elseif test="${physicalServerInstance.serverType == 'Solaris Global Zone'}">
            <tr>
                <td valign="top" class="name">Global Zone</td>
                <td valign="top" class="value">
                    ${physicalServerInstance.getHostOSLinkString()}
                </td>
            </tr>
        </g:elseif><g:elseif test="${physicalServerInstance.serverType == 'Standalone'}">
            <tr>
                <td valign="top" class="name">Standalone Host</td>
                <td valign="top" class="value">
                    ${physicalServerInstance.getHostOSLinkString()}
                </td>
            </tr>
        </g:elseif>
        </table>
    </div>
    <div class="item">

        <table class="floatTables" style="border:1px solid #CCCCCC;">
            <tr><td colspan="2"><center><b>Capacity Information</b></center></td></tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.memory.label" default="Total Memory" /></td>
                <td valign="top" class="value">${physicalServerInstance.memorySize} GB</td>
            </tr>

            <tr>
                <td valign="top" class="name"><g:message code="asset.memoryUsed.label" default="Memory Assigned to Hosts" /></td>
                <td valign="top" class="value">${physicalServerInstance.getTotalGBUsed()}</td>
            </tr>

            <tr>
                <td valign="top" class="name"><g:message code="asset.cpuSpeed.label" default="CPU Speed" /></td>
                <td valign="top" class="value">${physicalServerInstance.cpuSpeed} GHz</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.numCores.label" default="# Cores" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "numCores")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.numCores.label" default="Max Assigned CPU" /></td>
                <td valign="top" class="value">${physicalServerInstance.getTotalGhzUsed()}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.numThreads.label" default="# Threads" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "numThreads")}</td>
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
            <tr><td colspan="2"><center><b>Vendor Information</b></center></td></tr>
            %{--
            <tr>
                <td valign="top" class="name"><g:message code="asset.manufacturer.label" default="Manufacturer" /></td>
                <td valign="top" class="value">
                    ${physicalServerInstance?.manufacturer?.encodeAsHTML()}
                </td>
            </tr>
            --}%

            <tr>
                <td valign="top" class="name"><g:message code="asset.vendor.label" default="Vendor" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "vendor")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.modelDesignation.label" default="Model Designation" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "modelDesignation")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.productDescription.label" default="Product Description" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "productDescription")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.ownershipType.label" default="Ownership Type" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "ownershipType")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.supportLevel.label" default="Support Level" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "supportLevel")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.vendorSupportLevel.label" default="Vendor Support Level" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "vendorSupportLevel")}</td>
            </tr>
        </table>
    </div>

    <div class="item">
        <table class="floatTables" style="border:1px solid #CCCCCC;">
            <tr><td colspan="2"><center><b>Hardware Information</b></center></td></tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.serialNo.label" default="Serial No" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "serialNo")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.CSI.label" default="Customer Service ID" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "CSI")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.decalNo.label" default="Decal No" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "decalNo")}
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.isAvailableForParts.label" default="Is Available For Parts" /></td>
                <td valign="top" class="value"><g:formatBoolean boolean="${physicalServerInstance?.isAvailableForParts}" /></td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.postMigrationStatus.label" default="Post Migration Status" /></td>
                <td valign="top" class="value">${fieldValue(bean: physicalServerInstance, field: "postMigrationStatus")}</td>
            </tr>
            <tr>
                %{--
                                <td valign="top" class="name"><g:message code="asset.purchaseContract.label" default="Purchase Contract" /></td>
                                <td valign="top" class="value"><g:link controller="contract" action="show" id="${physicalServerInstance?.purchaseContract?.id}">
                                    ${physicalServerInstance?.purchaseContract?.encodeAsHTML()}</g:link></td>
                            </tr>
                            <tr>
                                <td valign="top" class="name"><g:message code="asset.maintenanceContract.label" default="Maintenance Contract" /></td>
                                <td valign="top" class="value"><g:link controller="contract" action="show" id="${physicalServerInstance?.maintenanceContract?.id}">
                                    ${physicalServerInstance?.maintenanceContract?.encodeAsHTML()}</g:link></td>
                            </tr>
                --}%
            <tr>
                <td valign="top" class="name"><g:message code="asset.lastUpdated.label" default="Last Updated" /></td>
                <td valign="top" class="value"><g:formatDate date="${physicalServerInstance?.lastUpdated}" /></td>
            </tr>
        </table>
    </div>
</div>
    <br />
</div>
			