

<%@ page import="edu.hawaii.its.dcmd.inf.Host" %>
<html>
<head>
    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

</head>
    <body>

    <g:form action="save" >
      	<div id="container">
              <g:render template="../show_secondary" model="[pageType:'host', objectId:hostId, action:'create']" />
              <g:render template="../breadcrumbs" model="[pageType:'host', action:'create']"/>

          </div>
		
       	<div class="pageBodyNoMargin">
	    <article class="module width_full">
				<div class="module_content">
            <h1>Create Host</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${hostInstance}">
            <div class="errors">
                <g:renderErrors bean="${hostInstance}" as="list" />
            </div>
            </g:hasErrors>
                    <g:render template="../toolTip" />
                    <g:render template="dialog" model="[action:'create']"/>
           <%--
              <g:render template='hostSupporter' model="['hostsupporter':null,'i':'_clone','hidden':true]"/>
        --%>
        </article>
                </div>
         </g:form>
    </body>
</html>

