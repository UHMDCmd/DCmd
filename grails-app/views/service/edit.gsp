<%@ page import="edu.hawaii.its.dcmd.inf.Service"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'service.edit.label', default: 'Service')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
</head>
<body>

<g:form method="post" controller="service">
<%--
        <g:render template="../nav" model="[entityName: entityName]" />
        --%>
    <div id="container">

        <g:hiddenField name="id" value="${serviceInstance?.id}" />
        <g:hiddenField name="version" value="${serviceInstance?.version}" />

        <g:render template="../show_secondary" model="[pageType:'service', objectId:serviceId, action:'edit', params:params]" />
        <g:render template="../breadcrumbs" model="[pageType:'service', action:'edit']"/>


    </div>


    <div class="pageBodyNoMargin">
        <article class="module width_full">
            <div class="module_content">

                <g:render template="../content_title"
                          model="[entityName: entityName, code:'default.edit.label']" />
                <dcmd:requiredInputFieldsReminder />
                <s:info />
                <g:hasErrors bean="${serviceInstance}">
                    <s:errorDiv>
                        <s:renderErrors bean="${serviceInstance}" as="list" />
                    </s:errorDiv>
                </g:hasErrors>
                <g:render template="../toolTip" />
                <g:render template="dialog" model="[serviceId: serviceId]"/>


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
