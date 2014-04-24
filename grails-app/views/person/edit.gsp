<%@ page import="edu.hawaii.its.dcmd.inf.Person"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'person.edit.label', default: 'Staff')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
<jqDT:resources jqueryUi="true" type="js" />

        </head>

<body>
<g:form method="post" controller="person">

<div id="container">

    <g:hiddenField name="id" value="${personInstance?.id}" />
    <g:hiddenField name="version" value="${personInstance?.version}" />

    <g:render template="../show_secondary" model="[pageType:'person', objectId:personId, action:'edit']" />
    <g:render template="../breadcrumbs" model="[pageType:'person', action:'edit']"/>



</div>
<div class="pageBodyNoMargin">
<article class="module width_full">
			<div class="module_content">
    <g:render template="../content_title" model="[entityName: entityName, code:'default.edit.label']" />
    <dcmd:requiredInputFieldsReminder/>
    <s:info/>
    <g:hasErrors bean="${personInstance}">
        <s:errorDiv>
            <s:renderErrors bean="${personInstance}" as="list" />
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

