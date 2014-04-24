<div class="dialog">

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="service.title.label" default="Service Name" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "serviceTitle")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.appEnv.label" default="Environment" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "env")}</td>
            </tr>

            <tr>
                <td valign="top" class="name"><g:message code="service.id.label" default="Id" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "id")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.serviceDescription.label" default="Description" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "serviceDescription")}</td>

            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.serviceStatus.label" default="Status" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "status")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.application.label" default="Application" /></td>
                <td>
                    <a href="../application/show?id=${serviceInstance.application?.id}">
                        ${serviceInstance?.application?.toString().encodeAsHTML()}</a></td>
            </tr>
        </table>
    </div>

    <div class="show-wrapper">
        <table class="floatTables">
            <tr>
                <td valign="top" class="name"><g:message code="service.dateCreated.label" default="Date Created" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "dateCreated")}</td>
            </tr>
            <tr>
                <td valign="top" class="name"><g:message code="service.lastUpdated.label" default="Last Updated" /></td>
                <td valign="top" class="value">${fieldValue(bean: serviceInstance, field: "lastUpdated")}</td>
            </tr>
        </table>
    </div>
    <br />
</div>
			