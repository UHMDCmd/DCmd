package edu.hawaii.its.dcmd.inf

import com.vmware.vim25.mo.HostSystem
import com.vmware.vim25.mo.InventoryNavigator
import com.vmware.vim25.mo.ManagedEntity
import com.vmware.vim25.mo.ServerConnection
import com.vmware.vim25.mo.ServiceInstance
import com.vmware.vim25.mo.util.MorUtil
import grails.converters.JSON
import groovy.json.JsonSlurper

import java.rmi.RemoteException

class VMService {

    def syncToVCenter() {
        System.out.println("VMware Sync Started...")

        // Set all VM States to "Not Found"
        Host.findAll().each {
            it.vCenterState = null
        }

        try {
            int count = 0;
            boolean status = true; //status marker to indicate successful connection and update
            URL url
            String username
            String password

            def inputFile = new File("${System.properties['user.home']}/.grails/vcenters.txt")
//            def inputFile = new File("dcmdConfig/vcenters.txt")
            def jsonSlurper = new JsonSlurper()
            def InputJSON = jsonSlurper.parseText(inputFile.text)
            InputJSON.each {

                def si
                def rootFolder
                ArrayList<ManagedEntity> servers = new ArrayList<ManagedEntity>()
                ArrayList<ManagedEntity> virtualMachines = new ArrayList<ManagedEntity>()
                InventoryNavigator nav

                url = new URL(it.url)
                username = it.username
                password = it.password

                println(url.host)

                si = new ServiceInstance(url, username, password, true)
                rootFolder = si.rootFolder

                nav = new InventoryNavigator(rootFolder)

                // Add physicalServers to list from VCenter

               // println("Before Adding Servers")
                nav.searchManagedEntities("HostSystem").each {
                    servers.add(it)
                }
               // println("Added Servers")
                // Add virtual Hosts to list from VCenter

                nav.searchManagedEntities("VirtualMachine").each {
                //    println(it.getParent().name)
               //     println(it.getParent().getParent().name)
                    //it.physicalServer = (HostSystem) MorUtil.createExactManagedEntity(si.getServerConnection(), it.getRuntime().getHost())
                    virtualMachines.add(it)
                }

                ArrayList<String> updatedServers = updateServers(servers, url.host.tokenize(".")?.get(0)?.toLowerCase())
                //println("Updated Servers")

                updatedServers.each {
                    //   System.out.println(it)
                }

                // uUpdate DCmd with all virtual Hosts from all VCenters
                ArrayList<String> updatedHosts = updateHosts(virtualMachines, si.serverConnection)
               // println("Updated VMs")

                //println("Added VMs")
                si.serverConnection.logout()

            }

            // Update DCmd with all servers from all VCenters


            // newHosts sent to list controller to display updated on modal
            //ArrayList <String> updatedHosts = updateHosts(virtualMachines, si.getServerConnection())

            /*
            render "Root folder: ${rootFolder.name} "
            new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine").each {
                render """
<p>Name: ${it.name}
<p>Guest OS: ${it.config.guestFullName}
<p>Multiple snapshots supported?: ${it.capability.isMultipleSnapshotsSupported()}
<p> parent: ${it.parent}
<p> MOR type: ${it.MOR.type}
<p> count: ${++count}
<p>______________________________________________
"""
            }
              */
            /*
            render updatedHosts as JSON
            // redirect(controller: "host" ,action: "list", params: [updatedHosts:updatedHosts] )
        }
        */
        }
        catch(RemoteException e){
            println "Error: ${e.message}"
 //           session.setAttribute("status",false)
//            redirect(controller: "host" ,action: "list" )
        }
        System.out.println("VMware Sync Completed.")
    }

    // Helper method to get only the first part of the hosts name since vmware returns the full name only.
    // ex. mdb74.pvt.hawaii.edu would return mdb74
    String getShortenedHostName(String name) {
        def parts = name.split("\\.")
        parts[0]
    }

