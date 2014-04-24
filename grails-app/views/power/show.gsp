
<%@ page import="edu.hawaii.its.dcmd.inf.PowerSource" %>
<html>
<head>
    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label"/></title>
    <jqDT:resources jqueryUi="true" type="js" />

    <r:require modules="ui,menu"/>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="power" action="setBreadCrumbForCurrentItem" params="[pageType: 'power']"/>
    </g:applyLayout>


</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'power', objectId:0, action:'show']" />

    <g:render template="../breadcrumbs" model="[pageType:'power', action:'show']"/>

</div>

<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">

            <g:render template="../content_title" model="[entityName: 'Power', code:'default.show.label']" />
            <s:info/>


            <g:render template="sourceDialog" />
            <g:render template="panelDialog" />


            <div class="clear"></div>

        </div> <!-- end pagebody -->
    </article>
</div>

</body>
</html>

