<%@ page import="edu.hawaii.its.dcmd.inf.Tier"%>
<html>
    <head>
        <r:require modules='footer, tabletools' />

        <meta content="main" name="layout" />
        <g:set var="entityName" value="${message(code: 'tier.label', default: 'Software Instance')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <jqDT:resources jqueryUi="true" type="js" />
        <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

        <g:applyLayout name="breadcrumb_bar">
            <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'tier']"/>
        </g:applyLayout>

    </head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'tier', objectId:0, action:'list']" />
    <g:render template="../breadcrumbs" model="[pageType:'tier', action:'list']"/>


</div>
<div class="pageBody" id="outerElement">
    <g:render template="../toolTip" />

    <g:render template="listGrid" />
</div>

</body>
</html>
