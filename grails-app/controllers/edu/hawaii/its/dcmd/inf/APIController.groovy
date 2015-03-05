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
import grails.transaction.Transactional
import org.springframework.http.HttpStatus

class APIController {
    static allowedMethods = [hostList: "GET", hostShow:"GET", hostUpdate:"PUT", hostCreate:"POST", hostAddService: "PUT", hostAddSupport: "PUT", hostListSupport: "GET", hostDelete: "DELETE",
                            roleTypeList: "GET", personList:"GET", serviceList:"GET", applicationList:"GET"]
    static responseFormats = ['json', 'xml']
    static requestFormats = ['json', 'xml']
    def generalService


    //  def scaffold = true
    def hostList() {
        JSON.use('hostAPI') {
            respond Host.list()
        }
    }
    def hostShow() {
        //System.out.println(params.id.getClass())
        def hostInstance
        try {
            hostInstance = Host.get(params.id)
        }
        catch(Exception E) {
            hostInstance = Host.findByHostname(params.id)
        }
        if(hostInstance == null)
            respond null, [status: HttpStatus.NOT_FOUND]

        respond hostInstance
    }

    @Transactional
    def hostUpdate () {
        def errorMessage = []
        // Convert incoming JSON to params structure
        request.JSON.each { k,v ->
            params[k] = v
        }
        // Get by either ID or hostname
        def hostInstance
        try {
            hostInstance = Host.get(params.id)
        }
        catch(Exception E) {
            hostInstance = Host.findByHostname(params.id)
        }
        if(hostInstance == null) {
            errorMessage.add("Host not found")
        }
        else {
            /* Convert parameters that need to reference objects */
            params.id = hostInstance.id
            if (params.asset) {
                params.asset = Asset.findByItsId(params.asset)
                System.out.println(params.asset)
                if (params.asset == null)
                    errorMessage.add("Asset not found")
            }
            if (params.environment) {
                params.environment = Environment.findByAbbreviation(params.environment)
                if(params.environment == null)
                    errorMessage.add("Environment not found")
            }
            if(params.status){
                params.status = Status.findByAbbreviation(params.status)
                if(params.status == null)
                    errorMessage.add("Status not found")
            }

            params.generalNote = params.notes
            if (params.cluster) {
                params.cluster = Cluster.findByName(params.cluster)
                if(params.cluster == null)
                    errorMessage.add("Cluster not found")
            }

            hostInstance.properties = params   // Set new properties

            if (params.primarySA) { // Delete old PrimarySA Role if exists
                if(Person.findByUhName(params.primarySA) == null) {
                    errorMessage.add("PrimarySA staff member not found")
                }
                else {
                    def oldPSA = SupportRole.createCriteria().get {
                        roleName {
                            like('roleName', "Primary SA")
                        }
                        eq('supportedObject.id', hostInstance.id)
                    }
                    def PSARole = new SupportRole(supportedObject: hostInstance, person: Person.findByUhName(params.primarySA), roleName: RoleType.findByRoleName("Primary SA"), roleType: "Technical")

                    if (errorMessage.empty) {
                        if (oldPSA != null) {
                            oldPSA.delete(flush: true)
                        }
                        PSARole.save(flush: true)
                    }
                    else
                        PSARole.discard()
                }
            }
        }
        if(errorMessage.empty) {
            hostInstance.save(flush: true)
            respond hostInstance, [status: HttpStatus.OK]
        }
        else {
            hostInstance?.discard()
            render text:errorMessage.toString(), status: HttpStatus.NOT_FOUND
        }
    }


    @Transactional
    def hostCreate() {
        def errorMessage = []
        request.JSON.each { k,v ->
            params[k] = v
        }

        if (params.asset) {
            params.asset = Asset.findByItsId(params.asset)
            if (params.asset == null)
                errorMessage.add("Asset not found")
        }
        if (params.environment) {
            params.env = Environment.findByAbbreviation(params.environment)
            if(params.env == null)
                errorMessage.add("Environment not found")
        }
        else
            params.env = Environment.findByAbbreviation('prod')
        if(params.status){
            params.status = Status.findByAbbreviation(params.status)
            if(params.status == null)
                errorMessage.add("Status not found")
        }
        else
            params.status = Status.findByAbbreviation('Available')

        params.generalNote = params.notes
        if (params.cluster) {
            params.cluster = Cluster.findByName(params.cluster)
            if(params.cluster == null)
                errorMessage.add("Cluster not found")
        }
        if(params.primarySA) {
            params.primarySA = Person.findByUhName(params.primarySA)
            if (params.primarySA == null)
                errorMessage.add("Primary SA person not found")
        }
        if(params.serviceName || params.appEnv || params.appName) {
            params.application = Application.findByApplicationTitleAndEnv(params.appName, Environment.findByAbbreviation(params.appEnv))
            params.service = Service.findByServiceTitleAndApplication(params.serviceName, params.application)
            if(params.service == null) {
                errorMessage.add("Service:" + params.serviceName + ", appEnv:" + params.appEnv + ", appName:" + params.appName)
                errorMessage.add("Service not found (requires valid appName, appEnv, and serviceName)")
            }
        }

        if(errorMessage.empty) {
            def hostInstance = new Host(params)
            hostInstance.save(flush: true)
            if(params.primarySA) {
                def PSARole = new SupportRole(supportedObject: hostInstance, person: params.primarySA, roleName: RoleType.findByRoleName("Primary SA"), roleType: "Technical")
                PSARole.save(flush: true)
                hostInstance.save(flush: true)
            }
            if(params.service) {
                def newTier = new Tier(host:hostInstance, mainApp:params.service.application)
                if (! newTier.hasErrors() && newTier.save()) {
                    def dependency = new TierDependency(tier: newTier, service: params.service, serviceInstance: true)
                    if (!dependency.hasErrors() && dependency.save())
                        generalService.inheritRoles(params.service)
                }
            }
            respond hostInstance, [status: HttpStatus.OK]
        }
        else
            render text:errorMessage.toString(), status: HttpStatus.NOT_FOUND
    }

