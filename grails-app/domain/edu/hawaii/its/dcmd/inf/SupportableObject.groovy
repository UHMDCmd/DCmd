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

/*
 * This abstract class helps bundle together any objects that need to have support staff
 * assigned to them.  Each class that inherits from this class needs to override the type 
 * property and assign it a value of the class name.  If you create a type named Person,
 * the type should default to "person".
 */
class SupportableObject {

	Long id
	String supportableType
//    Long Version

    static hasMany = [
        supporters: SupportRole
    ]

    static mapping = {
        supporters cascade: 'all-delete-orphan'
        tablePerHierarchy false
    }
    static constraints = {
        supporters(nullable:true)
        supportableType(nullable: false)
        id(nullable: true)
    }
}
