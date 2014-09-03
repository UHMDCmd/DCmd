

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

<%@ page import="edu.hawaii.its.dcmd.inf.Application" %>
<html>
    <head>

        <meta content="main" name="layout" />
        <g:set var="entityName" value="${message(code: 'application.create.label', default: 'Create Application')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <jqDT:resources jqueryUi="true" type="js" />

    </head>
    <body>
       <g:form action="save" >
      	<div id="container">
              <g:render template="../show_secondary" model="[pageType:'application', objectId:applicationId, action:'create']" />
              <g:render template="../breadcrumbs" model="[pageType:'application', action:'create']"/>


          </div>
		
        <div class="pageBodyNoMargin">
        	<article class="module width_full">
				<div class="module_content">
            <h1>Create Application</h1>
            <s:info/>
            <g:hasErrors bean="${applicationInstance}">
            <s:errorDiv>
                <s:renderErrors bean="${applicationInstance}" as="list" />
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

