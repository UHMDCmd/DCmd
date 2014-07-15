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

class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}


        "/"(view: "layouts/main")

		"/"(view:"/index")
		"500"(view:'/error')

//        "/hosts"(resources: 'host') {
//            "/api"(controller:"API", method:"GET", action:"hostTest")
//        }

        "/host/testAction" (controller: "host", action: "testAction", parseRequest: true)
        "/host/APIEdit" (controller:"host", action: "APIEdit", method: 'PUT', parseRequest:true)
//        "/api/hosts" (resources: "API")

        "/api/testAction" (controller:"API", action:"testAction", parseRequest:true)
        "/api/hostList" (controller:"API", action:"hostList", parseRequest:true)
        "/api/host" (controller:"API", action:"hostList", method:'GET', parseRequest:true)
        "/api/host/$id" (controller:"API", action:"hostShow", method:'GET', parseRequest:true)
        "/api/host/$id" (controller:"API", action:"hostUpdate", method:'PUT', parseRequest:true)
        "/api/host/$id" (controller:"API", action:"hostDelete", method:'DELETE', parseRequest:true)
        "/api/host" (controller:"API", action:"hostUpdate", method:'PUT', parseRequest:true)
      //  "/api/host/$oldname" (controller:"API", action:"hostUpdate", method:'PUT', parseRequest:true)
        "/api/host" (controller:"API", action:"hostCreate", method:'POST', parseRequest:true)
        "/api/host/addService/$id" (controller:"API", action:"hostAddService", method:'PUT', parseRequest:true)
        "/api/host/addSupport/$id" (controller:"API", action:"hostAddSupport", method:'PUT', parseRequest:true)
        "/api/host/listSupport" (controller:"API", action:"hostListSupport", method:'GET', parseRequest:true)

        "/api/roleType" (controller:"API", action:"roleTypeList", method:'GET', parseRequest:true)
        "/api/person" (controller:"API", action:"personList", method:'GET', parseRequest:true)
        "/api/application" (controller:"API", action:"applicationList", method:'GET', parseRequest:true)
        "/api/application/$id" (controller:"API", action:"applicationShow", method:'GET', parseRequest:true)

        "/api/service" (controller:"API", action:"serviceList", method:'GET', parseRequest:true)
        "/api/cluster" (controller:"API", action:"clusterList", method:'GET', parseRequest:true)
        "/api/environment" (controller:"API", action:"environmentList", method:'GET', parseRequest:true)
        "/api/status" (controller:"API", action:"statusList", method:'GET', parseRequest:true)
        "/api/hostType" (controller:"API", action:"hostTypeList", method:'GET', parseRequest:true)

        //"/api/host"(resources:'APIHost')

	}
}
