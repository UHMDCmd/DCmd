// Create a custom button for the toolbar
$('#${attrs.id}Grid').navButtonAdd('#${attrs.id}GridPager', {
	id:"${attrs.id}",
	<g:if test="${attrs.title}">title:"${attrs.title}",</g:if>
    caption:"${attrs.caption}",
    position: "${attrs.position ?: 'first'}",
    buttonicon:"${attrs.icon ?: 'ui-icon-check'}",
    <g:if test="${attrs.function}">
	    onClickButton: ${attrs.function}
	</g:if>
});