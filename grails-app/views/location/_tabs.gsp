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
<g:set var="locationId" value="${locationInstance.id.toLong()}" />
<!-- <span>actionName: ${actionName}; action: ${action}</span> -->
<div id="tabs" style="margin-top: 5px">
    <ul>
        <li>
            <a href="#tabs-notes">Notes</a>
        </li>
    </ul>
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
