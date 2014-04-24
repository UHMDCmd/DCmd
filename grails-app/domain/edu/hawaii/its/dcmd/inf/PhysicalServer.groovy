package edu.hawaii.its.dcmd.inf

class PhysicalServer extends Asset{

    String serverType

    static hasMany = [
            assetCapacities:AssetCapacity,
            hosts: Host,
            devicePlugs: DevicePlug
    ]

    static auditable = true

    static constraints = {
//        globalZone(nullable:true)
        serverType(nullable:true)
        hosts(nullable: true)
        devicePlugs(nullable:true)
    }

//    static belongsTo = [hosts: Host]

    static mapping = {
//        hosts cascade: 'all'
//        globalZone cascade: 'save-update'
//        resourceAllocations cascade: 'save-update'
//        discriminator column: "supportableType", value: 'physicalServer'
//        tablePerHierarchy true
    }

    Host getGlobalZone() {
        def globalZone

        globalZone = Host.createCriteria().list() {
            like('type', this.serverType)
            like('asset.id', this.id)
        }
        if(globalZone == null || globalZone.isEmpty())
            return null
        else
            return globalZone?.first()
    }
}