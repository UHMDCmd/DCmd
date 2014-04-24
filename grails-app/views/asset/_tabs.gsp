<g:set var="action" value="${actionName != 'show' ? 'edit' : 'show'}"/>
<g:set var="assetId" value="${assetInstance.id.toLong()}" />
<!-- <span>actionName: ${actionName}; action: ${action}</span> -->

    <div id="tabs" style="margin-top: 5px">
    <g:if test="${assetType == 'Rack'}">
	    <ul>
            <li>
                <a href="#tabs-rackContents">Rack Contents</a>
            </li>
            <li>
                <a href="#tabs-replacements">Replacements</a>
            </li>
            <li>
                <a href="#tabs-support-staff"><g:message code="asset.supportStaff.label" default="Support Staff" /></a>
            </li>
            <li>
                <a href="#tabs-notes"><g:message code="notes.label" default="Notes" /></a>
            </li>
        </ul>
    </g:if>
    <g:if test="${assetType == 'Physical Server'}">
        <ul>
            <li>
                <a href="#tabs-capacity">Capacities</a>
            </li>
            <li>
                <a href="#tabs-hosts">Hosts</a>
            </li>
            <li>
                <a href="#tabs-replacements">Replacements</a>
            </li>
            <li>
                <a href="#tabs-support-staff"><g:message code="asset.supportStaff.label" default="Support Staff" /></a>
            </li>
            <li>
                <a href="#tabs-notes"><g:message code="notes.label" default="Notes" /></a>
            </li>
        </ul>
    </g:if>
    <g:if test="${assetType == 'Misc'}">
        <ul>
            <li>
                <a href="#tabs-replacements">Replacements</a>
            </li>
            <li>
                <a href="#tabs-support-staff"><g:message code="asset.supportStaff.label" default="Support Staff" /></a>
            </li>
            <li>
                <a href="#tabs-notes"><g:message code="notes.label" default="Notes" /></a>
            </li>
        </ul>
    </g:if>

    <g:if test="${assetType == 'Rack'}">
        <div id="tabs-rackContents">
            <g:render template="../rack/rackTab" model="[action:action, rackId:assetId]" />
        </div>
    </g:if>
    <g:if test="${assetType == 'Physical Server'}">
        <div id="tabs-capacity">
            <g:render template='../physicalServer/capacityGrid' model="[action:action, physicalServerId: assetId]"/>
        </div>
        <div id="tabs-hosts">
            <g:render template='../physicalServer/hostGrid' model="[action:action, physicalServerId: assetId]"/>
        </div>
    </g:if>
    <div id="tabs-replacements">
        <g:render template='replacementGrid' model="[action:action]"/>
    </div>
	<div id="tabs-support-staff">
        <g:render template="../technicalSupportStaffGrid" model="[action:action, type:'asset', objectId:assetId]"/>
        <g:render template="../functionalSupportStaffGrid" model="[action:action, type:'asset', objectId:assetId]"/>
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
            <g:render template='../noteTab' model="[action:action, pageType:'asset', noteType:'generalNote', objectInstance: assetInstance, objectId: assetId]"/>
        </div>
        <div id="notes-change">
            <g:render template='../noteTab' model="[action:action, pageType:'asset', noteType:'changeNote', objectInstance: assetInstance, objectId: assetId]"/>
        </div>
        <div id="notes-planning">
            <g:render template='../noteTab' model="[action:action, pageType:'asset', noteType:'planningNote', objectInstance: assetInstance, objectId: assetId]"/>
        </div>
        </div>
    </div>
</div>
