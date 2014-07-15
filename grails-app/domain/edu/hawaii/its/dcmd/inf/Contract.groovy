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

import org.apache.commons.collections.list.LazyList
import org.apache.commons.collections.FactoryUtils

/**
 * Contracts are used to purchase goods and services and may be annually renewed.
 * The renewal process requires that specific forms be completed and submitted along
 * with the renewal.
 */
class Contract extends SupportableObject {

	
	

	// The hasMany doesn't create an empty list, which will be needed for the
	// lazylists defined below and to support javascript and to keep the unit
	// tests from failing.
	List requiredRenewalForms = new ArrayList()
	List contractFeatures = new ArrayList()
	List contractPaymentHistory = new ArrayList()
	List contractSupporters = new ArrayList()
	
	static hasMany = [
		//vendors:Vendor, 						 // One or more vendors are associated with a contract.
		initialAssetPurchases:Asset,              // Associate initial asset purchases with a contract.
		maintenanceRenewals:Asset,                // Associate asset maintenance and renewals with a contract.
		contractPaymentHistory:ContractPaymentHistory,
		requiredRenewalForms:ContractFormType,   // Many renewal forms may be required.
		contractFeatures:ContractFeatureType,    // A contract may be endowed with many features.
		contractSupporters:PersonSupportRole
	]
	 
	// Contract history is not needed if the contract is deleted.
	// The asset relationships do not require separate tables.
	static mapping = {
		contractPaymentHistory cascade:"all-delete-orphan"
		tablePerHierarchy false
	}

	static mappedBy = [
		initialAssetPurchases:'purchaseContract',   // Track initial purchase for later reporting.
		maintenanceRenewals:'maintenanceContract',  // Track renewals for budget planning.
		]

	// Ensure that the following are nullable so
	// that Grails does not cascade deletes and
	// remove Contracts.
	ContractStatus contractStatus                // Contracts are eventually retired, but are retained.
	HawaiiTaxRate taxRate

	String uhContractNo                          // Assigned by OPRPM.
	String uhContractTitle                       // Usually from the IFB, RFP, etc.
	String vendorContractNo                      // Vendors sometimes assign their own numbers.
	Date contractBeginDate                       // Some contracts are annually renewable.
	Date contractEndDate                         // some contracts are open ended.
	Date contractExtensibleEndDate               // Some contracts can be extended beyond their end date.
	Integer annualRenewalDeadlineMm              // The deadline is usually the anniversary date
	Integer annualRenewalDeadlineDd              //  of the Begin date.
	Integer annualRenewalReminderMm              // An email notice or other automated reminder
	Integer annualRenewalReminderDd              //  so provide ample time to renew the contract.
	Float annualCost                             // Current year costs, previous costs are in payment history.
	Date dateCreated = new Date()
	Date lastUpdated = new Date()

	// Identify this object for Notes.
	String supportableType = "contract"

	static constraints = {
		requiredRenewalForms(nullable:true)
		contractFeatures(nullable:true)
		contractStatus(nullable:true)
		taxRate(nullable:true)
		uhContractNo(nullable:false, unique:true, blank:false)
		uhContractTitle(nullable:false, blank:false)
		contractBeginDate(nullable:false, blank:false)
		contractEndDate(nullable:false, blank:false)
		contractExtensibleEndDate(nullable:true)
		annualCost(nullable:false, min:0.0F)
		annualRenewalDeadlineMm(nullable:false, range:1..12)
		annualRenewalDeadlineDd(nullable:false, range:1..31)
		annualRenewalReminderMm(range:1..12)
		annualRenewalReminderDd(range:1..31)
	
	
		contractSupporters(validator: {
			if(it){
				def invalid
				it.find{ contractsupporter ->
					invalid = contractsupporter.supportableObjectType.name != "Contract"
				}
				return (!invalid)
			}else{ true }

		})
	}

	def getContractSupportersList() {
		return LazyList.decorate(
		contractSupporters,
		FactoryUtils.instantiateFactory(PersonSupportRole.class))
	}

	def getContractFeaturesList() {
		return LazyList.decorate(
		contractFeatures,
		FactoryUtils.instantiateFactory(ContractFeatureType.class)
		)
	}

	def getContractPaymentHistoryList() {
		return LazyList.decorate(
		contractPaymentHistory,
		FactoryUtils.instantiateFactory(ContractPaymentHistory.class)
		)
	}

	def getRequiredRenewalFormsList() {
		return LazyList.decorate(
		requiredRenewalForms,
		FactoryUtils.instantiateFactory(ContractFormType.class)
		)
	}

	String toString(){
		"${uhContractNo} - ${uhContractTitle}"
	}
}

