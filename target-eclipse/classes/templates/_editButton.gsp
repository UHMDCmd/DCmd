// Create a custom 'edit' button in our toolbar
$('#${attrs.id}Grid').navButtonAdd('#${attrs.id}GridPager', {
	id:"${attrs.id}",
	<g:if test="${attrs.title}">title:"${attrs.title}",</g:if>
    caption:"${attrs.caption}",
    position: "${attrs.position ?: 'first'}",
    buttonicon:"${attrs.icon ?: 'ui-icon-pencil'}",
    <g:if test="${!attrs.function && !attrs.editButtonFunction}">
	    onClickButton:function() {
	        // Get selected row
	        var rowid = $("#${attrs.id}Grid").getGridParam("selrow");
	        // Must select a row to edit
	        if (rowid != null) {
	            // direct browser to our 'edit' page
	            $(location).attr('href', '${attrs.url}' + "/" + rowid);
	        } else {
	            // No row selected, can't edit. Show a dialog
	            if ($("#noSelection").length <= 0) {
	              $("body").append('<div id="noSelection" style="display: none;" />');
	            }
	
	            $("#noSelection").html("You must select a row to edit.");
	            $("#noSelection").dialog({
	                modal: true,
	                title: "Invalid Selection"
	            });
	        }
	    }
	</g:if>
	<g:else>
		onClickButton: ${attrs.function ?: attrs.editButtonFunction}
	</g:else>
});