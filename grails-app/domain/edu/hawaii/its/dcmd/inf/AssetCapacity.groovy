/*
 * Copyright (c) 2014 University of Hawaii
 *
 * This file is part of DataCenter metadata (DCmd) project.
 *
 * DCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DCmd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCmd.  It is contained in the DCmd release as LICENSE.txt
 * If not, see <http://www.gnu.org/licenses/>.
 */

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
