package edu.hawaii.its.dcmd.inf

import java.util.Date;

class Location {

	String locationDescription
    String addr
    String building
    String roomNum
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	Integer updatedById
    String generalNote
    String changeNote
    String planningNote

    static auditable = true


    static hasMany = [
		assets : Asset
	]
	
    static constraints = {
		locationDescription( nullable: false, maxSize: 45 )
        addr(nullable:true)
        building(nullable:true)
        roomNum(nullable:true)
//		dateCreated( nullable: false )
//		lastUpdated( nullable: false )
		updatedById( nullable: true )
        generalNote(nullable: true)
        changeNote(nullable: true)
        planningNote(nullable: true)
    }

	String toString() {
		return locationDescription
	}

    Number getRacks() {
        def numRacks = Rack.createCriteria().count() {
            like('location.id', this.id)
        }
        return numRacks
    }
    Number getServers() {
        def numServers = PhysicalServer.createCriteria().count() {
            like('location.id', this.id)
        }
        return numServers
    }

}
