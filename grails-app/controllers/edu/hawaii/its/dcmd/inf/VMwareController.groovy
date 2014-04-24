
package edu.hawaii.its.dcmd.inf

                      /*

import java.net.URL
import com.vmware.vim25.*
import com.vmware.vim25.mo.*
import com.vmware.vim25.ws.WSClient
import org.codenarc.rule.logging.PrintlnAstVisitor
import grails.validation.Validateable

@Validateable
class VMWareController {
    def index = {
        try{
            int count = 0;
            boolean status = true; //status marker to indicate successful connection and update

            URL url = new URL("https://10.1.9.91/sdk")
            String username = "***REMOVED***"
            String password = "***REMOVED***"

            def si = new ServiceInstance(url, username, password, true)
            def rootFolder = si.rootFolder

            ArrayList <ManagedEntity> entityList = new ArrayList();
            InventoryNavigator nav = new InventoryNavigator(rootFolder)

            nav.searchManagedEntities("VirtualMachine").each{
                entityList.add(it)
            }


            for (int x = 0; x < entityList.size(); x++){
                entityList.get(x).name  //replace the name of the vm
            }

            ArrayList hostList = Host.findAll()
            println("Host size: " + hostList.size())

            //newHosts sent to list controller to display updated on modal
            ArrayList <String> newHosts = updateHosts(entityList, hostList)


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

            redirect(controller: "host" ,action: "list", params: [newHosts:newHosts] )
        }
        catch(java.rmi.RemoteException e){
            session.putValue("status",false)
            redirect(controller: "host" ,action: "list" )
        }

    }



    public ArrayList <String> updateHosts(ArrayList<ManagedEntity> entityList, ArrayList <Host> currentHosts){

        int begin = currentHosts.size()
        int end = 0;
        String message = ""
        println("printing current hosts...")
        for (int x = 0; x < currentHosts.size(); x ++){
            println("host name: " + currentHosts.get(x).hostname)
        }

        //string list of names for displaying on screen
        ArrayList <String> hostNames = new ArrayList();
        for (int x = 0; x < currentHosts.size(); x++){
            hostNames.add(currentHosts.get(x).hostname)
        }

        ArrayList <String> newHostNames = new ArrayList<String>()

        for (int x = 0; x < entityList.size(); x++){
            def entity = entityList.get(x)

            if (!(hostNames.contains(entity.name))){ //if list does not contain entity, create new host
                //create new hosts
                new Host(hostname: entity.name).save(failOnError: true, flush:true)
                println("created new host: " + entity.name)

                newHostNames.add(entity.name)//add name to list to return
            }
        }

        newHostNames = newHostNames.sort()

        if (newHostNames.size() > 0){
            session.setAttribute("hostSize", newHostNames.size())
            session.setAttribute("newHosts",newHostNames)
        }
        if (newHostNames.size() == 0 ){
            session.setAttribute("hostSize", 0)
            session.setAttribute("newHosts",newHostNames)
        }

        return newHostNames

    }


    public void setSessionVars(ArrayList<String> names){

        if (names.size() == 0){
            session.setAttribute("hostSize", 0)
            session.setAttribute("newHosts",names)
        }
        else {
            session.setAttribute("hostSize", names.size())
            session.setAttribute("newHosts",names)
        }
    }





    public static Map<String, PerfCounterInfo> getPerfCounters(PerformanceManager pm) throws Exception {
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


}

                      */