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
 * The Contract Payment History lists the annual payments costs for a contract.
 * Trending information is used for preparing annual renewals since price increases
 * often require additional supporting documentation such as list price information
 * for a Form 95.
 */
class ContractPaymentHistory {

    // Contract encumberance periods are limited to 1-year.
	// By State law funds may be encumbered for not more than single fiscal year.
	
	String contractMod = ""                      // Contract modification, e.g.: Mod #2
	String contractModDescription  = ""          // Contract modification description
	Date periodBeginDate                         // Anniversary date begin.
	Date periodEndDate						     // Usually anniversary date end.
	Float amountEncumbered                       // Amount encumbered.
	Float amountPaid                             // Actual amount paid to vendor.
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	
	static belongsTo = [contract:Contract]
	
    static constraints = {
		periodBeginDate(nullable:false, blank:false)
		periodEndDate(nullable:false, blank:false)
		amountEncumbered(nullable:false, min:0.0F)
		amountPaid(nullable:true, min:0.0F)      // TODO: This property may not be put to use initially.
    }
	
	/**
	 * Example return: $12,345.12 for Period 12/31/2011-12/30/2012
	 */
	String toString(){
		def df = new java.text.SimpleDateFormat("MM/dd/yyyy", Locale.US)
	    String.format('$ %,10.2f', amountEncumbered) + " for Period " + df.format(periodBeginDate) + "-" + df.format(periodEndDate)
	}

}
