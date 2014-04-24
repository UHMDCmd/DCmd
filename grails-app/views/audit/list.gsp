<%@ page import="edu.hawaii.its.dcmd.inf.AuditController"%>
<html>
	<head>

        <r:require modules='footer, tabletools' />
        <export:resource/>
        <meta content="main" name="layout" />
	    	<g:set var="entityName" value="${message(code: 'audit_log.label', default: 'Audit Log')}" />
        <title><g:message code="default.dcmd.label" /></title>
        <jqDT:resources jqueryUi="true" type="js" />

    </head>
    <body>



   <div id="container">
       <g:render template="../show_secondary" model="[pageType:'audit_log', objectId:0, action:'list']" />
       <g:render template="../breadcrumbs" model="[pageType:'audit', action:'list']"/>

   </div>
 <div class="pageBody" id="outerElement">
     <g:render template="../toolTip" />

     <g:render template="listGrid" />



 </div>


</body>
	</html>
