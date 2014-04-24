<g:set var="action" value="${actionName != 'show' ? 'edit' : 'show'}"/>
<g:set var="tierId" value="${fieldValue(bean: tierInstance, field: 'id')}" />
<span>actionName: ${actionName}; action: ${action}</span>
<div id="tabs" style="margin-top: 5px">
    <ul>

            <li>
                <a href="#tabs-services">
                    <g:message code="tier.services.label" default="Dependent Services" />
                </a>
            </li>

            <li>
                <a href="#tabs-notes"><g:message code="notes.label" default="Notes" /></a>
            </li>
        </ul>

        <div id="tabs-services">
            <g:render template="serviceGrid" model="[action:action]" />
        </div>

    <div id="tabs-notes">
        <ul>

            <li>
                <a href="#notes-general"><g:message code="notes-general.label" default="General" /></a>
            </li>
        </ul>
        <div id="notes-general">
            <g:render template='../noteTab' model="[action:action, pageType:'tier', noteType:'generalNote', objectInstance: tierInstance, objectId: tierId]"/>
        </div>
    </div>
</div>
</div>
