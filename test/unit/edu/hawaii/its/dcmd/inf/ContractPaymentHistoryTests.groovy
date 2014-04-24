package edu.hawaii.its.dcmd.inf

import grails.test.*

class ContractPaymentHistoryTests extends GrailsUnitTestCase {

	def history
	def df = new java.text.SimpleDateFormat("MM/dd/yyyy", Locale.US)

	protected void setUp() {
		super.setUp()
		history = new ContractPaymentHistory(
				contractMod:'mod 2',
				amountEncumbered:24000.00F,
				)
		def contract = new Contract(
				uhContractNo:"C100046",
				vendorContractNo:"54546678",
				uhContractTitle:"Install, Implement and Maintain a Storage Array",
				annualRenewalReminderMm:12,
				annualRenewalReminderDd:30,
				annualRenewalDeadlineMm:12,
				annualRenewalDeadlineDd:30,
				annualCost:0F,
				)
		history.periodBeginDate = df.parse('12/31/2011')
		history.periodEndDate = df.parse('12/30/2012')
		contract.contractStatus = new ContractStatus(
				status: "Retired",
				description: "Contract has been retired and is no longer applicable.",
				)
		history.contract = contract
		//		history.validate()
		//		println history.errors.inspect()
		mockForConstraintsTests(ContractPaymentHistory, [history])
		assertTrue "Not a valid ContractPaymentHistory object", history.validate()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testListOfProperties() {

		// Test properties intended to be null-able.
		def not_nullable = [
			'periodBeginDate',
			'periodEndDate',
			'amountEncumbered',
		];
		history.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}

		// Test properties intended to be scaled to two decimal places.
		def decimal_two = ['renewalCost',];
		history.properties.each { p->
			if ( decimal_two.contains(p.key)) {
				testPropertyTwoDecimalPlaces( p )
			}
		}
	}

	/**
	 * Test a property to ensure that it is null-able.
	 * @param property
	 */
	void testPropertyNotNull( property ) {
		def original = property.value
		history."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", history.validate()
		assertEquals "nullable", history.errors."${property.key}"
		history."${property.key}" = original
	}

	/**
	 * Test a property to ensure that it is scalable to two decimal places.
	 * @param property
	 */
	void testPropertyTwoDecimalPlaces( property ) {
		history."${property.key}" = 0.145
		history.validate()
		assertEquals 0.15f, history."${property.key}"
	}
}
