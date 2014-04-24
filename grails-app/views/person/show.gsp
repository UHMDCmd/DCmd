
<%@ page import="edu.hawaii.its.dcmd.inf.Person"%>
<html>
    <head>
        <meta content="main" name="layout" />
        <g:set var="entityName" value="${message(code: 'person.show.label', default: 'Staff')}" />
        <title><g:message code="default.dcmd.label"/></title>
        <jqDT:resources jqueryUi="true" type="js" />

        <g:applyLayout name="breadcrumb_bar">
            <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'person']"/>
        </g:applyLayout>

    </head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'person', objectId:0, action:'show']" />
    <g:render template="../breadcrumbs" model="[pageType:'person', action:'show']"/>


</div>

<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">

            <g:render template="../content_title" model="[entityName: entityName, code:'default.show.label']" />
            <s:info/>
            <g:render template='dialog_show' />
            <div class="clear"></div>

        </div> <!-- end pagebody -->

    <g:render template="tabs"/>
    </article>
</div>
</body>
</html>

