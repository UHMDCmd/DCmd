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

<%@  page import="edu.hawaii.its.dcmd.inf.PersonService" %>
<%
    def personService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.PersonService').newInstance()
%>

<div class="dialog">

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="tier.title.label" default="Software Instance Name" /></td>
                <td valign="top" class="value">${fieldValue(bean: tierInstance, field: "tierName")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="tier.id.label" default="Id" /></td>
                <td valign="top" class="value">${fieldValue(bean: tierInstance, field: "id")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="tier.tierDescription.label" default="Description" /></td>
                <td valign="top" class="value">${fieldValue(bean: tierInstance, field: "tierDescription")}</td>

            </tr>

            <tr>
                <td valign="top" class="name"><g:message code="tier.application.label" default="Application" /></td>
                <td>
                    <a href="../application/show?id=${tierInstance.mainApp?.id}">${tierInstance.mainApp.toString().encodeAsHTML()}</a>
                </td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="tier.appEnv.label" default="App Environment" /></td>
                <td>${tierInstance?.mainApp?.env.toString().encodeAsHTML()}</td>
            </tr>


            <tr>
                <td valign="top" class="name"><g:message code="tier.appAdmin.label" default="App Admin" /></td>
                <td valign="top" class="value">
                    <a href="../person/show?id=${personService.getAdmin(tierInstance.mainApp)?.id}">${personService.getAdmin(tierInstance.mainApp).toString()}</a>
                </td>
            </tr>

        </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="tier.host.label" default="Host" /></td>
                <td>
                    <a href="../host/show?id=${tierInstance.host?.id}">${tierInstance.host.toString().encodeAsHTML()}</a>
                </td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="tier.hostAdmin.label" default="Host Admin" /></td>
                <td valign="top" class="value">
                    <a href="../person/show?id=${personService.getAdmin(tierInstance.host)?.id}">${personService.getAdmin(tierInstance.host).toString()}</a>
                </td>
            </tr>

            <tr>
                <td valign="top" class="name"><g:message code="tier.loadBalance.label" default="Load Balanced" /></td>
                <td valign="top" class="value">${fieldValue(bean: tierInstance, field: "loadBalanced")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="tier.tierType.label" default="Instance Type" /></td>
                <td valign="top" class="value">${fieldValue(bean: tierInstance, field: "type.abbreviation")}</td>
            </tr>

        </table>
    </div>
    <br />
</div>
			