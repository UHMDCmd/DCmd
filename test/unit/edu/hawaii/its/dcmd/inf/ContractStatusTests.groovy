package edu.hawaii.its.dcmd.inf

import grails.test.*

class ContractStatusTests extends GrailsUnitTestCase {

	def contractStatus

	protected void setUp() {
		super.setUp()

		contractStatus = new ContractStatus(
				status: "Active",
				description: "Contract is currently active."
				)
		mockForConstraintsTests(ContractStatus, [contractStatus])
		assertTrue "Not a valid ContractStatus", contractStatus.validate()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testNotNullDescription() {
		contractStatus.description = null
		assertFalse "null rate accepted but must not be allowed", contractStatus.validate()
		assertEquals "nullable", contractStatus.errors.description
	}

	void testNotNullStatus() {
		contractStatus.status = null
		assertFalse "null rate accepted but must not be allowed", contractStatus.validate()
		assertEquals "nullable", contractStatus.errors.status
	}

	void testNotUniqueStatus() {
		def cs = new ContractStatus(
				status: contractStatus.status,  // this matches the status we initially set up, and thus is not unique
				);
		assertFalse "duplicate status accepted, unique statuses are required", cs.validate()
		assertEquals "unique", cs.errors.status
	}
}