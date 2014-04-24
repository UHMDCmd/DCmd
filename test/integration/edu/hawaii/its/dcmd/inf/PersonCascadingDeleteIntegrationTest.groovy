package edu.hawaii.its.dcmd.inf

import grails.test.*

/**
 * Test assumption that Person delete cascades to PersonMaintenanceRole and ContactInfo.
 */
class PersonCascadingDeleteIntegrationTest extends GroovyTestCase {

	def testPerson

	protected void setUp() {
		super.setUp()

		//create some support roles
		def role1 = new SupportRole(name:'sa')
		role1.save()
		def role2 = new SupportRole(name:'bo')
		role2.save()
		def role3 = new SupportRole(name:'op')
		role3.save(flush: true)

		//create a person
		testPerson = new Person(uhNumber:999999999, title:"President", lastName:"Obama", firstName:"Barrack", midInit:"H")
		testPerson.save(failOnError: true)
		
		//add support roles to person
		testPerson.addToPersonSupportRoles(new PersonSupportRole(person:testPerson, supportRole: role1))
		testPerson.addToPersonSupportRoles(new PersonSupportRole(person:testPerson, supportRole: role2))
		testPerson.addToPersonSupportRoles(new PersonSupportRole(person:testPerson, supportRole: role3))
		
		//create some contact infos
		def contactInfo1	= new ContactInfo(contactType:"E-mail", contactInfo: "scott@one.com")
		contactInfo1.save()
		def contactInfo2	= new ContactInfo(contactType:"Phone", contactInfo: "808-235-7343")
		contactInfo2.save()
		def contactInfo3	= new ContactInfo(contactType:"Fax", contactInfo: "808-247-5162")
		contactInfo3.save(flush: true)
		
		//add contact infos to person
		testPerson.addToContactInfos(contactInfo1)
		testPerson.addToContactInfos(contactInfo2)
		testPerson.addToContactInfos(contactInfo3)
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testPersonSupportRolesCascadingDelete () {
		//ensure known state before deleting person
		assertNotNull testPerson
		assertNotNull testPerson.personSupportRoles
		assertEquals(3, testPerson.personSupportRoles.size())
		testPerson.delete()
		
		//verify expected result
		assertEquals 0, PersonSupportRole.count()
	}

	void testContactInfoCascadingDelete () {
		//ensure known state before deleting person
		assertNotNull testPerson // just to be safe
		assertEquals(3, testPerson.contactInfos.size())
		testPerson.delete()
		
		//verify expected result
		assertEquals 0, ContactInfo.count()
	}
}