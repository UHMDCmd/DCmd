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
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil
import org.springframework.validation.ObjectError
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import edu.hawaii.its.dcmd.inf.PhysicalServer
import edu.hawaii.its.dcmd.inf.Host
import edu.hawaii.its.dcmd.inf.ResourceAllocation
import edu.hawaii.its.dcmd.inf.Cluster
import edu.hawaii.its.dcmd.inf.AssetCapacity
import edu.hawaii.its.dcmd.inf.Asset
import edu.hawaii.its.dcmd.inf.SupportRole
import edu.hawaii.its.dcmd.inf.Person
import grails.gorm.DetachedCriteria
import org.hibernate.criterion.Restrictions
import de.andreasschmitt.export.taglib.util.RenderUtils
import groovy.json.JsonSlurper

class PhysicalServerController {

    def scaffold = PhysicalServer

    def personService
    def assetService
    def generalService
    def static List<String> supportFilterHostList = new ArrayList()



    def save = {
        System.out.println(params)
        if(params.statusSelect)
            params.status = generalService.createOrSelectStatus(params)

        if(params.serverType == 'VMWare') {
            params.asset = null
            if (params.clusterSelect == 'null')
                params.cluster = null
            else
                params.cluster = Cluster.get(params.clusterSelect)
        }
        params.assetType=AssetType.findByAbbreviation("Server")

        def physicalServerInstance = new PhysicalServer(params)

        if (params.globalZone && params.globalZone != 0) {
            def globalZone = Host.get(params.globalZone)
            globalZone.asset = physicalServerInstance
            globalZone.type = params.serverType
            globalZone.save()
            params.globalZone = globalZone
            physicalServerInstance.hostOS = globalZone
        }
        else {
            params.globalZone = null
            physicalServerInstance.hostOS = null
        }

        physicalServerInstance.properties = params
        String saveOption = params.option //selects type of save to do

        if (physicalServerInstance.save(flush: true)) {

            //save options
            if (saveOption.equals("saveEdit")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'physicalServer.label', default: 'Physical Server'), physicalServerInstance.id])}"
                redirect(url: "/physicalServer/edit?id=${physicalServerInstance.id}")
            }
            else if (saveOption.equals("saveList")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'physicalServer.label', default: 'Physical Server'), physicalServerInstance.id])}"
                redirect(url: "/physicalServer/list")
            }
            else if (saveOption.equals("saveCreate")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'physicalServer.label', default: 'Physical Server'), physicalServerInstance.id])}"
                redirect(url: "/physicalServer/create")
            }
            else{
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'physicalServer.label', default: 'Physical Server'), physicalServerInstance.id])}"
                redirect(url: "/physicalServer/show?id=${physicalServerInstance.id}")
            }
        }
        else {
            System.out.println(physicalServerInstance.errors.fieldError.toString())
            render(view: "create", model: [physicalServerInstance: physicalServerInstance])
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
        def physicalServerInstance = PhysicalServer.get(params.id)
        if (!physicalServerInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'host.label', default: 'PhysicalServer'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [physicalServerInstance: physicalServerInstance]
        }
    }

    def update = {
//        resetCloneVar()

        //get the instance being updated
        def physicalServerInstance = PhysicalServer.get(params.id)
        if(params.statusSelect)
            params.status = generalService.createOrSelectStatus(params)

        if (params.manufacturerSelect) {
            params.manufacturer = generalService.createOrSelectManufacturer(params)
        }
        if(params.assetSelect) {
            if (params.assetSelect == 'null')
                params.asset = null
            else
                params.asset = Asset.get(params.assetSelect.toLong())
        }

        if(params.serverType == 'VMWare') {
            if (params.clusterSelect == 'null')
                params.cluster = null
            else
                params.cluster = Cluster.get(params.clusterSelect)
        }
        else {
            params.cluster = null
        }
        System.out.println(params)

        if (params.globalZone && params.globalZone != 0) {
            /*
            def currentGlobalZone = physicalServerInstance.getGlobalZone()
            if(currentGlobalZone != null) {
                currentGlobalZone.asset = null
                currentGlobalZone.save()
            }
            */
            def globalZone = Host.get(params.globalZone)

            def oldGlobalZone = globalZone.asset
            if (oldGlobalZone != null) {
                oldGlobalZone.hostOS = null
                oldGlobalZone.save(flush: true)
            }

            globalZone.asset = physicalServerInstance
            globalZone.type = params.serverType
            System.out.println(globalZone.toString())
            System.out.println(globalZone.type)
            globalZone.save()
            params.globalZone = globalZone
            physicalServerInstance.hostOS = globalZone
        }
        else {
            params.globalZone = null
            physicalServerInstance.hostOS = null
        }


        if (physicalServerInstance) {

            log.debug "asset instance not null"
/*
            if(Integer.parseInt(params.currentRack) != 0) {
                if(params.RU_begin)
                    params.RU_begin = Integer.parseInt(params.RU_begin) + Integer.parseInt(params.RU_size)-1
            }
            else
                params.RU_begin = 0

            if (params.RU_size)
                params.RU_size = Integer.parseInt(params.RU_size)

            //update the object with the params and remove deleted notes
            physicalServerInstance.properties = params

            if(params.RU_size == "")
                params.RU_size = 0
            if(params.RU_size < 0) {
                physicalServerInstance.errors.rejectValue('RU_size', 'RU Size must be a positive number.')
                GrailsHibernateUtil.setObjectToReadyOnly(physicalServerInstance, sessionFactory)
            }
            if(Integer.parseInt(params.currentRack) == 0) {
                System.out.println(params)

                params.assetId = physicalServerInstance.id
                assetService.removeAsset(params)
            }
            else if(params.currentRack != physicalServerInstance.getRackAssignmentId() || params.RU_begin != physicalServerInstance.RU_begin) {

                if((params.RU_begin-params.RU_size+1) < 1 || params.RU_begin > 45) {
                    physicalServerInstance.errors.rejectValue('RU_begin', 'All RU Positions must be between 1 and 45.')
                    GrailsHibernateUtil.setObjectToReadyOnly(physicalServerInstance, sessionFactory)
                }
                else {
                    params.assetId = physicalServerInstance.id
                    assetService.removeAsset(params)
                    params.id = params.currentRack
                    params.asset_Id = physicalServerInstance.id
                    params.addPosition = params.RU_begin
                    if (assetService.addAssetToRack(params)==-2) {
                        physicalServerInstance.errors.rejectValue('RU_begin', 'Current Rack position already currently occupied.')
                        GrailsHibernateUtil.setObjectToReadyOnly(physicalServerInstance, sessionFactory)
                    }
                }
            }
            */
            physicalServerInstance.properties = params

            //save and redirect
            if (!physicalServerInstance.hasErrors() && physicalServerInstance.save(flush: true)) {
                System.out.println("SAVED SUCCESSFULLY!")
                System.out.println(physicalServerInstance.errors.errorCount)
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'asset.label', default: 'Asset'), physicalServerInstance.id])}"
                redirect(url: "/physicalServer/show?id=${physicalServerInstance.id}")
                //  forward action:  "show", params: [showUrl:"/asset/show?id=${assetInstance.id}"]
            } else {
                System.out.println("ERROR FOUND!")
                render(view: "edit", model: [physicalServerInstance: physicalServerInstance])
            }

            //asset not found; no edits can be performed; show the list
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'PhysicalServer'), params.id])}"
            redirect(action: "list")
        }
    }





    /*****************************************************************/
    /* LISTING FUNCTIONS
    /*****************************************************************/

    def listAsSelect={

        def lst = PhysicalServer.findAll()

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

    def listAvailHostsAsSelect={
        def lst = Host.createCriteria().list() {
            isEmpty('resourceAllocations')
        }
        def mine = ResourceAllocation.createCriteria().list() {
            like('physicalServer.id', params.physicalServerId)
        }
//        System.out.println(params.physicalServerId)
        for(resource in mine) {
            lst.add(resource.host)
        }

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }
    def listHostsAsSelect={
        def lst = Host.createCriteria().list() {
        }
        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listAvailServers = {
        def lst = PhysicalServer.createCriteria().list() {
            Restrictions.isNull('cluster')
        }
        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listJSON = {
        log.debug "controller params: ${params}"
        render assetService.listAssets(params) as JSON
    }

    /*****************************************************************/
    /* Main PhysicalServers Grid
    /*****************************************************************/
    def listAllPhyServer = {
        params.supportFilterHostList = supportFilterHostList

        def jsonData = assetService.listAllPhyServer(params)
        render jsonData as JSON
    }

    // Export action for the main list PhysicalServer page
    def exportListAll = {
        params.rows = Integer.MAX_VALUE
        params.supportFilterHostList = supportFilterHostList

        def jsonData = assetService.listAllPhyServer(params)
        def theList = []
        jsonData.rows.each { theRow ->
            for(def i=0; i<19; i++) {
                if(!theRow.cell[i]) theRow.cell[i]=""
            }
            /*
            if(!theRow.cell[1]) theRow.cell[1] = ""
            if(!theRow.cell[2]) theRow.cell[2] = ""
            if(!theRow.cell[6]) theRow.cell[6] = ""
            if(!theRow.cell[8]) theRow.cell[8] = ""
            if(!theRow.cell[9]) theRow.cell[9] = ""
            if(!theRow.cell[11]) theRow.cell[11] = ""
            if(!theRow.cell[12]) theRow.cell[12] = ""
            */
            def tempVals = [
                    'ITS Id': theRow.cell[0].replaceAll("\\<.*?>",""),
                    'Server Type': theRow.cell[1],
                    'VCenter': theRow.cell[2],
                    'VM Cluster':theRow.cell[3].replaceAll("\\<.*?>",""),
                    'OS Host': theRow.cell[4].replaceAll("\\<.*?>",""),
                    'Status': theRow.cell[5],
                    'Primary SA': theRow.cell[6].replaceAll("\\<.*?>",""),
                    'RU Size': theRow.cell[7],
                    'Current Rack': theRow.cell[8].replaceAll("\\<.*?>",""),
                    'Current Position': theRow.cell[9],
                    'Current Location': theRow.cell[10].replaceAll("\\<.*?>",""),
                    'Avail. for parts': theRow.cell[11],
                    'Serial #': theRow.cell[12],
                    'Vendor': theRow.cell[13],
                    'Model': theRow.cell[14],
                    'Total Memory': theRow.cell[15],
                    'Memory Assigned': theRow.cell[16],
                    'Total Cores': theRow.cell[17],
                    'Max CPU Assigned': theRow.cell[18],
                    'General Notes': theRow.cell[19]
            ]

            theList.add(tempVals)
        }

        render theList as JSON
    }

    /*****************************************************************/
    /* Capacity Grid
    /*****************************************************************/

    def listCapacities = {
        def sortIndex = params.sidx ?: 'resourceType'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentPhysicalServer = PhysicalServer.get(params.physicalServerId)

        def capacities = AssetCapacity.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("asset.id", params.physicalServerId.toLong())
        }
        def totalRows = capacities.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def reservedTotal = 0.0f
        def allocTotal = 0.0f
/*
        for(i in capacities) {
            assetService.calcReserved(currentAsset, i)
            assetService.calcAlloc(currentAsset, i)

            assignedTotal = 0.0f
            assignedTotal = Cluster.createCriteria().get() {
                like("asset.id", currentAsset.id)
                like("resourceType", i.resourceType)
                projections {
                    sum("amountAssigned")
                }
            }
            if (assignedTotal)
                i.unassignedCapacity = i.currentCapacity - assignedTotal
            else
                i.unassignedCapacity = i.currentCapacity
            }
            */

        def results = capacities?.collect { [ cell: ['', it.resourceType, it.currentCapacity, assetService.calcReserved(it.asset, it.resourceType), assetService.calcAlloc(it.asset, it.resourceType), it.maxExpandableCapacity, it.unitType, it.dateCreated, it.capacityNotes], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

    def editCapacities = {
        def item = null
        def message = ""
        def state = "FAIL"
        def id

        if (params.dateCreated) params.dateCreated = Date.parse('yy-mm-dd', params.dateCreated)

        // determine our action
        switch (params.oper) {
            case 'add':
                // add instruction sent
//                System.out.println(params)
                params.asset =PhysicalServer.get(params.assetId)
                item = new AssetCapacity(params)
//                item.main_asset = Asset.get(params.assetId)
//                System.out.println(params)
                if (params.currentCapacity > params.maxExpandableCapacity) {
                    message = "Total Capacity exceeds Max Expandable"
                    state = "FAIL"
                }
                else if (! item.hasErrors() && item.save()) {
//                    assetService.calcUnassigned(params.asset, item)
                    message = "Capacity ${item.toString()} Added for ${params.asset}"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'edit':
                params.asset = Asset.get(params.assetId)
                item = AssetCapacity.get(params.id)

                item.properties = params
//                assetService.calcUnassigned(params.asset, item)
                if (! item.hasErrors() && item.save()) {
                    message = "Capacity ${item.toString()} Updated for ${params.asset.toString()}"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = AssetCapacity.get(params.id)
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

        def response = [message:message,state:state,id:id]

        render response as JSON
    }



    /*****************************************************************/
    /* Servers Grid
    /*****************************************************************/

    def listHosts = {
        def jsonData = assetService.listPhyServerHosts(params)
        render jsonData as JSON
    }

    def editHosts = {
        def jsonData = assetService.editPhyServerHosts(params)
        render jsonData as JSON
    }

    def addHost = {
        def jsonData = assetService.editPhyServerHosts(params)
        System.out.println(jsonData)
        render jsonData as JSON

    }

    /*****************************************************************/
    /* Host Filter Functions
    /*****************************************************************/

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

    def getServerDetails = {
        System.out.println(params)
        def server = PhysicalServer.get(params.serverId)
        //def response = [retVal: true, itsId:server.itsId, status:server.status?.abbreviation]
        //render response as JSON
        render server as JSON

    }

    def testExtern = {
        def inputFile = new File("C:\\Users\\Ben\\.grails\\testFile.txt")
        def jsonSlurper = new JsonSlurper()
        def InputJSON = jsonSlurper.parseText(inputFile.text)
        InputJSON.each {
            println it.url
            println it.username
            println it.password
        }
        render InputJSON as JSON
    }

    def getServerTypes = {
        def typeList = []

        typeList.add(['id':'Solaris Global Zone', 'text':'Solaris Global Zone'])
        typeList.add(['id':'VMWare', 'text':'VMWare'])
        typeList.add(['id':'Windows Server', 'text':'Windows Server'])
        typeList.add(['id':'Standalone', 'text':'Standalone'])

        render typeList as JSON
    }

}

