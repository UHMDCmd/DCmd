<div class="dialog">

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="application.id.label" default="Id" /></td>
                <td valign="top" class="value">${fieldValue(bean: applicationInstance, field: "id")}</td>
            </tr>
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
        </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">
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
    <br />
</div>
			