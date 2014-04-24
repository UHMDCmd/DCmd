package edu.hawaii.its.dcmd.inf

import grails.test.*

class PersonContactInfoContactTypeIntegrationTests extends GroovyTestCase {

	def testPerson

	protected void setUp() {
		super.setUp()
		// Create a person
		testPerson = new Person(uhNumber:999999999, title:"President", lastName:"Obama", firstName:"Barrack", midInit:"H")
		testPerson.save(failOnError: true, flush:true)

		// Create contactTypes
		def contactTypeEma = new ContactType(name: "Email", description: "Electronic Mail")
		contactTypeEma.save(failOnError: true, flush: true)
		def contactTypePho = new ContactType(name: "Phone", description: "Phone")
		contactTypePho.save(failOnError: true, flush: true)
		def contactTypeFax = new ContactType(name: "Fax", description: "Facsimile")
		contactTypeFax.save(failOnError: true, flush: true)
		def contactTypeMob = new ContactType(name: "Mobile", description: "Cellular Phone or Pager")
		contactTypeMob.save(failOnError: true, flush: true)

		// Create some contact infos
		testPerson.addToContactInfos(new ContactInfo(contactType: contactTypeEma, contactInfo: "scott@hesarealnowhereman.com"))
		testPerson.addToContactInfos(new ContactInfo(contactType: contactTypePho, contactInfo: "808-235-7343"))
		testPerson.addToContactInfos(new ContactInfo(contactType: contactTypeFax, contactInfo: "808-247-5362"))

	}

	protected void tearDown() {
		super.tearDown()
	}

	// Test reference model.
	void testReferenceModel() {
		assertEquals 1, Person.count()		// Got person
		assertEquals 4, ContactType.count()	// Got 3 contact types
		assertEquals 3, ContactInfo.count()	// Got 3 contact infos
		assertNotNull testPerson			// person is not null
		assertNotNull testPerson.contactInfos	// Contact infoS field in the person is not null
		testPerson.contactInfos.each { assertNotNull it.contactType }	// each contactInfo is contactInfos list is not null
	}

	// Test that we can delete person w/o deleting contactTypes.
	void testCanDeletePersonWithoutDeletingContactTypes() {
		testPerson.delete(flush: true)
		assertEquals 0, Person.count()		//person should be gone
		assertEquals 0, ContactInfo.count()	//contact info should be gone (cascaded)
		assertEquals 4, ContactType.count()	//contact type validation table entries should NOT be gone.
	}

	// Test that we can delete contact info w/o deleting contactTypes.
	void testCanDeleteContactInfoWithoutDeletingContactTypes() {

		// Make sure the 1st contactInfo record is the one we expect.
		assertEquals "Email", testPerson.contactInfos[0].contactType.name
		assertEquals "scott@hesarealnowhereman.com", testPerson.contactInfos[0].contactInfo

		// Remove it from testPerson.
		testPerson.removeFromContactInfos(testPerson.contactInfos[0])

		// Make sure the new 1st record is the one we expect after delete.
		assertEquals "Phone", testPerson.contactInfos[0].contactType.name
		assertEquals "808-235-7343", testPerson.contactInfos[0].contactInfo

		// Check that all 4 contactTypes are still around.
		assertEquals 4, ContactType.count()
	}

}
