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

class Application extends SupportableObject {

	String applicationTitle
	String applicationDescription

	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	Integer updatedById
    String generalNote
    String changeNote
    String planningNote
    Status status
    Environment env

	String supportableType = "application"
	
	static hasMany = [
        supportStaff:PersonSupportRole,
        services: Service,
        tiers: Tier
	]

    static mapping = {
        table 'applications'
        version false
    }

	static constraints = {
		applicationTitle(blank: false, maxSize: 256)
		applicationDescription(nullable: true, maxSize: 256)
//		applicationTier(nullable: true, maxSize: 45)
		dateCreated(nullable:  false)
		lastUpdated(nullable:  false)
		updatedById(nullable:  true)
        generalNote(nullable: true, maxSize: 1024)
        changeNote(nullable: true, maxSize: 1024)
        planningNote(nullable: true, maxSize: 1024)
        status(nullable: true, default:"Available")
        services(nullable: true)
        env(nullable: true)

	} \

    static auditable = true

	String toString(){
        "${applicationTitle} (${env})"
	}
}

