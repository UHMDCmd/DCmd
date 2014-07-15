
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contract.label', default: 'Contract')}" />
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
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.requiredRenewalForms.label" default="Required Renewal Forms" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${contractInstance.requiredRenewalForms}" var="r">
                                    <li><g:link controller="contractFormType" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.contractFeatures.label" default="Contract Features" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${contractInstance.contractFeatures}" var="c">
                                    <li><g:link controller="contractFeatureType" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.contractStatus.label" default="Contract Status" /></td>
                            
                            <td valign="top" class="value"><g:link controller="contractStatus" action="show" id="${contractInstance?.contractStatus?.id}">${contractInstance?.contractStatus?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.taxRate.label" default="Tax Rate" /></td>
                            
                            <td valign="top" class="value"><g:link controller="hawaiiTaxRate" action="show" id="${contractInstance?.taxRate?.id}">${contractInstance?.taxRate?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.uhContractNo.label" default="Uh Contract No" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "uhContractNo")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.uhContractTitle.label" default="Uh Contract Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "uhContractTitle")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.contractBeginDate.label" default="Contract Begin Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${contractInstance?.contractBeginDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.contractEndDate.label" default="Contract End Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${contractInstance?.contractEndDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.contractExtensibleEndDate.label" default="Contract Extensible End Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${contractInstance?.contractExtensibleEndDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.annualCost.label" default="Annual Cost" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "annualCost")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.annualRenewalDeadlineMm.label" default="Annual Renewal Deadline Mm" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "annualRenewalDeadlineMm")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.annualRenewalDeadlineDd.label" default="Annual Renewal Deadline Dd" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "annualRenewalDeadlineDd")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.annualRenewalReminderMm.label" default="Annual Renewal Reminder Mm" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "annualRenewalReminderMm")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.annualRenewalReminderDd.label" default="Annual Renewal Reminder Dd" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "annualRenewalReminderDd")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.contractNotes.label" default="Contract Notes" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${contractInstance.notes}" var="c">
                                    <li><g:link controller="contractNotes" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.contractPaymentHistory.label" default="Contract Payment History" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${contractInstance.contractPaymentHistory}" var="c">
                                    <li><g:link controller="contractPaymentHistory" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${contractInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.initialAssetPurchases.label" default="Initial Asset Purchases" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${contractInstance.initialAssetPurchases}" var="i">
                                    <li><g:link controller="asset" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${contractInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.maintenanceRenewals.label" default="Maintenance Renewals" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${contractInstance.maintenanceRenewals}" var="m">
                                    <li><g:link controller="asset" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.type.label" default="Type" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "type")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="contract.vendorContractNo.label" default="Vendor Contract No" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: contractInstance, field: "vendorContractNo")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${contractInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
                
                   <div id="tabs" style="margin-top:10px">
            	<ul>
            		<li><a href="#tabs-1">Payment History</a></li>
            		<li><a href="#tabs-2">Support Staff</a></li>
            		<li><a href="#tabs-3">Contract Features</a></li>
            		<li><a href="#tabs-4">Required Forms</a></li>
            		<li><a href="#tabs-5">Purchased Assets</a></li>
            		<li><a href="#tabs-6">Assets on Maint</a></li>
            	    <li><a href="#tabs-7">Notes</a></li>
            	
            	</ul>
				<div id="tabs-1">
				<g:render template="contractPaymentHistoryTable" />
				</div>

				<div id="tabs-2">

				</div>
				<div id="tabs-3">
				
				<%--<g:render template="../dcmd/contractFeatureType/show"></g:render>
				--%>
				</div>
				
				<div id="tabs-4">
				<g:render template="requiredRenewalFormsTable" />
				
				</div>
				<div id="tabs-5"></div>
				<div id="tabs-6"></div>
				<div id="tabs-7"></div>

			</div>
        </div>
    </body>
</html>

