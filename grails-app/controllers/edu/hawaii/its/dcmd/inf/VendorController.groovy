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

