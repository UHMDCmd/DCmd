/*
 * Copyright (c) 2014 University of Hawaii
 *
 * This file is part of DataCenter metadata (DCmd) project.
 *
 * DCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DCmd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCmd.  It is contained in the DCmd release as LICENSE.txt
 * If not, see <http://www.gnu.org/licenses/>.
 */

package edu.hawaii.its.dcmd.inf

class PersonTagLib {

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
	def person = { attrs, body -> 
		out << g.javascript(src:"person.js")
		out << g.hiddenField(name:"personId")
		out << g.textField(name:"person", class:"ui-widget ui-widget-content")
	}

}
