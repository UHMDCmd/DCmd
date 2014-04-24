<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top"class="name">
                Location Name
            </td>
            <td valign="top"
                class="value ${hasErrors(bean: locationInstance, field: 'locationDescription', 'errors')}">
                <g:textField name="locationDescription" maxlength="45"
                             value="${locationInstance?.locationDescription}" title="Please specify a Name for this Location."/>
            </td>
        </tr>

        <tr class="prop" height="25px">
            <td valign="top" class="name"><g:message
                    code="location.id.label" default="DCMD ID" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "id")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.addr.label" default="Address" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: locationInstance, field: 'addr', 'errors')}">
                <g:textField name="addr" maxlength="60" style="width:400px"
                             value="${locationInstance?.addr}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.building.label" default="Building" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: locationInstance, field: 'building', 'errors')}">
                <g:textField name="building" maxlength="45"
                             value="${locationInstance?.building}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.roomNum.label" default="Room #" /></td>
            <td valign="top"
                class="value ${hasErrors(bean: locationInstance, field: 'roomNum', 'errors')}">
                <g:textField name="roomNum" maxlength="45"
                             value="${locationInstance?.roomNum}" />
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.lastUpdated.label" default="Last Updated" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "lastUpdated")}
            </td>

        </tr>

        </tbody>
    </table>
    <g:if test="${action == 'create'}">
        <div class="alert_info">
            - Create Location for more options to become available below -
        </div>
    </g:if>
</div>


