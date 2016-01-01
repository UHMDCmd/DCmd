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

            <tr>
                <td valign="top" class="name"><g:message code="application.title.label" default="Application Name" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "applicationTitle")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.title.label" default="Environment" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "env")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.applicationStatus.label" default="Status" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "status")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.applicationDescription.label" default="Description" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "applicationDescription")}</td>

            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.url.label" default="Application URL" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "url")}</td>

            </tr>
        </table>
    </div>
        <div class="item">
            <table class="floatTables" style="border:1px solid #CCCCCC;">
                <tr><td colspan="2"><center><b>Incident Information</b></center></td></tr>
                <tr>
                    <td valign="top" class="name"><g:message code="application.incidentLevel.label" default="Incident Service Level" /></td>
                    <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "incidentServiceLevel")}</td>
                </tr>
                <tr>
                    <td valign="top" class="name"><g:message code="application.incidentEmail.label" default="Incident EMail List" /></td>
                    <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "incidentEmailList")}</td>
                </tr>
            </table>
        </div>

    <div class="item">
        <table class="floatTables" style="border:1px solid #CCCCCC;">
            <tr><td colspan="2"><center><b>Dates</b></center></td></tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.maintenanceWindow.label" default="Maintenance Window" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "maintenanceWindow")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.dateCreated.label" default="Date Created" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "dateCreated")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="application.lastUpdated.label" default="Last Updated" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "lastUpdated")}</td>
            </tr>
        </table>
    </div>
</div>
    <br />
</div>
			