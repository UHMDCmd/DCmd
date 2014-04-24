package edu.hawaii.its.dcmd.inf

class ApplicationServerAssignment {

    Host host
    Application application

    String resourceType
    String unitType
    Float amountReserved
    Float amountAllocated

    String assignmentNote

    static belongsTo = [host: Host, application: Application]

    static constraints = {
        assignmentNote(nullable: true, maxSize: 1024)
        host(nullable: false)
        application(nullable: false)
        resourceType(nullable: false)
        unitType(nullable: true)
        amountReserved(nullable: true)
        amountAllocated(nullable: true)
    }
}
