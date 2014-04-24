package edu.hawaii.its.dcmd.inf

import grails.test.*

class PersonSupportRoleTests extends GrailsUnitTestCase {
	
	def person,supportableObjectType,supportRole,psr
	
    protected void setUp() {
        super.setUp()
		person = new Person()
		supportRole =  new SupportRole()
		supportableObjectType = new SupportableObjectType(name:"type")
		
		mockDomain(Person, [person])
		mockDomain(SupportRole, [supportRole])
		mockDomain(SupportableObjectType,[supportableObjectType])
		
		psr = new PersonSupportRole(person:person, supportRole:supportRole, supportableObjectType:supportableObjectType)
		mockForConstraintsTests(PersonSupportRole, [psr])
		
		assertTrue psr.validate()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testPersonCannotBeNull() {
		psr.person = null
		psr.validate()
		assertEquals 'nullable', psr.errors.person		
    }
	
	void testSupportRoleCannotBeNull() {
		psr.supportRole = null
		psr.validate()
		assertEquals 'nullable', psr.errors.supportRole
	}
	
	void testSupportableObjectTypeCannotBeNull() {
		psr.supportableObjectType = null
		psr.validate()
		assertEquals 'nullable', psr.errors.supportableObjectType
	}
	
	void testPersonIsUniqueForSupportRoleAndSupportableObjectType(){
		def psr2 = new PersonSupportRole(person:person, supportRole:supportRole, supportableObjectType:supportableObjectType)
		mockForConstraintsTests(PersonSupportRole, [psr,psr2])
		psr2.validate()
		assertEquals 'unique', psr2.errors.person
	}
	

}
