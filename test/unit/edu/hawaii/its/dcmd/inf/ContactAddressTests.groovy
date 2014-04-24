package edu.hawaii.its.dcmd.inf

import grails.test.*

class ContactAddressTests extends GrailsUnitTestCase {

	def contactAddress
	Integer maxSize = 45

	protected void setUp() {
		super.setUp()
		contactAddress = new ContactAddress(
				addressLine1: "University of Hawaii, ITS",
				addressLine2: "Keller Hall, Room 219",
				city: "Honolulu",
				state: "HI",
				zip: "96822"
				)
		contactAddress.person = new Person(
				uhNumber: 1,
				title: "dtitle",
				lastName: "dlastname",
				firstName: "dfirstName",
				midInit:"dmidInit"
				)
		mockForConstraintsTests(ContactAddress, [contactAddress])
		//println contactAddress.validate()
		//println contactAddress.errors.inspect()
		assertTrue "Not a valid contactAddress object", contactAddress.validate()
	}

	protected void tearDown() {
		super.tearDown()
	}

	// Loop through a list of properties to test each applicable constraint
	void testListOfProperties() {

		// populate this list with all properties that should not be blank strings
		def not_blank = [
			'addressLine1',
			'city',
			'state',
		];
		contactAddress.properties.each { p->
			if (not_blank.contains(p.key)) {
				testPropertyNotBlank( p )
			}
		}

		// populate this list with all properties that should not be null
		def not_nullable = [
			'addressLine1',
			'city',
			'state',
		];
		contactAddress.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}

		// populate this list with all properties that can be null
		def can_be_nullable = [
			'addressLine2',
			'zip',
		];
		contactAddress.properties.each { p->
			if ( can_be_nullable.contains(p.key)) {
				testPropertyIsNull( p )
			}
		}

		// populate this list with all properties that should not exceed length of maxSize (45)
		def not_max_size = [
			'addressLine1',
			'addressLine2',
			'city',
			'state',
			'zip',
		];
		contactAddress.properties.each { p->
			if ( not_max_size.contains(p.key)) {
				testPropertyMaxSize( p )
			}
		}
	}

	void testPropertyIsNull( property ) {
		contactAddress."${property.key}" = null
		assertTrue "This did not validate as expected: ${property.key}", contactAddress.validate()
		assertEquals null, contactAddress."${property.key}"
	}

	void testPropertyMaxSize( property ) {
		contactAddress."${property.key}" = "a" * ( maxSize + 1 )
		assertFalse "This did not fail as expected", contactAddress.validate()
		assertEquals "maxSize", contactAddress.errors."${property.key}"
	}

	void testPropertyNotBlank( property ) {
		def original = property.value
		contactAddress."${property.key}" = ""
		assertFalse "This did not fail as expected: ${property.key}", contactAddress.validate()
		assertEquals "blank", contactAddress.errors."${property.key}"
		contactAddress."${property.key}" = original
	}

	void testPropertyNotNull( property ) {
		def original = property.value
		contactAddress."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", contactAddress.validate()
		assertEquals "nullable", contactAddress.errors."${property.key}"
		contactAddress."${property.key}" = original
	}

	void testToString() {
		contactAddress.addressLine1 = "A"
		contactAddress.addressLine2 = null
		contactAddress.city = "C"
		contactAddress.state = "S"
		contactAddress.zip = null
		assertToString contactAddress, "A, C, S"
	}


}
