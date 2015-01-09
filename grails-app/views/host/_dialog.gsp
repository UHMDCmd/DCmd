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

<script language="javascript" type="text/javascript" src="../js/isotope.pkgd.min.js"></script>
<script language="javascript" type="text/javascript">
    $(document).ready(function() {

        var $container = $('#container');
        $container.isotope({
            itemSelector:'.item',
            layoutMode:'fitRows'
        });

    $("#assetSelect").select2({
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
        if(type.val == "VMWare" || type == "VMWare") {
            myElements = document.querySelectorAll(".vmOption");

            for (var i = 0; i < myElements.length; i++) {
                myElements[i].style.visibility='visible';
                myElements[i].style.position='relative';
            }
            document.getElementById("otherOption").style.visibility = 'hidden';
            document.getElementById("otherOption").style.position = 'absolute';
            if(${action!='create'})
                document.getElementById("vmWarning").style.visibility='visible';
        }
        else {
            myElements = document.querySelectorAll(".vmOption");

            for (var i = 0; i < myElements.length; i++) {
                myElements[i].style.visibility='hidden';
                myElements[i].style.position='absolute';
            }
            document.getElementById("otherOption").style.visibility = 'visible';
            document.getElementById("otherOption").style.position = 'relative';
            if(${action!='create'})
                document.getElementById("vmWarning").style.visibility='hidden';
        }
        var $container = $("#container");
        $container.isotope('layout');
    }

</script>



<div class="dialog">
    <div id="container" class="js-isotope" data-isotope-options='{ "columnWidth":400}' style="min-height:275px">

        <div class="item">
<table class="floatTables" style="border:1px solid #CCCCCC;">
<tr><td colspan="2"><center><b>General Information</b></center></td></tr>
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
    <g:if test="${action == 'create'}">
        <tr class="prop">
            <td valign="top" class="name"><label for="primarySA"><g:message
                    code="host.primarySA.label" default="Primary SA" /></label></td>
            <td valign="top"
                class="value">
                <g:render template="../personSelect" model="[objectInstance:hostInstance]"/>
            </td>
        </tr>
    </g:if>
</table>
</div>
<div class="item">

<table class="floatTables" style="border:1px solid #CCCCCC;">
        <tr class="vmOption"><td colspan="2"><center><span style="color: #FF0000;" >*</span><b>VMWare Details (pulled from VCenter)</b></center></td></tr>
        <tr class="vmOption">
            <td valign="top" class="name">VM State</td>
            <td valign="top" class="value">${hostInstance.getVCenterStateString()}</td>
        </tr>
        <tr class="vmOption">
            <td valign="top" class="name" id="clusterLabel">Cluster</td>
            <td valign="top"
                class="value ${hasErrors(bean: hostInstance, field: 'cluster', 'errors')}">
                <input type="hidden" name="clusterSelect" id="clusterSelect" />
            </td>
        </tr>
        <tr id="otherOption"><td colspan="2"><center><b>Host Details</b></center></td></tr>
    <tr class="prop" id="serverDiv">
        <td valign="top" class="name" id="serverLabel">Physical Server</td>
        <td valign="top"
            class="value ${hasErrors(bean: hostInstance, field: 'asset', 'errors')}">
            <input type="hidden" name="assetSelect" id="assetSelect" />
        </td>
    </tr>
    <tr>
        <td valign="top" class="name"><label for="maxMemory"><g:message code="host.maxMemory.label" default="Max Memory" /></label></td>
        <td><g:textField name="maxMemory" maxlength="45" value="${hostInstance?.maxMemory}"/></td>
        </td>
    </tr>
    <tr>
        <td valign="top" class="name"><label for="maxCpu"><g:message code="host.maxCpu.label" default="Max CPU" /></label></td>
        <td><g:textField name="maxCpu" maxlength="45" value="${hostInstance?.maxCpu}"/></td>
        </td>
    </tr>
    <tr>
        <td valign="top" class="name"><label for="ipAddress"><g:message code="host.ipAddress.label" default="IP Address" /></label></td>
        <td><g:textField name="ipAddress" maxlength="45" value="${hostInstance?.ipAddress}"/></td>

    </tr>
    <tr>
        <td valign="top" class="name"><label for="fullDomain"><g:message code="host.fullDomain.label" default="DNS" /></label></td>
        <td><g:textField name="fullDomain" maxlength="45" value="${hostInstance?.fullDomain}"/></td>
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
        <td valign="top" class="name"><label for="lastUpdated"><g:message
                code="host.lastUpdated.label" default="Last Updated" /></label></td>
        <td valign="top" class="value"><g:formatDate
                date="${hostInstance?.lastUpdated}" /></td>
    </tr>

</table>
</div>
</div>


    <g:if test="${action == 'create'}">
        <div class="alert_info">
            - Create Host for more options to become available below -
        </div>
    </g:if>
</div>