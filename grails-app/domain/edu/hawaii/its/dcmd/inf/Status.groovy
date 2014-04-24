package edu.hawaii.its.dcmd.inf

class Status {

    String abbreviation
    String description
    static constraints = {
        abbreviation(nullable: false)
        description(nullable:true)
    }

    static auditable = true


    static hasMany = [
            applications:Application,
            hosts:Host,
            services:Service
    ]


    String toString() {
        abbreviation
    }
}
