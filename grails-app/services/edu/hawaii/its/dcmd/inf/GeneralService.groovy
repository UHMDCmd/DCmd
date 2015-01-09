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

class GeneralService {

    static transactional = true

    def inheritRoles(parent) {
        def servRoles = SupportRole.createCriteria().list() {
            eq('supportedObject.id', parent.id)
        }
        servRoles.each { theRole ->
            cascadeSupportRole(parent, theRole.roleName?.id, theRole.roleType)
        }
    }

    def createOrEditRole(params, theRole) {
        def theSupportRole = SupportRole.createCriteria().list() {
            eq('supportedObject.id', params.theObject.id)
            roleName {
                like('roleName', theRole)
            }
        }
        if(params.thePerson != null) {
            if(theSupportRole) {
                theSupportRole = theSupportRole.first()
                theSupportRole.person = params.thePerson
                theSupportRole.save(flush: true)
            }
            else
                theSupportRole = createSupportRole(params.theObject, params.thePerson, params.roleType, RoleType.findByRoleName(theRole)?.id)
        }
        else {
            if(theSupportRole)
                theSupportRole.first().delete()
        }
    }

    def createSupportRole(supportedObject, person, roleType, roleNameId) {
        def newRole = new SupportRole(supportedObject: supportedObject, person: person, roleType: roleType, roleName:  RoleType.get(roleNameId))
        newRole.save()
        cascadeSupportRole(supportedObject, roleNameId, roleType)
        return newRole
    }

    def cascadeSupportRole(supportedObject, roleNameId, roleType) {
        if(supportedObject.supportableType == 'application') {
            def theApp = Application.get(supportedObject.id)
            def theRoleName = RoleType.get(roleNameId)
            def appsRole = SupportRole.createCriteria().list() {
                eq('supportedObject.id', theApp.id)
                roleName {
                    eq('id', roleNameId)
                }
            }
            if(!appsRole.isEmpty()) {
                def thePerson = appsRole.first()?.person
                theApp.services.each { theServ ->
                    def servRoles = SupportRole.createCriteria().list() {
                        eq('supportedObject.id', theServ.id)
                        roleName {
                            eq('id', roleNameId)
                        }
                    }
                    if(servRoles.isEmpty()) {
                        createSupportRole(theServ, thePerson, roleType, roleNameId)
                    }
                }
            }
        }
        if(supportedObject.supportableType == 'service') {
            def roleNameString = RoleType.get(roleNameId).roleName
            if(roleNameString in ['Primary SA', 'Secondary SA', 'Tertiary SA', 'Primary DBA']) {

                System.out.println(RoleType.get(roleNameId))
                def theServ = Service.get(supportedObject.id)
                def servRoles = SupportRole.createCriteria().list() {
                    eq('supportedObject.id', theServ.id)
                    roleName {
                        eq('id', roleNameId)
                    }
                }
                if(!servRoles.isEmpty()) {
                    def thePerson = servRoles.first()?.person

                    if(roleNameString == 'Primary DBA')
                        roleNameId = RoleType.findByRoleName('Service Lead').id

                    def allHosts = Host.createCriteria().list() {
                        tiers {
                            instanceDependencies {
                                eq('serviceInstance', true)
                                eq('service.id', theServ.id)
                            }
                        }
                    }

                    allHosts.each { theHost ->
                        def hostRoles = SupportRole.createCriteria().list() {
                            eq('supportedObject.id', theHost.id)
                            roleName {
                                eq('id', roleNameId)
                            }
                        }
                        if(hostRoles.isEmpty()) {
                            createSupportRole(theHost, thePerson, roleType, roleNameId)
                        }
                    }
                }
            }
        }
    }

    def envList(params) {
        def query = {
            like("abbreviation", "${params.term}%")
            projections {
                property("id")
                property("abbreviation")
                property("name")
            }
        }
        def eList = Environment.createCriteria().list(query)
        def envSelectList = []
        eList.each {
            def envMap = [:]
            envMap.put("id", it[0])
            envMap.put("label", it[2])
            envMap.put("value", it[2])
            envMap.put("abbreviation", it[1])
            envSelectList.add(envMap)
        }
        return envSelectList
    }

    def listEnvsAsSelect() {
        def lst = Environment.createCriteria().list {
            order('abbreviation', 'asc')
        }
        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }

    def listStatusAsSelect() {
        def lst = Status.createCriteria().list {
            order('abbreviation', 'asc')
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }

    def listManufacturersAsSelect() {
        def lst = Manufacturer.createCriteria().list {
            order('name', 'asc')
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }

    def listRolesAsSelect() {
        def lst = RoleType.createCriteria().list {
            order('roleName', 'asc')
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.roleName)
            buf.append("'},")
        }

        return buf.toString()
    }

    def listRacksAsSelect() {
        def lst = Rack.createCriteria().list {
            order('itsId', 'asc')
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }
        buf.append("{id:'0',text:'Not Assigned'}")

        return buf.toString()
    }

    def listConnectorsAsSelect() {
        def lst = PowerConnector.createCriteria().list {
            order('name', 'asc')
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.name)
            buf.append("'},")
        }
        //buf.append("{id:'0',text:'Not Assigned'}")

        return buf.toString()
    }
def listLocationsAsSelect() {
    def lst = Location.createCriteria().list {
        order('locationDescription', 'asc')
    }

    StringBuffer buf = new StringBuffer("")
    lst.each{
        buf.append("{id:\'${it.id}\',text:'")
        buf.append(it.locationDescription)
        buf.append("'},")
    }

    return buf.toString()
}

