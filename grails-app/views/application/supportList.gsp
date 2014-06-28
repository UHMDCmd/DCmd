
<%@ page import="edu.hawaii.its.dcmd.inf.Application" %>
<html>
    <head>
        <export:resource/>
        <meta content="main" name="layout" />
        <g:set var="entityName" value="${message(code: 'host.label', default: 'Application')}" />
        <title><g:message code="default.dcmd.label" /></title>
        <jqDT:resources jqueryUi="true" type="js" />
        <script language="javascript" type="text/javascript" src="../js/mustache.js">
        </script>
        <g:applyLayout name="breadcrumb_bar">
            <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'host']"/>
        </g:applyLayout>


    </head>
      <body>
    
   <div id="container">
       <g:render template="../show_secondary" model="[pageType:'supportList', objectId:0, action:'supportList']" />
       <g:render template="../breadcrumbs" model="[pageType:'host', action:'list']"/>


   </div>
 <div class="pageBody" id="outerElement">
     <g:render template="../toolTip" />
     <g:render template="supportListGrid" />


 </div>


		
</body>
	</html>
