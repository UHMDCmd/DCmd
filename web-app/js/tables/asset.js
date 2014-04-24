
$(document).ready(function() {

	$('#list').dataTable({
		bProcessing: true,			//show Processing... during data load
		bServerSide: true,			//ajax request
		sScrollX:'100%',			//too many columns, must scroll
		sAjaxSource: 'listJSON',	//controller method for all assets
		bJQueryUI: true,			//use jquery theme for styling
		sDom: 'T<"clear">lfrtip',	//sets placement of copy/paste/export
		aoColumnDefs: [
		               //col 0 (id) not visible
		               {bVisible: false, aTargets: [0]},		               
		               {
			               //col 1 (asset name)link renderer; links to show asset
		            	   fnRender: function(o){
		            	   		return "<a href='show/" + o.aData[0] + "'>" + o.aData[1] + "</a>";
		            	   },
		            	   bUseRendered: false,
		            	   aTargets: [1]
		               },
		               {
		            	   //ui-widget-content for green columns; ui-state-default for link coloring
		            	   sClass: 'ui-widget-content', aTargets: ['_all']
		               }],
	    //show the copy/paste/export via the TableTools plugin for jquery datatables
		oTableTools: {
			sSwfPath: "/dcmd/js/TableTools-2.0.1/media/swf/copy_cvs_xls_pdf.swf",
		}
	});
	
	//collapse the table borders
	$('#list').css({borderCollapse:"collapse"});
});