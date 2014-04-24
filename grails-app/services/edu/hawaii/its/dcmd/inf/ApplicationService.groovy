package edu.hawaii.its.dcmd.inf

import org.hibernate.criterion.Order
import org.hibernate.Criteria
import grails.gorm.DetachedCriteria

class ApplicationService {

    def personService
    def generalService

    static transactional = true

    def listAppsAsSelect() {
        def lst = Application.findAll()

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        return buf.toString()
    }

    def listApplicationsAsSelect() {
        def lst = Application.findAll()

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }


    def listEnvsAsSelect() {
        def lst = Environment.findAll()

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }

    def listAll(params) {
        def sortIndex = params.sidx ?: 'applicationTitle'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        // Initial large cached join query
        def appIds = Application.createCriteria().list() {
            // File List Filter
            if(!params.supportFilterHostList.isEmpty()) {
                tiers {
                    host {
                        'in'('hostname', params.supportFilterHostList)
                    }
                }
//                resultTransformer Criteria.DISTINCT_ROOT_ENTITY
            }

            projections {
                distinct('id')
            }
            createAlias('env', 'env', Criteria.LEFT_JOIN)
            createAlias('status', 'status', Criteria.LEFT_JOIN)
            createAlias('supporters.person', 'supporters.person', Criteria.LEFT_JOIN)
            createAlias('supporters.roleName', 'supporters.roleName', Criteria.LEFT_JOIN)
            cache true
        }

        // Secondary query on ids from previous (with filtering and sorting)...
        def applications = Application.createCriteria().list(max: maxRows, offset: rowOffset) {
            'in'('id', appIds)
// Search Filters
            if (params.applicationTitle) ilike('applicationTitle', "%${params.applicationTitle}%")
            if (params.env) {
                env {
                    ilike('abbreviation', "%${params.env}%")
                }
            }
            if (params.applicationDescription) ilike('applicationDescription', "%${params.applicationDescription}%")
            if (params.status) {
                status{
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
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")

// Sort
            switch(sortIndex) {
                case 'env':
                    env {
                        order('abbreviation', sortOrder)
                    }
                    break
                case 'status':
                    status {
                        order('abbreviation', sortOrder)
                    }
                    env {
                        order('abbreviation', 'asc')
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

        def totalRows = applications.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = applications?.collect { [ cell: [it.applicationTitle, it.env.toString(), it.applicationDescription,
                it.status.toString(),
                personService.getSupportPersonLink(it, 'Primary SA'),
                it.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def listAllServiceSubGrid(params) {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = 1000

        if(params.applicationId)
            params.id = params.applicationId

        def currentApplication = Application.get(params.id)

        def services = Service.createCriteria().list() {
            application {
                like('id', currentApplication.id)
            }
            order('serviceTitle')
        }
        def totalRows = services.size()
        def numberOfPages = totalRows
        def results = services?.collect { [ cell: [
                "<a href='../service/show?id=${it.id}'>${it.serviceTitle}</a>",
                it.env?.abbreviation, it.serviceDescription,  getHostListAsString(it),
                it.status.toString(),
                personService.getSupportPersonLink(it,'Primary SA'),
                it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: 1, records: totalRows, total: numberOfPages]
        return jsonData
    }


    def listAllSupportList(params) {
        def sortIndex = params.sidx
        def sortOrder  = params.sord
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        // Initial large cached join query
        def appIds = Application.createCriteria().list() {
            // File List Filter
            if(!params.supportFilterHostList.isEmpty()) {
                tiers {
                    host {
                        'in'('hostname', params.supportFilterHostList)
                    }
                }
//                resultTransformer Criteria.DISTINCT_ROOT_ENTITY
            }

            projections {
                distinct('id')
            }
            createAlias('env', 'env', Criteria.LEFT_JOIN)
            createAlias('supporters.person', 'supporters.person', Criteria.LEFT_JOIN)
            createAlias('supporters.roleName', 'supporters.roleName', Criteria.LEFT_JOIN)
            cache true
        }

        def apps = Application.createCriteria().list(max: maxRows, offset: rowOffset) {
            'in'('id', appIds)

// Search Filters
            if (params.applicationTitle) ilike('applicationTitle', "%${params.applicationTitle}%")
            if (params.env) {
                env {
                    ilike('abbreviation', "%${params.env}%")
                }
            }
            if(params.projManager) {
                supporters {
                    roleName {
                        like('roleName', 'Project Manager')
                    }
                    person {
                        ilike('uhName', "%${params.projManager}%")
                    }
                }
            }
            if(params.funcLead) {
                supporters {
                    roleName {
                        like('roleName', 'Function Lead')
                    }
                    person {
                        ilike('uhName', "%${params.funcLead}%")
                    }
                }
            }
            if(params.devLead) {
                supporters {
                    roleName {
                        like('roleName', 'Developer Lead')
                    }
                    person {
                        ilike('uhName', "%${params.devLead}%")
                    }
                }
            }
            if(params.techLead) {
                supporters {
                    roleName {
                        like('roleName', 'Technical Lead')
                    }
                    person {
                        ilike('uhName', "%${params.techLead}%")
                    }
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
            if(params.primaryDBA) {
                supporters {
                    roleName {
                        like('roleName', 'Primary DBA')
                    }
                    person {
                        ilike('uhName', "%${params.primaryDBA}%")
                    }
                }
            }
            if(params.secondDBA) {
                supporters {
                    roleName {
                        like('roleName', 'Secondary DBA')
                    }
                    person {
                        ilike('uhName', "%${params.secondDBA}%")
                    }
                }
            }
            if(params.thirdDBA) {
                supporters {
                    roleName {
                        like('roleName', 'Tertiary DBA')
                    }
                    person {
                        ilike('uhName', "%${params.thirdDBA}%")
                    }
                }
            }
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")

// Sort
            switch (sortIndex) {
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

        def totalRows = apps.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

                def results = apps?.collect { [ cell: ['',
                "${it.applicationTitle}",
                it.env.toString(),
                personService.getSupportPersonLink(it, 'Project Manager'),
                personService.getSupportPersonLink(it, 'Functional Lead'),
                personService.getSupportPersonLink(it, 'Developer Lead'),
                personService.getSupportPersonLink(it, 'Technical Lead'),
                personService.getSupportPersonLink(it, 'Primary SA'),
                personService.getSupportPersonLink(it, 'Secondary SA'),
                personService.getSupportPersonLink(it, 'Tertiary SA'),
                personService.getSupportPersonLink(it, 'Primary DBA'),
                personService.getSupportPersonLink(it, 'Secondary DBA'),
                personService.getSupportPersonLink(it, 'Tertiary DBA'),
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
        params.theObject = Application.get(params.id)

        if(params.hostname)
            params.theObject.applicationTitle = params.applicationTitle

        if(params.projManager && params.projManager != null) {
            params.thePerson = Person.get(params.projManager)
            params.roleType = 'Functional'
            generalService.createOrEditRole(params, 'Project Manager')
        }
        if(params.funcLead && params.funcLead != null) {
            params.thePerson = Person.get(params.funcLead)
            params.roleType = 'Functional'
            generalService.createOrEditRole(params, 'Functional Lead')
        }
        if(params.devLead && params.devLead != null) {
            params.thePerson = Person.get(params.devLead)
            params.roleType = 'Functional'
            generalService.createOrEditRole(params, 'Developer Lead')
        }
        if(params.techLead && params.techLead != null) {
            params.thePerson = Person.get(params.techLead)
            params.roleType = 'Technical'
            generalService.createOrEditRole(params, 'Technical Lead')
        }
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
        if(params.primaryDBA && params.primaryDBA != null) {
            params.thePerson = Person.get(params.primaryDBA)
            params.roleType = 'Technical'
            generalService.createOrEditRole(params, 'Primary DBA')
        }
        if(params.secondDBA && params.secondDBA != null) {
            params.thePerson = Person.get(params.secondDBA)
            params.roleType = 'Technical'
            generalService.createOrEditRole(params, 'Secondary DBA')
        }
        if(params.thirdDBA && params.thirdDBA != null) {
            params.thePerson = Person.get(params.thirdDBA)
            params.roleType = 'Technical'
            generalService.createOrEditRole(params, 'Tertiary DBA')
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




    def listServiceGrid(params) {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = 1000
        if(params.applicationId)
            params.id = params.applicationId

        def currentApplication = Application.get(params.id)

        def services = Service.createCriteria().list() {
            application {
                like('id', currentApplication.id)
            }
        }

        def totalRows = services.size()
        def numberOfPages = totalRows
        def results = services?.collect { [ cell: ['', it.serviceTitle,
                it.env?.abbreviation, it.serviceDescription,getHostListAsString(it),
                "<a href=../person/show?id=${personService.getAdmin(it).id}>${personService.getAdmin(it).toString()}</a>",
                it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: 1, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editServiceGrid(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        def role = null

        // determine our action
        switch (params.oper) {
            case 'add':
                if(params.serviceEnv)
                    params.env = Environment.get(params.serviceEnv)
                if(params.serviceStatus)
                    params.status = Status.get(params.serviceStatus)
                params.application = Application.get(params.applicationId)
                item = new Service(params)
                if (! item.hasErrors() && item.save()) {
                    message = "Service Created"
                    id = item.id

                    if(params.serviceAdmin != 'null') { // Create SupportRole entry for ServiceAdmin specified
                        role = new SupportRole([supportedObject: item, roleName: RoleType.findByRoleName("Primary SA"), roleType: 'Technical', person: Person.get(params.serviceAdmin)])
                        role.save()
                    }
                    generalService.inheritRoles(params.application)

                    state = "OK"
                } else {
                    System.out.println(item.errors.allErrors.get(0).toString())
                    message = item.errors.allErrors.get(0).toString()
                }
                break;
            case 'edit':
                if(params.serviceEnv)
                    params.env = Environment.get(params.serviceEnv)
                if(params.serviceStatus)
                    params.status = Status.get(params.serviceStatus)
                params.application = Application.get(params.applicationId)

                item = Service.get(params.id)
                if(item) {
                    item.properties = params

                    if(params.serviceAdmin && params.serviceAdmin != 'null') { // Create SupportRole entry for ServiceAdmin specified
                        def oldRole = SupportRole.createCriteria().get() {
                            supportedObject {
                                like('id', item.id)
                            }
                            roleName {
                                like('roleName', 'Primary SA')
                            }
                        }
                        if(oldRole) {
                            oldRole.delete()
                        }
                        role = new SupportRole([supportedObject: item, roleName: RoleType.findByRoleName("Primary SA"), roleType: 'Technical', person: Person.get(params.serviceAdmin)])
                        role.save()
                    }

                    if (! item.hasErrors() && item.save()) {
                        message = "Service changes saved"
                        id = item.id
                    }

                    state = "OK"
                } else {
                    System.out.println(item.errors.allErrors.get(0).toString())
                    message = item.errors.allErrors.get(0).toString()
                }
                break;
            case 'del':
                def error=false
                item = Service.get(params.id)
                if (item) {
                    def tiers = TierDependency.createCriteria().list() {
                        like('service.id', item.id)
                        eq('serviceInstance', true)
                        projections {
                            distinct("tier")
                        }
                    }
//        System.out.println(tiers)
                    tiers.each { tier ->
                        if(!error) {
                            if (!tier.hasErrors()) {
                                tier.delete()
                            }
                            else {
                                System.out.println(tier.errors.fieldError)
                                error = true
                            }
                        }
                    }
                    if (!error) {
                        if(! item.hasErrors() && item.delete()) {
                            state = "OK"
                        }
                    }
                }
                break;
        }
        def response = [message:message,state:state,id:id]

        return response
    }

    def listTierGrid(params) {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = 1000
        if(params.applicationId)
            params.id = params.applicationId

        def currentApplication = Application.get(params.id)
//        def currentAssignment = ApplicationServerAssignment.get(params.id)

        def tiers = Tier.createCriteria().list() {
            mainApp {
                eq('id', currentApplication.id)
            }
            instanceDependencies {
                eq('serviceInstance', true)
                service {
                    order('serviceTitle')
                }
            }
        }
        def totalRows = tiers.size()
        def numberOfPages = totalRows
        def results = tiers?.collect { [ cell: ['',
                it.getMainServiceLink(),
                "<a href='../host/show?id=${it.host?.id}'>${it.host.toString()}</a>",
                "<a href='../person/show?id=${personService.getAdmin(it.host).id}'>${personService.getAdmin(it.host).toString()}</a>",
                it.loadBalanced, it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: 1, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editTierGrid(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        if(params.tierService)
            params.service = Service.get(params.tierService)
        if(params.host)
            params.host = Host.get(params.host)

        System.out.println(params)

        // determine our action
        switch (params.oper) {
            case 'add':
                params.mainApp = Application.get(params.applicationId)
                item = new Tier(params)
                if (! item.hasErrors() && item.save()) {
                    if(params.service) {
                        def newDep = new TierDependency(tier:item, service: params.service, serviceInstance: true)
                        if(!newDep.hasErrors()) {
                            newDep.save()
                            generalService.inheritRoles(params.service)
                        }
                    }
                    message = "Tier ${item.tierName} created."
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.allErrors.get(0).toString()
                }
                break;
            case 'edit':
                item = Tier.get(params.id)
                if(params.service) {
                    def dependencies = TierDependency.createCriteria().list() {
                        eq('tier.id', item.id)
                        eq('serviceInstance', true)
                    }
                    dependencies.each { dep ->
                        dep.delete()
                    }
                    def newDep = new TierDependency(tier: item, service: params.service, serviceInstance: true)
                    if(!newDep.hasErrors())
                        newDep.save()
                }
                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    message = "Tier ${item.tierName} Updated"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = Tier.get(params.id)
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


    def getHostListAsString(Service myService) {
        def hosts
        hosts = Host.createCriteria().list() {
            tiers {
                instanceDependencies {
                    like('service.id', myService.id)
                    eq('serviceInstance', true)
                }
            }
            resultTransformer Criteria.DISTINCT_ROOT_ENTITY
            order("hostname","asc")
        }
        StringBuffer buf = new StringBuffer("")
        hosts.each {
            buf.append("<a href='../host/show?id=${it.id}'>${it.hostname}</a>,")
        }
        if(buf.length() > 1) {
            buf.delete(buf.length()-2, buf.length())
        }
        return buf.toString()
    }

}
