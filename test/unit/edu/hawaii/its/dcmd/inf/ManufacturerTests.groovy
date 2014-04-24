package edu.hawaii.its.dcmd.inf

import grails.test.*

class ManufacturerTests extends GrailsUnitTestCase {
	
	def manufacturer
	
    protected void setUp() {
        super.setUp()
		manufacturer = new Manufacturer(
			name: "IBM",
			code: "some code",
			phone: "555-1234",
			updatedById: 1
			)
		mockForConstraintsTests(Manufacturer, [manufacturer])
		assertTrue "Manufacturer was not a valid object", manufacturer.validate()
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testPropertyNotNull( property ) {
		def original = property.value
		manufacturer."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", manufacturer.validate()
		assertEquals "nullable", manufacturer.errors."${property.key}"
		manufacturer."${property.key}" = original
	}

	void testPropertyNotBlank( property ) {
		def original = property.value
		manufacturer."${property.key}" = ""
		assertFalse "This did not fail as expected: ${property.key}", manufacturer.validate()
		assertEquals "blank", manufacturer.errors."${property.key}"
		manufacturer."${property.key}" = original
	}
 
	void testListOfProperties() {

		// populate this list with all properties that should not be null
		def not_nullable = [
			'name', 
			'phone',
//			'dateCreated',
//			'lastUpdated',
			'updatedById',
		];
		manufacturer.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}

		// populate this list with all properties that should not be blank strings
		def not_blank = [
			'name', 
			'phone',
		];
		manufacturer.properties.each { p->
			if ( not_blank.contains(p.key)) {
				testPropertyNotBlank( p )
			}
		}
	}
   
	void testToString() {
		assertEquals manufacturer.name, manufacturer.toString()
    }
}
