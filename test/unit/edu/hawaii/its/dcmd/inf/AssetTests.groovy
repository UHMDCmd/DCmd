package edu.hawaii.its.dcmd.inf

import grails.test.*


class AssetTests extends GrailsUnitTestCase {
	def asset, aType, madeBy
	Integer maxSize = 45
	
	protected void setUp() {
		super.setUp()
		
		aType = new AssetType( name: "Host", description: "A physical server" )
		madeBy = new Manufacturer( name: "IBM", code: "some code", phone: "555-1234", updatedById: 1 )
		asset = new Asset( itsId: "t2k73", assetType: aType, manufacturer: madeBy )
		mockForConstraintsTests(AssetType, [aType])
		assertTrue "AssetType was not a valid object", aType.validate()
		mockForConstraintsTests(Manufacturer, [madeBy])
		assertTrue "Manufacturer was not a valid object", madeBy.validate()
		mockForConstraintsTests(Asset, [asset])
		assertTrue "Asset was not a valid object", asset.validate()
	}

	protected void tearDown() {
		super.tearDown()
	}

    void testToString() {
      	assertToString asset, "Host - t2k73"
    }

	void testNotUniqueItsId() {
		def asset2 = new Asset(
				itsId:"t2k73",  // this matches the itsId we initially set up, and thus is not unique
				updatedById: 1,
				assetType: aType,
				manufacturer: madeBy,
		);
		assertFalse "This should fail: itsId is not unique", asset2.validate()
		assertEquals "unique", asset2.errors.itsId
	}

	void testPropertyNotNull( property ) {
		def original = property.value
		asset."${property.key}" = null
		assertFalse "This did not fail as expected: ${property.key}", asset.validate()
		assertEquals "nullable", asset.errors."${property.key}"
		asset."${property.key}" = original
	}

	void testPropertyIsNull( property ) {
		asset."${property.key}" = null
		assertTrue "This did not validate as expected: ${property.key}", asset.validate()
		assertEquals null, asset."${property.key}"
	}
	
	void testPropertyNotBlank( property ) {
		def original = property.value
		asset."${property.key}" = ""
		assertFalse "This did not fail as expected: ${property.key}", asset.validate()
		assertEquals "blank", asset.errors."${property.key}"
		asset."${property.key}" = original
	}

	void testPropertyIsBlank( property ) {
		asset."${property.key}" = ""
		assertTrue "This did not validate as expected: ${property.key}", asset.validate()
		assertEquals "", asset."${property.key}"
	}
	
	void testPropertyMaxSize( property ) {
		asset."${property.key}" = "a" * ( maxSize+1 )
		assertFalse "This did not fail as expected", asset.validate()
		assertEquals "maxSize", asset.errors."${property.key}"
	}

	void testPropertyTwoDecimalPlaces( property ) {
		asset."${property.key}" = 0.145
		asset.validate()
		assertEquals 0.15f, asset."${property.key}"
	}
	
	// This function will loop through a list of given properties to test each applicable constraint
	void testListOfProperties() {

		// populate this list with all properties that should not be null
		def not_nullable = [
			'assetType',
			'manufacturer',
			'itsId', 
			'ownershipType', 
		];
		asset.properties.each { p->
			if ( not_nullable.contains(p.key)) {
				testPropertyNotNull( p )
			}
		}

		// populate this list with all properties that should not be blank strings
		def not_blank = [
			'itsId', 
			'ownershipType',
		];
		asset.properties.each { p->
			if ( not_blank.contains(p.key)) {
				testPropertyNotBlank( p )
			}
		}
		
		// populate this list with all properties that can be null
		def can_be_nullable = [
			'location',
			'purchaseListPrice',
			'purchaseDiscountPrice',
			'maintenanceListPrice',
			'maintenanceDiscountPrice',
			'productDescription',
			'modelDesignation',
			'vendorSupportLevel',
			'supportLevel',
			'serialNo',
			'endOfServiceLife',
			'warrantyEndDate',
			'decalNo',
			'retiredDate',
			'removedFromInventoryDate',
			'isAvailableForParts',
			'replacementAssetsGrp',
			'replacementTaskingGrp',
			'replacementAvailabilityDate',
			'migrationCompletionDate',
			'assetStatus',
			'postMigrationStatus',
			'purchaseContract',
			'maintenanceContract',
		];
		asset.properties.each { p->
			if ( can_be_nullable.contains(p.key)) {
				testPropertyIsNull( p )
			}
		}

		// populate this list with all properties that can be blank strings
		def can_be_blank = [
			'productDescription',
			'modelDesignation',
			'vendorSupportLevel',
			'supportLevel',
			'serialNo',
			'decalNo',
			'assetStatus',
			'postMigrationStatus',
		];
		asset.properties.each { p->
			if ( can_be_blank.contains(p.key)) {
				testPropertyIsBlank( p )
			}
		}
		
		// populate this list with all properties that should not exceed length of maxSize (45)
		def not_max_size = [
			'itsId',
			'ownershipType',
			'productDescription',
			'modelDesignation',
			'vendorSupportLevel',
			'supportLevel',
			'serialNo',
			'decalNo',
			'assetStatus',
			'postMigrationStatus',	
		];
		asset.properties.each { p->
			if ( not_max_size.contains(p.key)) {
				testPropertyMaxSize( p )
			}
		}
		
		// populate this list with all properties that should have 2 digits after the decimal
		def decimal_two = [
			'purchaseListPrice',
			'purchaseDiscountPrice',
			'maintenanceListPrice',
			'maintenanceDiscountPrice',
		];
		asset.properties.each { p->
			if ( decimal_two.contains(p.key)) {
				testPropertyTwoDecimalPlaces( p )
			}
		}
	}


}
