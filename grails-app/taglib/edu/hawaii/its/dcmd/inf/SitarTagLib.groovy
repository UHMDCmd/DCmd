package edu.hawaii.its.dcmd.inf

class SitarTagLib {
	
	static namespace = 'dcmd'

	/*
	 * Provide a tag for create and edit pages that describes the flagging of required fields.
	 */
	def requiredInputFieldsReminder = { attrs, body ->
		out << "<p><font color=\"red\">*</font> = required fields </p>"
	}
	
	/*
	 * Provide a tag for flagging required fields.
	 */
	def requiredInputFieldFlag = { attrs, body ->
		out << "<font color=\"red\">* </font>"
	}
}