    // Updates/Creates any PhysicalServers or Clusters
    ArrayList<String> updateServers(ArrayList<ManagedEntity> servers, String vcName) {
        def tempCluster, tempServer, serverShortName
        def ArrayList<String> newServers = new ArrayList<String>()
        def ArrayList<String> updatedServers = new ArrayList<String>()
        def isChanged = false

        servers.each { server ->
            isChanged=false
            tempCluster = Cluster.findByName(server.getParent().name)
            if(tempCluster == null) {
                tempCluster = new Cluster(name: server.getParent().name, dataCenter: vcName)
                tempCluster.save(flush:true)
                newServers.add("Cluster " + tempCluster.name + " created")
            }

            serverShortName = getShortenedHostName(server.name)

            tempServer = PhysicalServer.findByItsId(serverShortName)
            long memSize = Math.round((server.getHardware().getMemorySize()/1000000000))
            double cpuSpeed = (server.getHardware().getCpuInfo().hz/1000000000.0)
            cpuSpeed = Math.round(cpuSpeed * 100.0)/100.0
            def numCores = server.getHardware().getCpuInfo().getNumCpuCores()
            def numThreads = server.getHardware().getCpuInfo().getNumCpuThreads()

            if(tempServer == null) {
                tempServer = new PhysicalServer(itsId:serverShortName, cluster:tempCluster, assetType:AssetType.findByAbbreviation("Server"), serverType:"VMWare",
                        memorySize:memSize, cpuSpeed:cpuSpeed,numCores:numCores, numThreads:numThreads,
                        vendor: server.getHardware().getSystemInfo().vendor, modelDesignation: server.getHardware().getSystemInfo().model) // Add other attributes from VCenter...
                tempServer.save(flush:true, failOnError: true)
                newServers.add("Server " + tempServer.itsId + " created")
            }
            else {
                if(tempServer.cluster?.id != tempCluster.id) {
                    tempServer.cluster = tempCluster
                    isChanged = true
                }
                if(tempServer.memorySize != memSize) {
                    tempServer.memorySize = memSize
                    isChanged = true
                }
                if(Math.round(tempServer.cpuSpeed) != Math.round(cpuSpeed)) {
                    tempServer.cpuSpeed = cpuSpeed
                    isChanged = true
                }
                if(tempServer.numCores != numCores) {
                    tempServer.numCores = numCores
                    isChanged = true
                }
                if(tempServer.numThreads != numThreads) {
                    tempServer.numThreads = numThreads
                    isChanged = true
                }
                if(tempServer.vendor != server.getHardware()?.getSystemInfo()?.vendor) {
                    tempServer.vendor = server.getHardware()?.getSystemInfo()?.vendor
                    isChanged = true
                }
                if(tempServer.modelDesignation != server.getHardware()?.getSystemInfo()?.model) {
                    tempServer.modelDesignation = server.getHardware()?.getSystemInfo()?.model
                    isChanged = true
                }
                // Check for changes in other attributes from VCenter...

                if(isChanged) {
                    tempServer.save(flush:true)
                    updatedServers.add("Server " + tempServer.itsId + " updated")
                }
            }
        }
        return newServers + updatedServers
    }

