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

import groovy.xml.MarkupBuilder
import org.springframework.validation.Errors

class InfoAndErrorsTagLib {
	static namespace = "s"
	
	/*
	 * Provides a message banner to show the flash message
	 * 
	 * @attr - attributes are ignored
	 * @body - body is ignored
	 */
	def info = {
		if(flash.message){
			out << "<div class='ui-state-highlight ui-corner-all' style='margin-top: 20px; padding: 0 .7em;'>"
			out << "<p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;'></span>"
			out << flash.message
			out << "</p></div>"
		}
	}
	
	def errorDiv = { attrs, body ->
		out << "<div class='ui-state-error ui-corner-all' style='margin-top: 20px; padding: 0 .7em;'>"
		out << body()
		out << "</div>"
	}

	/*
	 * The following tags and defs were stolen from the grails source at:
	 * grails-1.3.7/src/java/org/codehaus/groovy/grails/plugins/web/taglib/ValidationTagLib.groovy.
	 * The only changes made were to include default jquery-ui styles to the list items.
	 * 
	 * If using the renderErrors tag, you should probably incude it inside the errorDiv tag above.
	 */
	
	/**
	* Loops through each error and renders it using one of the supported mechanisms (defaults to "list" if unsupported).
	*
	* @attr bean REQUIRED The bean to check for errors
	* @attr field The field of the bean or model reference to check
	* @attr model The model reference to check for errors
	*/
   def renderErrors = { attrs, body ->
	   def renderAs = attrs.remove('as')
	   if (!renderAs) renderAs = 'list'

	   if (renderAs == 'list') {
		   def codec = attrs.codec ?: 'HTML'
		   if (codec == 'none') codec = ''

		   out << "<ul>"
		   out << eachErrorInternal(attrs, {
			   out << "<li><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span>${message(error:it, encodeAs:codec)}</li>"
		   })
		   out << "</ul>"
	   }
	   else if (renderAs.equalsIgnoreCase("xml")) {
		   def mkp = new MarkupBuilder(out)
		   mkp.errors() {
			   eachErrorInternal(attrs, {
				   error(object: it.objectName,
						 field: it.field,
						 message: message(error:it)?.toString(),
						   'rejected-value': StringEscapeUtils.escapeXml(it.rejectedValue))
			   })
		   }
	   }
   }
   
   def eachErrorInternal(attrs, body, boolean outputResult = false) {
	   def errorsList = extractErrors(attrs)
	   def var = attrs.var
	   def field = attrs.field

	   def errorList = []
	   for (errors in errorsList) {
		   if (field) {
			   if (errors.hasFieldErrors(field)) {
				   errorList += errors.getFieldErrors(field)
			   }
		   }
		   else {
			   errorList += errors.allErrors
		   }
	   }

	   for (error in errorList) {
		   def result
		   if (var) {
			   result = body([(var):error])
		   }
		   else {
			   result = body(error)
		   }
		   if (outputResult) {
			   out << result
		   }
	   }

	   null
   }

   def extractErrors(attrs) {
	   def model = attrs.model
	   def checkList = []
	   if (attrs.containsKey('bean')) {
		   if (attrs.bean) {
			   checkList << attrs.bean
		   }
	   }
	   else if (attrs.containsKey('model')) {
		   if (model) {
			   checkList = model.findAll {it.value?.errors instanceof Errors}.collect {it.value}
		   }
	   }
	   else {
		   for (attributeName in request.attributeNames) {
			   def ra = request[attributeName]
			   if (ra) {
				   def mc = GroovySystem.metaClassRegistry.getMetaClass(ra.getClass())
				   if (ra instanceof Errors && !checkList.contains(ra)) {
					   checkList << ra
				   }
				   else if (mc.hasProperty(ra, 'errors') && ra.errors instanceof Errors && !checkList.contains(ra.errors)) {
					   checkList << ra.errors
				   }
			   }
		   }
	   }

	   def resultErrorsList = []

	   for (i in checkList) {
		   def errors = null
		   if (i instanceof Errors) {
			   errors = i
		   }
		   else {
			   def mc = GroovySystem.metaClassRegistry.getMetaClass(i.getClass())
			   if (mc.hasProperty(i, 'errors')) {
				   errors = i.errors
			   }
		   }
		   if (errors?.hasErrors()) {
			   // if the 'field' attribute is not provided then we should output a body,
			   // otherwise we should check for field-specific errors
			   if (!attrs.field || errors.hasFieldErrors(attrs.field)) {
				   resultErrorsList << errors
			   }
		   }
	   }

	   resultErrorsList
   }


}
