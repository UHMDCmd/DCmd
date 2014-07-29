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

<%@ page import="edu.hawaii.its.dcmd.inf.Contract" %>
<div class="dialog">

<div class="show-wrapper">
    <table class="floatTables">
        <tr>
            <td valign="top" class="name"><g:message code="rack.rackNum.label" default="Rack Number" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'rackNum', 'errors')}">
                <g:textField name="rackNum" maxlength="45" value="${rackInstance?.rackNum}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.id.label" default="Id" /></td>
            <td valign="top" class="value">${fieldValue(bean: rackInstance, field: "id")}</td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.assetStatus.label" default="Asset Status" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'assetStatus', 'errors')}">
                <g:textField name="assetStatus" maxlength="45" value="${rackInstance?.assetStatus}" />
            </td>
        </tr>
        <tr>
        <td valign="top" class="name"><g:message code="rack.location.label" default="Location" /></td>
        <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'location', 'errors')}">
            <g:select name="location.id" from="${edu.hawaii.its.dcmd.inf.Location.list()}" optionKey="id" value="${rackInstance?.location?.id}" noSelection="['null': '']" />
        </td>
    </tr>
    </table>
</div>

<div class="show-wrapper">
    <table class="floatTables">
        <tr>
            <td valign="top" class="name"><g:message code="rack.manufacturer.label" default="Manufacturer" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'manufacturer', 'errors')}">
                <g:select name="manufacturer.id" from="${edu.hawaii.its.dcmd.inf.Manufacturer.list()}" optionKey="id" value="${rackInstance?.manufacturer?.id}"  />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.modelDesignation.label" default="Model Designation" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'modelDesignation', 'errors')}">
                <g:textField name="modelDesignation" maxlength="45" value="${rackInstance?.modelDesignation}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.productDescription.label" default="Product Description" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'productDescription', 'errors')}">
                <g:textField name="productDescription" maxlength="45" value="${rackInstance?.productDescription}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.ownershipType.label" default="Ownership Type" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'ownershipType', 'errors')}">
                <g:textField id="ownerTypes" name="ownershipType" maxlength="45" value="${rackInstance?.ownershipType}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.supportLevel.label" default="Support Level" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'supportLevel', 'errors')}">
                <g:textField name="supportLevel" maxlength="45" value="${rackInstance?.supportLevel}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.vendorSupportLevel.label" default="Vendor Support Level" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'vendorSupportLevel', 'errors')}">
                <g:textField name="vendorSupportLevel" maxlength="45" value="${rackInstance?.vendorSupportLevel}" />
            </td>
    </table>
</div>
<br/>
<div class="show-wrapper">
    <table class="floatTables">
        <tr>

            <td valign="top" class="name"><g:message code="rack.serialNo.label" default="Serial No" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'serialNo', 'errors')}">
                <g:textField name="serialNo" maxlength="45" value="${rackInstance?.serialNo}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.decalNo.label" default="Decal No" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'decalNo', 'errors')}">
                <g:textField name="decalNo" maxlength="45" value="${rackInstance?.decalNo}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.isAvailableForParts.label" default="Is Available For Parts" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'isAvailableForParts', 'errors')}">
                <g:checkBox name="isAvailableForParts" value="${rackInstance?.isAvailableForParts}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.postMigrationStatus.label" default="Post Migration Status" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'postMigrationStatus', 'errors')}">
                <g:textField name="postMigrationStatus" maxlength="45" value="${rackInstance?.postMigrationStatus}" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.purchaseContract.label" default="Purchase Contract" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'purchaseContract', 'errors')}">
                <g:select name="purchaseContract.id" from="${Contract.list()}" optionKey="id" value="${rackInstance?.purchaseContract?.id}" noSelection="['null': '']" />
            </td>
        </tr>
        <tr>
            <td valign="top" class="name"><g:message code="rack.maintenanceContract.label" default="Maintenance Contract" /></td>
            <td valign="top" class="value ${hasErrors(bean: rackInstance, field: 'maintenanceContract', 'errors')}">
                <g:select name="maintenanceContract.id" from="${edu.hawaii.its.dcmd.inf.Contract.list()}" optionKey="id" value="${rackInstance?.maintenanceContract?.id}" noSelection="['null': '']" />
            </td>
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