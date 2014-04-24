package edu.hawaii.its.dcmd.inf

import grails.converters.JSON

class TierController {

    def serviceService
    def tierService
    def generalService

    def scaffold = Tier

    def save = {
        System.out.println(params)
//        params.id = params.objectId

        if(params.application) {
            params.mainApp = Application.get(params.application)
        }
        if (params.host)
            params.host = Host.get(params.host)

        def tierInstance = new Tier(params)
        String saveOption = params.option //selects type of save to do

        if (tierInstance.save(flush: true)) {

            //save options
            if (saveOption.equals("saveEdit")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'tier.label', default: 'Tier'), tierInstance.id])}"
                redirect(url: "/tier/edit?id=${tierInstance.id}")
            }
            else if (saveOption.equals("saveList")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'tier.label', default: 'Tier'), tierInstance.id])}"
                redirect(url: "/tier/list")
            }
            else if (saveOption.equals("saveCreate")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'tier.label', default: 'Tier'), tierInstance.id])}"
                redirect(url: "/tier/create")
            }
            else{
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'tier.label', default: 'Tier'), tierInstance.id])}"
                redirect(url: "/tier/show?id=${tierInstance.id}")
            }
         /*   flash.message = "${message(code: 'default.created.message', args: [message(code: 'tier.label', default: 'Tier'), tierInstance.id])}"
            redirect(url: "/tier/show?id=${tierInstance.id}")*/
        }
        else {
            System.out.println(tierInstance.errors.allErrors.get(0).toString())

            render(view: "create", model: [tierInstance: tierInstance])

        }
    }
    //save options redirected to master save action
    def saveEdit = {
        String saveType;
        forward action: "save", params: [option: "saveEdit"]
    }

    def saveList = {
        String saveType;
        forward action: "save", params: [option: "saveList"]
    }

    def saveCreate = {
        String saveType;
        forward action: "save", params: [option: "saveCreate"]
    }

    def discard = {
        redirect(action:"create")
    }

    def update = {

        //get the instance being updated

        if (params.application)
            params.mainApp = Application.get(params.application)

        if (params.host)
            params.host = Host.get(params.host)

        def tierInstance = Tier.get(params.id)

        if (tierInstance) {
            tierInstance.properties = params

            //save and redirect
            if (!tierInstance.hasErrors() && tierInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tier.label', default: 'Tier'), tierInstance.id])}"
                redirect(url: "/tier/show?id=${tierInstance.id}")
            } else {
                render(view: "edit", model: [tierInstance: tierInstance])
            }

            //asset not found; no edits can be performed; show the list
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tier.label', default: 'Tier'), params.id])}"
            redirect(action: "list")
        }
    }

    /*****************************************************************/
    /* Main Tier List Grid
    /*****************************************************************/

    def listAll = {
        def jsonData = tierService.listAll(params)
        render jsonData as JSON
    }

    def listAllSubGrid = {
        def jsonData = tierService.listAllSubGrid(params)
        render jsonData as JSON
    }

    /*****************************************************************/
    /* Services Grid
    /*****************************************************************/
    def listServices = {
        def jsonData = tierService.listServices(params)
        render jsonData as JSON
    }

    def editServices = {
        def jsonData = tierService.editServices(params)
        render jsonData as JSON
    }

    /*****************************************************************/
    /* Autocomplete Functions
    /*****************************************************************/

    def envList = {
        render generalService.envList(params) as JSON
    }


    /*********************************************************************************/
    /* Listing functions for select dropdowns
    /*********************************************************************************/

    def listTiersAsSelect = {
        def response = tierService.listTiersAsSelect()
        render response
    }

    def listTierTypesAsSelect = {
        def response = tierService.listTierTypesAsSelect()
        render response
    }


}
