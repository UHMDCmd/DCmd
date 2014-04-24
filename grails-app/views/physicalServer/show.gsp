<%@ page import="edu.hawaii.its.dcmd.inf.PhysicalServer"%>
<html>
<head>
    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

</head>
<body>

<div id="container">

    <g:render template="../show_secondary" model="[pageType:'asset', objectId:physicalServerInstance.id, action:'show']" />

</div>

<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">

            <g:render template="../content_title" model="[entityName: 'Physical Server', code:'default.show.label']" />
            <s:info/>

            <g:render template='dialog_show' model="[ assetId: physicalServerInstance.id]"/>
            <div class="clear"></div>

        </div> <!-- end pagebody -->

    <g:render template="tabs" model="[actionName:'show']"/>

    </article>
</div>

</body>
</html>

