
<%@ page import="edu.hawaii.its.dcmd.inf.User" %>
<html>
<head>
    <r:require modules='footer, tabletools' />

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'host.label', default: 'User')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />


</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'user', objectId:0, action:'list']" />

</div>
<div class="pageBody" id="outerElement">
    <g:render template="../toolTip" />

    <g:render template="listGrid"/>
</div>

</body>
</html>
