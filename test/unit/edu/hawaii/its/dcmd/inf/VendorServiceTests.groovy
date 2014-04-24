//package edu.hawaii.its.dcmd.inf
//
//import grails.test.*
//import org.gmock.*
//import static org.hamcrest.Matchers.*
//import grails.orm.PagedResultList
//import edu.hawaii.its.dcmd.inf.PersonSupportRoleService
//import edu.hawaii.its.dcmd.inf.VendorService
//
//@WithGMock
//class VendorServiceTests extends GrailsUnitTestCase {
//
//	def person, supportRole, supportableObjectType, personSupportRole
//	def vendor
//	def message
//
//    protected void setUp() {
//        super.setUp()
//
//		//standard grails mocking for log object
//		mockLogging VendorService
//
//		person = new Person(id:12, uhNumber: "55555555", title: "dtitle", lastName: "dlastname", firstName: "dfirstName", midInit:"dmidInit")
//		supportRole = new SupportRole(id:12,name:'role')
//		supportableObjectType = new SupportableObjectType(id:13, name:'Vendor')
//
//		//standard grails mocking for domain objects
//		mockDomain(Person, [person])
//		mockDomain(SupportRole, [supportRole])
//		mockDomain(SupportableObjectType, [supportableObjectType])
//
//		//standard grails domain mockhh
//		personSupportRole = new PersonSupportRole(id:12, person:person, supportRole:supportRole, supportableObjectType:supportableObjectType)
//		mockDomain(PersonSupportRole, [personSupportRole])
//		personSupportRole.save(failOnError:true, flush:true)
//
//		//standard grails domain mock
//		vendor = new Vendor(id:11, name:'vendor', phone:'3333333')
//		mockDomain(Vendor, [vendor])
//
//	}
//
//
//    protected void tearDown() {
//        super.tearDown()
//    }
//
//
//	/*
//	 * Using GMock to set expectations on domain objects.  GMock is nice
//	 * because it plays well with mockDomain from Grails.  In setup, we
//	 * used mockDomain but we could also use GMock to stub out methods that
//	 * mockDomain does not provide.
//	 */
//    void testCanGetVendorAndAddNewPersonSupportRole() {
//		//GMock of Vendor class static get method -- returns the
//		//vendor created in setup
//		mock(Vendor).static.get(instanceOf(Long)).returns(vendor)
//
//		//GMock of specific vendor instance to control what is
//		//returned in the addToSupporters and save methods
//		mock(vendor).addToSupporters(instanceOf(PersonSupportRole))
//		mock(vendor).save(instanceOf(Map)).returns(true)
//
//		//GMock works with this paradigm
//		//	1. Set up all of you expectations/mocks
//		//	2. Place code that will cause those expectations to be met
//		//	   in a play closuure.
//		//GMock will insure that all of the expectations declared above
//		//have been met.  See GMock docs for how to change the syntax to
//		//allow for multiple (or range) executions of a single expectation.
//		//It is also legal to expect that a method will NOT called
//		play{
//			//create the service to be tested and assert that a vendor model
//			//is returned
//			def vendorService = new VendorService()
//			def v = vendorService.getVendorAndAddNewPersonSupportRole(vendor.id, personSupportRole)
//			assertNotNull v
//		}
//    }
//
//    /*
//	 * No mocks required; just exercising the actual code.
//	 */
//	void testCanGetSupportersForJSON(){
//		def vendorService = new VendorService()
//		def returned = vendorService.getSupportersForJSON(PersonSupportRole.list() as Set)
//		assertEquals 1, returned.size()
//		assertEquals personSupportRole.id, returned[0].id
//		assertEquals person.toString(), returned[0].person
//		assertEquals supportRole.toString(), returned[0].supportRole
//	}
//
//	void testCanGetVendorAndRemovePersonSupportRole(){
//		def vendorService = new VendorService()
//		def success = vendorService.getVendorAndRemovePersonSupportRole(vendor.id, personSupportRole.id)
//		assertEquals vendor, success
//		assertEquals 0, vendor.supporters.size()
//	}
//
//	void testCanCreateWithNoSupporters(){
//		def vendorService = new VendorService()
//		def success = vendorService.createWithSupporters(vendor)
//		assertEquals success, vendor
//		assertEquals success.supporters.size(), vendor.supporters.size()
//	}
//
//	void testCanCreateWithOneSupporter(){
//		def ps = [phone:666, name:'rfrr',  action:'save', controller:'vendor', create:'Create',
//			'supportersList[0]':['new':'true', deleted:'false', 'person.id':1L, person:[id:1L], 'supportRole.id':1L, supportRole:[id:1L], 'supportableObjectType.id':1L, supportableObjectType:[id:1L]],
//			'supportersList[0].new':true,
//			'supportersList[0].deleted':false,
//			'supportersList[0].person.id':1,
//			'supportersList[0].supportRole.id':1,
//			'supportersList[0].supportableObjectType.id':1]
//		def v = new Vendor(ps)
//        groovy.util.GroovyTestCase.assertEquals 1, v.supporters.size()
//
//		def personSupportRoleService = mock(PersonSupportRoleService)
//		personSupportRoleService.getOrCreatePersonSupportRole(instanceOf(Long), instanceOf(Long), instanceOf(Long)).returns(personSupportRole)
//
//		def vendorService = new VendorService()
//		vendorService.personSupportRoleService = personSupportRoleService
//
//		play{
//			def success = vendorService.createWithSupporters(v)
//			assertEquals success, v
//			assertEquals success.supporters.size(), v.supporters.size()
//		}
//	}
//
//	void testCanCreateWithMultipleSupporters(){
//		def ps = [phone:666, name:'rfrr',  action:'save', controller:'vendor', create:'Create',
//			'supportersList[0]':['new':'true', deleted:'false', 'person.id':1L, person:[id:1L], 'supportRole.id':1L, supportRole:[id:1L], 'supportableObjectType.id':1L, supportableObjectType:[id:1L]],
//			'supportersList[0].new':true,
//			'supportersList[0].deleted':false,
//			'supportersList[0].person.id':1L,
//			'supportersList[0].supportRole.id':1L,
//			'supportersList[0].supportableObjectType.id':1L,
//			'supportersList[1]':['new':'true', deleted:'false', 'person.id':2L, person:[id:2L], 'supportRole.id':2L, supportRole:[id:2L], 'supportableObjectType.id':2L, supportableObjectType:[id:2L]],
//			'supportersList[1].new':true,
//			'supportersList[1].deleted':false,
//			'supportersList[1].person.id':2L,
//			'supportersList[1].supportRole.id':2L,
//			'supportersList[1].supportableObjectType.id':2L]
//		def v = new Vendor(ps)
//        groovy.util.GroovyTestCase.assertEquals 2, v.supporters.size()
//
//		def personSupportRoleService = mock(PersonSupportRoleService)
//		personSupportRoleService.getOrCreatePersonSupportRole(1L,1L,1L).returns(personSupportRole)
//		personSupportRoleService.getOrCreatePersonSupportRole(2L,2L,2L).returns(personSupportRole)
//
//		def vendorService = new VendorService()
//		vendorService.personSupportRoleService = personSupportRoleService
//
//		play{
//			def success = vendorService.createWithSupporters(v)
//			assertEquals success, v
//			assertEquals success.supporters.size(), v.supporters.size()
//		}
//	}
//
//
//	void testListVendors(){
//
//		def ps = [:]
//		ps.sEcho = "echo"
//		ps.iSortCol_0 = "1"
//
//		def returnedData = new PagedResultList(Vendor.list())
//		def mockCriteria = mock(){
//			list(instanceOf(Map), instanceOf(Closure)).returns(returnedData)
//		}
//		mock(Vendor).static.createCriteria().returns(mockCriteria)
//
//		def mockCountCriteria = mock(){
//			get(instanceOf(Closure)).returns(1)
//		}
//		mock(Vendor).static.createCriteria().returns(mockCountCriteria)
//
//		play{
//			def vendorService = new VendorService()
//			def dataToRender = vendorService.listVendors(ps)
//			assertNotNull dataToRender
//			assertEquals returnedData.list.size(), dataToRender.iTotalRecords
//			assertEquals returnedData.getTotalCount(), dataToRender.iTotalDisplayRecords
//			assertEquals returnedData.list.size(), dataToRender.aaData.size()
//			assertEquals ps.sEcho, dataToRender.sEcho
//			assertEquals returnedData.list[0].id, dataToRender.aaData[0][0]
//			assertEquals returnedData.list[0].code, dataToRender.aaData[0][1]
//			assertEquals returnedData.list[0].name, dataToRender.aaData[0][2]
//			assertEquals returnedData.list[0].phone, dataToRender.aaData[0][3]
//			assertEquals returnedData.list[0].fax, dataToRender.aaData[0][4]
//			assertEquals returnedData.list[0].addressLine1, dataToRender.aaData[0][4]
//			assertEquals returnedData.list[0].addressLine2, dataToRender.aaData[0][4]
//			assertEquals returnedData.list[0].city, dataToRender.aaData[0][4]
//			assertEquals returnedData.list[0].state, dataToRender.aaData[0][4]
//			assertEquals returnedData.list[0].zip, dataToRender.aaData[0][4]
//		}
//	}
//}