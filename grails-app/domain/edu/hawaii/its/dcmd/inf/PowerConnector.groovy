package edu.hawaii.its.dcmd.inf

class PowerConnector {

    String name
    int voltage
    int amps

    static constraints = {
        name(nullable: true)
        voltage(nullable:true)
        amps(nullable:true)
    }

    String toString() {
        name
    }
}
