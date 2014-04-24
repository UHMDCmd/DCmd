// Create a custom 'search' button for the toolbar
$('#${attrs.id}Grid').navButtonAdd('#${attrs.id}GridPager', {
	id:"${attrs.id}",
	<g:if test="${attrs.title}">title:"${attrs.title}",</g:if>
    position: "${attrs.position ?: 'first'}",
    caption:"${attrs.caption}",
    buttonicon:"${attrs.icon ?: 'ui-icon-search'}",
    <g:if test="${!attrs.function && !attrs.searchButtonFunction}">
	    onClickButton:function() {
	        // We are doing inline search/filtering so when the user clicks
	        // search either display or hide the filter/search row
	        $('#${attrs.id}Grid')[0].toggleToolbar();
	    }
	</g:if>
	<g:else>
		onClickButton: ${attrs.function ?: attrs.searchButtonFunction}
	</g:else>
});