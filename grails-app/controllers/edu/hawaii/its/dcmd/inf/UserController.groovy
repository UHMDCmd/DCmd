package edu.hawaii.its.dcmd.inf

import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.orm.auditable.AuditLogEvent

class UserController {

    def scaffold = User
    def index() { }

    /*****************************************************************/
    /* Main Hosts Grid
    /*****************************************************************/
    def listUsers = {
        def sortIndex = params.sidx ?: 'username'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

//        def users = User.createCriteria().list(max: maxRows, offset: rowOffset) {
//        }

        def users = User.createCriteria().list(sort:"username") {
            if(params.username)
                ilike('username', "%${params.username}%")

            order(sortIndex, sortOrder)
        }


        def totalRows = users.size()
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = users?.collect { [ cell: ['', it.username, it.authorities.first().authority], id: it.id]}

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON

    }


    def editUsers = {
        def item = null
        def message = ""
        def state = "FAIL"
        def id

        // determine our action
        switch (params.oper) {
            case 'add':
                params.role = Role.get(params.role)
                item = new User(username: params.username, password:'1', enabled:true)
//                item.main_asset = Asset.get(params.assetId)
                if (! item.hasErrors() && item.save()) {
                    def roles = UserRole.create(item, params.role)
                    if(! roles.hasErrors() && roles.save()) {

                        id = item.id
                        state = "OK"
                    }
                } else {
                    message = item.errors.errorCount
                }
                break;
            case 'edit':

                item = User.get(params.id)
                if(params.role) {
                    def roleList = UserRole.createCriteria().list() {
                        like("user.id", item.id)
                    }
                    if(roleList) {
                        roleList.each {
                            if(! it.hasErrors()) {
                                it.delete()
                                System.out.println("Deleting ${it.user.username} - ${it.role.authority}")
                            }
                        }
                    }
                    params.role = Role.get(params.role)
                    def newRole = new UserRole(user: item, role: params.role)
                    if(! newRole.hasErrors() && newRole.save()) {

                        id = item.id
                        state = "OK"
                    }
                }
                if(params.username) {
                    item.username = params.username
                    if(! item.hasErrors() && item.save()) {
                        id = item.id
                        state = "OK"
                    }
                }

                break;
            case 'del':
                item = User.get(params.id)
                if (item) {
                    id = item.id
                    def roleList = UserRole.createCriteria().list() {
                        like("user.id", item.id)
                    }
                    if(roleList) {
                        roleList.each {
                            if(! it.hasErrors())
                                it.delete()
                        }
                    }
                    if (! item.hasErrors() && item.delete()) {
//                        message = "Replacement ${params.replacement} for Asset ${params.main_asset} Deleted."
                        state = "OK"
                    }
                }
                break;
        }

        def response = [message:message,state:state,id:id]

        render response as JSON
    }

    def listRolesAsSelect={

        def lst = Role.findAll()

        lst.sort{ it.authority }

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.authority)
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

}
