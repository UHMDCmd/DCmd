package edu.hawaii.its.dcmd.inf

class ContractFeatureTypeController {

	def scaffold = true
	
	/**
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [contractFeatureTypeInstanceList: ContractFeatureType.list(params), contractFeatureTypeInstanceTotal: ContractFeatureType.count()]
    }

    def create = {
        def contractFeatureTypeInstance = new ContractFeatureType()
        contractFeatureTypeInstance.properties = params
        return [contractFeatureTypeInstance: contractFeatureTypeInstance]
    }

    def save = {
        def contractFeatureTypeInstance = new ContractFeatureType(params)
        if (contractFeatureTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'contractFeatureType.label', default: 'ContractFeatureType'), contractFeatureTypeInstance.id])}"
            redirect(action: "show", id: contractFeatureTypeInstance.id)
        }
        else {
            render(view: "create", model: [contractFeatureTypeInstance: contractFeatureTypeInstance])
        }
    }

    def show = {
        def contractFeatureTypeInstance = ContractFeatureType.get(params.id)
        if (!contractFeatureTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contractFeatureType.label', default: 'ContractFeatureType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [contractFeatureTypeInstance: contractFeatureTypeInstance]
        }
    }

    def edit = {
        def contractFeatureTypeInstance = ContractFeatureType.get(params.id)
        if (!contractFeatureTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contractFeatureType.label', default: 'ContractFeatureType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [contractFeatureTypeInstance: contractFeatureTypeInstance]
        }
    }

    def update = {
        def contractFeatureTypeInstance = ContractFeatureType.get(params.id)
        if (contractFeatureTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (contractFeatureTypeInstance.version > version) {
                    
                    contractFeatureTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'contractFeatureType.label', default: 'ContractFeatureType')] as Object[], "Another user has updated this ContractFeatureType while you were editing")
                    render(view: "edit", model: [contractFeatureTypeInstance: contractFeatureTypeInstance])
                    return
                }
            }
            contractFeatureTypeInstance.properties = params
            if (!contractFeatureTypeInstance.hasErrors() && contractFeatureTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'contractFeatureType.label', default: 'ContractFeatureType'), contractFeatureTypeInstance.id])}"
                redirect(action: "show", id: contractFeatureTypeInstance.id)
            }
            else {
                render(view: "edit", model: [contractFeatureTypeInstance: contractFeatureTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contractFeatureType.label', default: 'ContractFeatureType'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def contractFeatureTypeInstance = ContractFeatureType.get(params.id)
        if (contractFeatureTypeInstance) {
            try {
                contractFeatureTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'contractFeatureType.label', default: 'ContractFeatureType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'contractFeatureType.label', default: 'ContractFeatureType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'contractFeatureType.label', default: 'ContractFeatureType'), params.id])}"
            redirect(action: "list")
        }
    }*/
}
