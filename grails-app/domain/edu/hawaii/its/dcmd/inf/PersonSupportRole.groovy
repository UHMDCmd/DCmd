package edu.hawaii.its.dcmd.inf
/**
 * 
 * @author Jesse
 * 
 * @param person Instance Variable for Person Object
 * @param supportRole Instance Variable for SupportRole Object
 * @param supportableObjectType Instance Variable for the SupportableObjectType 
 */
class PersonSupportRole {

	Person person	
	SupportRole supportRole 
    SupportableObjectType supportableObjectType

    static auditable = true


    static hasMany = [contracts:Contract, vendors:Vendor, hosts:Host]
	
	static belongsTo = [Contract, Vendor, Host]
	
	static constraints = {
		person(nullable:false, unique:['supportRole', 'supportableObjectType'])
		supportRole(nullable:false)
		supportableObjectType(nullable:false)
	}
	
	String toString(){
		"${person} - ${supportRole} - ${supportableObjectType}"
	}
}
