package edu.hawaii.its.dcmd.inf

class ResourceAllocation {

    Cluster cluster
    Host host
    PhysicalServer asset
    String resourceType
    String unitType
    Float reservedAmount
    Float allocatedAmount
    Boolean isFixed
    Date dateAssigned
    String allocNotes

    static belongsTo = [host: Host]

    static constraints = {
        cluster(nullable: true)
        host(nullable: false)
        asset(nullable: true)
        resourceType(nullable: false)
        reservedAmount(nullable: true)
        unitType(nullable: true)
        allocatedAmount(nullable: true)
        isFixed(nullable: true, default: false)
        allocNotes(nullable: true, maxSize: 1024)
        dateAssigned(nullable: true)
    }
}