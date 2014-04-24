package edu.hawaii.its.dcmd.inf

import grails.test.*

class EnvironmentTests extends GrailsUnitTestCase {
	
	def env
	
    protected void setUp() {
        super.setUp()
		
		env = new Environment(
			name:"Production",
			abbreviation:"prod",
			)
		mockForConstraintsTests(Environment, [env])
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testToString() {
		assertToString env, env.name
    }

	// Test not nullable

	void testNoBlankName(){
		env.name = ""
		assertFalse env.validate()
		assertEquals "blank", env.errors.name
	}

	void testNoNullname(){
		env.name = null
		assertFalse env.validate()
		assertEquals "nullable", env.errors.name
	}
	
	void testNotUniqueName(){
		def testEnv = new Environment(name:env.name)
		assertFalse testEnv.validate()
		assertFalse "duplicate environment accepted, unique environments are required", testEnv.validate()
		assertEquals "unique", testEnv.errors.name
	}
	
	
	
}
