<%@ page import="edu.hawaii.its.dcmd.inf.Rack"%>
<html>
	<head>


        <meta content="main" name="layout" />
        <g:set var="entityName" value="${message(code: 'rack.show.label', default: 'Show Rack')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <jqDT:resources jqueryUi="true" type="js" />

    </head>
    <body>
    
    <div id="container">

        <g:render template="../show_secondary" model="[pageType:'rack', objectId:rackId, action:'show']" />
    
        <div class="pageBodyNoMargin">
        <article class="module width_full">
			<div class="module_content">
				
		<g:render template="../content_title" model="[entityName: entityName, code:'default.show.label']" />

        <s:info/>
			<g:render template='dialog_show' />
			 		<div class="clear"></div>
		
		</div> <!-- end pagebody -->

        <g:render template="tabs" />
        
		</article>
		</div>
		
	</body>
</html>

