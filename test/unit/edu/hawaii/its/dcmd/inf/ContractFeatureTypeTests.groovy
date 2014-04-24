package edu.hawaii.its.dcmd.inf

import grails.test.*

class ContractFeatureTypeTests extends GrailsUnitTestCase {

	def contractFeatureType

	protected void setUp() {
		super.setUp()

		contractFeatureType = new ContractFeatureType (
				type:"Software Maintenance",
				description:"Contract provides for software maintenance. "
				)
		mockForConstraintsTests(ContractFeatureType, [contractFeatureType])
		assertTrue "ContractFeatureType was not a valid object", contractFeatureType.validate()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testToString() {
		assertToString contractFeatureType, contractFeatureType.type
	}

	// This function will loop through a list of given properties to test each applicable constraint
	void testListOfProperties() {

		// populate this list with all properties that should not be null
		def not_nullable = [
			'type',
			'description',
		];
		contractFeatureType.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}
	}

	void testPropertyNotNull( property ) {
		def original = property.value
		contractFeatureType."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", contractFeatureType.validate()
		assertEquals "nullable", contractFeatureType.errors."${property.key}"
		contractFeatureType."${property.key}" = original
	}
	
	void testNotUniqueStatus() {
		def cft = new ContractFeatureType (
			type: contractFeatureType.type,
			description: "test description"
			)
		assertFalse "duplicate type accepted, unique types are required", cft.validate()
		//assertEquals "unique", cft.errors.status
		
	}
}
