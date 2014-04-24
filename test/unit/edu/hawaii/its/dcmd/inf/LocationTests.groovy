package edu.hawaii.its.dcmd.inf

import grails.test.*

class LocationTests extends GrailsUnitTestCase {
	def location
	Integer maxSize = 45

    protected void setUp() {
        super.setUp()
		location = new Location(
			locationDescription: "Keller 102",
			updatedById: 1
		);
		mockForConstraintsTests(Location, [location])
		assertTrue "Location was not a valid object", location.validate()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testToString() {
      	assertToString location, "Keller 102"
    }

	void testNotUniqueItsId() {
		def location2 = new Location(
				locationDescription: "Keller 102",  // this matches the locationDescription we initially set up, and thus is not unique
				updatedById: 1,
		);
		assertFalse "Unique locationDescription did not fail as expected", location2.validate()
		assertEquals "unique", location2.errors.locationDescription
	}
	
	void testPropertyNotNull( property ) {
		def original = location."${property.key}"
		location."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", location.validate()
		assertEquals "nullable", location.errors."${property.key}"
		location."${property.key}" = original
	}

	void testLocationDescriptionMaxSize() {
		location.locationDescription = "a".multiply( maxSize+1 )
		assertFalse "This did not fail as expected", location.validate()
		assertEquals "maxSize", location.errors.locationDescription
	}

	void testPropertyIsNull( property ) {
		location."${property.key}" = null
		assertTrue "This did not validate as expected: ${property.key}", location.validate()
		assertEquals null, location."${property.key}"
	}
	
	// This function will loop through a list of given properties to test each applicable constraint
	void testListOfProperties() {
		
		// populate this list with all properties that should not be null
		def not_nullable = [
			'locationDescription',
//			'dateCreated',
//			'lastUpdated',
			'updatedById',
		];
		location.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}

		// populate this list with all properties that can be null
		def can_be_nullable = [
			'notesGrp',
		];
		location.properties.each { p->
			if ( can_be_nullable.contains(p.key)) {
				testPropertyIsNull( p )
			}
		}
	}
}
