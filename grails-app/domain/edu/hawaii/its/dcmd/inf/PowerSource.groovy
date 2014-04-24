package edu.hawaii.its.dcmd.inf

class PowerSource extends Asset{

    String label
    String dataCenter
    Integer capacity
    String ipAddress
    String SNMP_community
    String load_OID
   // String generalNote

    //tree variables
    //capacity

    static hasMany = [panels:PowerPanel]

    static constraints = {
       label(nullable: true)
       dataCenter(nullable: true)
       capacity(nullable:  true)
       ipAddress(nullable: true)
       SNMP_community(nullable:  true)
       load_OID(nullable: true)
      // generalNote(nullable:  true)

        //tree variables
        panels(nullable:true)

        //capacity is same variable

    }


    def getAllSources(){
        ArrayList<String> sourceNames = new ArrayList<String>()
        ArrayList<PowerSource> feeds = PowerSource.findAll()

        int count = PowerSource.count
        for(int x = 0; x < count; x++){
        sourceNames.add(feeds.get(x).label)
        }
       return sourceNames
    }

    //tree method
    int totalDraw() {
        def total = 0
        panels.each {
            total += it.totalDraw()
        }
        return total
    }

}
