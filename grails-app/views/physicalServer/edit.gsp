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

<%@ page import="edu.hawaii.its.dcmd.inf.PhysicalServer"%>
<html>
<head>
    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'physicalServer.edit.label', default: 'Edit Server')}" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
    <r:require modules='message_alert' />

</head>
<body>

<g:form method="post" controller="physicalServer">
%{--    <g:set var="physicalServerInstance" value="${physicalServerInstance}" /> --}%
<%--
    <g:render template="../nav" model="[entityName: entityName]" />
    --%>
    <div id="container">
        <g:hiddenField name="id" value="${physicalServerInstance?.id}" />
        <g:hiddenField name="version" value="${physicalServerInstance?.version}" />
        <g:render template="../show_secondary" model="[pageType:'physicalServer', objectId:physicalServerId, action:'edit', params:params]" />
        <g:render template="../breadcrumbs" model="[pageType:'physicalServer', action:'edit']"/>
    </div>


    <div class="pageBodyNoMargin">
        <article class="module width_full">
            <div class="module_content">

                <g:render template="../content_title"
                          model="[entityName:'Server', code:'default.edit.label']" />
                <div id="vmWarning" style="font-size:16pt; color:red; position:relative; margin-top:-25px; text-align:center;">*Editing VMWare Host attributes may be overwritten by VCenter</div>
                <dcmd:requiredInputFieldsReminder />
                <s:info />
                <g:hasErrors bean="${physicalServerInstance}">
                    <s:errorDiv>
                        <s:renderErrors bean="${physicalServerInstance}" as="list" />
                    </s:errorDiv>
                </g:hasErrors>

              %{--  <g:render template="../toolTip" /> --}%
                <g:render template="dialog" model="[action:'edit']"/>

            </div>
        </article>
    </div>

</g:form>


<div class="pageBodyNoMargin" style="margin-top:-125px">
    <article class="module width_full">
        <div class="module_content">
            <g:render template="tabs" model="[actionName:'edit']"/>
        </div>
    </article>
</div>


</body>
</html>
