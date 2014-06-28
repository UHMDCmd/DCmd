<%@ page import="edu.hawaii.its.dcmd.inf.Location"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'location.label', default: 'Location')}" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'location']"/>
    </g:applyLayout>

</head>
<body>



<div id="container">
    <g:render template="../show_secondary" model="[pageType:'location', objectId:0, action:'list']" />
    <g:render template="../breadcrumbs" model="[pageType:'location', action:'list']"/>

</div>
<div class="pageBody" id="outerElement">
    <g:render template="../toolTip" />

    <g:render template="listGrid" />
</div>


</body>
</html>
