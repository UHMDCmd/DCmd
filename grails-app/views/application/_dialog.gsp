<div class="dialog">

    <div class="show-wrapper">
        <table class="floatTables">

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

<g:if test="${pageType != 'create'}">
                <tr>
                    <td valign="top" class="name"><g:message code="application.id.label" default="Id" /></td>
                    <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "id")}</td>
                </tr>
            </g:if>
            <tr>
                <td valign="top" class="name"><g:message code="application.title.label" default="Application Name" /></td>

                <g:if test="${session.getValue("cloned") == true}">
                    <span style="color: #ff272d;font-size: 14px">Cloned From: ${applicationInstance.applicationTitle}</span>
                    <br>
                    <span style="color: #ff272d; font-size: 14px" >Please Specify a Unique Name For This Entity.</span>
                    <td valign="top" class="value ${hasErrors(bean: applicationInstance, field: 'applicationTitle', 'errors')}">
                    <g:textField name="applicationTitle" maxlength="45" value="" style="border-color: #ff4400"/>
                </g:if>

                <g:else>
                <td valign="top" class="value ${hasErrors(bean: applicationInstance, field: 'applicationTitle', 'errors')}">
                    <g:textField name="applicationTitle" maxlength="45" value="${applicationInstance?.applicationTitle}" title="Please specify a name for this Application."/>
                </td>
                </g:else>
            <tr>
                <td valign="top" class="name"><g:message code="application.applicationEnv.label" default="Environment" /></td>
                <td valign="top"
                    class="value ${hasErrors(bean: applicationInstance, field: 'env', 'errors')}">
                    <g:render template="../environmentSelect" model="[objectInstance:applicationInstance]"/>                </td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.applicationStatus.label" default="Status" /></td>
                <td valign="top"
                    class="value ${hasErrors(bean: applicationInstance, field: 'status', 'errors')}">
                    <g:render template="../statusSelect" model="[objectInstance:applicationInstance]"/>                </td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.applicationDescription.label" default="Description" /></td>
                <td valign="top" class="value ${hasErrors(bean: applicationInstance, field: 'applicationDescription', 'errors')}">
                    <g:textArea name="applicationDescription" style="width:18em; height:4em" cols="30" rows="2" value="${applicationInstance?.applicationDescription}" />
                </td>
            </tr>



        </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">

            <g:if test="${pageType != 'create'}">
            <tr>
                <td valign="top" class="name"><g:message code="application.dateCreated.label" default="Date Created" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "dateCreated")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.lastUpdated.label" default="Last Updated" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "lastUpdated")}</td>
            </tr>
            </g:if>
            <g:if test="${pageType == 'create'}">
                <tr class="prop">
                    <td valign="top" class="name"><label for="primarySA"><g:message
                            code="host.primarySA.label" default="Primary SA" /></label></td>
                    <td valign="top" class="value">
                <g:render template="../personSelect" model="[objectInstance:applicationInstance]"/>

                    </td>
                </tr>
            </g:if>
        </table>

    </div>
    <br />
    <g:if test="${pageType == 'create'}">
        <div class="alert_info">
            - Create Application for more options to become available below -
        </div>
    </g:if>
</div>

