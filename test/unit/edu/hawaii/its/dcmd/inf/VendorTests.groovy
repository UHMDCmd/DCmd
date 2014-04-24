package edu.hawaii.its.dcmd.inf

import grails.test.*

class VendorTests extends GrailsUnitTestCase {
	
	def vendor
	def vendorSaved
	
	protected void setUp() {
		super.setUp()
		vendor = new Vendor(
				name:"Commercial Data Systems",
				code:"CDS",
				phone:"808-527-2000",
				)
		mockForConstraintsTests(Vendor, [vendor])
		assertTrue "Not a valid Vendor object", vendor.validate()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testAllPropertiesSetNullableFalse(){

		//vendor.validate()
		//vendor.errors.inspect()
		
		def includes = [
			'name',
			'phone',
		]

		vendor.properties.each{p->
			if(includes.contains(p.key)){
				assertTrue vendor.validate()
				vendorSaved = p.value
				vendor."${p.key}" = null
				assertFalse vendor.validate()
				assertEquals "nullable", vendor.errors."${p.key}"
				vendor."${p.key}" = vendorSaved
			}
		}
	}
	
	void testAcceptsSupporterWithVendorSupportableObjectType(){
		def person = new Person(uhNumber: "55555555", title: "dtitle", lastName: "dlastname", firstName: "dfirstName", midInit:"dmidInit")
		def supportRole = new SupportRole(name:'sa')
		def supportableObjectType = new SupportableObjectType(name:'Vendor')
				
		mockDomain(Vendor, [vendor])
		vendor.addToSupporters(person:person, supportRole:supportRole, supportableObjectType:supportableObjectType)
		assertTrue vendor.validate()		
	}

	void testRejectsSupporterWithNonVendorSupportableObjectType(){
		def person = new Person(uhNumber: "55555555", title: "dtitle", lastName: "dlastname", firstName: "dfirstName", midInit:"dmidInit")
		def supportRole = new SupportRole(name:'sa')
		def supportableObjectType = new SupportableObjectType(name:'AnythingButVendor')
				
		mockDomain(Vendor, [vendor])
		vendor.addToSupporters(person:person, supportRole:supportRole, supportableObjectType:supportableObjectType)
		vendor.validate()
		assertEquals 'validator', vendor.errors.supporters
	}
		
	void testToString(){
		assertEquals vendor.name, vendor.toString()
	}
}
