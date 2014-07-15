
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

<%@ page import="edu.hawaii.its.dcmd.inf.ServiceLevelAgreement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'serviceLevelAgreement.label', default: 'ServiceLevelAgreement')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <%-- <g:sortableColumn property="id" title="${message(code: 'serviceLevelAgreement.id.label', default: 'Id')}" /> --%>
                        
                            <g:sortableColumn property="slaTitle" title="${message(code: 'serviceLevelAgreement.slaTitle.label', default: 'Sla Title')}" />
                        
                            <g:sortableColumn property="slaType" title="${message(code: 'serviceLevelAgreement.slaType.label', default: 'Sla Type')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${serviceLevelAgreementInstanceList}" status="i" var="serviceLevelAgreementInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <%-- <td><g:link action="show" id="${serviceLevelAgreementInstance.id}">${fieldValue(bean: serviceLevelAgreementInstance, field: "id")}</g:link></td> --%>
                        
                            <td><g:link action="show" id="${serviceLevelAgreementInstance.id}">${fieldValue(bean: serviceLevelAgreementInstance, field: "slaTitle")}</g:link></td>
                        
                            <td><g:link action="show" id="${serviceLevelAgreementInstance.id}">${fieldValue(bean: serviceLevelAgreementInstance, field: "slaType")}</g:link></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${serviceLevelAgreementInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
