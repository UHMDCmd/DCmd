

<%@ page import="edu.hawaii.its.dcmd.inf.Vendor"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'vendor.label', default: 'Vendor')}" />
<title><g:message code="default.create.label" args="[entityName]" />
</title>
</head>
<body>
	<g:javascript>
		//create tabs
		$(function() {
			$( "#tabs" ).tabs({
			});
		});		
	</g:javascript>	
	<div class="nav">
		<span class="menuButton">
			<a class="home" href="${createLink(uri: '/')}">
				<g:message code="default.home.label" />
		</a>
		</span> 
		<span class="menuButton">
			<g:link class="list" action="list">
				<g:message code="default.list.label" args="[entityName]" />
			</g:link>
		</span>
	</div>
	<div class="body">
		<h1>
			<g:message code="default.create.label" args="[entityName]" />
		</h1>
		<s:info/>
		<g:hasErrors bean="${vendorInstance}">
			<s:errorDiv>
				<s:renderErrors bean="${vendorInstance}" as="list" />
			</s:errorDiv>
		</g:hasErrors>
		<g:form action="save">
			<g:render template="dialog" />
			<div class="buttons">
				<span class="button"><g:submitButton name="create"
						class="save"
						value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</span>
			</div>
			<div id="tabs" style="margin-top: 10px">
				<ul>
					<li><a href="#tabs-1">Support Staff</a>
					</li>
					<li><a href="#tabs-2">Notes</a>
					</li>
				</ul>
				<div id="tabs-1">

				</div>
				<div id="tabs-2">

			</div>	
			</div>	
		</g:form>

	</div>
</body>
</html>
