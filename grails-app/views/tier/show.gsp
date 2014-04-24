<%@ page import="edu.hawaii.its.dcmd.inf.Tier"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'tier.show.label', default: 'Software Instance')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'tier']"/>
    </g:applyLayout>

</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'tier', objectId:0, action:'show']" />

    <g:render template="../breadcrumbs" model="[pageType:'tier', action:'show']"/>

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