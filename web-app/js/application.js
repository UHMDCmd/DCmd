var Ajax;
if (Ajax && (Ajax != null)) {
	Ajax.Responders.register({
	  onCreate: function() {
        if($('spinner') && Ajax.activeRequestCount>0)
          Effect.Appear('spinner',{duration:0.5,queue:'end'});
	  },
	  onComplete: function() {
        if($('spinner') && Ajax.activeRequestCount==0)
          Effect.Fade('spinner',{duration:0.5,queue:'end'});
	  }
	});
}

$(document).ready(function() {
	addTableStyles();
});

/*
 * This function also called from the vendorSupporters.js after
 * ajax call to add/remove supporter
 */
function addTableStyles(){
	//alert('styling');
	$(".jquiTable").css({borderCollapse:"collapse"});
	$(".jquiTable th").addClass("ui-state-default");
	$(".jquiTable td").addClass("ui-widget-content");	
	$(".jquiTable tr").hover(function(){
			$(this).children("td").addClass("ui-state-hover");
		},
		function(){
			$(this).children("td").removeClass("ui-state-hover");
	  });
//		$("#editSupportersTable tr").click(function(){		   
//			$(this).children("td").toggleClass("ui-state-highlight");
//		});	
}
