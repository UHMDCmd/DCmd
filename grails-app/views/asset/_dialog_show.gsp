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
<div class="dialog">

    <div class="show-wrapper">
    <table class="floatTables">
        <tr>
            <td valign="top" class="name"><g:message code="asset.itsId.label" default="Its Id" /></td>
            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "itsId")}</td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.id.label" default="Id" /></td>
            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "id")}</td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.assetType.label" default="Asset Type" /></td>
            <td valign="top" class="value">${assetInstance?.assetType?.encodeAsHTML()}</td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.status.label" default="Asset Status" /></td>
            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "status")}</td>
        </tr>
        <g:if test="${assetType == 'Physical Server'}">
            <tr>
                <td valign="top" class="name">Server Type</td>
                <td valign="top" class="value">
                    ${assetInstance.serverType}
                </td>
            </tr>
            <g:if test="${assetInstance.serverType == 'VMWare'}">
                <tr>
                    <td valign="top" class="name">Cluster</td>
                    <td valign="top" class="value">
                        <a href="../cluster/show?id=${assetInstance.cluster?.id}">${assetInstance.cluster.toString()}</a>
                    </td>
                </tr>
                <tr>
                    <td valign="top" class="name">VMWare Host OS</td>
                    <td valign="top" class="value">
                        <a href="../host/show?id=${assetInstance.getGlobalZone()?.id}">${assetInstance.getGlobalZone().toString()}</a>
                    </td>
                </tr>

            </g:if><g:elseif test="${assetInstance.serverType == 'Solaris Global Zone'}">
            <tr>
                <td valign="top" class="name">Global Zone</td>
                <td valign="top" class="value">
                    <a href="../host/show?id=${assetInstance.getGlobalZone()?.id}">${assetInstance.getGlobalZone().toString()}</a>
                </td>
            </tr>
        </g:elseif><g:else>
            <tr>
                <td valign="top" class="name">Standalone Host</td>
                <td valign="top" class="value">
                    <a href="../host/show?id=${assetInstance.getGlobalZone()?.id}">${assetInstance.getGlobalZone().toString()}</a>
                </td>
            </tr>
        </g:else>

            <tr>
                <td valign="top" class="name">RU (Rack Unit) Size</td>
                <td valign="top" class="value">${assetInstance.RU_size.encodeAsHTML()}</td>
            </tr>
            </table>
            <table class="floatTables" style="border:1px solid #CCCCCC;">
                <tr><td colspan="2"><center><b>Current Rack</b></center></td></tr>
                <tr>
                    <td valign="top" class="name">Rack</td>
                    <td valign="top" class="value">
                        <a href="show?id=${assetInstance.getRackAssignmentId()}">${assetInstance?.getRackAssignment().encodeAsHTML()}</a>
                </tr>
                <tr>
                    <td valign="top" class="name">Rack Location</td>
                    <td valign="top" class="value">
                        <a href="../location/show?id=${Rack.get(assetInstance?.getRackAssignmentId())?.location?.id}">
                            ${Rack.get(assetInstance?.getRackAssignmentId())?.location?.encodeAsHTML()}
                        </a>
                    </td>
                </tr>
                <tr>
                    <td valign="top" class="name">Position in Rack</td>
                    <td valign="top" class="value">${assetInstance?.position().encodeAsHTML()}</td>
                </tr>
            </table>
            <br>
            <table class="floatTables" style="border:1px solid #CCCCCC;">

              <tr><td colspan="2"><center><b>Planned Rack</b></center></td></tr>
              <tr>
                <td valign="top" class="name">Rack</td>
                <td valign="top" class="value"><g:link controller="asset" action="show" id="${assetInstance?.getPlannedRackAssignmentId()}">
            ${assetInstance?.getPlannedRackAssignment().encodeAsHTML()}</g:link></td>
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
                <td valign="top" class="value">${assetInstance?.plannedPosition().encodeAsHTML()}</td>
            </tr>
        </g:if>
        <g:if test="${assetType == 'Rack'}">

                <td valign="top" class="name"><g:message code="rack.location.label" default="Location" /></td>
                <td valign="top"><g:link controller="location" action="show" id="${assetInstance?.location?.id}">
                        ${assetInstance?.location?.encodeAsHTML()}</g:link></td>
                <td valign="top" class="name"><g:message code="rack.rowId.label" default="Row Id" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "rowId")}</td>
                <td valign="top" class="name"><g:message code="rack.zoneId.label" default="Zone Id" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "zoneId")}</td>

        </g:if>

    </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="asset.manufacturer.label" default="Manufacturer" /></td>
                <td valign="top" class="value">
                    ${assetInstance?.manufacturer?.encodeAsHTML()}
                </td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.modelDesignation.label" default="Model Designation" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "modelDesignation")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.productDescription.label" default="Product Description" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "productDescription")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.ownershipType.label" default="Ownership Type" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "ownershipType")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.supportLevel.label" default="Support Level" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "supportLevel")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.vendorSupportLevel.label" default="Vendor Support Level" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "vendorSupportLevel")}</td>
        </table>
    </div>
    <br/>
    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="asset.serialNo.label" default="Serial No" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "serialNo")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.CSI.label" default="Customer Service ID" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "CSI")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.decalNo.label" default="Decal No" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "decalNo")}
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.isAvailableForParts.label" default="Is Available For Parts" /></td>
                <td valign="top" class="value"><g:formatBoolean boolean="${assetInstance?.isAvailableForParts}" /></td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.postMigrationStatus.label" default="Post Migration Status" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "postMigrationStatus")}</td>
            </tr>
            <tr>
                %{--
                                <td valign="top" class="name"><g:message code="asset.purchaseContract.label" default="Purchase Contract" /></td>
                                <td valign="top" class="value"><g:link controller="contract" action="show" id="${assetInstance?.purchaseContract?.id}">
                                    ${assetInstance?.purchaseContract?.encodeAsHTML()}</g:link></td>
                            </tr>
                            <tr>
                                <td valign="top" class="name"><g:message code="asset.maintenanceContract.label" default="Maintenance Contract" /></td>
                                <td valign="top" class="value"><g:link controller="contract" action="show" id="${assetInstance?.maintenanceContract?.id}">
                                    ${assetInstance?.maintenanceContract?.encodeAsHTML()}</g:link></td>
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
</div>
			