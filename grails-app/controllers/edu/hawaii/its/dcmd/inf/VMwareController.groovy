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

import com.vmware.vim25.mo.util.MorUtil

import java.rmi.RemoteException
import com.vmware.vim25.*
import com.vmware.vim25.mo.*
//import org.codenarc.rule.logging.PrintlnAstVisitor
import grails.validation.Validateable

@Validateable
class VMwareController {
    def index = {
        try{
            int count = 0;
            boolean status = true; //status marker to indicate successful connection and update

            URL url = new URL("https://10.1.9.91/sdk")
            String username = "***REMOVED***"
            String password = "***REMOVED***"

            def si = new ServiceInstance(url, username, password, true)
            def rootFolder = si.rootFolder

            InventoryNavigator nav = new InventoryNavigator(rootFolder)

            ArrayList<ManagedEntity> virtualMachines = new ArrayList<ManagedEntity>()
            nav.searchManagedEntities("VirtualMachine").each {
                virtualMachines.add(it)
            }

            // newHosts sent to list controller to display updated on modal
            ArrayList <String> updatedHosts = updateHosts(virtualMachines, si.getServerConnection())

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



            si.serverConnection.logout()

            redirect(controller: "host" ,action: "list", params: [updatedHosts:updatedHosts] )
        }
        catch(RemoteException e){
            println "Error: ${e.message}"
            session.setAttribute("status",false)
            redirect(controller: "host" ,action: "list" )
        }
    }

    // Helper method to get only the first part of the hosts name since vmware returns the full name only.
    // ex. mdb74.pvt.hawaii.edu would return mdb74
    private String getShortenedHostName(String name) {
        def parts = name.split("\\.")

        parts[0]
    }

    // Have to create a method that updates the current Hosts(Virtual Machines) already in DCMD.
    // This controller is not to create new Hosts or Assets(HostSystems).
    private ArrayList<String> updateHosts(ArrayList<ManagedEntity> virtualMachines, ServerConnection sc){

        def dcmdHosts = Host.findAll()

        //string list of names for displaying on screen
        ArrayList <String> hostNames = new ArrayList()
        for (int x = 0; x < dcmdHosts.size(); x++){
            hostNames.add(dcmdHosts.get(x).hostname)
        }

        ArrayList<String> updatedHosts = new ArrayList<String>()
        for (ManagedEntity vm: virtualMachines){
            if ((hostNames.contains(vm.name))){ //if current hosts contains the vmware host
                //Check against fields that can change: Asset, Status
                updatedHosts = updatedHosts + checkForChanges(dcmdHosts, vm, sc)
            }
        }

        session.setAttribute("hostSize", hostNames.size())
        session.setAttribute("newHosts",hostNames)

        updatedHosts

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
}
