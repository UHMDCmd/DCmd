package edu.hawaii.its.dcmd.inf

class Resource {

	ResourceType resourceType
	UnitType unitType
	Float capacity
	
//	static belongsTo = [asset : Asset]
//	static hasMany = [resourceSlices : ResourceSlice, usableCapacities : UsableCapacity]

	
//	static mapping = {
//		resourceSlices cascade: "all-delete-orphan"
//		usableCapacities cascade: "all-delete-orphan"
//	}

	static constraints = {
		capacity( nullable: true )
	}

	String toString() {
		// for example, 100.0 GB RAM
		"${capacity} ${unitType} ${resourceType}"
	}
}
