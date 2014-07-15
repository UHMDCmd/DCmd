

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

<%@ page import="edu.hawaii.its.dcmd.inf.Manufacturer" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'manufacturer.label', default: 'Manufacturer')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${manufacturerInstance}">
            <div class="errors">
                <g:renderErrors bean="${manufacturerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${manufacturerInstance?.id}" />
                <g:hiddenField name="version" value="${manufacturerInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="manufacturer.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${manufacturerInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="code"><g:message code="manufacturer.code.label" default="Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'code', 'errors')}">
                                    <g:textField name="code" value="${manufacturerInstance?.code}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phone"><g:message code="manufacturer.phone.label" default="Phone" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'phone', 'errors')}">
                                    <g:textField name="phone" value="${manufacturerInstance?.phone}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fax"><g:message code="manufacturer.fax.label" default="Fax" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'fax', 'errors')}">
                                    <g:textField name="fax" value="${manufacturerInstance?.fax}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="addressLine1"><g:message code="manufacturer.addressLine1.label" default="Address Line1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'addressLine1', 'errors')}">
                                    <g:textField name="addressLine1" value="${manufacturerInstance?.addressLine1}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="addressLine2"><g:message code="manufacturer.addressLine2.label" default="Address Line2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'addressLine2', 'errors')}">
                                    <g:textField name="addressLine2" value="${manufacturerInstance?.addressLine2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="city"><g:message code="manufacturer.city.label" default="City" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'city', 'errors')}">
                                    <g:textField name="city" value="${manufacturerInstance?.city}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="state"><g:message code="manufacturer.state.label" default="State" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'state', 'errors')}">
                                    <g:textField name="state" value="${manufacturerInstance?.state}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="zip"><g:message code="manufacturer.zip.label" default="Zip" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'zip', 'errors')}">
                                    <g:textField name="zip" value="${manufacturerInstance?.zip}" />
                                </td>
                            </tr>
                            
                            <g:hiddenField name="type" value="${manufacturerInstance?.type}" />
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="updatedById"><g:message code="manufacturer.updatedById.label" default="Updated By Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: manufacturerInstance, field: 'updatedById', 'errors')}">
                                    <g:textField name="updatedById" value="${fieldValue(bean: manufacturerInstance, field: 'updatedById')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
