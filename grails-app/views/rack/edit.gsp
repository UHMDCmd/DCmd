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


    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'asset.edit.label', default: 'Edit Asset')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
</head>
<body>

<g:form method="post" controller="asset">
<%--
        <g:render template="../nav" model="[entityName: entityName]" />
        --%>
    <div id="container">
        <section id="secondary_bar">

            <div class="user">
                <p>Ben Karsin</p>
            </div>

            <div class="breadcrumbs_container">

                <article class="actionsbar">

                    <a class="home_button" href="${createLink(uri: '/')}">DCMD Home</a>

                    <div class="breadcrumb_divider"></div>


                    <g:hiddenField name="id" value="${assetInstance?.id}" />
                    <g:hiddenField name="version" value="${assetInstance?.version}" />

                    <span class="update_button"> <g:actionSubmit class="save"
                                                                 action="update"
                                                                 value="${message(code: 'default.button.update.label', default: 'Update')}" />
                    </span>


                    <span class="delete_button"> <g:actionSubmit class="delete"
                                                                 action="delete"
                                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </span>


                </article>
            </div>

        </section>

    </div>


    <div class="pageBodyNoMargin">
        <article class="module width_full">
            <div class="module_content">

                <g:render template="../content_title"
                          model="[entityName: entityName, code:'default.edit.label']" />
                <dcmd:requiredInputFieldsReminder />
                <s:info />
                <g:hasErrors bean="${assetInstance}">
                    <s:errorDiv>
                        <s:renderErrors bean="${assetInstance}" as="list" />
                    </s:errorDiv>
                </g:hasErrors>
                <g:render template="../toolTip" />
                <g:render template="dialog" />


                <g:render template="tabs" />
            </div>
        </article>
    </div>

</g:form>

</body>
</html>