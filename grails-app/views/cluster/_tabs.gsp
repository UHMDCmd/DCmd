<g:set var="action" value="${actionName != 'show' ? 'edit' : 'show'}"/>
<g:set var="clusterId" value="${clusterInstance.id.toLong()}" />
<!-- <span>actionName: ${actionName}; action: ${action}</span> -->
<div id="tabs" style="margin-top: 5px">
    <ul>
        <li>
            <a href="#tabs-cluster-assets">Physical Servers</a>
        </li>
        <li>
            <a href="#tabs-cluster-hosts">Hosts</a>
        </li>
        <li>
            <a href="#tabs-notes">
                <g:message code="host.notes.label" default="Notes" />
            </a>
        </li>
    </ul>
    <div id="tabs-cluster-assets">
        <g:render template='assetsGrid' model="[action:action]"/>
    </div>
    <div id="tabs-cluster-hosts">
        <g:render template='hostGrid' model="[action:action]"/>
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
            <g:render template='../noteTab' model="[action:action, pageType:'cluster', noteType:'generalNote', objectInstance: clusterInstance, objectId: clusterId]"/>
        </div>
        <div id="notes-change">
            <g:render template='../noteTab' model="[action:action, pageType:'cluster', noteType:'changeNote', objectInstance: clusterInstance, objectId: clusterId]"/>
        </div>
        <div id="notes-planning">
            <g:render template='../noteTab' model="[action:action, pageType:'cluster', noteType:'planningNote', objectInstance: clusterInstance, objectId: clusterId]"/>
        </div>
    </div>
</div>
