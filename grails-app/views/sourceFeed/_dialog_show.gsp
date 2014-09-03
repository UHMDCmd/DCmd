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

<%@ page import="edu.hawaii.its.dcmd.inf.PowerSource" %>

<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top"class="name">
               Source Name
            </td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: 'itsId')}


            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="source.id.label" default="Id" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "id")}
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name">Data Center</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "dataCenter")}
            </td>

        </tr>
        <tr class="prop">
            <td valign="top" class="name">Capacity</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "capacity")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name">IP Address</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "ipAddress")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name">SNMP Community</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "SNMP_community")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name">Load OID</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "load_OID")}
            </td>

        </tr>



        %{--  <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.lastUpdated.label" default="Last Updated" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "lastUpdated")}
            </td>

        </tr>--}%

        </tbody>
    </table>
</div>