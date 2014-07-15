/*
 * Copyright (c) 2014 University of Hawaii
 *
 * This file is part of DataCenter metadata (DCmd) project.
 *
 * DCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DCmd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCmd.  It is contained in the DCmd release as LICENSE.txt
 * If not, see <http://www.gnu.org/licenses/>.
 */

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
