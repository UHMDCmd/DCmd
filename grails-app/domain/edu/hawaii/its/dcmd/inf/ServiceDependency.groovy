package edu.hawaii.its.dcmd.inf

class ServiceDependency {

    Service service
    Service dependsOn
    String generalNote

    static belongsTo = [service: Service]

    static constraints = {
        generalNote(nullable:true)
    }
}
