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
    int deviceId

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
        deviceId(nullable:true)
    }

    String toString() {
        label
    }
}
