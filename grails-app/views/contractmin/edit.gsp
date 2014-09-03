

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

<%@ page import="edu.hawaii.its.dcmd.inf.Contractmin" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contractmin.label', default: 'Contractmin')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
	<g:javascript>
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

<!-- <g:javascript src="../js/combobox.js" /> -->
<!-- <g:javascript src="../js/requiredRenewalForms.js" />	-->

        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <s:info/>
            <g:hasErrors bean="${contractminInstance}">
            <s:errorDiv>
                <s:renderErrors bean="${contractminInstance}" as="list" />
            </s:errorDiv>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${contractminInstance?.id}" />
                <g:hiddenField name="version" value="${contractminInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
                <div id="tabs">
                    <ul>
                        <li><a href="#tabs-1">Payment History</a>
                        </li>
                        <li><a href="#tabs-2">Support Staff (0)</a></li>
                        <li><a href="#tabs-3">Contract Features</a>
                        </li>
                        <li><a href="#tabs-4">Required Forms (${contractminInstance?.requiredRenewalForms.size()})</a>
                        </li>
                        <li><a href="#tabs-5">Purchased Assets (0)</a></li>
                        <li><a href="#tabs-6">Assets on Maint (0)</a></li>
                        <li><a href="#tabs-7">Notes</a></li>
                    </ul>
                    <div id="tabs-1">
                        <table>
                            <tr>
                                <td>payment history</td>
                            </tr>
                        </table>
                    </div>
                    <div id="tabs-2">
                        <table>
                            <tr>
                                <td>support staff</td>
                            </tr>
                        </table>
                    </div>
                    <div id="tabs-3">
                        <table>
                            <tr>
                                <td>contract features</td>
                            </tr>
                        </table>
                    </div>
                    <div id="tabs-4">
                        <g:render template="requiredRenewalFormsTable"
                            model="['contractminInstance':contractminInstance]" />
                    </div>
                    <div id="tabs-5">
                        <table>
                            <tr>
                                <td>purchased assets</td>
                            </tr>
                        </table>
                    </div>
                    <div id="tabs-6">
                        <table>
                            <tr>
                                <td>assets on maintenance</td>
                            </tr>
                        </table>
                    </div>
                    <div id="tabs-7">
                        <table>
                            <tr>
                                <td>notes</td>
                            </tr>
                        </table>
                    </div>
                </div>
            </g:form>
        </div>
	<!-- Render hidden template(s) for cloning. -->
	<g:render template='requiredRenewalForm'
		model="['requiredRenewalForm':null,'i':'_clone','hidden':true]" />
    </body>
</html>
