package edu.hawaii.its.dcmd.inf

import grails.test.*
import edu.hawaii.its.dcmd.inf.Person

class PersonSupportRoleIntegrationTests extends GroovyTestCase {
		
	def person, sr, vendorType, psr
	def supportableObjectService
	
    protected void setUp() {
        super.setUp()
		
		person = new Person(uhNumber:"11111111", title:"Sir", lastName:"John", firstName:"Elton", midInit:"H")
		person.save(failOnError:true, flush:true)
		assertNotNull person
		
		sr = new SupportRole(name:'sa')
		sr.save(failOnError:true, flush:true)
		assertNotNull sr
		
		vendorType = new SupportableObjectType(name:'Vendor', description:'a vendor has the following types of support: ...')
		vendorType.save(failOnError:true, flush:true)
		assertNotNull vendorType
				
		psr = new PersonSupportRole(person:person, supportRole:sr, supportableObjectType:vendorType)
		psr.save(flush:true)
		assertNotNull psr
		assertEquals psr.person, person
		assertEquals psr.supportRole, sr
		assertEquals psr.supportableObjectType, vendorType
		
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void testCanDeletePersonSupportRoleWithoutAffectingSupportableObjectTypes(){
		psr.delete(flush:true)
		assertEquals 0, PersonSupportRole.count()
		assertEquals 1, SupportableObjectType.count()
	}

	void testCanDeletePersonSupportRoleWithoutAffectingSupportRoles(){
		psr.delete(flush:true)
		assertEquals 0, PersonSupportRole.count()
		assertEquals 1, SupportRole.count()
	}

	void testCanDeletePersonSupportRoleWithoutAffectingPeople(){
		psr.delete(flush:true)
		assertEquals 0, PersonSupportRole.count()
		assertEquals 1, Person.count()
	}
	
	void testVendorCanAddPersonSupportRolesWhoseTypeIsVendor(){
		def vendor = new Vendor(name:'vendor A', phone:'1234567')
		vendor.addToSupporters(psr)
		vendor.save(flush:true)
		
		assertEquals 1, vendor.supporters.size()
	}
	
	//TODO: cannot add other types of psr's

	void testDeleteVendorDoesNotDeletePersonSupportRolesWhoseTypeIsVendor(){
		def vendor = new Vendor(name:'vendor A', phone:'1234567')
		vendor.addToSupporters(psr)
		vendor.save(flush:true)		
		assertEquals 1, vendor.supporters.size()
		vendor.delete(flush:true)
		assertEquals 0, Vendor.count()
		assertEquals 1, PersonSupportRole.count()
	}

}
