//package edu.hawaii.its.dcmd.inf
//
//import grails.test.*
//
//import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletResponse;
//import org.gmock.*
//import static org.hamcrest.Matchers.*
//import grails.converters.JSON
//import com.grailsrocks.cacheheaders.CacheHeadersService
//import edu.hawaii.its.dcmd.inf.VendorController
//import edu.hawaii.its.dcmd.inf.PersonSupportRoleService
//import edu.hawaii.its.dcmd.inf.VendorService
//
//@WithGMock
//class VendorControllerTests extends ControllerUnitTestCase {
//
//	def person, supportRole, supportableObjectType, personSupportRole
//	def vendor
//	def message
//
//
//	protected void setUp() {
//		super.setUp()
//
//		//mock the logger
//		mockLogging VendorController
//
//		//grails mock all domain objects required
//		person = new Person(id:12, uhNumber: "55555555", title: "dtitle", lastName: "dlastname", firstName: "dfirstName", midInit:"dmidInit")
//		mockDomain(Person, [person])
//		person.save(failOnError:true)
//
//		supportRole = new SupportRole(id:12,name:'role')
//		mockDomain(SupportRole, [supportRole])
//		supportRole.save(failOnError:true)
//
//		supportableObjectType = new SupportableObjectType(id:13, name:'Vendor')
//		mockDomain(SupportableObjectType, [supportableObjectType])
//		supportableObjectType.save(failOnError:true)
//
//
//		personSupportRole = new PersonSupportRole(id:12, person:person, supportRole:supportRole, supportableObjectType:supportableObjectType)
//		mockDomain(PersonSupportRole, [personSupportRole])
//		personSupportRole.save(failOnError:true)
//
//		vendor = new Vendor(id:11, name:'vendor', phone:'3333333')
//		mockDomain(Vendor, [vendor])
//		vendor.save(failOnError:true, flush:true)
//
//		}
//
//	protected void tearDown() {
//		super.tearDown()
//	}
//
//
//	void testAddSupporterReturnsJSONWhenPersonSupportRoleDoesNotExist() {
//
//		//mock params passed to controller method - provided by grails mocks
//		mockParams.person = person.id.toString()
//		mockParams.supportRole = supportRole.id.toString()
//		mockParams.supportableObjectType = supportableObjectType.id.toString()
//		mockParams.vendor = vendor.id.toString()
//
//		//GMock a vendor and expect it's supporters property to be called
//		Vendor mockVendor = mock(Vendor)
//		mockVendor.supporters.returns(PersonSupportRole.list())
//
//		//GMock psr service and add it to the controller
//		PersonSupportRoleService personRoleSupportServiceMock = mock(PersonSupportRoleService)
//		//the params of this mock say that any call to this method is okay so long as
//		//it provides 3 Long values as its params.  See below if you need to know the
//		//exact params passed
//		personRoleSupportServiceMock.getOrCreatePersonSupportRole(instanceOf(Long), instanceOf(Long), instanceOf(Long)).returns(personSupportRole)
//		controller.personSupportRoleService = personRoleSupportServiceMock
//
//		//GMock vendor service. a call to it's service method returns a
//		//mocked Vendor created above.  This is because we set the expectation above
//		//for a mocked vendor's supporters property to be called.  Not that the params
//		//passed to this mocked service method are actual values instead of instanceOf(Type)
//		//values.  This means the call during execution must match exactly this expectation.
//		//If you didn't care what values were passed, you could say instanceOf(Long),
//		//instanceOf(PersonSupportRole)
//		VendorService vendorServiceMock = mock(VendorService)
//		vendorServiceMock.getVendorAndAddNewPersonSupportRole(vendor.id, personSupportRole).returns(mockVendor)
//
//		//set up the return value for the other vendor service call
//		def result = []
//		PersonSupportRole.list().each{
//			result << [id: personSupportRole.id, person:person.toString(), supportRole:supportRole.toString()]
//		}
//		//set expectation for next method and return results from above
//		vendorServiceMock.getSupportersForJSON(instanceOf(List)).returns(result)
//		//add vendor service to controller; remember, when the entire grails
//		//framework is in play (i.e., not unit testing), the vendorService and
//		//personSupportRoleService would be injected by Spring.  In unit test
//		//environment, this is how its done
//		controller.vendorService = vendorServiceMock
//
//		play{
//			//controller is a instance variable made available by the fact
//			//that we're extending a controller unit test.  It represents
//			//an instance of the VendorController class
//			def v = controller.addSupporter()
//
//			//make sure we returned from the controller method with an object
//			assertNotNull v
//
//			//use grails controller.response mock to retrieve the response
//			//as a string and convert to a JSON object
//			def jsonAsString = controller.response.contentAsString
//			def jsonResult = JSON.parse(jsonAsString)
//
//			//make sure the JSON returned is what was expected.
//			assertEquals 1, jsonResult.size()
//			assertEquals personSupportRole.id, jsonResult[0].id
//			assertEquals person.toString(), jsonResult[0].person
//			assertEquals supportRole.toString(), jsonResult[0].supportRole
//		}
//	}
//
//	void testCanRemoveSupporter(){
//		//vendor.addToSupporters(personSupportRole)
//		//assertEquals 1, vendor.supporters.size()
//
//		mockParams.vendor = vendor.id
//		mockParams.personSupportRole = personSupportRole.id
//
//		VendorService vendorServiceMock = mock(VendorService)
//		vendorServiceMock.getVendorAndRemovePersonSupportRole(vendor.id, personSupportRole.id).returns(vendor)
//		vendorServiceMock.getSupportersForJSON([]).returns([])
//		controller.vendorService = vendorServiceMock
//
//		play{
//			controller.removeSupporter()
//			def jsonAsString = controller.response.contentAsString
//            groovy.util.GroovyTestCase.assertEquals '[]', jsonAsString
//
//			def jsonResult = JSON.parse(jsonAsString)
//			println jsonResult.class
//			assertTrue jsonResult.isEmpty()
//		}
//	}
//
//	/*
//	 * This doesn't actually test the edit method.  Can't figure out how to mock the cache call.  But it
//	 * does show that calling cache(false) on the mocked controller response will add the required
//	 * response headers for preventing the cache.
//	 */
//	void testEditExpiresCache(){
//		def cacheHeaderService = new CacheHeadersService()
//		cacheHeaderService.cache(controller.response, false)
//		controller.response.headerNames.each{ println "${it}: ${controller.response.getHeaders(it)}" }
//		assertTrue(controller.response.getHeaders('Cache-Control').contains('no-cache, no-store'))
//		assertNotNull controller.response.getHeaders('Expires')
//		assertTrue(controller.response.getHeaders('Pragma').contains('no-cache'))
//	}
//
//	/*
//	 * Mocks out the cache service and tests that the edit action
//	 * redirects to list action when vendor instance cannot be found.
//	 * Also mocks out the flash message for the redirect
//	 */
//	void testEditRedirectsToListWhenFailsToGetVendorInstance(){
//
//		mockCache()
//
//		//no need to mock out Vendor.get(params.id); returns null without doing anything
//		play{
//			controller.metaClass.message = { LinkedHashMap args ->
//				controller.metaClass.message = { LinkedHashMap key ->
//					assertEquals "default.not.found.message", key.code
//				}
//				assertEquals 'vendor.label', args.code
//				assertEquals 'Vendor', args.default
//			}
//			def v = controller.edit()
//			assertEquals "list", controller.redirectArgs.action
//		}
//	}
//
//	void testEditDisplaysVendorForEdit(){
//
//		mockCache()
//
//		mockParams.id = vendor.id
//		mock(Vendor).static.get(mockParams.id).returns(vendor)
//
//		play{
//			def model = controller.edit()
//			assertEquals vendor, model.vendorInstance
//		}
//	}
//
//	void testSaveWithNoSupporters(){
//
//		mockParams.phone = '3333333'
//		mockParams.name = 'test vendor'
//
//		def mockVendor = mock(Vendor, constructor(mockParams))
//		mockVendor.supporters.returns([])
//		mockVendor.save(flush:true).returns(true)
//		mockVendor.id.returns(14).times(2)
//
//		def mockVendorService = mock(VendorService)
//		mockVendorService.createWithSupporters(mockVendor).returns(mockVendor)
//
//		controller.vendorService = mockVendorService
//		play{
//			controller.metaClass.message = { LinkedHashMap args ->
//				controller.metaClass.message = { LinkedHashMap key ->
//					assertEquals "default.created.message", key.code
//				}
//				assertEquals 'vendor.label', args.code
//				assertEquals 'Vendor', args.default
//			}
//			controller.save()
//			assertEquals "show", controller.redirectArgs.action
//			assertEquals 14, controller.redirectArgs.id
//		}
//	}
//
//	void testSaveWithOneSupporter(){
//		mockParams.phone = 666
//		mockParams.name = 'rfrr'
//		mockParams.action = 'save'
//		mockParams.controller = 'vendor'
//		mockParams.create = 'Create'
//		mockParams['supportersList[0]'] = ['new':'true', deleted:'false', 'person.id':1L, person:[id:1L], 'supportRole.id':1L, supportRole:[id:1L], 'supportableObjectType.id':1L, supportableObjectType:[id:1L]]
//		mockParams['supportersList[0].new'] = true
//		mockParams['supportersList[0].deleted'] = false
//		mockParams['supportersList[0].person.id'] = 1L
//		mockParams['supportersList[0].supportRole.id'] = 1L
//		mockParams['supportersList[0].supportableObjectType.id'] = 1L
//
//		def mockVendor = mock(Vendor, constructor(mockParams))
//		mockVendor.supporters.returns(PersonSupportRole.list())
//		mockVendor.save(flush:true).returns(true)
//		mockVendor.id.returns(14).times(2)
//
//		def mockVendorService = mock(VendorService)
//		mockVendorService.createWithSupporters(mockVendor).returns(mockVendor)
//
//		controller.vendorService = mockVendorService
//		play{
//			controller.metaClass.message = { LinkedHashMap args ->
//				controller.metaClass.message = { LinkedHashMap key ->
//					assertEquals "default.created.message", key.code
//				}
//				assertEquals 'vendor.label', args.code
//				assertEquals 'Vendor', args.default
//			}
//			def model = controller.save()
//			assertEquals "show", controller.redirectArgs.action
//			assertEquals 14, controller.redirectArgs.id
//		}
//	}
//
//	void testSaveWithMultipleSupporters(){
//		mockParams.phone = 666
//		mockParams.name = 'rfrr'
//		mockParams.action = 'save'
//		mockParams.controller = 'vendor'
//		mockParams.create = 'Create'
//		mockParams['supportersList[0]'] = ['new':'true', deleted:'false', 'person.id':1L, person:[id:1L], 'supportRole.id':1L, supportRole:[id:1L], 'supportableObjectType.id':1L, supportableObjectType:[id:1L]]
//		mockParams['supportersList[0].new'] = true
//		mockParams['supportersList[0].deleted'] = false
//		mockParams['supportersList[0].person.id'] = 1L
//		mockParams['supportersList[0].supportRole.id'] = 1L
//		mockParams['supportersList[0].supportableObjectType.id'] = 1L
//		mockParams['supportersList[1]'] = ['new':'true', deleted:'false', 'person.id':2L, person:[id:2L], 'supportRole.id':2L,
//					supportRole:[id:2L], 'supportableObjectType.id':2L, supportableObjectType:[id:2L]]
//		mockParams['supportersList[1].new'] = true
//		mockParams['supportersList[1].deleted'] = false
//		mockParams['supportersList[1].person.id'] = 2L
//		mockParams['supportersList[1].supportRole.id'] = 2L
//		mockParams['supportersList[1].supportableObjectType.id'] = 2L
//
//		def mockVendor = mock(Vendor, constructor(mockParams))
//		mockVendor.supporters.returns(PersonSupportRole.list())
//		mockVendor.save(flush:true).returns(true)
//		mockVendor.id.returns(14).times(2)
//
//		def mockVendorService = mock(VendorService)
//		mockVendorService.createWithSupporters(mockVendor).returns(mockVendor)
//
//		controller.vendorService = mockVendorService
//		play{
//			controller.metaClass.message = { LinkedHashMap args ->
//				controller.metaClass.message = { LinkedHashMap key ->
//					assertEquals "default.created.message", key.code
//				}
//				assertEquals 'vendor.label', args.code
//				assertEquals 'Vendor', args.default
//			}
//			def model = controller.save()
//			assertEquals "show", controller.redirectArgs.action
//			assertEquals 14, controller.redirectArgs.id
//		}
//	}
//
//	void testSaveFails(){
//
//		mockParams.phone = '3333333'
//		mockParams.name = 'test vendor'
//
//		def mockVendor = mock(Vendor, constructor(mockParams))
//		mockVendor.save(flush:true).returns(false)
//
//		def mockVendorService = mock(VendorService)
//		mockVendorService.createWithSupporters(mockVendor).returns(mockVendor)
//
//
//		controller.vendorService = mockVendorService
//		play{
//			controller.save()
//			assertEquals mockVendor, controller.modelAndView.model.vendorInstance
//		}
//	}
//
//	void testListJSON() {
//		def vendorService = mock(VendorService)
//		//return can be anything; just want to check that controller returns it as JSON
//		vendorService.listVendors(instanceOf(Map)).returns([new Vendor()])
//		controller.vendorService = vendorService
//
//		play{
//			controller.listJSON()
//			def contentString = controller.response.contentAsString
//			assertNotNull contentString
//			def contentJSON = JSON.parse(contentString)
//			assertFalse contentJSON.isEmpty()
//		}
//	}
//
//
//	def mockCache(){
//		def cacheService = mock(CacheHeadersService)
//		cacheService.cache(instanceOf(GrailsMockHttpServletResponse), false)
//		controller.class.metaClass.cache = { Boolean allow -> cacheService.cache(delegate.response, allow) }
//	}
//}
