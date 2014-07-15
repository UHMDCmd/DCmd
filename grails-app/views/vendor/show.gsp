
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

<%@ page import="edu.hawaii.its.dcmd.inf.Vendor" %>
<html>
    <head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<meta name="layout" content="main" />
			<g:set var="entityName" value="${message(code: 'vendor.label', default: 'Vendor')}" />
			<title><g:message code="default.show.label" args="[entityName]" /></title>
			<g:javascript>
				$(function() {
					$( "#tabs" ).tabs({
					});
				});
			</g:javascript>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            	<s:info />
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.code.label" default="Code" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "code")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.phone.label" default="Phone" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "phone")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.fax.label" default="Fax" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "fax")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.addressLine1.label" default="Address Line1" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "addressLine1")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.addressLine2.label" default="Address Line2" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "addressLine2")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.city.label" default="City" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "city")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.state.label" default="State" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "state")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="vendor.zip.label" default="Zip" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: vendorInstance, field: "zip")}</td>
                            
                        </tr>
                                                            
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${vendorInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
            <div id="tabs" style="margin-top:10px">
            	<ul>
            		<li><a href="#tabs-1">Support Staff</a></li>
            		<li><a href="#tabs-2">Notes</a></li>
            	</ul>
            	<div id="tabs-1">

              </div>
            	<div id="tabs-2">
            	
            	</div>
            </div>
        </div>
    </body>
</html>
