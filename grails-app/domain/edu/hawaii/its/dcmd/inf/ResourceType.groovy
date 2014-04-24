package edu.hawaii.its.dcmd.inf

import java.util.Date;

class ResourceType {

	String resourceType
	String resourceDescription
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	Integer updatedById

    static constraints = {
		resourceType( blank: false, maxSize: 45, unique: true )
		resourceDescription( nullable: true)
        updatedById(nullable:true)
	}
	
	String toString() {
		resourceType
	}
}
