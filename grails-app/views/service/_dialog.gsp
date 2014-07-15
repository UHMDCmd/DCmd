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
<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
    def appService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.ApplicationService').newInstance()
%>
<r:require modules='select2' />

<script>
    $(document).ready(function() { $("#applicationSelect").select2({
        placeholder: 'Please Select...',
        maximumInputLength: 20,
        width:150,
        initSelection: function(element, callback) {
            var data = {id: "${serviceInstance.application?.id}", text: "${serviceInstance.application?.applicationTitle}"};
            callback(data);
        },
        data: [${appService.listApplicationsAsSelect()}]
    }).select2('val', '0');



});

</script>



    <div class="dialog">

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="service.title.label" default="Service Name" /></td>
                <td valign="top" class="value ${hasErrors(bean: serviceInstance, field: 'serviceTitle', 'errors')}">
                    <g:textField name="serviceTitle" maxlength="45" value="${serviceInstance?.serviceTitle}" title="Please specify a Title for this Service."/>
                </td>
            </tr>
            <tr>
                <td valign="top" class="name">
                    <label for="environment"><g:message code="service.environment.label" default="Environment" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: serviceInstance, field: 'env', 'errors')}">
                    <g:render template="../environmentSelect" model="[objectInstance:serviceInstance]"/>
                </td>
            </tr>

            <g:if test="${pageType != 'create'}">
            <tr>
                <td valign="top" class="name"><g:message code="service.id.label" default="Id" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "id")}</td>
            </tr>
            </g:if>
            <tr>
                <td valign="top" class="name"><g:message code="service.serviceDescription.label" default="Description" /></td>
                <td valign="top" class="value ${hasErrors(bean: serviceInstance, field: 'serviceDescription', 'errors')}">
                    <g:textArea name="serviceDescription" style="width:18em; height:4em" cols="30" rows="2" value="${serviceInstance?.serviceDescription}" />
                </td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.serviceStatus.label" default="Status" /></td>
                <td valign="top"
                    class="value ${hasErrors(bean: serviceInstance, field: 'status', 'errors')}">
                    <g:render template="../statusSelect" model="[objectInstance:serviceInstance]"/>                </td>
            </td>
            </tr>
            <tr>
                <td valign="top" class="name">
                    <label for="application"><g:message code="service.application.label" default="Application" /></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: serviceInstance, field: 'application', 'errors')}">
                    <input type="hidden" name="applicationSelect" id="applicationSelect" />
                </td>
            </tr>

        </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">

            <g:if test="${pageType != 'create'}">
            <tr>
                <td valign="top" class="name"><g:message code="service.dateCreated.label" default="Date Created" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "dateCreated")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.lastUpdated.label" default="Last Updated" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "lastUpdated")}</td>
            </tr>
            </g:if>
            <g:if test="${pageType == 'create'}">
                <tr class="prop">
                    <td valign="top" class="name"><label for="primarySA"><g:message
                            code="host.primarySA.label" default="Primary SA" /></label></td>
                    <td valign="top" class="value">
                        <g:render template="../personSelect" model="[objectInstance:serviceInstance]"/>
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

