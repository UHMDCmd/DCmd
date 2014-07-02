<%@ page import="edu.hawaii.its.dcmd.inf.Contract" %>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contract.label', default: 'Contract')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <jqDT:resources jqueryUi="true" type="js" />
        <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

        <g:applyLayout name="breadcrumb_bar">
            <g:include controller="person" action="setBreadCrumbForCurrentItem"/>
        </g:applyLayout>
    </head>
    <body>
    <div id="container">
        <g:render template="../show_secondary" model="[pageType:'contract', objectId:0, action:'list']" />
        %{--<g:render template="../breadcrumbs" model="[pageType:'application', action:'list']"/>--}%

    </div>
    <div class="pageBody" id="outerElement">

        <h1>Contracts List Grid</h1>
        %{--<g:render template="../toolTip" />--}%

        %{--<g:render template="listGrid" />--}%
    </div>
    </body>
</html>
