%{--
  - Copyright (c) 2014 University of Hawaii
  -
  - This file is part of DataCenter metadata (DCmd) project.
  -
  - DCmd is free software: you can redistribute it and/or modify
  - it under the terms of the GNU General Public License as published by
  - the Free Software Foundation, either version 3 of the License, or
  - (at your option) any later version.
  -
  - DCmd is distributed in the hope that it will be useful,
  - but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with DCmd.  It is contained in the DCmd release as LICENSE.txt
  - If not, see <http://www.gnu.org/licenses/>.
  --}%

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
	        
