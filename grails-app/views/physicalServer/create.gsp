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

                <article class="breadcrumbs">
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