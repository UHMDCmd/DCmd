package edu.hawaii.its.dcmd.inf

import grails.test.*


class ContactInfoTests extends GrailsUnitTestCase {

	def person, contactInfo, contactType

	protected void setUp() {
		super.setUp()
		person = new Person()
		contactType = new ContactType(name: "Mobile", description: "Celluar Phone or Pager")
		mockDomain(Person, [person])
		mockDomain(ContactType, [contactType])
 		contactInfo = new ContactInfo(person: person, contactType: contactType, contactInfo: "808-123-1234")
		mockForConstraintsTests(ContactInfo, [contactInfo])
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testReferenceCase() {
		assertTrue contactInfo.validate()
	}
	
	void testNoNullContactType() {
		contactInfo.contactType = null
		assertFalse contactInfo.validate()
		assertEquals "nullable", contactInfo.errors.contactType
	}

	void testNoNullPerson() {
		contactInfo.person = null
		assertFalse contactInfo.validate()
		assertEquals "nullable", contactInfo.errors.person
	}

	void testNoNullContactInfo() {
		contactInfo.contactInfo = null
		assertFalse contactInfo.validate()
		assertEquals "nullable", contactInfo.errors.contactInfo
	}
	void testMaxContactInfo() {
		contactInfo.contactInfo = "a".multiply(46)
		assertFalse contactInfo.validate()
		assertEquals "size", contactInfo.errors.contactInfo
	}
	
	void testToString() {
		assertEquals("Mobile: 808-123-1234", contactInfo.toString())
	}

}
