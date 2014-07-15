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
    <g:applyLayout name="main">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName"
               value="${message(code: 'asset.label', default: 'Asset')}" />
        <g:javascript library="asset" />
        <calendar:resources lang="en" theme="blue" />
        <title><g:message code="default.create.label"
                          args="[entityName]" /></title>

        <jqgrid:resources />
    </g:applyLayout>
</head>
<body>
<g:form action="save">
    <g:javascript>
        $(function() {
            var ownerTypes = [
                "Leased",
                "Perpetual",
            ];
            $( "#ownerTypes" ).autocomplete({
                source: ownerTypes
            });
        });
    </g:javascript>

    <div id="container">
        <section id="secondary_bar">

            <div class="user">
                <p>John Doe</p>
            </div>

            <div class="breadcrumbs_container">

                <article class="actionsbar">
                <%--<g:form controller="asset">
                                --%><a class="home_button" href="${createLink(uri: '/')}">DCMD
                Home</a>
                    <div class="breadcrumb_divider"></div>

                    <span class="save_button"><g:submitButton name="create"
                                                              class="save"
                                                              value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>



                    <%--<span class="delete_button"><g:actionSubmit class="delete"
                                         action="delete"
                                         value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                         onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                                 <g:hiddenField name="id" value="${assetInstance?.id}" />
                             --%>
                </article>
            </div>

        </section>
    </div>
    <div class="pagebodynomargin">
        <article class="module width_full">
            <div class="module_content">
                <h1>
                    <g:message code="default.create.label" args="[entityName]" />
                </h1>
                <dcmd:requiredInputFieldsReminder />
                <s:info />
                <g:hasErrors bean="${assetInstance}">
                    <s:errorDiv>
                        <s:renderErrors bean="${assetInstance}" as="list" />
                    </s:errorDiv>
                </g:hasErrors>
                <g:render template="../toolTip" />

                <g:render template="dialog" />



            </div>
        </article>

    </div>

</g:form>
</body>
</html>