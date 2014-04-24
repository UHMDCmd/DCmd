package edu.hawaii.its.dcmd.inf

import grails.converters.JSON
import org.hibernate.criterion.Order
import de.andreasschmitt.export.taglib.util.RenderUtils

class ApplicationController {

    def applicationService
    def generalService
    def serviceService
    def personService

    def scaffold = Application

    def static List<String> supportFilterHostList = new ArrayList()


/*******************************
/*   Clone Module
 *******************************/
    def clone = {
        println("this is the orginal id: " + params.id)
        Application currentApp = Application.get(params.id)

        println("Current Application: " + currentApp.applicationTitle)
        Application cloneInstance = new Application()

            cloneInstance = new Application()
            cloneInstance.properties = currentApp.properties

            //disable duplication of these attributes
            cloneInstance.supporters = null
            cloneInstance.supportStaff = null
            cloneInstance.services = null
            cloneInstance.tiers = null

        cloneInstance.save(failOnError: true,flush: true)

        if (cloneInstance){
            println("clone name: " + cloneInstance.applicationTitle)
            println("clone id : " + cloneInstance.id)
        }

        boolean cloned = true;
        session.setAttribute("cloned",cloned)

        redirect (url:"/application/edit?id=${cloneInstance.id}")

    }

    //clears session variables -- called in list and update
    //This is not in a service class because session vars cannot be accessed in services
    void resetCloneVar() {
        if(session.valueNames.contains("cloned")){
            session.removeAttribute("cloned")
            //  println("reset clone var...")
        }
    }
    //***********************************

