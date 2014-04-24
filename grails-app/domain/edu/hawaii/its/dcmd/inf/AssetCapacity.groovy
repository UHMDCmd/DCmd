package edu.hawaii.its.dcmd.inf

import java.util.Date;

class AssetCapacity {
	
	PhysicalServer asset
    String resourceType
    String unitType
    Float currentCapacity = 0.0f
    Float unassignedCapacity = 0.0f
    Float maxExpandableCapacity
	Date dateCreated = new Date()
    String capacityNotes

	static belongsTo = [ asset : PhysicalServer ]


    static auditable = true



    static constraints = {
        resourceType(nullable: false)
        unitType(nullable: true)
		asset(nullable: false)
        resourceType(nullable: true)
        currentCapacity(nullable: false, matches: "[0-9]")
        maxExpandableCapacity(nullable: true)
        dateCreated(nullable: true)
        capacityNotes(nullable: true, maxSize: 1024)
        unassignedCapacity(nullable: true)
    }
	
	String toString() {
		"${asset.toString()} ${currentCapacity} ${resourceType} ${unitType}"
	}
    Float plus(Float value) {
        currentCapacity + value
    }
    Float plus(AssetCapacity value) {
        currentCapacity + value.currentCapacity
    }

    Float totalReserved() {
        def total = 0.0
        def resourceAllocations = ResourceAllocation.createCriteria().list {
            like("asset.id", asset.id)
            like("resourceType", resourceType)
        }
        for (it in resourceAllocations) {
            if(it.reservedAmount)
                total += it.reservedAmount
        }
        return total
    }

    Float totalAllocated() {
        def total = 0.0
        def resourceAllocations = ResourceAllocation.createCriteria().list {
            like("asset.id", asset.id)
            like("resourceType", resourceType)
        }
        for (it in resourceAllocations) {\
            if(it.allocatedAmount)
                total += it.allocatedAmount
        }
        return total
    }
    public Long clusterId() {
        asset.cluster.id
    }
}
