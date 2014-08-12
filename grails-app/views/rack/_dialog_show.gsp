<div class="dialog">

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name">%{--
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

<g:message code="rack.rackNum.label" default="Rack Number" /></td>
                <td valign="top" class="value">${fieldValue(bean: rackInstance, field: "rackNum")}</td>
            </tr>
            <tr>
				<td valign="top" class="name"><g:message code="rack.id.label" default="Id" /></td>
				<td valign="top" class="value">${fieldValue(bean: rackInstance, field: "id")}</td>
            </tr>

            <tr>
                <td valign="top" class="name"><g:message code="rack.assetStatus.label" default="Asset Status" /></td>
                <td valign="top" class="value">${fieldValue(bean: rackInstance, field: "assetStatus")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="rack.location.label" default="Location" /></td>
                <td valign="top" class="value"><g:link controller="location" action="show" id="${rackInstance?.location?.id}">
                    ${rackInstance?.location?.encodeAsHTML()}</g:link></td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="rack.datacenter.label" default="Row Id" /></td>
                <td valign="top" class="value">${fieldValue(bean: rackInstance, field: "rowId")}</td>
            </tr><tr>
                <td valign="top" class="name"><g:message code="rack.datacenter.label" default="Zone Id" /></td>
                <td valign="top" class="value">${fieldValue(bean: rackInstance, field: "zoneId")}</td>
            </tr>
        </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="rack.manufacturer.label" default="Manufacturer" /></td>
                <td valign="top" class="value"><g:link controller="manufacturer" action="show" id="${rackInstance?.manufacturer?.id}">
                    ${rackInstance?.manufacturer?.encodeAsHTML()}</g:link></td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="rack.modelDesignation.label" default="Model Designation" /></td>
                <td valign="top" class="value">${fieldValue(bean: rackInstance, field: "modelDesignation")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="rack.productDescription.label" default="Product Description" /></td>
                <td valign="top" class="value">${fieldValue(bean: rackInstance, field: "productDescription")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="rack.ownershipType.label" default="Ownership Type" /></td>
				<td valign="top" class="value">${fieldValue(bean: rackInstance, field: "ownershipType")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="rack.supportLevel.label" default="Support Level" /></td>
                <td valign="top" class="value">${fieldValue(bean: rackInstance, field: "supportLevel")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="rack.vendorSupportLevel.label" default="Vendor Support Level" /></td>
                <td valign="top" class="value">${fieldValue(bean: rackInstance, field: "vendorSupportLevel")}</td>
        </table>
    </div>
       <br/>
    <div class="show-wrapper">
        <table class="floatTables">
            <tr>

                <td valign="top" class="name"><g:message code="rack.serialNo.label" default="Serial No" /></td>
				<td valign="top" class="value">${fieldValue(bean: rackInstance, field: "serialNo")}</td>
            </tr>
            <tr>
				<td valign="top" class="name"><g:message code="rack.decalNo.label" default="Decal No" /></td>
				<td valign="top" class="value">${fieldValue(bean: rackInstance, field: "decalNo")}
            </tr>
            <tr>
				<td valign="top" class="name"><g:message code="rack.isAvailableForParts.label" default="Is Available For Parts" /></td>
				<td valign="top" class="value"><g:formatBoolean boolean="${rackInstance?.isAvailableForParts}" /></td>
            </tr>
            <tr>
				<td valign="top" class="name"><g:message code="rack.postMigrationStatus.label" default="Post Migration Status" /></td>
				<td valign="top" class="value">${fieldValue(bean: rackInstance, field: "postMigrationStatus")}</td>
            </tr>
            <tr>
				<td valign="top" class="name"><g:message code="rack.purchaseContract.label" default="Purchase Contract" /></td>
				<td valign="top" class="value"><g:link controller="contract" action="show" id="${rackInstance?.purchaseContract?.id}">
					${rackInstance?.purchaseContract?.encodeAsHTML()}</g:link></td>
            </tr>
            <tr>
				<td valign="top" class="name"><g:message code="rack.maintenanceContract.label" default="Maintenance Contract" /></td>
				<td valign="top" class="value"><g:link controller="contract" action="show" id="${rackInstance?.maintenanceContract?.id}">
					${rackInstance?.maintenanceContract?.encodeAsHTML()}</g:link></td>
            </tr>
            <tr>
				<td valign="top" class="name"><g:message code="rack.dateCreated.label" default="Date Created" /></td>
				<td valign="top" class="value"><g:formatDate date="${rackInstance?.dateCreated}" /></td>
            </tr>
            <tr>
				<td valign="top" class="name"><g:message code="rack.lastUpdated.label" default="Last Updated" /></td>
				<td valign="top" class="value"><g:formatDate date="${rackInstance?.lastUpdated}" /></td>
		    </tr>
	    </table>
    </div>
    <br />
</div>
			