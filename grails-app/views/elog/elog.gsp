<%@ page import="edu.hawaii.its.dcmd.inf.Application"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'application.show.label', default: 'Application')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'application', objectId:0, action:'show']" />

</div>

<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">

            <g:render template="../content_title" model="[entityName: entityName, code:'default.show.label']" />
            <s:info/>
            <g:render template='dialog_show' />
            <div class="clear"></div>

        </div> <!-- end pagebody -->

    <g:render template="tabs" model="[actionName:'show']"/>

    </article>
</div>

</body>
</html>

