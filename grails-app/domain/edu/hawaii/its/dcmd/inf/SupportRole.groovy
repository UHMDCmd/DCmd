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
