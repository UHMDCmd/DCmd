<calendar:resources lang="en" theme="tiger" />
<calendar:datePicker name='test' value="${new Date() }"/>
<div class="dialog">
	<table>
		<tbody>
			<tr class="prop">
				<td valign="top" class="name"><label for="code"><g:message
							code="vendor.code.label" default="Code" />
				</label></td>
				<td valign="top" class="value${hasErrors(bean: vendorInstance, field: 'code', 'errors')}">
					<g:textField name="code" value="${vendorInstance?.code}" /></td>
				<td valign="top" class="name"><label for="addressLine1"><g:message
							code="vendor.addressLine1.label" default="Address Line1" />
				</label></td>
				<td valign="top"
					class="value${hasErrors(bean: vendorInstance, field: 'addressLine1', 'errors')}">
					<g:textField name="addressLine1"
						value="${vendorInstance?.addressLine1}" /></td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="name"><g:message
							code="vendor.name.label" default="Name" />
				</label></td>
				<td valign="top"
					class="value${hasErrors(bean: vendorInstance, field: 'name', 'errors')}">
					<g:textField name="name" value="${vendorInstance?.name}" /></td>
				<td valign="top" class="name"><label for="addressLine2"><g:message
							code="vendor.addressLine2.label" default="Address Line2" />
				</label></td>
				<td valign="top"
					class="value${hasErrors(bean: vendorInstance, field: 'addressLine2', 'errors')}">
					<g:textField name="addressLine2"
						value="${vendorInstance?.addressLine2}" /></td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="phone"><g:message
							code="vendor.phone.label" default="Phone" />
				</label></td>
				<td valign="top"
					class="value${hasErrors(bean: vendorInstance, field: 'phone', 'errors')}">
					<g:textField name="phone" value="${vendorInstance?.phone}" /></td>
				<td valign="top" class="name"><label for="city"><g:message
							code="vendor.city.label" default="City" />
				</label></td>
				<td valign="top"
					class="value${hasErrors(bean: vendorInstance, field: 'city', 'errors')}">
					<g:textField name="city" value="${vendorInstance?.city}" /></td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="fax"><g:message
							code="vendor.fax.label" default="Fax" />
				</label></td>
				<td valign="top"
					class="value${hasErrors(bean: vendorInstance, field: 'fax', 'errors')}">
					<g:textField name="fax" value="${vendorInstance?.fax}" /></td>
				<td valign="top" class="name"><label for="state"><g:message
							code="vendor.state.label" default="State" />
				</label></td>
				<td valign="top"
					class="value${hasErrors(bean: vendorInstance, field: 'state', 'errors')}">
					<g:textField name="state" value="${vendorInstance?.state}" /></td>
			</tr>
			<tr class="prop">
				<td></td><td></td>
				<td valign="top" class="name"><label for="zip"><g:message
							code="vendor.zip.label" default="Zip" />
				</label></td>
				<td valign="top"
					class="value${hasErrors(bean: vendorInstance, field: 'zip', 'errors')}">
					<g:textField name="zip" value="${vendorInstance?.zip}" /></td>
			</tr>
		</tbody>
	</table>
</div>
<g:javascript>
		//reset td to auto width
		$(document).ready(function() {	
			($('td.value^')).width('auto');
		});	
</g:javascript>