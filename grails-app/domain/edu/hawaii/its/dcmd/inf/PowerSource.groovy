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

class PowerSource extends Asset{

    String label
    String dataCenter
    Integer capacity
    String ipAddress
    String SNMP_community
    String load_OID
   // String generalNote

    //tree variables
    //capacity

    static hasMany = [panels:PowerPanel]

    static constraints = {
       label(nullable: true)
       dataCenter(nullable: true)
       capacity(nullable:  true)
       ipAddress(nullable: true)
       SNMP_community(nullable:  true)
       load_OID(nullable: true)
      // generalNote(nullable:  true)

        //tree variables
        panels(nullable:true)

        //capacity is same variable

    }


    def getAllSources(){
        ArrayList<String> sourceNames = new ArrayList<String>()
        ArrayList<PowerSource> feeds = PowerSource.findAll()

        int count = PowerSource.count
        for(int x = 0; x < count; x++){
        sourceNames.add(feeds.get(x).label)
        }
       return sourceNames
    }

    //tree method
    int totalDraw() {
        def total = 0
        panels.each {
            total += it.totalDraw()
        }
        return total
    }

}
