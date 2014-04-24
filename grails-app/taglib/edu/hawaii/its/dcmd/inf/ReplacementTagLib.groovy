package edu.hawaii.its.dcmd.inf

class ReplacementTagLib {

	public static namespace = "s"
	
	/*
	 * Renders the necessary javascript to create
	 * a jquery autocompletebox that searches
	 * Person#searchJSON for a list of users, a
	 * hidden field that will contain the id of the
	 * selected person (#personId), and a text
	 * field (#person) where the user will enter
	 * the search term.
	 *
	 * Consult that method to see what params 
	 * are used in the search.
	 * 
	 * @attr - all attributes currently ignored
	 * @body - no body is used in this tag
	 */
	def replacementTag = { attrs, body ->
		out << g.javascript(src:"assetReplacements.js")
		out << g.hiddenField(name:"replacementId")
		out << g.textField(name:"replacement", class:"ui-widget ui-widget-content")
	}

}
