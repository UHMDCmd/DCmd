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
