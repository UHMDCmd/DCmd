<%@ page import="edu.hawaii.its.dcmd.inf.Contract" %>
<div class="dialog">

<div class="show-wrapper">
    <table class="floatTables">
        <tr>
            <td valign="top" class="name"><g:message code="asset.itsId.label" default="* Its Id" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'itsId', 'errors')}">

                <g:if test="${params.cloned == true}">
                 <h1>This is a Clone, and needs a unique name</h1>
                <g:textField name="itsId" maxlength="45" value="" />
                </g:if>

                <g:else>
                    <g:textField name="itsId" maxlength="45" value="${assetInstance?.itsId}" />
                </g:else>
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.id.label" default="Id" /></td>
            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "id")}</td>
        </tr>
      %{--  <tr>
            <td valign="top" class="name"><g:message code="asset.assetType.label" default="Asset Type" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'assetType', 'errors')}">
                <g:select name="assetType.id" from="${edu.hawaii.its.dcmd.inf.AssetType.list()}" optionKey="id" value="${assetInstance?.assetType?.id}"  />
            </td>
        </tr>--}%
        <tr>
            <td valign="top" class="name"><g:message code="asset.assetStatus.label" default="Asset Status" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'assetStatus', 'errors')}">
                <g:textField name="assetStatus" maxlength="45" value="${assetInstance?.assetStatus}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.cluster.label" default="Cluster" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'location', 'errors')}">
                <g:select name="cluster.id" from="${edu.hawaii.its.dcmd.inf.Cluster.list()}" optionKey="id" value="${assetInstance?.cluster?.id}" noSelection="['null': '']" />
            </td>
        </tr>
        <td valign="top" class="name"><g:message code="asset.location.label" default="Location" /></td>
        <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'location', 'errors')}">
            <g:select name="location.id" from="${edu.hawaii.its.dcmd.inf.Location.list()}" optionKey="id" value="${assetInstance?.location?.id}" noSelection="['null': '']" />
        </td>
    </tr>
    </table>
</div>

<div class="show-wrapper">
    <table class="floatTables">
        <tr>
            <td valign="top" class="name"><g:message code="asset.manufacturer.label" default="Manufacturer" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'manufacturer', 'errors')}">
                <g:select name="manufacturer.id" from="${edu.hawaii.its.dcmd.inf.Manufacturer.list()}" optionKey="id" value="${assetInstance?.manufacturer?.id}"  />
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
        <tr>
            <td valign="top" class="name"><g:message code="asset.purchaseContract.label" default="Purchase Contract" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'purchaseContract', 'errors')}">
                <g:select name="purchaseContract.id" from="${Contract.list()}" optionKey="id" value="${assetInstance?.purchaseContract?.id}" noSelection="['null': '']" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="asset.maintenanceContract.label" default="Maintenance Contract" /></td>
            <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'maintenanceContract', 'errors')}">
                <g:select name="maintenanceContract.id" from="${edu.hawaii.its.dcmd.inf.Contract.list()}" optionKey="id" value="${assetInstance?.maintenanceContract?.id}" noSelection="['null': '']" />
            </td>
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