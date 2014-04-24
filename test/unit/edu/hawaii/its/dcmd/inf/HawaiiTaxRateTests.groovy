package edu.hawaii.its.dcmd.inf

import grails.test.*

class HawaiiTaxRateTests extends GrailsUnitTestCase {
	
	def hawaiiTaxRate

    protected void setUp() {
        super.setUp()
		
		hawaiiTaxRate = new HawaiiTaxRate(
			rate: 0.0450F,
			description: "Ohau basic tax rate"
			) 
		mockForConstraintsTests(HawaiiTaxRate, [hawaiiTaxRate])
		assertTrue "Not a valid HawaiiTaxRate object", hawaiiTaxRate.validate()
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testNotUniqueRate() {
		def testTaxRate = new HawaiiTaxRate(
				rate: hawaiiTaxRate.rate,  // this matches the taxRate we initially set up, and thus is not unique
		);
		assertFalse "duplicate rate accepted, unique rates are required", testTaxRate.validate()
		assertEquals "unique", testTaxRate.errors.rate
	}

	void testNullableDescription() {
		hawaiiTaxRate.description = null
		assertTrue "null description must be allowed", hawaiiTaxRate.validate()
		assertEquals null, hawaiiTaxRate.description
	}

	void testNotNullRate() {
		hawaiiTaxRate.rate = null
		assertFalse "null rate accepted but must not be allowed", hawaiiTaxRate.validate()
		assertEquals "nullable", hawaiiTaxRate.errors.rate
	}
}
