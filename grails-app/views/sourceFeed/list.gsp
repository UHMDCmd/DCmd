<%@ page import="edu.hawaii.its.dcmd.inf.PowerSource"%>
<html>
<head>

    <r:require modules='footer,tabletools' />

    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

    <meta content="main" name="layout" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'sourceFeed', sourceInstance :sourceInstance]"/>
    </g:applyLayout>


</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'sourceFeed', objectId:0, action:'list']" />

    <g:render template="../breadcrumbs" model="[pageType:'sourceFeed', action:'list']"/>


</div>
<div class="pageBody" id="outerElement">
    <g:render template="../toolTip" />
    <g:render template="listGrid" />
</div>


</body>
</html>
