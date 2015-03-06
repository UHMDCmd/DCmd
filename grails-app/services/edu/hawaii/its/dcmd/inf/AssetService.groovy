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

import grails.gorm.DetachedCriteria
import org.hibernate.Criteria
import org.hibernate.criterion.Projections

class AssetService {

	def grailsApplication
    def personService
    def replacementService

    static transactional = true


    /*****************************************************************/
    /* Grid Functions
    /*****************************************************************/
    def listAllAsset(params) {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        def assets
    try {
        assets = Asset.createCriteria().list(max: maxRows, offset: rowOffset) {
// Search Filters
//            eq('inStorage', params.stored.asBoolean())
        assetType {
            ne('abbreviation', "Power")
        }

        if (params.assetType) {
            assetType {
                ilike('abbreviation', "%${params.assetType}%")
            }
        }
        if (params.itsId) ilike('itsId', "%${params.itsId}%")
        if (params.assetStatus) {
            status {
                ilike('abbreviation', "%${params.assetStatus}%")
            }
        }

        if (params.rack) {
            def rackCriteria = new DetachedCriteria(RackUnit).build {
                isNotNull('filledBy')
                onRack {
                    ilike('itsId', "%${params.rack}%")
                }
            }
            def rackCollection = rackCriteria.collect()

            if (!rackCollection.isEmpty()) {
                if (rackCollection.filledBy != null)
                    'in'('id', rackCollection.filledBy?.id)
            } else
                eq('id', null)
        }
        if (params.isAvailableForParts) eq('isAvailableForParts', Boolean.valueOf(params.isAvailableForParts))
        if (params.primarySA) {
            def SACriteria = new DetachedCriteria(SupportRole).build {
                roleName {
                    ilike('roleName', 'Primary SA')
                }
                person {
                    ilike('uhName', "%${params.primarySA}%")
                }
            }
            if (!SACriteria.collect().isEmpty()) {
                'in'('id', SACriteria.collect()?.supportedObject?.id)

            } else
                eq('id', null)
        }
        if (params.serialNo) ilike('serialNo', "%${params.serialNo}%")
        if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")

// Sort
        switch (sortIndex) {
            case 'assetType':
                assetType {
                    order('abbreviation', sortOrder)
                }
                break;
            case 'assetStatus':
                status {
                    order('abbreviation', sortOrder)
                }
                break;
            case 'primarySA':

                break;
            default:
                order(sortIndex, sortOrder)
                break;
        }
    }
    }
    catch (Exception e) {
         System.out.println(e.message)
    }
        def totalRows = assets.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results
        /*
        if(params.stored == 'false') {
            results = assets?.collect { [ cell: [it.assetType.toString(), it.itsId, it.status.toString(),
                "<a href='../asset/show?id=${it.getRackAssignmentId()}'>${it.getRackAssignment()}</a>",
                    it.position(), it.isAvailableForParts,
                "<a href='../person/show?id=${personService.getAdmin(it)?.id}'>${personService.getAdmin(it).toString()}</a>",
                    it.lastUpdated.format('MM/dd/yy h:m a'), it.serialNo, it.generalNote], id: it.id ] }
        }
        else {
        */
            results = assets?.collect { [ cell: [it.assetType.toString(), it.getItsIdPageLink(), it.status.toString(),
                    "<a href='../location/show?id=${it.location?.id}'>${it.location.toString()}</a>",
                    it.isAvailableForParts,
                    "<a href='../person/show?id=${personService.getAdmin(it)?.id}'>${personService.getAdmin(it).toString()}</a>",
                    it.lastUpdated.format('MM/dd/yy h:m a'), it.serialNo, it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData

    }

    def listAllPhyServer(params) {
        def sortIndex = params.sidx ?: 'itsId'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        // Initial large cached join query
        def serverIds = PhysicalServer.createCriteria().list() {
            // File List Filter
            if(!params.supportFilterHostList.isEmpty()) {
                hosts {
                    'in'('hostname', params.supportFilterHostList)
                }
//                resultTransformer Criteria.DISTINCT_ROOT_ENTITY
            }
            projections {
                distinct('id')
            }
            createAlias('status', 'status', Criteria.LEFT_JOIN)
            createAlias('supporters.person', 'supporters.person', Criteria.LEFT_JOIN)
            createAlias('supporters.roleName', 'supporters.roleName', Criteria.LEFT_JOIN)
            createAlias('manufacturer', 'manufacturer', Criteria.LEFT_JOIN)
            createAlias('RUplacement.onRack', 'RUplacement.onRack', Criteria.LEFT_JOIN)

            cache true
        }

        def physicalServers = PhysicalServer.createCriteria().list(max: maxRows, offset: rowOffset) {
            'in'('id', serverIds)


// Search Filters
            if (params.itsId) ilike('itsId', "%${params.itsId}%")
            if (params.serverType) ilike('serverType', "%${params.serverType}%")
            if (params.vcenter)
                cluster {
                    ilike('dataCenter', "%${params.vcenter}")
                }
            if (params.cluster)
                cluster {
                    ilike('name', "%${params.cluster}%")
                }
            if (params.assetStatus)
                status {
                    ilike('abbreviation', "%${params.assetStatus}%")
                }
            if(params.primarySA) {
                supporters {
                    roleName {
                        like('roleName', 'Primary SA')
                    }
                    person {
                        ilike('uhName', "%${params.primarySA}%")
                    }
                }
            }
            if (params.RU_size) eq('RU_size', params.RU_size.toInteger())
            if (params.rack){
                RUplacement {
                    onRack {
                        ilike('itsId', "%${params.rack}%")
                    }
                }
            }

            if (params.isAvailableForParts)
                eq('isAvailableForParts', Boolean.valueOf(params.isAvailableForParts))
            if (params.serialNo) ilike('serialNo', "%${params.serialNo}%")
            if (params.manufacturer) {
                manufacturer {
                    ilike('name', "%${params.manufacturer}%")
                }
            }
            if (params.modelDesignation) ilike('modelDesignation', "%${params.modelDesignation}%")
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")

// Sort
            switch(sortIndex) {
                case 'assetStatus':
                    status {
                        order('abbreviation', sortOrder)
                    }
                    break
                case 'cluster':
                    cluster {
                        order('name', sortOrder)
                    }
                    break
                case 'vcenter' :
                    cluster {
                        order('dataCenter', sortOrder)
                    }
                     break
                case 'manufacturer':
                    manufacturer {
                        order('name', sortOrder)
                    }
                    break

                case 'memoryAssigned':
                    //       hosts {
                    //           projections{
                    //               sum('maxMemory').as('memoryAssigned')
                    //           }
                    setProjection(Projections.sum('hosts.maxMemory'))
                    createAlias('hosts.maxMemory', 'memoryAssigned')
                    order('memoryAssigned', sortOrder)
            //}
                    break

                default:
                    order(sortIndex, sortOrder)
                    break
            }
        }

        def totalRows = physicalServers.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = physicalServers?.collect { [ cell: [
                //"<a href='../physicalServer/show?id=${it.id}'>${it.itsId}</a>",
                "<a onclick='openItem(${it.id})'>${it.itsId}</a>",
                it.serverType,
                it.getDatacenterLinkString(),
                it.getClusterLinkString(),
                it.getHostOSLinkString(),
                it.status.toString(),
                personService.getSupportPersonLink(it, 'Primary SA'),
                it.RU_size,
                "<a href='../asset/show?id=${it.getRackAssignmentId()}'>${it.getRackAssignment()}</a>",
                it.position(),
                getCurrentLocationLink(it),
                it.isAvailableForParts,
                it.serialNo, it.vendor, it.modelDesignation,
                it.memorySize + " GB",
                it.getMemoryPercentUsed(),
                it.numCores,
                it.getCPUPercentUsed(),
                it.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData

    }

    def listAllRack(params) {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        def racks = Rack.createCriteria().list(max: maxRows, offset: rowOffset) {

// Search Filters
            if (params.itsId) ilike('itsId', "%${params.itsId}%")
            if(params.location) {
                location {
                    ilike('locationDescription', "%${params.location}%")
                }
            }
            if (params.isAvailableForParts)
                eq('isAvailableForParts', Boolean.valueOf(params.isAvailableForParts))
            if (params.serialNo) ilike('serialNo', "%${params.serialNo}%")

            if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")

// Sort
            if(sortIndex == 'location') {
                location {
                    order('locationDescription', sortOrder)
                }
            }
            order(sortIndex, sortOrder)
        }

        def totalRows = racks.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = racks?.collect { [ cell: [ "<a href='../asset/show?id=${it.id}'>${it.itsId}</a>", "<a href='../location/show?id=${it.location?.id}'>${it.location.toString()}</a>",
                                                 it.rowId, it.zoneId, it.cabLocation,
                                                 it.lastUpdated.format('MM/dd/yy h:m a'), it.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData

    }

    def listReplacements(params) {
        def sortIndex = params.sidx ?: 'replacement'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentAsset = Asset.get(params.assetId)

        def replacements = Replacement.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("main_asset.id", currentAsset.id)
        }
        def totalRows = replacements.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

//        def results = replacements?.collect { [ cell: ['', it.replacement.toString(), it.priority, it.ready_date, it.replacement_notes], id: it.id ] }
        def results = replacements?.collect { [ cell: ['', it.replacement.toString(), it.priority, it.ready_date, it.replacement_notes], id: it.replacementId ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editReplacements(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id

        if (params.replacement) params.replacement = Asset.get(params.replacement)
        if (params.ready_date) params.ready_date = Date.parse('yy-mm-dd', params.ready_date)

        // determine our action
        switch (params.oper) {
            case 'add':
                // add instruction sent
                params.main_asset = Asset.get(params.assetId)
                item = new Replacement(params)
//                item.main_asset = Asset.get(params.assetId)
                if (! item.hasErrors() && item.save()) {
                    message = "Replacement  ${item.toString()} Added for ${params.main_asset}"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'edit':
                item = Replacement.get(params.id)
                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    message = "Replacement  ${item.toString()} Updated for ${params.main_asset}"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = Replacement.get(params.id)
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

        return response
    }

    def listPhyServerHosts(params) {
        def sortIndex = params.sidx ?: 'itsId'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentAsset = Asset.get(params.assetId)

        def hosts = Host.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("asset.id", currentAsset.id)
        }
        def totalRows = hosts.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = hosts?.collect { [ cell: ['',
            "<a href='../host/show?id=${it.id}'>${it.hostname}</a>",
                it.status.toString(), it.env.toString(),
                "<a href='../person/show?id=${personService.getAdmin(it)?.id}'>${personService.getAdmin(it).toString()}</a>",
                it.getMaxMemoryGBString(), it.getCpuMhzCoreString(),
                it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editPhyServerHosts(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        System.out.println(params)
        if (params.hostEnv) params.env = Environment.get(params.hostEnv)
        if (params.status) params.status = Status.get(params.status)
        if(!params.type)
            params.type = 'Non-global Zone'

        if(params.isGlobal) {
            params.asset = null
        }
        else
            params.asset = PhysicalServer.get(params.assetId)



        // determine our action
        switch (params.oper) {
            case 'add':
                // add instruction sent
                //System.out.println(params)
                item = new Host(params)
//                item.main_asset = Asset.get(params.assetId)
                if (! item.hasErrors() && item.save()) {
                    if(params.hostSA) {
                        def primarySA = new SupportRole(supportedObject: item, person: Person.get(params.hostSA), roleType: 'Technical', roleName: RoleType.findByRoleName('Primary SA') )
                        primarySA.save(failOnError: true, flush:true)
                    }
                    message = "Created host ${params.hostname}"
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

        def response = [message:message,state:state,id:id,text:item.toString()]

        return response
    }


    /*****************************************************************/
    /* Rack Functionality
    /*****************************************************************/
    def addAssetToRack(params) {

        def collision = false
        def rackInstance = Rack.get(params.id)
        def asset = Asset.get(params.asset_Id)
        def rackTemp = rackInstance.RUs
        params.addPosition = (params.addPosition.toInteger() -1)
//        System.out.println(params.addPosition)
        def retVal
        def i

        if(!asset.RUplacement.empty) {
            return -1
        }
        else {

            for (i=0; i<asset.RU_size; i++) {
                if (rackTemp.get(params.addPosition-i).RUstatus != 'Open' && rackTemp.get(params.addPosition-i).RUstatus != 'Planned') {
                    collision = true
                }
            }
            if(! collision) {
                for (i=0; i<asset.RU_size; i++) {
                    def label
                    def fillRU = rackTemp.get(params.addPosition-i)
                    if (fillRU.RUstatus == 'Planned') {
                        label = """<table  style=\"margin-top: -3px;\"><tr> \
                            <td id='ruFilled' style=\"width:50%\"><a href='/its/dcmd/asset/show?id=${asset.id}'> \
                            ${asset.itsId}</a> \
                             </td><td id='ruPlanned' style=\"width:50%\"> \
                              <a href='/its/dcmd/asset/show?id=${fillRU.planFill?.id}'>${fillRU.planFill?.itsId} (Planned)</a></td> \
                             </tr></table>"""
                        fillRU.RUstatus = 'Both'
                        fillRU.filledBy = asset
                        fillRU.onRack = rackInstance
                        fillRU.label = label
                        fillRU.save()

                    }
                    else {
                        label = "<a href='/its/dcmd/asset/show?id=${asset.id}'>${asset.itsId}</a>"
                        fillRU = new RackUnit(RUstatus:'Filled', filledBy: asset, onRack: rackInstance, label: label)
                    }
                    rackTemp.set(params.addPosition-i, fillRU)
                    asset.RUplacement.add(fillRU)
                }
                asset.RU_begin = params.addPosition+1
                rackInstance.save(failOnError:true, flush: false)
                asset.save(failOnError: true, flush:false)
                return rackInstance.RUs

            }
            else {
                return -2
//                flash.message = "Cannot put asset there"
            }
        }
    }

    def addPlannedAssetToRack(params) {

        def collision = false
        def rackInstance = Rack.get(params.id)
        def asset = Asset.get(params.asset_Id)
        def rackTemp = rackInstance.RUs
        params.addPosition = (params.addPosition.toInteger() -1)
        def i

        if(!asset.RUplanning.empty) {
            return -1
        }
        else {

            for (i=0; i<asset.RU_size; i++) {
                if (rackTemp.get(params.addPosition-i).RUstatus != 'Open' && rackTemp.get(params.addPosition-i).RUstatus != 'Filled') {
                    collision = true
                }
            }
            if(! collision) {
                for (i=0; i<asset.RU_size; i++) {
                    def label
                    def fillRU = rackTemp.get(params.addPosition-i)
                    if (fillRU.RUstatus == 'Filled') {
                        label = """<table  style=\"margin-top: -3px;\"><tr> \
                            <td id='ruFilled' style=\"width:50%\"><a href='/its/dcmd/asset/show?id=${fillRU?.filledBy?.id}'> \
                            ${fillRU.filledBy?.itsId}</a> \
                             </td><td id='ruPlanned' style=\"width:50%\"> \
                              <a href='/its/dcmd/asset/show?id=${asset.id}'>${asset.itsId} (Planned)</a></td> \
                             </tr></table>"""
//                        fillRU = new RackUnit(RUstatus:'Both', planFill: asset, filledBy: rackTemp.get(params.addPosition-i).filledBy,  onRack: rackInstance, label: label)
                        fillRU.RUstatus = 'Both'
                        fillRU.planFill = asset
                        fillRU.onRack = rackInstance
                        fillRU.label = label
                        fillRU.save()

                    }
                    else {
                        label = "<a href='/its/dcmd/asset/show?id=${asset.id}'>${asset.itsId} (Planned)</a>"
                        fillRU = new RackUnit(RUstatus:'Planned', planFill: asset, onRack: rackInstance, label: label)
                    }
                    rackTemp.set(params.addPosition-i, fillRU)
                    asset.RUplanning.add(fillRU)
                }
                asset.RU_planned_begin = params.addPosition+1
                rackInstance.save(failOnError:true, flush: false)
                asset.save(failOnError: true, flush:false)
                return rackInstance.RUs
            }
            else {
                return -2
            }
        }
    }

    def removePlannedAsset(params) {

        def theAsset = Asset.get(params.assetId)

        System.out.println(params)
        System.out.println(theAsset.RUplanning)
        theAsset.RUplanning.each{clearRU->
            if(clearRU.RUstatus == 'Both') {
                clearRU.RUstatus = 'Filled'
                clearRU.label = "<a href='/its/dcmd/asset/show?id=${clearRU.filledBy?.id}'>${clearRU.filledBy?.itsId}</a>"
            }
            else if(clearRU.RUstatus == 'Planned') {
                clearRU.RUstatus = 'Open'
                clearRU.label = 'Open'
            }
            clearRU.planFill = null
            if(! clearRU.hasErrors() && clearRU.save()) {
            }
            else {
                def message = clearRU.errors.allErrors.get(0).toString()
                System.out.println(message)
            }
        }
        System.out.println(theAsset.RUplanning)
        theAsset.RU_planned_begin = 0
        theAsset.RUplanning.clear()

//        theAsset.save()
        if (!theAsset.hasErrors() && theAsset.save()) {}
        else {
            def message = theAsset.errors.allErrors.get(0).toString()
            System.out.println(message)
        }
    }

    def removeAsset(params) {

        def theAsset = Asset.get(params.assetId)

        theAsset.RUplacement.each{clearRU->
            if(clearRU.RUstatus == 'Both') {
                clearRU.RUstatus = 'Planned'
                clearRU.label = "<a href='/its/dcmd/asset/show?id=${clearRU.planFill?.id}'>${clearRU.planFill?.itsId} (Planned)</a>"
            }
            else if(clearRU.RUstatus == 'Filled') {
                clearRU.RUstatus = 'Open'
                clearRU.label = 'Open'
            }
            clearRU.filledBy = null
            if(! clearRU.hasErrors() && clearRU.save()) {
            }
            else {
                def message = clearRU.errors.allErrors.get(0).toString()
                System.out.println(message)
            }
        }
        theAsset.RU_begin = 0
        theAsset.RUplacement.clear()

        if (!theAsset.hasErrors() && theAsset.save()) {}
        else {
            def message = theAsset.errors.allErrors.get(0).toString()
            System.out.println(message)
        }
        System.out.println(theAsset.RUplacement)
    }

    /*****************************************************************/
    /* ELOG
    /*****************************************************************/

    def elogReportHosts(Asset myAsset) {
          /*
        StringBuffer elogMessage = new StringBuffer()
        String tempString

        def hostList = Host.createCriteria().list() {
            like("asset.id", myAsset.id)
            order("hostname", "desc")
        }

        if(hostList.size() > 0) {
            hostList?.each { thisHost->
                if(thisHost.)
                tempString = hostService.elogReportServices(thisHost)
                if(tempString.length() == 0)
                    elogMessage.append("<b>" + thisHost.hostname + " (" + thisHost.env.toString() + "</b> is dependent but does not support any Applications")
                elogMessage.append("<b>" + thisHost.hostname + " (" + thisHost.env.toString() + ")</b> is dependent and supports the following Applications:<br>")
                elogMessage.append(hostService.elogReportServices(thisHost))

                elogMessage.append("-------------------------------------------------------------------------------------------<br><br>")
            }
        }

        return elogMessage.toString()
             */
    }

    /*****************************************************************/
    /* Derived Column Functions
    /*****************************************************************/
    def calcUnassigned(Asset asset, AssetCapacity capacity) {
        def assignedTotal = 0.0f
        assignedTotal = Cluster.createCriteria().get() {
            like("asset.id", asset.id)
            like("resourceType", capacity.resourceType)
            projections {
                sum("amountAssigned")
            }
        }
        if (assignedTotal)
            capacity.unassignedCapacity = capacity.currentCapacity - assignedTotal
        else
            capacity.unassignedCapacity = capacity.currentCapacity

        return capacity.unassignedCapacity
    }

    def calcReserved(Asset asset, String rType) {
        def reserveTotal = 0.0f
        reserveTotal = ResourceAllocation.createCriteria().list {
            like("asset.id", asset.id)
            like("resourceType", rType)
            projections {
                sum("reservedAmount")
            }
        }
        return reserveTotal
    }

    def calcAlloc(Asset asset, String rType) {
        def reserveTotal = 0.0f
        reserveTotal = ResourceAllocation.createCriteria().get {
            like("asset.id", asset.id)
            like("resourceType", rType)
            projections {
                sum("allocatedAmount")
            }
        }
        return reserveTotal
    }

    def getCurrentLocationLink(Asset asset) {
        def location = Rack.get(asset?.getRackAssignmentId())?.location
        if(!location) {
            return ""
        }
        else
            return "<a href='../location/show?id=${location.id}'>${location.toString()}</a>"
    }
    def getPlannedLocationLink(Asset asset) {
        def location = Rack.get(asset?.getPlannedRackAssignmentId())?.location
        if(location == null) {
            return ""
        }
        else
            return "<a href='../location/show?id=${location.id}'>${location.toString()}</a>"
    }

    /*****************************************************************/
    /* VALIDATION FUNCTIONS
    /*****************************************************************/
    def checkCapExpand(Asset asset, Float resourceCapacity, Float maxExpand, Long rowId) {

        def capacity = AssetCapacity.createCriteria().get {
            like("asset", asset)
            like("id", rowId)
        }
        if (maxExpand==-1) {
            maxExpand = capacity.maxExpandableCapacity
        }
        else if(resourceCapacity==-1){
            resourceCapacity = capacity.currentCapacity
        }
        if (maxExpand >= resourceCapacity)
            return true
        else
            return false
    }

    def getAssetAndAddNewReplacement(Long assetId, Replacement replacement){
        def asset = Asset.get(assetId)
        asset.addToReplacementSet(replacement)
        asset.save(failOnError:true, flush:true)
        asset
    }

    def getAssetAndRemoveReplacement(Long assetId, Long replacementAssetId){
        def asset = Asset.get(assetId)
        def replacement = Replacement.get(replacementAssetId)
        asset.removeFromReplacementSet(replacement)
        asset.save(flush:true)
    }

    def getReplacementsForJSON(replacements){
        def result = []
        replacements.sort{priority -> priority}.each{s ->
            result << [id:s.id, mainAsset:s.main_asset.toString(), replacementAsset:s.replacement.toString()]
        }
        log.debug "result: ${result.inspect()}"
        result
    }

    def listAssets(Map params) {
		log.debug "service params: ${params}"
		
		if(!params.iDisplayStart){
			params.iDisplayStart = 0
		}
		
		if(!params.iDisplayLength){
			params.iDisplayLength = 10
		}
		
		if(!params.sSortDir_0){
			params.sSortDir_0 = 'asc'
		}

				def dataToRender = setupReturnValues()
		dataToRender.sEcho = params.sEcho
		//params.iSortCol_0 = params.iSortCol_0 == "0" ? 1 : (int)params.iSortCol_0
		
		log.debug "sort col index: ${params.iSortCol_0}"
		
		def assets
		def sortableColumns = [ 'itsId']
		
		log.debug "sortable Columns: ${sortableColumns[0]}"
		
		def criteria = Asset.createCriteria()
		
		assets = criteria.list(max: params.iDisplayLength, offset:params.iDisplayStart){
			if(params.sSearch){
				like("itsId", "%${params.sSearch}%")
			}
			order(sortableColumns[0], params.sSortDir_0)
		}
		def countCriteria = Asset.createCriteria()
		def assetCount = countCriteria.get(){
			projections{
				rowCount()
			}
		}
		dataToRender.iTotalRecords = assetCount
		dataToRender.iTotalDisplayRecords = assets.getTotalCount()

		assets?.each { asset ->
		   dataToRender.aaData << [asset.id,
								   asset.itsId,
								   asset.assetType?.name,
								   asset.ownershipType,
								   asset.productDescription,
								   asset.modelDesignation,
								   asset.vendorSupportLevel,
								   asset.serialNo,
								   asset.endOfServiceLife,
								   asset.warrantyEndDate,
//								   asset.purchaseListPrice,
//								   asset.purchaseDiscountPrice,
//								   asset.maintenanceListPrice,
//								   asset.maintenanceDiscountPrice,
								   asset.decalNo,
								   asset.retiredDate,
								   asset.removedFromInventoryDate,
								   asset.isAvailableForParts,
//								   asset.replacementAvailabilityDate,
								   asset.migrationCompletionDate,
								   asset.assetStatus,
								   asset.postMigrationStatus,
								   asset.dateCreated,
								   asset.lastUpdated]
		}
		dataToRender
	}

	def cleanseCloneParams(params){
		def keys = params.keySet().findAll{k->k.contains('_clone')}
		keys.each{k->
			params.remove(k)
			log.debug "removed ${k}"
		}
	}
	
	def logParams(params){
		params.sort().each{k,v ->
			log.debug "${k}: ${v}"
		}
	}
	
	def failedVersionCheck(assetInstance, version, locale){
		if(version){
			version = version.toLong()
			if (assetInstance.version > version) {
				def msg = getMessage('asset.label',//code
									 null, //args
									 'Asset', //default
									 locale)
				assetInstance.errors.rejectValue("version", //property
												"default.optimistic.locking.failure", //code
												msg as Object[], //args
												"Another user has updated this Asset while you were editing") //default
				return true
			}
		}
		false
	}


    def listPhysicalServerAsSelect() {
        def lst = PhysicalServer.createCriteria().list {
            order('itsId', 'asc')
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }

	
	private def setupReturnValues(){
		def dataToRender = [:]
		dataToRender.sEcho = ''
		dataToRender.aaData=[]
		dataToRender
	}
	
	private def getMessage(String code, Object[] args, String defaultMessage, Locale locale){
		def context = grailsApplication.getMainContext()
		context.getMessage(code, args, defaultMessage, locale)		
	}

}
