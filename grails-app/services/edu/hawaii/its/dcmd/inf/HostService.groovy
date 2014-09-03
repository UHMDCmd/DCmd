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
import org.hibernate.criterion.Order
import org.hibernate.Criteria
import javax.persistence.criteria.JoinType
//import org.bouncycastle.asn1.isismtt.x509.Restriction
import javax.swing.plaf.basic.BasicInternalFrameTitlePane
import org.hibernate.criterion.CriteriaSpecification
import groovy.time.*
import org.hibernate.criterion.Projections

class HostService {

    def personService
    def assetService
    def generalService

    static transactional = true

    /***********************************************************************************/
    /* Grid Functions
    /***********************************************************************************/

    def listAll(params) {
        def sortIndex = params.sidx
        def sortOrder  = params.sord
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def timeStart = new Date()

/*************************************************************************************
 * Difficult problem to do a large join query but get a distinct list.  Solution is to
 * first do the join query and cache, then query on the returned ids.
 * Resources:
 * http://stackoverflow.com/questions/19781198/improving-grails-createcriteria-query-speed-with-joins
 * http://ondrej-kvasnovsky.blogspot.com/2012/01/grails-listdistinct-and-pagination.html
  *************************************************************************************/

        // Initial large cached join query
        def hostIds = Host.createCriteria().list() {
            projections {
                distinct('id')
            }
            // File List Filter
            if(!params.supportFilterHostList.isEmpty()) {
                'in'('hostname', params.supportFilterHostList)
            }

            createAlias('asset', 'asset', Criteria.LEFT_JOIN)
            createAlias('cluster', 'cluster', Criteria.LEFT_JOIN)
            createAlias('tiers.mainApp', 'tiers.mainApp', Criteria.LEFT_JOIN)
            createAlias('tiers.instanceDependencies.service.supporters', 'tiers.instanceDependencies.service.supporters', Criteria.LEFT_JOIN)
            createAlias('supporters.person', 'supporters.person', Criteria.LEFT_JOIN)
            createAlias('supporters.roleName', 'supporters.roleName', Criteria.LEFT_JOIN)

            cache true
        }

        // Secondary query on ids from previous (with filtering and sorting)...
        def hosts = Host.createCriteria().list(max: maxRows, offset: rowOffset) {
            'in'('id', hostIds)
            // Search Filters
            if (params.hostname) ilike('hostname', "%${params.hostname}%")
            if (params.asset) {
                asset {
                    ilike('itsId', "%${params.asset}%")
                }
            }
            if (params.type) ilike('type', "%${params.type}%")
            if (params.status) {
                status {
                    ilike('abbreviation', "%${params.status}%")
                }
            }
            if (params.os) ilike('os', "%${params.os}%")
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
            if (params.env) {
                env{
                    ilike('abbreviation', "%${params.env}%")
                }
            }
            if (params.appList) {
                tiers {
                    mainApp {
                        ilike('applicationTitle', "%${params.appList}%")
                    }
                }
            }
            if (params.serviceList) {
                tiers {
                    instanceDependencies {
                        service {
                            ilike('serviceTitle', "%${params.serviceList}%")
                        }
                    }
                }
            }
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")

            // Sort
            switch (sortIndex) {
                case 'asset':
                    asset {
                        order('itsId', sortOrder)
                    }
                    break
                case 'status':
                    asset {
                        order('modelDesignation', sortOrder)
                    }
                    break
                case 'env':
                    env {
                        order('abbreviation', sortOrder)
                    }
                    break
                default:
                    if(sortOrder == 'asc') {
                        order( Order.asc(sortIndex).ignoreCase() )
                    }
                    else
                        order( Order.desc(sortIndex).ignoreCase() )
                    break
            }
        }

        def totalRows = hosts.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

//        System.out.println("createCriteria " + TimeCategory.minus(new Date(), timeStart))
//        timeStart = new Date()

        def results = hosts?.collect { [ cell: [
                "${it.hostname}",
                it.getServerOrClusterLink(), it.type,
                it.status.toString(), it.os,
                personService.getSupportPersonLink(it, 'Primary SA'),
                it.env.toString(),
                getApplicationListAsString(it),
                getServiceListAsString(it),
                getServiceAdminAsString(it),
                it.generalNote,
//                "<a href='../asset/show?id=${it.asset?.getRackAssignmentId()}'>${it.asset?.getRackAssignment().toString()}</a>",
//                assetService.getCurrentLocationLink(it.asset)],
                ],id: it.id ]}

//        System.out.println("collection time: " + TimeCategory.minus(new Date(), timeStart))
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }


