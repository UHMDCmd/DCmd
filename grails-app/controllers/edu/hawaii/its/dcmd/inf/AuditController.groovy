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

import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEvent
import grails.converters.JSON

class AuditController {
    def scaffold = AuditLogEvent
    def exportService
    def index() { }

    def listAuditLog = {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        def log = AuditLogEvent.createCriteria().list(max: maxRows, offset: rowOffset) {
            if(params.eventName) ilike('eventName', "%${params.eventName}%")
//            if(params.lastUpdated) ilike('lastUpdated', "%${params.lastUpdated}%")
          if(params.actor) ilike('actor', "%${params.actor}%")
          if(params.className) ilike('className', "%${params.className}%")
          if(params.persistedObjectId) ilike('persistedObjectId', "%${params.persistedObjectId}%")
          if(params.propertyName) ilike('propertyName', "%${params.propertyName}%")
            order(sortIndex, sortOrder)
        }
//        def totalRows = AuditLogEvent.count()
        def totalRows = log.totalCount
//        System.out.println(maxRows)
//        System.out.println(totalRows)

        def results = log?.collect { [ cell:
            [it.eventName, it.lastUpdated.dateTimeString,
                it.actor,
              //  it.className.substring(24),
                    it.className,
                it.persistedObjectId, it.propertyName, it.oldValue, it.newValue], id: it.id ] }

        def numberOfPages = Math.ceil(totalRows / maxRows)

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON


    }
    def list = {

        if(!params.max) params.max = AuditLogEvent.count()
       def log = AuditLogEvent.all

        if(params?.format && params.format != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=auditLog.${params.extension}")


            // Formatter closure
            def upperCase = { domain, value ->
                return value.toUpperCase()
            }

            Map formatters = [:]
            Map parameters = ["column.widths": [0.1,0.2,0.3,0.3,0.2,0.1,0.3,0.1,0.1,0.2]]
           exportService.export(params.format,response.outputStream, log,formatters,parameters)

    }
    }

    def clearLog = {

        AuditLogEvent.executeUpdate('delete from AuditLogEvent')
        //def log = AuditLogEvent.createCriteria().list(){}
        //AuditLogEvent.deleteAll(log)
        def response = [retVal: true]
        render response as JSON
    }



}
