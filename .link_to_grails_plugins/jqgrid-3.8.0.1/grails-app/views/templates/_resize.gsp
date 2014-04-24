$(window).resize(function() {
	$('#${attrs.id}Grid').fluidGrid({
	      base:'#${attrs.id}Wrapper',
	      offset: ${attrs.resizeOffset ?: -2}
	});
});