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

class Vendor extends SupportableObject {

	// Default optional attributes only to empty Strings.
	String name
	String code=""
	String phone
	String fax=""
	String addressLine1=""
	String addressLine2=""
	String city=""
	String state=""
	String zip=""
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	String supportableType = "vendor"

	List notes = new ArrayList()
	
	
	//static belongsTo = Contract
	
	static hasMany = [
	//	supporters:PersonSupportRole,
		]
	
    static constraints = {
		code()
		name(blank:false)
		phone(blank:false)
		fax()
		addressLine1()
		addressLine2()
		city()
		state()
		zip()

		
//		supporters(validator: {
//			if(it){
//				def invalid
//				it.find{ supporter ->
//					invalid = supporter.supportableObjectType.name != "Vendor"
//				}
//				return (!invalid)
//			}else{ true }
		
//		})
    }
	
//	def getSupportersList() {
//		return LazyList.decorate(
//		supporters,
//		FactoryUtils.instantiateFactory(PersonSupportRole.class))
//	}

	
	String toString(){
		name
	}
}
