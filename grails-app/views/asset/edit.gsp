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
    <g:set var="assetType" value="${fieldValue(bean:assetInstance, field: 'assetType')}" />

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'asset.edit.label', default: 'Edit Asset')}" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
    <r:require modules='message_alert' />

</head>
<body>

<g:form method="post" controller="asset">
	<%--
		<g:render template="../nav" model="[entityName: entityName]" />
		--%>
	<div id="container">
        <g:hiddenField name="id" value="${assetInstance?.id}" />
        <g:hiddenField name="version" value="${assetInstance?.version}" />
        <g:render template="../show_secondary" model="[pageType:'asset', objectId:assetId, action:'edit', params:params]" />
        <g:render template="../breadcrumbs" model="[pageType:'asset', action:'edit']"/>


    </div>


	<div class="pageBodyNoMargin">
		<article class="module width_full">
			<div class="module_content">

				<g:render template="../content_title"
					model="[entityName: assetType.toString(), code:'default.edit.label']" />
				<dcmd:requiredInputFieldsReminder />
				<s:info />
				<g:hasErrors bean="${assetInstance}">
					<s:errorDiv>
						<s:renderErrors bean="${assetInstance}" as="list" />
					</s:errorDiv>
				</g:hasErrors>

                    <g:render template="../toolTip" />
					<g:render template="dialog" model="[assetType: assetType.toString(), action:'edit']"/>

		</div>
		</article>
	</div>

</g:form>


<div class="pageBodyNoMargin" style="margin-top:-125px">
    <article class="module width_full">
        <div class="module_content">
            <g:render template="tabs" model="[assetType: assetType.toString(), actionName:'edit']"/>
            </div>
        </article>
    </div>


</body>
</html>
