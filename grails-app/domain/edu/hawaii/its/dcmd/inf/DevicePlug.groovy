package edu.hawaii.its.dcmd.inf

class DevicePlug {

    PhysicalServer device
    PowerStrip strip

    PowerConnector connector

    static constraints = {
        connector(nullable:true)
    }
}
