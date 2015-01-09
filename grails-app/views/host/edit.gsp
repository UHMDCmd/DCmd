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

<%@ page import="edu.hawaii.its.dcmd.inf.Host"%>
<html>
<head>

    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

</head>
<body>

<g:form method="post" controller="host">
	<%--
		<g:render template="../nav" model="[entityName: entityName]" />
		--%>
	<div id="container">

    <g:hiddenField name="id" value="${hostInstance?.id}" />
    <g:hiddenField name="version" value="${hostInstance?.version}" />

    <g:render template="../show_secondary" model="[pageType:'host', objectId:hostId, action:'edit']" />
    <g:render template="../breadcrumbs" model="[pageType:'host', action:'edit']"/>


    </div>


	<div class="pageBodyNoMargin">
		<article class="module width_full">
			<div class="module_content">

				<g:render template="../content_title"
					model="[entityName: 'Host', code:'default.edit.label']" />
                <div id="vmWarning" style="font-size:16pt; color:red; position:relative; margin-top:-25px; text-align:center;">*Editing VMWare Guest attributes may be overwritten by VCenter</div>
				<dcmd:requiredInputFieldsReminder />
				<s:info />
				<g:hasErrors bean="${hostInstance}">
					<s:errorDiv>
						<s:renderErrors bean="${hostInstance}" as="list" />
					</s:errorDiv>
				</g:hasErrors>

                <g:render template="../toolTip" />
					<g:render template="dialog" />


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
