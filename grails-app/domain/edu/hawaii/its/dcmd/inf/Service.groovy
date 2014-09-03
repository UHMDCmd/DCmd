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

class Service extends SupportableObject {

    ServiceLevelAgreement sla

    String serviceTitle
    String serviceDescription
    Status status
    String generalNote
    Environment env
    Date dateCreated = new Date()
    Date lastUpdated = new Date()

    String supportableType = "service"

    Application application

    static auditable = true

    static belongsTo = [application: Application]

    static mapping = {
        dependencySet cascade: 'all-delete-orphan'
        tablePerHierarchy false
    }

    static hasMany = [
            tiers: TierDependency,
            dependencySet: ServiceDependency
    ]

    static constraints = {
        serviceTitle(nullable:false, blank:false)
        serviceDescription(nullable:true)
        status(nullable:true)
        generalNote(nullable: true, maxSize: 1024)
        sla(nullable: true)
        env(nullable: true)
        tiers(nullable:true)

    }

    static mappedBy = [dependencySet: 'service']

    String toString() {
        "${serviceTitle} (${env?.abbreviation})"
    }

}
