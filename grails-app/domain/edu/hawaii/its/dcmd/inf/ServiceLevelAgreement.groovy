package edu.hawaii.its.dcmd.inf

import java.util.Date;

class ServiceLevelAgreement {

	String slaTitle
	String slaType
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	
    static constraints = {
		slaTitle(blank: false, maxSize: 45)
		slaType(blank: false, maxSize: 45)
    }
	
	String toString() {
		"${slaTitle} - ${slaType}"
	}
}
