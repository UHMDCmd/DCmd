<%@ page import="edu.hawaii.its.dcmd.inf.Asset"%>

<html>
	<head>
        <g:set var="assetType" value="${fieldValue(bean:assetInstance, field: 'assetType')}" />
        %{--<g:set var="objectId" value="{assetId}" />--}%
        <jqDT:resources jqueryUi="true" type="js" />
        <title><g:message code="default.dcmd.label" /></title>

        <meta content="main" name="layout" />
        <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

        <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'asset', instance :assetInstance]"/>
        </g:applyLayout>


    </head>
    <body>


    <div id="container">

        <g:render template="../show_secondary" model="[pageType:'asset', objectId:assetId, action:'show', assetType: assetType, assetName: assetInstance]" />

        <g:render template="../breadcrumbs" model="[pageType:'asset', action:'show']"/>

        </div>


    
        <div class="pageBodyNoMargin">
        <article class="module width_full">
			<div class="module_content">
				
		<g:render template="../content_title" model="[entityName: assetType.toString(), code:'default.show.label']" />
			<s:info/>

			<g:render template='dialog_show' model="[assetType: assetType.toString(), assetId: assetId]"/>
			 		<div class="clear"></div>

		</div> <!-- end pagebody -->

        <g:render template="tabs" model="[assetType: assetType.toString(), actionName:'show']"/>

		</article>
		</div>

	</body>




</html>

