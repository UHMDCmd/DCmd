
<%@ page import="edu.hawaii.its.dcmd.inf.Host"%>
<html>
    <head>
        <meta content="main" name="layout" />
        <title><g:message code="default.dcmd.label"/></title>
        <jqDT:resources jqueryUi="true" type="js" />


        <g:applyLayout name="breadcrumb_bar">
            <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'host']"/>
        </g:applyLayout>


 </head>
 <body>
    
    <div id="container">
        <g:render template="../show_secondary" model="[pageType:'host', objectId:0, action:'show']" />

        <g:render template="../breadcrumbs" model="[pageType:'host', action:'show']"/>

    </div>
    
        <div class="pageBodyNoMargin">
        <article class="module width_full">
			<div class="module_content">
				
		<g:render template="../content_title" model="[entityName: 'Host', code:'default.show.label']" />
			<s:info/>
			<g:render template='dialog_show' />
			 		<div class="clear"></div>
			
		
		
	
		
		
		</div> <!-- end pagebody -->
        
        <g:render template="tabs" />
       
		</article>
		</div>
		
	</body>
</html>

