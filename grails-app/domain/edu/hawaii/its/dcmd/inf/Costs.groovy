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

class Costs {
	
	Integer renewalFiscalYear
	Float renewalCost
	Float listPrice
	Float uhDiscount
	Boolean isProrated=false
	Boolean includesTax=false
	Float taxRate
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	
	static belongsTo = [asset:Asset]

    static constraints = { 
		renewalFiscalYear(blank:false, range:10001231..30001231) 
		renewalCost(blank:false, min:0F)
		uhDiscount(blank:false, range:0F..0.75F)
		isProrated(blank:false)
		includesTax(blank:false)
		taxRate(blank:false, range:0F..0.1500F) 
    } 
}
