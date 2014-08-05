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
    Integer squareFootage
    Integer dataCenterID

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
        squareFootage(nullable:true)
        dataCenterID(nullable:true)
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
