<div class="dialog">

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name">%{--
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

<g:message code="service.title.label" default="Service Name" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "serviceTitle")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.appEnv.label" default="Environment" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "env")}</td>
            </tr>

            <tr>
                <td valign="top" class="name"><g:message code="service.id.label" default="Id" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "id")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.serviceDescription.label" default="Description" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "serviceDescription")}</td>

            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.serviceStatus.label" default="Status" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "status")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.application.label" default="Application" /></td>
                <td>
                    <a href="../application/show?id=${serviceInstance.application?.id}">
                        ${serviceInstance?.application?.toString().encodeAsHTML()}</a></td>
            </tr>
        </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="service.dateCreated.label" default="Date Created" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "dateCreated")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.lastUpdated.label" default="Last Updated" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "lastUpdated")}</td>
            </tr>
        </table>
    </div>
    <br />
</div>
			