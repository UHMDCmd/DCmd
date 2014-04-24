package edu.hawaii.its.dcmd.inf

import grails.test.*

class UnitTypeTests extends GrailsUnitTestCase {
	
	def uType
	Integer max_size = 45
	
    protected void setUp() {
        super.setUp()
		uType = new UnitType ( unit:"GB", unitDescription:"Gigabyte", updatedById: 1 )
 		mockForConstraintsTests(UnitType, [uType])
		assertTrue "Unit was not a valid object", uType.validate()
   }

    protected void tearDown() {
        super.tearDown()
    }

	void testToString() {
		assertToString uType.toString(), "GB"
	}
	
	void testNotUnique() {
		def uType2 = new UnitType( unit: "GB", unitDescription: "another Gigabyte type" )
		assertFalse "This should fail: unit is not unique", uType2.validate()
		assertEquals "unique", uType2.errors.unit

	}
	void testPropertyNotNull( property ) {
		def original = property.value
		uType."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", uType.validate()
		assertEquals "nullable", uType.errors."${property.key}"
		uType."${property.key}" = original
	}

	void testPropertyNotBlank( property ) {
		def original = property.value
		uType."${property.key}" = ""
		assertFalse "This did not fail as expected: ${property.key}", uType.validate()
		assertEquals "blank", uType.errors."${property.key}"
		uType."${property.key}" = original
	}

	void testPropertyMaxSize( property ) {
		uType."${property.key}" = "a" * ( max_size + 1 )
		assertFalse "This did not fail as expected", uType.validate()
		assertEquals "maxSize", uType.errors."${property.key}"
	}

	// This function will loop through a list of given properties to test each applicable constraint
	void testListOfProperties() {

		// populate this list with all properties that should not be null
		def not_nullable = [
			'unit', 
			'unitDescription', 
		];
		uType.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}

		// populate this list with all properties that should not be blank strings
		def not_blank = [
			'unit', 
			'unitDescription', 
		];
		uType.properties.each { p->
			if ( not_blank.contains(p.key)) {
				testPropertyNotBlank( p )
			}
		}
		
		// populate this list with all properties that should not exceed length of maxSize (45)
		def not_max_size = [
			'unit', 
			'unitDescription', 
		];
		uType.properties.each { p->
			if ( not_max_size.contains(p.key)) {
				testPropertyMaxSize( p )
			}
		}
	}

}
