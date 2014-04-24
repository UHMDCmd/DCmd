  	<div class="footer-update-delete-bar" id="footer-update-delete-bar">
			<div class="fixed-bar-buttons">	
			<span class="update_button">
		<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
	</span> 
	<span class="delete_button">
		<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
				onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
	</span>
	<%--<g:form controller="asset">
		<span class="update_button">
		<g:actionSubmit controller="asset" class="update" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
		</span>
		
		<span class="delete_button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
			onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
        <g:hiddenField name="id" value="${assetInstance?.id}" />
	</g:form>
--%></div>
	

		</div>
		

