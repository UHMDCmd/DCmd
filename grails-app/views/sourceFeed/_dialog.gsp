<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top"class="name">
                Source Name
            </td>
            <td valign="top"
                class="value ${hasErrors(bean: sourceInstance, field: 'label', 'errors')}">
                <g:textField name="label" maxlength="45"
                             value="${sourceInstance?.label}" title="Please specify a Name for this Source."/>
            </td>
        </tr>

        <tr class="prop" height="25px">
            <td valign="top" class="name"><g:message
                    code="source.id.label" default="Id" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "id")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="source.dataCenter.label" default="Data Center" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: sourceInstance, field: 'dataCenter', 'errors')}">
                <g:textField name="Data Center" maxlength="60" style="width:400px"
                             value="${sourceInstance?.dataCenter}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="source.capacity.label" default="capacity" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: sourceInstance, field: 'capacity', 'errors')}">
                <g:textField name="capacity" maxlength="45"
                             value="${sourceInstance?.capacity}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="source.ipAddress.label" default="IP Address" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: sourceInstance, field: 'idAddress', 'errors')}">
                <g:textField name="IP Address" maxlength="45"
                             value="${sourceInstance?.ipAddress}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="source.SNMP_community.label" default="SNMP Community" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "SNMP_community")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="source.load_OID.label" default="Load OID" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "load_OID")}
            </td>

        </tr>


        </tbody>
    </table>
    <g:if test="${action == 'create'}">
        <div class="alert_info">
            - Create Source for more options to become available below -
        </div>
    </g:if>
</div>


