package edu.hawaii.its.dcmd.inf

import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.FactoryUtils;

class Vendor extends SupportableObject {

	// Default optional attributes only to empty Strings.
	String name
	String code=""
	String phone
	String fax=""
	String addressLine1=""
	String addressLine2=""
	String city=""
	String state=""
	String zip=""
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	String supportableType = "vendor"
	//List supporters = new ArrayList()
	List notes = new ArrayList()
	
	
	//static belongsTo = Contract
	
	static hasMany = [
	//	supporters:PersonSupportRole,
		]
	
    static constraints = {
		code()
		name(blank:false)
		phone(blank:false)
		fax()
		addressLine1()
		addressLine2()
		city()
		state()
		zip()

		
//		supporters(validator: {
//			if(it){
//				def invalid
//				it.find{ supporter ->
//					invalid = supporter.supportableObjectType.name != "Vendor"
//				}
//				return (!invalid)
//			}else{ true }
		
//		})
    }
	
//	def getSupportersList() {
//		return LazyList.decorate(
//		supporters,
//		FactoryUtils.instantiateFactory(PersonSupportRole.class))
//	}

	
	String toString(){
		name
	}
}
