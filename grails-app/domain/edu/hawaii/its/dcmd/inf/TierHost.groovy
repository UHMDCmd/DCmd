package edu.hawaii.its.dcmd.inf

class TierHost {

    Host host
    Tier tier

    ResourceType resourceType
    UnitType unitType
    int amountAssigned



    static constraints = {
        resourceType(nullable:true)
        unitType(nullable:true)
        amountAssigned(nullable:true, blank:true)
        host(nullable: false)
        tier(nullable: false)
    }
}
