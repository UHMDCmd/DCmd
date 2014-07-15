
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

<%@ page import="edu.hawaii.its.dcmd.inf.PowerSource" %>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'power.label', default: 'Power')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

    %{--pop up for row select--}%
    <style>
    .toggler { width: 500px; height: 200px; position: absolute}
    #button { padding: .5em 1em; text-decoration: none; }
    #effect { width: 240px; height: 135px; padding: 0.4em; position: absolute; float: left }
    #effect h3 { margin: 0; padding: 0.4em; text-align: center; }
    </style>


    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'power']"/>
    </g:applyLayout>

</head>
<body>

<div id="container" >
    <g:render template="../show_secondary" model="[pageType:'power', objectId:0, action:'list']" />
    <g:render template="../breadcrumbs" model="[pageType:'power', action:'list']"/>

</div>

<div class="pageBody"  id="outerElement">


%{--<g:form action="updatePersonsWithLDAP">--}%
%{--<g:submitButton name="refreshList" value="Refresh List"></g:submitButton>--}%
%{--</g:form>--}%
    <g:render template="../toolTip" />

    <g:render template="ITCPower"/>
%{--    <g:render template="listGrid" /> --}%

    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

</div>

</body>
</html>
