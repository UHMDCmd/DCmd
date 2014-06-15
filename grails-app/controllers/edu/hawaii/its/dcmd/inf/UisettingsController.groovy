package edu.hawaii.its.dcmd.inf

import grails.plugin.springsecurity.SpringSecurityService
import grails.converters.JSON

class UisettingsController {

    SpringSecurityService springSecurityService

    def index = {

    }

    def save_settings = {
        //  println("save settings action called")


        //retrieve the current logged in user
        def principal = springSecurityService.principal
        def username = principal.username
        long userId = principal.id
        User user = User.get(userId)
        //strange issue with offset number, temp switch case fix
        int num = 0
        if (params.theme == 49){  //grape theme
            num = 1
        }
        else if (params.theme == 50){ //darkness theme
            num =2
        }
        else if (params.theme == 51){ //dot luv
            num = 3
        } 


        user.properties['themeVal'] = num
        user.save(failOnError: true, flush: true)
        // println("theme val is set to (before call): " + User.get(userId).themeVal)
        // setThemeSessionVar(); //set the session variable




        redirect(controllerUri:'its/dcmd')

    }

    //     void setThemeSessionVar (){
    //
    //        def principal = springSecurityService.principal
    //        String username = principal.username
    //        long userId = principal.id
    //        int val = User.get(userId).themeVal
    //
    //       // println("retrieved user theme val: " + val)
    //
    //        session.setAttribute("themeVal",val)
    //    }

    /*   def listSampleData = {
    def results = new ArrayList<String>()

    for(int x = 0; x < 10; x ++){
    String input = "sample data " + x
    results.add(input)
    }
    def jsonData = [rows: results]
    //        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
    render jsonData as JSON
    }*/


}
