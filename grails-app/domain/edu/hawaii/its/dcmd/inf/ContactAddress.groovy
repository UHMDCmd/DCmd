package edu.hawaii.its.dcmd.inf

import java.util.Date;

class ContactAddress {

	String addressLine1
	String addressLine2
	String city
	String state
	String zip       // Using string in case a foreign address requires some more flexible than allowed in the US.
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	
	static belongsTo = [person:Person]
	
    static constraints = {
		addressLine1(nullable:false, blank:false, maxSize:45)
		addressLine2(nullable:true, maxSize:45)
		city(nullable:false, blank:false, maxSize:45)
		state(nullable:false, blank:false, maxSize:45)
		zip(nullable:true, maxSize:45)
    }
	
	// Return the entire address as a comma delimited single line, skipping any missing optional data.
	String toString(){
		String address = addressLine1
		if (addressLine2 != null) {
		  address+=", " + AddressLine2
		}
		address+=", " + city + ", " + state
		if (zip != null){
			address+=", " + zip
		}
		return address
	}
}
