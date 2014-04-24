
/*
 * On document load, bind the click event of the 
 * 'Add Support Staff' button to an ajax call that
 * adds a PersonSupportRole to the current Vendor.
 * Also, create an autocomplete box for person. Create
 * a combobox for the support roles.
 */
$(document).ready(function() {
	
	$(function() {
		$( "#replacement" ).combobox();
		$( "#toggle" ).click(function() {
			$( "#replacement" ).toggle();
		});
	});	
	
	//Bind the click event here
  	$('#remoteAdd').bind('click', function() {		
  		//validate person and role have been checked
  		if(validateReplacementSelection()){
  			
  			//set up the ajax call
	  		$.ajax({
	  			url: '/dcmd/asset/addReplacement',
	  			data: getReplacementParamsForAdd(),
	  			type: 'POST',
	  			success: updateReplacements,
	  			error: function(){
	  				alert('The addition of the replacement was not complete.');
	  			}
	  		});
  		}
	});
  
	addDeleteButtons();
	addEditReplacementsTableStyles();
});

/*
 * Get the value of the person and role dropdown
 * boxes and ensure they are not empty strings.
 * If so, show error message and return false.
 */
function validateReplacementSelection(){
	var msg='';
	if($('#replacement').val() == ''){
		msg += 'You must select a replacement asset from the dropdown.';
	}
    /*
	if($('#priority').val() == ''){
		msg += 'You must select a support role from the dropdown.';
	}
	*/
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
function updateReplacements(data, status, jqXHR){
	
	//get count of data rows so we can see whether we added any new data
	var rowCount = $('#replacementTable tbody tr').length;

	//clear out all table rows not in thead
	$('#replacementTable tbody tr').remove();
	
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
		
		//newRow += getIdRow(data[rowIndex].id)
				
		//add the replacement <td>
		newRow += "<td>"
		newRow += data[rowIndex].replacement;
		newRow += "</td><td>";
		
		//add the priority <td>
		newRow += data[rowIndex].priority;
        newRow += "</td><td>"

        //add the ready_date <td>
        newRow += data[rowIndex].ready_date;
        newRow += "</td><td>"

        //add the notes <td>
        newRow += data[rowIndex].replacement_notes;
		
		//close open tags and append the row to the table
		newRow += "</td>"
		newRow += "</tr>";
		$('#replacementTable').append(newRow);
	}
	
	//add the delete buttons
	addDeleteButtons();
	
	//figure out if anything changed; alert user if supporter was not added
	var newRowCount = $('#replacementTable tbody tr').length;
	if(rowCount == newRowCount){
		alert("The update of replacements for this asset has failed!");
	}else{
		addTableStyles(); //function in application.js
	}
}

/*
 * Get a <td> containing a linkable id for person support role
 */
function getIdRow(id){
	var td = "<td>";
	td += "<a class='white-link' href='replacement/show/"
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
function getReplacementParamsForAdd(){
	//alert("person: " + $('#person').val());
    alert("replacementAsset: " + $('#replacementAsset').val() + ", priority: " + $('#priority').val()
        + ", ready_date: " + $('#ready_date').val() + ", replacement_notes: " + $('#replacement_notes').val()
        + ", assetId: " + $('#id').val());

	var params = { assetId:$('#id').val(), replacementAsset:$('#replacementAsset').val(), priority:$('#priority').val(),
        ready_date:$('#ready_date').val(), replacement_notes:$('#replacement_notes').val()
        };
	return $.param(params);
}

/*
 * Formats the params to be sent in the ajax request to remove a supporter (VendorController#removeSupporter)
 */
function getReplacementParamsForRemove(id){
	var params = {replacementAsset:$('#replacementAsset').val(), priority:$('#priority').val()};
	return $.param(params);
}

/*
 * Loop through all tbody rows in #supportTable and add a delete button column
 */
function addDeleteButtons(){
	if($('#replacementTable thead tr th').length == 3){
		$('#replacementTable thead tr').append("<th>&nbsp;</th>");
	}
	$('#replacementTable tbody tr').each(function(index){
		var id = this.children[0].innerText.trim();
		var del = getDeleteButton(index);
		$(this).append(del);
		var buttonId = "#remoteDelete" + index;
		$(buttonId).bind('click', function(){
			//alert('delete ' + id + '?');
	  		$.ajax({
	  			url: '/dcmd/asset/removeReplacement',
	  			data: getReplacementParamsForRemove(id),
	  			type: 'POST',
	  			success: updateReplacements,
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

function addEditReplacementsTableStyles(){
	$("#editReplacementsTable").css({borderCollapse:"collapse", borderSpacing:"0px", marginBottom:"10px"});
	$("#editReplacementsTable th").addClass("ui-state-default").css({border:"0px"});
	$("#editReplacementsTable td").addClass("ui-widget-content").css({border:"0px"});
}


