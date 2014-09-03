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

import java.util.logging.Logger;

import grails.converters.JSON

class VendorService {

    static transactional = true
	def personSupportRoleService
	def noteService
	
/*
	def getVendorAndAddNewPersonSupportRole(Long vendorId, PersonSupportRole personSupportRole){
		def vendor = Vendor.get(vendorId)
		vendor.addToSupporters(personSupportRole)
		vendor.save(failOnError:true, flush:true)
		vendor
	}
	
	def getVendorAndRemovePersonSupportRole(Long vendorId, Long personSupportRoleId){
		def vendor = Vendor.get(vendorId)
		def personSupportRole = PersonSupportRole.get(personSupportRoleId)
		vendor.removeFromSupporters(personSupportRole)
		vendor.save(flush:true)
	}
	
	
	
	def getSupportersForJSON(supporters){
		def result = []
		supporters.sort{supporter -> supporter.toString()}.each{s ->
			result << [id:s.id, person:s.person.toString(), supportRole:s.supportRole.toString()]
		}
		log.debug "result: ${result.inspect()}"
		result
	}


   
	def createWithSupporters(Vendor vendor){
		
		// find the supporters that were added then deleted in same form submission...
		def _toBeRemoved = vendor.supporters.findAll {it == null}

		// ...and remove them from the "lazy List" to make the list indices contiguous.
		if (_toBeRemoved) {
			log.debug "had to remove a null instance of PersonSupportRole"
			vendor.supporters.removeAll(_toBeRemoved)
		}

		log.debug vendor.supporters.inspect()
		
		def personSupportRoles = []
		vendor.supporters.each{supporter ->
			log.debug "supporter to be added: ${supporter.inspect()}"
			personSupportRoles << personSupportRoleService.getOrCreatePersonSupportRole(
				supporter.person.id,
				supporter.supportRole.id,
				supporter.supportableObjectType.id)
		}
		
		log.debug "personSupportRoles: ${personSupportRoles.size()}"
		vendor.supporters.clear()
		personSupportRoles.each{ supporter ->
			vendor.addToSupporters(supporter)
		}
		vendor
	}
	
	def createWithNotes(Vendor vendor){
		
		// find the notes that were added then deleted in same form submission...
		def _toBeRemoved = vendor.notes.findAll {it == null}

		// ...and remove them from the "lazy List" to make the list indices contiguous.
		if (_toBeRemoved) {
			log.debug "had to remove a null instance of note"
			vendor.notes.removeAll(_toBeRemoved)
		}

		log.debug vendor.notes.inspect()
		
		def notesArray = []
		vendor.notes.each{note ->
			log.debug "note to be added: ${note.inspect()}"
			notesArray << noteService.getOrCreateNote(
				note.person.id,
				note.message.id,
				note.noteType.id,
				note.supportableObjectType.id)
		}
		
		log.debug "notesArray: ${notesArray.size()}"
		vendor.notes.clear()
		notesArray.each{ note ->
			vendor.addToNotes(note)
		}
		vendor
	}
	
	
	
	def listVendors(Map params) {
		
		log.debug "params: ${params}"
		
		def dataToRender = setupReturnValues()
		dataToRender.sEcho = params.sEcho
		params.iSortCol_0 = params.iSortCol_0 == "0" ? 1 : (int)params.iSortCol_0
		
		log.debug "sort col index: ${params.iSortCol_0}"
		
		def vendors
		def sortableColumns = [ 'id', 'code', 'name']
		
		log.debug sortableColumns[params.iSortCol_0]
		
		def criteria = Vendor.createCriteria()
		
		vendors = criteria.list(max: params.iDisplayLength, offset:params.iDisplayStart){
			if(params.sSearch){
				or{
					like("name", "%${params.sSearch}%")
					like("code", "%${params.sSearch}%")
				}
			}
			order(sortableColumns[params.iSortCol_0], params.sSortDir_0)
		}
		def countCriteria = Vendor.createCriteria()
		def vendorsCount = countCriteria.get(){
			projections{
				rowCount()
			}
		}
		dataToRender.iTotalRecords = vendorsCount
		dataToRender.iTotalDisplayRecords = vendors.getTotalCount()

		vendors?.each { vendor ->
		   dataToRender.aaData << [vendor.id,
								   vendor.code,
								   vendor.name,
								   vendor.phone,
								   vendor.fax,
								   vendor.addressLine1,
								   vendor.addressLine2,
								   vendor.city,
								   vendor.state,
								   vendor.zip]
		}
		dataToRender
	}
	
	private Map setupReturnValues(){
		def dataToRender = [:]
		dataToRender.sEcho = ''
		dataToRender.aaData=[]
		dataToRender
	}
	*/

}
