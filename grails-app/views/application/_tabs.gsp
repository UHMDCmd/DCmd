<g:set var="action" value="${actionName != 'show' ? 'edit' : 'show'}"/>
<g:set var="applicationId" value="${applicationInstance.id.toLong()}" />
<!-- <span>actionName: ${actionName}; action: ${action}</span> -->
<div id="tabs" style="margin-top: 5px">
    <ul>
            <li>
                <a href="#tabs-services">
                    <g:message code="application.services.label" default="Services" />
                </a>
            </li>
        %{--
        <li>
            <a href="#tabs-tiers">
                <g:message code="application.tiers.label" default="Software Instances" />
            </a>
        </li>
        --}%
            <li>
                <a href="#tabs-support-staff"><g:message code="application.supportStaff.label" default="Support Staff" /></a>
            </li>
            <li>
                <a href="#tabs-notes"><g:message code="notes.label" default="Notes" /></a>
            </li>
        </ul>

        <div id="tabs-services">
            <g:render template="serviceGrid" model="[action:action]" />
            <br><hr><br>
            <g:render template="tierGrid" model="[action:action]" />

        </div>
<!--
    <div id="tabs-tiers">
    </div>
-->
    <div id="tabs-support-staff">
        <g:render template="../technicalSupportStaffGrid" model="[action:action, type:'application', objectId:applicationId]"/>
        <g:render template="../functionalSupportStaffGrid" model="[action:action, type:'application', objectId:applicationId]"/>
    </div>
    <div id="tabs-notes">
        <ul>

            <li>
                <a href="#notes-general"><g:message code="notes-general.label" default="General" /></a>
            </li>
            <li>
                <a href="#notes-change"><g:message code="notes-change.label" default="Change" /></a>
            </li>
            <li>
                <a href="#notes-planning"><g:message code="notes-planning.label" default="Planning" /></a>
            </li>
        </ul>
        <div id="notes-general">
            <g:render template='../noteTab' model="[action:action, pageType:'application', noteType:'generalNote', objectInstance: applicationInstance, objectId: applicationId]"/>
        </div>
        <div id="notes-change">
            <g:render template='../noteTab' model="[action:action, pageType:'application', noteType:'changeNote', objectInstance: applicationInstance, objectId: applicationId]"/>
        </div>
        <div id="notes-planning">
            <g:render template='../noteTab' model="[action:action, pageType:'application', noteType:'planningNote', objectInstance: applicationInstance, objectId: applicationId]"/>
        </div>
    </div>
</div>
</div>
