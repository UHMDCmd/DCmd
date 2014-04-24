package edu.hawaii.its.dcmd.inf

import grails.test.*

class PersonSupportRoleControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {

    }
	
	void testCanCreateFromParams(){
		
		def ps  = ["person.id":"1", "person":["id":"1"], "supportableObjectType.id":"1", "supportableObjectType":["id":"1"], "supportRole.id":"1", "supportRole":["id":"1"], "create":"Create", "action":"save", "controller":"personSupportRole"]
		
		def per = new Person(lastName:'last', firstName:'first', uhNumber:'232323233',id:1, midInit:'d', title:'title')
		mockDomain(Person,[per])
		per.save(failOnError:true)

		def sup = new SupportRole(id:1, name:'role')
		mockDomain(SupportRole, [sup])
		sup.save(failOnError:true)
		
		def sot = new SupportableObjectType(id:1, name:'type')
		mockDomain(SupportableObjectType, [sot])
		sot.save(failOnError:true)

		def psrp = new PersonSupportRole()
		mockDomain(PersonSupportRole, [psrp])
		println psrp.properties
		psrp.properties = ps
		println psrp.person.properties
		psrp.save()
		assertFalse psrp.hasErrors()

	}
}
