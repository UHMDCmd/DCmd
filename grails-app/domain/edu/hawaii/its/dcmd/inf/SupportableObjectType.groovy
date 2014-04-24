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
