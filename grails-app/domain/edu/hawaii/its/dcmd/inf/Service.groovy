package edu.hawaii.its.dcmd.inf

class Service extends SupportableObject {

    ServiceLevelAgreement sla

    String serviceTitle
    String serviceDescription
    Status status
    String generalNote
    Environment env
    Date dateCreated = new Date()
    Date lastUpdated = new Date()

    String supportableType = "service"

    Application application

    static auditable = true

    static belongsTo = [application: Application]

    static mapping = {
        dependencySet cascade: 'all-delete-orphan'
        tablePerHierarchy false
    }

    static hasMany = [
            tiers: TierDependency,
            dependencySet: ServiceDependency
    ]

    static constraints = {
        serviceTitle(nullable:false, blank:false)
        serviceDescription(nullable:true)
        status(nullable:true)
        generalNote(nullable: true, maxSize: 1024)
        sla(nullable: true)
        env(nullable: true)
        tiers(nullable:true)

    }

    static mappedBy = [dependencySet: 'service']

    String toString() {
        "${serviceTitle} (${env?.abbreviation})"
    }

}
