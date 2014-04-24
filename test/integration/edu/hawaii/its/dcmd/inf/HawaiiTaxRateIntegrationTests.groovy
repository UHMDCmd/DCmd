package edu.hawaii.its.dcmd.inf

import grails.test.*

class HawaiiTaxRateIntegrationTests extends GroovyTestCase {

	def hawaiiTaxRate
	def contractStatus
	def contract1
	def contract2
	def df = new java.text.SimpleDateFormat("MM/dd/yyyy", Locale.US)

	protected void setUp() {
		super.setUp()

		contractStatus = new ContractStatus(
			status: "Retired",
			description: "Contract has been retired and is no longer applicable.",
			)
		hawaiiTaxRate = new HawaiiTaxRate(
				rate: 0.04120F
				)
		contract1 = new Contract(
				uhContractNo:"C090101",
				vendorContractNo:"54546678",
				uhContractTitle:"Install, Implement and Maintain Application Switch",
				annualRenewalDeadlineMm:12,
				annualRenewalDeadlineDd:30,
				annualRenewalReminderMm:12,
				annualRenewalReminderDd:30,
				annualCost:0F,
				)
		contract1.contractBeginDate = df.parse("12/31/2011")
		contract1.contractEndDate = df.parse("12/30/2012")
		contract1.contractStatus = contractStatus
		contract1.taxRate = hawaiiTaxRate

		contract2 = new Contract(
				uhContractNo:"C070101",
				vendorContractNo:"54546678",
				uhContractTitle:"Install, Implement and Maintain an IBM Mainframe",
				annualRenewalDeadlineMm:12,
				annualRenewalDeadlineDd:30,
				annualRenewalReminderMm:12,
				annualRenewalReminderDd:30,
				annualCost:0F,
				)
		contract2.contractStatus = contractStatus
		contract2.contractBeginDate = df.parse("12/31/2011")
		contract2.contractEndDate = df.parse("12/30/2012")
		contract2.taxRate = hawaiiTaxRate

		println hawaiiTaxRate.validate()
		println hawaiiTaxRate.errors.inspect()

		hawaiiTaxRate.save(failOnError: true, flush: true)
		contractStatus.save(failOnError: true, flush: true)
		contract1.save(failOnError: true, flush: true)
		contract2.save(failOnError: true, flush: true)

		// Verify that 1 HawaiiTaxRate and 2 Contract(s) exist.
		assertEquals 1, HawaiiTaxRate.count()
		assertEquals 1, ContractStatus.count()
		assertEquals 2, Contract.count()
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testInappropriateTaxRateDelete(){
		shouldFail{
			hawaiiTaxRate.delete(flush: true)
		}
		assertEquals 2, Contract.count()
	}
	
	void testInappropriateContractStatusDelete(){
		shouldFail{
			contractStatus.delete(flush: true)
		}
		assertEquals 2, Contract.count()
	}
}
