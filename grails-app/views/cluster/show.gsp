
<%@ page import="edu.hawaii.its.dcmd.inf.Cluster"%>
<html>
<head>
    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'cluster.show.label', default: 'Show Cluster')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />


    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'cluster', instance :clusterInstance]"/>
    </g:applyLayout>
</head>
<body>

	<div id="container">
        <g:render template="../show_secondary" model="[pageType:'cluster', objectId:clusterInstance.id, action:'show']" />

        <g:render template="../breadcrumbs" model="[pageType:'cluster', action:'show']"/>

    </div>
	<%--<g:render template="../nav" model="[entityName: entityName]" />
	--%>
	<div class="pageBodyNoMargin">
		<article class="module width_full">
			<div class="module_content">
				<g:render template="../content_title"
					model="[entityName: entityName, code:'default.show.label']" />

				<g:render template="dialog_show" />
			</div>
			<%--
        <g:render template="edit_delete_buttons"/>
            --%>

			<g:render template="tabs" />
		</article>
	</div>
</body>
</html>

