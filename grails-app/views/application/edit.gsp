<%@ page import="edu.hawaii.its.dcmd.inf.Application"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'application.edit.label', default: 'Application')}" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
</head>
<body>

<g:form method="post" controller="application">
<%--
        <g:render template="../nav" model="[entityName: entityName]" />
        --%>
    <div id="container">

        <g:hiddenField name="id" value="${applicationInstance?.id}" />
        <g:hiddenField name="version" value="${applicationInstance?.version}" />

        <g:render template="../show_secondary" model="[pageType:'asset', objectId:assetId, action:'edit', params:params]" />
        <g:render template="../breadcrumbs" model="[pageType:'application', action:'edit']"/>


    </div>


    <div class="pageBodyNoMargin">
        <article class="module width_full">
            <div class="module_content">

                <g:render template="../content_title"
                          model="[entityName: entityName, code:'default.edit.label']" />
                <dcmd:requiredInputFieldsReminder />
                <s:info />
                <g:hasErrors bean="${applicationInstance}">
                    <s:errorDiv>
                        <s:renderErrors bean="${applicationInstance}" as="list" />
                    </s:errorDiv>
                </g:hasErrors>
                <g:render template="../toolTip" />
                <g:render template="dialog" model="[applicationId: applicationId]"/>


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
