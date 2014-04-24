//package edu.hawaii.its.dcmd.inf
//
//import grails.test.*
//
//import org.gmock.*
//import static org.hamcrest.Matchers.*
//import edu.hawaii.its.dcmd.inf.ContractFormType
//import edu.hawaii.its.dcmd.inf.ContractService
//
//@WithGMock
//class ContractServiceTests extends GrailsUnitTestCase {
//
//	def contract, contractFormType, hawaiiTaxRate, contractStatus
//	def contractFormType2
//	def df = new java.text.SimpleDateFormat("MM/dd/yyyy", Locale.US)
//
//	protected void setUp() {
//		super.setUp()
//
//		mockLogging ContractService
//
//		contract = new Contract(
//				uhContractNo:"C110126",
//				vendorContractNo:"123",
//				uhContractTitle:"Install, Implement and Maintain a Network Appliance",
//				annualRenewalReminderMm:12,
//				annualRenewalReminderDd:30,
//				annualRenewalDeadlineMm:12,
//				annualRenewalDeadlineDd:30,
//				annualCost:0F,
//				)
//		contract.contractBeginDate = df.parse("12/31/2011")
//		contract.contractEndDate = df.parse("12/30/2012")
//
//		contractStatus = new ContractStatus(
//				status: "Retired",
//				description: "Contract has been retired and is no longer applicable.",
//				)
//		contract.contractStatus = contractStatus
//
//		hawaiiTaxRate = new HawaiiTaxRate(
//				rate: 0.0450F,
//				description: "Oahu basic tax rate"
//				)
//		contract.taxRate = hawaiiTaxRate
//
//		contractFormType = new ContractFormType (
//				form: "Form 95t",
//				description: "Price Reasonableness test"
//				)
//
//		contractFormType2 = new ContractFormType (
//				form: "Form 95t2",
//				description: "Price Reasonableness test, 2nd round"
//				)
//
//		mockDomain(ContractStatus, [contractStatus])
//		contractStatus.save(failOnError:true)
//
//		mockDomain(HawaiiTaxRate, [hawaiiTaxRate])
//		hawaiiTaxRate.save(failOnError:true)
//
//		mockDomain(ContractFormType, [contractFormType, contractFormType2])
//		contractFormType.save(failOnError:true)
//		contractFormType2.save(failOnError:true)
//
//		mockDomain(Contract, [contract])
//		contract.addToRequiredRenewalForms(contractFormType)
//		contract.save(failOnError:true)
//	}
//
//    protected void tearDown() {
//        super.tearDown()
//    }
//
//	void testGetContractAndAddNewRequiredRenewalForm() {
//		mock(Contract).static.get(instanceOf(Long)).returns(contract)
//		mock(contract).addToRequiredRenewalForms(instanceOf(ContractFormType))
//		mock(contract).save(instanceOf(Map)).returns(true)
//		play {
//			//println contractFormType.id
//			//println contractFormType2.id
//			def contractService = new ContractService()
//			assertEquals 1, contract.requiredRenewalForms.size()
//			def success = contractService.getContractAndAddNewRequiredRenewalForm(contract.id, contractFormType2.id)
//			assertEquals contract, success
//			// The following test doesn't work for gmock since the domain doesn't persist.
//			// Would need to use mock domain testing instead.
//			//assertEquals 2, success.requiredRenewalForms.size()
//		}
//	}
//
//	void testGetContractAndRemoveRequiredRenewalForm() {
//		def cs = new ContractService()
//		assertEquals 1, contract.requiredRenewalForms.size()
//		def success = cs.getContractAndRemoveRequiredRenewalForm(contract.id, contractFormType.id)
//		assertEquals contract, success
//		//assertEquals 0, success.requiredRenewalForms.size()
//	}
//
//	void testGetRequiredRenewalFormsForJSON() {
//		def contractService = new ContractService()
//		def returned = contractService.getRequiredRenewalFormsForJSON(contract.requiredRenewalForms)
//		assertEquals 1, returned.size()
//		assertEquals contractFormType.id, returned[0].id
//	}
//}
