package edu.hawaii.its.dcmd.inf

class TierType {

    String abbreviation
    String description

    static constraints = {
        abbreviation(nullable:false)
        description(nullable:true)
    }

    String toString(){
        abbreviation
    }

}
