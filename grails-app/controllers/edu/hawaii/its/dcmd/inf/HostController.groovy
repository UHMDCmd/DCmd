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

import grails.converters.JSON
import org.hibernate.criterion.Order

import org.hibernate.Criteria
import grails.gorm.DetachedCriteria
import org.springframework.security.core.context.SecurityContextHolder
import de.andreasschmitt.export.taglib.util.RenderUtils
import grails.rest.*


class HostController {
    static allowedMethods = [save: "POST", update: "POST", testAction: "GET", APIEdit: "PUT", index:"POST"]
    static responseFormats = ['json', 'xml']
    static requestFormats = ['application/json', 'xml']

    def scaffold = true
	
	def hostService
    def generalService
    def personService
    def breadCrumbService

    def exportService   // Export service provided by Export plugin
//    def grailsApplication  //inject GrailsApplication
  /*
    HostController() {
        super(Host)
    }
   */
    // Global variable holding the filter list for supportList file filtering
    def static List<String> supportFilterHostList = new ArrayList()

    def supportList() {

    }

    def reports() {

    }

    def APIEdit(Host hostInstance) {
        respond hostInstance
    }

    def listHosts() {
        respond Host.list()
    }


    def testAction() {

        System.out.println(params)
        Host temp = new Host(params)
        respond temp
    }
/*******************************
 /*   Clone Module
 *******************************/
    def clone = {
        println("this is the orginal id: " + params.id)
        Host currentHost = Host.get(params.id)

        println("Current Host: " + currentHost.hostname)
        Host cloneInstance = new Host()

            cloneInstance.properties = currentHost.properties

            //disable duplication of these attributes
            cloneInstance.supporters = null
            cloneInstance.tiers = null

        cloneInstance.save(failOnError: true,flush: true)

        if (cloneInstance){
            println("clone name: " + cloneInstance.hostname)
            println("clone id : " + cloneInstance.id)
        }

        boolean cloned = true;
        session.setAttribute("cloned",cloned)

        redirect (url:"/host/edit?id=${cloneInstance.id}")

    }

    //clears session variables -- called in list and update
    //This is not in a service class because session vars cannot be accessed in services
    void resetCloneVar() {
        if(session.valueNames.contains("cloned")){
            session.removeAttribute("cloned")
            println("reset clone var...")
        }
    }
 /********************************/


