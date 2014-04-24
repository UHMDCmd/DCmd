package edu.hawaii.its.dcmd.inf

class PowerStripType {

    String name

    List<PowerConnector> connectors = []

    static hasMany = [
      //  connectors:PowerConnector
    ]

    static constraints = {
        name(nullable:true)
       // connectors(nullable:true)
    }

    String toString() {
        name
    }
}
