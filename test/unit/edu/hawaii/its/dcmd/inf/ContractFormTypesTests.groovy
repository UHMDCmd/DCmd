package edu.hawaii.its.dcmd.inf

import grails.test.*

class ContractFormTypesTests extends GrailsUnitTestCase {

	def contractFormType

	protected void setUp() {
		super.setUp()

		contractFormType = new ContractFormType (
				form: "Form 95",
				description: "Price Reasonableness"
				)

		mockForConstraintsTests(ContractFormType, [contractFormType])
		assertTrue "ContractFormType was not a valid object", contractFormType.validate()	}

	protected void tearDown() {
		super.tearDown()
	}

	void testToString() {
		assertToString contractFormType, contractFormType.form + " - " + contractFormType.description
	}

	// This function will loop through a list of given properties to test each applicable constraint
	void testListOfProperties() {

		// populate this list with all properties that should not be null
		def not_nullable = [
			'form',
			'description',
		];
		contractFormType.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}
	}

	void testPropertyNotNull( property ) {
		def original = property.value
		contractFormType."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", contractFormType.validate()
		assertEquals "nullable", contractFormType.errors."${property.key}"
		contractFormType."${property.key}" = original
	}
	
	void testNotUniqueStatus() {
		def cft = new ContractFormType (
			form: contractFormType.form,
			description: "test description"
			)
		assertFalse "duplicate form accepted, unique forms are required", cft.validate()
		//assertEquals "unique", cft.errors.status
		
	}
}
