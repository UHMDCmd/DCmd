//package edu.hawaii.its.dcmd.inf
//
//import grails.test.*
//import org.gmock.*
//import static org.hamcrest.Matchers.*
//import grails.orm.PagedResultList
//import org.codehaus.groovy.grails.commons.GrailsApplication
//import org.springframework.context.ApplicationContext
//import edu.hawaii.its.dcmd.inf.AssetService;
//
////@WithGMock
//class AssetServiceTests extends GrailsUnitTestCase {
//
//	def asset
//
//    protected void setUp() {
//        super.setUp()
//
//		mockLogging(AssetService)
//
//		def assetType = new AssetType(name:'assetType')
//		mockDomain(AssetType, [assetType])
//		assetType.save(failOnError:true, flush:true)
//
//		def madeBy = new Manufacturer( name: "IBM", code: "some code", phone: "555-1234", updatedById: 1 )
//
//		asset = new Asset(id:14,
//			itsId:'server1',
//			assetType: assetType,
//			manufacturer: madeBy,
//			productDescription: 'prod desc',
//			modelDesignation: 'model designation',
//			vendorSupportLevel:'support level',
//			serialNo:'12345',
//			endOfServiceLife: new Date(2000,1,1),
//			warrantyEndDate: new Date(2000,1,2),
//			purchaseListPrice: 1F,
//			purchaseDiscountPrice: 2F,
//			maintenanceListPrice: 3F,
//			maintenanceDiscountPrice:4F,
//			decalNo:"decal1",
//			retiredDate: new Date(2000,1,3),
//			removedFromInventoryDate: new Date(2000,1,4),
//			replacementAvailabilityDate: new Date(2000,1,5),
//			migrationCompletionDate: new Date(2000,1,6),
//			assetStatus: 'asset status',
//			postMigrationStatus: 'post migration status',
//			dateCreated: new Date(2000,1,7),
//			lastUpdated: new Date(2000,1,8))
//		mockDomain(Asset, [asset])
//		asset.save(failOnError:true, flush:true)
//    }
//
//    protected void tearDown() {
//        super.tearDown()
//    }
//
//    void testListAssets() {
//
//		def ps = [:]
//		ps.sEcho = "echo"
//		ps.iSortCol_0 = "1"
//
//		def returnedData = new PagedResultList(Asset.list())
//		def mockCriteria = mock(){
//			list(instanceOf(Map), instanceOf(Closure)).returns(returnedData)
//		}
//		mock(Asset).static.createCriteria().returns(mockCriteria)
//
//		def mockCountCriteria = mock(){
//			get(instanceOf(Closure)).returns(1)
//		}
//		mock(Asset).static.createCriteria().returns(mockCountCriteria)
//
//		play{
//			def assetService = new AssetService()
//			def dataToRender = assetService.listAssets(ps)
//			assertNotNull dataToRender
//			assertEquals returnedData.list.size(), dataToRender.iTotalRecords
//			assertEquals returnedData.getTotalCount(), dataToRender.iTotalDisplayRecords
//			assertEquals returnedData.list.size(), dataToRender.aaData.size()
//			assertEquals ps.sEcho, dataToRender.sEcho
//			assertEquals returnedData.list[0].id, dataToRender.aaData[0][0]
//			assertEquals returnedData.list[0].itsId, dataToRender.aaData[0][1]
//			assertEquals returnedData.list[0].assetType.name, dataToRender.aaData[0][2]
//			assertEquals returnedData.list[0].ownershipType, dataToRender.aaData[0][3]
//			assertEquals returnedData.list[0].productDescription, dataToRender.aaData[0][4]
//			assertEquals returnedData.list[0].modelDesignation, dataToRender.aaData[0][5]
//			assertEquals returnedData.list[0].vendorSupportLevel, dataToRender.aaData[0][6]
//			assertEquals returnedData.list[0].serialNo, dataToRender.aaData[0][7]
//			assertEquals returnedData.list[0].endOfServiceLife, dataToRender.aaData[0][8]
//			assertEquals returnedData.list[0].warrantyEndDate, dataToRender.aaData[0][9]
//			assertEquals returnedData.list[0].purchaseListPrice, dataToRender.aaData[0][10]
//			assertEquals returnedData.list[0].purchaseDiscountPrice, dataToRender.aaData[0][11]
//			assertEquals returnedData.list[0].maintenanceListPrice, dataToRender.aaData[0][12]
//			assertEquals returnedData.list[0].maintenanceDiscountPrice, dataToRender.aaData[0][13]
//			assertEquals returnedData.list[0].decalNo, dataToRender.aaData[0][14]
//			assertEquals returnedData.list[0].retiredDate, dataToRender.aaData[0][15]
//			assertEquals returnedData.list[0].removedFromInventoryDate, dataToRender.aaData[0][16]
//			assertEquals returnedData.list[0].isAvailableForParts, dataToRender.aaData[0][17]
//			assertEquals returnedData.list[0].replacementAvailabilityDate, dataToRender.aaData[0][18]
//			assertEquals returnedData.list[0].migrationCompletionDate, dataToRender.aaData[0][19]
//			assertEquals returnedData.list[0].assetStatus, dataToRender.aaData[0][20]
//			assertEquals returnedData.list[0].postMigrationStatus, dataToRender.aaData[0][21]
//			assertEquals returnedData.list[0].dateCreated, dataToRender.aaData[0][22]
//			assertEquals returnedData.list[0].lastUpdated, dataToRender.aaData[0][23]
//		}
//    }
//
//	void testCleanseParams(){
//		def params = [:]
//		params['notesList[_clone].noteType.id'] = 1
//		params['notesList[_clone].new'] = false
//		params['notesList[_clone].text'] = 'text'
//		params['notesList[_clone].id'] = 2
//		params['notesList[_clone]'] = null
//		params['notesList[_clone].deleted'] = true
//		assertEquals 6, params.size()
//
//		def assetService = new AssetService()
//		assetService.cleanseCloneParams(params)
//
//		assertEquals 0, params.size()
//	}
//
//	void testFailedVersionCheckWithNullVersion(){
//		def assetService = new AssetService()
//		assertFalse assetService.failedVersionCheck(null, null, null)
//	}
//
//	void testFailedVersionCheckWithAssetVersionNotGreaterThanVersion(){
//		def assetService = new AssetService()
//		def version = "0"
//		asset.version = 0
//		assertFalse assetService.failedVersionCheck(asset, version, null)
//	}
//
//	void testFailedVersionCheckWithAssetVersionGreaterThanVersion(){
//
//		//mock out the main context that provides message lookup
//		def mockContext = mock(ApplicationContext)
//		mockContext.getMessage(instanceOf(String), null, instanceOf(String), instanceOf(Locale)).returns('message')
//
//		//mock out the grailsApplication service and provide a mock application context
//		def mockGrailsApplication = mock(GrailsApplication)
//		mockGrailsApplication.getMainContext().returns(mockContext)
//
//		play{
//
//			//create the service and sub the mocked grails application service
//			def assetService = new AssetService()
//			assetService.grailsApplication = mockGrailsApplication
//
//			//set the version of asset higher to trigger the error
//			def version = "0"
//			asset.version = 1
//
//			//call the version check and ensure error was added
//			assertTrue assetService.failedVersionCheck(asset, version, new java.util.Locale("en"))
//			assertTrue asset.hasErrors()
//			assertEquals "default.optimistic.locking.failure", asset.errors.version
//		}
//	}
//
//}
