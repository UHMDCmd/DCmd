<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top"class="name">
                Location Name
            </td>
            <td valign="top"
                class="value %{--
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

${hasErrors(bean: locationInstance, field: 'locationDescription', 'errors')}">
                <g:textField name="locationDescription" maxlength="45"
                             value="${locationInstance?.locationDescription}" title="Please specify a Name for this Location."/>
            </td>
        </tr>

        <tr class="prop" height="25px">
            <td valign="top" class="name"><g:message
                    code="location.id.label" default="DCMD ID" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "id")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.addr.label" default="Address" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: locationInstance, field: 'addr', 'errors')}">
                <g:textField name="addr" maxlength="60" style="width:400px"
                             value="${locationInstance?.addr}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.building.label" default="Building" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: locationInstance, field: 'building', 'errors')}">
                <g:textField name="building" maxlength="45"
                             value="${locationInstance?.building}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.roomNum.label" default="Room #" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: locationInstance, field: 'roomNum', 'errors')}">
                <g:textField name="roomNum" maxlength="45"
                             value="${locationInstance?.roomNum}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.lastUpdated.label" default="Last Updated" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "lastUpdated")}
            </td>

        </tr>

        </tbody>
    </table>
    <g:if test="${action == 'create'}">
        <div class="alert_info">
            - Create Location for more options to become available below -
        </div>
    </g:if>
</div>


