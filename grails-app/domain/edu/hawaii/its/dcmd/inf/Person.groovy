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

import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.FactoryUtils;
/**
 * 
 * @author Jesse
 * @class Person 
 */
class Person{

    String uhName
	String uhNumber
	String title
	String lastName
	String firstName
	String midInit = ""

    String primaryEmail
    String telephone

    String generalNote
    String changeNote
    String planningNote




    static auditable = true



//	List contactInfos = new ArrayList()

   // static hasMany = [contactInfos:ContactInfo, supportRoles:SupportRole]
   // static mapping = { contactInfos cascade:"all-delete-orphan" }
    static hasMany = [ supportRoles:SupportRole]


    /*
	def getContactInfosList() {
		return LazyList.decorate(
		contactInfos,
		FactoryUtils.instantiateFactory(ContactInfo.class))
	}
	*/

	static constraints = {
        uhName(unique:true, nullable:false)
		uhNumber(unique:true, matches: "[0-9]+", minSize: 8, maxSize: 12, nullable: true)
		title(maxSize: 45, nullable: true)
		lastName(maxSize: 45, nullable:true)
		firstName(maxSize: 45, nullable:true)

        primaryEmail(maxSize: 45, nullable:true)
        telephone(maxSize: 45, nullable:true)

        midInit(maxSize: 45, nullable: true)
        generalNote(nullable: true, maxSize: 1024)
        changeNote(nullable: true, maxSize: 1024)
        planningNote(nullable: true, maxSize: 1024)
	}

	String toString(){
        uhName
//		"${lastName}, ${firstName} ${midInit}".trim()
	}

    String primaryPhone() {
        return telephone
}
    String primaryEmail(){
       return primaryEmail
    }
/*    String primaryPhone() {
        for (info in contactInfos) {
            if (info.isPrimary && info.contactType=="Phone") {
                return info.contactInfo
            }
        }
        return null
    }
    String primaryEmail() {
        for (info in contactInfos) {
            if (info.isPrimary && info.contactType=="Email") {
                return info.contactInfo
            }
        }
        return null
    }*/
	
	
	
}
