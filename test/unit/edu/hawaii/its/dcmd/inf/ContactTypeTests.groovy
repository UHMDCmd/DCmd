package edu.hawaii.its.dcmd.inf

import grails.test.*

class ContactTypeTests extends GrailsUnitTestCase {

	def contactType

	protected void setUp() {
		super.setUp()
		contactType = new ContactType(name: "Mobile", description: "Cellular Phone or Pager")
		mockForConstraintsTests(ContactType, [contactType])
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testReferenceCase() {
		assertTrue contactType.validate()
	}

	void testNoBlankName() {
		contactType.name = ""
		assertFalse contactType.validate()
		assertEquals "blank", contactType.errors.name
	}

	void testMaxName() {
		contactType.name = "a" * 46
		assertFalse contactType.validate()
		assertEquals "size", contactType.errors.name
	}

	void testUniqueNameContactType() {
		def contactType2 = new ContactType(name: "Mobile", description: "zzzzz")
		mockForConstraintsTests(ContactType, [contactType, contactType2])
		assertFalse contactType2.validate()
		assertEquals "unique", contactType2.errors.name
	}

	void testNoBlankDescription() {
		contactType.description = ""
		assertFalse contactType.validate()
		assertEquals "blank", contactType.errors.description
	}

	void testMaxDescription() {
		contactType.description = "a" * 46
		assertFalse contactType.validate()
		assertEquals "size", contactType.errors.description
	}

	void testToString() {
		assertEquals("Mobile", contactType.toString())
	}
}
