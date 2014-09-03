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

import java.util.Map;

import grails.converters.JSON

class ContractService {

	
    static transactional = true
	def personSupportRoleService

	def getContractAndAddNewRequiredRenewalForm(Long contractId, Long formId){
		log.debug("contractId: ${contractId}: formId: ${formId}");
		def contract = Contract.get(contractId)
		def requiredRenewalForm = ContractFormType.get(formId)
		contract.addToRequiredRenewalForms(requiredRenewalForm)
		contract.save(failOnError:true, flush:true)
		contract
	}
	
	def getContractAndRemoveRequiredRenewalForm(Long contractId, Long formId){
		log.debug("contractId: ${contractId}: formId: ${formId}");
		def contract = Contract.get(contractId)
		def requiredRenewalForm = ContractFormType.get(formId)
		contract.removeFromRequiredRenewalForms(requiredRenewalForm)
		contract.save(failOnError:true, flush:true)
		contract
	}
	
	/*
	 * Get the Contract's features formatted as JSON.
	 */
    def getContractFeaturesforJSON(contractFeatures) {
		def result = []
		contractFeatures.sort{featureType -> featureType.toString()}.each{s ->
			result << [id:s.id, contractFeatureType:s.contractFeatureType.toString()]
		}
        result
    }
	
	/*
	 * Get the Contract's annual payment history as JSON.
	 */
    def getContractPaymentHistoryForJSON(contractPaymentHistory) {
		def result = []
		contractPaymentHistory.sort{paymentHistory -> paymentHistory.toString()}.each{s ->
			result << [id:s.id, contractPaymentHistory:s.contractPaymentHistory.toString()]
		}
        result
    }
	
	/*
	 * Get the Contract's required annual renewal forms as JSON.
	 */
    def getRequiredRenewalFormsForJSON(Collection requiredRenewalForms) {
		def result = []
		requiredRenewalForms.sort{formType -> formType.toString()}.each{s ->
			result << [id:s.id, contractFormType:s.toString()]
		}
        result
    }
	
	/*
	 * person support role defs
	 */

	
	
	def listContracts(Map params) {
		
		log.debug "params: ${params}"
		
		def dataToRender = setupReturnValues()
		dataToRender.sEcho = params.sEcho
		params.iSortCol_0 = params.iSortCol_0 == "0" ? 1 : (int)params.iSortCol_0
		
		log.debug "sort col index: ${params.iSortCol_0}"
		
		def contracts
		def sortableColumns = [ 'id', 'code', 'name']
		
		log.debug sortableColumns[params.iSortCol_0]
		
		def criteria = Contract.createCriteria()
		
		contracts = criteria.list(max: params.iDisplayLength, offset:params.iDisplayStart){
			if(params.sSearch){
				or{
					like("name", "%${params.sSearch}%")
					like("code", "%${params.sSearch}%")
				}
			}
			order(sortableColumns[params.iSortCol_0], params.sSortDir_0)
		}
		def countCriteria = Contract.createCriteria()
		def contractsCount = countCriteria.get(){
			projections{
				rowCount()
			}
		}
		dataToRender.iTotalRecords = contractsCount
		dataToRender.iTotalDisplayRecords = contracts.getTotalCount()

		contracts?.each { contract ->
		   dataToRender.aaData << [contract.id,
			   				contract.uhContractNo,                         // Assigned by OPRPM.
							contract.uhContractTitle,                       // Usually from the IFB, RFP, etc.
							contract.vendorContractNo,                      // Vendors sometimes assign their own numbers.
							//contract.contractBeginDate,                       // Some contracts are annually renewable.
							//contract.contractEndDate,                         // Note that some contracts are open ended.
							//contract.contractExtensibleEndDate,               // Some contracts can be extended beyond their end date.
							//contract.annualRenewalDeadlineMm,              // The deadline is usually the anniversary date
							//contract.annualRenewalDeadlineDd,              //  of the Begin date.
							//contract.annualRenewalReminderMm,              // An email notice or other automated reminder
							//contract.annualRenewalReminderDd,              //  so provide ample time to renew the contract.
							contract.annualCost]               
		}
		dataToRender
	}
	
	private Map setupReturnValues(){
		def dataToRender = [:]
		dataToRender.sEcho = ''
		dataToRender.aaData=[]
		dataToRender
	}

}

