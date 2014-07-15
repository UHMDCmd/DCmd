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

/**
 * Hawaii State GET tax rates validation.
 */
class HawaiiTaxRate {
	
	Float rate
	String description
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	
	// While we don't want cascading deletes, we want to ensure that
	// a tax rate can't be deleted if it is associated with any
	// contracts.
	static hasMany = [contracts:Contract]

    static constraints = {
		rate(nullable:false, unique:true, min:0.0000F, max:0.2000F)
		description(nullable:true)
    }
	
	String toString(){
		//return (rate * 100.000F).toString() + "%"
		return String.format ('%5.4f', rate * 100.0000F) + "%"
	}
}