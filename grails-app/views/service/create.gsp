

<%@ page import="edu.hawaii.its.dcmd.inf.Service" %>
<html>
    <head>

        <meta content="main" name="layout" />
        <g:set var="entityName" value="${message(code: 'service.create.label', default: 'Create Service')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <jqDT:resources jqueryUi="true" type="js" />

    </head>
    <body>
       <g:form action="save" >
      	<div id="container">
              <g:render template="../show_secondary" model="[pageType:'service', objectId:serviceId, action:'create']" />
              <g:render template="../breadcrumbs" model="[pageType:'service', action:'create']"/>


          </div>
		
        <div class="pageBodyNoMargin">
        	<article class="module width_full">
				<div class="module_content">
            <h1>Create Service</h1>
            <s:info/>
            <g:hasErrors bean="${serviceInstance}">
            <s:errorDiv>
                <s:renderErrors bean="${serviceInstance}" as="list" />
            </s:errorDiv>
            </g:hasErrors>
            <g:render template="../toolTip" />

            <g:render template="dialog" model="[pageType:'create']"/>
            </div>
        </article>
        </div>
        </g:form>
    </body>
</html>

