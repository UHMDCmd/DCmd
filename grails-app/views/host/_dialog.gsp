%{--
  - Copyright (c) 2014 University of Hawaii
  -
  - This file is part of DataCenter metadata (DCmd) project.
  -
  - DCmd is free software: you can redistribute it and/or modify
  - it under the terms of the GNU General Public License as published by
  - the Free Software Foundation, either version 3 of the License, or
  - (at your option) any later version.
  -
  - DCmd is distributed in the hope that it will be useful,
  - but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with DCmd.  It is contained in the DCmd release as LICENSE.txt
  - If not, see <http://www.gnu.org/licenses/>.
  --}%

<%@  page import="edu.hawaii.its.dcmd.inf.AssetService" %>
<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%
    def assetService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.AssetService').newInstance()
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
%>

<r:require modules='select2' />

<script>
    $(document).ready(function() { $("#assetSelect").select2({
        placeholder: 'Please Select...',
        maximumInputLength: 20,
        width:150,
        initSelection: function(element, callback) {
            var data = {id: "${hostInstance.asset?.id}", text: "${hostInstance.asset.toString()}"};
            callback(data);
        },
        data: [${assetService.listPhysicalServerAsSelect()}]
    }).select2('val', '0');

        $("#clusterSelect").select2({
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            width:150,
            initSelection: function(element, callback) {
                var data = {id: "${hostInstance.cluster?.id}", text: "${hostInstance.cluster.toString()}"};
                callback(data);
            },
            data: [${genService.listClustersAsSelect()}]
        }).select2('val', '0');

        $("#type").select2({
            width:150,
            placeholder: 'Please Select...',
            maximumInputLength: 20,
            initSelection: function(element, callback) {
                var data = {id: "${hostInstance.type}", text: "${hostInstance.type}"};
                callback(data);
            },
            data: [${genService.listHostTypesAsSelect()}]
        }).select2('val', 'Standalone');

        $("#type").on("change", function(e){changeHostType(e);} );
        changeHostType("${hostInstance.type}");

    });


    function changeHostType(type) {

        if(type.val == "VMware Guest" || type == "VMware Guest") {
            document.getElementById("clusterDiv").style.visibility = 'visible';
            document.getElementById("serverDiv").style.visibility = 'hidden';
        }
        else {
            document.getElementById("serverDiv").style.visibility = 'visible';
            document.getElementById("clusterDiv").style.visibility = 'hidden';

        }
    }

</script>

<div class="dialog">
<table>
<tbody>

<tr class="prop">

<td valign="top" class="name"><span style="color: #FF0000;" >*</span>
    <label for="hostname"><g:message
                code="host.hostname.label" default="Hostname" /></label></td>

    <g:if test="${session.getValue("cloned") == true}">
        <span style="color: #ff272d;font-size: 14px">Cloned From: ${hostInstance.hostname}</span>
        <br>
        <span style="color: #ff272d; font-size: 14px" >Please Specify a Unique Name For This Entity.</span>
        <td valign="top" class="value ${hasErrors(bean: hostInstance, field: 'hostname', 'errors')}">
        <g:textField name="hostname" maxlength="45" value="" style="border-color: #ff4400"/>
    </g:if>

    <g:else>
        <td valign="top" class="value ${hasErrors(bean: hostInstance, field: 'hostname', 'errors')}">
            <g:textField name="hostname" maxlength="45" value="${hostInstance?.hostname}" title="This field is Required and must be unique."/>
        </td>
    </g:else>
    %{--
    <td valign="top"
            class="value ${hasErrors(bean: hostInstance, field: 'hostname', 'errors')}">
            <g:textField name="hostname" maxlength="45"
                         value="${hostInstance?.hostname}" title="This field is Required and must be unique." />
        </td>--}%
    </tr>

