
<%@ page import="edu.hawaii.its.dcmd.inf.Rack" %>
<html>
<head>
    <r:require modules='footer, tabletools' />

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'host.label', default: 'Rack')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

    <meta content="main" name="layout" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'asset', instance :assetInstance]"/>
    </g:applyLayout>


</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'asset', objectId:0, action:'list']" />
    <g:render template="../breadcrumbs" model="[pageType:'asset', action:'show']"/>


</div>
<div class="pageBody" id="outerElement">
    <g:render template="../toolTip" />
    <g:render template="listGrid" />
</div>

</body>
</html>
