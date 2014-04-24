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