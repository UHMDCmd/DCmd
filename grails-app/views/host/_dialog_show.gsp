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


<script language="javascript" type="text/javascript" src="../js/isotope.pkgd.min.js"></script>
<script language="javascript" type="text/javascript">
    var container = document.querySelector("#container");
    var iso = new Isotope( container, {
        itemSelector:'.item',
        layoutMode:'fitRows'
    });
</script>


<div class="dialog">
<div id="container" class="js-isotope" data-isotope-options='{ "columnWidth":400}'>

    <div class="item">
        <table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr><td colspan="2"><center><b>General Information</b></center></td></tr>
            <tr class="prop">
                <td valign="top" class="name"><div class="ObjTitle"><g:message
                        code="host.hostname.label" default="Hostname" /></div></td>

                <td valign="top" class="value">
                    <div class="ObjTitle">${fieldValue(bean: hostInstance, field: "hostname")}</div>
                </td>

            </tr>
        <tr class="prop">
            <td valign="top" class="name">
<g:message
                    code="host.id.label" default="DCmd ID" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: hostInstance, field: "id")}
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.env.label" default="Environment" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: hostInstance, field: "env")}
            </td>

        </tr>


        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.status.label" default="Status" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: hostInstance, field: "status")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.type.label" default="Type" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: hostInstance, field: "type")}
            </td>

        </tr>
        </table>
    </div>
    <div class="item">
        <table class="floatTables" style="border:1px solid #CCCCCC;">
            <g:if test="${hostInstance.type == 'VMWare'}">
                <tr><td colspan="2"><center><b>VMWare Details (pulled from VCenter)</b></center></td></tr>
                <tr>
                    <td valign="top" class="name">VM State</td>
                    <td valign="top" class="value">${hostInstance.getVCenterStateString()}</td>
                </tr>
                <tr class="prop">
                    <td valign="top" class="name">Cluster</td>

                    <td valign="top" class="value">
                        <a href="../cluster/show?id=${hostInstance.cluster?.id}">
                            ${hostInstance?.cluster?.toString().encodeAsHTML()}
                        </a>
                    </td>
                </tr>
            </g:if>
            <g:if test="${hostInstance.type != 'VMWare'}">
                <tr><td colspan="2"><center><b>Host Details</b></center></td></tr>
            </g:if>
            <tr class="prop">
                <td valign="top" class="name">Physical Server</td>
                <td valign="top" class="value">
                    <a href="../physicalServer/show?id=${hostInstance.asset?.id}">${hostInstance?.asset?.toString().encodeAsHTML()}</a>
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">Max Memory</td>
                <td valign="top" class="value">${hostInstance.maxMemory}</td>
            </tr>
            <tr>
                <td valign="top" class="name">Max CPU</td>
                <td valign="top" class="value">${hostInstance.maxCpu}</td>
            </tr>
            <tr>
                <td valign="top" class="name">IP Address</td>
                <td valign="top" class="value">${hostInstance.ipAddress}</td>
            </tr>
            <tr>
                <td valign="top" class="name">Full Domain</td>
                <td valign="top" class="value">${hostInstance.fullDomain}</td>
            </tr>
            <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.os.label" default="OS Version" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: hostInstance, field: "os")}
            </td>
            <tr class="prop">
                <td valign="top" class="name"><g:message
                        code="host.lastUpdated.label" default="Last Updated" /></td>

                <td valign="top" class="value"><g:formatDate
                        date="${hostInstance?.lastUpdated}" /></td>

            </tr>
        </table>
    </div>
</div>
</div>