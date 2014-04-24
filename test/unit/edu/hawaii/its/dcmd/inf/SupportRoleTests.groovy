package edu.hawaii.its.dcmd.inf

import grails.test.*

class SupportRoleTests extends GrailsUnitTestCase {
	
	def role
	
   protected void setUp() {
        super.setUp()
		role = new SupportRole(name:'sa')
		mockForConstraintsTests(SupportRole, [role])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNoNullRole() {
		role.name = null
		assertFalse role.validate()
		assertEquals "nullable", role.errors.name
    }
	
	void testNoBlankRole(){
		role.name = ""
		assertFalse role.validate()
		assertEquals "blank", role.errors.name
	}
	
	void testMaxLengthRole(){
		role.name = "a".multiply(46)
		assertFalse role.validate()
		assertEquals "size", role.errors.name

	}
}
