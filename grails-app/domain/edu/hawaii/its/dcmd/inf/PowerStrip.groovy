package edu.hawaii.its.dcmd.inf

class PowerStrip extends Asset {

    PowerBreaker breaker
    int capacity
    int powerUsed
    float temperature
    String IP
    Rack rack
    PowerStripType type

    static hasMany = [
        devices: DevicePlug
    ]

    static constraints = {
        breaker(nullable: true)
        powerUsed(nullable:true)
        temperature(nullable:true)
        capacity(nullable:true)
        IP(nullable:true)
        rack(nullable:true)
        devices(nullable:true)
        type(nullable:true)
    }
}
