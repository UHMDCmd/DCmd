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
        <td valign="top" class="name"><label for="cluster"><g:message
                code="cluster.name.label" default="Cluster Name" /></label></td>
        <td valign="top"
            class="value ${hasErrors(bean: clusterInstance, field: 'name', 'errors')}">
            <g:textField name="clusterName" value="${fieldValue(bean: clusterInstance, field: 'name')}"/>
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