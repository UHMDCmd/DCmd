package edu.hawaii.its.dcmd.inf

class RackUnit {

    String RUstatus
    Rack onRack
    Asset filledBy
    Asset planFill
    String label
    String note
    int imageId
    int ru_slot
    int slotSize
    int connector

    static belongsTo = [onRack: Rack]

    static auditable = true


    static constraints = {
        RUstatus(nullable: true)
        filledBy(nullable:true)
        planFill(nullable:true)
        onRack(nullable: true)
        label(nullable:true)
        note(nullable: true)
        imageId (nullable: true)
        ru_slot(nullable: true)
        slotSize(nullable:true)
    }

    String toString() {
        label
    }
}
