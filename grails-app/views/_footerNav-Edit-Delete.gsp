<div class="footer-edit-delete-bar" id="footer-edit-delete-bar">
			<div class="fixed-bar-buttons">	
			
	<g:form controller="${controllerName}">
		<span class="edit_button">
		<g:actionSubmit controler="${controllerName}" class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" />
		</span>
		
		<span class="delete_button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
			onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
        <g:hiddenField name="id" value="${id}" />
	</g:form>
</div>
	

		</div>
		

		