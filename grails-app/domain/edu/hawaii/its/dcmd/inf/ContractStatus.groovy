package edu.hawaii.its.dcmd.inf

import java.util.Date;

class ContractStatus {

	String status
	String description
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	
	static hasMany = [contracts:Contract]
	
    static constraints = {
		status(nullable:false, blank:false, unique:true)
		description(nullable:false, blank:false)
    }
	
	String toString(){
		"${status} - ${description}"
		
	}
}
