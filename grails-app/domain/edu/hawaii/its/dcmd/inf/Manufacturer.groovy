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

import java.util.Date;

class Manufacturer {

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
	Integer updatedById
	String type = "Manufacturer"

    static constraints = {
		code(nullable:true)
		name(blank:false)
		phone(nullable:true)
		fax(nullable:true)
		addressLine1(nullable:true)
		addressLine2(nullable:true)
		city(nullable:true)
		state(nullable:true)
		zip(nullable:true)
//		dateCreated( nullable: false )
//		lastUpdated( nullable: false )
		updatedById(nullable:true)
    }
	
	String toString() {
		name
	}
}
