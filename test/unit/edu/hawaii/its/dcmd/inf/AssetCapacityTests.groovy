package edu.hawaii.its.dcmd.inf

import grails.test.*

class AssetCapacityTests extends GrailsUnitTestCase {

	def asset, aType, madeBy, rType, uType
	def assetCapacity
	def maxSize = 45
	
    protected void setUp() {
        super.setUp()
		rType = new ResourceType( resourceType:"RAM", resourceDescription:"Random Access Memory", updatedById: 1 )
		uType = new UnitType( unit:"GB", unitDescription:"Gigabyte", updatedById: 1 )
		aType = new AssetType( name: "Host", description: "A physical server" )
		madeBy = new Manufacturer( name: "IBM", code: "some code", phone: "555-1234", updatedById: 1 )
		asset = new Asset( itsId: "ESX Cluster", assetType: aType, manufacturer: madeBy )

		assetCapacity = new AssetCapacity( asset: asset, resourceType: rType, unitType: uType, currentMaximumCapacity: 500.0, futureMaximumCapacity: 1000.0, updatedById: 123 )
		
		mockForConstraintsTests(AssetCapacity, [assetCapacity])
		assertTrue "assetCapacity was not a valid object", assetCapacity.validate()

    }

    protected void tearDown() {
        super.tearDown()
    }

	void testToString() {
		assertEquals assetCapacity.toString(), assetCapacity.currentMaximumCapacity + " " + assetCapacity.unitType + " " + assetCapacity.resourceType
	}
	
	void testPropertyNotNull( property ) {
		def original = property.value
		assetCapacity."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", assetCapacity.validate()
		assertEquals "nullable", assetCapacity.errors."${property.key}"
		assetCapacity."${property.key}" = original
	}

	void testPropertyFourDecimalPlaces( property ) {
		assetCapacity."${property.key}" = 0.12345
		assetCapacity.validate()
		assertEquals 0.1235f, assetCapacity."${property.key}"
	}

	// This function will loop through a list of given properties to test each applicable constraint
	void testListOfProperties() {

		// populate this list with all properties that should not be null
		def not_nullable = [
			'asset',
			'resourceType',
			'unitType', 
			'currentMaximumCapaity',
			'futureMaximumCapacity',
			'updatedById',
		];
		assetCapacity.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}

		// populate this list with all properties that should have 4 digits after the decimal
		def decimal_four = [
			'currentMaximumCapacity',
			'futureMaximumCapacity',
		];
		assetCapacity.properties.each { p->
			if ( decimal_four.contains(p.key)) {
				testPropertyFourDecimalPlaces( p )
			}
		}
	}
	
}
