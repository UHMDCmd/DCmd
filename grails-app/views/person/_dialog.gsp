<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top"class="name">
                UH Username
            </td>
            <td valign="top"
                class="value ${hasErrors(bean: personInstance, field: 'uhName', 'errors')}">
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
                Primary Phone
            </td>
            <td valign="top"
                class="value ${hasErrors(bean: personInstance, field: 'telephone', 'errors')}">
                ${fieldValue(bean:personInstance, field: "telephone")}
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


