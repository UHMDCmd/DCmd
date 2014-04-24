package edu.hawaii.its.dcmd.inf

import grails.converters.JSON

class EnvironmentController {

    def scaffold = Environment

    def listEnvsAsSelect={
        def lst = Environment.createCriteria().list {
            order('abbreviation', 'asc')
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

}
