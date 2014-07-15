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
import org.hibernate.Criteria

class PowerController {

    def powerService
    def generalService

    def index() { }

    def list() {
        if(!params.id)
            params.id = 0

        render(view: "list", model: [assetId: params.id])
    }
    def show() {}

    def saveStripAttributes = {
        params.rack = Rack.get(params.rack)
        params.type = PowerStripType.get(params.type)
        def item = PowerStrip.get(params.id)
        item.properties = params

        def response
        if (! item.hasErrors() && item.save()) {
            response = [state:"OK", id: item.id]
        }
        else
            response = [state: "failed"]
        render response as JSON
    }

    def listDevices = {
        def jsonData = powerService.listDevices(params)
        render jsonData as JSON
    }

    def editDevices = {
        def jsonData = powerService.editDevices(params)
        render jsonData as JSON
    }

    def unplugDevice = {
        System.out.println(params)
        def thePlug = DevicePlug.get(params.plugId)
        thePlug.delete()
        def response = [state:"OK", id: 0]
        render response as JSON
    }

    def createStripType = {
        System.out.println(params)

        def item = new PowerStripType(name: params.typeName)
        item.connectors = []
        def theConnector

        // FIGURE OUT WHY NOT PRINTING! GET IT TO CREATE POWERSTRIPTYPE CORRECTLY...
        System.out.println(Integer.parseInt(params.numConnectors))

        for(int i=1; i<=Integer.parseInt(params.numConnectors); i++) {
            System.out.println(params.'connectorTypes[]'[i])
            System.out.println(params.'connectorQtys[]'[i])
            theConnector = PowerConnector.get(Long.parseLong(params.'connectorTypes[]'[i]))
            for (int j=1; j<=Integer.parseInt(params.'connectorQtys[]'[i]); j++) {
                System.out.println("Adding")
                item.connectors.add(theConnector)
            }
        }
        if(!item.hasErrors() && item.save(flush: true)){
            System.out.println("SAVED!")
        }
        else
            System.out.println(item.errors.fieldError)

       // if(! item.hasErrors() && item.save()) {
            def response = [message:"",state:"OK",id:item.id,text:item.name]
       // }
       // else
       //     def response = [state: "ERROR"]

        System.out.println(item.connectors)
        render response as JSON
    }

    /******************************************************************************************************
     *   Methods for manipulation of visualization tree.
     *                   Functions to load and edit the tree.
     ******************************************************************************************************/
    def getFullTree() {

        def sourceElems = PowerSource.count()
        def panelElems = PowerPanel.count()
        def breakerElems = PowerBreaker.count()
        def stripElems = PowerStrip.count()
        def totalDraw = PowerStrip.createCriteria().get() {
            projections {
                sum('powerUsed')
            }
        }
        def sourceCap = PowerSource.createCriteria().get() {
            projections {
                sum('capacity')
            }
        }
        def panelCap = PowerPanel.createCriteria().get() {
            projections {
                sum('capacity')
            }
        }
        def breakerCap = PowerBreaker.createCriteria().get() {
            projections {
                sum('capacity')
            }
        }
        def stripCap = PowerStrip.createCriteria().get() {
            projections {
                sum('capacity')
            }
        }
        def sourceUtil=0;
        def panelUtil=0;
        def breakerUtil=0;
        def stripUtil=0;
        if(totalDraw != null) {
            sourceUtil = ((totalDraw/sourceCap)*100)
            panelUtil = ((totalDraw/panelCap)*100)
            breakerUtil = ((totalDraw/breakerCap)*100)
            stripUtil = ((totalDraw/stripCap)*100)
        }

        def treeData = [id: 0, name: 'All', data:[$color:"#0000ff", $height:10, type:'root', capacity: 'N/A', draw: 'N/A',
                totalDraw:totalDraw, avgUtil:0, totalElements:sourceElems+panelElems+breakerElems+stripElems,
                sourceCap:sourceCap, sourceUtil:sourceUtil.setScale(2, BigDecimal.ROUND_HALF_UP), sourceElements:sourceElems,
                panelCap:panelCap, panelUtil:panelUtil.setScale(2, BigDecimal.ROUND_HALF_UP), panelElements:panelElems,
                breakerCap:breakerCap, breakerUtil:breakerUtil.setScale(2, BigDecimal.ROUND_HALF_UP), breakerElements:breakerElems,
                stripCap:stripCap, stripUtil:stripUtil.setScale(2, BigDecimal.ROUND_HALF_UP), stripElements:stripElems],
                children: powerService.getSources()]


        System.out.println(treeData)
        render treeData as JSON
    }

