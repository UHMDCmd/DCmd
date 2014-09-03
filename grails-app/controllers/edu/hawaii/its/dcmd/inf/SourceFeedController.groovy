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

class SourceFeedController {


    def scaffold = PowerSource

    def show_source_panel ={
        def sourceInstance = PowerSource.get(params.id)
        //  println(sourceInstance.itsId)

        // def panels = PowerPanel.findAllByPowersource(sourceInstance)

        /*  for (int x = 0; x < panels.size(); x++){
            println(panels.get(x).itsId)
        }*/
        println("total panels: " + sourceInstance.panels.toListString())

        render(view: "show_source_panel", model: [sourceInstance: sourceInstance])
    }

    def listAllSources = {

        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows


        def feeds = PowerSource.createCriteria().list(max: maxRows, offset: rowOffset) {
            if (params.itsId) ilike('itsId', "%${params.itsId}%")

            order(sortIndex, sortOrder)
        }

        def totalRows = feeds.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = feeds?.collect { [ cell: [it.itsId, it.dataCenter, it.capacity, it.ipAddress, it.SNMP_community, it.load_OID, it.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

    def listSourcePanels = {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        def currentSource = PowerSource.get(params.sourceId)

        def panels = PowerPanel.createCriteria().list(max: maxRows, offset: rowOffset) {
            like("source.id",currentSource.id)
            order(sortIndex, sortOrder)
        }


        def totalRows = panels.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = panels?.collect { [ cell: ['',it.itsId, it.breaker_poles, it.breakersInUse(),it.mainBreakerAmperage, it.panelVoltage, it.numberingScheme,  it.id], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON


    }

    /* def editSourcePanels = {

            def item = null
            def message = ""
            def state = "FAIL"
            def id
            // Get objects from IDs
              params.currentPanel = PowerPanel.get(params.id)
              println("current panel id: " + params.currentPanel.id)
               println("current panel name: " + params.currentPanel.itsId)


            if (params.itsId){
                params.currentPanel.itsId = params.itsId
            }

            if(params.breaker_poles){
               params.currentPanel.breaker_poles = Integer.parseInt(params.breaker_poles) //update the breaker poles to user input
            }

             if(params.mainBreakerAmperage) {
            params.currentPanel.mainBreakerAmperage = Integer.parseInt(params.mainBreakerAmperage)
              }
            if(params.mainBreakerAmperage) {
            params.currentPanel.mainBreakerAmperage = Integer.parseInt(params.mainBreakerAmperage)
              }
              if(params.numberingScheme) {
            params.currentPanel.numberingScheme = params.numberingScheme
               }
              if(params.generalNote)
                params.currentPanel.generalNote = params.generalNote

            if (! params.currentPanel.hasErrors() && params.currentPanel.save()) {
                id = params.currentPanel.id
                state = "OK"
            }

            def jsonData = [state:state,id:id]
            render jsonData as JSON

    }*/

    def editSourcePanels={

        def item = null
        def message = ""
        def state = "FAIL"
        def id
        def currentSource
        def newPanel = null
        def poleCount

        switch (params.oper){
            case 'add':
                currentSource = PowerSource.get(params.sourceId)

                params.currentPanel = newPanel

                newPanel = new PowerPanel(assetType:AssetType.findByAbbreviation('Power'), itsId: params.itsId, breaker_poles: params.breaker_poles,mainBreakerAmperage: params.mainBreakerAmperage)

                currentSource.addToPanels(newPanel)

                if (!newPanel.hasErrors() && newPanel.save()){
                    if (!currentSource.hasErrors() && currentSource.save()){
                        println("panel instance created.")
                        id = newPanel.id
                        state = "OK"
                    }
                }
                else{
                    System.out.println(newPanel.errors.allErrors.get(0).toString())
                }
                break;

            case 'edit':
                // inline Edit
                params.currentPanel = PowerPanel.get(params.id)
                println("current panel id: " + params.currentPanel.id)
                println("current panel name: " + params.currentPanel.itsId)


                if (params.itsId){
                    params.currentPanel.itsId = params.itsId
                }
                if(params.breaker_poles){
                    params.currentPanel.breaker_poles = Integer.parseInt(params.breaker_poles) //update the breaker poles to user input
                }
                if(params.mainBreakerAmperage) {
                    params.currentPanel.mainBreakerAmperage = Integer.parseInt(params.mainBreakerAmperage)
                }
                if(params.numberingScheme) {
                    params.currentPanel.numberingScheme = params.numberingScheme
                }
                if(params.generalNote)
                    params.currentPanel.generalNote = params.generalNote

                if (! params.currentPanel.hasErrors() && params.currentPanel.save()) {
                    id = params.currentPanel.id
                    state = "OK"
                }
                break;

            case 'del':
                System.out.println(params.id)
                item = PowerPanel.get(params.id)


                if (item){
                    if (item.breakersInUse() != 0){
                    }

                    else{
                        if (!item.hasErrors() && item.delete()){
                            state = "OK"
                        }
                    }
                }
                break;
        }

        def jsonData = [message:message,state:state,id:id]
        render jsonData as JSON

    }


    def save = {

        String saveOption = params.option //selects type of save to do

        def sourceInstance = new PowerSource()
        sourceInstance = PowerSource.get(params.id)

        //save and redirect or re-edit the new asset
        if (sourceInstance.save(flush: true)) {
            //save options
            if (saveOption.equals("saveEdit")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'source.itsId', default: 'source'), sourceInstance.id])}"
                redirect(url: "/PowerSource/edit?id=${sourceInstance.id}")
            }
            else if (saveOption.equals("saveList")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'source.itsId', default: 'source'), sourceInstance.id])}"
                redirect(url: "/PowerSource/list")
            }
            else if (saveOption.equals("saveCreate")){
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'source.itsId', default: 'source'), sourceInstance.id])}"
                redirect(url: "/PowerSource/create")
            }
            else{
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'source.itsId', default: 'source'), sourceInstance.id])}"
                redirect(url: "/PowerSource/show_source_panel?id=${sourceInstance.id}")
            }

        } else {
            render(view: "create", model: [sourceInstance: sourceInstance])
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
        // assetService.cleanseCloneParams(params)
        // assetService.logParams(params)

        //get the instance being updated
        def sourceInstance = PowerSource.get(params.id.toLong())

        if (sourceInstance) {
            System.out.println(params)
            log.debug "source instance not null"
            sourceInstance.properties = params

            //save and redirect
            if (!sourceInstance.hasErrors() && sourceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'source.itsId', default: 'Source'), sourceInstance.id])}"
                redirect(url: "/PowerSource/show_source_panel?id=${sourceInstance.id}")
            } else {
                render(view: "edit", model: [sourceInstance: sourceInstance])
            }

            //asset not found; no edits can be performed; show the list
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'source.itsId', default: 'Source'), params.id])}"
            redirect(action: "list")
        }
    }

    def getPanelDataForBreakerForm = {
        def jsonData
        PowerPanel panel = PowerPanel.get(params.panelId)
        println("ajax for breaker form: " + "\npanel:" +panel.itsId + "\npoles: " + panel.breaker_poles +
                "\nbreakers in use: " + panel.breakersInUse() + "\nsource id:" + panel.source.id)

        jsonData = [panel.itsId,panel.breaker_poles, panel.breakersInUse(), panel.source.id];

        render jsonData as JSON

    }
}