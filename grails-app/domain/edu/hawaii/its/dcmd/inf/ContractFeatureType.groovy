package edu.hawaii.its.dcmd.inf

import java.util.Date;

class ContractFeatureType {
	
	static hasMany = [contracts:Contract]
	static belongsTo = Contract
	
	String type
	String description
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	
    static constraints = {
		type(nullable:false, blank:false, unique:true)
		description(nullable:false, blank:false)
    }
	
	String toString() {
		type
	}
}