    def createChild = {
        System.out.println(params)

        switch(params.type) {
            case 'root':
                for (int i=1; i<=Integer.valueOf(params.numItems); i++) {
                    def newPSU = new PowerSource(itsId: params.'name[]'[i], capacity:  params.'capacity[]'[i], generalNote: params.'notes[]'[i], assetType: AssetType.findByAbbreviation('Power'))
                    if(!newPSU.hasErrors())
                        newPSU.save(failOnError:true)
                }
                break
            case 'PSU':
                def thePSU = PowerSource.get(params.id)
                for (int i=1; i<=Integer.valueOf(params.numItems); i++) {
                    def newBus = new PowerPanel(itsId: params.'name[]'[i], source: thePSU, capacity:  params.'capacity[]'[i], generalNote: params.'notes[]'[i], assetType: AssetType.findByAbbreviation('Power'))
                    if(!newBus.hasErrors())
                        newBus.save(failOnError:true)
                }
                break
            case 'panel':
                def newBreaker = new PowerBreaker(itsId: params.name, panel: PowerPanel.get(params.id), capacity: params.capacity, generalNote: params.note, assetType: AssetType.findByAbbreviation('Power'), voltage: params.voltage)
                if(!newBreaker.hasErrors())
                    newBreaker.save(failOnError:true)
                break
            case 'Bus':
                def theBus = PowerPanel.get(params.id)
                for (int i=1; i<=Integer.valueOf(params.numCDUs); i++) {
                    def theRack = Rack.get(params.'rack[]'[i])
                    def newCDU = new PowerBreaker(itsId: params.'name[]'[i], panel: theBus, capacity: params.'capacity[]'[i], generalNote: params.'notes[]'[i], ipAddress: params.'IP[]'[i],  rack: theRack, assetType: AssetType.findByAbbreviation('Power'))
                    if(!newCDU.hasErrors()) {
                        newCDU.save(failOnError:true)
                        def XY = new PowerStrip(itsId:"XY", breaker: newCDU, rack:theRack, assetType: AssetType.findByAbbreviation('Power'), capacity: 1)
                        XY.save(failOnError:true)
                        def XZ = new PowerStrip(itsId:"XZ", breaker: newCDU, rack:theRack, assetType: AssetType.findByAbbreviation('Power'), capacity: 1)
                        XZ.save(failOnError:true)
                        def YZ = new PowerStrip(itsId:"YZ", breaker: newCDU, rack:theRack, assetType: AssetType.findByAbbreviation('Power'), capacity: 1)
                        YZ.save(failOnError:true)
                    }
                    else
                        System.out.println(newCDU.errors.errorCount)
                }
                break
/*            case 'breaker':
                def theBreaker = PowerBreaker.get(params.id)
                for (int i=1; i<=Integer.valueOf(params.numStrips); i++) {
                    def theRack = Rack.get(params.'rack[]'[i])
                    def theType = PowerStripType.get(params.'stripType[]'[i])
                    def newStrip = new PowerStrip(itsId: params.'name[]'[i], breaker: theBreaker, capacity: params.'capacity[]'[i], generalNote: params.'notes[]'[i], IP: params.'IP[]'[i],  rack: theRack, type: theType, assetType: AssetType.findByAbbreviation('Power'))
                    if(!newStrip.hasErrors()) {
                        newStrip.save(failOnError:true)
                    }
                    else
                        System.out.println(newStrip.errors.errorCount)
                }

        //        def newStrip = new PowerStrip(itsId: params.name, breaker: PowerBreaker.get(params.id), capacity: params.capacity, generalNote: params.note, assetType: AssetType.findByAbbreviation('Power'))
        //        if(!newStrip.hasErrors())
        //            newStrip.save(failOnError:true)
                break
                            */
            case 'strip':

                break
        }
        //System.out.println(params)
        def response = [message:"",state:"OK",id:1]

        render response as JSON
    }

