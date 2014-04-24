//package edu.hawaii.its.dcmd.inf
//
//import grails.test.*
//import grails.converters.JSON
//import org.gmock.*
//import static org.hamcrest.Matchers.*
//import edu.hawaii.its.dcmd.inf.PersonController
//import edu.hawaii.its.dcmd.inf.PersonService
//
//@WithGMock
//class PersonControllerTests extends ControllerUnitTestCase {
//
//	def person1, person2
//
//    protected void setUp() {
//        super.setUp()
//		mockLogging PersonController
//
//		person1 = new Person(id:1, uhNumber: "55555555", title: "Mr.", lastName: "smith", firstName: "john", midInit:"r")
//		person2 = new Person(id:2, uhNumber: "33333333", title: "Ms.", lastName: "jones", firstName: "jane", midInit:"t")
//
//		mockDomain(Person, [person1, person2])
//		person1.save(failOnError:true)
//		person2.save(failOnError:true, flush:true)
//
//    }
//
//    protected void tearDown() {
//        super.tearDown()
//    }
//
//    void testSearchJSONWithNoResults() {
//
//		mockParams.searchTerm = "blah"
//		mockParams.maxRows = "1"
//
//		def mockService = mock(PersonService)
//		mockService.searchJSON(mockParams.searchTerm, mockParams.int('maxRows')).returns([])
//
//		controller.personService = mockService
//
//		play{
//			def model = controller.searchJSON()
//			assertNotNull model
//
//			def jsonString = controller.response.contentAsString
//            groovy.util.GroovyTestCase.assertEquals("[]", jsonString)
//
//			def json = JSON.parse(jsonString)
//			assertTrue json.isEmpty()
//		}
//    }
//
//	void testSearchJSONWithOneResult() {
//
//		mockParams.searchTerm = "blah"
//		mockParams.maxRows = "1"
//
//		def mockService = mock(PersonService)
//		def returned = [[id:person1.id, name:person1.toString()]]
//		mockService.searchJSON(mockParams.searchTerm, mockParams.int('maxRows')).returns(returned)
//
//		controller.personService = mockService
//
//		play{
//			def model = controller.searchJSON()
//			assertNotNull model
//
//			def jsonString = controller.response.contentAsString
//			assertTrue(jsonString.length() > 5)
//			println jsonString
//			def json = JSON.parse(jsonString)
//
//			assertEquals 1, json.length()
//			assertEquals person1.id, json[0].id
//			assertEquals person1.toString(), json[0].name
//		}
//	}
//
//	void testSearchJSONWithMultipleResults() {
//
//		mockParams.searchTerm = "blah"
//		mockParams.maxRows = "1"
//
//		def mockService = mock(PersonService)
//		def returned = [[id:person1.id, name:person1.toString()],
//						[id:person2.id, name:person2.toString()]]
//		mockService.searchJSON(mockParams.searchTerm, mockParams.int('maxRows')).returns(returned)
//
//		controller.personService = mockService
//
//		play{
//			def model = controller.searchJSON()
//			assertNotNull model
//
//			def jsonString = controller.response.contentAsString
//			assertTrue(jsonString.length() > 5)
//			println jsonString
//			def json = JSON.parse(jsonString)
//
//			assertEquals 2, json.length()
//			assertEquals person1.id, json[0].id
//			assertEquals person1.toString(), json[0].name
//			assertEquals person2.id, json[1].id
//			assertEquals person2.toString(), json[1].name
//		}
//	}
//
//}
//
