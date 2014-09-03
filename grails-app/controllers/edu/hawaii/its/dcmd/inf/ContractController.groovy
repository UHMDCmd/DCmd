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

import java.util.Collection;
import grails.converters.JSON
import edu.hawaii.its.dcmd.inf.Contract
import edu.hawaii.its.dcmd.inf.ContractPaymentHistory

class ContractController {

	def scaffold = true
	def contractService, personSupportRoleService 

	/* the following are needed if we redesign for AJAX calls for adding/deleting forms.
	 * 
	 def contractFormTypeService, contractFeatureTypeService
	 def addToRequiredRenewalForms = {
	 log.debug ("addToRequiredRenewalForms: ${params}")
	 def contract = contractService.getContractAndAddNewRequiredRenewalForm(
	 params.long('contractId'),
	 params.long('requiredRenewalForm')
	 )
	 def requiredRenewalForms = contractService.getRequiredRenewalFormsForJSON(contract.requiredRenewalForms)
	 log.debug ("requiredRenewalForms: ${requiredRenewalForms}")
	 render requiredRenewalForms as JSON
	 }
	 def removeFromRequiredRenewalForms = {
	 log.debug ("removeFromRequiredRenewalForms: ${params}")
	 log.debug ("params: ${params.long('requiredRenewalForm') ?: 'null'}")
	 def contract = contractService.getContractAndRemoveRequiredRenewalForm(
	 params.long('contractId'),
	 params.long('requiredRenewalForm')
	 )
	 def requiredRenewalForms = contractService.getRequiredRenewalFormsForJSON(contract.requiredRenewalForms)
	 log.debug ("requiredRenewalForms: ${requiredRenewalForms}")
	 render requiredRenewalForms as JSON
	 }
	 */


	def save = {
		def contractInstance = new Contract(params)
		
//		// find the requiredRenewalForms that were added then deleted in same form submission...
//		def _toBeRemoved = contractInstance.requiredRenewalForms.findAll {it == null}
//
//		// ...and remove them from the "lazy List" to make the list indices contiguous.
//		if (_toBeRemoved) {
//			contractInstance.requiredRenewalForms.removeAll(_toBeRemoved)
//		}
//
//		if (contractInstance.save(flush: true)) {
//			flash.message = "${message(code: 'default.created.message', args: [message(code: 'contract.label', default: 'Contract'), contractInstance.id])}"
//			redirect(action: "show", id: contractInstance.id)
//		}
//		else {
//			render(view: "create", model: [contractInstance: contractInstance])
//		}
//		
		contractInstance = contractService.createWithSupporters(contractInstance)
		
		if (contractInstance.save(flush: true)) {
			log.debug contractInstance.contractSupporters.inspect()
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'contract.label', default: 'Contract'), contractInstance.id])}"
			redirect(action: "show", id: contractInstance.id)
			//render(view:"create", model:[contractInstance: contractInstance])
			
			
	
			}
		else {
			render(view: "create", model: [contractInstance: contractInstance])
			
		}
	}

	
	def edit = {
		cache(false)
		def contractInstance = Contract.get(params.id)
		if (!contractInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contract.label', default: 'Contract'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [contractInstance: contractInstance]
		}
	}
	

	
	def update = {
		//println "ContractController params: ${params.inspect()}"
		
		def contractInstance = Contract.get(params.id)
		
		if (contractInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (contractInstance.version > version) {

					contractInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
						message(code: 'contract.label', default: 'Contract')]
					as Object[], "Another user has updated this Contract while you were editing")
					render(view: "edit", model: [contractInstance: contractInstance])
					return
				}
			}
			contractInstance.properties = params

			// find the requiredRenewalForms that are marked for deletion
			def _toBeDeleted = contractInstance.requiredRenewalForms.findAll {(it?.deleted || (it == null))}

			// if there are requiredRenewalForms to be deleted remove them all
			if (_toBeDeleted) {
				contractInstance.requiredRenewalForms.removeAll(_toBeDeleted)
			}

            println contractInstance.properties
			if (!contractInstance.hasErrors() && contractInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'contract.label', default: 'Contract'), contractInstance.id])}"
				redirect(action: "show", id: contractInstance.id)
			}
			else {
				render(view: "edit", model: [contractInstance: contractInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contract.label', default: 'Contract'), params.id])}"
		redirect(action: "list")
		}
	}
	
def addPaymentHistory = {
	log.debug "params: ${params.inspect()}"
	def contractInstance = Contract.get(params.id)
	def paymentHistoryInstance = new ContractPaymentHistory()
		
	render(view:"/contractPaymentHistory/create",  model: [contractInstance: contractInstance, paymentHistoryInstance: contractPaymentHistory.get(params['contractPaymentHistory.id'] as Long)])
	
	}	

	def searchJSON = {
		log.debug "params: ${params.inspect()}"
		def result = []
		//	   requiredRenewalForms.sort{formType -> formType.toString()}.each{s ->
		//		   result << [id:s.id, contractFormType:s.toString()]
		//	   }
		//	   render result as JSON
	}

}
