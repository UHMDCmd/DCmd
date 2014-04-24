<%@ page import="edu.hawaii.its.dcmd.inf.Cluster"%>
<html>
<head>

    <r:require modules='footer, tabletools' />

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'cluster.label', default: 'Cluster')}" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'asset', instance :assetInstance]"/>
    </g:applyLayout>

</head>
<body>



<div id="container">
    <g:render template="../show_secondary" model="[pageType:'cluster', objectId:0, action:'list']" />
    <g:render template="../breadcrumbs" model="[pageType:'asset', action:'list']"/>

</div>
<div class="pageBody" id="outerElement">
    <g:render template="../toolTip" />

    <g:render template="listGrid" />
</div>


</body>
</html>
