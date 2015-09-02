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

import com.unboundid.ldap.sdk.LDAPConnection
import com.unboundid.ldap.sdk.SearchResult
import com.unboundid.ldap.sdk.SearchScope
import com.unboundid.ldap.sdk.SearchResultEntry
import com.unboundid.ldap.sdk.LDAPSearchException
import grails.converters.JSON

class PersonService {

    def generalService
    static transactional = true


    /*****************************************************************/
    /* Support Staff Grids
    /*****************************************************************/
    def listTechnicalSupport(params) {
        def sortIndex = params.sidx ?: 'roleName'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentObject = SupportableObject.get(params.objectId.toLong())

        def supportStaff = SupportRole.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("supportedObject.id", currentObject.id)
            like("roleType", "Technical")
        }
        def totalRows = supportStaff.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = supportStaff?.collect { [ cell: ['', it.roleName.toString(),
            "<a href='../person/show?id=${it.person.id}'>${it.person.toString()}</a>",
                it.person.primaryEmail(), it.person.primaryPhone(), it.supportRoleNotes], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editTechnicalSupport(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id

        if (params.person) params.person = Person.get(params.person)
        params.supportedObject = SupportableObject.get(params.objectId.toLong())
        params.roleType = "Technical"

        // determine our action
        switch (params.oper) {
            case 'add':
                // add instruction sent
                if(params.roleNameSelect)
                    params.roleName = generalService.createOrSelectRoleType(params)

                item = generalService.createSupportRole(params.supportedObject, params.person, params.roleType, params.roleName.id)

                break;
            case 'edit':
                if(params.roleNameSelect)
                    params.roleName = generalService.createOrSelectRoleType(params)

                item = SupportRole.get(params.id)
                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    message = "Support Role  ${item.toString()} Updated for ${params.supportedObject}"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = SupportRole.get(params.id)
//
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

    def listFunctionalSupport(params) {
        def sortIndex = params.sidx ?: 'roleName'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows
        def currentObject = SupportableObject.get(params.objectId.toLong())

        def supportStaff = SupportRole.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("supportedObject.id", currentObject.id)
            like("roleType", "Functional")
        }
        def totalRows = supportStaff.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = supportStaff?.collect { [ cell: ['', it.roleName.toString(),
                "<a href='../person/show?id=${it.person.id}'>${it.person.toString()}</a>",
                it.person.primaryEmail(), it.person.primaryPhone(), it.supportRoleNotes], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editFunctionalSupport(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id

        if (params.person) params.person = Person.get(params.person)
        params.supportedObject = SupportableObject.get(params.objectId.toLong())
        params.roleType = "Functional"

        // determine our action
        switch (params.oper) {
            case 'add':
                // add instruction sent
                if(params.roleNameSelect)
                    params.roleName = generalService.createOrSelectRoleType(params)

                item = generalService.createSupportRole(params.supportedObject, params.person, params.roleType, params.roleName.id)

                break;
            case 'edit':
                if(params.roleNameSelect)
                    params.roleName = generalService.createOrSelectRoleType(params)
                System.out.println(params)

                item = SupportRole.get(params.id)
                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    message = "Support Role  ${item.toString()} Updated for ${params.supportedObject}"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = SupportRole.get(params.id)
//
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



    def listPersonAsSelect() {
        def lst = Person.findAll()

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }


    def getAdmin(Application myApp) {
        if(myApp == null)
            return new Person(uhName: "")
        def item = SupportRole.createCriteria().list() {
            supportedObject {
                like("supportableType", 'application')
            }
            like("supportedObject.id", myApp.id)
            roleName {
                like("roleName", 'Primary SA')
            }
        }
        if (!item.empty)
            return item.first().person
        else
            return new Person(uhName: "")
    }

    def getAdmin(Service myService) {
        if(myService == null)
            return new Person(uhName: "")
        def item = SupportRole.createCriteria().list() {
            supportedObject {
                like("supportableType", 'service')
            }
            like("supportedObject.id", myService.id)
            roleName{
                like("roleName", 'Primary SA')
            }
        }
        if (!item.empty)
            return item.first().person
        else
            return new Person(uhName: "")
    }


    def getAdmin(Host myHost) {
        def primarySA = new Person(uhName: "")
        myHost.supporters.each {
            if(it.roleName.roleName == 'Primary SA')
                primarySA = it.person
        }
        return primarySA
    }


    def getAdminLink(Host myHost) {
        def primarySA = ""
        myHost.supporters.each {
            if(it.roleName.roleName == 'Primary SA')
                primarySA = "<a href='../person/show?id=${it?.person?.id}'>${it?.person?.uhName}</a>"
        }
        return primarySA
    }

    def getAdmin(Asset myAsset) {
        if(myAsset == null)
            return new Person(uhName: "")
        def item = SupportRole.createCriteria().list() {
            supportedObject {
                like("supportableType", 'asset')
            }
            like("supportedObject.id", myAsset.id)
            roleName {
                like("roleName", 'Primary SA')
            }
        }
        if (!item.empty)
            return item.first().person
        else
            return new Person(uhName: "")
    }

    def getSupportPerson(SupportableObject myObj, String theRole) {
        def thePerson = new Person(uhName: "")
        myObj.supporters.each {
            if(it.roleName.roleName == theRole)
                thePerson = it.person
        }
        return thePerson
    }

    def getSupportPersonLink(SupportableObject myObj, String theRole) {
        def theLink = ""
        myObj.supporters.each {
            if(it.roleName.roleName == theRole)
                theLink = "<a href='../person/show?id=${it.person?.id}'>${it.person.toString()}</a>"
        }
        return theLink
    }

    def searchJSON(String searchTerm, Integer maxRows) {
		
		log.debug "search term: ${searchTerm}"
		log.debug "max rows: ${maxRows}"
		
		//build the criteria
		def crit = Person.createCriteria()
		def people = crit.list(){
			or{
				like("firstName", "%${searchTerm}%")
				like("lastName", "%${searchTerm}%")
				like("uhNumber", "%${searchTerm}%")
			}
			maxResults(maxRows)
		}
		
		log.debug "people: ${people.inspect()}"
		
		//build the return array
		def returned = []
		people.each{
			returned << [id:it.id, name:it.toString()]
		}
		
		log.debug "returned: ${returned.inspect()}"
		returned
    }

    /**
     * personLDAP: is a def created to create ldap connections to UHM ldap test server ldap1.its.hawaii.edu
     * Connection is made anonymously with no authentication -- using Java Unboundid SDK
     * Search is made by UHusername, LDAP returns the information of the user, and a new Person instance is created to replace the old
     *
     * Links:https://www.unboundid.com/products/ldap-sdk/
     */
    def personLDAP(String uhName){

        //String name = params.uhName
        String name = uhName
        String nameFilter = "(uid="+ name +")"
//        println("filter:" + nameFilter)
        List<String> resultList = new ArrayList<String>() //list of all retrieved items
        List<String> creds = new ArrayList<String>() //list of desired credentials
        def personInstance = null

        LDAPConnection c = new LDAPConnection("ldap1.its.hawaii.edu", 389);

        try{

            //println("connected: " + c.connected)
            //println("port: " + c.connectedPort)
            //println("c time: " + c.connectTime)
            //println("addr: " + c.connectedAddress)
            //println("root dse: " + c.rootDSE)

            SearchResult searchResult = c.search("dc=hawaii,dc=edu",
                    SearchScope.SUB, nameFilter);

            //should only return 1 since uh username is unique
            //System.out.println(searchResult.getEntryCount() + " entries returned.");


            if (searchResult.entryCount > 0){

                SearchResultEntry entry = searchResult.getSearchEntries().get(0)
                String r = entry.toLDIFString()

                resultList = new ArrayList<String>(Arrays.asList(r.split("\n")));
                Map<String, String> attrMap = new HashMap<String, String>();

                //println("printing results arraylist, creating hash map")
                for (int i = 0 ; i < resultList.size(); i++){
                    //println(i + ": " + resultList.get(i))

                    String [] keyValue  = resultList.get(i).split(':')
                    attrMap.put(keyValue[0],keyValue[1])
                    keyValue = null
                }

                String id = attrMap.get("dn").substring(8,16)
                //println("\nuhid: " + id)

                String title = attrMap.get("title")
                //println("title: " + title)

                String firstName = attrMap.get("givenName")
                //println("firstname: " + firstName)

                String lastName = attrMap.get("sn")
                //println("lastname: " + lastName)

                //contact infos
                String phone = attrMap.get("telephoneNumber")
                //println("phone: " + phone)

               // String email = attrMap.get("uhpreferredmail")
                String email = attrMap.get("mail")
                //println("email: " + email)

                personInstance = new Person(uhName: name,title: title,firstName: firstName, telephone: phone,primaryEmail: email,
                lastName: lastName)
            }

        }

           catch (LDAPSearchException e){
            println("The search failed.");
            println("diagnostic: " + e.diagnosticMessage)
            println("cause: " + e.cause)
            println("cause: " + e.exceptionMessage)
        }

        c.close() //close ldap connection
        //println("is connected: " + c.connected)

        return personInstance
    }

}