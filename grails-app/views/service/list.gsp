<%@ page import="edu.hawaii.its.dcmd.inf.Service"%>
<html>
    <head>

        <meta content="main" name="layout" />
        <g:set var="entityName" value="${message(code: 'service.label', default: 'Service')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <jqDT:resources jqueryUi="true" type="js" />
        <script language="javascript" type="text/javascript" src="../js/mustache.js">
        </script>
        <g:applyLayout name="breadcrumb_bar">
            <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'service']"/>
        </g:applyLayout>

    </head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'service', objectId:0, action:'list']" />
    <g:render template="../breadcrumbs" model="[pageType:'service', action:'list']"/>


</div>
<div class="pageBody" id="outerElement">
    <g:render template="../toolTip" />

    <g:render template="listGrid" />
</div>

</body>
</html>
