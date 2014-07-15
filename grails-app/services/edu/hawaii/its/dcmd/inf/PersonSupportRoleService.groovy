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

/**
 * 
 * @author Jesse
 * @class PersonSupportRoleService class that implements PSR objects, used by controller 
 */
class PersonSupportRoleService {

    static transactional = true

    def getOrCreatePersonSupportRole(long personId, long supportRoleId, long supportableObjectTypeId) {

		log.debug "personId: ${personId}"
		log.debug "supportRoleId: ${supportRoleId}"
		log.debug "supportableObjectId: ${supportableObjectTypeId}"
		
		def crit = PersonSupportRole.createCriteria()
		def personSupportRole = crit.get(){
			person{
				eq "id", personId
			}
			supportRole{
				eq "id", supportRoleId
			}
			supportableObjectType{
				eq "id", supportableObjectTypeId
			}
		}
		
		/*
		 * If the criteria for the PSR is not found, creates a new PSR object with input data 
		 */
		if(personSupportRole == null){
			log.debug "Creating new psr -- existing not found"
			personSupportRole = new PersonSupportRole(person:Person.get(personId),
					supportRole:SupportRole.get(supportRoleId),
					supportableObjectType:SupportableObjectType.get(supportableObjectTypeId))
			personSupportRole.save(failOnError:true, flush:true)
		}
		personSupportRole
    }
}
