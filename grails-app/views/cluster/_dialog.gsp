<div class="dialog">
<table>
<tbody>

<tr class="prop">
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

<g:message
            code="cluster.id.label" default="Id" /></td>

    <td valign="top" class="value">
        ${fieldValue(bean: clusterInstance, field: "id")}
    </td>

</tr>

    <tr class="prop">
        <td valign="top" class="name"><label for="cluster"><g:message
                code="cluster.name.label" default="Cluster Name" /></label></td>
        <td valign="top"
            class="value ${hasErrors(bean: clusterInstance, field: 'name', 'errors')}">
            <g:textField name="clusterName" value="${fieldValue(bean: clusterInstance, field: 'name')}"/>
        </td>
    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message
                code="cluster.dateCreated.label" default="Date Created" /></td>

        <td valign="top" class="value">
        </td>

    </tr>
    <tr class="prop">
        <td valign="top" class="name"><g:message
                code="cluster.lastUpdated.label" default="Last Updated" /></td>

        <td valign="top" class="value"><g:formatDate
                date="${clusterInstance?.lastUpdated}" /></td>

    </tr>


    </tbody>
</table>
</div>