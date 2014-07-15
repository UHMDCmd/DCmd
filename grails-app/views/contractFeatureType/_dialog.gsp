<!-- ContractFeatureType -->
<div class="dialog">
	<table>
		<tbody>

			<tr class="prop">
				<td valign="top" class="name"><label for="type">%{--
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

<g:message
							code="contractFeatureType.type.label" default="Type" />
				</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractFeatureTypeInstance, field: 'type', 'errors')}">
					<g:textField name="type"
						value="${contractFeatureTypeInstance?.type}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="description"><g:message
							code="contractFeatureType.description.label"
							default="Description" />
				</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractFeatureTypeInstance, field: 'description', 'errors')}">
					<g:textField name="description"
						value="${contractFeatureTypeInstance?.description}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="contracts"><g:message
							code="contractFeatureType.contracts.label" default="Contracts" />
				</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractFeatureTypeInstance, field: 'contracts', 'errors')}">

				</td>
			</tr>

		</tbody>
	</table>
</div>