package org.grails.jquery.datatables

class JQueryDataTablesTagLib {
   static namespace = "jqDT"
   
   def resources = { attrs, body ->
     String type = attrs.remove("type")
     def jqueryUi = attrs.remove("jqueryUi")
	   def minified = grailsApplication.config.jqueryDataTables.get('minified', true)
		 if (!type) {
		   renderJavaScript g.resource(plugin:"jqueryDatatables", dir:"js", file:"jquery.dataTables.${minified ? 'min.js' : 'js'}")
		   renderCSS g.resource(plugin:"jqueryDatatables", dir:"css", file:"demo_table${jqueryUi ? '_jui.css' : '.css'}")
		 } else if (type.equals("js")) {
       renderJavaScript g.resource(plugin:"jqueryDatatables", dir:"js", file:"jquery.dataTables.${minified ? 'min.js' : 'js'}")
		 } else if (type.equals("css")) {
		   renderCSS g.resource(plugin:"jqueryDatatables", dir:"css", file:"demo_table${jqueryUi ? '_jui.css' : '.css'}")
		  }	
   }
   
  private renderJavaScript(def url) {
	   out << '<script type="text/javascript" src="' + url + '"></script>\n'
   }   
   
	private renderCSS(def url) {
    out << '<link rel="stylesheet" type="text/css" media="screen" href="' + url + '" />\n'
	}	   
}
