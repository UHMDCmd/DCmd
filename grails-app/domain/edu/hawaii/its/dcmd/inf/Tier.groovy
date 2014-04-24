package edu.hawaii.its.dcmd.inf

class Tier {

    def personService

    String tierName
    String tierDescription
    Application mainApp
    Boolean loadBalanced = false;
    TierType type

    String generalNote

    Host host

    static auditable = true


    static belongsTo = [mainApp: Application]

    static hasMany = [
            services: ServiceDependency,
            instanceDependencies: TierDependency
//            hosts: TierHost,
    ]
    static mapping = {
        instanceDependencies: 'all-delete-orphan'
    }

    static constraints = {
        tierName(nullable: true, blank: true)
        services(nullable: true)
        host(nullable:true)
        generalNote(nullable:true)
        tierDescription(nullable:true)
        loadBalanced(nullable: false, default:false)
        type(nullable:true)
    }

    String toString() {
        tierName
    }
    String getMainServiceLink() {
        def tierDeps = TierDependency.createCriteria().list() {
            like('tier.id', id)
            eq('serviceInstance', true)
        }
        if(tierDeps.empty)
            return ""
        else
            return "<a href='../service/show?id=${tierDeps.first()?.service?.id}'>${tierDeps.first()?.service.toString()}</a>"
    }

    String getMainServiceAdminLink() {
        def tierDeps = TierDependency.createCriteria().list() {
            like('tier.id', id)
            eq('serviceInstance', true)
        }
        if(tierDeps.empty)
            return ""
        else {
            def theAdmin = personService.getAdmin(tierDeps.first()?.service)
            return "<a href='../person/show?id=${theAdmin?.id}'>${theAdmin.toString()}</a>"
        }
    }
}
