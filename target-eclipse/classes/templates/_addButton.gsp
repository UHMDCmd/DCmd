// Create a custom 'add' button for the toolbar
$('#${attrs.id}Grid').navButtonAdd('#${attrs.id}GridPager', {
	id:"${attrs.id}",
	<g:if test="${attrs.title}">title:"${attrs.title}",</g:if>
    caption:"${attrs.caption}",
    position: "${attrs.position ?: 'first'}",
    buttonicon:"${attrs.icon ?: 'ui-icon-plus'}",
    <g:if test="${!attrs.function && !attrs.addButtonFunction}">
	    onClickButton:function() {
	        $(location).attr('href', '${attrs.url}');
	    }
	</g:if>
	<g:else>
		onClickButton: ${attrs.function ?: attrs.addButtonFunction}
	</g:else>
});