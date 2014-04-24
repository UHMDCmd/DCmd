<g:set var="action" value="${actionName != 'show' ? 'edit' : 'show'}"/>
<g:set var="personId" value="${personInstance.id.toLong()}" />

<!-- <span>actionName: ${actionName}; action: ${action}</span> -->
<div id="tabs" style="margin-top: 5px">
    <ul>
        <li>
            <a href="#tabs-supportRoles">Support Roles</a>
        </li>
        <li>
            <a href="#tabs-notes">Notes</a>
        </li>
    </ul>
    <div id="tabs-supportRoles">
        <g:render template='supportRoleGrid' model="[action:action]"/>
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
            <g:render template='../noteTab' model="[action:action, pageType:'person', noteType:'generalNote', objectInstance: personInstance, objectId: personId]"/>
        </div>
        <div id="notes-change">
            <g:render template='../noteTab' model="[action:action, pageType:'person', noteType:'changeNote', objectInstance: personInstance, objectId: personId]"/>
        </div>
        <div id="notes-planning">
            <g:render template='../noteTab' model="[action:action, pageType:'person', noteType:'planningNote', objectInstance: personInstance, objectId: personId]"/>
        </div>
    </div>
</div>
</div>
