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

import edu.hawaii.its.dcmd.inf.Contractmin

class ContractminController {

	def scaffold = true
	def contractService

	def save = {
		def contractminInstance = new Contractmin(params)

		// find the requiredRenewalForms that were added then deleted in same form submission...
		def _toBeRemoved = contractminInstance.requiredRenewalForms.findAll {it == null}

		// ...and remove them from the "lazy List" to make the list indices contiguous.
		if (_toBeRemoved) {
			contractminInstance.requiredRenewalForms.removeAll(_toBeRemoved)
		}

		if (contractminInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'contract.label', default: 'Contract'), contractminInstance.id])}"
			redirect(action: "show", id: contractminInstance.id)
		}
		else {
			render(view: "create", model: [contractminInstance: contractminInstance])
		}
	}

    def show = {
        def contractminInstance = Contractmin.get(params.id)
        if (!contractminInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contractmin.label', default: 'Contractmin'), params.id])}"
            redirect(action: "list")
        }
        else {
            [contractminInstance: contractminInstance]
        }
    }

    def edit = {
        def contractminInstance = Contractmin.get(params.id)
        if (!contractminInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contractmin.label', default: 'Contractmin'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [contractminInstance: contractminInstance]
        }
    }

    def update = {
        def contractminInstance = Contractmin.get(params.id)
        if (contractminInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (contractminInstance.version > version) {

                    contractminInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'contractmin.label', default: 'Contractmin')] as Object[], "Another user has updated this Contractmin while you were editing")
                    render(view: "edit", model: [contractminInstance: contractminInstance])
                    return
                }
            }
            contractminInstance.properties = params
            if (!contractminInstance.hasErrors() && contractminInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'contractmin.label', default: 'Contractmin'), contractminInstance.id])}"
                redirect(action: "show", id: contractminInstance.id)
            }
            else {
                render(view: "edit", model: [contractminInstance: contractminInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contractmin.label', default: 'Contractmin'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def contractminInstance = Contractmin.get(params.id)
        if (contractminInstance) {
            try {
                contractminInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'contractmin.label', default: 'Contractmin'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'contractmin.label', default: 'Contractmin'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contractmin.label', default: 'Contractmin'), params.id])}"
            redirect(action: "list")
        }
    }
}
