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

<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%@  page import="edu.hawaii.its.dcmd.inf.ApplicationService" %>
<%@  page import="edu.hawaii.its.dcmd.inf.HostService" %>
<%@  page import="edu.hawaii.its.dcmd.inf.PersonService" %>

<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
    def appService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.ApplicationService').newInstance()
    def hostService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.HostService').newInstance()
    def personService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.PersonService').newInstance()

%>
<r:require modules='select2' />

<script>
    $(document).ready(function() { $("#application").select2({
        placeholder: 'Please Select...',
        maximumInputLength: 20,
        width:150,
        initSelection: function(element, callback) {
            var data = {id: "${tierInstance.mainApp?.id}", text: "${tierInstance.mainApp.toString()}"};
            callback(data);
        },
        data: [${appService.listApplicationsAsSelect()}]
    }).select2('val', '0');

    $("#host").select2({
        placeholder: 'Please Select...',
        maximumInputLength: 20,
        width:150,
        initSelection: function(element, callback) {
            var data = {id: "${tierInstance.host?.id}", text: "${tierInstance.host.toString()}"};
            callback(data);
        },
        data: [${hostService.listHostsAsSelect()}]
    }).select2('val', '0');

});

</script>



    <div class="dialog">

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="tier.title.label" default="Software Instance Name" /></td>
                <td valign="top" class="value ${hasErrors(bean: tierInstance, field: 'tierName', 'errors')}">
                    <g:textField name="tierName" maxlength="45" value="${tierInstance?.tierName}" title="Please specify a name for this Software Instance."/>
                </td>
            </tr>
            <g:if test="${pageType != 'create'}">
                <tr>
                    <td valign="top" class="name"><g:message code="tier.id.label" default="Id" /></td>
                    <td valign="top" class="value">${fieldValue(bean: tierInstance, field: "id")}</td>
                </tr>
            </g:if>
            <tr>
                <td valign="top" class="name"><g:message code="tier.tierDescription.label" default="Description" /></td>
                <td valign="top" class="value ${hasErrors(bean: tierInstance, field: 'tierDescription', 'errors')}">
                    <g:textArea name="tierDescription" style="width:14em; height:4em" cols="25" rows="2" value="${tierInstance?.tierDescription}" />
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">
                    <label for="application"><g:message code="tier.application.label" default="Application" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: tierInstance, field: 'mainApp', 'errors')}">
                    <input type="hidden" name="application" id="application" />
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">
                    <label for="environment"><g:message code="tier.environment.label" default="App Environment" /></label>
                </td>
                <td valign="top" class="name">
                <b>${tierInstance?.mainApp?.env.toString().encodeAsHTML()}</b>
            </td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="tier.appAdmin.label" default="App Admin" /></td>
                <td valign="top" class="value">
                    <a href="../person/show?id=${personService.getAdmin(tierInstance.mainApp)?.id}">${personService.getAdmin(tierInstance.mainApp).toString()}</a>
                </td>
            </td>
            </tr>

        </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name">
                    <label for="host"><g:message code="tier.host.label" default="Host" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: tierInstance, field: 'host', 'errors')}">
                    <input type="hidden" name="host" id="host" />
                </td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="tier.hostAdmin.label" default="Host Admin" /></td>
                <td valign="top" class="value">
                    <a href="../person/show?id=${personService.getAdmin(tierInstance.host)?.id}">${personService.getAdmin(tierInstance.host).toString()}</a>
                </td>
            </tr>

            <tr>
                <td valign="top" class="name"><g:message code="tier.loadBalanced.label" default="Load Balanced" /></td>
                <td valign="top" class="name">
                    <g:checkBox name="loadBalanced" value="${tierInstance.loadBalanced}"/>
                </td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="tier.tierType.label" default="Instance Type" /></td>
                <td valign="top" class="name">

                </td>
            </tr>

        </table>

    </div>
    <br />
    <g:if test="${pageType == 'create'}">
        <div class="alert_info">
            - Create Software Instance for more options to become available below -
        </div>
    </g:if>

</div>

