
/*
 * On document load, bind the click event of the 
 * 'Add Support Staff' button to an ajax call that
 * adds a PersonSupportRole to the current Vendor.
 * Also, create an autocomplete box for person. Create
 * a combobox for the support roles.
 */
$(document).ready(function() {
	
	$(function() {
		$( "#supportRole" ).combobox();
		$( "#toggle" ).click(function() {
			$( "#supportRole" ).toggle();
		});
	});	
	
	//Bind the click event here
  	$('#remoteAdd').bind('click', function() {		
  		//validate person and role have been checked
  		if(validatePersonSupportSelection()){
  			
  			//set up the ajax call
	  		$.ajax({
	  			url: '/dcmd/contract/addSupporter',
	  			data: getPersonSupportRoleParamsForAdd(),
	  			type: 'POST',
	  			success: updateSupporters,
	  			error: function(){
	  				alert('The addition of the person and role was not complete.');
	  			}
	  		});
  		}
	});
  
	addDeleteButtons();
	addEditSupportersTableStyles();
});

/*
 * Get the value of the person and role dropdown
 * boxes and ensure they are not empty strings.
 * If so, show error message and return false.
 */
function validatePersonSupportSelection(){
	var msg='';
	if($('#personId').val() == ''){
		msg += 'You must select a person from the dropdown.';
	}
	if($('#supportRole').val() == ''){
		msg += 'You must select a support role from the dropdown.';
	}
	if(msg != ''){
		alert(msg);
		return false;
	}else{
		return true;
	}
}

/*
 * When ajax call completes succesfully, replace all
 * rows in the table with values returned.
 */
function updateSupporters(data, status, jqXHR){
	
	//get count of data rows so we can see whether we added any new data
	var rowCount = $('#contractSupportTable tbody tr').length;

	//clear out all table rows not in thead
	$('#contractSupportTable tbody tr').remove();
	
	//create html for new row for each supporter returned in data
	var newRow;
	for(rowIndex in data){
		
		//create the row tag and set class for alternating colors
		newRow = '<tr ';
		if((rowIndex % 2) == 1){
			newRow += "class='even'>";
		}else{
			newRow += "class='odd'>";
		}
		
		newRow += getIdRow(data[rowIndex].id)
				
		//add the person <td>
		newRow += "<td>"
		newRow += data[rowIndex].person;
		newRow += "</td><td>";
		
		//add the supportRole <td>
		newRow += data[rowIndex].supportRole;
		
		//close open tags and append the row to the table
		newRow += "</td>"
		newRow += "</tr>";
		$('#contractSupportTable').append(newRow);
	}
	
	//add the delete buttons
	addDeleteButtons();
	
	//figure out if anything changed; alert user if supporter was not added
	var newRowCount = $('#contractSupportTable tbody tr').length;
	if(rowCount == newRowCount){
		alert("The update of contract supporters for this contract has failed!");
	}else{
		addTableStyles(); //function in application.js
	}
}

/*
 * Get a <td> containing a linkable id for person support role
 */
function getIdRow(id){
	var td = "<td>";
	td += "<a class='white-link' href='personSupportRole/show/"
	td += id;
	td += "'>";
	td += id;
	td += "</a>"
	td += "</td>";
	return td;
}

/*
 * Formats the params to be sent in the ajax request to add a supporter (VendorController#addSupporter)
 */
function getPersonSupportRoleParamsForAdd(){
	//alert("person: " + $('#person').val());
	var params = { person:$('#personId').val(), supportRole:$('#supportRole').val(), supportableObjectType:$('#supportableObjectType').val(), contract:$('#id').val()};
	return $.param(params);
}

/*
 * Formats the params to be sent in the ajax request to remove a supporter (VendorController#removeSupporter)
 */
function getPersonSupportRoleParamsForRemove(id){
	var params = {contract:$('#id').val(), personSupportRole:id};
	return $.param(params);
}

/*
 * Loop through all tbody rows in #supportTable and add a delete button column
 */
function addDeleteButtons(){
	if($('#contractSupportTable thead tr th').length == 3){
		$('#contractSupportTable thead tr').append("<th>&nbsp;</th>");
	}
	$('#contractSupportTable tbody tr').each(function(index){
		var id = this.children[0].innerText.trim();
		var del = getDeleteButton(index);
		$(this).append(del);
		var buttonId = "#remoteDelete" + index;
		$(buttonId).bind('click', function(){
			//alert('delete ' + id + '?');
	  		$.ajax({
	  			url: '/dcmd/contract/removeSupporter',
	  			data: getPersonSupportRoleParamsForRemove(id),
	  			type: 'POST',
	  			success: updateSupporters,
	  			error: function(){
	  				alert('The removal of the person and role was not complete.');
	  			}
	  		});
		});
	});
	addTableStyles(); //found in application.js (styles the added <td> element
}

function getDeleteButton(index){
	return "<td><input type='submit' name='remoteDelete" + index + "' value='Remove' id='remoteDelete" + index + "' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only' role='button' aria-disabled='false'></td>";
}

function addEditSupportersTableStyles(){
	$("#editContractSupportersTable").css({borderCollapse:"collapse", borderSpacing:"0px", marginBottom:"10px"});
	$("#editContractSupportersTable th").addClass("ui-state-default").css({border:"0px"});
	$("#editContractSupportersTable td").addClass("ui-widget-content").css({border:"0px"});
}


