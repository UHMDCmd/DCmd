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

import org.hibernate.Criteria

class PowerService {

    def serviceMethod() {

    }

    /************************************************************************************************
     * Grid Methods
     ************************************************************************************************/

    def listDevices(params) {
        def sortIndex = params.sidx ?: 'itsId'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        def devices = DevicePlug.createCriteria().list(max: maxRows, offset: rowOffset) {
            eq('strip.id', Long.valueOf(params.stripId))
        }
        def totalRows = devices.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = devices?.collect { [ cell: [
            "<a href='../asset/show?id=${it.device.id}'>${it.device.itsId}</a>",
            it.connector.toString(),
            "<center><input type='button' value='Unplug' onclick=unplugDevice(${it.id}) /></center>",
        ],id: it.id ]}

        def jsonData = [rows: results, page: currentPage, records: totalRows, total:numberOfPages]
        return jsonData
    }

    def editDevices(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        // Get objects from IDs
        System.out.println(params)

        params.strip = PowerStrip.get(params.stripId)
        params.device = PhysicalServer.get(params.device)

        item = new DevicePlug(strip: params.strip, device: params.device)

        if (! item.hasErrors() && item.save()) {
//                    message = "Capacity ${item.toString()} Updated for ${params.asset.toString()}"
            id = item.id
            state = "OK"
        }
        else {
            System.out.println(item.errors.fieldError)
        }
        def response = [state:state,id:id]
        return response
    }


    /************************************************************************************************
     * Functions needed for the Visualization (loading, editing, etc.)
     ***********************************************************************************************/

    def getSources() {
        def index=0
        def sources = PowerSource.createCriteria().listDistinct() {

            createAlias('panels.breakers.strips', 'panels.breakers.strips', Criteria.LEFT_JOIN)
            cache true
        }
        def sourceData = []



        sources.each{ theSource ->
//            sourceData[index] = [id: theSource.id, name: theSource.itsId, children: getPanels(theSource)]
 //           index++

            sourceData.add([id:theSource.id, name:theSource.itsId, data:[$color:getColor(theSource.capacity, theSource.totalDraw()), type: 'PSU', capacity: theSource.capacity, draw: theSource.totalDraw()], children:getPanels(theSource)])
        }
        /*
        sourceData.add([id:0, name:'', data:[$width:'10%', type: 'label'], children:[
                [id: 0.2, name: '', data: [type: 'label'], children: [
                        [id: 0.3, name: '', data: [type: 'label'], children: [
                                [id: 0.4, name: '', data: [type: 'label']]
                        ]]
                ]]
        ]])
        */
        return sourceData
    }

    def getPanels(PowerSource theSource) {
        def panelData = []
        theSource.panels.each { thePanel ->
            panelData.add([id:thePanel.id, name:thePanel.itsId, data:[$color:getColor(thePanel.capacity, thePanel.totalDraw()), type: 'Bus', capacity: thePanel.capacity, draw: thePanel.totalDraw(), poles: thePanel.poles], children:getBreakers(thePanel)])
        }
        return panelData
    }

    def getBreakers(PowerPanel thePanel) {
        def breakerData = []

        thePanel.breakers.each{ theBreaker ->
            def rackId = 0
            if(theBreaker.rack)
                rackId = theBreaker.rack.id

            breakerData.add([id:theBreaker.id, name:theBreaker.itsId, data:[$color:getColor(theBreaker.capacity, theBreaker.totalDraw()), type: 'CDU', capacity: theBreaker.capacity, rackId: rackId, draw: theBreaker.totalDraw()], children:getStrips(theBreaker)])
        }
        return breakerData
    }
    def getStrips(PowerBreaker theBreaker) {
        def stripData = []
        theBreaker.strips.each{ theStrip ->
            def rackId = 0
            def typeId = 0
            if(theStrip.rack)
                rackId = theStrip.rack.id
            if(theStrip.type)
                typeId = theStrip.type.id

            stripData.add([id:theStrip.id, name:theStrip.itsId,data:[$color:getColor(theStrip.capacity, theStrip.powerUsed), type: 'strip', capacity:theStrip.capacity, draw:theStrip.powerUsed, ip:theStrip.IP, rackId: rackId, typeId:typeId]])
        }
        return stripData
    }

    def getColor(int capacity, int used) {
        return "#${(((float)used/(float)capacity)*255.0).toInteger().encodeAsHex()}00${(255.0-((float)used/(float)capacity)*255.0).toInteger().encodeAsHex()}"
    }

    def listSourcesAsSelect() {
        def lst = PowerSource.findAll()

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }
    def listPSUsAsSelect() {
        def lst = PowerSource.findAll()

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }
    def listBusAsSelect() {
        def lst = PowerPanel.findAll()

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }

}
