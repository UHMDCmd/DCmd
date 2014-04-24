package edu.hawaii.its.dcmd.inf

import java.util.Date;

class UnitType {

	String unit
	String unitDescription
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	Integer updatedById

    static constraints = {
		unit( blank: false, maxSize: 45, unique: true )
		unitDescription( blank: false, maxSize: 45 )
	}
	
	String toString() {
		unit
	}
}
