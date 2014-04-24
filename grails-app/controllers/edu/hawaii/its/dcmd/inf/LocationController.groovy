package edu.hawaii.its.dcmd.inf

import grails.converters.JSON
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil

class LocationController {

    def scaffold = Location

    def assetService

    def save = {

        String saveOption = params.option //selects type of save to do

        def locationInstance = new Location(params)
    //save and redirect or re-edit the new asset
        if (locationInstance.save(flush: true)) {
            //save options
            if (saveOption.equals("saveEdit")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.id])}"
                redirect(url: "/location/edit?id=${locationInstance.id}")
            }
            else if (saveOption.equals("saveList")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.id])}"
                redirect(url: "/location/list")
            }
            else if (saveOption.equals("saveCreate")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.id])}"
                redirect(url: "/location/create")
            }
            else{
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.id])}"
                redirect(url: "/location/show?id=${locationInstance.id}")
            }

        } else {
            render(view: "create", model: [locationInstance: locationInstance])
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

        //clean up the params
        assetService.cleanseCloneParams(params)
        assetService.logParams(params)

        //get the instance being updated
        def locationInstance = Location.get(params.id.toLong())

        if (locationInstance) {
            System.out.println(params)
            log.debug "location instance not null"
            locationInstance.properties = params

            //save and redirect
            if (!locationInstance.hasErrors() && locationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'location.label', default: 'Location'), locationInstance.id])}"
                redirect(url: "/location/show?id=${locationInstance.id}")
            } else {
                render(view: "edit", model: [locationInstance: locationInstance])
            }

            //asset not found; no edits can be performed; show the list
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])}"
            redirect(action: "list")
        }
    }

    def listAllLocation = {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows


        def locations = Location.createCriteria().list(max: maxRows, offset: rowOffset) {
            if (params.locationDescription) ilike('locationDescription', "%${params.locationDescription}%")

            order(sortIndex, sortOrder)
        }

        def totalRows = locations.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        //def results = assets?.collect { [ cell: ['', it.itsId, it.cluster, it.assetStatus, it.isAvailableForParts, it.lastUpdated, it.serialNo, it.generalNote], id: it.id ] }
        def results = locations?.collect { [ cell: [it.locationDescription, it.addr, it.building, it.roomNum, it.getRacks(), it.getServers(), it.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

}
