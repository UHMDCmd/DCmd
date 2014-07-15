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

<%@ page import="edu.hawaii.its.dcmd.inf.Asset"%>
<html>
	<head>

        <r:require modules='footer, tabletools' />

        <meta content="main" name="layout" />
	    	<g:set var="entityName" value="${message(code: 'asset.label', default: 'Asset')}" />
        <title><g:message code="default.dcmd.label" /></title>
        <jqDT:resources jqueryUi="true" type="js" />

        <meta content="main" name="layout" />
        <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

        <g:applyLayout name="breadcrumb_bar">
            <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'asset', instance :assetInstance]"/>
        </g:applyLayout>

    </head>
    <body>

      <div id="container">
       <g:render template="../show_secondary" model="[pageType:'asset', objectId:0, action:'list']" />

       <g:render template="../breadcrumbs" model="[pageType:'asset', action:'list']"/>


      </div>
 <div class="pageBody" id="outerElement">
     <g:render template="../toolTip" />
     <g:render template="listGrid" />
	</div>


</body>
	</html>
