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
//import org.codenarc.rule.logging.PrintlnAstVisitor
import grails.validation.Validateable


@Validateable
class VMwareController {

    def VMService = new VMService()

    def index = {

        System.out.println("In index...")
        VMService.syncToVCenter()

         /*
        try{
            int count = 0;
            boolean status = true; //status marker to indicate successful connection and update

            def si = new ServiceInstance(url, username, password, true)
            def rootFolder = si.rootFolder

            InventoryNavigator nav = new InventoryNavigator(rootFolder)

            ArrayList<ManagedEntity> servers = new ArrayList<ManagedEntity>()
            nav.searchManagedEntities("HostSystem").each {
                servers.add(it)
            }
            ArrayList <String> updatedServers = updateServers(servers, si.getServerConnection())

            updatedServers.each {
                System.out.println(it)
            }
            ArrayList<ManagedEntity> virtualMachines = new ArrayList<ManagedEntity>()
            nav.searchManagedEntities("VirtualMachine").each {
                virtualMachines.add(it)
            }
            ArrayList<String> updatedHosts = updateHosts(virtualMachines, si.getServerConnection())

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
            si.serverConnection.logout()
            render updatedHosts as JSON
           // redirect(controller: "host" ,action: "list", params: [updatedHosts:updatedHosts] )
        }
        catch(RemoteException e){
            println "Error: ${e.message}"
            session.setAttribute("status",false)
            redirect(controller: "host" ,action: "list" )
        }
        */
    }
    /*
    // Helper method to get only the first part of the hosts name since vmware returns the full name only.
    // ex. mdb74.pvt.hawaii.edu would return mdb74
    private String getShortenedHostName(String name) {
        def parts = name.split("\\.")

        parts[0]
    }

    // Updates/Creates any PhysicalServers or Clusters
    private ArrayList<String> updateServers(ArrayList<ManagedEntity> servers, ServerConnection sc) {
        def tempCluster, tempServer, serverShortName
        def ArrayList<String> newServers = new ArrayList<String>()
        def ArrayList<String> updatedServers = new ArrayList<String>()
        def isChanged = false

        servers.each { server ->
            isChanged=false
            tempCluster = Cluster.findByName(server.getParent().name)
            if(tempCluster == null) {
                tempCluster = new Cluster(name: server.getParent().name, dataCenter: server.getParent().getParent().getParent().name)
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
    private ArrayList<String> updateHosts(ArrayList<ManagedEntity> virtualMachines, ServerConnection sc) {

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
            }
            else
                hostName = null

            if(hostName != null) {
                // Get physical server running it
                HostSystem vmHost = (HostSystem) MorUtil.createExactManagedEntity(sc, vm.getRuntime().getHost())

                // See if Host already exists in DCmd)
                tempHost = Host.findByHostname(hostName)

                tempServer = PhysicalServer.findByItsId(getShortenedHostName(vmHost.name))
                if (tempServer == null)
                    System.out.println("Server not found. This should never happen...")

                tempCluster = Cluster.findByName(vmHost.getParent().name)

                /*************************
                 * These stats are what we will use for memory... Find out what to use for CPU....
                 */
                  /*
                if(hostName == 'odb61') {
                    // Memory info
                    System.out.println("toolsRunningStatus  " + vm.getGuest().toolsRunningStatus)
                    System.out.println("state " + vm.getRuntime().connectionState)
                }

                //System.out.println(vm.name + ", " + getShortenedHostName(vmHost.name) + ", " + hostName + ", " + tempCluster.name)

                if (tempHost == null) { // If doesn't exist, create it
                    tempHost = new Host(hostname: hostName, type: 'VMWare', asset: tempServer, cluster: tempCluster,
                            vcName:vm.name, fullDomain:dnsName[0]?.toLowerCase(), ipAddress:vm.getGuest()?.ipAddress, maxMemory: vm.getRuntime().maxMemoryUsage,
                            maxCpu: vm.getRuntime().maxCpuUsage, vCenterState: String.valueOf(vm.getRuntime().connectionState))
                    tempHost.save(flush:true, failOnError: true)
                    updatedHosts.add("VM " + tempHost.hostname + " created")
                }
                else { // If it does exist, check for changes
                    if(tempHost.asset?.id != tempServer.id) {
                        System.out.println(tempHost.asset?.itsId + " -> " + tempServer.itsId)
                        tempHost.asset = tempServer
                        isChanged=true
                    }
                    if(tempHost.cluster != tempCluster) {
                        System.out.println(tempHost.cluster?.name + " -> " + tempCluster.name)
                        tempHost.cluster = tempCluster
                        isChanged=true
                    }
                    if(tempHost.vcName != vm.name) {
                        System.out.println(tempHost.vcName + " -> " + vm.name)
                        tempHost.vcName = vm.name
                        isChanged=true
                    }
                    if(tempHost.fullDomain != dnsName[0]) {
                        System.out.println(tempHost.fullDomain + " -> " + dnsName[0])
                        tempHost.fullDomain = dnsName[0]
                        isChanged=true
                    }
                    if(tempHost.ipAddress != vm.getGuest()?.ipAddress) {
                        System.out.println(tempHost.ipAddress + " -> " + vm.getGuest()?.ipAddress)
                        tempHost.ipAddress = vm.getGuest()?.ipAddress
                        isChanged=true
                    }
                    if(tempHost.maxCpu != vm.getRuntime().maxCpuUsage) {
                        System.out.println("Max CPU Changed: " + tempHost.maxCpu + " -> " + vm.getRuntime().maxCpuUsage)
                        tempHost.maxCpu = vm.getRuntime().maxCpuUsage
                        isChanged=true
                    }
                    if(tempHost.maxMemory != vm.getRuntime().maxMemoryUsage) {
                        System.out.println("Max Memory Changed: " + tempHost.maxMemory + " -> " + vm.getRuntime().maxMemoryUsage)
                        tempHost.maxMemory = vm.getRuntime().maxMemoryUsage
                        isChanged=true
                    }
                    if(tempHost.vCenterState != vm.getRuntime().connectionState) {
                        tempHost.vCenterState = vm.getRuntime().connectionState
                        isChanged=true
                    }

                    if(isChanged) {
                        tempHost.save(flush:true, failOnError: true)
                        //System.out.println("Saved: " + tempHost.hostname + " - " + tempHost.asset.itsId)
                        updatedHosts.add("VM " + hostName + " updated, " + tempHost.asset.itsId)
                    }
                }
            }
            else // dnsName was null, i.e., can't get name of Host
                System.out.println("Guest named " + vm.name + " dnsName was null, could not determine hostName")
        }

       // updatedHosts.each {
        //    System.out.println(it)
        //}
        return updatedHosts

    }

    private ArrayList<String> checkForChanges(ArrayList<Host> dcmdHosts, ManagedEntity vm, ServerConnection sc) {
        def added
        ArrayList<String> updatedHosts = new ArrayList<String>()
        for (Host host : dcmdHosts) {
            added = false

            if (host.hostname == vm.name) {
                HostSystem vcHost = (HostSystem) MorUtil.createExactManagedEntity(sc, vm.runtime.host)
                def vcHostName = getShortenedHostName(vcHost.name)
                if (isAssetChanged(Asset.findById(host.assetId).itsId, vcHostName)) {
                    // Change Asset
                    def asset = Asset.findByItsId(vcHostName)
                    if (asset == null) {
                        assetDoesntExist(vcHostName)
                    }
                    else {
                        host.asset = asset
                        host.save(flush: true)
                        updatedHosts.add(host.hostname)
                        added = true
                    }
                }

                if (isVcStateChanged(host.vCenterState, vm.runtime.connectionState)) {
                    // Change Status
                    host.vCenterState = vm.runtime.connectionState.toString()
                    host.save(flush: true)
                    if (!added) {
                        updatedHosts.add(host.hostname)
                    }
                }
            }
        }

        updatedHosts
    }

    private boolean isAssetChanged(String assetName, String vmHost) {
        if (!assetName.equals(vmHost)) {
            true
        }
        else {
            false
        }
    }

    private boolean isVcStateChanged(String vCenterState, VirtualMachineConnectionState conn) {
        if (vCenterState != conn.toString()) {
            true
        }
        else {
            false
        }
    }

    void assetDoesntExist(String name) {
        //TODO: Should this create the asset or notify someone that this asset doesn't exist in dcmd
    }

/*
    private static Map<String, PerfCounterInfo> getPerfCounters(PerformanceManager pm) throws Exception {
        Map<String, PerfCounterInfo> result = new HashMap<String, PerfCounterInfo>();
        PerfCounterInfo[] counters = pm.getPerfCounter();

        println("*****************map method******************")
        if (counters != null) {
            for (PerfCounterInfo counter : counters) {

                def group = counter.getGroupInfo().key.toString()
                def name = counter.getNameInfo().key.toString()
                def rollup = counter.getRollupType().toString()

                def key =  group + "." + name + "." + rollup

                println("Key: " + key)

                result.put(key, counter);
            }
        }
        println("*****************map method******************")
        return result;
    }



    static void GetMultiPerf(String [] args, PerfMetricId metricId)
    {
        try{
            ServiceInstance si = new ServiceInstance(
                    new URL(args[0]), args[1], args[2], true);
            String vmname = args[3];


            VirtualMachine vm = (VirtualMachine) new InventoryNavigator(
                    si.getRootFolder()).searchManagedEntity(
                    "VirtualMachine", vmname);
            if(vm == null)
            {
                System.out.println("Virtual Machine " + vmname
                        + " cannot be found.");
                si.getServerConnection().logout();
                return;
            }

            println("Vmname retrieved from si: " + vm.name)

            PerformanceManager perfMgr = si.getPerformanceManager();
            int perfInterval = 20; // 20 sec interval
// retrieve all the available perf metrics for vm
//        PerfMetricId[] pmis = perfMgr.queryAvailablePerfMetric(
//                vm, null, null, perfInterval);
//

            PerfQuerySpec qSpec = new PerfQuerySpec();

            PerfMetricId [] pmis = new PerfMetricId[1];
            pmis[0] = metricId
            println("metric instance to be set: " + pmis[0].instance)
            println("metric id to be set: " + pmis[0].counterId)

            qSpec.setMetricId(pmis);


            Calendar curTime = si.currentTime();
            qSpec.setEntity(vm.getRuntime().getHost());
            // qSpec.setEntity(vm.MOR)
            println("Entity is set to: "  + qSpec.getEntity()._value)
            //metricIDs must be provided, or InvalidArgumentFault


            def vm_sum = vm.getSummary()

            int overallCPU = vm_sum.getQuickStats().getOverallCpuUsage()

            println("Quick Stats for VM: " + vm.name)
            println("overall CPU: " + overallCPU)
            println("overall status: " + vm_sum.overallStatus.toString())
            println("consumed memory: " + vm_sum.quickStats.consumedOverheadMemory)
            println("guest memory usage: " + vm_sum.quickStats.guestMemoryUsage)
            println("private memory" + vm_sum.quickStats.privateMemory)

            //printing out of retireved metrics
            if(qSpec.getMetricId() != null){
                println("metrics for qSpec are:")
                println("name: " + qSpec.getMetricId()[0].instance);
                println("id: " + qSpec.getMetricId()[0].counterId);
            }
            else {
                println("error: the metric query returned null")
            }

            qSpec.setFormat("normal"); //optional since it's default
            qSpec.setIntervalId(perfInterval);
            Calendar startTime = (Calendar) curTime.clone();
            startTime.roll(Calendar.DATE, -4);

            System.out.println("start:" + startTime.getTime());

            qSpec.setStartTime(startTime);
            Calendar endTime = (Calendar) curTime.clone();
            endTime.roll(Calendar.DATE, -3);

            System.out.println("end:" + endTime.getTime());

            qSpec.setEndTime(endTime);
            PerfCompositeMetric pv = perfMgr.queryPerfComposite(qSpec);
            if(pv != null)
            {
                printPerfMetric(pv.getEntity());
                PerfEntityMetricBase[] pembs = pv.getChildEntity();
                for(int i=0; pembs!=null && i< pembs.length; i++)
                {
                    printPerfMetric(pembs[i]);
                }
            }
            else if (pv.equals(null)){
                println("perf query returned null")
            }

            si.getServerConnection().logout();

        } catch(Exception e){
            println("Caught exception... " + e)
            println("message: " + e.message)
            println("cause: " + e.cause)
            println("stacktrace: " + e.stackTrace.toArrayString())
        }
    }

    static void printPerfMetric(PerfEntityMetricBase val)
    {
        String entityDesc = val.getEntity().getType()
        + ":" + val.getEntity().get_value();
        System.out.println("Entity:" + entityDesc);
        if(val instanceof PerfEntityMetric)
        {
            printPerfMetric((PerfEntityMetric)val);
        }
        else if(val instanceof PerfEntityMetricCSV)
        {
            printPerfMetricCSV((PerfEntityMetricCSV)val);
        }
        else
        {
            System.out.println("UnExpected sub-type of " +
                    "PerfEntityMetricBase.");
        }
    }
    static void printPerfMetric(PerfEntityMetric pem)
    {
        PerfMetricSeries[] vals = pem.getValue();
        PerfSampleInfo[] infos = pem.getSampleInfo();
        System.out.println("Sampling Times and Intervales:");
        for(int i=0; infos!=null && i<infos.length; i++)
        {
            System.out.println("sample time: "
                    + infos[i].getTimestamp().getTime());
            System.out.println("sample interval (sec):"
                    + infos[i].getInterval());
        }
        System.out.println("\nSample values:");
        for(int j=0; vals!=null && j<vals.length; ++j)
        {
            System.out.println("Perf counter ID:"
                    + vals[j].getId().getCounterId());
            System.out.println("Device instance ID:"
                    + vals[j].getId().getInstance());
            if(vals[j] instanceof PerfMetricIntSeries)
            {
                PerfMetricIntSeries val = (PerfMetricIntSeries) vals[j];
                long[] longs = val.getValue();
                for(int k=0; k<longs.length; k++)
                {
                    System.out.print(longs[k] + " ");
                }
                System.out.println("Total:"+longs.length);
            }
            else if(vals[j] instanceof PerfMetricSeriesCSV)
            { // it is not likely coming here...
                PerfMetricSeriesCSV val = (PerfMetricSeriesCSV) vals[j];
                System.out.println("CSV value:" + val.getValue());
            }
        }
    }
    static void printPerfMetricCSV(PerfEntityMetricCSV pems)
    {
        System.out.println("SampleInfoCSV:"
                + pems.getSampleInfoCSV());
        PerfMetricSeriesCSV[] csvs = pems.getValue();
        for(int i=0; i<csvs.length; i++)
        {
            System.out.println("PerfCounterId:"
                    + csvs[i].getId().getCounterId());
            System.out.println("CSV sample values:"
                    + csvs[i].getValue());
        }
    }
*/

    def testConfig = {
        System.out.println("IN TEST ACTION")
        def config = new ConfigSlurper().parse(new File('C:\\Users\\Ben\\Desktop\\configTest.groovy').toURL())

        System.out.println(config.item1.test1)
    }
}
