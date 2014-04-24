%{--
<g:set var="action" value="${actionName != 'show' ? 'edit' : 'show'}"/>
--}%
<g:set var="sourceId" value="${sourceInstance.id.toLong()}" />

%{--<span>actionName: ${actionName}; action: ${action}</span>--}%

<div id="tabs" style="margin-top: 5px">

    <ul>
        <li>
            <a href="#tabs-panels">Panels</a>
        </li>

        <li>
            <a href="#tabs-notes">Notes</a>
        </li>
    </ul>
    <div id="tabs-panels">
        <table>
        <tr>
           <g:render template="panelGrid"  model="[sourceId: sourceId]"/>
           </tr>
        %{--<tr>--}%
            %{--<g:render template="panelUI" />--}%
        %{--</tr>--}%
        </table>
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
            <g:render template='../noteTab' model="[action:action, pageType:'location', noteType:'generalNote', objectInstance: locationInstance, objectId: locationId]"/>
        </div>
        <div id="notes-change">
            <g:render template='../noteTab' model="[action:action, pageType:'location', noteType:'changeNote', objectInstance: locationInstance, objectId: locationId]"/>
        </div>
        <div id="notes-planning">
            <g:render template='../noteTab' model="[action:action, pageType:'location', noteType:'planningNote', objectInstance: locationInstance, objectId: locationId]"/>
        </div>
    </div>
</div>
</div>
