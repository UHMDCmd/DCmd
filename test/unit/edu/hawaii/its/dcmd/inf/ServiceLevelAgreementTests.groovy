package edu.hawaii.its.dcmd.inf

import grails.test.*

class ServiceLevelAgreementTests extends GrailsUnitTestCase {

	def sla

	protected void setUp() {
		super.setUp()
		sla = new ServiceLevelAgreement( slaTitle: "Superior Service", slaType: "Enterprise" )
		mockForConstraintsTests(ServiceLevelAgreement, [sla])
	}

	protected void tearDown() {
		super.tearDown()
	}

	// If a property should be nullable, add it to the excludes list.
	void testAllPropertiesSetNullableFalse(){

		def excludes = ['id', 'metaClass', 'constraints', 'class', 'errors', 'version', 'dateCreated', 'lastUpdated']

		sla.properties.each{p->
			if(!excludes.contains(p.key)){
				assertTrue sla.validate()
				def original = p.value
//				println "${p.key}: ${p.value}"
				sla."${p.key}" = null
//				println "${p.key}: ${p.value}"
				assertFalse sla.validate()
				sla."${p.key}" = original
			}
		}
	}

	// Test maxSize
	void testMaxSizeSlaTitle(){
		sla.slaTitle = "a".multiply(46)
		assertFalse sla.validate()
		assertEquals "maxSize", sla.errors.slaTitle
	}

	void testMaxSizeSlaType(){
		sla.slaType = "a".multiply(46)
		assertFalse sla.validate()
		assertEquals "maxSize", sla.errors.slaType
	}

	// Test no blanks
	void testNoBlankSlaTitle(){
		sla.slaTitle = ""
		assertFalse sla.validate()
		assertEquals "blank", sla.errors.slaTitle
	}

	void testNoBlankSlaType(){
		sla.slaType = ""
		assertFalse sla.validate()
		assertEquals "blank", sla.errors.slaType
	}
	
	void testToString() {
		assertToString sla, "Superior Service - Enterprise"
	}
}
