<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.id.label" default="Id" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: hostInstance, field: "id")}
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.hostname.label" default="Hostname" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: hostInstance, field: "hostname")}
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

        <g:if test="${hostInstance.type != 'VMware Guest'}">
        <tr class="prop">
            <td valign="top" class="name">Physical Server</td>

            <td valign="top" class="value">
                <a href="../asset/show?id=${hostInstance.asset?.id}">${hostInstance?.asset?.toString().encodeAsHTML()}</a>
            </td>
        </tr>
        </g:if>
        <g:if test="${hostInstance.type == 'VMware Guest'}">
            <tr class="prop">
                <td valign="top" class="name">Cluster</td>

                <td valign="top" class="value">
                    <a href="../cluster/show?id=${hostInstance.cluster?.id}">
                        ${hostInstance?.cluster?.toString().encodeAsHTML()}
                    </a>
                </td>
            </tr>
        </g:if>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.os.label" default="OS Version" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: hostInstance, field: "os")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.solarisFssShare.label" default="Solaris Fss Share" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: hostInstance, field: "solarisFssShare")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.dateCreated.label" default="Date Created" /></td>

            <td valign="top" class="value"><g:formatDate
                    date="${hostInstance?.dateCreated}" /></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.lastUpdated.label" default="Last Updated" /></td>

            <td valign="top" class="value"><g:formatDate
                    date="${hostInstance?.lastUpdated}" /></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="host.nwaccScan.label" default="Nwacc Scan" /></td>

            <td valign="top" class="value"><g:formatBoolean
                    boolean="${hostInstance?.nwaccScan}" /></td>

        </tr>


        </tbody>
    </table>
</div>