    def hostDelete() {
        def hostInstance
        try {
            hostInstance = Host.get(params.id)
        }
        catch(Exception E) {
            hostInstance = Host.findByHostname(params.id)
        }
        if(hostInstance == null)
            render status: HttpStatus.NOT_FOUND
        else {
            hostInstance.delete()
            render status: HttpStatus.OK
        }
    }

    def hostAddService() {
        def hostInstance
        def errorMessage = []
        // Convert incoming JSON to params structure
        request.JSON.each { k,v ->
            params[k] = v
        }
        // Get by either ID or hostname
        try {
            hostInstance = Host.get(params.id)
        }
        catch(Exception E) {
            hostInstance = Host.findByHostname(params.id)
        }
        if(hostInstance == null) {
            errorMessage.add("Host not found")
        }
        else {
            params.id = hostInstance.id
            if(params.serviceName || params.appEnv || params.appName) {
                params.application = Application.findByApplicationTitle(params.appName)
                params.service = Service.findByServiceTitleAndEnvAndApplication(params.serviceName, Environment.findByAbbreviation(params.appEnv), params.application)
                if(params.service == null) {
                    errorMessage.add("Service not found (requires valid appName, appEnv, and serviceName)")
                    errorMessage.add(params.serviceName + ", " + Environment.findByAbbreviation(params.appEnv)+ ", " + params.application)
                }
            }
//            if (params.serviceName || params.serviceEnv) {
//                params.service = Service.findByServiceTitleAndEnv(params.serviceName, Environment.findByAbbreviation(params.serviceEnv))
//                if (params.service == null)
//                    errorMessage.add("Service not found (requires valid serviceName and serviceEnv)")
//            }

            params.generalNote = params.notes

            if (errorMessage.empty) {
                hostInstance.properties = params
                hostInstance.save(flush: true)

                def newTier = new Tier(host:hostInstance, mainApp:params.service.application)
                if (! newTier.hasErrors() && newTier.save()) {
                    def dependency = new TierDependency(tier: newTier, service: params.service, serviceInstance: true)
                    if (!dependency.hasErrors() && dependency.save())
                        generalService.inheritRoles(params.service)
                }
                respond hostInstance, [status: HttpStatus.OK]
            } else
                render text: errorMessage.toString(), status: HttpStatus.NOT_FOUND
        }
    }

    def hostAddSupport() {
        def errorMessage = []
        // Convert incoming JSON to params structure
        request.JSON.each { k,v ->
            params[k] = v
        }
        // Get by either ID or hostname
        def hostInstance
        try {
            hostInstance = Host.get(params.id)
        }
        catch(Exception E) {
            hostInstance = Host.findByHostname(params.id)
        }
        if(hostInstance == null) {
            errorMessage.add("Host not found")
        }

        params.person = Person.findByUhName(params.person)
        if(params.person == null)
            errorMessage.add("Staff member not found")

        params.roleName = RoleType.findByRoleName(params.role)
        if(params.roleName == null)
            errorMessage.add("Role not found")

        params.roleType = 'Technical'
        params.supportRoleNotes = params.notes
        params.supportedObject = hostInstance

        if(errorMessage.empty) {
            def newRole = new SupportRole(params)
            newRole.save(flush:true)
            respond newRole, [status: HttpStatus.OK]
        }
        else
            render text:errorMessage.toString(), status: HttpStatus.NOT_FOUND
    }

    def hostListSupport() {

    }

    def roleTypeList() {
        respond RoleType.getAll()
    }

    def personList() {
        JSON.use('personAll') {
            respond Person.getAll()
        }
    }

    def applicationList() {
        JSON.use('applicationAll') {
            respond Application.getAll()
        }
    }

    def applicationShow() {
        //System.out.println(params.id.getClass())
        def appInstance
        try {
            appInstance = Application.get(params.id)
        }
        catch(Exception E) {
            appInstance = Application.findByApplicationTitle(params.id)
        }
        if(appInstance == null)
            respond null, [status: HttpStatus.NOT_FOUND]

        respond appInstance
    }

    def serviceList() {
        respond Service.getAll()
    }

    def clusterList() {
        respond Cluster.getAll()
    }

    def hostTypeList() {
        def typeList = [
                [Name:'Solaris Global Zone'],
                [Name:'Non-Global Zone'],
                [Name:'VMWare'],
                [Name:'VMWare Host'],
                [Name:'Standalone']
        ]

        respond typeList
    }

    def environmentList() {
        respond Environment.getAll()
    }

    def statusList() {
        respond Status.getAll()
    }


    def index() {}
}