    def listStripTypesAsSelect() {
        def lst = PowerStripType.createCriteria().list {
            order('name', 'asc')
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }

    def listClustersAsSelect() {
        def lst = Cluster.createCriteria().list {
            order('name', 'asc')
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }
        buf.append("{id:'0', text:'Not Assigned'}")

        return buf.toString()
    }
    def listServerTypesAsSelect() {

        StringBuffer buf = new StringBuffer("")
        buf.append("{id:\'Solaris Global Zone\', text:\'Solaris Global Zone\'},")
        buf.append("{id:\'VMWare\', text:\'VMWare\'},")
        buf.append("{id:\'Windows\', text:\'Windows Server\'},")
        buf.append("{id:\'Standalone\', text:\'Standalone\'}")


        return buf.toString()
    }

    def listHostTypesAsSelect() {
        StringBuffer buf = new StringBuffer("")
        buf.append("{id:\'Solaris Global Zone\', text:\'Solaris Global Zone\'},")
        buf.append("{id:\'Non-Global Zone\', text:\'Non-Global Zone\'},")
        buf.append("{id:\'VMWare\', text:\'VMWare\'},")
        buf.append("{id:\'Standalone\', text:\'Standalone OS\'}")


        return buf.toString()
    }
    def listServicesFromAppAsSelect(appId) {
        StringBuffer buf = new StringBuffer("")

        def services = Service.createCriteria().list() {
            like('application.id', appId)
        }
        services.each {serv ->
            buf.append("{id:${serv.id}, text:${serv.serviceTitle}}")
        }

        return buf.toString()
    }

    def listRackableAssetsAsSelect() {
        def lst = Asset.createCriteria().list() {
            ge('RU_size', 1)
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }
        buf.append("{id:'0', text:'Select an Asset'}")

        return buf.toString()
    }

    def createOrSelectEnv(params) {
        if(params.int('environmentSelect') == null) {
            def newEnv = new Environment(name: "${params.environmentSelect}", abbreviation: "${params.environmentSelect}")
            newEnv.save(flush: true)
            params.env = Environment.get(newEnv.id)
        }
        else {
            params.env = Environment.get(params.environmentSelect)
        }

        return params.env
    }

    def createOrSelectStatus(params) {
        if(params.int('statusSelect') == null) {
            def newStatus = new Status(name: "${params.statusSelect}", abbreviation: "${params.statusSelect}")
            newStatus.save(flush: true)
            params.status = Status.get(newStatus.id)
        }
        else {
            params.status = Status.get(params.statusSelect)
        }

        return params.status
    }

    def createOrSelectRoleType(params) {
        if(params.int('roleNameSelect') == null) {
            def newRole = new RoleType(roleName: "${params.roleNameSelect}")
            newRole.save(flush: true)
            params.roleName = RoleType.get(newRole.id)
        }
        else {
            params.roleName = RoleType.get(params.roleNameSelect)
        }

        return params.roleName
    }
    def createOrSelectManufacturer(params) {
        if(params.int('manufacturerSelect') == null) {
            def manufacturer = new Manufacturer(name: "${params.manufacturerSelect}")
            manufacturer.save(flush: true)
            params.manufacturer = Manufacturer.get(manufacturer.id)
        }
        else {
            params.manufacturer = Manufacturer.get(params.manufacturerSelect)
        }

        return params.manufacturer
    }

    def quickSearchSelect() {
        StringBuffer buf = new StringBuffer("<select id=\'quickSearchSelect\'>")
        buf.append("<option value=\"0\" class=\'quickSearchOption\' selected=\'selected\'>Select for QuickSearch...</option>")

        def items = Host.createCriteria().list() {
            order('hostname', 'asc')
        }
        buf.append("<optgroup label=\'Hosts\'>")
        items.each{

            buf.append("<option value=\"../host/show?id=${it.id}\" class=\'quickSearchOption\'>")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</optgroup>")

        items = PhysicalServer.createCriteria().list() {
            order('itsId', 'asc')
        }
        buf.append("<optgroup label=\'Physical Servers\'>")
        items.each{

            buf.append("<option value=\"../asset/show?id=${it.id}\" class=\'quickSearchOption\'>")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</optgroup>")

        items = Rack.createCriteria().list() {
            order('itsId', 'asc')
        }
        buf.append("<optgroup label=\'Racks\'>")
        items.each{

            buf.append("<option value=\"../asset/show?id=${it.id}\" class=\'quickSearchOption\'>")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</optgroup>")

        items = Application.createCriteria().list() {
            order('applicationTitle', 'asc')
        }
        buf.append("<optgroup label=\'Applications\'>")
        items.each{

            buf.append("<option value=\"../application/show?id=${it.id}\" class=\'quickSearchOption\'>")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</optgroup>")

        items = Service.createCriteria().list() {
            order('serviceTitle', 'asc')
        }
        buf.append("<optgroup label=\'Services\'>")
        items.each{

            buf.append("<option value=\"../service/show?id=${it.id}\" class=\'quickSearchOption\'>")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</optgroup>")

        items = Person.createCriteria().list() {
            order('uhName', 'asc')
        }
        buf.append("<optgroup label=\'Staff\'>")
        items.each{

            buf.append("<option value=\"../person/show?id=${it.id}\" class=\'quickSearchOption\'>")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</optgroup>")

        buf.append("</select>")

        return buf.toString()
    }
}