    // Have to create a method that updates the current Hosts(Virtual Machines) already in DCMD.
    // This controller is not to create new Hosts or Assets(HostSystems).
    ArrayList<String> updateHosts(ArrayList<ManagedEntity> virtualMachines, ServerConnection sc) {

        def tempHost
        def tempServer
        def tempCluster
        def dcmdHosts = Host.findAll()
        def updatedHosts = new ArrayList<String>()
        def dnsInfo
        def isChanged=false

        virtualMachines.each{ vm ->
            isChanged=false
            // Find name by beginning of dns
            def dnsName = vm.getGuest()?.getIpStack()?.dnsConfig?.hostName
            def hostName
            if(dnsName != null) {
                hostName = dnsName[0]?.tokenize(".")?.get(0)?.toLowerCase()
            //    println("DNSname[0]: " + dnsName[0] + ", Hostname: " + hostName + ", contains? " + dnsName[0].contains('.'))
            }
            else
                hostName = null

            if(hostName != null) {
                // Get physical server running it
                HostSystem vmHost = (HostSystem) MorUtil.createExactManagedEntity(sc, vm.getRuntime().getHost())
                //HostSystem vmHost = vm.physicalServer

                // See if Host already exists in DCmd)
                tempHost = Host.findByHostname(hostName)

                tempServer = PhysicalServer.findByItsId(getShortenedHostName(vmHost.name))
                if (tempServer == null)
                    System.out.println("Server not found. This should never happen...")

                tempCluster = Cluster.findByName(vmHost.getParent().name)

                /*************************
                 * These stats are what we will use for memory... Find out what to use for CPU....
                 */

                //if(hostName == 'odb61') {
                    // Memory info
                 //   System.out.println("toolsRunningStatus  " + vm.getGuest().toolsRunningStatus)
                 //   System.out.println("state " + vm.getRuntime().connectionState)
                 //   System.out.println("OS1 " + vm.getGuest().guestId)
                 //   System.out.println("OS2 " + vm.getGuest().guestFullName)
                //}

                //System.out.println(vm.name + ", " + getShortenedHostName(vmHost.name) + ", " + hostName + ", " + tempCluster.name)
                if (tempHost == null) { // If doesn't exist, create it
                    if(hostName == 'adreset') {
                        println("creating adreset")
                    }
                    tempHost = new Host(hostname: hostName, type: 'VMWare', asset: tempServer, cluster: tempCluster,
                            vcName:vm.name, fullDomain:dnsName[0]?.toLowerCase(), ipAddress:vm.getGuest()?.ipAddress, maxMemory: vm.getRuntime().maxMemoryUsage,
                            maxCpu: vm.getRuntime().maxCpuUsage, vCenterState: String.valueOf(vm.getRuntime().connectionState),
                            os:vm.getGuest().guestFullName)
                    tempHost.save(flush:true, failOnError: true)
                    updatedHosts.add("VM " + tempHost.hostname + " created")
                }
                else { // If it does exist, check for changes
                    if(tempHost.asset?.id != tempServer.id) {
                    //    System.out.println(tempHost.asset?.itsId + " -> " + tempServer.itsId)
                        tempHost.asset = tempServer
                        isChanged=true
                    }
                    if(tempHost.cluster != tempCluster) {
                     //   System.out.println(tempHost.cluster?.name + " -> " + tempCluster.name)
                        tempHost.cluster = tempCluster
                        isChanged=true
                    }
                    if(tempHost.vcName != vm.name) {
                     //   System.out.println(tempHost.vcName + " -> " + vm.name)
                        tempHost.vcName = vm.name
                        isChanged=true
                    }
                    if(tempHost.fullDomain != dnsName[0]) {
                     //   System.out.println(tempHost.fullDomain + " -> " + dnsName[0])
                        tempHost.fullDomain = dnsName[0]
                        isChanged=true
                    }
                    if(tempHost.ipAddress != vm.getGuest()?.ipAddress) {
                      //  System.out.println(tempHost.ipAddress + " -> " + vm.getGuest()?.ipAddress)
                        tempHost.ipAddress = vm.getGuest()?.ipAddress
                        isChanged=true
                    }
                    if(tempHost.maxCpu != vm.getRuntime().maxCpuUsage) {
                    //    System.out.println("Max CPU Changed: " + tempHost.maxCpu + " -> " + vm.getRuntime().maxCpuUsage)
                        tempHost.maxCpu = vm.getRuntime().maxCpuUsage
                        isChanged=true
                    }
                    if(tempHost.maxMemory != vm.getRuntime().maxMemoryUsage) {
                    //    System.out.println("Max Memory Changed: " + tempHost.maxMemory + " -> " + vm.getRuntime().maxMemoryUsage)
                        tempHost.maxMemory = vm.getRuntime().maxMemoryUsage
                        isChanged=true
                    }
                    if(tempHost.vCenterState != vm.getRuntime().connectionState) {
                        tempHost.vCenterState = vm.getRuntime().connectionState
                        isChanged=true
                    }
                    if(tempHost.os != vm.getGuest()?.guestFullName) {
                        tempHost.os = vm.getGuest()?.guestFullName
                        isChanged=true
                    }

                    if(isChanged) {
                        tempHost.save(flush:true, failOnError: true)
                        //System.out.println("Saved: " + tempHost.hostname + " - " + tempHost.asset.itsId)
                        updatedHosts.add("VM " + hostName + " updated, " + tempHost.asset.itsId)
                    }
                }
            }
//            else // dnsName was null, i.e., can't get name of Host
                //System.out.println("Guest named " + vm.name + " dnsName was null, could not determine hostName")
 //               System.out.println(vm.name)
        }

        // updatedHosts.each {
        //    System.out.println(it)
        //}
        return updatedHosts

    }



}
