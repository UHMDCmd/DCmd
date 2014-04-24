package edu.hawaii.its.dcmd.inf

import grails.test.*

class PersonTests extends GrailsUnitTestCase {

	def person

	protected void setUp() {
		super.setUp()
		person = new Person(uhNumber: "55555555", title: "dtitle", lastName: "dlastname", firstName: "dfirstName", midInit:"dmidInit")
		mockForConstraintsTests(Person, [person])
		assertTrue "Not a valid Person object", person.validate()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testUniqueUhNumber() {
		def person2 = new Person(uhNumber: "55555555", title: "atitle", lastName: "alastname", firstName: "afirstname", midInit: "amidinit")
		mockForConstraintsTests(Person, [person, person2])
		assertFalse person2.validate()
		assertEquals "unique", person2.errors.uhNumber
	}

	void testMinSizeUhNumber() {
		person.uhNumber = "9999788"   // 7 char long
		assertFalse person.validate()
		assertEquals "minSize", person.errors.uhNumber
	}

	void testBlankUhNumber() {
		person.uhNumber = ""
		assertFalse person.validate()
		assertEquals "blank", person.errors.uhNumber
	}
	
	void testMaxSizeUhNumber() {
		person.uhNumber = "0123456789012"   // 13 char long
		assertFalse person.validate()
		assertEquals "maxSize", person.errors.uhNumber
	}

	void testNumericUhNumber() {
		person.uhNumber = "xxxxxxxxxxxx"
		assertFalse person.validate()
		assertEquals "matches", person.errors.uhNumber
	}

	void testMaxSizeTitle() {
		person.title = "a" * 46
		assertFalse person.validate()
		assertEquals "maxSize", person.errors.title
	}
	void testMaxSizeLastName() {
		person.lastName = "a" * 46
		assertFalse person.validate()
		assertEquals "maxSize", person.errors.lastName
	}
	void testMaxSizeFirstName() {
		person.firstName = "a" * 46
		assertFalse person.validate()
		assertEquals "maxSize", person.errors.firstName
	}
	void testMaxSizeMidInit() {
		person.midInit = "a" * 46
		assertFalse person.validate()
		assertEquals "maxSize", person.errors.midInit
	}

	void testBlankOkMidInit() {
		person.midInit = ""
		assertTrue person.validate()
	}

	void testToString() {
		assertEquals "${person.lastName}, ${person.firstName} ${person.midInit}", person.toString()
	}
}