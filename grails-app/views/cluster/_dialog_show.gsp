<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="cluster.id.label" default="Id" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: clusterInstance, field: "id")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="cluster.name.label" default="Name" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: clusterInstance, field: "name")}
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