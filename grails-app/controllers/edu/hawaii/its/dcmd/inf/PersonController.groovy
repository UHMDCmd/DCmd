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
import edu.hawaii.its.dcmd.inf.Person
import edu.hawaii.its.dcmd.inf.SupportRole
import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEvent
import org.springframework.security.core.context.SecurityContextHolder
import grails.gorm.DetachedCriteria

class PersonController {

	def personService
    def generalService, breadCrumbService
	def scaffold = Person

    def testPage = {}

    def tree = {}

    def home() {
//        render view: 'home'
    }
    def tree() {}

 def setBreadCrumbForCurrentItem = {

     def user = User.load(SecurityContextHolder.context.authentication.principal.id)
     def username = user.username
     def userId = user.id


    def objectType = params.pageType
   // println("objectType: " + objectType)

    def itsName = ""
    def objectId = null;
    if (objectType.equals('asset')){

        Asset assetInstance = params.instance
        itsName = assetInstance.itsId
        objectId = assetInstance.id
    //    println("asset name: " + itsName)
    //    println("asset id: " + objectId)
    }
    else if (objectType.equals('host')){
        Host hostInstance = Host.get(params.id)
        itsName = hostInstance.hostname
        objectId = hostInstance.id
     //   println("host name:" + itsName)
     //   println("host id: " + objectId)
    }
    else if (objectType.equals('application')){
        Application appInstance = Application.get(params.id)
        itsName = appInstance.applicationTitle
        objectId = appInstance.id
     //   println("application name:" + itsName)
     //   println("application id: " + objectId)
    }
    else if (objectType.equals('service')) {
        Service service = Service.get(params.id)
        itsName = service.serviceTitle
        objectId = service.id
       // println("service name:" + itsName)
       // println("service id: " + objectId)
    }
    else if (objectType.equals('tier')){
        Tier tier = Tier.get(params.id)
        itsName = tier.tierName
        objectId = tier.id
       // println("software instance name: " + itsName)
       // println("software instance id: " + objectId)

    }
     else if (objectType.equals('person')){
        Person person = Person.get(params.id)
        itsName = person.uhName
        objectId = person.id
       // println("person name: " + itsName)
       // println("person id: " + objectId)
    }

    else if (objectType.equals('cluster')){
         Cluster cluster = Cluster.get(params.id)
           itsName = cluster.name
           objectId = cluster.id
    }
    else if (objectType.equals('location')){
        Location location = Location.get(params.id)
        itsName = location.locationDescription
        objectId = location.id
    }

     def ext = "/its/dcmd/" + objectType + "/show?id=" + objectId;

    //special case for uisettings page
     if (objectType.equals('settings')){
         itsName = "UI Settings"
         ext = "/its/dcmd/uisettings";
     }

  //   println("Name: " + itsName)
  //   println("extension: " + ext)

     //make new Session Variables
     List bcLabels = session.getAttribute("bcLabels").collect()
     List bcLinks = session.getAttribute("bcLinks").collect()


     //if list doesnt have this item, add it
     if (!(bcLabels.contains(itsName))){

     bcLabels.add(itsName)
     bcLinks.add(ext)

     session.setAttribute("bcLabels",bcLabels)
     session.setAttribute("bcLinks", bcLinks)
     }
     //if the item is already in the list, re-shuffle so that it is the newest item
     else {
         bcLabels.remove(itsName)
         bcLinks.remove(ext)

         bcLabels.add(itsName)
         bcLinks.add(ext)

         session.setAttribute("bcLabels",bcLabels)
         session.setAttribute("bcLinks", bcLinks)
     }
     //maintain list size of 10, remove oldest item on the list.
     if(bcLabels.size() > 10){
         bcLabels.remove(0);
         bcLinks.remove(0);

         session.setAttribute("bcLabels",bcLabels)
         session.setAttribute("bcLinks", bcLinks)
     }

 }

