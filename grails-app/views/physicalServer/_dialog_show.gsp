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
				<td valign="top" class="value"><g:link controller="assetType" action="show" id="${assetInstance?.assetType?.id}">
					${assetInstance?.assetType?.encodeAsHTML()}</g:link></td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.assetStatus.label" default="Asset Status" /></td>
                <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "assetStatus")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="asset.cluster.label" default="Cluster" /></td>
                <td valign="top" class="value"><g:link url="../cluster/show?id=${assetInstance?.cluster?.id}">
                    ${assetInstance?.cluster?.encodeAsHTML()}</g:link></td>
            </tr>
                <td valign="top" class="name"><g:message code="asset.location.label" default="Location" /></td>
                <td valign="top" class="value"><g:link controller="location" action="show" id="${assetInstance?.location?.id}">
                    ${assetInstance?.location?.encodeAsHTML()}</g:link></td>
            </tr>
        </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="asset.manufacturer.label" default="Manufacturer" /></td>
                <td valign="top" class="value"><g:link controller="manufacturer" action="show" id="${assetInstance?.manufacturer?.id}">
                    ${assetInstance?.manufacturer?.encodeAsHTML()}</g:link></td>
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
				<td valign="top" class="name"><g:message code="asset.purchaseContract.label" default="Purchase Contract" /></td>
				<td valign="top" class="value"><g:link controller="contract" action="show" id="${assetInstance?.purchaseContract?.id}">
					${assetInstance?.purchaseContract?.encodeAsHTML()}</g:link></td>
            </tr>
            <tr>
				<td valign="top" class="name"><g:message code="asset.maintenanceContract.label" default="Maintenance Contract" /></td>
				<td valign="top" class="value"><g:link controller="contract" action="show" id="${assetInstance?.maintenanceContract?.id}">
					${assetInstance?.maintenanceContract?.encodeAsHTML()}</g:link></td>
            </tr>
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
			