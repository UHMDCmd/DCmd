package edu.hawaii.its.dcmd.inf

import grails.test.*

class ApplicationTests extends GrailsUnitTestCase {

	def app

	protected void setUp() {
		super.setUp()

		app = new Application(
				applicationTitle: "SITAR",
				applicationDescription: "Systems Infrastructure Tracking and Reporting System Development",
				applicationGroup: "Systems Engineering",
				applicationTier: "Enterprise",
				environment: new Environment(name:"Test"),
				tierInstance: 1,
				updatedById: 1,
				)
		mockForConstraintsTests(Application, [app])
	}

	protected void tearDown() {
		super.tearDown()
	}

	void testToString() {
		assertToString app, "SITAR"
	}

	// Test not nullable
	void testNoNullApplicationTitle(){
		app.applicationTitle = null
		assertFalse app.validate()
		assertEquals "nullable", app.errors.applicationTitle
	}

	void testNoNullApplicationDescription(){
		app.applicationDescription = null
		assertFalse app.validate()
		assertEquals "nullable", app.errors.applicationDescription
	}

	void testNoNullDateCreated() {
		app.dateCreated = null
		assertFalse app.validate()
		assertEquals "nullable", app.errors.dateCreated
	}

	void testNoNullEnvironment(){
		app.environment = null
		assertFalse app.validate()
		assertEquals "nullable", app.errors.environment
	}

	void testNoNullLastUpdated() {
		app.lastUpdated = null
		assertFalse app.validate()
		assertEquals "nullable", app.errors.lastUpdated
	}

	void testNoNullUpdatedById() {
		app.updatedById = null
		assertFalse app.validate()
		assertEquals "nullable", app.errors.updatedById
	}

	// Test maxSize
	void testMaxSizeApplicationTitle(){
		app.applicationTitle = "a".multiply(46)
		assertFalse app.validate()
		assertEquals "maxSize", app.errors.applicationTitle
	}

	void testMaxSizeApplicationDescription(){
		app.applicationDescription = "a".multiply(257)
		assertFalse app.validate()
		assertEquals "maxSize", app.errors.applicationDescription
	}

	void testMaxSizeApplicationGroup(){
		app.applicationGroup = "a".multiply(46)
		assertFalse app.validate()
		assertEquals "maxSize", app.errors.applicationGroup
	}

	void testMaxSizeApplicationTier(){
		app.applicationTier = "a".multiply(46)
		assertFalse app.validate()
		assertEquals "maxSize", app.errors.applicationTier
	}

	// Test no blanks
	void testNoBlankApplicationTitle(){
		app.applicationTitle = ""
		assertFalse app.validate()
		assertEquals "blank", app.errors.applicationTitle
	}

	void testNoBlankApplicationDescription(){
		app.applicationDescription = ""
		assertFalse app.validate()
		assertEquals "blank", app.errors.applicationDescription
	}
}
