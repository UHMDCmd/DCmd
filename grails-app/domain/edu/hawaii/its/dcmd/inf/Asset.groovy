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

import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.FactoryUtils;

class Asset extends SupportableObject{

	AssetType assetType
	Location location
	Contract purchaseContract
	Contract maintenanceContract
	Manufacturer manufacturer
//    Replacement replacementSet

    String vendor
	String itsId
	String ownershipType = "Perpetual"
	String productDescription
	String modelDesignation
	String vendorSupportLevel
	String supportLevel
	String serialNo
    String CSI
    Date endOfServiceLife
	Date warrantyEndDate
//	Float purchaseListPrice
//	Float purchaseDiscountPrice
//	Float maintenanceListPrice
//	Float maintenanceDiscountPrice
	String decalNo
	Date retiredDate
	Date removedFromInventoryDate
	Boolean isAvailableForParts
//	Integer replacementAssetsGrp
//	Integer replacementTaskingGrp
//	Date replacementAvailabilityDate
	Date migrationCompletionDate
	Status status
	String postMigrationStatus
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
    String generalNote
    String changeNote
    String planningNote
    String supportableType = "asset"
    Integer RU_size
    Integer RU_begin
    Integer RU_planned_begin
    Boolean rackable = true
    Boolean inStorage = false

    Cluster cluster

    static auditable = true



    // List notes = new ArrayList()
	/*
	static mapping = {
		//if a note is removed from an asset, it should be deleted
		notes cascade:'all-delete-orphan'
	}
	*/
    static mapping = {
        replacementSet cascade: 'save-update'
        tablePerHierarchy false
    }

	static hasMany = [
		supportStaff:PersonSupportRole,
		purchaseContract:AssetPurchaseContract, 
		maintenanceContract:AssetMaintenanceContract,
        replacementSet: Replacement,
        RUplacement: RackUnit,
        RUplanning: RackUnit
	]

    static mappedBy = [replacementSet: 'main_asset', RUplacement:  'filledBy', RUplanning: 'planFill']
	
	static constraints = {
		assetType(nullable:false)
		location(nullable:true)

        vendor(nullable:true)
        cluster(nullable:true)
        itsId (blank: false, nullable:false, maxSize:45)
        ownershipType( blank:false, maxSize:45 )
		productDescription(nullable:true, blank:true, maxSize:45)
		modelDesignation(nullable:true, blank:true, maxSize:45)
		vendorSupportLevel(nullable:true, blank:true, maxSize:45)
		supportLevel(nullable:true, blank:true, maxSize:45)
		serialNo(nullable:true, blank:true, maxSize:45)
        CSI(nullable:true, blank:true)
        endOfServiceLife(nullable:true)
		warrantyEndDate(nullable:true)
//		purchaseListPrice(nullable:true, scale:2)
//		purchaseDiscountPrice(nullable:true, scale:2)
//		maintenanceListPrice(nullable:true, scale:2)
//		maintenanceDiscountPrice(nullable:true, scale:2)
		decalNo(nullable:true, blank:true, maxSize:45)
		retiredDate(nullable:true)
		removedFromInventoryDate(nullable:true)
		isAvailableForParts(nullable:true, default:false)
//		replacementAssetsGrp(nullable:true)
//		replacementTaskingGrp(nullable:true)
//		replacementAvailabilityDate(nullable:true)
		migrationCompletionDate(nullable:true)
		status(nullable:true)
		postMigrationStatus(nullable:true, blank:true, maxSize:45)
		purchaseContract(nullable:true)
		maintenanceContract(nullable:true)
        replacementSet(nullable: true)
        manufacturer(nullable:true)
//        virtualResources(nullable: true)
	    generalNote(nullable: true, maxSize:1024)
        changeNote(nullable: true, maxSize:1024)
        planningNote(nullable: true, maxSize:1024)
        rackable(nullable:true, default:true)
        RU_planned_begin(nullable: true, min: 0, max: 45, default:0)
        RU_begin(nullable: true, min: 0, max: 45, default: 0)
        RU_size(nullable:true, min: 0, max: 45, default: 0)
        inStorage(nullable:true, default:false)
    }

	/*
	def getNotesList() {
		return LazyList.decorate(notes, FactoryUtils.instantiateFactory(Note.class))
	}
    */
	
	String toString(){
		itsId
	}

    /**************************************************************************************************
     *     Methods for CURRENT Rack position
     **************************************************************************************************/
    String position(){
        if(RU_begin && RU_size) {
            if(RU_size == 1)
                return "${RU_begin}"
            else
                return "${RU_begin-RU_size+1} - ${RU_begin}"
        }
        else return null
    }
    String getRackAssignment() {
        if(RUplacement != null) {
            if (RUplacement?.size() > 0) {
                return RUplacement?.first()?.onRack.toString()
            }
            else
                return "Not Assigned"
        }
        else
            return "Not Assigned"
    }

    Long getRackAssignmentId() {
        if(itsId!=null) {
            if(!(itsId.isEmpty())){
                if (RUplacement?.size() > 0) {
                    return RUplacement?.first()?.onRack.id
                }
                else return 0
            }
            else
                return 0
        }
        else
            return 0
    }


    /**************************************************************************************************
     *     Methods for PLANNED Rack position
     **************************************************************************************************/
    String plannedPosition(){
        if(RU_planned_begin && RU_size) {
            if(RU_size == 1)
                return "${RU_planned_begin}"
            else
                return "${RU_planned_begin-RU_size+1} - ${RU_planned_begin}"
        }
        return null
    }
    String getPlannedRackAssignment() {
        if(RUplanning != null) {
            if (RUplanning.size() > 0) {
                return RUplanning.first().onRack.toString()
            }
            else
                return "Not Assigned"
        }
        else
            return "Not Assigned"
    }

    Long getPlannedRackAssignmentId() {
        if(itsId!=null) {
            if(!(itsId.isEmpty())){
                if (RUplanning.size() > 0) {
                    return RUplanning.first().onRack.id
                }
                else return 0
            }
            else
                return 0
        }
        else
            return 0
    }

    Integer getRUBeginPosition() {
        if(RU_begin && RU_size) {
            if (RU_begin-RU_size+1 < 0)
                return 0
            else
                return (RU_begin-RU_size+1)
        }
        else
            return 0
    }
    Integer getRUPlannedBeginPosition() {
        if(RU_planned_begin && RU_size) {
            if(RU_planned_begin-RU_size+1 < 0)
                return 0
            else
                return (RU_planned_begin-RU_size+1)
        }
        else
            return 0
    }

    String getItsIdPageLink() {
        String returnString
        if(assetType.abbreviation == 'Server') {
            returnString = "<a href='../physicalServer/show?id=${id}'>${itsId}</a>"
        }
        else {
            returnString = "<a href='../asset/show?id=${id}'>${itsId}</a>"
        }
        return returnString
    }

}