    def save = {
        System.out.println(params)
//        params.id = params.objectId

        if(params.statusSelect) {
            params.status = generalService.createOrSelectStatus(params)
        }
        if(params.environmentSelect) {
            params.env = generalService.createOrSelectEnv(params)
        }
        def applicationInstance = new Application(params)
        String saveOption = params.option //selects type of save to do

        if (applicationInstance.save(flush: true)) {

            if(params.personSelect) {
                generalService.createSupportRole(applicationInstance, Person.get(params.personSelect), 'Technical', RoleType.findByRoleName("Primary SA")?.id)
            }
            //save options
            if (saveOption.equals("saveEdit")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'application.label', default: 'Application'), applicationInstance.id])}"
                redirect(url: "/application/edit?id=${applicationInstance.id}")
            }
            else if (saveOption.equals("saveList")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'application.label', default: 'Application'), applicationInstance.id])}"
                redirect(url: "/application/list")
            }
            else if (saveOption.equals("saveCreate")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'application.label', default: 'Application'), applicationInstance.id])}"
                redirect(url: "/application/create")
            }
            else{
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'application.label', default: 'Application'), applicationInstance.id])}"
                redirect(url: "/application/show?id=${applicationInstance.id}")
            }
         /*   flash.message = "${message(code: 'default.created.message', args: [message(code: 'application.label', default: 'Application'), applicationInstance.id])}"
            redirect(url: "/application/show?id=${applicationInstance.id}")*/
        }
        else {
            render(view: "create", model: [hostInstance: applicationInstance])

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

        resetCloneVar()

        //get the instance being updated
        def applicationInstance = Application.get(params.id)

        if(params.statusSelect) {
            params.status = generalService.createOrSelectStatus(params)
        }
        if(params.environmentSelect) {
            params.env = generalService.createOrSelectEnv(params)
        }

        if (applicationInstance) {
       //     System.out.println(params)

            log.debug "asset instance not null"

            //update the object with the params and remove deleted notes
            applicationInstance.properties = params
//			assetService.removeDeletedNotes(assetInstance)

            //save and redirect
            if (!applicationInstance.hasErrors() && applicationInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'application.label', default: 'Application'), applicationInstance.id])}"
                redirect(url: "/application/show?id=${applicationInstance.id}")
            } else {
                render(view: "edit", model: [applicationInstance: applicationInstance])
            }

            //asset not found; no edits can be performed; show the list
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'application.label', default: 'Application'), params.id])}"
            redirect(action: "list")
        }
    }

    def supportList() {

    }

    /*****************************************************************/
    /* Main Application List Grid
    /*****************************************************************/
    def listAll = {
        params.supportFilterHostList = supportFilterHostList

        def jsonData = applicationService.listAll(params)
        render jsonData as JSON
    }

    def listAllServiceSubGrid = {
        def jsonData = applicationService.listAllServiceSubGrid(params)
        render jsonData as JSON
    }

    // Export action for the main list Application page
    def exportListAll = {
        params.rows = Integer.MAX_VALUE
        params.supportFilterHostList = supportFilterHostList

        def jsonData = applicationService.listAll(params)
        def theList = []
        jsonData.rows.each { theRow ->
            if(!theRow.cell[6])
                theRow.cell[6] = ""

            def tempVals = [
                    Application: theRow.cell[0].replaceAll("\\<.*?>",""),
                    Environment: theRow.cell[1],
                    Description: theRow.cell[2],
                    Status:  theRow.cell[3],
                    'Application Primary SA': theRow.cell[4].replaceAll("\\<.*?>",""),
                    'General note': theRow.cell[6]
            ]
            theList.add(tempVals)
        }

        render theList as JSON
    }

    def listAllSupportList = {
        params.supportFilterHostList = supportFilterHostList

        def jsonData = applicationService.listAllSupportList(params)
        render jsonData as JSON
    }
    def editAllSupportList = {
        def jsonData = applicationService.editAllSupportList(params)
        render jsonData as JSON
    }

    def exportSupportList = {
        params.rows = Integer.MAX_VALUE
        params.supportFilterHostList = supportFilterHostList

        def jsonData = applicationService.listAllSupportList(params)
        def theList = []
        jsonData.rows.each { theRow ->
            if(!theRow.cell[13])
                theRow.cell[13] = ""

            def tempVals = [
                    Application: theRow.cell[1].replaceAll("\\<.*?>",""),
                    Envionrment: theRow.cell[2],
                    'Project Manager': theRow.cell[3].replaceAll("\\<.*?>",""),
                    'Functional Lead': theRow.cell[4].replaceAll("\\<.*?>",""),
                    'Developer Lead': theRow.cell[5].replaceAll("\\<.*?>",""),
                    'Technical Lead': theRow.cell[6].replaceAll("\\<.*?>",""),
                    'Primary SA': theRow.cell[7].replaceAll("\\<.*?>",""),
                    'Secondary SA':theRow.cell[8].replaceAll("\\<.*?>",""),
                    'Tertiary SA': theRow.cell[9].replaceAll("\\<.*?>",""),
                    'Primary DBA': theRow.cell[10].replaceAll("\\<.*?>",""),
                    'Secondary DBA': theRow.cell[11].replaceAll("\\<.*?>",""),
                    'Tertiary DBA': theRow.cell[12].replaceAll("\\<.*?>",""),
                    'General Notes': theRow.cell[13].replaceAll("\\<.*?>","")
            ]
            theList.add(tempVals)
        }

        render theList as JSON
    }


    /*****************************************************************/
    /* Services Grid
    /*****************************************************************/

    def listServiceGrid = {
        def jsonData = applicationService.listServiceGrid(params)
        render jsonData as JSON
    }

    def editServiceGrid = {
        def jsonData = applicationService.editServiceGrid(params)
        render jsonData as JSON
    }


    /*****************************************************************/
    /* Tiers Grid
    /*****************************************************************/

    def listTierGrid = {
        def jsonData = applicationService.listTierGrid(params)
        render jsonData as JSON
    }

    def editTierGrid = {
        def jsonData = applicationService.editTierGrid(params)
        render jsonData as JSON

    }

    def listTierSubGrid = {
        def jsonData = serviceService.listTiersSubGrid(params)
        render jsonData as JSON
    }




    def listHosts = {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentApplication = Application.get(params.applicationId)

        def assignments = ApplicationServerAssignment.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("application.id", currentApplication.id)
        }
        def totalRows = assignments.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = assignments?.collect { [
                ell: ['', it.host.hostname, it.resourceType, it.amountReserved, it.unitType, it.assignmentNote], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

    def editHosts = {
        def item = null
        def message = ""
        def state = "FAIL"
        def id

        if (params.host) {
            params.host = Host.get(params.host)
        }

        // determine our action
        switch (params.oper) {
            case 'add':
                // add instruction sent
                params.application = Application.get(params.applicationId)
                item = new ApplicationServerAssignment(params)
//                item.main_asset = Asset.get(params.assetId)
//                System.out.println(params)
                if (! item.hasErrors() && item.save()) {
                    message = "Assigned ${params.host.hostname} to for ${params.application}"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.allErrors.get(0).toString()
                }
                break;
            case 'edit':
                item = ApplicationServerAssignment.get(params.id)
                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    message = "Assignment of ${item.host.hostname} Updated for ${params.application}"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = ApplicationServerAssignment.get(params.id)
                if (item) {
                    id = item.id
                    if (! item.hasErrors() && item.delete()) {
//                        message = "Replacement ${params.replacement} for Asset ${params.main_asset} Deleted."
                        state = "OK"
                    }
                }
                break;
        }

        def response = [message:message,state:state,id:id]

        render response as JSON
    }



    def listEnvironmentsAsSelect={
        def lst = Environment.findAll()

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listApplicationsAsSelect = {
        def response = applicationService.listAppsAsSelect()
        render response
    }

    def listAppsAsSelect = {
        def apps = Application.createCriteria().list() {
            order('applicationTitle', 'asc')
        }
        def theList = []

        apps.each {
            def tmp = [id:"${it.id}", text: it.toString()]
            theList.add(tmp)
        }
        def jsonData = [retVal: true, theList: theList]
        render jsonData as JSON
    }

    def listServicesForAppAsSelect = {
        def lst = Service.createCriteria().list() {
            eq('application.id', params.appId.toLong())
            order('serviceTitle', 'asc')
        }
        System.out.println(lst)

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def getStatus = {
        def jsonData
        def theStatus = Application.get(params.appId)?.status
        if(theStatus) {
            jsonData = [retVal:true, statusId: theStatus?.id]
        }
        else
            jsonData = [retVal: true, statusId: 1]

        render jsonData as JSON
    }

    def saveSupportFileFilter = {
        InputStream is = params.uploadField?.getInputStream()
        def fileString = is.readLines()
        is.close()

        supportFilterHostList = fileString
        def response = [fileData: fileString, state: "OK", id: 1]
        render response as JSON
    }

    def clearSupportFileFilter = {
        supportFilterHostList = []
        def response = [state: "OK", id: 1]
        render response as JSON
    }

}
