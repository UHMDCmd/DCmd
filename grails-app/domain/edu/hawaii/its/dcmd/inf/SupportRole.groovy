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

/**
 * 
 * @author Jesse
 * 
 * @class SupportRole definition/label for type of role such as DBA, Programmer etc.
 * @param name label for the type of role
 */

class SupportRole {

	RoleType roleName // Project Lead, etc.
    String roleType // Functional, Technical, Fiscal
    Person person
    SupportableObject supportedObject
    String supportRoleNotes

    static auditable = true

    static belongsTo = [person: Person, supportedObject: SupportableObject]

//    static fetchMode = [roleName:'eager', person: 'eager']

    static constraints = {
		roleName(nullable:false)
        roleType(nullable: false)
        person(nullable: true)
        supportedObject(nullable: false)
        supportRoleNotes(nullable: true)

    }

}
