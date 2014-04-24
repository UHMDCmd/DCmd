package edu.hawaii.its.dcmd.inf

/**
 * 
 * @author Jesse
 * @class PersonSupportRoleService class that implements PSR objects, used by controller 
 */
class PersonSupportRoleService {

    static transactional = true

    def getOrCreatePersonSupportRole(long personId, long supportRoleId, long supportableObjectTypeId) {

		log.debug "personId: ${personId}"
		log.debug "supportRoleId: ${supportRoleId}"
		log.debug "supportableObjectId: ${supportableObjectTypeId}"
		
		def crit = PersonSupportRole.createCriteria()
		def personSupportRole = crit.get(){
			person{
				eq "id", personId
			}
			supportRole{
				eq "id", supportRoleId
			}
			supportableObjectType{
				eq "id", supportableObjectTypeId
			}
		}
		
		/*
		 * If the criteria for the PSR is not found, creates a new PSR object with input data 
		 */
		if(personSupportRole == null){
			log.debug "Creating new psr -- existing not found"
			personSupportRole = new PersonSupportRole(person:Person.get(personId),
					supportRole:SupportRole.get(supportRoleId),
					supportableObjectType:SupportableObjectType.get(supportableObjectTypeId))
			personSupportRole.save(failOnError:true, flush:true)
		}
		personSupportRole
    }
}
