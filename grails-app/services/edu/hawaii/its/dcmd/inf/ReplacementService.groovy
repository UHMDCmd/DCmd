package edu.hawaii.its.dcmd.inf

/**
 * 
 * @author Jesse
 * @class PersonSupportRoleService class that implements PSR objects, used by controller 
 */
class ReplacementService {

    static transactional = true

    def getOrCreateReplacement(long assetId, long replacementAsset, int priority,
                               String replacement_notes) {

        printf("assetId: %lf, replacementId: %lf...\n", assetId, replacementAsset)
		log.debug "replacementId: ${assetId}"
		log.debug "replacementAsset: ${replacementAsset}"

		
		def crit = Replacement.createCriteria()
		def replacement = crit.get(){
            eq("main_asset", "assetId")
            eq("replacement", "replacementAsset")
		}
		
		/*
		 * If the criteria for the PSR is not found, creates a new PSR object with input data 
		 */

		if(replacement == null){
			log.debug "Creating new psr -- existing not found"
            printf("Creating new replacement!")
			replacement = new Replacement(main_asset:Asset.get(assetId),
					replacement:Asset.get(replacementAsset), priority: priority,
                    replacement_notes: replacement_notes)
//					supportableObjectType:SupportableObjectType.get(supportableObjectTypeId))
			replacement.save(failOnError:true, flush:true)
		}
		replacement
    }
}
