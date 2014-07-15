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

<g:javascript src="../js/combobox.js" />
<%--<g:javascript src="../js/contractSupporters.js" />
--%>
<div id="new_contract_payment_history">
	<table id="editContractPaymentHistoryTable">
<g:form>
		<tbody>
			<tr class="prop">
				<td valign="top" class="name"><label for="periodBeginDate"><g:message
							code="contractPaymentHistory.periodBeginDate.label"
							default="Period Begin Date" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractPaymentHistoryInstance, field: 'periodBeginDate', 'errors')}">
					<g:datePicker name="periodBeginDate" precision="day"
						value="${contractPaymentHistoryInstance?.periodBeginDate}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="periodEndDate"><g:message
							code="contractPaymentHistory.periodEndDate.label"
							default="Period End Date" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractPaymentHistoryInstance, field: 'periodEndDate', 'errors')}">
					<g:datePicker name="periodEndDate" precision="day"
						value="${contractPaymentHistoryInstance?.periodEndDate}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="amountEncumbered"><g:message
							code="contractPaymentHistory.amountEncumbered.label"
							default="Amount Encumbered" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractPaymentHistoryInstance, field: 'amountEncumbered', 'errors')}">
					<g:textField name="amountEncumbered"
						value="${fieldValue(bean: contractPaymentHistoryInstance, field: 'amountEncumbered')}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="amountPaid"><g:message
							code="contractPaymentHistory.amountPaid.label"
							default="Amount Paid" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractPaymentHistoryInstance, field: 'amountPaid', 'errors')}">
					<g:textField name="amountPaid"
						value="${fieldValue(bean: contractPaymentHistoryInstance, field: 'amountPaid')}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="contract"><g:message
							code="contractPaymentHistory.contract.label" default="Contract" /></label>
				</td>
				<td valign="top"
					class="value ${hasErrors(bean: contractPaymentHistoryInstance, field: 'contract', 'errors')}">
					<g:select name="contract.id"
						from="${edu.hawaii.its.dcmd.inf.Contract.list()}" optionKey="id"
						value="${contractPaymentHistoryInstance?.contract?.id}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="contractMod"><g:message
							code="contractPaymentHistory.contractMod.label"
							default="Contract Mod" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractPaymentHistoryInstance, field: 'contractMod', 'errors')}">
					<g:textField name="contractMod"
						value="${contractPaymentHistoryInstance?.contractMod}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label
					for="contractModDescription"><g:message
							code="contractPaymentHistory.contractModDescription.label"
							default="Contract Mod Description" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractPaymentHistoryInstance, field: 'contractModDescription', 'errors')}">
					<g:textField name="contractModDescription"
						value="${contractPaymentHistoryInstance?.contractModDescription}" />
				</td>
			</tr>
		
		</tbody>
</g:form>
			<tr>
			<td>
			<button id="remoteAddPaymentHistory"
				class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
				role="button" aria-disabled="false">
				<span class="ui-button-text">Add Payment History</span>
			</button>
			</td>

	</table>
</div>