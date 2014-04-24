package edu.hawaii.its.dcmd.inf

import java.util.Date;

class AssetType {

	String name
	String description
    String abbreviation
    Date dateCreated = new Date()
	Date lastUpdated = new Date()

    static auditable = true


    static hasMany = [
		assets : Asset,
	]
	
	static constraints = {
		name( blank: false, maxSize: 45 )
		description ( nullable:true, maxSize: 45 )
	}
	
	String toString() {
        if(abbreviation == 'Server')
            return "Physical Server"
        else
		    return abbreviation
	}


}
