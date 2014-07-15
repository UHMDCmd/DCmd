<!-- Contract -->

<div id="tabs">
	<!-- 
    Provide with the tab headings a count of the elements in the respective tables.
    In Contract.groovy initial the lists referenced below to empty lists so that .size() works. 
    -->
	<ul>
		<li><a href="#tabs-1">Payment History (%{--
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

${contractInstance?.contractPaymentHistory.size()})</a>
		</li>
		<li><a href="#tabs-2">Support Staff (0)</a></li>
		<li><a href="#tabs-3">Contract Features (${contractInstance?.contractFeatures.size()})</a>
		</li>
		<li><a href="#tabs-4">Required Forms (${contractInstance?.requiredRenewalForms.size()})</a>
		</li>
		<li><a href="#tabs-5">Purchased Assets (0)</a></li>
		<li><a href="#tabs-6">Assets on Maint (0)</a></li>
		<li><a href="#tabs-7">Notes (0)</a></li>
	</ul>
	<div id="tabs-1">
		<table>
			<tr>
				<td>payment history</td>
			</tr>
		</table>
	</div>
	<div id="tabs-2">
		<table>
			<tr>
				<td>support staff</td>
			</tr>
		</table>
	</div>
	<div id="tabs-3">
		<table>
			<tr>
				<td>contract features</td>
			</tr>
		</table>
	</div>
	<div id="tabs-4">
		<table>
			<tr>
				<g:render template="requiredRenewalFormsAjax" />
				<g:render template="requiredRenewalFormsTable" />
			</tr>
		</table>
	</div>
	<div id="tabs-5">
		<table>
			<tr>
				<td>purchased assets</td>
			</tr>
		</table>
	</div>
	<div id="tabs-6">
		<table>
			<tr>
				<td>assets on maintenance</td>
			</tr>
		</table>
	</div>
	<div id="tabs-7">
		<table>
			<tr>
				<td>notes</td>
			</tr>
		</table>
	</div>
</div>