    def editNode = {
        System.out.println(params)
        switch(params.type) {
            case 'PSU':
                def theSource = PowerSource.get(params.id)
                theSource.properties = params
                if(!theSource.hasErrors())
                    theSource.save(failOnError:true)
                break
            case 'Bus':
                def thePanel = PowerPanel.get(params.id)
                thePanel.properties = params
                thePanel.source = PowerSource.get(params.parent)
                if(!thePanel.hasErrors())
                    thePanel.save(failOnError:true)
                break
            case 'CDU':
                def theBreaker = PowerBreaker.get(params.id)
                theBreaker.properties = params
                theBreaker.panel = PowerPanel.get(params.parent)
                theBreaker.rack = Rack.get(params.rackId)
                if(!theBreaker.hasErrors())
                    theBreaker.save(failOnError:true)
                else
                    System.out.println(theBreaker.errors.fieldError)
                break
        }
//        System.out.println(params)
        def response = [message:"",state:"OK",id:1]

        render response as JSON
    }

    def deleteObject = {
        def theObject
        switch(params.type) {
            case 'CDU':
                theObject = PowerBreaker.get(params.id)
                theObject.strips.each { theStrip ->
                    theStrip.devices.each {
                        it.delete()
                    }
                    theStrip.devices.clear()
                    theStrip.delete()
                }
                theObject.delete()
                break
            case 'Bus':
                theObject = PowerPanel.get(params.id)
                theObject.delete()
                break
            case 'PSU':
                theObject = PowerSource.get(params.id)
                theObject.delete()
                break
        }
        def response = [message:"", state: "OK", id: 1]
        render response as JSON
    }

    /*
    def unplugPowerStrip = {
        // Unplug all devices
        def strip = PowerStrip.get(Long.parseLong(params.id))
        strip.devices.each {
            it.delete()
        }
        strip.devices.clear()

        if(params.storageOption == 'delete') {
            strip.delete()
        }
        else if(params.storageOption){
            strip.inStorage = true
            strip.location = Location.get(Long.parseLong(params.storageOption))
            strip.breaker = null
            if(! strip.hasErrors())
                strip.save(flush:true)
        }

        def response = [message:"", state: "OK", id: 1]
        render response as JSON
    }
    */


    def listDevicesAsSelect = {
        System.out.println(params)
        def lst = PhysicalServer.createCriteria().list() {
            order('itsId')
        }

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listStripTypesAsSelect = {
        //System.out.println(params.assetId)
        def selectedId = 0
        if(params.assetId) {
            def theStrip = PowerStrip.get(Long.parseLong(params.assetId))
            selectedId = theStrip.type?.id
        }

        def apps = PowerStripType.createCriteria().list() {
            order('name', 'asc')
        }
        def theList = []

        apps.each {
            def tmp = [id:"${it.id}", text: it.toString()]
            theList.add(tmp)
        }
        def jsonData = [retVal: true, theList: theList, id: selectedId]
        render jsonData as JSON
    }

    def listStripTypesAsSelect2 = {
        def response = [retVal: true, result: []]
        //response.result.add([id:0, text:'Not Assigned'])
        def lst = PowerStripType.createCriteria().list() {
            order('name', 'asc')
        }

        lst.each {
            def item = [id:it.id, text:it.toString()]
            response.result.add(item);
        }
        render response as JSON
    }

    def listStoredStripsAsSelect2 = {
        def response = [retVal: true, result: []]
        //response.result.add([id:0, text:'Not Assigned'])
        def lst = PowerStrip.createCriteria().list() {
            eq('inStorage', true)
            order('itsId', 'asc')
        }

        lst.each {
            def item = [id:it.id, text:it.toString()]
            response.result.add(item);
        }
        render response as JSON
    }

    def getStripData = {
        def theStrip = PowerStrip.get(Long.parseLong(params.id))
        def response = [retVal: true, capacity: theStrip.capacity, IP: theStrip.IP, rack: theStrip.rack?.id, stripType:theStrip.type?.id, notes:theStrip.generalNote]
        render response as JSON

    }


}