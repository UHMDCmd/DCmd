package edu.hawaii.its.dcmd.inf

class PowerPanel extends Asset {

    Integer panel_id
    String label
    Integer breaker_poles
    Integer mainBreakerAmperage
    Integer panelVoltage
    String numberingScheme //0 = even, 1 = 0dd

    //tree variable
    PowerSource source
    int capacity
    int poles

   // static belongsTo = [powersource: PowerSource]
    static hasMany = [breakers:PowerBreaker]


//    static mapping = {
//        breakers cascade: 'all-delete-orphan'
//    }

    static constraints = {
        label(nullable: true)
        panel_id(nullable: true)
        breaker_poles(nullable: true)
        mainBreakerAmperage(nullable: true)
        panelVoltage(nullable:  true)
        numberingScheme(nullable:  true)

        //tree variables
        source(nullable:true)
        breakers(nullable:true)
        capacity(nullable:true)
        poles(nullable:true)
    }

    int breakersInUse(){
        return breakers.size()
    }

    //tree method
    int totalDraw() {
        def total = 0
        breakers.each {
            total += it.totalDraw()
        }
        return total
    }

}
