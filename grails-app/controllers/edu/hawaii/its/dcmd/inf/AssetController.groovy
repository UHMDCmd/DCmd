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
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil
import edu.hawaii.its.dcmd.inf.Asset
import edu.hawaii.its.dcmd.inf.AssetType
import edu.hawaii.its.dcmd.inf.PhysicalServer
import edu.hawaii.its.dcmd.inf.Rack
import edu.hawaii.its.dcmd.inf.Cluster
import edu.hawaii.its.dcmd.inf.Replacement
import edu.hawaii.its.dcmd.inf.SupportRole
import edu.hawaii.its.dcmd.inf.Person
import edu.hawaii.its.dcmd.inf.Host
import edu.hawaii.its.dcmd.inf.Application
import grails.gorm.DetachedCriteria
import org.codehaus.groovy.grails.commons.ConfigurationHolder

import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.context.request.RequestContextHolder


class AssetController {

    def scaffold = Asset

	def assetService, replacementService, generalService, breadCrumbService
    def sessionFactory


/*******************************
 /*   Clone Module
 *******************************/
     def clone = {
         println("this is the orginal id: " + params.id)
         Asset currentAsset = Asset.get(params.id)

         println("Current Asset: " + currentAsset.itsId)
         Asset cloneInstance = new Asset()

         if (currentAsset.assetType.toString() == 'Physical Server'){
             cloneInstance = new PhysicalServer()
             cloneInstance.properties = currentAsset.properties

             //disable duplication of these attributes

             cloneInstance.supporters = null
             cloneInstance.supportStaff = null
             cloneInstance.replacementSet = null
             cloneInstance.assetCapacities = null
             cloneInstance.hosts = null
             cloneInstance.RUplacement = null
             cloneInstance.RUplanning = null

         }
         if (currentAsset.assetType.toString()== 'Rack'){
             cloneInstance = new Rack()

             //itsId is temporarily passed, edit page clears field if cloned session var is true.
             cloneInstance.properties = currentAsset.properties

             cloneInstance.assetType.abbreviation = "Rack";
             //disable duplication of these attributes
             cloneInstance.RUplacement = null
             cloneInstance.RUplanning = null
             cloneInstance.supportStaff = null
             cloneInstance.replacementSet = null
             cloneInstance.supporters = null

             //create clean attribute without duplicate references
             List<RackUnit> RUs= new ArrayList();
              cloneInstance.RUs = RUs;

         }


         cloneInstance.save(failOnError: true,flush: true)

         if (cloneInstance){
             println("clone name: " + cloneInstance.itsId)
             println("clone id : " + cloneInstance.id)
            }

         boolean cloned = true;
         session.setAttribute("cloned",cloned)

        redirect (url:"/asset/edit?id=${cloneInstance.id}")

     }

    //clears session variables -- called in list and update
    //This is not in a service class because session vars cannot be accessed in services
    void resetCloneVar() {
        if(session.valueNames.contains("cloned")){
        session.removeAttribute("cloned")
            println("reset clone var...")
        }
    }
 /***********************/

