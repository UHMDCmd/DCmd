package edu.hawaii.its.dcmd.inf

import grails.test.*

class HostTests extends GrailsUnitTestCase {

	def host

	protected void setUp() {
		super.setUp()
		host = new Host(
				hostname:'uhunix',
				nwaccScan: true,
				environment: new Environment(name:"Test"),
				updatedById: 1,
				)
		mockForConstraintsTests(Host, [host])
	}

	protected void tearDown() {
		super.tearDown()
	}

	
	// test not nullable

	void testNoNullEnvironment(){
		host.environment = null
		assertFalse host.validate()
		assertEquals "nullable", host.errors.environment
	}
	
	void testNoNullHostname() {
		host.hostname = null
		assertFalse host.validate()
		assertEquals "nullable", host.errors.hostname

	}

	void testNoNullUpdatedById() {
		host.updatedById = null
		assertFalse host.validate()
		assertEquals "nullable", host.errors.updatedById
	}

	//test maxsize
	void testMaxSizeHostname(){
		host.hostname = "a".multiply(46)
		assertFalse host.validate()
		assertEquals "maxSize", host.errors.hostname
	}
	
	//test no blank
	void testNoBlankHostname(){
		host.hostname = ""
		assertFalse host.validate()
		assertEquals "blank", host.errors.hostname
	}
	
	void testToString(){
		assertToString host.hostname, host.toString()
	}
	
}
