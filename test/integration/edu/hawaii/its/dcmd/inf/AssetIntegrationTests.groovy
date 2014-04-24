/*
 * This integration test verifies the associations between Asset and "neighboring" domain classes.
 */

package edu.hawaii.its.dcmd.inf

import grails.test.*
import edu.hawaii.its.dcmd.inf.AssetCapacity

class AssetIntegrationTests extends GroovyTestCase {

	def aType, madeBy, locale
	def rType, uType
	def asset1, asset2
	def assetCapacity
	def rc, rcType
	
	
    protected void setUp() {
        super.setUp()
		aType = new AssetType( name: "Host", description: "A physical device" )
		aType.save( flush: true, failOnError: true )
		madeBy = new Manufacturer( name: "Hitachi", code: "some_code", phone: "555-1234", updatedById: 1 )
		madeBy.save( flush: true, failOnError: true )
		locale = new Location( locationDescription: "Keller 103", updatedById: 1 )
		locale.save( flush: true, failOnError: true )
		asset1 = new Asset( itsId: "esx01", assetType: aType, manufacturer: madeBy, location: locale )
		asset1.save( flush: true, failOnError: true )
		asset2 = new Asset( itsId: "esx02", assetType: aType, manufacturer: madeBy, location: locale )
		asset2.save( flush: true, failOnError: true )
		
		rType = new ResourceType( resourceType:"RAM", resourceDescription:"Random Access Memory", updatedById: 1 )
		rType.save( flush: true, failOnError: true )
		uType = new UnitType( unit:"GB", unitDescription:"Gigabyte", updatedById: 1 )
		uType.save( flush: true, failOnError: true )
		assetCapacity = new AssetCapacity( resourceType: rType, unitType: uType, currentMaximumCapacity: 500.0, futureMaximumCapacity: 1000.0, updatedById: 123 )
		asset1.addToAssetCapacities(assetCapacity)
		asset1.save( flush: true, failOnError: true )
		assetCapacity.save( flush: true, failOnError: true )

    }

    protected void tearDown() {
        super.tearDown()
    }

	// This tests that we cannot delete an AssetType that has Assets associated with it.
	void testCannotDeleteAssetTypeWithChild () {
		assertEquals 2, Asset.count()
		assertEquals 1, AssetType.count()
		// This delete call should fail because of a Data Integrity Violation
		shouldFail () {
			aType.delete( flush: true )
		}
		// We should still have two Asset objects and one AssetType
		assertEquals 2, Asset.count()	
		assertEquals 1, AssetType.count()
	}
	
	// This tests that we cannot delete a Manufacturer that has Assets associated with it.
	void testCannotDeleteManufacturerWithChild () {
		assertEquals 2, Asset.count()
		assertEquals 1, Manufacturer.count()
		// This delete call should fail because of a Data Integrity Violation
		shouldFail () {
			madeBy.delete( flush: true )
		}
		// We should still have two Asset objects and one Manufacturer
		assertEquals 2, Asset.count()	
		assertEquals 1, Manufacturer.count()
	}
	
	// This tests that we cannot delete a Location that has Assets associated with it.
	void testCannotDeleteLocationWithChild () {
		assertEquals 2, Asset.count()
		assertEquals 1, Location.count()
		// This delete call should fail because of a Data Integrity Violation
		shouldFail () {
			locale.delete( flush: true )
		}
		// We should still have two Asset objects and one Location
		assertEquals 2, Asset.count()	
		assertEquals 1, Location.count()
	}
	
	// This tests that we can successfully delete an Asset without affecting anything else
	// and also that child AssetCapacity objects are deleted.
	// This test also demonstrates that the deletion of an Asset that is associated with

	void testCanDeleteAsset() {
		assertEquals 2, Asset.count()
		assertEquals 1, AssetCapacity.count()
		assertEquals 1, AssetType.count()
		assertEquals 1, Manufacturer.count()
		assertEquals 1, Location.count()
		asset1.delete( flush: true )

		// and no AssetCapacity (because it was associated with asset1)
		assertEquals 1, Asset.count()
		assertEquals 0, AssetCapacity.count()
		assertEquals 1, AssetType.count()
		assertEquals 1, Manufacturer.count()
		assertEquals 1, Location.count()
	}


}