    def resetBreadCrumbs = {
        if((session.valueNames.contains("bcLabels"))
                ||(session.valueNames.contains("bcLinks"))){
            session.removeAttribute("bcLabels")
            session.removeAttribute("bcLinks")
        }

        ArrayList<String> bcLabels = new ArrayList<String>();
        ArrayList<String> bcLinks = new ArrayList<String>();

        //make new Session Variables
        session.setAttribute("bcLabels",bcLabels)
        session.setAttribute("bcLinks",bcLinks)

        def jsonData = [retVal: true]
        render jsonData as JSON
    }


    def save = {

        def personInstance = personService.personLDAP(params.uhName)
//        def personInstance = new Person(params)


        if (!personInstance)
            personInstance = new Person(params)
        else
            personInstance.properties = params
        //selects type of save to do
        String saveOption = params.option

        if(params.managerSelect != '0') {
            personInstance.manager = Person.get(params.managerSelect)
        }
        else
            personInstance.manager=null
        // find the contactInfos that were added then deleted in same form submission...
        //	def _toBeRemoved = personInstance.contactInfos.findAll {it == null}

        // ...and remove them from the "lazy List" to make the list indices contiguous.
        //	if (_toBeRemoved) {
//			personInstance.contactInfos.removeAll(_toBeRemoved)
//		}

        if (personInstance.save(flush: true)) {
            //save options
            if (saveOption.equals("saveEdit")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
                redirect(url: "/person/edit?id=${personInstance.id}")
            }
            else if (saveOption.equals("saveList")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
                redirect(url: "/person/list")
            }
            else if (saveOption.equals("saveCreate")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
                redirect(url: "/person/create")
            }
            else{
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
                redirect(url: "/person/show?id=${personInstance.id}")
            }
            /*	flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
           redirect(url: "/person/show?id=${personInstance.id}")*/
        }
        else {
            render(view: "create", model: [personInstance: personInstance])
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
        println(params)

        //get the instance being updated
        def personInstance = Person.get(params.id)

        if(params.managerSelect != '0') {
            params.manager = Person.get(params.managerSelect)
        }
        else
            params.manager=null
        if (personInstance) {
            //update the object with the params and remove deleted notes
            personInstance.properties = params
//			assetService.removeDeletedNotes(assetInstance)
            def newPerson = personService.personLDAP(params.uhName)
            if(newPerson)
                personInstance.properties = newPerson.properties

            if(params.primaryPhone)
                personInstance.primaryPhone = params.primaryPhone
            if(params.secondPhone)
                personInstance.secondPhone = params.secondPhone
            if(params.alternateEmail)
                personInstance.alternateEmail = params.alternateEmail
            if(params.manager)
                personInstance.manager = params.manager

            //save and redirect
            if (!personInstance.hasErrors() && personInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
                redirect(url: "/person/show?id=${personInstance.id}")
                println(personInstance.primaryPhone)
            } else {
                render(view: "edit", model: [personInstance: personInstance])
            }

            //asset not found; no edits can be performed; show the list
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
    }



    def searchJSON = {
		log.debug "params: ${params.inspect()}"
		def people = personService.searchJSON(params.searchTerm, params.int('maxRows'))
		render people as JSON
	}

    /*****************************************************************/
    /* LISTING FUNCTIONS
    /*****************************************************************/
/*    def list = {
        //BreadCrumb session variable reset
        if((session.valueNames.contains("bcLabels"))
                ||(session.valueNames.contains("bcLinks"))){
            session.removeAttribute("bcLabels")
            session.removeAttribute("bcLinks")
      //      println("removed session variables...")
        }

        ArrayList<String> bcLabels = new ArrayList<String>();
        ArrayList<String> bcLinks = new ArrayList<String>();

        //make new Session Variables
        session.setAttribute("bcLabels",bcLabels)
        session.setAttribute("bcLinks",bcLinks)
      //  println("created new session variables...")
        *//*********************//*
    }*/


    def listAsSelect={

        def lst = Person.findAll()

        lst.sort{ it.uhName }
//        println (lst.size())
        StringBuffer buf = new StringBuffer("<select><option value=null>Please Select...</option>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listAsSelectWithNull={

        def lst = Person.findAll()

        lst.sort{ it.uhName }
//        println (lst.size())
        StringBuffer buf = new StringBuffer("<select><option value=\"0\">Not Assigned</option>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listRoleTypesAsSelect={
        def roleTypes = RoleType.getAll()
        def theList = []

        roleTypes.each {
            def tmp = [id:"${it.id}", text: it.roleName]
            theList.add(tmp)
        }
        def jsonData = [retVal: true, theList: theList]
        render jsonData as JSON
    }

    def getRoleTypes = {
        JSON.use('roleTypeOptions')
        def typeList = RoleType.getAll()
        render typeList as JSON
    }

    /*****************************************************************/
    /* Technical Support Staff Grid
    /*****************************************************************/

    def listTechnicalSupport = {
        def jsonData = personService.listTechnicalSupport(params)
        render jsonData as JSON
    }

    def editTechnicalSupport = {
        def jsonData = personService.editTechnicalSupport(params)
        render jsonData as JSON
    }

    /*****************************************************************/
    /* Functional Support Staff Grid
    /*****************************************************************/

    def listFunctionalSupport = {
        def jsonData = personService.listFunctionalSupport(params)
        render jsonData as JSON
    }

    def editFunctionalSupport = {
        def jsonData = personService.editFunctionalSupport(params)
        render jsonData as JSON
    }

    /******************************************************
     * List and Edit fcns for single-page version
     *****************************************************/
    def listSupportStaffSinglePage = {
        def sortIndex = params.sidx ?: 'roleName'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentObject = SupportableObject.get(params.objectId.toLong())

        def supportStaff = SupportRole.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("supportedObject.id", currentObject.id)
        }
        def totalRows = supportStaff.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = supportStaff?.collect { [ cell: ['', it.roleName.toString(),
                                                       it.person.uhName,
                                                       it.person.id,
                                                       it.person.primaryEmail(), it.person.primaryPhone(), it.supportRoleNotes], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]

        render jsonData as JSON
    }

    def editSupportStaffSinglePage = {
        println(params)
        def item = null
        def message = ""
        def state = "FAIL"
        def id

        if(params.roleName)
            params.roleName = RoleType.get(params.roleName.toLong())
        if(params.person)
            params.person = Person.get(params.person)
        if(params.objectId)
            params.supportedObject = SupportableObject.get(params.objectId)
        params.roleType = 'Functional'

        def response = [state:"FAIL"]
        // determine our action
        switch (params.oper) {
            case 'add':
                item = new SupportRole(params)
                if (! item.hasErrors() && item.save()) {
                    response.id = item.id
                    response.object = item.properties
                    response.state = "OK"
                } else {
                    response.message = item.errors.fieldErrors[0]
                    println(item.errors.fieldErrors[0])
                }
                break;
            case 'edit':
                item = SupportRole.get(params.id)
                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    response.id = item.id
                    response.state = "OK"
                } else {
                    response.message = item.errors.errorCount
                }
                break;
            case 'del':
                item = SupportRole.get(params.id)
                if (item) {
                    response.id = item.id
                    if (! item.hasErrors() && item.delete()) {
//                        message = "Replacement ${params.replacement} for Asset ${params.main_asset} Deleted."
                        response.state = "OK"
                    }
                }
                break;
        }
//        response = [message:message,state:state,id:id]

        render response as JSON
    }


    def getPersonOptions = {
        JSON.use('personOptions')
        render Person.getAll() as JSON
    }


     /***********************************************
     /* Update List of People via LDAP sever, PersonService method is called
     /***********************************************/
     def updatePersonsWithLDAP = {

         List<Person> persons = Person.all
         ArrayList<Person> updatedList = new ArrayList<Person>();

         for (int x = 0; x < persons.size(); x++){
             Person currentPerson = persons.get(x)
                     //println("person" + x + ":" + currentPerson)
             String uhUserName = currentPerson.uhName

             def personInstance = personService.personLDAP(uhUserName)
             if (personInstance == null){
                // println(currentPerson.uhName + " not found in ldap")
                 updatedList.add(currentPerson) //add the original entry
             }
             else if (personInstance != null){
                 updatedList.add(personInstance)
             //    println("person was updated\n")
             }

                personInstance = null
         }

//                 println("original size: " +persons.size())
         for (int i = 0; i < Person.all.size(); i++){

             persons.get(i).properties['uhName','title','lastName','firstName','midInit','telephone','primaryEmail']=  updatedList.get(i).properties['uhName','title','lastName','firstName','midInit','telephone','primaryEmail']

//                     println(Person.all.get(i).properties['uhNumber','uhName','title','lastName','firstName','midInit','telephone','primaryEmail'])

         }
         def jsonData = [retVal: true, val: ""]
         render jsonData as JSON
         //redirect(action: "listAllPerson")
     }


    /*****************************************************************/
    /* Main Hosts Grid
    /*****************************************************************/
    def listAllPerson = {

        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows


        def persons = Person.createCriteria().list(max: maxRows, offset: rowOffset) {
            if (params.uhName) ilike('uhName', "%${params.uhName}%")
            if (params.lastName) ilike('lastName', "%${params.lastName}%")
            if (params.firstName) ilike('firstName', "%${params.firstName}%")
            if (params.midInit) ilike('midInit', "%${params.midInit}%")
            if (params.title) ilike('title', "%${params.title}%")

            if (params.primaryEmail) ilike('primaryEmail', "%${params.primaryEmail}%")
            if (params.telephone) ilike('telephone', "%${params.telephone}%")

            if (params.uhNumber) ilike('uhNumber', "%${params.uhNumber}%")
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")

            order(sortIndex, sortOrder)

        }


        def totalRows = persons.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = persons?.collect { [ cell: ['', it.uhName, it.lastName, it.firstName, it.midInit, it.title, it.getManagerLink(),
                                                  it.telephone,it.primaryPhone, it.secondPhone, it.primaryEmail, it.uhNumber, it.generalNote], id: it.id ] }


        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON

//        refresh = true;
    }

    def editAllPerson = {
        def state='FAIL'
        def id
        def message
        def item = Person.get(params.id)
        if(params.primaryPhone)
            item.primaryPhone = params.primaryPhone
        if(params.secondPhone)
            item.secondPhone = params.secondPhone
        if(params.generalNote)
            item.generalNote = params.generalNote
        if(params.manager != 0)
            item.manager = Person.get(params.manager)
        else
            item.manager=null

        if (! item.hasErrors() && item.save()) {
//                    message = "Capacity ${item.toString()} Updated for ${params.asset.toString()}"
            id = item.id
            state = "OK"
        } else {
            message = item.errors.errorCount
        }

        def response = [state:state,id:id]

        render response as JSON
    }

    /*****************************************************************/
    /* Contact Info Grid
    /*****************************************************************/
    def listContacts = {
        def sortIndex = params.sidx ?: 'contactType'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentPerson = Person.get(params.personId)

       // def contacts =  currentPerson?.collect { [ cell: [it.telephone, it.primaryEmail,it.generalNote], id: it.id ] }
        def contacts =  currentPerson?.collect { [ cell: ['',it.primaryEmail,currentPerson.primaryPhone()], id: it.id ] }

        //def totalRows = contacts.totalCount
        //def numberOfPages = Math.ceil(totalRows / maxRows)
//        def results = contacts?.collect { [ cell: ['', it.isPrimary, it.contactType, it.contactInfo, it.contactNotes], id: it.id ] }

//        def jsonData = [rows: contacts, page: currentPage, records: totalRows, total: numberOfPages]
        def jsonData = [rows: contacts, page: currentPage]

        render jsonData as JSON
    }

    def updateContacts = {
        def currentPerson = Person.get(params.personId)
         currentPerson.properties['primaryEmail','telephone'] = [params.email,params.phone]

    }

    /*****************************************************************/
    /* Support Roles Grid
    /*****************************************************************/
    def listSupportRoles = {
        def sortIndex = params.sidx ?: 'supportedObject'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentPerson = Person.get(params.personId)
        def supportRoles = SupportRole.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("person.id", currentPerson.id)

            // Apply search filters
            if(params.supportedObject) {
                def idList = [0.longValue()]
                def HostCriteria = new DetachedCriteria(Host).build {
                    ilike('hostname', "%${params.supportedObject}%")
                }
                if(!HostCriteria.collect().isEmpty())
                        idList += HostCriteria.collect()?.id

                def AppCriteria = new DetachedCriteria(Application).build {
                    ilike('applicationTitle', "%${params.supportedObject}%")
                }
                if(!AppCriteria.collect().isEmpty())
                        idList += AppCriteria.collect()?.id

                def ServiceCriteria = new DetachedCriteria(Service).build {
                    ilike('serviceTitle', "%${params.supportedObject}%")
                }
                if(!ServiceCriteria.collect().isEmpty())
                    idList += ServiceCriteria.collect()?.id

                def AssetCriteria = new DetachedCriteria(Asset).build {
                    ilike('itsId', "%${params.supportedObject}%")
                }
                if(!AssetCriteria.collect().isEmpty())
                    idList += AssetCriteria.collect()?.id

                'in'('supportedObject.id', idList)
            }
            if(params.objectType) {
                supportedObject {
                    ilike('supportableType', "%${params.objectType}%")
                }
            }
            if(params.roleName) {
                roleName {
                    ilike('roleName', "%${params.roleName}%")
                }
            }
            if(params.roleType) ilike('roleType', "%${params.roleType}%")
            if(params.supportRoleNotes) ilike('supportRoleNotes', "%${params.supportRoleNotes}%")


            // Sort by whatever index was chosen
            if(sortIndex=='objectType') {
                supportedObject {
                    order('supportableType', sortOrder)
                }
            }
            else {
                order(sortIndex, sortOrder)
            }
        }
        def totalRows = supportRoles.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = supportRoles?.collect { [ cell: ['',
                "<a href='../${it.supportedObject.supportableType}/show?id=${it.supportedObject.id}'>${it.supportedObject.toString()}</a>",
                it.supportedObject.supportableType, it.roleName?.roleName, it.roleType, it.supportRoleNotes], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

    def editSupportRoles = {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        // Get objects from IDs
        params.person = Person.get(params.personId)

        // determine our action
        switch (params.oper) {
            case 'add':
                item = generalService.createSupportRole(params.supportedObject, params.person, params.roleType, params.roleName)
                break;
            case 'edit':
                item = SupportRole.get(params.id)
                item.properties = params

                if (! item.hasErrors() && item.save()) {
//                    message = "Capacity ${item.toString()} Updated for ${params.asset.toString()}"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = SupportRole.get(params.id)
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

    def getRoleName = {
        def jsonData
        if(params.supportRoleId) {
            def theRole = SupportRole.createCriteria().get {
                eq('id', params.supportRoleId.toLong())
            }
            if(theRole) {
                jsonData = [retVal:true, roleNameId: theRole.roleName?.id]
            }
            else
                jsonData = [retVal: true, roleNameId: 0]
        }
        else
            jsonData = [retVal: true, roleNameId: 0]

        render jsonData as JSON
    }

    def getPersonIdFromRoleId = {
        def jsonData
        if(params.supportRoleId) {
            def theRole = SupportRole.createCriteria().get {
                eq('id', params.supportRoleId.toLong())
            }
            if(theRole) {
                jsonData = [retVal:true, personId: theRole.person?.id]
            }
            else
                jsonData = [retVal: true, personId: 0]
        }
        else
            jsonData = [retVal: true, personId: 0]

        render jsonData as JSON
    }

    def getPersonIdFromRoleName = {
        def jsonData
        def allRoles = SupportRole.createCriteria().list() {
            eq('supportedObject.id', params.objectId.toLong())
            roleName {
                like('roleName', params.roleName)
            }
        }
        if(!allRoles.isEmpty()) {
            jsonData = [retVal:true, personId: allRoles.first().person?.id]
        }
        else
            jsonData = [retVal: true, personId: 0]
        render jsonData as JSON
    }

    def getManagerId = {
        def jsonData
        def thePerson = Person.get(params.personId.toLong())
        if(thePerson.manager == null)
            jsonData = [retVal:true, managerId: 0]
        else
            jsonData = [retVal:true, managerId: thePerson.manager?.id]

        render jsonData as JSON
    }


    /*****************************************************************/
    /* QuickSearch Functions
    /*****************************************************************/

    def doAllQuickSearch={

        def maxRows = 20
        def hosts = Host.createCriteria().list(max: maxRows) {
            ilike('hostname', "%${params.searchString}%")
            order('hostname', 'asc')
        }
        StringBuffer hostString = new StringBuffer()
        if (hosts.size() == 0)
            hostString.append("No Results Found")
        else {
            hosts.each {
                hostString.append("<a href=\'../host/show?id=${it.id}\'>${it.toString()}</a><br>")
            }
            if (hosts.size() == maxRows)
                hostString.append("More than ${maxRows} found......<br>")
        }


        def assets = PhysicalServer.createCriteria().list(max: maxRows) {
           or {
               ilike('itsId', "%${params.searchString}%")
               ilike('serialNo', "%${params.searchString}%")
               ilike('decalNo',"%${params.searchString}%")
           }
            order('itsId', 'asc')
            order('serialNo', 'asc')
            order('decalNo', 'asc')

        }
        StringBuffer assetString = new StringBuffer()
        if (assets.size() == 0)
            assetString.append("No Results Found")
        else {
            assets.each {
                assetString.append("<a href=\'../physicalServer/show?id=${it.id}\'>${it.toString()}</a><br>")
            }
            if (assets.size() == maxRows)
                assetString.append("More than ${maxRows} found......<br>")
        }


        def apps = Application.createCriteria().list(max: maxRows) {
            ilike('applicationTitle', "%${params.searchString}%")
            order('applicationTitle', 'asc')
        }
        StringBuffer appString = new StringBuffer()
        if (apps.size() == 0)
            appString.append("No Results Found")
        else {
            apps.each {
                appString.append("<a href=\'../application/show?id=${it.id}\'>${it.toString()}</a><br>")
            }
            if (apps.size() == maxRows)
                appString.append("More than ${maxRows} found......<br>")
        }


        def services = Service.createCriteria().list(max: maxRows) {
            ilike('serviceTitle', "%${params.searchString}%")
            order('serviceTitle', 'asc')
        }
        StringBuffer serviceString = new StringBuffer()
        if (services.size() == 0)
            serviceString.append("No Results Found")
        else {
            services.each {
                serviceString.append("<a href=\'../service/show?id=${it.id}\'>${it.toString()}</a><br>")
            }
            if (services.size() == maxRows)
                serviceString.append("More than ${maxRows} found......<br>")
        }

        def staff = Person.createCriteria().list(max: maxRows) {
            ilike('uhName', "%${params.searchString}%")
            order('uhName', 'asc')
        }
        StringBuffer staffString = new StringBuffer()
        if (staff.size() == 0)
            staffString.append("No Results Found")
        else {
            staff.each {
                staffString.append("<a href=\'../person/show?id=${it.id}\'>${it.toString()}</a><br>")
            }
            if (staff.size() == maxRows)
                staffString.append("More than ${maxRows} found......<br>")
        }


        def jsonData = [retVal: true, hostList:hostString.toString(), assetList:assetString.toString(), appList:appString.toString(), serviceList:serviceString.toString(), staffList:staffString.toString()]
        render jsonData as JSON
    }

    def staffChangeReport = {
        def report = personService.staffChangeReport()
        render report as JSON
    }

    def roleChangeReport = {
        def report = personService.roleChangeReport()
        render report as JSON
    }

}
