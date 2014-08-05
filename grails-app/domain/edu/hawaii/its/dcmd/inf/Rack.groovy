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

class Rack extends Asset{

    Integer ruCap
    String rackNum
    String railType
    String cabLocation
    String rowId
    Integer zoneId

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
        rowId(nullable:true)
        cabLocation(nullable:true)
        zoneId(nullable:true)
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