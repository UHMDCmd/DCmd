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
