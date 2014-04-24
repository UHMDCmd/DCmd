package edu.hawaii.its.dcmd.inf

import org.hibernate.Criteria

class Cluster {

    String dataCenter
    String name
    Date lastUpdated = new Date()
	Date dateCreated = new Date()
    String generalNote
    String planningNote
    String changeNote

    static hasMany = [
        assetSet:Asset,
        hostSet:Host
//        resourceAllocations:ResourceAllocation,
    ]

    static constraints = {
        dataCenter(nullable: true)
        name(nullable: false)
		assetSet(nullable: true)
//        resourceAllocations(nullable: true)
        hostSet(nullable:true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        generalNote(nullable: true, maxSize: 1024)
        planningNote(nullable: true, maxSize: 1024)
        changeNote(nullable: true, maxSize: 1024)
    }
	
	String toString() {
		name
	}

    String totalCPU() {
        def total = 0.0
        def assets = AssetCapacity.createCriteria().list() {
            'in'("asset", this.assetSet)
            like("resourceType", "CPU")
        }
        for(it in assets) {
            total += it.currentCapacity
        }
        return "${total} Threads"
    }
    String reservedCPU() {
        def total = 0.0
        def assets = AssetCapacity.createCriteria().list() {
            'in'("asset", this.assetSet)
            like("resourceType", "CPU")
        }
        for(it in assets) {
            total += it.totalReserved()
        }
        return "${total} Threads"
    }
    String allocatedCPU() {
        def total = 0.0
        def assets = AssetCapacity.createCriteria().list() {
            'in'("asset", this.assetSet)
            like("resourceType", "CPU")
        }
        for(it in assets) {
            total += it.totalAllocated()
        }
        return "${total} Threads"
    }


    String totalMemory() {
        def total = 0.0
        def assets = AssetCapacity.createCriteria().list() {
            'in'("asset", this.assetSet)
            like("resourceType", "Memory")
        }
        for(it in assets) {
            total += it.currentCapacity
        }
        return "${total} GB"
    }
    String reservedMemory() {
        def total = 0.0
        def assets = AssetCapacity.createCriteria().list() {
            'in'("asset", this.assetSet)
            like("resourceType", "Memory")
        }
        for(it in assets) {
            total += it.totalReserved()
        }
        "${total} GB"
    }
    String allocatedMemory() {
        def total = 0.0
        def assets = AssetCapacity.createCriteria().list() {
            'in'("asset", this.assetSet)
            like("resourceType", "Memory")
        }
        for(it in assets) {
            total += it.totalAllocated()
        }
        return "${total} GB"
    }
}
