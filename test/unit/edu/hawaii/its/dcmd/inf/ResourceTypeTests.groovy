package edu.hawaii.its.dcmd.inf

import grails.test.*

class ResourceTypeTests extends GrailsUnitTestCase {
	
	def rType
	Integer maxSize = 45
	
    protected void setUp() {
        super.setUp()
		rType = new ResourceType ( resourceType:"RAM", resourceDescription:"Random Access Memory", updatedById: 1 )
 		mockForConstraintsTests(ResourceType, [rType])
		assertTrue "rType was not a valid ResourceType object", rType.validate()
   }

    protected void tearDown() {
        super.tearDown()
    }

	void testToString() {
		assertToString rType.toString(), "RAM"
	}
	
	void testNotUnique() {
		def rType2 = new ResourceType ( resourceType: "RAM", resourceDescription: "Random Access Memory" )
		assertFalse "This should fail: resourceType is not unique", rType2.validate()
		assertEquals "unique", rType2.errors.resourceType

	}
	void testPropertyNotNull( property ) {
		def original = property.value
		rType."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", rType.validate()
		assertEquals "nullable", rType.errors."${property.key}"
		rType."${property.key}" = original
	}

	void testPropertyNotBlank( property ) {
		def original = property.value
		rType."${property.key}" = ""
		assertFalse "This did not fail as expected: ${property.key}", rType.validate()
		assertEquals "blank", rType.errors."${property.key}"
		rType."${property.key}" = original
	}

	void testPropertyMaxSize( property ) {
		rType."${property.key}" = "a" * ( maxSize + 1 )
		assertFalse "This did not fail as expected", rType.validate()
		assertEquals "maxSize", rType.errors."${property.key}"
	}

	// This function will loop through a list of given properties to test each applicable constraint
	void testListOfProperties() {

		// populate this list with all properties that should not be null
		def not_nullable = [
			'resourceType', 
			'resourceDescription', 
		];
		rType.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}

		// populate this list with all properties that should not be blank strings
		def not_blank = [
			'resourceType', 
			'resourceDescription', 
		];
		rType.properties.each { p->
			if ( not_blank.contains(p.key)) {
				testPropertyNotBlank( p )
			}
		}
		
		// populate this list with all properties that should not exceed length of maxSize (45)
		def not_max_size = [
			'resourceType', 
			'resourceDescription', 
		];
		rType.properties.each { p->
			if ( not_max_size.contains(p.key)) {
				testPropertyMaxSize( p )
			}
		}
	}

}