    def save = {
        System.out.println(params)
        if(params.statusSelect)
            params.status = generalService.createOrSelectStatus(params)

        if(params.environmentSelect)
            params.env = generalService.createOrSelectEnv(params)

        if(params.assetSelect) {
            if (params.assetSelect == 'null')
                params.asset = null
            else
                params.asset = Asset.get(params.assetSelect.toLong())
        }
        if(params.type == 'VMWare') {
            if (params.clusterSelect == 'null')
                params.cluster = null
            else
                params.cluster = Cluster.get(params.clusterSelect)
        }
        else {
            params.cluster = null
        }

        def hostInstance = new Host(params)

        String saveOption = params.option //selects type of save to do

		if (hostInstance.save(flush: true)) {
            if(params.personSelect) {
                def primarySA = generalService.createSupportRole(hostInstance, Person.get(params.personSelect), 'Technical', RoleType.findByRoleName("Primary SA")?.id)

            }

           //save options
            if (saveOption.equals("saveEdit")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'host.label', default: 'Host'), hostInstance.id])}"
                redirect(url: "/host/edit?id=${hostInstance.id}")
            }
           else if (saveOption.equals("saveList")){
               flash.message = "${message(code: 'default.created.message', args: [message(code: 'host.label', default: 'Host'), hostInstance.id])}"
                redirect(url: "/host/list")
            }
            else if (saveOption.equals("saveCreate")){
               flash.message = "${message(code: 'default.created.message', args: [message(code: 'host.label', default: 'Host'), hostInstance.id])}"
                redirect(url: "/host/create")
            }
            else{
               flash.message = "${message(code: 'default.created.message', args: [message(code: 'host.label', default: 'Host'), hostInstance.id])}"
            redirect(url: "/host/show?id=${hostInstance.id}")
            }
        }
		else {
            System.out.println(hostInstance.errors.fieldError.toString())
			render(view: "create", model: [hostInstance: hostInstance])
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

	def edit = {
		cache(false)
		def hostInstance = Host.get(params.id)
		if (!hostInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'host.label', default: 'Host'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [hostInstance: hostInstance]
		}
	}

    def update = {

//        resetCloneVar()

        //get the instance being updated
        def hostInstance = Host.get(params.id)
        if(params.statusSelect)
            params.status = generalService.createOrSelectStatus(params)

        if(params.environmentSelect)
            params.env = generalService.createOrSelectEnv(params)

        if(params.assetSelect) {
            if (params.assetSelect == 'null')
                params.asset = null
            else
                params.asset = Asset.get(params.assetSelect.toLong())
        }

        if(params.type == 'VMWare') {
            if (params.clusterSelect == 'null')
                params.cluster = null
            else
                params.cluster = Cluster.get(params.clusterSelect)
        }
        else {
            params.cluster = null
        }


        if (hostInstance) {

            log.debug "asset instance not null"

            //update the object with the params and remove deleted notes
            hostInstance.properties = params

            //save and redirect
            if (!hostInstance.hasErrors() && hostInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'host.label', default: 'Host'), hostInstance.id])}"
                redirect(url: "/host/show?id=${hostInstance.id}")
            } else {
                render(view: "edit", model: [hostInstance: hostInstance])
            }

            //asset not found; no edits can be performed; show the list
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'host.label', default: 'Host'), params.id])}"
            redirect(action: "list")
        }
    }



    /*********************************************************
    * List action contains code for spreadsheet file export
        * Support service methods are located in hostService
        * String values for parameters pulled from other sources
          are located in Host domain
     /********************************************************/
    def list = {

        resetCloneVar()

    }


    /*****************************************************************/
    /* Main Hosts Grid
    /*****************************************************************/
    def listAll = {
        params.supportFilterHostList = supportFilterHostList

        def jsonData = hostService.listAll(params)
        render jsonData as JSON
    }

    // Export action for the main list Host page
    def exportListAll = {
        params.rows = Integer.MAX_VALUE
        params.supportFilterHostList = supportFilterHostList

        def theList = []
        def jsonData = hostService.listAll(params)
        jsonData.rows.each { theRow ->

            if(!theRow.cell[4])
                theRow.cell[4] = ""
            if(!theRow.cell[13])
                theRow.cell[13] = ""

            def tempVals = [
                    'Hostname': theRow.cell[0]?.replaceAll("\\<.*?>",""),
                    'Physical Server': theRow.cell[1]?.replaceAll("\\<.*?>",""),
                    'Host Type': theRow.cell[2],
                    'VM State':  theRow.cell[3],
                    'Environment': theRow.cell[4],
                    'Primary Host SA': theRow.cell[5]?.replaceAll("\\<.*?>",""),
                    'Host OS': theRow.cell[6],
                    'Application(s)': theRow.cell[7]?.replaceAll("\\<.*?>",""),
                    'Service(s)': theRow.cell[8]?.replaceAll("\\<.*?>",""),
                    'Service Primary SA(s)': theRow.cell[9]?.replaceAll("\\<.*?>",""),
                    'Max. Memory (GB)': theRow.cell[10],
                    'Max. CPU (MHz)': theRow.cell[11],
                    'IP Address': theRow.cell[12],
                    'General Notes': theRow.cell[13]
            ]
            theList.add(tempVals)
        }
        render theList as JSON
    }

    /*****************************************************************/
    /* SupportStaff Hosts Grid
    /*****************************************************************/
    def listAllSupportList = {
        params.supportFilterHostList = supportFilterHostList

        def jsonData = hostService.listAllSupportList(params)
        render jsonData as JSON
    }
    def editAllSupportList = {
        def jsonData = hostService.editAllSupportList(params)
        render jsonData as JSON
    }

    def exportSupportList = {
        params.rows = Integer.MAX_VALUE
        params.supportFilterHostList = supportFilterHostList

        def theList = []
        def jsonData = hostService.listAllSupportList(params)
        jsonData.rows.each { theRow ->

            if(!theRow.cell[7])
                theRow.cell[7] = ""

            def tempVals = [
                    Host: theRow.cell[1].replaceAll("\\<.*?>",""),
                    Status: theRow.cell[2],
                    'Primary SA': theRow.cell[3].replaceAll("\\<.*?>",""),
                    'Secondary SA':  theRow.cell[4].replaceAll("\\<.*?>",""),
                    'Tertiary SA': theRow.cell[5].replaceAll("\\<.*?>",""),
                    'Service Lead': theRow.cell[6]?.replaceAll("\\<.*?>",""),
                    'General note': theRow.cell[7]
            ]
            theList.add(tempVals)
        }
        render theList as JSON
    }

    /*****************************************************************/
    /* Resources Grid
    /*****************************************************************/
    def listResources = {
        def sortIndex = params.sidx ?: 'resourceType'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentHost = Host.get(params.hostId)

        def resources = ResourceAllocation.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("host.id", currentHost.id)
        }
        def totalRows = resources.size()
        def numberOfPages = Math.ceil(totalRows / maxRows)


        def results = resources?.collect { [ cell: ['', it.cluster.toString(), it.asset.toString(), it.resourceType, it.reservedAmount, it.allocatedAmount,  it.unitType, it.dateAssigned, it.allocNotes], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

    def editResources = {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        // Get objects from IDs
        if (params.dateAssigned) params.dateAssigned = Date.parse('yy-mm-dd', params.dateAssigned)
        if (params.asset) {
            if (params.asset == -1)
                paramse.asset = null
            else
                params.asset = Asset.get(params.asset)
        }
        if (params.cluster) params.cluster = Cluster.get(params.cluster)
        params.host = Host.get(params.hostId)

        // determine our action
        switch (params.oper) {
            case 'add':
                item = new ResourceAllocation(params)
                if (! item.hasErrors() && item.save()) {
//                    message = "Capacity ${item.toString()} Added for ${params.asset}"
                    id = item.id
                    state = "OK"
                } else {
//                    System.out.println(item.errors.errorCount)
//                    System.out.println(item.errors.allErrors)
//                            message = item.errors.errorCount
                }
                break;
            case 'edit':
                item = ResourceAllocation.get(params.id)
                item.properties = params

                if (! item.hasErrors() && item.save()) {
//                    message = "Capacity ${item.toString()} Updated for ${params.asset.toString()}"
                    id = item.id
                    state = "OK"
                } else {
//                    message = item.errors.errorCount
//                    System.out.println(item.errors.errorCount)
                }
                break;
            case 'del':
                item = ResourceAllocation.get(params.id)
//                System.out.println(item.id)
//                item.properties = params
                if (item) {
                    id = item.id
                    if (! item.hasErrors() && item.delete()) {
//                        message = "Replacement ${params.replacement} for Asset ${params.main_asset} Deleted."
                        state = "OK"
                    }
                }
                break;
        }

        def response = [state:state,id:id]

        render response as JSON
    }

    /*****************************************************************/
    /* AppServerAssignment Grid
    /*****************************************************************/
    def listAppServerAssignments = {
        def sortIndex = params.sidx ?: 'resourceType'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentHost = Host.get(params.hostId)


        def applications = ApplicationServerAssignment.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("host.id", currentHost.id)
        }

//        def applications = Application.getAll()
        def totalRows = applications.size()
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = applications?.collect { [ cell: ['',it.application.applicationTitle, it.application.appEnv?.abbreviation, it.application.applicationDescription,  it.resourceType, it.amountReserved, it.unitType, it.assignmentNote], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

    def editAppServerAssignments = {

        def item = null
        def message = ""
        def state = "FAIL"
        def id
        // Get objects from IDs
        if (params.application) params.application = Application.get(params.application)
        params.host = Host.get(params.hostId)

        // determine our action
        switch (params.oper) {
            case 'add':

                item = new ApplicationServerAssignment(params)

                if (! item.hasErrors() && item.save()) {
//                    message = "Capacity ${item.toString()} Added for ${params.asset}"
                    id = item.id
                    state = "OK"
                } else {
//                    System.out.println(item.errors.errorCount)
//                    System.out.println(item.errors.allErrors)
//                            message = item.errors.errorCount
                }
                break;
            case 'edit':
                item = ApplicationServerAssignment.get(params.id)
                item.properties = params

                if (! item.hasErrors() && item.save()) {
//                    message = "Capacity ${item.toString()} Updated for ${params.asset.toString()}"
                    id = item.id
                    state = "OK"
                } else {
//                    message = item.errors.errorCount
//                    System.out.println(item.errors.errorCount)
                }
                break;
            case 'del':
                item = ApplicationServerAssignment.get(params.id)
//                System.out.println(item.id)
//                item.properties = params
                if (item) {
                    id = item.id
                    if (! item.hasErrors() && item.delete()) {
//                        message = "Replacement ${params.replacement} for Asset ${params.main_asset} Deleted."
                        state = "OK"
                    }
                }
                break;
        }

        def response = [state:state,id:id]
        render response as JSON

    }


    /*****************************************************************/
    /* Software Instance Grid
    /*****************************************************************/

    def generateName = {
        def currentHost = Host.get(params.hostId)
        def count = currentHost.tiers.size();

        def response = [retVal:true, generatedName: "${currentHost.hostname} Instance ${count+1}"]
        render response as JSON
    }

    def listTiers = {
        def jsonData = hostService.listTiers(params)
        render jsonData as JSON

    }

    def editTiers = {
        def jsonData = hostService.editTiers(params)
        render jsonData as JSON
    }

    /*****************************************************************/
    /* Applications Sub-Grid
    /*****************************************************************/
    def listSubApp = {
        def sortIndex = params.sidx ?: 'resourceType'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentHost = Host.get(params.hostId)

        def assignments = ApplicationServerAssignment.createCriteria().list(max: maxRows, offset: rowOffset) {
            like('host.id', currentHost.id)
            like('application.id', params.rowId)
        }
        def totalRows = assignments.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = assignments?.collect { [ cell: [it.resourceType], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }


    /*****************************************************************/
    /* ELOG
    /*****************************************************************/

    def elog = {

        def currentHost = Host.get(params.id)
        StringBuffer elogMessage = new StringBuffer()

        elogMessage.append("<center>eLog report for Host " + currentHost.hostname + " (" + currentHost.env.toString() + ")</center>")

        elogMessage.append(hostService.elogReport(currentHost))

        def response = [message: elogMessage.toString(), state: "OK", id: 1]
        render response as JSON
    }

    /*****************************************************************/
    /* GETTER FUNCTIONS
    /*****************************************************************/

    String getPrimarySA(Host myHost) {
        def item = SupportRole.createCriteria().list() {
            like("supportedObject.id", myHost.id)
            roleName {
                like("roleName", 'Primary SA')
            }
        }
        StringBuffer buf = new StringBuffer("")
        if (!item.empty) {
            buf.append("<a href='../person/show?id=${item.first()?.person?.id}'>")
            buf.append("${item.first()?.person?.uhName}</a>")
        }
        return buf.toString()
    }



    Person getAdminForApp(Application myApp) {
        def item = SupportRole.createCriteria().list() {
            like("supportedObject.id", myApp.id)
            roleName {
                like("roleName", 'Primary SA')
            }
        }
        if (!item.empty)
            return item.first().person
        else
            return null
    }

    def getStatus = {
        def jsonData
        def theStatus = Host.get(params.hostId)?.status
        if(theStatus) {
            jsonData = [retVal:true, statusId: theStatus?.id]
        }
        else
            jsonData = [retVal: true, statusId: 1]

        render jsonData as JSON
    }



    /*****************************************************************/
    /* LISTING FUNCTIONS
    /*****************************************************************/

    def listHostsAsSelect={

        def lst = Host.createCriteria().list() {
            order('hostname','asc')
        }

//        println (lst.size())
        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listResourceTypesAsSelect={
        def lst = ResourceType.findAll()

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listUnitTypesAsSelect={
        def lst = UnitType.findAll()

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listTiersAsSelect={
        def lst = Tier.findAll()

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listApplicationsAsSelect={
        def lst = Application.findAll()
        lst.sort{it.toString()}
        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
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

    def listHostTypesAsSelect={

        StringBuffer buf = new StringBuffer("<select>")
        buf.append("<option value=\'Solaris Global Zone\'>Solaris Global Zone</option>")
        buf.append("<option value=\'Non-Global Zone\'>Non-Global Zone</option>")
        buf.append("<option value=\'VMWare\'>VMWare</option>")
        buf.append("<option value=\'VMware Host\'>VMware Host OS</option>")
        buf.append("<option value=\'VMware Standalone\'>VMware Standalone OS</option>")
        buf.append("<option value=\'Standalone\'>Standalone OS</option>")

        buf.append("</select>")

        render buf.toString()
    }


    def listAssetsByClusterAsSelect={
        def lst
        if (params.cluster) {
            params.cluster = Cluster.get(params.cluster)
            lst = Asset.createCriteria().list {
                like("cluster", params.cluster)
            }
        }
        else
            lst = PhysicalServer.findAll()

        StringBuffer buf = new StringBuffer("<select>")
        buf.append("<option value=-1>none</option>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    /*************************************************************************************
     * REPORTS
    *************************************************************************************/
    def shortSupportersReport = {
        InputStream is = params.uploadField?.getInputStream()

        def hostListString = is.readLines()
        is.close()

        def hostIdList = []
        def tempHost

        hostListString.each { hostString ->
            tempHost = Host.findByHostname(hostString)
            if(tempHost != null) {
                hostIdList.add(tempHost.id)
            }
        }

        def serviceList = Service.createCriteria().list() {
            tiers {
                tier {
                    'in'('host.id', hostIdList)
                }
            }
            resultTransformer Criteria.DISTINCT_ROOT_ENTITY
        }

        def objectIdList = serviceList.id + serviceList.application.id.unique() + hostIdList

        def staffList = Person.createCriteria().list() {
            supportRoles {
                'in'('supportedObject.id', objectIdList)
            }
            resultTransformer Criteria.DISTINCT_ROOT_ENTITY

        }
        StringBuffer reportMessage = new StringBuffer()

        reportMessage.append("<center><h3>uhNames of Staff responsible for Hosts given (and Apps and Services affected):</h3></center><br>")

        staffList.each { thePerson ->
            reportMessage.append("${thePerson.uhName}, ")
        }
        if (reportMessage.length() > 0)
            reportMessage.delete(reportMessage.length()-2, reportMessage.length())

        reportMessage.append("<br><br><center><h3>EMail list:</h3></center><br>")
        staffList.each { thePerson ->
            if(thePerson.primaryEmail == null) {
                reportMessage.append("${thePerson.uhName} (email not found), ")
            }
            else {
                reportMessage.append("${thePerson.primaryEmail}, ")
            }
        }
        if (reportMessage.length() > 0)
            reportMessage.delete(reportMessage.length()-2, reportMessage.length())

        def response = [message: reportMessage.toString(), state: "OK", id: 1]
        render response as JSON
    }

    def hostSupportReport = {
        InputStream is = params.uploadField?.getInputStream()

        def hostListString = is.readLines()
        is.close()

        def hostIdList = []
        def tempHost

        hostListString.each { hostString ->
            tempHost = Host.findByHostname(hostString)
            if(tempHost != null) {
                hostIdList.add(tempHost.id)
            }
        }
      /*
        def serviceList = Service.createCriteria().list() {
            tiers {
                tier {
                    'in'('host.id', hostIdList)
                }
            }
            resultTransformer Criteria.DISTINCT_ROOT_ENTITY
        }

        def objectIdList = serviceList.id + serviceList.application.id.unique() + hostIdList
*/
        StringBuffer reportMessage = new StringBuffer()
        reportMessage.append("<center><h3>Support Roles assigned to each Host:</h3></center><br>")

        hostIdList.each { theHostId ->
            def roleList = SupportRole.createCriteria().list() {
                eq('supportedObject.id', theHostId)
            }

            reportMessage.append("<br><b>${Host.get(theHostId).toString()}</b>:<br>")
            if (roleList.isEmpty()) {
                reportMessage.append("     No Support Roles Assigned...<br>")
            }

            roleList.each { theRole ->
                reportMessage.append("     ${theRole.roleName.toString()} - ${theRole.person.toString()}<br>")
            }
        }

        def response = [message: reportMessage.toString(), state: "OK", id: 1]
        render response as JSON
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

    /*****************************************************************************************************************
     * BACKBONE API MENTHODS
    /*****************************************************************************************************************/

    def getHostsByServer = {

        def hosts = Host.createCriteria().list() {
            eq('asset.id', params.serverId.toLong())
        }
        JSON.use("serverHostGrid") {
            respond hosts
        }
    }
}
