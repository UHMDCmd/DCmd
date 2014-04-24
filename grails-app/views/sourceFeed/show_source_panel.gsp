
<%@ page import="edu.hawaii.its.dcmd.inf.PowerSource"%>
<html>
<head>

    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'source.show.label', default: 'Source')}" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'source']"/>
    </g:applyLayout>

</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'source', objectId:0, action:'show']" />
    <g:render template="../breadcrumbs" model="[pageType:'source', action:'show']"/>


</div>

<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">

            <s:info/>
            <g:render template='dialog_show'/>
            <div class="clear"></div>

        </div> <!-- end pagebody -->

    <g:render template="tabs"/>
    </article>
</div>
</body>
</html>

