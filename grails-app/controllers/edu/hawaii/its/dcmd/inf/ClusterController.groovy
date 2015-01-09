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
import edu.hawaii.its.dcmd.inf.Cluster
import edu.hawaii.its.dcmd.inf.Asset
import edu.hawaii.its.dcmd.inf.AssetCapacity
import edu.hawaii.its.dcmd.inf.ResourceAllocation
import edu.hawaii.its.dcmd.inf.Host
import grails.gorm.DetachedCriteria

class ClusterController {

    def scaffold = Cluster
    def personService
    def assetService
    def generalService

    def save = {
        params.name = params.clusterName
        System.out.println(params)
        //create new instance from params and remove any notes flagged as deleted
        def clusterInstance = new Cluster(params)
//		assetService.removeDeletedNotes(assetInstance)
        String saveOption = params.option //selects type of save to do
        //save and redirect or re-edit the new asset
        if (clusterInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'cluster.label', default: 'Cluster'), clusterInstance.id])}"
            if (saveOption.equals("saveEdit")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'host.label', default: 'Cluster'), clusterInstance.id])}"
                redirect(url: "/cluster/edit?id=${clusterInstance.id}")
            }
            else if (saveOption.equals("saveList")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'host.label', default: 'Cluster'), clusterInstance.id])}"
                redirect(url: "/cluster/list")
            }
            else if (saveOption.equals("saveCreate")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'host.label', default: 'Cluster'), clusterInstance.id])}"
                redirect(url: "/cluster/create")
            }
            else{
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'host.label', default: 'Cluster'), clusterInstance.id])}"
                redirect(url: "/cluster/show?id=${clusterInstance.id}")
            }
        } else {
            render(view: "create", model: [clusterInstance: clusterInstance])
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
        def clusterInstance = Cluster.get(params.id)

        if (clusterInstance) {

            log.debug "asset instance not null"

            //update the object with the params and remove deleted notes
            clusterInstance.properties = params
//			assetService.removeDeletedNotes(assetInstance)

            //save and redirect
            if (!clusterInstance.hasErrors() && clusterInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'cluster.label', default: 'Cluster'), clusterInstance.id])}"
                redirect(url: "/cluster/show?id=${clusterInstance.id}")
            } else {
                render(view: "edit", model: [assetInstance: clusterInstance])
            }

            //asset not found; no edits can be performed; show the list
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cluster.label', default: 'Cluster'), params.id])}"
            redirect(action: "list")
        }

    }

    /*****************************************************************/
    /* Main Cluster Grid
    /*****************************************************************/

    def listAllCluster = {
        def sortIndex = params.sidx ?: 'name'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        def clusters = Cluster.createCriteria().list(max: maxRows, offset: rowOffset) {
// Search Filters
            if (params.name) ilike('name', "%${params.name}%")
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}")

// Sort
            order(sortIndex, sortOrder)
        }


        def totalRows = clusters.size()
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = clusters?.collect { [ cell: [it.name, it.assetSet.size(), it.hostSet.size(),it.lastUpdated.format('MM/dd/yy h:m a'), it.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON

    }


    /*****************************************************************/
    /* Assets Grid
    /*****************************************************************/
    def listAssets = {
        def sortIndex = params.sidx ?: 'itsId'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentCluster = Cluster.get(params.clusterId)

        def assets = PhysicalServer.createCriteria().list(max:maxRows, offset:  rowOffset) {
            like('cluster.id', currentCluster.id)

            // Add Search Filters
            if(params.itsId) ilike('itsId', "%${params.itsId}%")
            if(params.status) {
                status {
                    ilike('abbreviation', "%${params.status}%")
                }
            }
            if(params.primarySA) {
                def SACriteria = new DetachedCriteria(SupportRole).build {
                    roleName {
                        ilike('roleName', 'Primary SA')
                    }
                    person {
                        ilike('uhName', "%${params.primarySA}%")
                    }
                }
                if(!SACriteria.collect().isEmpty()) {
                    'in'('id', SACriteria.collect()?.supportedObject?.id)

                }
                else
                    eq('id', null)
            }
            if(params.generalNote) ilike('generalNote', "%${params.generalNote}%")

            // Sort
            order(sortIndex, sortOrder)
        }

        def totalRows = assets.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        // Calculate total of that resource provided by the asset

        def results = assets?.collect { [ cell: ['',
                "<a href='../asset/show?id=${it.id}'>${it.toString()}</a>",
                it.status.toString(),
                "<a href='../person/show?id=${personService.getAdmin(it)?.id}'>${personService.getAdmin(it).toString()}</a>",
                "<a href='../asset/show?id=${it.getRackAssignmentId()}'>${it.getRackAssignment()}</a>",
                it.position(), assetService.getCurrentLocationLink(it), it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

    def editAssets = {
        System.out.println(params)
        def theServer = null
        def currentCluster = Cluster.get(params.clusterId)
        def message = ""
        def state = "FAIL"
        def id
        // Get objects from IDs

        // determine our action
        switch (params.oper) {
            case 'add':
                theServer = PhysicalServer.get(params.asset.toLong())
                theServer.cluster = currentCluster
                currentCluster.assetSet.add(theServer)

                if (! theServer.hasErrors() && theServer.save()) {
                    if(! currentCluster.hasErrors() && currentCluster.save()) {
//                    message = "Capacity ${item.toString()} Updated for ${params.asset.toString()}"
                        id = theServer.id
                        state = "OK"

                    } else {
//                        message = item.errors.errorCount
                    }
                }
                break;
        }

        def response = [state:state,id:id]

        render response as JSON
    }

    /*****************************************************************/
    /* Hosts Grid
    /*****************************************************************/
    def listHosts = {
        def sortIndex = params.sidx ?: 'hostName'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentCluster = Cluster.get(params.clusterId)

        def hosts = Host.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("cluster.id", currentCluster.id)
// Search Filter
            if (params.hostname) ilike('hostname', "%${params.hostname}%")
            if (params.hostEnv) {
                env {
                    ilike('abbreviation', "%${params.hostEnv}%")
                }
            }
            if (params.status) {
                status {
                    ilike('abbreviation', "%${params.status}%")
                }
            }

            if(params.hostSA) {
                def SACriteria = new DetachedCriteria(SupportRole).build {
                    roleName {
                        ilike('roleName', 'Primary SA')
                    }
                    person {
                        ilike('uhName', "%${params.hostSA}%")
                    }
                }
                if(!SACriteria.collect().isEmpty()) {
                    'in'('id', SACriteria.collect()?.supportedObject?.id)

                }
                else
                    eq('id', null)
            }

            if(params.generalNote) ilike('generalNote', "%${params.generalNote}%")

// Sort
            switch(sortIndex) {
                case 'hostEnv':
                    env {
                        order('abbreviation', sortOrder)
                    }
                    break
                case 'status':
                    status {
                        order('abbreviation', sortOrder)
                    }
                    break
                default:
                    order(sortIndex, sortOrder)
                    break
            }
        }

        def totalRows = hosts.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = hosts?.collect { [ cell: ['',
                "<a href='../host/show?id=${it.id}'>${it.hostname}</a>",
                it.env.toString(), it.status.toString(),
                "<a href='../person/show?id=${personService.getAdmin(it)?.id}'>${personService.getAdmin(it).toString()}</a>",
                it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

    def editHosts = {
        System.out.println(params)
        def item = null
        def message = ""
        def state = "FAIL"
        def id

        if (params.hostEnv) params.env = Environment.get(params.hostEnv)
        if (params.status) params.status = Status.get(params.status)

        params.cluster = Cluster.get(params.clusterId)
        params.type = 'VMWare'


        // determine our action
        switch (params.oper) {
            case 'add':
                // add instruction sent
                //System.out.println(params)
                item = new Host(params)
                item.type = 'VMWare'
//                item.main_asset = Asset.get(params.assetId)
                if (! item.hasErrors() && item.save()) {
                    message = "Created host ${params.hostname}"
                    if(params.hostSA) {
                        def primarySA = generalService.createSupportRole(item, Person.get(params.personSelect), 'Technical', RoleType.findByRoleName("Primary SA")?.id)

                    }
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.allErrors.get(0).toString()
                }
                break;
            case 'edit':
                item = Host.get(params.id)
                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    message = "Host ${item.hostname} Updated"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = Host.get(params.id)
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

}
