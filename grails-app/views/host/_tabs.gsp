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

<g:set var="action" value="${actionName != 'show' ? 'edit' : 'show'}"/>
<g:set var="hostId" value="${hostInstance.id.toLong()}" />
<!-- <span>actionName: ${actionName}; action: ${action}</span> -->
<div id="tabs" style="margin-top: 5px">
    <ul>
        <li>
            <a href="#tabs-tiers">Services</a>
        </li>
    %{--    <li>
            <a href="#tabs-resources">Resources</a>
        </li>--}%
        <li>
            <a href="#tabs-host-supportStaff">
                <g:message code="host.supportstaff.label" default="Support Staff" />
            </a>
        </li>
        <li>
            <a href="#tabs-notes">
                <g:message code="host.notes.label" default="Notes" />
            </a>
        </li>
    </ul>
    <div id="tabs-tiers">
        <g:render template='tiersGrid' model="[action:action]"/>
    </div>
 %{--   <div id="tabs-resources">
        <g:render template='resourceGrid' model="[action:action]"/>
        <g:render template='appServerAssignmentGrid' model="[action:action]"/>
    </div>--}%
    <div id="tabs-host-supportStaff">
        <g:render template="../technicalSupportStaffGrid" model="[action:action, type:'host', objectId:hostId]"/>
        <g:render template="../functionalSupportStaffGrid" model="[action:action, type:'host', objectId:hostId]"/>
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
            <g:render template='../noteTab' model="[action:action, pageType:'host', noteType:'generalNote', objectInstance: hostInstance, objectId: hostId]"/>
        </div>
        <div id="notes-change">
            <g:render template='../noteTab' model="[action:action, pageType:'host', noteType:'changeNote', objectInstance: hostInstance, objectId: hostId]"/>
        </div>
        <div id="notes-planning">
            <g:render template='../noteTab' model="[action:action, pageType:'host', noteType:'planningNote', objectInstance: hostInstance, objectId: hostId]"/>
        </div>
    </div>
</div>
</div>
