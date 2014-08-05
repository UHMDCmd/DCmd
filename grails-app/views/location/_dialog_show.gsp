<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top"class="name">
            Location Name
            </td>
            <td valign="top" class="value">
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

${fieldValue(bean: locationInstance, field: 'locationDescription')}
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.id.label" default="Id" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "id")}
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.id.label" default="DCIM dataCenter Id" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "dataCenterID")}
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name">Address</td>
            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "addr")}
            </td>

        </tr>
        <tr class="prop">
            <td valign="top" class="name">Building</td>
            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "building")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name">Room #</td>
            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "roomNum")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.lastUpdated.label" default="Last Updated" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "lastUpdated")}
            </td>

        </tr>
        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.squareFootage.label" default="Square Footage" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "squareFootage")}
            </td>

        </tr>

        </tbody>
    </table>
</div>