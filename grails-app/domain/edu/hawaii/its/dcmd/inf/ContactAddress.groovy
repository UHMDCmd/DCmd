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

class ContactAddress {

	String addressLine1
	String addressLine2
	String city
	String state
	String zip       // Using string in case a foreign address requires some more flexible than allowed in the US.
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	
	static belongsTo = [person:Person]
	
    static constraints = {
		addressLine1(nullable:false, blank:false, maxSize:45)
		addressLine2(nullable:true, maxSize:45)
		city(nullable:false, blank:false, maxSize:45)
		state(nullable:false, blank:false, maxSize:45)
		zip(nullable:true, maxSize:45)
    }
	
	// Return the entire address as a comma delimited single line, skipping any missing optional data.
	String toString(){
		String address = addressLine1
		if (addressLine2 != null) {
		  address+=", " + AddressLine2
		}
		address+=", " + city + ", " + state
		if (zip != null){
			address+=", " + zip
		}
		return address
	}
}
