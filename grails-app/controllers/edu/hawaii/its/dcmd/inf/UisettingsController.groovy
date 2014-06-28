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

        user.properties['themeVal'] = params.theme
        user.save(failOnError: true, flush: true)
        //println("theme val is set : " + User.get(userId).themeVal)

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
