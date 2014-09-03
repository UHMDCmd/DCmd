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

class PowerBreaker extends Asset{

    String label
    String ipAddress
    Integer pole_1
    Integer pole_2
    Integer voltage
    Rack rack

    //tree variables
    int capacity
    PowerPanel panel
    //int voltage

  // static belongsTo = [panel:PowerPanel]
    static hasMany = [strips: PowerStrip]

/*    static mapping = {
        strips cascade: 'all-delete-orphan'
    }*/

    static constraints = {
        rack(nullable:true)
        label(nullable: true)
        ipAddress(nullable: true)
        pole_1(nullable: true)
        pole_2(nullable: true)
        voltage (nullable:  true)

        //tree variables
        panel(nullable:true)
        capacity(nullable:true)
        strips(nullable:true)
      //  voltage(nullable:true)

    }

    //tree method
    def totalDraw() {
        def total = 0
        strips.each {
            total += it.powerUsed
        }
        return total
    }
}
