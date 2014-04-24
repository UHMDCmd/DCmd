package edu.hawaii.its.dcmd.inf

import grails.test.*

class AssetTypeTests extends GrailsUnitTestCase {
	def assetType
	Integer max_size = 45
	
    protected void setUp() {
        super.setUp()
		assetType = new AssetType (
			name: "Host",
			description: "A physical device running services"
		)
 		mockForConstraintsTests(AssetType, [assetType])
		assertTrue "AssetType was not a valid object", assetType.validate()
   }

    protected void tearDown() {
        super.tearDown()
    }

	void testToString() {
		assertToString assetType.name, "Host"
	}

	void testPropertyNotNull( property ) {
		def original = property.value
		assetType."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", assetType.validate()
		assertEquals "nullable", assetType.errors."${property.key}"
		assetType."${property.key}" = original
	}

	void testPropertyNotBlank( property ) {
		def original = property.value
		assetType."${property.key}" = ""
		assertFalse "This did not fail as expected: ${property.key}", assetType.validate()
		assertEquals "blank", assetType.errors."${property.key}"
		assetType."${property.key}" = original
	}

	void testPropertyMaxSize( property ) {
		assetType."${property.key}" = "a" * ( max_size + 1 )
		assertFalse "This did not fail as expected", assetType.validate()
		assertEquals "maxSize", assetType.errors."${property.key}"
	}

	// This function will loop through a list of given properties to test each applicable constraint
	void testListOfProperties() {

		// populate this list with all properties that should not be null
		def not_nullable = [
			'assetType', 
			'assetDescription'
		];
		assetType.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}

		// populate this list with all properties that should not be blank strings
		def not_blank = [
			'assetType', 
			'assetDescription', 
		];
		assetType.properties.each { p->
			if ( not_blank.contains(p.key)) {
				testPropertyNotBlank( p )
			}
		}
		
		// populate this list with all properties that should not exceed length of maxSize (45)
		def not_max_size = [
			'assetType', 
			'assetDescription', 
		];
		assetType.properties.each { p->
			if ( not_max_size.contains(p.key)) {
				testPropertyMaxSize( p )
			}
		}
	}

}
