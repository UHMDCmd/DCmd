<!-- Contract dialog -->
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

<calendar:resources lang="en" theme="tiger" />
<div class="dialog">
	<table>
		<tbody>

			<tr class="prop">
				<td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
					for="uhContractNo"><g:message
							code="contract.uhContractNo.label" default="Uh Contract No" /> </label>
				</td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'uhContractNo', 'errors')}">
					<g:textField name="uhContractNo"
						value="${contractInstance?.uhContractNo}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
					for="uhContractTitle"><g:message
							code="contract.uhContractTitle.label" default="Uh Contract Title" />
				</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'uhContractTitle', 'errors')}">
					<g:textArea class="cols2rows96" name="uhContractTitle"
						value="${contractInstance?.uhContractTitle}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="vendorContractNo"><g:message
							code="contract.vendorContractNo.label"
							default="Vendor Contract No" /> </label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'vendorContractNo', 'errors')}">
					<g:textField name="vendorContractNo"
						value="${contractInstance?.vendorContractNo}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
					for="contractStatus"><g:message
							code="contract.contractStatus.label" default="Contract Status" />
				</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'contractStatus', 'errors')}">
					<g:select name="contractStatus.id"
						from="${edu.hawaii.its.dcmd.inf.ContractStatus.list()}"
						optionKey="id" value="${contractInstance?.contractStatus?.id}"
						noSelection="['null': '']" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
					for="annualCost"><g:message
							code="contract.annualCost.label" default="Annual Cost" /> </label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'annualCost', 'errors')}">
					<g:textField name="annualCost"
						value="${fieldValue(bean: contractInstance, field: 'annualCost')}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
					for="taxRate"><g:message code="contract.taxRate.label"
							default="Tax Rate" /> </label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'taxRate', 'errors')}">
					<g:select name="taxRate.id"
						from="${edu.hawaii.its.dcmd.inf.HawaiiTaxRate.list()}" optionKey="id"
						value="${contractInstance?.taxRate?.id}"
						noSelection="['null': '']" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
					for="contractBeginDate"><g:message
							code="contract.contractBeginDate.label"
							default="Contract Begin Date" /> </label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'contractBeginDate', 'errors')}">
					<calendar:datePicker name="contractBeginDate"
						value="${contractInstance?.contractBeginDate}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
					for="contractEndDate"><g:message
							code="contract.contractEndDate.label" default="Contract End Date" />
				</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'contractEndDate', 'errors')}">
					<calendar:datePicker name="contractEndDate"
						value="${contractInstance?.contractEndDate}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label
					for="contractExtensibleEndDate"><g:message
							code="contract.contractExtensibleEndDate.label"
							default="Contract Extensible End Date" /> </label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'contractExtensibleEndDate', 'errors')}">
					<calendar:datePicker name="contractExtensibleEndDate"
						value="${contractInstance?.contractExtensibleEndDate}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
					for="annualRenewalDeadlineMm"><g:message
							code="contract.annualRenewalDeadlineMm.label"
							default="Annual Renewal Deadline Mm" /> </label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'annualRenewalDeadlineMm', 'errors')}">
					<g:select name="annualRenewalDeadlineMm" from="${1..12}"
						value="${fieldValue(bean: contractInstance, field: 'annualRenewalDeadlineMm')}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
					for="annualRenewalDeadlineDd"><g:message
							code="contract.annualRenewalDeadlineDd.label"
							default="Annual Renewal Deadline Dd" /> </label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'annualRenewalDeadlineDd', 'errors')}">
					<g:select name="annualRenewalDeadlineDd" from="${1..31}"
						value="${fieldValue(bean: contractInstance, field: 'annualRenewalDeadlineDd')}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label
					for="annualRenewalReminderMm"><g:message
							code="contract.annualRenewalReminderMm.label"
							default="Annual Renewal Reminder Mm" /> </label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'annualRenewalReminderMm', 'errors')}">
					<g:select name="annualRenewalReminderMm" from="${1..12}"
						value="${fieldValue(bean: contractInstance, field: 'annualRenewalReminderMm')}" />
				</td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label
					for="annualRenewalReminderDd"><g:message
							code="contract.annualRenewalReminderDd.label"
							default="Annual Renewal Reminder Dd" /> </label></td>
				<td valign="top"
					class="value ${hasErrors(bean: contractInstance, field: 'annualRenewalReminderDd', 'errors')}">
					<g:select name="annualRenewalReminderDd" from="${1..31}"
						value="${fieldValue(bean: contractInstance, field: 'annualRenewalReminderDd')}" />
				</td>
			</tr>

		</tbody>
	</table>
</div>