<tr>
    <td valign="top" class="name">
        <label for="environment"><g:message code="host.environment.label" default="Environment" /></label>
    </td>
    <td valign="top" class="value ${hasErrors(bean: hostInstance, field: 'env', 'errors')}">
        <g:render template="../environmentSelect" model="[objectInstance:hostInstance]"/>
    </td>
</tr>

<tr>
    <td valign="top" class="name"><g:message code="application.applicationStatus.label" default="Status" /></td>
    <td valign="top"
        class="value ${hasErrors(bean: hostInstance, field: 'status', 'errors')}">
        <g:render template="../statusSelect" model="[objectInstance:hostInstance]"/>
    </td>
</tr>

    <tr class="prop">
        <td valign="top" class="name"><label for="type"><g:message
                code="host.type.label" default="Type" /></label></td>
        <td valign="top"
            class="value ${hasErrors(bean: hostInstance, field: 'type', 'errors')}">

            <input type="hidden" name="type" id="type" />
        </td>
    </tr>

    <tr class="prop" id="serverDiv">
        <td valign="top" class="name" id="serverLabel">Physical Server</td>
        <td valign="top"
            class="value ${hasErrors(bean: hostInstance, field: 'asset', 'errors')}">
            <input type="hidden" name="assetSelect" id="assetSelect" />
        </td>
    </tr>

    <tr class="prop" id="clusterDiv">
        <td valign="top" class="name" id="clusterLabel">Cluster</td>
        <td valign="top"
            class="value ${hasErrors(bean: hostInstance, field: 'cluster', 'errors')}">
            <input type="hidden" name="clusterSelect" id="clusterSelect" />
        </td>
    </tr>

    <tr class="prop">
        <td valign="top" class="name"><label for="os"><g:message
                code="host.os.label" default="OS" /></label></td>
        <td valign="top"
            class="value ${hasErrors(bean: hostInstance, field: 'os', 'errors')}">
            <g:textField name="os"
                         value="${fieldValue(bean: hostInstance, field: 'os')}" />
        </td>
    </tr>

    <tr class="prop">
        <td valign="top" class="name"><label for="solarisFssShare"><g:message
                code="host.solarisFssShare.label"
                default="Solaris Fss Share" /></label></td>
        <td valign="top"
            class="value ${hasErrors(bean: hostInstance, field: 'solarisFssShare', 'errors')}">
            <g:textField name="solarisFssShare"
                         value="${fieldValue(bean: hostInstance, field: 'solarisFssShare')}" />
        </td>
    </tr>

    <tr class="prop">
        <td valign="top" class="name"><label for="dateCreated"><g:message
                code="host.dateCreated.label" default="Date Created" /></label></td>
        <td valign="top" class="value"><g:formatDate
                date="${hostInstance?.dateCreated}" /></td>
    </tr>

    <tr class="prop">
        <td valign="top" class="name"><label for="lastUpdated"><g:message
                code="host.lastUpdated.label" default="Last Updated" /></label></td>
        <td valign="top" class="value"><g:formatDate
                date="${hostInstance?.lastUpdated}" /></td>
    </tr>

    <tr class="prop">
        <td valign="top" class="name"><label for="nwaccScan"><g:message
                code="host.nwaccScan.label" default="Nwacc Scan" /></label></td>
        <td valign="top"
            class="value ${hasErrors(bean: hostInstance, field: 'nwaccScan', 'errors')}">
            <g:checkBox name="nwaccScan"
                        value="${hostInstance?.nwaccScan}" />
        </td>
    </tr>

<g:if test="${action == 'create'}">
    <tr class="prop">
        <td valign="top" class="name"><label for="primarySA"><g:message
                code="host.primarySA.label" default="Primary SA (inherited from Services if not defined)" /></label></td>
        <td valign="top"
            class="value">
            <g:render template="../personSelect" model="[objectInstance:hostInstance]"/>
        </td>
    </tr>
</g:if>

    </tbody>
</table>
    <g:if test="${action == 'create'}">
        <div class="alert_info">
            - Create Host for more options to become available below -
        </div>
    </g:if>
</div>