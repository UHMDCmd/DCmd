//package edu.hawaii.its.dcmd.inf
//
//import grails.test.*
//import org.gmock.*
//import static org.hamcrest.Matchers.*
//import edu.hawaii.its.dcmd.inf.PersonService
//
//@WithGMock
//class PersonServiceTests extends GrailsUnitTestCase {
//
//	def person1, person2
//
//    protected void setUp() {
//        super.setUp()
//
//		mockLogging PersonService
//
//		person1 = new Person(id:1, uhNumber: "55555555", title: "Mr.", lastName: "smith", firstName: "john", midInit:"r")
//		person2 = new Person(id:2, uhNumber: "33333333", title: "Ms.", lastName: "jones", firstName: "jane", midInit:"t")
//
//		mockDomain(Person, [person1, person2])
//		person1.save(failOnError:true)
//		person2.save(failOnError:true, flush:true)
//    }
//
//    protected void tearDown() {
//        super.tearDown()
//    }
//
//    void testSearchWithNoResults() {
//		def mockCriteria = mock(){
//			//when get is called with any closure, return the first
//			//PersonSupportRole instance in the list of all PersonSupportRoles
//			list(instanceOf(Closure)).returns([])
//		}
//		mock(Person).static.createCriteria().returns(mockCriteria)
//		play{
//			def personService = new PersonService()
//			def result = personService.searchJSON("blah", 10)
//			assertEquals([], result)
//		}
//    }
//
//	void testSearchWithNullResults() {
//		def mockCriteria = mock(){
//			//when get is called with any closure, return the first
//			//PersonSupportRole instance in the list of all PersonSupportRoles
//			list(instanceOf(Closure)).returns(null)
//		}
//		mock(Person).static.createCriteria().returns(mockCriteria)
//		play{
//			def personService = new PersonService()
//			def result = personService.searchJSON("blah", 10)
//			assertEquals([], result)
//		}
//	}
//
//	void testSearchWithOneResult() {
//		def mockCriteria = mock(){
//			//when get is called with any closure, return the first
//			//PersonSupportRole instance in the list of all PersonSupportRoles
//			list(instanceOf(Closure)).returns([Person.list()[0]])
//		}
//		mock(Person).static.createCriteria().returns(mockCriteria)
//		play{
//			def personService = new PersonService()
//			def result = personService.searchJSON("blah", 10)
//			assertEquals(1, result.size())
//			assertEquals(person1.id, result[0].id)
//			assertEquals(person1.toString(), result[0].name)
//		}
//	}
//
//	void testSearchWithMultipleResults() {
//		def mockCriteria = mock(){
//			//when get is called with any closure, return the first
//			//PersonSupportRole instance in the list of all PersonSupportRoles
//			list(instanceOf(Closure)).returns(Person.list())
//		}
//		mock(Person).static.createCriteria().returns(mockCriteria)
//		play{
//			def personService = new PersonService()
//			def result = personService.searchJSON("blah", 10)
//			assertEquals(2, result.size())
//			assertEquals(person1.id, result[0].id)
//			assertEquals(person1.toString(), result[0].name)
//			assertEquals(person2.id, result[1].id)
//			assertEquals(person2.toString(), result[1].name)
//		}
//	}
//
//}
