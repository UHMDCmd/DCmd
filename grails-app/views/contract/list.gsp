
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'contract.id.label', default: 'Id')}" />
                        
                            <th><g:message code="contract.contractStatus.label" default="Contract Status" /></th>
                        
                            <th><g:message code="contract.taxRate.label" default="Tax Rate" /></th>
                        
                            <g:sortableColumn property="uhContractNo" title="${message(code: 'contract.uhContractNo.label', default: 'Uh Contract No')}" />
                        
                            <g:sortableColumn property="uhContractTitle" title="${message(code: 'contract.uhContractTitle.label', default: 'Uh Contract Title')}" />
                        
                            <g:sortableColumn property="contractBeginDate" title="${message(code: 'contract.contractBeginDate.label', default: 'Contract Begin Date')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${contractInstanceList}" status="i" var="contractInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${contractInstance.id}">${fieldValue(bean: contractInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: contractInstance, field: "contractStatus")}</td>
                        
                            <td>${fieldValue(bean: contractInstance, field: "taxRate")}</td>
                        
                            <td>${fieldValue(bean: contractInstance, field: "uhContractNo")}</td>
                        
                            <td>${fieldValue(bean: contractInstance, field: "uhContractTitle")}</td>
                        
                            <td><g:formatDate date="${contractInstance.contractBeginDate}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${contractInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
