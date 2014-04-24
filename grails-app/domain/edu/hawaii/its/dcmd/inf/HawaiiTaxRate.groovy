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