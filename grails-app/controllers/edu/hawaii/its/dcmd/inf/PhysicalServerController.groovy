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

class PhysicalServerController {

    def scaffold = PhysicalServer

    def personService
    def assetService
    def static List<String> supportFilterHostList = new ArrayList()


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
            if(!theRow.cell[1]) theRow.cell[1] = ""
            if(!theRow.cell[2]) theRow.cell[2] = ""
            if(!theRow.cell[6]) theRow.cell[6] = ""
            if(!theRow.cell[8]) theRow.cell[8] = ""
            if(!theRow.cell[9]) theRow.cell[9] = ""
            if(!theRow.cell[11]) theRow.cell[11] = ""
            if(!theRow.cell[12]) theRow.cell[12] = ""

            def tempVals = [
                    'ITS Id': theRow.cell[0].replaceAll("\\<.*?>",""),
                    'Server Type': theRow.cell[1],
                    Status: theRow.cell[2],
                    'Primary SA': theRow.cell[3].replaceAll("\\<.*?>",""),
                    'RU Size': theRow.cell[4],
                    'Current Rack': theRow.cell[5].replaceAll("\\<.*?>",""),
                    'Current Position': "\'${theRow.cell[6]}\'",
                    'Current Location': theRow.cell[7].replaceAll("\\<.*?>",""),
                    'Serial #': theRow.cell[9],
                    'Manufacturer': theRow.cell[10],
                    'Model': theRow.cell[11],
                    'General Notes': theRow.cell[12]
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

}

