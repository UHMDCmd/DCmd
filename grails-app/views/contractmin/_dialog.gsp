<!-- Contractmin dialog -->
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
					class="value ${hasErrors(bean: contractminInstance, field: 'uhContractNo', 'errors')}">
					<g:textField name="uhContractNo"
						value="${contractminInstance?.uhContractNo}" /></td>
			</tr>

		</tbody>
	</table>
</div>