  def save = {
		//cleanse cloned params
		assetService.cleanseCloneParams(params)
		assetService.logParams(params)
        String saveOption = params.option //selects type of save to do

        if(params.statusSelect) {
            params.status = generalService.createOrSelectStatus(params)
        }
        if (params.manufacturerSelect) {
            params.manufacturer = generalService.createOrSelectManufacturer(params)
        }
        if (params.cluster) {
            if(params.cluster == 0)
                params.cluster = null
            else
                params.cluster = Cluster.get(params.cluster)
        }

        def assetInstance

        if (params.assetType)
            params.assetType = AssetType.get(params.assetType.id)
		//create new instance from params and remove any notes flagged as deleted
        if(params.assetType.toString() == 'Physical Server') {
            assetInstance = new PhysicalServer(params)
        }
        else if(params.assetType.toString() == 'Rack') {
            params.ruCap = 45
            assetInstance = new Rack(params)
            assetInstance.Initialize()
        }
        else {
		    assetInstance = new Asset(params)
        }
//		assetService.removeDeletedNotes(assetInstance)

		//save and redirect or re-edit the new asset
		if (!assetInstance.hasErrors() && assetInstance.save(flush: true)) {

            if(params.personSelect) {
                def primarySA = generalService.createSupportRole(assetInstance, Person.get(params.personSelect), 'Technical', RoleType.findByRoleName("Primary SA")?.id)
            }

            //save options
            if (saveOption.equals("saveEdit")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'asset.label', default: 'Asset'), assetInstance.id])}"
                redirect(url: "/asset/edit?id=${assetInstance.id}")
            }
            else if (saveOption.equals("saveList")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'asset.label', default: 'Asset'), assetInstance.id])}"
                redirect(url: "/asset/list")
            }
            else if (saveOption.equals("saveCreate")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'asset.label', default: 'Asset'), assetInstance.id])}"
                redirect(url: "/asset/create")
            }
            else{
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'asset.label', default: 'Asset'), assetInstance.id])}"
               redirect(url: "/asset/show?id=${assetInstance.id}")
               //    redirect(action:"/asset/show")
              //  forward action:  "altShow", params: [showUrl:"/asset/show?id=${assetInstance.id}"]

            }
			/*flash.message = "${message(code: 'default.created.message', args: [message(code: 'asset.label', default: 'Asset'), assetInstance.id])}"
            redirect(url: "/asset/show?id=${assetInstance.id}")*/
		} else {
			render(view: "create", model: [assetInstance: assetInstance])
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
        //clean up the params
		assetService.cleanseCloneParams(params)
		assetService.logParams(params)

        def assetInstance = Asset.get(params.id)
//        resetCloneVar()

        System.out.println(assetInstance.itsId)
        if(params.statusSelect) {
            params.status = generalService.createOrSelectStatus(params)
        }
        if (params.manufacturerSelect) {
            params.manufacturer = generalService.createOrSelectManufacturer(params)
        }
        if (params.cluster) {
            if(params.cluster == 0)
                params.cluster = null
            else
                params.cluster = Cluster.get(params.cluster)
        }
        if (params.globalZone && params.globalZone != 0) {
            def currentGlobalZone = assetInstance.getGlobalZone()
            if(currentGlobalZone != null) {
                currentGlobalZone.asset = null
                currentGlobalZone.save()
            }
            def globalZone = Host.get(params.globalZone)
            globalZone.asset = assetInstance
            globalZone.type = params.serverType
            System.out.println(globalZone.toString())
            System.out.println(globalZone.type)
            globalZone.save()
            params.globalZone = globalZone
        }
        else
            params.globalZone = null

        if(params.serverType != 'VMWare') {
            params.cluster = null
        }

//        System.out.println(params)


        if (assetInstance) {
			//update the object with the params
            if(assetInstance.assetType.toString() == 'Physical Server') {
                if(Integer.parseInt(params.currentRack) != 0) {
                    if(params.RU_begin)
                        params.RU_begin = Integer.parseInt(params.RU_begin) + Integer.parseInt(params.RU_size)-1
                }
                else
                    params.RU_begin = 0
                /*
                if(Integer.parseInt(params.plannedRack) != 0) {
                    if(params.RU_planned_begin)
                        params.RU_planned_begin = Integer.parseInt(params.RU_planned_begin)+Integer.parseInt(params.RU_size)-1
                }
                else
                    params.RU_planned=0
                    */

                if (params.RU_size)
                    params.RU_size = Integer.parseInt(params.RU_size)
            }

			assetInstance.properties = params

            if(assetInstance.assetType.toString() == 'Physical Server') {
                if(params.RU_size == "")
                    params.RU_size = 0
                if(params.RU_size < 0) {
                    assetInstance.errors.rejectValue('RU_size', 'RU Size must be a positive number.')
                    GrailsHibernateUtil.setObjectToReadyOnly(assetInstance, sessionFactory)
                }
                if(Integer.parseInt(params.currentRack) == 0) {
                    System.out.println(params)

                    params.assetId = assetInstance.id
                    assetService.removeAsset(params)
                }
                else if(params.currentRack != assetInstance.getRackAssignmentId() || params.RU_begin != assetInstance.RU_begin) {

                    if((params.RU_begin-params.RU_size+1) < 1 || params.RU_begin > 45) {
                        assetInstance.errors.rejectValue('RU_begin', 'All RU Positions must be between 1 and 45.')
                        GrailsHibernateUtil.setObjectToReadyOnly(assetInstance, sessionFactory)
                    }
                    else {
                        params.assetId = assetInstance.id
                        assetService.removeAsset(params)
                        params.id = params.currentRack
                        params.asset_Id = assetInstance.id
                        params.addPosition = params.RU_begin
                        if (assetService.addAssetToRack(params)==-2) {
                            assetInstance.errors.rejectValue('RU_begin', 'Current Rack position already currently occupied.')
                            GrailsHibernateUtil.setObjectToReadyOnly(assetInstance, sessionFactory)
                        }
                    }
                }
                      /*
                if(Integer.parseInt(params.plannedRack) == 0) {
                    params.assetId = assetInstance.id
                    assetService.removePlannedAsset(params)
                }
                else if(params.plannedRack != assetInstance.getPlannedRackAssignmentId() || params.RU_planned_begin != assetInstance.RU_planned_begin) {

                    if((params.RU_planned_begin-params.RU_size+1) < 1 || params.RU_planned_begin > 45) {
                        assetInstance.errors.rejectValue('RU_planned_begin', 'All RU Positions must be between 1 and 45')
                        GrailsHibernateUtil.setObjectToReadyOnly(assetInstance, sessionFactory)
                    }
                    else {
                        if(!assetInstance.hasErrors()) {
                            params.assetId = assetInstance.id
                            assetService.removePlannedAsset(params)
                            params.id = params.plannedRack
                            params.asset_Id = assetInstance.id
                            params.addPosition = params.RU_planned_begin
                            if (assetService.addPlannedAssetToRack(params)==-2) {
                                assetInstance.errors.rejectValue('RU_begin', 'Planned Rack position is already planned to be occupied.')
                                GrailsHibernateUtil.setObjectToReadyOnly(assetInstance, sessionFactory)
                            }
                        }
//
                    }

                }
                */
            }

			//save and redirect
			if (!assetInstance.hasErrors() && assetInstance.save(flush: true)) {
                System.out.println(assetInstance.errors.errorCount)
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'asset.label', default: 'Asset'), assetInstance.id])}"
				redirect(url: "/asset/show?id=${assetInstance.id}")
              //  forward action:  "show", params: [showUrl:"/asset/show?id=${assetInstance.id}"]
			} else {
				render(view: "edit", model: [assetInstance: assetInstance])
			}

		//asset not found; no edits can be performed; show the list
		} else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'asset.label', default: 'Asset'), params.id])}"
			redirect(action: "list")
		}

	}

    def delete() {
        def assetInstance = Asset.get(params.id)
        if (assetInstance.assetType.toString() == 'Physical Server') {
            assetInstance.hosts.each { theHost ->
                theHost.asset = null
                theHost.save()
            }
            params.assetId = params.id
            assetService.removeAsset(params)
            assetService.removePlannedAsset(params)
        }
        if(!assetInstance.hasErrors() && assetInstance.delete()) {
            redirect(action: "list")
        }
        else {
            redirect(url: "/asset/show?id=${assetInstance.id}")
        }
    }

    def storage() {

    }

    /*****************************************************************/
    /* ELOG
    /*****************************************************************/


    def elog = {

        def currentAsset = Asset.get(params.id)
        StringBuffer elogMessage = new StringBuffer()

        if(currentAsset.assetType.toString() != "Physical Server"){
            def response = [message: "Asset is not a Physical Server, no eLog available.", state: "OK", id: 1]
            render response as JSON
        }

        elogMessage.append("<center>eLog report for PhysicalServer " + currentAsset.itsId + "</center>")
        elogMessage.append("-------------------------------------------------------------------------------------------<br><br>")

        elogMessage.append(assetService.elogReportHosts(currentAsset))


        def response = [message: elogMessage.toString(), state: "OK", id: 1]
        render response as JSON
    }


    /*****************************************************************/
    /* Utility Functions
    /*****************************************************************/
    /*
    void removeAssetFromRack(Asset myAsset) {
        if(!myAsset.RUplacement.empty) {
            myAsset.RUplacement.each { RUitem ->
                RUitem.filledBy = null
                RUitem.label = 'Open'
                RUitem.RUstatus = 'Open'
                RUitem.save(failOnError: true, flush: true)
            }
        //    myAsset.RUplacement.clear()
        //    myAsset.save(failOnError: true)
        }
    }

    void removePlannedAssetFromRack(Asset myAsset) {
        if(!myAsset.RUplanning.empty) {
            myAsset.RUplanning.each { RUitem ->
                RUitem.planFill = null
                RUitem.label = 'Open'
                RUitem.RUstatus = 'Open'
                RUitem.save(failOnError: true, flush: true)
            }
            //    myAsset.RUplacement.clear()
            //    myAsset.save(failOnError: true)
        }
    }

    Boolean addAssetToRack(Asset myAsset, Rack onRack) {
        def collision = false
        def currentRU

        if (!myAsset.RUplacement.empty) {
            removeAssetFromRack(myAsset)
        }
        for (def i=0; i<myAsset.RU_size; i++) {
            currentRU = onRack.RUs.get(myAsset.RU_begin+i-1)
            if (currentRU.RUstatus != 'Open') {
                collision = true
            }
            else {
                currentRU.label = "<a href='/dcmd/asset/show?id=${myAsset.id}'>${myAsset.itsId}</a>"
                currentRU.RUstatus = 'Filled'
                currentRU.filledBy = myAsset
                myAsset.RUplacement.add(currentRU)
            }
        }
        if (collision) {
            return false
        }
        else{
            onRack.save(failOnError:true, flush: false)
            myAsset.save(failOnError: true, flush:false)
            return true
            //System.out.println("assets in rack" + rackInstance.RUs.toString())
        }
    }

         */

    /*****************************************************************/
    /* LISTING FUNCTIONS
    /*****************************************************************/

    //action to clear out the session variables
    def list = {
        //BreadCrumb session variable reset
       //resetBreadCrumbs()
        //for safety, clear out clone variable
        resetCloneVar()
    }

    def listAsSelect={

        def lst = Asset.findAll()

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


    def listJSON = {
		log.debug "controller params: ${params}"
		render assetService.listAssets(params) as JSON
	}

    def listServerTypesAsSelect={
        StringBuffer buf = new StringBuffer("<select>")
        buf.append("<option value=\'Solaris Global Zone\'>Solaris Global Zone</option>")
        buf.append("<option value=\'VMWare\'>VMWare</option>")
        buf.append("<option value=\'VMware Standalone\'>VMware Standalone</option>")
        buf.append("<option value=\'Standalone\'>Standalone</option>")

        buf.append("</select>")

        render buf.toString()
    }


    /*****************************************************************/
    /* Main Assets Grid
    /*****************************************************************/
    def listAllAsset = {
        params.stored = false
        def jsonData = assetService.listAllAsset(params)
        render jsonData as JSON
    }

    def listAllStoredAsset = {
        params.stored = true
        def jsonData = assetService.listAllAsset(params)
        System.out.println(jsonData)
        render jsonData as JSON
    }

    /*****************************************************************/
    /* Replacements Grid
    /*****************************************************************/

    def listReplacements = {
        def jsonData = assetService.listReplacements(params)
        render jsonData as JSON
    }

    def editReplacements = {
        def jsonData = assetService.editReplacements(params)
        render jsonData as JSON
    }


    /*****************************************************************/
    /* General methods for notes used by nearly all show and edit pages!
    /*****************************************************************/

    def saveNote = {
        System.out.println(params)
        def objectInstance
        def response
        if (params.pageType == 'asset') {
            objectInstance = Asset.get(params.id)
        }
        else if (params.pageType == 'host') {
            objectInstance = Host.get(params.id)
        }
        else if (params.pageType == 'application') {
            objectInstance = Application.get(params.id)
        }
        else if (params.pageType == 'person') {
            objectInstance = Person.get(params.id)
        }
        else if (params.pageType == 'location') {
            objectInstance = Location.get(params.id)
        }
        else if (params.pageType == 'service') {
            objectInstance = Service.get(params.id)
        }
        else if (params.pageType == 'tier') {
            objectInstance = Tier.get(params.id)
        }
        else if (params.pageType == 'cluster') {
            objectInstance = Cluster.get(params.id)
        }



        objectInstance.setProperty(params.noteType, params.noteData)

        if (objectInstance.save(failOnError: true, flush:  true)) {

            response = [message: "Note Saved.", status: 'OK', id: params.id]
        }
        System.out.println(response)
        render response as JSON
    }


    /*****************************************************************/
    /* VALIDATION FUNCTIONS
    /*****************************************************************/

    def validateCapExpand = {
//        System.out.println("currentCapacity: " + params.currentCapacity + ", asset: " + params.assetId + " rowId: " + params.rowId)
        if (!params.maxExpandableCapacity)
            params.maxExpandableCapacity = -1
        else if (!params.currentCapacity)
            params.currentCapacity = -1

        def retVal = assetService.checkCapExpand(Asset.get(params.assetId.toLong()), params.currentCapacity.toFloat(), params.maxExpandableCapacity, params.rowId.toLong())
        def response = [retVal: retVal]
        //    def response = [message: "Test Message", state: "Error", id: params.assetId, retVal: retVal]
        render response as JSON
    }


}