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
 * @class 	SupportableObjectType definition/label for object being supported by
 * 		  	the person in the support role
 * @param 	name the label for the object
 * @param 	description a description for the type of object
 */
class SupportableObjectType {

	String name
	String description

	static constraints = {
		name(nullable:false, blank:false, unique:true)
		description(nullable:true)
	}

	String toString(){
		name
	}
}
