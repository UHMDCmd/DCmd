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
    <g:set var="serverType" value="${fieldValue(bean:physicalServerInstance, field: 'serverType')}" />
    <jqDT:resources jqueryUi="true" type="js" />
    <title><g:message code="default.dcmd.label" /></title>

    <meta content="main" name="layout" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>


    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'asset', instance :physicalServerInstance]"/>
    </g:applyLayout>


</head>
<body>


<div id="container">

    <g:render template="../show_secondary" model="[pageType:'physicalServer', assetType: 'server', objectId:physicalServerId, action:'show', assetName: physicalServerInstance]" />

    <g:render template="../breadcrumbs" model="[pageType:'asset', action:'show']"/>

</div>



<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">

            <g:render template="../content_title" model="[entityName: 'Physical Server', code:'default.show.label']" />
            <s:info/>

            <g:render template='dialog_show' model="[physicalServerId: physicalServerInstance.id, serverType:serverType]"/>
            <div class="clear"></div>

        </div> <!-- end pagebody -->

 <g:render template="tabs" model="[assetType: 'Server', actionName:'show']"/>

    </article>
</div>

</body>




</html>
