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

import grails.plugin.springsecurity.SpringSecurityService
import grails.converters.JSON

class UisettingsController {

    SpringSecurityService springSecurityService

    def index = {

    }

    def save_settings = {
        //retrieve the current logged in user
        def principal = springSecurityService.principal
        def username = principal.username
        long userId = principal.id
        User user = User.get(userId)

        user.userSettings.properties['themeVal'] = params.theme
        user.save(failOnError: true, flush: true)
        //println("theme val is set : " + User.get(userId).themeVal)

        redirect(controllerUri:'its/dcmd')

    }

    def changeBackground = {

        def principal = springSecurityService.principal
        def username = principal.username
        long userId = principal.id
        User user = User.get(userId)

        println("selection: " + params.selected)
        def selection = params.selected
        if (selection == 'selectable1'){
            user.userSettings.properties['background'] = 1
        }
        else if (selection == 'selectable2'){
            user.userSettings.properties['background'] = 2
        }
        else if (selection == 'selectable3'){
            user.userSettings.properties['background'] = 3
        }
        else if (selection == 'selectable4'){
            user.userSettings.properties['background'] = 4
        }
        else if (selection == 'selectable5'){
            user.userSettings.properties['background'] = 5
        }

        println("background set: " + user.userSettings.background)

        user.save(failOnError: true, flush: true)

        def jsonData = [returnVal : user.userSettings.background]
        render jsonData as JSON
    }

    def changeFont = {
        def size = params.fontValue

        def username = principal.username
        long userId = principal.id
        User user = User.get(userId)

        println("font selected: " + size)
        if(size == '11'){
            user.userSettings.properties['font'] = 11;
        }else if(size == '14'){
            user.userSettings.properties['font'] = 14;
        }  else if(size == '16'){
            user.userSettings.properties['font'] = 16;
        }  else if(size == '18'){
            user.userSettings.properties['font'] = 18;
        }  else if(size == '20'){
            user.userSettings.properties['font'] = 20;
        }
        else{
            println("font not set correctly")
        }
        println("font set: " + user.userSettings.font)

        user.save(failOnError: true, flush: true)

        def jsonData = [returnVal : user.userSettings.font]
        render jsonData as JSON

    }




}
