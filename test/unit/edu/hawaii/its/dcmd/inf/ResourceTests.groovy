package edu.hawaii.its.dcmd.inf

import grails.test.*

class ResourceTests extends GrailsUnitTestCase {

	def oneAssetType, madeBy, oneAsset, oneResourceType, oneUnitType, oneResource
	def rType, uType, resource

	protected void setUp() {
		super.setUp()
		
		rType = new ResourceType( resourceType: "RAM", resourceDescription: "Random Access Memory", updatedById: 1 )
		uType = new UnitType( unit: "GB", unitDescription: "Gigabyte", updatedById: 1 )
		resource = new Resource( resourceType: rType, unitType: uType, capacity: 500.0 )

		mockForConstraintsTests( ResourceType, [rType] )
		assertTrue "rType is not a valid ResourceType object", rType.validate()
		mockForConstraintsTests( UnitType, [uType] )
		assertTrue "uType is not a valid UnitType object", uType.validate()
		mockForConstraintsTests( Resource, [resource] )
		assertTrue "resource is not a valid Resource object", resource.validate()
		
	}

	protected void tearDown() {
		super.tearDown()
	}
	
	void testToString() {
		assertEquals resource.toString(), "500.0 GB RAM"
	}
	
	void testNotNullResourceType() {
		resource.resourceType = null
		assertFalse "resource must have a resource type", resource.validate()
	}
	
	void testNotNullUnitType() {
		resource.unitType = null
		assertFalse "oneResource must have a unit type", resource.validate()
	}

}