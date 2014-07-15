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