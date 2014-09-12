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
import org.apache.jasper.tagplugins.jstl.core.ForEach
import edu.hawaii.its.dcmd.inf.Rack
import edu.hawaii.its.dcmd.inf.Cluster
import edu.hawaii.its.dcmd.inf.Replacement
import edu.hawaii.its.dcmd.inf.SupportRole
import edu.hawaii.its.dcmd.inf.Person
import edu.hawaii.its.dcmd.inf.Asset
import edu.hawaii.its.dcmd.inf.RackUnit

class RackController {

    def scaffold = Rack

    def assetService
    def generalService

    /*****************************************************************/
    /* LISTING FUNCTIONS
    /*****************************************************************/
    def listAsSelect={

        def lst = Rack.findAll()

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

    def listRacksAsSelect2 = {

        def response = [retVal: true, result: []]
        response.result.add([id:0, text:'Not Assigned'])
        def lst = Rack.findAll()

        lst.each {
            def item = [id:it.id, text:it.toString()]
            response.result.add(item);
        }
        render response as JSON
    }

    def listJSON = {
        log.debug "controller params: ${params}"
        render assetService.listRacks(params) as JSON
    }

    /*****************************************************************/
    /* Main Racks Grid
    /*****************************************************************/
    def listAllRack = {
        def jsonData = assetService.listAllRack(params)
        render jsonData as JSON
    }


    /*****************************************************************/
    /* Rack Visualization Methods/Attributes
    /*****************************************************************/
    def addAssetToRack = {
        def response = [retVal: true, data: assetService.addAssetToRack(params)]
        render response as JSON
    }

    def addPlannedAssetToRack = {
        def response = [retVal: true, data: assetService.addPlannedAssetToRack(params)]
        render response as JSON
    }

    def removePlannedAsset = {
        assetService.removePlannedAsset(params)
        def response = [retVal: true]
        render response as JSON
    }

    def reserveSelected = {
        log.debug "params: ${params.inspect()}"
        def rackInstance = Rack.get(params.id)
        def collision = false
        def selectedList = params.selected.split()
        selectedList.each{indexVal->
            if (indexVal.toInteger() > 0) {
                if (rackInstance.RUs.get(indexVal.toInteger()-1).RUstatus != 'Open') {
                    collision = true
                }
                else{
                rackInstance.RUs.get(indexVal.toInteger()-1).RUstatus = 'Reserved'
                rackInstance.RUs.get(indexVal.toInteger()-1).label = 'Reserved'
                }
            }
        }
      //  if (!collision) {
        //    rackInstance.save(failOnError:true, flush: false)

            def response = [retVal: !collision, data: rackInstance.RUs]
            render response as JSON
    //    }

    }


    def openSelected = {
        log.debug "params: ${params.inspect()}"
        def rackInstance = Rack.get(params.id)
        def selectedList = params.selected.split()

        selectedList.each{indexVal->
            if (indexVal.toInteger() <46) {
                /*
                def currentRU = rackInstance.RUs.get(indexVal.toInteger()-1)
                if (currentRU.RUstatus == 'Filled' || currentRU.RUstatus == 'Both') {
                    def removeAsset = currentRU.filledBy
                    removeAsset.RUplacement.each {clearRU ->
                        if(clearRU.RUstatus == 'Both') {
                            clearRU.RUstatus = 'Planned'
                            clearRU.label = "<a href='/its/dcmd/asset/show?id=${clearRU.planFill?.id}'>${clearRU.planFill?.itsId}</a>"
                            if(! clearRU.hasErrors() && clearRU.save()) {
                            }
                            else {
                                def message = clearRU.errors.allErrors.get(0).toString()
                                System.out.println(message)
                            }
                        }
                        else {
                            clearRU.RUstatus = 'Open'
                            clearRU.label = 'Open'
                        }
                        clearRU.filledBy = null
                    }
                    removeAsset.RU_begin = null
                    if (!removeAsset.hasErrors() && removeAsset.save()) {}
                    else {
                        def message = removeAsset.errors.allErrors.get(0).toString()
                        System.out.println(message)
                    }
                }
                */
                params.assetId = rackInstance.RUs.get(indexVal.toInteger()-1)?.filledBy?.id
                if(params.assetId != null)
                    assetService.removeAsset(params)\

                params.assetId = rackInstance.RUs.get(indexVal.toInteger()-1)?.planFill?.id
                if(params.assetId != null)
                    assetService.removePlannedAsset(params)

            }
        }
            /*
        selectedList.each { indexVal ->
            if (indexVal.toInteger() <46) {
                def currentRU = rackInstance.RUs.get(indexVal.toInteger()-1)
                if (currentRU.RUstatus == 'Planned') {
                    def removePlannedAsset = currentRU.planFill
                    removePlannedAsset.RUplanning.each {clearRU ->
                        if(clearRU.RUstatus == 'Both') {
                            clearRU.RUstatus = 'Filled'
                            clearRU.label = "<a href='/its/dcmd/asset/show?id=${clearRU.filledBy?.id}'>${clearRU.filledBy?.itsId}</a>"
                            if(! clearRU.hasErrors() && clearRU.save()) {
                            }
                            else {
                                def message = clearRU.errors.allErrors.get(0).toString()
                                System.out.println(message)
                            }
                        }
                        else {
                            clearRU.RUstatus = 'Open'
                            clearRU.label = 'Open'
                        }
                        clearRU.planFill = null
                    }
                    removePlannedAsset.RU_planned_begin = null
                    removePlannedAsset.save(failOnError:  true, flush: false)
                }
            }
        }
        */

        rackInstance.save(failOnError:true, flush: false)
        def response = [retVal: true, data: rackInstance.RUs]

        render response as JSON
    }

    def numPositions = {

        log.debug "params: ${params.inspect()}"
        def response
        def theAsset = Asset.get(params.asset_Id)
        if (theAsset) {
            def currentRack = "<a href='../asset/show?id=${theAsset.getRackAssignmentId()}'>${theAsset.getRackAssignment()}</a>"
            def plannedRack = "<a href='../asset/show?id=${theAsset.getPlannedRackAssignmentId()}'>${theAsset.getPlannedRackAssignment()}</a>"

            response = [retVal: true, positions: theAsset.RU_size, currentRack: currentRack, plannedRack:plannedRack]
        }
        else {
            response = [retVal:  true, positions: ' ', currentRack: ' ', plannedRack: ' ']
        }
        render response as JSON

    }

    def getDeviceDetails ={
        def deviceName = params.deviceName
        def theAsset = Asset.findByItsId(deviceName)
        PhysicalServer device = theAsset
        def jsonData

        if (theAsset.assetType == 'Physical Server'){


        println("retrieved data: ")
            println("itsid: " + device.itsId)
            println("itstype: " + device.assetType)
                 }

        jsonData = [device.itsId,device.assetType.toString()]

        render jsonData as JSON
    }

}

