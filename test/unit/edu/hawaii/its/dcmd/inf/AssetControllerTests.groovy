//package edu.hawaii.its.dcmd.inf
//
//import grails.test.*
//import org.gmock.*
//import static org.hamcrest.Matchers.*
//import grails.converters.JSON
//import edu.hawaii.its.dcmd.inf.AssetService
//
//@WithGMock
//class AssetControllerTests extends ControllerUnitTestCase {
//    protected void setUp() {
//        super.setUp()
//    }
//
//    protected void tearDown() {
//        super.tearDown()
//    }
//
//    void testListJSON() {
//		def assetService = mock(AssetService)
//		//return can be anything; just want to check that controller returns it as JSON
//		assetService.listAssets(instanceOf(Map)).returns([new Asset()])
//		controller.assetService = assetService
//
//		play{
//			controller.listJSON()
//			def contentString = controller.response.contentAsString
//			assertNotNull contentString
//			def contentJSON = JSON.parse(contentString)
//			assertFalse contentJSON.isEmpty()
//		}
//    }
//
//	void testSaveSuccess(){
//
//		//mock the asset service and its calls
//		def mockAssetService = mock(AssetService)
//		mockAssetService.cleanseCloneParams(mockParams)
//		mockAssetService.logParams(mockParams)
//
//		//mock the asset created by the controller
//		def mockAsset = mock(Asset, constructor(mockParams))
//		mockAssetService.removeDeletedNotes(mockAsset)
//
//		//mock a successful save and the id called in the redirect and message
//		mockAsset.save(flush:true).returns(true)
//		mockAsset.id.returns(14).times(2)
//
//		//replace the injected service with the mockAssetService
//		controller.assetService = mockAssetService
//
//		//mock out the flash mesage
//		controller.metaClass.message = { LinkedHashMap args ->
//			controller.metaClass.message = { LinkedHashMap key ->
//				assertEquals "default.created.message", key.code
//				return 'test message output'
//			}
//			assertEquals 'asset.label', args.code
//			assertEquals 'Asset', args.default
//		}
//
//		//playback for GMock expectations
//		play{
//			def model = controller.save()
//			assertEquals 'test message output', controller.flash.message
//			assertEquals "show", controller.redirectArgs.action
//			assertEquals 14, controller.redirectArgs.id
//		}
//	}
//
//	void testSaveFailure(){
//		//mock the asset service and its calls
//		def mockAssetService = mock(AssetService)
//		mockAssetService.cleanseCloneParams(mockParams)
//		mockAssetService.logParams(mockParams)
//
//		//mock the asset created by the controller
//		def mockAsset = mock(Asset, constructor(mockParams))
//		mockAssetService.removeDeletedNotes(mockAsset)
//
//		//mock a failed save
//		mockAsset.save(flush:true).returns(false)
//
//		//replace the injected service with the mockAssetService
//		controller.assetService = mockAssetService
//		play{
//			def model = controller.save()
//			assertEquals mockAsset, model.assetInstance
//		}
//	}
//
//	void testUpdateAssetNotFound(){
//
//		mockParams.id = 9
//
//		//mock the asset service and its calls
//		def mockAssetService = mock(AssetService)
//		mockAssetService.cleanseCloneParams(mockParams)
//		mockAssetService.logParams(mockParams)
//
//		//mock the asset retrieved by the controller
//		mock(Asset).static.get(mockParams.id).returns(null)
//
//		//mock out the flash mesage
//		controller.metaClass.message = { LinkedHashMap args ->
//			controller.metaClass.message = { LinkedHashMap key ->
//				assertEquals "default.not.found.message", key.code
//				return 'test message output'
//			}
//			assertEquals 'asset.label', args.code
//			assertEquals 'Asset', args.default
//		}
//
//		controller.assetService = mockAssetService
//		play{
//			controller.update()
//			assertEquals 'list', controller.redirectArgs.action
//			assertEquals 'test message output', controller.flash.message
//		}
//	}
//
//	void testUpdateFailedVersion(){
//
//		mockParams.id = 9
//		mockParams.version = 0
//
//		def mockAsset = mock(Asset)
//		mockAsset.asBoolean().returns(true)
//
//		//mock the asset service and its calls
//		def mockAssetService = mock(AssetService)
//		mockAssetService.cleanseCloneParams(mockParams)
//		mockAssetService.logParams(mockParams)
//		mockAssetService.failedVersionCheck(instanceOf(Asset), mockParams.version, instanceOf(java.util.Locale)).returns(true)
//
//		//mock the asset retrieved by the controller
//		mock(Asset).static.get(mockParams.id).returns(mockAsset)
//
//		controller.assetService = mockAssetService
//		play{
//			def model = controller.update()
//		}
//	}
//
//	void testUpdateSuccess(){
//
//		mockParams.id = 9
//		mockParams.version = 0
//
//		def mockAsset = mock(Asset)
//		mockAsset.asBoolean().returns(true)
//		mockAsset.properties.set(mockParams)
//		mockAsset.hasErrors().returns(false)
//		mockAsset.save(flush:true).returns(true)
//		mockAsset.id.returns(mockParams.id).times(2)
//
//		//mock the asset retrieved by the controller
//		mock(Asset).static.get(mockParams.id).returns(mockAsset)
//
//		//mock the asset service and its calls
//		def mockAssetService = mock(AssetService)
//		mockAssetService.cleanseCloneParams(mockParams)
//		mockAssetService.logParams(mockParams)
//		mockAssetService.failedVersionCheck(mockAsset, mockParams.version, instanceOf(java.util.Locale)).returns(false)
//		mockAssetService.removeDeletedNotes(mockAsset)
//
//		//mock out the message retrieval from i18n
//		controller.metaClass.message = { LinkedHashMap second ->
//			controller.metaClass.message = { LinkedHashMap first ->
//				assertEquals "default.updated.message", first.code
//				assertNull first.default
//				assertEquals 2, first.args.size()
//				assertEquals 'Asset', first.args[0]
//				assertEquals mockParams.id, first.args[1]
//				return "Asset ${mockParams.id} updated"
//			}
//			assertEquals 'asset.label', second.code
//			assertEquals 'Asset', second.default
//			return 'Asset'
//		}
//
//		controller.assetService = mockAssetService
//		play{
//			controller.update()
//			assertEquals mockParams.id, controller.redirectArgs.id
//			assertEquals 'show', controller.redirectArgs.action
//			assertEquals "Asset ${mockParams.id} updated", controller.flash.message
//		}
//	}
//
//}