    def listAllSupportList(params) {
        def sortIndex = params.sidx
        def sortOrder  = params.sord
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        def hostIds = Host.createCriteria().list() {
            projections {
                distinct('id')
            }
            createAlias('status', 'status', Criteria.LEFT_JOIN)
            //createAlias('supporters', 'supporters', Criteria.LEFT_JOIN)
            createAlias('supporters.person', 'supporters.person', Criteria.LEFT_JOIN)
            createAlias('supporters.roleName', 'supporters.roleName', Criteria.LEFT_JOIN)
            cache true
        }

        def hosts = Host.createCriteria().list(max: maxRows, offset: rowOffset) {
            'in'('id', hostIds)
// File List Filter
            if(!params.supportFilterHostList.isEmpty()) {
                'in'('hostname', params.supportFilterHostList)
            }

// Search Filters
            if (params.hostname) ilike('hostname', "%${params.hostname}%")
            if (params.status) {
                status {
                    ilike('abbreviation', "%${params.status}%")
                }
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
            if(params.secondSA) {
                supporters {
                    roleName {
                        like('roleName', 'Secondary SA')
                    }
                    person {
                        ilike('uhName', "%${params.secondSA}%")
                    }
                }
            }
            if(params.thirdSA) {
                supporters {
                    roleName {
                        like('roleName', 'Tertiary SA')
                    }
                    person {
                        ilike('uhName', "%${params.thirdSA}%")
                    }
                }
            }
            if(params.servLead) {
                supporters {
                    roleName {
                        like('roleName', 'Service Lead')
                    }
                    person {
                        ilike('uhName', "%${params.servLead}%")
                    }
                }
            }
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")

// Sort
            switch (sortIndex) {
                case 'asset':
                    asset {
                        order('itsId', sortOrder)
                    }
                    break
                default:
                    if(sortOrder == 'asc') {
                        order( Order.asc(sortIndex).ignoreCase() )
                    }
                    else
                        order( Order.desc(sortIndex).ignoreCase() )
                    break
            }
        }

        def totalRows = hosts.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = hosts?.collect { [ cell: ['',
                "${it.hostname}",
                it.status?.abbreviation,
                personService.getSupportPersonLink(it, 'Primary SA'),
                personService.getSupportPersonLink(it, 'Secondary SA'),
                personService.getSupportPersonLink(it, 'Tertiary SA'),
                personService.getSupportPersonLink(it, 'Service Lead'),
                it.generalNote],
                id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editAllSupportList(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        // Get objects from IDs
        System.out.println(params)
        params.theObject = Host.get(params.id)

        if(params.hostname)
            params.theObject.hostname = params.hostname
        if(params.status)
            params.theObject.status = Status.get(params.status)

        if(params.primarySA && params.primarySA != null) {
            params.thePerson = Person.get(params.primarySA)
            params.roleType = 'Technical'
            generalService.createOrEditRole(params, 'Primary SA')
        }
        if(params.secondSA && params.secondSA != null) {
            params.thePerson = Person.get(params.secondSA)
            params.roleType = 'Technical'
            generalService.createOrEditRole(params, 'Secondary SA')
        }
        if(params.thirdSA && params.thirdSA != null) {
            params.thePerson = Person.get(params.thirdSA)
            params.roleType = 'Technical'
            generalService.createOrEditRole(params, 'Tertiary SA')
        }
        if(params.servLead && params.servLead != null) {
            params.thePerson = Person.get(params.servLead)
            params.roleType = 'Functional'
            generalService.createOrEditRole(params, 'Service Lead')
        }
        if(params.generalNote)
            params.theObject.generalNote = params.generalNote

        if (! params.theObject.hasErrors() && params.theObject.save()) {
//                    message = "Capacity ${item.toString()} Updated for ${params.asset.toString()}"
            id = params.theObject.id
            state = "OK"
        }

        def response = [state:state,id:id]
        return response

    }


    def listTiers(params) {
        def sortIndex = params.sidx ?: 'tierTitle'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentHost = Host.get(params.hostId)

        def tierDependencies = TierDependency.createCriteria().list(max: maxRows, offset: rowOffset) {
            tier {
                like("host.id", currentHost.id)
            }
            eq("serviceInstance", true)
        }

//        def applications = Application.getAll()
        def totalRows = tierDependencies.size()
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = tierDependencies?.collect { [ cell: ['',
               // it.tier?.tierName,
                "<a href='../application/show?id=${it.service?.application?.id}'>${it.service?.application?.toString()}</a>",
                "<a href='../service/show?id=${it.service?.id}'>${it.service.toString()}</a>",
                personService.getSupportPersonLink(it.service, 'Primary SA'),
                it.tier.loadBalanced, it.tier.type.toString(),
                it.tier.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editTiers(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        // Get objects from IDs
        if (params.tierType) params.type = TierType.get(params.tierType)
        if (params.service) {
            params.service = Service.get(params.service)
            params.mainApp = Application.get(params.application)
        }

        params.host = Host.get(params.hostId)

        // determine our action
        switch (params.oper) {
            case 'add':
                System.out.println(params)
                item = new Tier(params)

                if (! item.hasErrors() && item.save()) {
                    def dependency = new TierDependency(tier: item, service: params.service, serviceInstance: true)
                    if (! dependency.hasErrors() && dependency.save()) {
                        generalService.inheritRoles(params.service)
                        id = item.id
                        state = "OK"
                    }
                } else {
            message = item.errors.allErrors.get(0).toString()
                    System.out.println(message)
                }
                break;
            case 'edit':
                def dependency = TierDependency.get(params.id)
                item = dependency.tier
                item.properties = params

                if (! item.hasErrors() && item.save()) {
//                    message = "Capacity ${item.toString()} Updated for ${params.asset.toString()}"
                    id = item.id
                    state = "OK"
                }
                break;
            case 'del':
                def dependency = TierDependency.get(params.id)

                item = dependency.tier

                if (item) {
                    id = item.id

                    item.delete()
                    dependency.delete()
                    state = "OK"

                }
                break;
        }

        def response = [state:state,id:id]
        return response

    }

    /*****************************************************************/
    /* ELOG
    /*****************************************************************/

    def elogReport(Host myHost) {
        StringBuffer elogMessage = new StringBuffer()
        def serviceList
        def instanceList
        def applicationList = Application.createCriteria().list() {
            services {
                tiers {
                    tier {
                        like("host.id", myHost.id)
                        like("loadBalanced", false)
                    }
                }
            }
            order("applicationTitle")
        }

        System.out.println(applicationList)
        if(applicationList.size() > 0) {
            applicationList.unique()
            applicationList.each { app ->
                elogMessage.append("-------------------------------------------------------------------------------------------<br><br>")
                elogMessage.append("Application: ${app.applicationTitle} (${app.env.toString()})<br>")
                elogMessage.append("Contact Info: ")
                elogMessage.append(personService.getAdmin(app)?.firstName + " ")
                elogMessage.append(personService.getAdmin(app)?.lastName + " - ")
                elogMessage.append(personService.getAdmin(app).toString() + "@hawaii.edu" + "<br>")

                serviceList = Service.createCriteria().list() {
                    like("application.id", app.id)
                    tiers {
                        tier {
                            like("host.id", myHost.id)
                            like("loadBalanced", false)
                        }
                    }
                }
                if(serviceList.size() > 0) {
                    elogMessage.append("<ul>")
                    serviceList.each { serv ->
                        elogMessage.append("<li>Service: ${serv.toString()}")

                        instanceList = Tier.createCriteria().list {
                            like("host.id", myHost.id)
                            like("loadBalanced", false)
                            instanceDependencies {
                                service {
                                    like("id", serv.id)
                                }
                            }
                        }
                        if(instanceList.size() > 0) {
                            elogMessage.append("<ul>")
                            instanceList.each { inst ->
                                elogMessage.append("<li>Software Instance: ${inst.toString()}")
                            }
                            elogMessage.append("</ul>")
                        }
                    }
                    elogMessage.append("</ul>")
                }

            }
        }
        return elogMessage.toString()
    }

    def elogReportTiers(Host myHost) {

        StringBuffer elogMessage = new StringBuffer()

        def tiersList = Tier.createCriteria().list() {
            like("host.id", myHost.id)
            order("tierName")
        }

        if(tiersList.size() > 0) {
            elogMessage.append("Software Instances affected:<br><ul>")
            tiersList?.each { tier->
                elogMessage.append("<li>" + tier.toString())
                if (tier.loadBalanced == true)
                    elogMessage.append(" (Load Balanced)")
            }
            elogMessage.append("</ul>")
        }

        return elogMessage.toString()

    }

    def elogReportServices(Host myHost) {

        StringBuffer elogMessage = new StringBuffer()
        def currentApp = new Application(applicationTitle: "1")
        def first = true

        def services = Service.createCriteria().list() {
            tiers {
                tier {
                    like("host.id", myHost.id)
                    like("loadBalanced", false)
                }
            }
            order("serviceTitle")
            application {
                order("applicationTitle")
            }
        }

        def balancedServices = Service.createCriteria().list() {
            tiers {
                tier {
                    like("host.id", myHost.id)
                    like("loadBalanced", true)
                }
            }
            order("serviceTitle")
            application {
                order("applicationTitle")
            }
        }

        System.out.println(balancedServices)
//        balancedServices = balancedServices.minus(services)

        if(services.size() > 0) {
            elogMessage.append("-------------------------------------------------------------------------------------------<br><br>")

            services.unique()
            services?.each { service->
                if(service.application?.applicationTitle != currentApp.applicationTitle)
                {
                    if (currentApp.applicationTitle != "1") {
                        elogMessage.append("</ul><br>")

                        if(balancedServices.size() > 0) {
                            first = true
                            System.out.println(currentApp.applicationTitle)

                            balancedServices.each { balServ->
                                if(balServ.application?.applicationTitle == currentApp.applicationTitle) {
                                    if (first) {
                                        first = false
                                        elogMessage.append(currentApp.applicationTitle + " services that are load balanced and remain available:<ul>")
                                    }
                                    elogMessage.append("<li>" + balServ.toString())
                                    balancedServices.remove(balServ)
                                }
                            }
                            if (!first)
                                elogMessage.append("</ul><br>")
                        }
                        elogMessage.append(currentApp.applicationTitle + " (" + currentApp.env.toString() + ")" + " Application Administrator:<br>")
                        elogMessage.append(personService.getAdmin(currentApp)?.firstName + " ")
                        elogMessage.append(personService.getAdmin(currentApp)?.lastName + " - ")
                        elogMessage.append(personService.getAdmin(currentApp).toString() + "@hawaii.edu" + "<br><br>")
                        elogMessage.append("-------------------------------------------------------------------------------------------<br><br>")

                    }
                    currentApp = service.application
                    elogMessage.append(currentApp.applicationTitle + " (" + currentApp.env.toString() + ")" + " will be affected, with the following services unavailable:<ul>")
                }
                elogMessage.append("<li>" + service.toString())
            }
            elogMessage.append("</ul><br>")

            if(balancedServices.size() > 0) {
                first = true
                System.out.println(currentApp.applicationTitle)

                balancedServices.each { balServ->
                    if(balServ.application?.applicationTitle == currentApp.applicationTitle) {
                        if (first) {
                            first = false
                            elogMessage.append(currentApp.applicationTitle + " Services that are load balanced and remain available:<ul>")
                        }
                        elogMessage.append("<li>" + balServ.toString())
                        balancedServices.remove(balServ)

                    }
                }
                if (!first)
                    elogMessage.append("</ul><br>")
            }
            elogMessage.append(currentApp.applicationTitle + " (" + currentApp.env.toString() + ")" + " Application Administrator:<br>")
            elogMessage.append(personService.getAdmin(currentApp)?.firstName + " ")
            elogMessage.append(personService.getAdmin(currentApp)?.lastName + " - ")
            elogMessage.append(personService.getAdmin(currentApp).toString()+ "@hawaii.edu" + "<br><br>")
        }

        currentApp = new Application(applicationTitle: "1")

        if(balancedServices.size() > 0) {
            elogMessage.append("-------------------------------------------------------------------------------------------<br><br>")
            balancedServices?.each { balServ->
                if(balServ.application?.applicationTitle != currentApp.applicationTitle)
                {
                    if (currentApp.applicationTitle != "1") {
                        elogMessage.append("</ul><br>")

                        elogMessage.append(currentApp.applicationTitle + " (" + currentApp.env.toString() + ")" + " Application Administrator:<br>")
                        elogMessage.append(personService.getAdmin(currentApp)?.firstName + " ")
                        elogMessage.append(personService.getAdmin(currentApp)?.lastName + " - ")
                        elogMessage.append(personService.getAdmin(currentApp).toString() + "@hawaii.edu" + "<br><br>")
                        elogMessage.append("-------------------------------------------------------------------------------------------<br><br>")

                    }
                    currentApp = balServ.application
                    elogMessage.append(currentApp.applicationTitle + " (" + currentApp.env.toString() + ")" + " will be affected, but only the following <b>load-balanced</b> services:<ul>")
                }
                elogMessage.append("<li>" + balServ.toString())
            }
            elogMessage.append("</ul><br>")
            elogMessage.append(currentApp.applicationTitle + " (" + currentApp.env.toString() + ")" + " Application Administrator:<br>")
            elogMessage.append(personService.getAdmin(currentApp)?.firstName + " ")
            elogMessage.append(personService.getAdmin(currentApp)?.lastName + " - ")
            elogMessage.append(personService.getAdmin(currentApp).toString()+ "@hawaii.edu" + "<br><br>")

        return elogMessage.toString()
        }
    }


    /***********************************************************************************/
    /* Formatting functions to display complex info
    /***********************************************************************************/

    def getApplicationListAsString(Host myHost) {

        def applications

        StringBuffer buf = new StringBuffer("")

        myHost.tiers.mainApp.unique().each {
            buf.append("<a href='../application/show?id=${it.id}'>")
            buf.append("${it.toString()}</a>, ")
        }
        if(buf.length() > 1) {
            buf.delete(buf.length()-2, buf.length())
        }

        return buf.toString()
    }


    def getServiceListAsString(Host myHost) {
        def services
        StringBuffer buf = new StringBuffer("")
        myHost.tiers.each { tier->
            tier.instanceDependencies.service.each {
                buf.append("<a href='../service/show?id=${it.id}'>")
                buf.append("${it.toString()}</a>, ")
            }
        }
        if(buf.length() > 1) {
            buf.delete(buf.length()-2, buf.length())
        }

        return buf.toString()
    }


    def getServiceAdminAsString(Host myHost) {

        StringBuffer buf = new StringBuffer("")
        def personList = []

        myHost.tiers.each { tier->
            tier.instanceDependencies.service.each { theService ->
                theService.supporters.each {
                    if(it.roleName.roleName == 'Primary SA') {
                        personList.add(it.person)
                    }
                }
            }
        }
        personList.unique().each {
            buf.append("<a href='../person/show?id=${it.id}'>")
            buf.append("${it.uhName}</a>, ")
        }
        if(buf.length() > 1) {
            buf.delete(buf.length()-2, buf.length())
        }
        return buf.toString()
    }


    /*****************************
       Export File functions
    /****************************/
    def getAppListForFileExport(Host myHost) {
        def applications
        applications = Application.createCriteria().list() {
            tiers {
                like('host.id', myHost.id)
            }
            resultTransformer Criteria.DISTINCT_ROOT_ENTITY
            order("applicationTitle","asc")
        }
        StringBuffer buf = new StringBuffer("")
        applications.each {
            buf.append("${it.applicationTitle}, ")
        }
        if(buf.length() > 1)
            buf.delete(buf.length()-2, buf.length())

        return buf.toString()
    }

    def getAppAdminForFileExport(Host myHost) {
         def applications = Application.createCriteria().list() {
            tiers {
                like('host.id', myHost.id)
            }
            resultTransformer Criteria.DISTINCT_ROOT_ENTITY
            order("applicationTitle","asc")
        }

        StringBuffer buf = new StringBuffer("")
        if(!applications.empty) {
            def myApp = applications.first()

            def item = SupportRole.createCriteria().list() {
                like("supportedObject.id", myApp.id)
                roleName {
                    like("roleName", 'Primary SA')
                }
            }
            if (!item.empty) {
                buf.append("${item.first()?.person?.uhName}")
            }
        }
        return buf.toString()
    }

    def getLocation(Host myHost){
      return myHost?.asset?.location.toString()
    }

    def getRackAssignment(Host myHost){
        return myHost?.asset?.rackAssignment
    }


    def calcUtil(Host host, String rType) {
        def utilTotal = 0.0f
        utilTotal = ApplicationServerAssignment.createCriteria().get {
            like("host.id", host.id)
            like("resourceType", rType)
            projections {
                sum("amountReserved")
            }
        }
        return utilTotal
    }

    def listHostsAsSelect() {
        def lst = Host.findAll()

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }
        return buf.toString()
    }
}
