package edu.hawaii.its.dcmd.inf

class Rack extends Asset{

    Integer ruCap
    String rackNum
    String railType

    List RUs = new ArrayList();

    static auditable = true

    static hasMany = [
   //     assets: Asset,
        RUs: RackUnit,
        strips: PowerStrip
    ]

    static mappedBy = [RUs: 'onRack']

    static constraints = {
        ruCap(nullable: false)
        rackNum(nullable:true)
        railType(nullable: true)
     //   assets(nullable:true)
        RUs(nullable:true)
        strips(nullable:true)
    }


    String toString() {
        itsId
    }

    void Initialize() {
        //commented out for itc rack implementation
        def i
        for(i=0; i<45; i++) {
            RUs.add(new RackUnit(RUstatus: 'Open', label: 'Open', onRack: this, ru_slot: (i+1)))
        }
    }

    RackUnit getUnitBySlot(Rack rack, int num){
        RackUnit unit = null
        ArrayList<RackUnit> collection = rack.RUs
        for (int x = 0; x < collection.size(); x ++){
             if(collection.get(x).ru_slot == num){
                unit = collection.get(x)
             }
        }
        return unit
    }



}