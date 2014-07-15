
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

<%@ page import="edu.hawaii.its.dcmd.inf.PowerSource"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'source.show.label', default: 'Source')}" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'source']"/>
    </g:applyLayout>

</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'source', objectId:0, action:'show']" />
    <g:render template="../breadcrumbs" model="[pageType:'source', action:'show']"/>


</div>

<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">

            <s:info/>
            <g:render template='dialog_show'/>
            <div class="clear"></div>

        </div> <!-- end pagebody -->

    <g:render template="tabs"/>
    </article>
</div>
</body>
</html>

