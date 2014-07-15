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

import org.hibernate.criterion.Order
import grails.gorm.DetachedCriteria

class ServiceService {

    def personService
    def hostService
    def generalService

	def grailsApplication

    static transactional = true

    /*****************************************************************/
    /* List Page Grid Functions
    /*****************************************************************/
    def listAll(params) {
        def sortIndex = params.sidx ?: 'serviceTitle'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        if(params.appEnv) params.serviceEnv = Environment.findByAbbreviation(params.serviceEnv)

        def services = Service.createCriteria().list(max: maxRows, offset: rowOffset) {
// Search Filters
            if (params.serviceTitle) ilike('serviceTitle', "%${params.serviceTitle}%")
            if (params.app) {
                application {
                    ilike('applicationTitle', "%${params.app}%")
                }
            }
            if (params.serviceEnv) {
                env {
                    ilike('abbreviation', "%${params.serviceEnv}%")
                }
            }
            if (params.serviceDescription) ilike('serviceDescription', "%${params.serviceDescription}%")
            if (params.status) {
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
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")

// Sort
            switch (sortIndex) {
                case 'serviceEnv':
                    env {
                        order('abbreviation', sortOrder)
                    }
                    break
                case 'app':
                    application {
                        order('applicationTitle', sortOrder)
                    }
                    break
                case 'status':
                    status {
                        order('abbreviation', sortOrder)
                    }
                    break
                default:
                    if(sortOrder == 'asc')
                        order( Order.asc(sortIndex).ignoreCase() )
                    else
                        order( Order.desc(sortIndex).ignoreCase() )
                    break
            }
        }

        def totalRows = services.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = services?.collect { [ cell: [it.serviceTitle,
                "<a href='../application/show?id=${it.application?.id}'>${it.application.toString()}</a>",
                it.env.toString(),it.serviceDescription, it.status.toString(),
                "<a href='../person/show?id=${personService.getAdmin(it)?.id}'>${personService.getAdmin(it).toString()}</a>",
                "<a href='../person/show?id=${personService.getAdmin(it.application)?.id}'>${personService.getAdmin(it.application).toString()}</a>",
                it.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def listAllSubGrid(params) {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = 1000

        if(params.serviceId)
            params.id = params.serviceId

        def currentService = Service.get(params.id)

        def tiers = TierDependency.createCriteria().list() {
            like('service.id', currentService.id)
            eq('serviceInstance', true)
        }

        def totalRows = tiers.size()
        def numberOfPages = totalRows
        def results = tiers?.collect { [ cell: [
            "<a href='../tier/show?id=${it.tier?.id}'>${it.tier.toString()}</a>",
            it.tier?.tierDescription,
            "<a href='../host/show?id=${it.tier.host?.id}'>${it.tier.host.toString()}</a>",
                it.tier?.loadBalanced, it.tier?.type?.toString(),
                it.tier?.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: 1, records: totalRows, total: numberOfPages]
        return jsonData
    }


    /*****************************************************************/
    /* Tab Grid Functions
    /*****************************************************************/
    def listTierDependencies(params) {

        System.out.println(params)
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = 1000
        def rowOffset = 0
        if(params.serviceId)
            params.id = params.serviceId

        def currentService = Service.get(params.id)
//        def currentAssignment = ServiceServerAssignment.get(params.id)

        System.out.println(currentService.id)

        def tierDependencies = Tier.createCriteria().list(max: maxRows, offset: rowOffset) {
            instanceDependencies {
                eq('service.id', currentService.id)
                eq('serviceInstance', true)
            }
        }

        def totalRows = tierDependencies.size()
        def numberOfPages = totalRows
        def results = tierDependencies?.collect { [ cell: ['',
//                it.toString(),
//                it.instanceDependencies.first().serviceInstance,
//                it.tierDescription,
                "<a href='../host/show?id=${it.host?.id}'>${it.host.toString()}</a>",
                "<a href='../person/show?id=${personService.getAdmin(it.host).id}'>${personService.getAdmin(it.host).toString()}</a>",
                it.loadBalanced,
                it.type.toString(), it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: 1, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editTierDependencies(params) {
        def currentService
        def item = null
        def dependency = null
        def newTier = null
        def newDependency = null
        def message = ""
        def state = "FAIL"
        def id

        // determine our action
        switch (params.oper) {
            case 'add':
                currentService = Service.get(params.serviceId)
                params.service = currentService
                params.host = Host.get(params.host)
                params.mainApp = currentService.application
                params.serviceInstance = true

                newTier = new Tier(params)
                params.tier = newTier
                newDependency = new TierDependency(params)

                if (! newTier.hasErrors() && newTier.save()) {
                    if(! newDependency.hasErrors() && newDependency.save()) {
                        message = "Service Instance created."
                        generalService.inheritRoles(currentService)
                        id = newTier.id
                        state = "OK"
//                        System.out.println(newDependency.id + " " + newTier.tierName)
                    }
                    else {
                        System.out.println(newDependency.errors.allErrors.get(0).toString())
                    }
                } else {
                    System.out.println(newTier.errors.allErrors.get(0).toString())
                }
                break;
            case 'edit':
                System.out.println(params.id)
                item = Tier.get(params.id)
                System.out.println(item.toString())

                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    message = "Tier ${item.id} Updated"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                System.out.println(params.id)
                item = Tier.get(params.id)
                System.out.println(dependency.toString())
                if (item) {
                    //if (! dependency.hasErrors() && dependency.delete()){}
                    if (! item.hasErrors() && item.delete()) {
                        state = "OK"
                    }
                }
                break;
        }
        def response = [message:message,state:state,id:id]
        return response
    }


    def listServiceDependencies(params) {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = 1000
        if(params.serviceId)
            params.id = params.serviceId

        def currentService = Service.get(params.id)

        def serviceDependencies = TierDependency.createCriteria().list() {
            service {
                like('id', currentService.id)
            }
            eq('serviceInstance', false)
        }

        def totalRows = serviceDependencies.size()
        def numberOfPages = totalRows
        def results = serviceDependencies?.collect { [ cell: ['',
            it.tier.toString(),
            "<a href='../application/show?id=${it.tier.mainApp?.id}'>${it.tier.mainApp.toString()}</a>",
            it.tier.mainApp?.env.toString(),
            "<a href='../host/show?id=${it.tier.host?.id}'>${it.tier.host.toString()}</a>",
            it.tier.loadBalanced, it.tier.type.toString(),
            it.tier.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: 1, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editServiceDependencies(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        params.service = Service.get(params.serviceId)

        // determine our action
        switch (params.oper) {
            case 'add':
                params.tier = Tier.get(params.tier)
                params.serviceInstance = false
                item = new TierDependency(params)

                if (! item.hasErrors() && item.save()) {
                    message = "Software Dependency created."
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.allErrors.get(0).toString()
                }
                break;
            case 'edit':
                item = TierDependency.get(params.id)

                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    message = "Software Dependency ${item.id} Updated"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = TierDependency.get(params.id)
                if (item) {
                    id = item.id
                    if (! item.hasErrors() && item.delete()) {
                        state = "OK"
                    }
                }
                break;
        }
        def response = [message:message,state:state,id:id]
        return response
    }

    /*****************************************************************/
    /* List & Get Functions
    /*****************************************************************/
    def listServicesAsSelect() {
        def lst = Service.createCriteria().list() {
            order('serviceTitle', 'asc')
        }

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append("${it.serviceTitle} (${it.env?.abbreviation})")
            buf.append("</option>")
        }
        buf.append("</select>")

        return buf.toString()
    }

    /*****************************************************************/
    /* VALIDATION FUNCTIONS
    /*****************************************************************/

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
	
	private def getMessage(String code, Object[] args, String defaultMessage, Locale locale){
		def context = grailsApplication.getMainContext()
		context.getMessage(code, args, defaultMessage, locale)		
	}

}
