// Create a custom 'refresh' button for the toolbar
$('#${attrs.id}Grid').navButtonAdd('#${attrs.id}GridPager', {
	id:"${attrs.id}",
	<g:if test="${attrs.title}">title:"${attrs.title}",</g:if>
    caption:"${attrs.caption}",
    position: "${attrs.position ?: 'first'}",
    buttonicon:"${attrs.icon ?: 'ui-icon-refresh'}",
    <g:if test="${!attrs.function}">
	    onClickButton:function() {
	        $('#${attrs.id}Grid').trigger('reloadGrid');
	    }
	</g:if>
	<g:else>
		onClickButton: ${attrs.function}
	</g:else>
});