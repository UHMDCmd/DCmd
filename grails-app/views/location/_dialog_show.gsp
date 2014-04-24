<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top"class="name">
            Location Name
            </td>
            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: 'locationDescription')}
            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.id.label" default="Id" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "id")}
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name">Address</td>
            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "addr")}
            </td>

        </tr>
        <tr class="prop">
            <td valign="top" class="name">Building</td>
            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "building")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name">Room #</td>
            <td valign="top" class="value">
                ${fieldValue(bean: locationInstance, field: "roomNum")}
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
</div>