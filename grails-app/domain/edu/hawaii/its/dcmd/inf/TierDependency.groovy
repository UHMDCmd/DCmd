package edu.hawaii.its.dcmd.inf

class TierDependency {

    Service service
    Tier tier
    boolean serviceInstance
    String generalNote

    static belongsTo = [service: Service, tier: Tier]

    static auditable = true


    static constraints = {
        generalNote(nullable: true, maxSize: 1024)
        serviceInstance(nullable:false, default:false)
    }
}
