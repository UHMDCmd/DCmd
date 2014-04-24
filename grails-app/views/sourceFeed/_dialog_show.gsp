<%@ page import="edu.hawaii.its.dcmd.inf.PowerSource" %>

<div class="dialog">
    <table>
        <tbody>

        <tr class="prop">
            <td valign="top"class="name">
               Source Name
            </td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: 'itsId')}


            </td>
        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="source.id.label" default="Id" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "id")}
            </td>
        </tr>
        <tr class="prop">
            <td valign="top" class="name">Data Center</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "dataCenter")}
            </td>

        </tr>
        <tr class="prop">
            <td valign="top" class="name">Capacity</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "capacity")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name">IP Address</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "ipAddress")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name">SNMP Community</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "SNMP_community")}
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name">Load OID</td>
            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "load_OID")}
            </td>

        </tr>



        %{--  <tr class="prop">
            <td valign="top" class="name"><g:message
                    code="location.lastUpdated.label" default="Last Updated" /></td>

            <td valign="top" class="value">
                ${fieldValue(bean: sourceInstance, field: "lastUpdated")}
            </td>

        </tr>--}%

        </tbody>
    </table>
</div>