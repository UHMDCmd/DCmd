package edu.hawaii.its.dcmd.inf

import grails.test.*

//import org.gmock.*
//
//import static org.hamcrest.Matchers.*
//
//import edu.hawaii.its.dcmd.inf.ContractController
//
//@WithGMock
class ContractControllerTests extends ControllerUnitTestCase {

	def contract, contractFormType, contractFormType2

	def df = new java.text.SimpleDateFormat("MM/dd/yyyy", Locale.US)

	protected void setUp() {
		super.setUp()

		mockLogging ContractController

		def mockContractId

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
				description: "Oahu basic tax rate"
				)

		contractFormType = new ContractFormType (
				form: "Form 65",
				description: "test form 65"
				)
		contractFormType2 = new ContractFormType (
				form: "Form 95",
				description: "test form 95"
				)
		
		mockDomain(Contract, [contract])
		mockDomain(ContractFormType, [contractFormType, contractFormType2])
		
		// The form types are saved, but not added to the Contract.
		// Save that for testing.
		contract.save(flush:true, failOnError:true)
		contractFormType.save(flush:true, failOnError:true)
		contractFormType2.save(flush:true, failOnError:true)
		contract.version = "0"
		//contract.save(flush:true, failOnError:true)
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testUpdateWithTwoRequiredRenewalForms() {
		
		mockParams['mockParams[\'controller\']'] = "contract"
		mockParams['_action_update'] = "Update"
		mockParams['id'] = contract.id
		mockParams['version'] = "99999"
		
		mockParams['annualCost'] = "0"
		mockParams['annualRenewalDeadlineDd'] = "30"
		mockParams['annualRenewalDeadlineMm'] = "12"
		mockParams['annualRenewalReminderDd'] = "30"
		mockParams['annualRenewalReminderMm'] = "12"
		mockParams['contractBeginDate_day'] = "31"
		mockParams['contractBeginDate_hour'] = "0"
		mockParams['contractBeginDate_minute'] = "0"
		mockParams['contractBeginDate_month'] = "12"
		mockParams['contractBeginDate_value'] = "12/31/2011"
		mockParams['contractBeginDate_year'] = "2011"
		mockParams['contractBeginDate'] = "12/31/2011"
		mockParams['contractEndDate_day'] = "30"
		mockParams['contractEndDate_hour'] = "0"
		mockParams['contractEndDate_minute'] = "0"
		mockParams['contractEndDate_value'] = "12/30/2012"
		mockParams['contractEndDate_year'] = "2012"
		mockParams['contractEndDate'] = "12/31/2011"
		mockParams['contractExtensibleEndDate_day'] = ""
		mockParams['contractExtensibleEndDate_hour'] = ""
		mockParams['contractExtensibleEndDate_minute'] = ""
		mockParams['contractExtensibleEndDate_month'] = ""
		mockParams['contractExtensibleEndDate_value'] = ""
		mockParams['contractExtensibleEndDate_year'] = ""
		mockParams['contractExtensibleEndDate'] = ""
		mockParams['contractStatus.id":"2", "contractStatus'] = ["id":"2"]
		mockParams['taxRate.id":"1", "taxRate'] = ["id":"1"]
		mockParams['uhContractNo'] = "AAE324"
		mockParams['uhContractTitle'] = "Build a test app"
		mockParams['vendorContractNo'] = "54AAAF6678"
		mockParams['type'] = "contract"
		
		mockParams['requiredRenewalFormList[0]'] = ["contractFormType.id":1L, "contractFormType":[id:1L], "new":true, "deleted":false]
		mockParams['requiredRenewalFormsList[0].contractFormType'] = 1L
		mockParams['requiredRenewalFormsList[0].id'] = ""
		mockParams['requiredRenewalFormsList[0].new'] = true
		mockParams['requiredRenewalFormsList[0].deleted'] = false
		mockParams['requiredRenewalFormList[1]'] = ["contractFormType.id":2L, "contractFormType":[id:2L], "new":true, "deleted":false]
		mockParams['requiredRenewalFormsList[1].contractFormType'] = 2L
		mockParams['requiredRenewalFormsList[1].id'] = ""
		mockParams['requiredRenewalFormsList[1].new'] = true
		mockParams['requiredRenewalFormsList[1].deleted'] = false

		controller.metaClass.message = { LinkedHashMap args ->
			controller.metaClass.message = { LinkedHashMap key ->
			}
			assertEquals 'contract.label', args.code
			assertEquals 'Contract', args.default
		}
		def model = controller.update()
		assertEquals "show", controller.redirectArgs.action
		assertEquals contract.id, controller.redirectArgs.id
		//assertEquals 2, contract.requiredRenewalForms.size()
	}
}
