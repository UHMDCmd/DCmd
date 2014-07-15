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

class BreakerController {

    def createBreakers = {
        println("\n***************CREATE Breakers**************")

        def itsId = params.itsId
        def data = params.'poleData[]'
        def sourceId = params.sourceId
        def panelId = params.panelId
        def breakerCount = 0;
        println('sourceId: ' + sourceId)
        println('panel being edited: ' + itsId)


        println("pole data output from breaker controller:")
        println(data.toString())

        def sourceFeed = PowerSource.get(sourceId)
        def sourcePanels = sourceFeed.getPanels().asList()
        def current = PowerPanel.get(panelId)

        PowerPanel panelInstance

        if (sourcePanels.contains(current)){
            panelInstance = current
            println("current panel retrieved: " + panelInstance.itsId +"\n poles: " + panelInstance.breaker_poles)
        }
        else{
            println("could not find panel.")
        }

        /*************************************
         Create and assign new breakers
         ***************************************/
        try{
            for (int x = 0; x < data.length; x++){

                def index = (x+1);

                //PowerBreaker twoTwentyBreaker = new PowerBreaker()

                String oneTenLabel = panelInstance.itsId +'_'+ index
                String twoTwentyLabel = panelInstance.itsId + '_'+index + "/" + (index+2)

                //get current list of breakers
                def breakerList = panelInstance.breakers.collect {it.itsId}

                //if the list of breakers do not contain the newly added breaker, create them.
                if(((breakerList.collect().contains(oneTenLabel)))||((breakerList.collect().contains(twoTwentyLabel)))){
                    println("found match skipping current breaker") //if breaker already exists in panel, skip it
                }
                else{
                    println("did not find match, creating new breaker")
                    if (data[x].equals('110')){

                        PowerBreaker oneTenBreaker = new PowerBreaker(itsId:oneTenLabel, label:oneTenLabel, assetType:AssetType.findByAbbreviation('Power'), voltage: 110, pole_1: index, pole_2: 0, panel: panelInstance )

                        if(!oneTenBreaker.hasErrors() && oneTenBreaker.save(flush: true, failonError:true)){

                            panelInstance.addToBreakers(oneTenBreaker) //add new breaker to panel and save
                            if(!panelInstance.hasErrors() && panelInstance.save(flush: true, failOnError: true)){
                                println("breaker created successfully. PowerPanel saved")
                            }
                        }
                        else{
                            System.out.println(oneTenBreaker.errors.allErrors.get(0).toString())
                        }
                    }

                    else if (data[x].equals('220')){
                        PowerBreaker twoTwentyBreaker = new PowerBreaker(itsId:twoTwentyLabel, label:twoTwentyLabel, assetType:AssetType.findByAbbreviation('Power'), voltage: 220, pole_1: index, pole_2: (index+2), panel: panelInstance )

                        if(!twoTwentyBreaker.hasErrors() && twoTwentyBreaker.save(flush: true, failonError:true)){
                            panelInstance.addToBreakers(twoTwentyBreaker) //add new breaker to panel and save
                            if(!panelInstance.hasErrors() && panelInstance.save(flush: true, failOnError: true)){
                                println("breaker created successfully. PowerPanel saved")
                            }
                        }
                        else{
                            System.out.println(twoTwentyBreaker.errors.allErrors.get(0).toString())
                        }
                    }

                }

            }
        }
        catch(Exception e){
            println("exception caught: " + e.toString() +'\nstack trace:' + e.stackTrace.toString())
        }

        if(!panelInstance.hasErrors() && panelInstance.save(flush: true, failOnError: true)){
            println("PowerPanel updated")
        }
        else{
            System.out.println(panelInstance.errors.allErrors.get(0).toString())
        }

        println("breakers in use: " + panelInstance.breakersInUse())

        def breakers = panelInstance.getBreakers().asList()

        println("current breakers => ")
        for (int x = 0 ; x < breakers.size(); x++){
            PowerBreaker b =  breakers.get(x);
            println("\nbreaker: " + b.label)
            println("voltage: " + b.voltage)
        }

    }


    def getBreakers = {
        println("\n***************GET Breakers**************")
        println("the seleted panel: " + params.itsId)
        println("source id " + params.sourceId)
        println("panel Id " + params.panelId)

        def jsonData

        def sourceId = params.sourceId
        def sourceFeed = PowerSource.get(sourceId)
        def panel = PowerPanel.get(params.panelId)
        println("retrieved power source " + sourceFeed.itsId)
        println("retrieved power panel " + panel.itsId)

        def breakers = panel.breakers.asList()
        println("breaker count " + breakers.size())


        //TO DO: add code to inject links for power strip edit/view pages

        jsonData = breakers.collect{[it.label,it.voltage, it.pole_1, it.pole_2, [it.strips.itsId]]}


        render jsonData as JSON
    }

}
