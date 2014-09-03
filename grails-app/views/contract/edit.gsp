
%{--
  - Copyright (c) 2014 University of Hawaii
  -
  - This file is part of DataCenter metadata (DCmd) project.
  -
  - DCmd is free software: you can redistribute it and/or modify
  - it under the terms of the GNU General Public License as published by
  - the Free Software Foundation, either version 3 of the License, or
  - (at your option) any later version.
  -
  - DCmd is distributed in the hope that it will be useful,
  - but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with DCmd.  It is contained in the DCmd release as LICENSE.txt
  - If not, see <http://www.gnu.org/licenses/>.
  --}%

<%@ page import="edu.hawaii.its.dcmd.inf.Contract"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="main" />
	<g:set var="entityName" 		value="${message(code: 'contract.label', default: 'Contract')}" />
	<title><g:message code="default.edit.label" args="[entityName]" /></title>
	<style>
		.ui-autocomplete-loading {
			background: white url('images/spinner.gif') right center no-repeat;
		}		
		.ui-button {
			margin-left: -1px;
		}		
		.ui-button-icon-only .ui-button-text {
			padding: 0.35em;
		}		
		.ui-autocomplete-input {
			margin: 0;
			padding: 0.48em 0 0.47em 0.45em;
		}
	</style>
	<g:javascript>
		//create tabs
		$(function() {
			$( "#tabs" ).tabs({
			});
		});		
		
		$(document).ready(function() {
		addStyles();
	});
	
	/*
	 * This function also called from the requiredRenewalForms.js after
	 * ajax call to add/remove a form.
	 */
	function addStyles(){
		$("#requiredRenewalFormsTable").css({borderCollapse:"collapse"});
		$("#requiredRenewalFormsTable th").addClass("ui-state-default");
		$("#requiredRenewalFormsTable td").addClass("ui-widget-content");	
		$("#requiredRenewalFormsTable tr").hover(function(){
				$(this).children("td").addClass("ui-state-hover");
			},
			function(){
				$(this).children("td").removeClass("ui-state-hover");
		  });
			$("#editRequiredRenewalFormsTable tr").click(function(){		   
				$(this).children("td").toggleClass("ui-state-highlight");
			});	
	}				
	</g:javascript>
</head>
<body>
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
		<span class="menuButton">
			<g:link class="create" action="create">
				<g:message code="default.new.label" args="[entityName]" />
			</g:link>
		</span>
	</div>
	<div class="body">
		<h1>			
			<g:message code="default.edit.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<s:info />
		</g:if>
		<g:hasErrors bean="${contractInstance}">
			<s:errorDiv>
				<s:renderErrors bean="${contractInstance}" as="list" />
			</s:errorDiv>
		</g:hasErrors>
		<g:form method="post">
			<g:hiddenField name="id" value="${contractInstance?.id}" />
			<g:hiddenField name="version" value="${contractInstance?.version}" />
			<g:hiddenField name="type" value="${contractInstance?.type}" />
			<g:render template="dialog"/>
			<div class="buttons">
				<span class="button"><g:actionSubmit class="save"
						action="update"
						value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</span> <span class="button"><g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</span>
			</div>
		</g:form>
		<div id="tabs" style="margin-top: 10px">
			<ul>
				<li><a href="#tabs-1">Payment History</a>
					</li>
					<li><a href="#tabs-2">Support Staff</a>
					</li>
					<li><a href="#tabs-3">Contract Features</a>
					</li>
					<li><a href="#tabs-4">Required Forms</a>
					</li>
					<li><a href="#tabs-5">Purchased Assets</a>
					</li>
					<li><a href="#tabs-6">Assets On Maintenance</a>
					</li>
					<li><a href="#tabs-7">Notes</a>
					</li>
			</ul>
				<div id="tabs-1">
				<g:render template="contractPaymentHistoryAjax" />
				<g:render template="contractPaymentHistoryTable" />
				</div>
		
			<div id="tabs-2">

			</div>
			<div id="tabs-3">
			</div>
			<div id="tabs-4">
			<g:render template="requiredRenewalFormsTable" model="['contractInstance':contractInstance]"/>
			</div>
			<div id="tabs-5">
			</div>
			<div id="tabs-6">
			</div>
			<div id="tabs-7">
			</div>
			
		</div>
	</div>
	<g:render template='requiredRenewalForm'
		model="['requiredRenewalForm':null,'i':'_clone','hidden':true]" />
</body>
</html>

