package edu.hawaii.its.dcmd.inf

class PowerBreaker extends Asset{

    String label
    String ipAddress
    Integer pole_1
    Integer pole_2
    Integer voltage
    Rack rack

    //tree variables
    int capacity
    PowerPanel panel
    //int voltage

  // static belongsTo = [panel:PowerPanel]
    static hasMany = [strips: PowerStrip]

/*    static mapping = {
        strips cascade: 'all-delete-orphan'
    }*/

    static constraints = {
        rack(nullable:true)
        label(nullable: true)
        ipAddress(nullable: true)
        pole_1(nullable: true)
        pole_2(nullable: true)
        voltage (nullable:  true)

        //tree variables
        panel(nullable:true)
        capacity(nullable:true)
        strips(nullable:true)
      //  voltage(nullable:true)

    }

    //tree method
    def totalDraw() {
        def total = 0
        strips.each {
            total += it.powerUsed
        }
        return total
    }
}
