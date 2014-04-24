//package edu.hawaii.its.dcmd.inf
//
//import grails.test.*
//import org.gmock.*
//import static org.hamcrest.Matchers.*
//import edu.hawaii.its.dcmd.inf.PersonSupportRole
//import edu.hawaii.its.dcmd.inf.PersonSupportRoleService
//
//@WithGMock
//class PersonSupportRoleServiceTests extends GrailsUnitTestCase {
//
//	def person, supportRole, supportableObjectType, personSupportRole
//
//    protected void setUp() {
//        super.setUp()
//
//		//mock out the logger
//		mockLogging PersonSupportRoleService
//
//		//mock out all the domain objects
//		person = new Person(id:12, uhNumber: "55555555", title: "dtitle", lastName: "dlastname", firstName: "dfirstName", midInit:"dmidInit")
//		supportRole = new SupportRole(id:12,name:'role')
//		supportableObjectType = new SupportableObjectType(id:13, name:'Vendor')
//
//		mockDomain(Person, [person])
//		mockDomain(SupportRole, [supportRole])
//		mockDomain(SupportableObjectType, [supportableObjectType])
//
//		personSupportRole = new PersonSupportRole(person:person, supportRole:supportRole, supportableObjectType:supportableObjectType)
//		mockDomain(PersonSupportRole, [personSupportRole])
//    }
//
//    protected void tearDown() {
//        super.tearDown()
//    }
//
//    void testCanGetExistingPersonRole() {
//
//		//a GMock for a hibernate get criteria
//		def mockCriteria = mock(){
//			//when get is called with any closure, return the first
//			//PersonSupportRole instance in the list of all PersonSupportRoles
//			get(instanceOf(Closure)).returns(PersonSupportRole.list()[0])
//		}
//		//GMock the createCriteria static method and return the mock created
//		// above
//		mock(PersonSupportRole).static.createCriteria().returns(mockCriteria)
//
//		play{
//
//			//create the service and call the method being tested
//			def service = new PersonSupportRoleService()
//			def psr = service.getOrCreatePersonSupportRole(person.id, supportRole.id, supportableObjectType.id)
//
//			//check that all is well in the world
//			assertNotNull psr
//			assertEquals person, psr.person
//			assertEquals supportRole, psr.supportRole
//			assertEquals supportableObjectType, psr.supportableObjectType
//		}
//
//    }
//
//	void testCanCreatePersonRole(){
//
//		def mockCriteria = mock(){
//			get(instanceOf(Closure)).returns(null)
//		}
//		mock(PersonSupportRole).static.createCriteria().returns(mockCriteria)
//		def psrMock = mock(PersonSupportRole, constructor(person:person, supportRole:supportRole, supportableObjectType:supportableObjectType))//.returns(personSupportRole)
//		psrMock.save(instanceOf(Map)).returns(true)
//		play{
//			def service = new PersonSupportRoleService()
//			def psr = service.getOrCreatePersonSupportRole(person.id, supportRole.id, supportableObjectType.id)
//			assertNotNull psr
//		}
//	}
//}
