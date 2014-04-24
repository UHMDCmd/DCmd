package edu.hawaii.its.dcmd.inf

import java.security.Identity;
import java.text.MessageFormat;

import javax.swing.JTextArea
import grails.converters.JSON
import edu.hawaii.its.dcmd.inf.Vendor

class VendorController {

	def scaffold = Vendor
	def vendorService, personSupportRoleService, noteService
	
	def listJSON = {
		render vendorService.listVendors(params) as JSON
	}
	
	
	def save = {
		log.debug "params: ${params}"
		
		def vendorInstance = new Vendor(params)
		
		vendorInstance = vendorService.createWithSupporters(vendorInstance)
				
		if (vendorInstance.save(flush: true)) {
			log.debug vendorInstance.supporters.inspect()
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'vendor.label', default: 'Vendor'), vendorInstance.id])}"
			redirect(action: "show", id: vendorInstance.id)
	
			}
		else {
			render(view: "create", model: [vendorInstance: vendorInstance])
		}
	}

	
	def edit = {
		cache(false)
		def vendorInstance = Vendor.get(params.id)
		if (!vendorInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'vendor.label', default: 'Vendor'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [vendorInstance: vendorInstance]
		}
	}

		

}

