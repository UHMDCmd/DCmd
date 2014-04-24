
<%@ page import="edu.hawaii.its.dcmd.inf.Location"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'location.show.label', default: 'Location')}" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'location']"/>
    </g:applyLayout>

</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'location', objectId:0, action:'show']" />
    <g:render template="../breadcrumbs" model="[pageType:'location', action:'show']"/>


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

