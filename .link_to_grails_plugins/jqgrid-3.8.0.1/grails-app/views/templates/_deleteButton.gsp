// Add a custom 'delete' button.
$('#${attrs.id}Grid').navButtonAdd('#${attrs.id}GridPager', {
	id:"${attrs.id}",
	<g:if test="${attrs.title}">title:"${attrs.title}",</g:if>
    caption:"${attrs.caption ?: attrs.deleteCaption}",
    position: "${attrs.position ?: 'first'}",
    buttonicon:"${attrs.icon ?: 'ui-icon-trash'}",
    <g:if test="${!attrs.function && !attrs.deleteButtonFunction}">
	    onClickButton:function() {
	        // Custom javascript handler that is executed when our button is clicked
	        // Get the row that is selected
	        var rowid = $("#${attrs.id}Grid").getGridParam("selrow");
	        // User must select a row for delete to work
	        if (rowid != null) {
	            // Post to our controller for the delete operation
	            $.post('${attrs.url}', {id: rowid, oper: "del"}, function(data){
	                // Server returned success so show message and set it to fade
	                if (data.state == "OK") {
	                    // Tell the Grid to remove the selected row from the view
	                    $("#${attrs.id}Grid").delRowData(rowid);
	                    $('#${attrs.messageId}').html(data.message);
	                    $('#${attrs.messageId}').show().fadeOut(5000);
	                } else {
	                    // Server reported failure. Show message and set to fade
	                    $('#${attrs.messageId}').html(data.message);
	                    $('#${attrs.messageId}').show().fadeOut(10000);
	                }
	            });
	        } else {
	            // User did not select a row. Show a dialog telling them what is wrong
	            if ($("#noSelection").length <= 0) {
	              $("body").append('<div id="noSelection" style="display: none;" />');
	            }
	
	            $("#noSelection").html("You must select a row to delete.");
	            $("#noSelection").dialog({
	                modal: true,
	                title: "Invalid Selection"
	            });
	        }
	    }
	</g:if>
	<g:else>
		onClickButton: ${attrs.function ?: attrs.deleteButtonFunction}
	</g:else>
});
