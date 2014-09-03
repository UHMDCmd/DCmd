<script type="text/javascript">
   var childCount = %{--
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

${contractInstance?.requiredRenewalForms.size()} + 0;

   function addRequiredRenewalForm(){
	   
     var clone = $("#requiredRenewalForm_clone").clone()
     var htmlId = 'requiredRenewalFormsList['+childCount+'].';
     var requiredRenewalFormSelect = clone.find("select[id$=contractFormType]");
   
     clone.find("input[id$=id]")
            .attr('id',htmlId + 'id')
            .attr('name',htmlId + 'id');
     clone.find("input[id$=deleted]")
            .attr('id',htmlId + 'deleted')
            .attr('name',htmlId + 'deleted');
     clone.find("input[id$=new]")
            .attr('id',htmlId + 'new')
            .attr('name',htmlId + 'new')
			.attr('value', 'true');
     requiredRenewalFormSelect.attr('id',htmlId + 'contractFormType')
            .attr('name',htmlId + 'contractFormType.id');

     clone.attr('id', 'requiredRenewalForm'+childCount);
     $("#requiredRenewalFormTable").append(clone);
     clone.show();
     requiredRenewalFormSelect.focus();
     childCount++;
   }

   //bind click event on delete buttons using jquery live
   $('.del-requiredRenewalForm').live('click', function() {
       //find the parent div
       var prnt = $(this).parents(".requiredRenewalForm-div");
       //find the deleted hidden input
       var delInput = prnt.find("input[id$=deleted]");
       //check if this is still not persisted
       var newValue = prnt.find("input[id$=new]").attr('value');
       //if it is new then i can safely remove from dom
       if(newValue == 'true'){
           prnt.remove();
       }else{
           //set the deletedFlag to true
           delInput.attr('value','true');
           //hide the div
           prnt.hide();
       }
   });

</script>

<div id="requiredRenewalFormTable">
	<g:each var="requiredRenewalForm" in="${contractInstance.requiredRenewalForms}"
		status="i">
		<g:render template='requiredRenewalForm'
			model="['requiredRenewalForm':requiredRenewalForm,'i':i,'hidden':false]" />
	</g:each>
</div>
<input type="button" value="Add Required Renewal Form" onclick="addRequiredRenewalForm();" />
