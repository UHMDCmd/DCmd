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
    Cluster cluster
    Long memorySize=0
    Float cpuSpeed=0.0
    Integer numCores=0
    Integer numThreads =0
    String vendor

    Host hostOS

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
        cluster(nullable:true)
        memorySize(nullable:false)
        cpuSpeed(nullable:false)
        numCores(nullable:false)
        numThreads(nullable:false)
        hostOS(nullable:true)
        vendor(nullable:true)
    }

//    static belongsTo = [hosts: Host]

    static mapping = {
//        hosts cascade: 'all'
//        globalZone cascade: 'save-update'
//        resourceAllocations cascade: 'save-update'
//        discriminator column: "supportableType", value: 'physicalServer'
//        tablePerHierarchy true
    }
  /*
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
    */
    Host getGlobalZone() {
        return hostOS
    }

    Integer getTotalMemoryUsed() {

        def memUsed = Host.createCriteria().list() {
            asset {
                eq('id', this.id)
            }
            projections {
                sum('maxMemory')
            }
        }
        if(memUsed.isEmpty() || memUsed.first() == null)
            return 0
        else
            return memUsed.first()
    }

    String getTotalGBUsed() {
        def totalMem = getTotalMemoryUsed()
        if(totalMem == 0)
            return 'Not Provided'
        else
            return getTotalMemoryUsed()/1000.0 + " GB"
    }

    String getMemoryPercentUsed() {
        def memUsed = this.getTotalMemoryUsed()
        if (memUsed == 0 || this.memorySize == 0)
            return '0'
        else
            return (Math.round(memUsed/100)/10) + " GB (" + (Math.round(10.0*(memUsed / this.memorySize)))/100.0 + "%)"
    }

    Integer getTotalCPUUsed() {

        def cpuUsed = Host.createCriteria().list() {
            asset {
                eq('id', this.id)
            }
            projections {
                sum('maxCpu')
            }
        }
        if(cpuUsed.isEmpty() || cpuUsed.first() == null)
            return 0
        else
            return cpuUsed.first()
    }

    String getTotalGhzUsed() {
        def totalCPU = getTotalCPUUsed()
        if(totalCPU == 0)
            return "Not Provided"
        else
            return (totalCPU/1000) + " GHz"
    }

    String getTotalCoresUsed() {
        def cpuUsed = getTotalCPUUsed()
        return cpuUsed/cpuSpeed + " Cores"
    }

    String getCPUPercentUsed() {
        def cpuUsed = this.getTotalCPUUsed()
       // System.out.println(cpuUsed + ", " + cpuSpeed + ", " + this.numCores)
        if (cpuUsed == 0 || this.numCores == 0)
            return '0 %'
        else
            return (Math.round(10000*(cpuUsed/(cpuSpeed*1000.0)/numCores)))/100.0 + "%"
    }

    String getHostOSLinkString() {
        if(hostOS == null)
            return "Not Provided"
        else
            return "<a href='../host/show?id=" + hostOS.id + "'>" + hostOS.hostname + "</a>"
    }

    String getClusterLinkString() {
        if(serverType != 'VMWare')
            return "N/A"
        else
            return "<a href='../cluster/show?id=" + cluster?.id + "'>" + cluster?.name + "</a>"
    }

    String getDatacenterLinkString() {
        if(serverType != 'VMWare')
            return "N/A"
        else
            return cluster?.dataCenter
    }

    //float getCpuUsage() {
    //    def usage = Host.createCriteria().sum()
    //}
}