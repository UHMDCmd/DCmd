package edu.hawaii.its.dcmd.inf

import grails.test.*
import edu.hawaii.its.dcmd.inf.ContractStatus

class ContractTests extends GrailsUnitTestCase {

	def contract
	def contractSaved

	def df = new java.text.SimpleDateFormat("MM/dd/yyyy", Locale.US)

	protected void setUp() {
		super.setUp()
		contract = new Contract(
				uhContractNo:"C100046",
				vendorContractNo:"54546678",
				uhContractTitle:"Install, Implement and Maintain a Storage Array",
				annualRenewalReminderMm:12,
				annualRenewalReminderDd:30,
				annualRenewalDeadlineMm:12,
				annualRenewalDeadlineDd:30,
				annualCost:0F,
				)
		contract.contractBeginDate = df.parse("12/31/2011")
		contract.contractEndDate = df.parse("12/30/2012")
		contract.contractStatus = new ContractStatus(
				status: "Retired",
				description: "Contract has been retired and is no longer applicable.",
				)
		contract.taxRate = new HawaiiTaxRate(
				rate: 0.0450F,
				description: "Ohau basic tax rate"
				)
		mockForConstraintsTests(Contract, [contract])
		assertTrue "Not a valid Contract object", contract.validate()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testAllPropertiesSetNullableFalse(){

		//contract.validate()
		//println contract.errors.inspect()

		def includes = [
			'uhContractNo',
			'uhContractTitle',
			'contractBeginDate',
			'contractEndDate',
			'annualRenewalDeadlineMm',
			'annualRenewalDeadlineDd',
		]

		contract.properties.each{p->
			if(includes.contains(p.key)){
				//println contract.errors.inspect()
				assertTrue contract.validate()
				contractSaved = p.value
				contract."${p.key}" = null
				assertFalse contract.validate()
				assertEquals "nullable", contract.errors."${p.key}"
				contract."${p.key}" = contractSaved
			}
		}
	}

	void testAllRangesInvalid(){

		contractSaved = contract

		// Exceeds Max tests
		contract.annualRenewalDeadlineMm = 99I
		contract.annualRenewalDeadlineDd = 99I
		contract.annualRenewalReminderMm = 99I
		contract.annualRenewalReminderDd = 99I
		assertFalse contract.validate()

		// Below Min tests
		contract.annualRenewalDeadlineMm = 0I
		contract.annualRenewalDeadlineDd = 0I
		contract.annualRenewalReminderMm = 0I
		contract.annualRenewalReminderDd = 0I
		assertFalse contract.validate()

		contract = contractSaved
	}
}
