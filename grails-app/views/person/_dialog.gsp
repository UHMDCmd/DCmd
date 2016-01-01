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
<%@  page import="edu.hawaii.its.dcmd.inf.PersonService" %>
<%
    def personService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.PersonService').newInstance()
%>
<r:require modules='select2' />

<script>
    $(document).ready(function() { $("#managerSelect").select2({
        placeholder: 'Not Assigned',
        maximumInputLength: 20,
        width:150,
        initSelection: function(element, callback) {
            var data = {id: "${personInstance.manager?.id}", text: "${personInstance.manager.toString()}"};
            callback(data);
        },
        data: [${personService.listPersonAsSelectWithNull()}]
    }).select2('val', '0');

    });

</script>



<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top"class="name">
                UH Username
            </td>
            <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'uhName', 'errors')}">
                <g:textField name="uhName" maxlength="45"
                             value="${personInstance?.uhName}" title="Please Specify the unique UH Username for this Person." />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="person.id.label" default="Id" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: personInstance, field: "id")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="person.lastName.label" default="Last Name" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: personInstance, field: "lastName")}
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="person.firstName.label" default="First Name" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: personInstance, field: "firstName")}
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="person.midInit.label" default="MI" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: personInstance, field: "midInit")}
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="person.title.label" default="Title" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: personInstance, field: "title")}
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="person.manager.label" default="Manager" /></td>

            <td valign="top" class="value">
                <input type="hidden" name="managerSelect" id="managerSelect" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="person.uhNum.label" default="UH ID" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: personInstance, field: "uhNumber")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top"class="name">
             Primary Email
            </td>
            <td valign="top"
                class="value ${hasErrors(bean: personInstance, field: 'primaryEmail', 'errors')}">
                ${fieldValue(bean:personInstance, field: "primaryEmail")}
            </td>
        </tr>
        <tr class="prop">
            <td valign="top"class="name">
                Alternate Email
            </td>
            <td valign="top"
                class="value ${hasErrors(bean: personInstance, field: 'alternateEmail', 'errors')}">
                <g:textField name="alternateEmail" maxlength="45"
                             value="${personInstance?.alternateEmail}" title="Alternate Email address" />
            </td>
        </tr>
        <tr class="prop">
            <td valign="top"class="name">
                Office Phone
            </td>
            <td valign="top"
                class="value ${hasErrors(bean: personInstance, field: 'telephone', 'errors')}">
                ${fieldValue(bean:personInstance, field: "telephone")}
            </td>
        </tr>
        <tr class="prop">
            <td valign="top"class="name">
                Primary Phone
            </td>
            <td valign="top"
                class="value ${hasErrors(bean: personInstance, field: 'primaryPhone', 'errors')}">
                <g:textField name="primaryPhone" maxlength="45"
                             value="${personInstance?.primaryPhone}" title="Primary Private phone number" />
            </td>
        </tr>
        <tr class="prop">
            <td valign="top"class="name">
                Secondary Phone
            </td>
            <td valign="top"
                class="value ${hasErrors(bean: personInstance, field: 'secondPhone', 'errors')}">
                <g:textField name="secondPhone" maxlength="45"
                             value="${personInstance?.secondPhone}" title="Secondary Private phone number." />
            </td>
        </tr>

        </tbody>
    </table>
    <g:if test="${action == 'create'}">
        <div class="alert_info">
            - UH UserName is needed for autofill on other fields -
        </div>
    </g:if>
</div>


