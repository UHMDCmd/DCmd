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

class Replacement {

    Asset replacement
    Asset main_asset

    Integer priority
    Date ready_date
    String replacement_notes

    static auditable = true


//    static belongsTo = [main_asset:Asset]
    static constraints = {
        main_asset(nullable: false)
        replacement(nullable: false)
        priority(nullable: true)
        ready_date(nullable: true)
        replacement_notes(nullable: true, maxSize: 1024)
    }
    static belongsTo = [main_asset: Asset]

    String toString(){
        "${replacement.toString()}"
    }
}
