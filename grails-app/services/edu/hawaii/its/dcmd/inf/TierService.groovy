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

import org.hibernate.criterion.Order

class TierService {

    static transactional = true

    def personService

    /*********************************************************************************/
    /* Grid Funtions
    /*********************************************************************************/

    def listAll(params) {
        def sortIndex = params.sidx ?: 'tierName'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        if(params.appEnv) params.env = Environment.findByAbbreviation(params.env)

        def tiers = Tier.createCriteria().list(max: maxRows, offset: rowOffset) {
// Search Filters
            if (params.tierName) ilike('tierName', "%${params.tierName}%")
            if (params.app) {
                mainApp {
                    ilike('applicationTitle', "%${params.app}%")
                }
            }
            if (params.env) {
                mainApp {
                    env {
                        ilike('abbreviation', "%${params.serviceEnv}%")
                    }
                }
            }
            if (params.host) {
                host {
                    ilike('hostname', "%${params.host}%")
                }
            }
            if (params.loadBalanced) eq('loadBalanced', Boolean.valueOf(params.loadBalanced))
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}%")
// Sort
            switch (sortIndex) {
                case 'app':
                    mainApp {
                        order('applicationTitle', sortOrder)
                    }
                    break
                case 'env':
                    mainApp {
                        env {
                            order('abbreviation', sortOrder)
                        }
                    }
                    break
                case 'host':
                    host {
                        order('hostname', sortOrder)
                    }
                    break
                default:
                    if(sortOrder == 'asc')
                        order( Order.asc(sortIndex).ignoreCase() )
                    else
                        order( Order.desc(sortIndex).ignoreCase() )
                    break
            }
        }

        def totalRows = tiers.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        def results = tiers?.collect { [ cell: [it.tierName, it.mainServiceLink,
                it.mainServiceAdminLink,
                "<a href='../application/show?id=${it.mainApp?.id}'>${it.mainApp.toString()}</a>",
                it.mainApp?.env.toString(),
                "<a href='../host/show?id=${it.host?.id}'>${it.host.toString()}</a>",
                "<a href='../person/show?id=${personService.getAdmin(it.host)?.id}'>${personService.getAdmin(it.host).toString()}</a>",
                it.loadBalanced,
                it.generalNote], id: it.id ] }
        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def listAllSubGrid(params) {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = 1000

        def currentTier = Tier.get(params.id)

        def hosts = Host.createCriteria().list() {
            tiers {
                tier {
                    like('id', currentTier.id)
                }
            }
        }

        def totalRows = hosts.size()
        def numberOfPages = totalRows
        def results = hosts?.collect { [ cell: [
            "<a href='../host/show?id=${it.id}'>${it.hostname}</a>",
                it.env.toString(), it.status.toString(),
            "<a href='../person/show?id=${personService.getAdmin(it)?.id}'>${personService.getAdmin(it).toString()}</a>",
            "<a href='../asset/show?id=${it.asset?.id}'>${it.asset.toString()}</a>",
                it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: 1, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def listServices(params) {
        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = 1000

        def currentTier = Tier.get(params.tierId)

        def dependencies = TierDependency.createCriteria().list() {
            tier {
                like('id', currentTier.id)
            }
        }

        def totalRows = dependencies.size()
        def numberOfPages = totalRows
        def results = dependencies?.collect { [ cell: ['',
                "<a href='../service/show?id=${it.service.id}'>${it.service.toString()}</a>",
                it.service.env.toString(), it.service.status.toString(), it.service.serviceDescription,
                "<a href='../person/show?id=${personService.getAdmin(it.service)?.id}'>${personService.getAdmin(it.service).toString()}</a>",
                it.service.generalNote, it.generalNote], id: it.id ] }

        def jsonData = [rows: results, page: 1, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def editServices(params) {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        def role = null
                   System.out.println(params)
        if(params.service)
            params.service = Service.get(params.service)
        // determine our action
        switch (params.oper) {
            case 'add':
                params.tier = Tier.get(params.tierId)

                item = new TierDependency(params)
                if (! item.hasErrors() && item.save()) {
                    message = "Dependency Created"
                    id = item.id

                    state = "OK"
                } else {
                    System.out.println(item.errors.allErrors.get(0).toString())
                    message = item.errors.allErrors.get(0).toString()
                }
                break;
            case 'edit':
                item = TierDependency.get(params.id)
                item.properties = params
                if (! item.hasErrors() && item.save()) {
                    message = "Dependency Updated"
                    id = item.id
                    state = "OK"
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'del':
                item = TierDependency.get(params.id)
                if (item) {
                    id = item.id
                    if (! item.hasErrors() && item.delete()) {
                        state = "OK"
                    }
                }
                break;
        }
        def response = [message:message,state:state,id:id]

        return response
    }

    def getAllTiers() {
        def allTiers = Tier.getAll()
        StringBuffer buf = new StringBuffer("{id:\'${allTiers.id}\',text:'${allTiers.toString()}'}")
        return buf.toString()
    }

    def listTiersAsSelect={
        def lst = Tier.findAll()

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        return buf.toString()
    }

    def listTierTypesAsSelect={
        def lst = TierType.findAll()

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        return buf.toString()
    }

    def getTierFromDependency(id) {
        System.out.println("TEST")
        System.out.println(id)
        return 0
    }


}
