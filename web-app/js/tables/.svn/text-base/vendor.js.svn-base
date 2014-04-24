$(document).ready(function() {
	
	$('#list').dataTable({
		bProcessing: true,
		bServerSide: true,
		iSortCol_0: 2,
		sAjaxSource: 'listJSON',
		bJQueryUI: true,
		aoColumnDefs: [
		               //col 0 (id) not visible
		               {bVisible: false, aTargets: [0]},
		               
		               //col 2 (vendor name) link renderer; links to show vendor
		               {
		            	   fnRender: function(o){
		            	   		return "<a href='show/" + o.aData[0] + "'>" + o.aData[2] + "</a>"
		            	   },
		            	   bUseRendered: false,
		            	   aTargets: [2]
		               }],	
		oTableTools: {
			sDom: 'T<"clear">lfrtip',	
			sSwfPath: "/swf/copy_cvs_xls_pdf.swf"
		}
		
	});
	
});