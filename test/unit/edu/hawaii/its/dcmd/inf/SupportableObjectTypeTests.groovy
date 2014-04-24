package edu.hawaii.its.dcmd.inf

import grails.test.*

class SupportableObjectTypeTests extends GrailsUnitTestCase {
	def sot
    protected void setUp() {
        super.setUp()
		sot = new SupportableObjectType(name:"Vendor")
		mockForConstraintsTests(SupportableObjectType, [sot])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNameCannotBeNull() {
		sot.name = null
		sot.validate()
		assertEquals 'nullable', sot.errors.name
    }
	
	void testNameUnique(){
		def sot2 = new SupportableObjectType(name:"Vendor")
		sot2.validate()
		assertEquals 'unique', sot2.errors.name
	}
	
	void testNameCannotBeBlank(){
		sot.name = ""
		sot.validate()
		assertEquals 'blank', sot.errors.name
	}
	
	
}
