package edu.hawaii.its.dcmd.inf

import grails.test.*

class CostsTests extends GrailsUnitTestCase {

	def costs
	def costsSaved
	def madeBy
	
	protected void setUp() {
		super.setUp()
		madeBy = new Manufacturer( name: "Company", code: "Code", phone: "555-5555", updatedById: 1)
		costs = new Costs(
				renewalFiscalYear:20111231I,
				renewalCost:100.00F,
				listPrice:100.00F,
				uhDiscount:0.20F,
				isProrated:true,
				includesTax:true,
				taxRate:0.04712F,
				)
		costs.asset = new Asset(
				itsId:"ITS_ID",
				manufacturer: madeBy,
				)		
		mockForConstraintsTests(Costs, [costs])
        assertTrue "Not a valid Costs object", costs.validate()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testAllPropertiesSetNullableFalse(){
		
		costs.validate()
		println costs.errors.inspect()
		
		def includes = [
			'renewalFiscalYear',
			'renewalCost',
			'uhDiscount',
			'isProrated',
			'uhDiscount',
			'taxRate',
		]

		costs.properties.each{p->
			if(includes.contains(p.key)){
				assertTrue costs.validate()
				costsSaved = p.value
				costs."${p.key}" = null
				assertFalse costs.validate()
				assertEquals "nullable", costs.errors."${p.key}"
				costs."${p.key}" = costsSaved
			}
		}
	}

	void testAllRangesInvalid(){

		costsSaved = costs

		// Exceeds Max tests
		costs.renewalFiscalYear = 4000I
		costs.uhDiscount = 0.90F
		costs.taxRate = 0.50F
		assertFalse costs.validate()

		// Below Min tests
		costs.renewalCost = -1
		costs.renewalFiscalYear = 1000I
		costs.uhDiscount = 0.10F
		costs.taxRate = -0.10F
		assertFalse costs.validate()

		costs = costsSaved
	}
}
