package edu.hawaii.its.dcmd.inf

import grails.test.*

class SupportableObjectTests extends GrailsUnitTestCase {
	def so
    protected void setUp() {
        super.setUp()
		so = new SupportableObject(type:"type")
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void testTypeCannotBeNull(){
		mockForConstraintsTests(SupportableObject, [so])
		so.type = null
		so.validate()
		assertEquals "nullable", so.errors.type
	}
}
