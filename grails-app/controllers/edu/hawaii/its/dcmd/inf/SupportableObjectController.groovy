package edu.hawaii.its.dcmd.inf

import grails.converters.*
import edu.hawaii.its.dcmd.inf.SupportableObject

class SupportableObjectController {
	
	def supportableObjectService
	
    def searchJSON = {
		render supportableObjectService.searchJSON(params) as JSON
	}
	
	def scaffold = SupportableObject
}
 