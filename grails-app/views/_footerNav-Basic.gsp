<%--
 <button class = "toggle_button"></button>

<script type="text/javascript">
var flip = 0;
$("button").click(function () {
$("#footer-bar-basic").toggle( flip++ % 2 == 0 );
});
</script>
<div style="display: none" class="footer-bar-basic" id="footer-bar-basic">
	--%>
	<div class="footer-bar-basic" id="footer-bar-basic">

	        <div class="fixed-bar-buttons">
	                <ul>
	                    <li>
	                   <div class="home_button">
	                    <a href="${createLink(uri: '/')}">Home</a>
						</div>
						</li>
	                    
	                    <li>
	                    <div class="create_button">
	   					<g:link action="create">
	   					<g:message code="default.create.label" args="[entityName]" /></g:link>
						</div>
						</li>
						<%--
						   
	            		<li>
						<div class = "save_button">Save Asset</div>
						</li>
						
						<li>
						<div class = "update_button">Update</div>
						</li>
					
	                --%></ul>
	            </div>
	        </div>
	        
