<%@ page import="edu.hawaii.its.dcmd.inf.Location"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'location.edit.label', default: 'Location')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

</head>

<body>
<g:form method="post" controller="location">

    <div id="container">

        <g:hiddenField name="id" value="${locationInstance?.id}" />
        <g:hiddenField name="version" value="${locationInstance?.version}" />

        <g:render template="../show_secondary" model="[pageType:'location', objectId:locationId, action:'edit', params:params]" />
        <g:render template="../breadcrumbs" model="[pageType:'location', action:'edit']"/>



    </div>
    <div class="pageBodyNoMargin">
        <article class="module width_full">
            <div class="module_content">
                <g:render template="../content_title" model="[entityName: entityName, code:'default.edit.label']" />
                <dcmd:requiredInputFieldsReminder/>
                <s:info/>
                <g:hasErrors bean="${locationInstance}">
                    <s:errorDiv>
                        <s:renderErrors bean="${locationInstance}" as="list" />
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

