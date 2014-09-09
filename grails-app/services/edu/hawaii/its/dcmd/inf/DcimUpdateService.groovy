package edu.hawaii.its.dcmd.inf

import ssh.RemoteSSH
import ssh.RemoteSCPGet
import ssh.RemoteSCP
import org.apache.ivy.core.settings.Validatable
import org.apache.tools.ant.types.Description
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil
import javax.activation.DataSource


class DcimUpdateService {


    def sshConfig
    def scpConfig1
    def scpConfig2
    def scpConfig3
    def port=22;
    def sessionFactory
    def racksToBeUpdated

    /**
     *   retrieve all devices, cabinets and datacenters via ssh connection to dcim.its.hawaii.edu/keller
     */
    def startUpdate(){
        runQuery()
    }

    def runQuery () {

        println("waiting for query to complete...")

        RemoteSSH ash=new RemoteSSH('dcim.its.hawaii.edu', 'elfalan', '14611782','', './runAllQueries.sh','',port)
        def result=ash.Result(sshConfig)

        RemoteSCPGet scpget1 =new RemoteSCPGet('dcim.its.hawaii.edu', 'elfalan', '14611782', '/home/elfalan/devices.txt' ,'grails-app/views/dcimConnector/queryResults', port)
        RemoteSCPGet scpget2=new RemoteSCPGet('dcim.its.hawaii.edu', 'elfalan', '14611782', '/home/elfalan/cabinets.txt' ,'grails-app/views/dcimConnector/queryResults', port)
        RemoteSCPGet scpget3=new RemoteSCPGet('dcim.its.hawaii.edu', 'elfalan', '14611782', '/home/elfalan/datacenters.txt' ,'grails-app/views/dcimConnector/queryResults', port)

        def results=scpget1.Result(sshConfig) + scpget2.Result(sshConfig) + scpget3.Result(sshConfig)

        def deviceList = createItemList("grails-app/views/dcimConnector/queryResults/devices.txt");
        def dataCenterList = createItemList("grails-app/views/dcimConnector/queryResults/datacenters.txt");
        def cabinetList = createItemList("grails-app/views/dcimConnector/queryResults/cabinets.txt");

        //response for page
        Thread.sleep(10000)
        importServersFromProduction()
    }


    /**********************************************************************
     * Update Physical Server/Device Controller Action
     *********************************************************************/

    /**
     * Update the DCmd servers list through file saved in queryResults directory in dcimConnector view dir.
     * Functions: + creates list of attributes for physical servers from input file "devices.txt"
     *            + Checks current physical servers in dcmd and does cross check with dcim references
     *            + If the rack that the server is on does not exist, creates new rack instance
     *            + Adds servers to rack and updates attributes
     */
    def updatePhysicalDevicesWithDCIM () {
        println("waiting for Physical Server update to complete...")



        def deviceList = createItemList("grails-app/views/dcimConnector/queryResults/devices.txt");
        def size = 0;

        ArrayList<PhysicalServer> servers = PhysicalServer.getAll()

        ArrayList filteredList = new ArrayList();
        ArrayList <String> deviceLabels = new ArrayList();
        ArrayList <String> notFound = new ArrayList();

        //get names of devices, convert to lower case string, add to list called deviceLabels
        for(int i=0; i<deviceList.size();i++){
            def item = deviceList.get(i)
            String name = item[0]
            deviceLabels.add(name.toLowerCase())
        }


        //do cross check of physical servers found in DCIM, add Servers not found to new list
        for(int x = 0; x < servers.size(); x++){
            String serverName = servers.get(x).itsId

            if(deviceLabels.contains(serverName)){
                def index = deviceLabels.indexOf(serverName)
                filteredList.add(deviceList.get(index))
            }
            else{
                notFound.add(serverName)
            }
        }

        println("printing filtered list: ")
        for(int z=0; z < filteredList.size(); z++){
            println(filteredList.get(z).toString())
        }

        def masterList = trimList(filteredList)
        println("\nprinting master list: ")
        size = masterList.size()
        for(int y=0; y < masterList.size(); y++){
            println(masterList.get(y).toString())
        }

        //if device that rack is on does not exits in dcmd, create a new rack instance
        createRacksFromDCIM(masterList)

        //update server attributes and add server to corresponding rack
        addDevicesToRacks(masterList)

        def updateStatus = "found " + size + " out of " + servers.size()

        updateDataCentersWithDCIM()

    }

    def createRacksFromDCIM (inputList) {
        ArrayList<String> rackUpdateList = new ArrayList<String>()
        for (int x = 0; x < inputList.size(); x++) {
            def item = inputList.get(x)
            String label = item[3]
            def num = Integer.parseInt(label)

            if (Rack.findByItsId(label) == null) {
                println("\ndid not find the rack named: " + label)
                println("creating new rack...")
                Rack rackInstance = new Rack(itsId: label, rackNum: label, updatedById: num, assetType: AssetType.findByName('Server Rack'), ruCap: 45, RU_begin: 0, RU_size: 0, RU_planned_begin: 0).save(failOnError: true, flush:true)
                rackInstance.Initialize()
                println("saved new rack instance: " + rackInstance.itsId)
            } else {
                rackUpdateList.add(label)
                println("added rack to update list: " + label)
            }
        }
        return rackUpdateList
    }

    def addDevicesToRacks(inputList) {

        for (int x = 0; x < inputList.size(); x++) {
            println("\nupdating device positions...")

            def item = inputList.get(x)
            String deviceLabel = item[0]
            def rackLabel = item[3]
            def slotPosition = Integer.parseInt(item[4])
            deviceLabel = deviceLabel.toLowerCase()

            Rack rackInstance = Rack.findByItsId(rackLabel)
            PhysicalServer serverInstance = PhysicalServer.findByItsId(deviceLabel)
            //def serverInstance = Asset.findById(temp.id)

            //update the server attributes
            serverInstance = updateServerInstance(serverInstance, item)


            if (!serverInstance.hasErrors()) {
                serverInstance.save(failOnError: true, flush: true)
                println("++updated server++")
            } else {
                println("errors occured during server save, on server: " + serverInstance.itsId)
            }

//            update rack unit on current rack to take server
            def height = serverInstance.RU_size
            for (int z = 0; z < height; z++) {
                println("slot postion: " + slotPosition)
                RackUnit rackUnitInstance = rackInstance.getUnitBySlot(rackInstance, slotPosition)

                //add the asset intially, if height is > 1, create labels for occupied slots

                rackUnitInstance.properties['filledBy'] = serverInstance
                rackUnitInstance.properties['onRack'] = rackInstance
                // rackUnitInstance.properties['label'] = deviceLabel
                rackUnitInstance.properties['label'] = "<a href='/its/dcmd/asset/show?id=${serverInstance.id}'>${serverInstance.itsId}</a>"
                rackUnitInstance.properties['RUstatus'] = 'Filled'
                rackUnitInstance.properties['ru_slot'] = slotPosition
                rackUnitInstance.save(failOnError: true, flush: true)
                rackInstance.save(failOnError: true, flush: true)

                //rackInstance.save(failOnError:true, flush:true)

                println("server rack assignment: " + serverInstance.getRackAssignment())
                println("server instance filled: " + rackUnitInstance.filledBy)
                println("rack unit on rack: " + rackUnitInstance.onRack.itsId)
                println("rack units" + rackInstance.RUs.toString())

                slotPosition++


                if (rackInstance.RUs.contains(rackUnitInstance)) {
                    println("rackunit is on rack")
                } else {
                    println("unit is not on rack")
                }
            }
        }
    }

    /**
     * Support method for updatePhysicalServerWihDCIM
     * @param serverInstance
     * @param parameters
     * @return
     */
    def updateServerInstance(serverInstance, parameters){
        int size = Integer.parseInt(parameters[5])

        if(size > 1){
            serverInstance.properties['RU_size'] = size
        }else{serverInstance.properties['RU_size'] = 1}

        serverInstance.properties['RU_begin'] = Integer.parseInt(parameters[4]); //slot position
        serverInstance.properties['serialNo'] = parameters[2];

        return serverInstance
    }
/****************************************************************************
 * Update DataCenters
 ****************************************************************************/
    /**
     * Update the DCmd Locations/Datacenters list through file saved in queryResults directory in dcimConnector view dir.
     * Functions: + creates list of attributes for locations from input file "datacenters.txt"
     *            + Checks current locations in dcmd and does cross check with dcim references
     *            + If the location does not exist, creates new location instance
     *
     *            #note:Action must be performed before rack attibute update, location is attribute of rack
     */
    def updateDataCentersWithDCIM () {
        println("waiting for Data Center update to complete...")



        def attributesToUpdateFrom = createItemList("grails-app/views/dcimConnector/queryResults/datacenters.txt")
        ArrayList <Location> dataCenters = Location.getAll();
        ArrayList <String> labels = new ArrayList<String>()

        println("dc size: " + dataCenters.size())

        for (int  x = 0; x < attributesToUpdateFrom.size(); x ++){
            def item = attributesToUpdateFrom.get(x)
            def label = item[1]
            labels.add(label)
        }


        for(int i = 0; i < labels.size(); i++){
            def dataCenterInstance
            def item = attributesToUpdateFrom.get(i)
            def dataCenterId = Integer.parseInt(item[0])
            def description = item[1]
            def squareFootage = Integer.parseInt(item[2])

            //keller 103 already named, dcim name for it is k103, only update other attributes
            if (description.equals('K103')){
                dataCenterInstance = Location.findByLocationDescription('Keller 103')
                dataCenterInstance.properties['dataCenterID'] = dataCenterId
                //dataCenterInstance.properties['locationDescription'] = attributesToUpdateFrom[1]
                dataCenterInstance.properties['squareFootage'] = squareFootage
                dataCenterInstance.save(failOnError: true, flush:true)
            }
            else if(Location.findByLocationDescription(description)) {
                dataCenterInstance = Location.findByLocationDescription(description)
                dataCenterInstance.properties['dataCenterID'] = dataCenterId
                dataCenterInstance.properties['locationDescription'] = description
                dataCenterInstance.properties['squareFootage'] = squareFootage
                dataCenterInstance.save(failOnError: true, flush:true)
            }
            else if(!(Location.findByLocationDescription(description))){
                Location dataCenter = new Location(dataCenterID: dataCenterId,
                        locationDescription: description,
                        squareFootage:squareFootage).save(failOnError: true, flush: true)
                println("created new data center :" + dataCenter.locationDescription)
            }

        }

        def updateStatus = Location.getAll().toString()


        updateRackAttributesWithDCIM()
    }


/*****************************************************************************
 * Update Racks with DCIM attributes
 *****************************************************************************/
/**
 * Functions: Updates rack attributes retrieved from file cabinets.txt
 */
    def updateRackAttributesWithDCIM (){
        println("waiting for rack update to complete...")

        def cabinetList = createItemList("grails-app/views/dcimConnector/queryResults/cabinets.txt");

        ArrayList <String> cabinetLabels = new ArrayList();

        for(int i = 0; i < cabinetList.size(); i++){
            def item = cabinetList.get(i)
            def label = item[0].toString()
            cabinetLabels.add(label)
        }

        ArrayList<Rack> rackList = Rack.getAll()
        ArrayList<String> racksThatHaveBeenUpdated = new ArrayList();

        //do cross check of racks in DCIM
        for(int x = 0; x < rackList.size(); x++){

            def itsId = rackList.get(x).itsId

            if(cabinetLabels.contains(itsId)){
                def index = cabinetLabels.indexOf(itsId)
                def attributes = cabinetList.get(index)

                def dataCenterId = Integer.parseInt(attributes[1])
                def cabLocation = attributes[2]
                def zoneId = Integer.parseInt(attributes[3])
                def rowId = attributes[4]

                Rack rackInstance = Rack.findByItsId(itsId)

                Location dataCenter = Location.findByDataCenterID(dataCenterId)

                rackInstance.properties['location'] = dataCenter //location in reference to datacenter
                rackInstance.properties['cabLocation'] = cabLocation
                rackInstance.properties['rowId'] = rowId
                rackInstance.properties['zoneId'] = zoneId
                rackInstance.save(failOnError: true, flush: true)

                racksThatHaveBeenUpdated.add(rackInstance.itsId)
            }
        }

        //populates slots that are occupied by other devices such as switchs, filters etc.
        //racksToBeUpdated = racksThatHaveBeenUpdated

        def updateStatus = racksThatHaveBeenUpdated.toString()

        println("racks that have been updated: " + updateStatus)

        updateOccupiedRackSlots()

    }

    def updateOccupiedRackSlots () {
        println("waiting for rack slot update to complete...")
        ArrayList<String> rackList = new ArrayList<String>()
        def racks = Rack.getAll()

        for (int i = 0; i < racks.size(); i ++){
            rackList.add(racks.get(i).itsId)
        }

        ArrayList<String> serverList = new ArrayList<String>()
        def servers = PhysicalServer.getAll()
        for (int i = 0; i < servers.size(); i ++){
            serverList.add(servers.get(i).itsId)
        }


        println("rack list: " + rackList.toString())
        println("server list: " + serverList.toString())

        def allDeviceList = createItemList("grails-app/views/dcimConnector/queryResults/devices.txt")
        ArrayList <String []> newDeviceList = new ArrayList<String []>()
        ArrayList<String> deviceListLabels = new ArrayList<String>()
        def finalDeviceList
        //ArrayList <String> devicesByRack = new ArrayList<String>()

        //filter out only wanted devices on racks
        for(int x=0; x < allDeviceList.size(); x++){
            def item = allDeviceList.get(x)
            def rackName = item[3]
            String deviceName = item[0]
            def rackNum = Integer.parseInt(rackName)

            if(rackNum > 0){
                // println("current item: " + item.toString())

                if(rackList.contains(rackName)){ //skip dontcare racks
                    newDeviceList.add(allDeviceList.get(x))
                    deviceListLabels.add(deviceName.toLowerCase())
                    //   println(x + "\n ADDED : " + item)
                }
                else{
                    //    println( x + "\n SKIPPED :" + item)
                }
            }
        }
        if(!(deviceListLabels == null)){
            println(deviceListLabels.toString())
        }else{println("list is empty")}

        for(int x=0; x < serverList.size(); x++){
            String label = serverList.get(x)
            println("current label: " + label)
            if(deviceListLabels.contains(label)){ //remove dontcare servers, already updated
                newDeviceList.remove(deviceListLabels.indexOf(label))
                deviceListLabels.remove(label)
            }
        }
        println(deviceListLabels.toString())


        for(int x=0; x < newDeviceList.size(); x++){
            def item = newDeviceList.get(x)

            println("item: " + item.toString())

            def rackName = item[3]
            def deviceLabel = item[0]
            def slotPosition  = Integer.parseInt(item[4])
            def deviceId = Integer.parseInt(item[1])
            def deviceHeight = Integer.parseInt(item[5])

            if(slotPosition > 0){

                Rack rackInstance = Rack.findByItsId(rackName)
                for(int i = 0; i < deviceHeight; i++){
                    RackUnit rackUnitInstance = rackInstance.getUnitBySlot(rackInstance,slotPosition)
                    if(!rackUnitInstance.filledBy){ //if the rack unit is not filled by asset, update occupied label
                        rackUnitInstance.properties['label'] = "Unavailable Per DCIM - " + deviceLabel
                        rackUnitInstance.properties['RUstatus'] = 'OccupiedByDCIM'
                        rackUnitInstance.properties['deviceId'] = deviceId
                        //rackUnitInstance.properties['filledBy'] = serverInstance
                        rackUnitInstance.properties['onRack'] = rackInstance
                        rackUnitInstance.properties['ru_slot'] = slotPosition

                        if(!rackUnitInstance.hasErrors()){
                            rackUnitInstance.save(failOnError: true, flush: true)
                            rackInstance.save(failOnError: true, flush: true)
                            println("updated rack: " + rackInstance.itsId)
                            println("updated rackunit: " + rackUnitInstance.label)
                        }else{println("save failed")}
                    }
                    slotPosition++
                }
            }
        }

        println("\nDCIM Update Successful")
        flash.message = "DCIM Device Update Was Successful"

        //clean up GORM
        def session = sessionFactory.currentSession
        session.flush()
        session.clear()

        //redirect(uri: '/physicalServer/list')
    }

    /*******************************************************
     /**
     * Support Methods for trimming and creating item lists from file
     */
    /*******************************************************
     /**
     * support method use in runQuery/updatewithdcim, reads in text file and creates arraylist of array items
     * @return arrayList of String []
     *
     */
    ArrayList createItemList(String fileUrl){

        def devices = new File(fileUrl)
        ArrayList items = new ArrayList();

        devices.eachLine{line ->
            String [] deviceAttributes = line.split("\t");
            items.add(deviceAttributes)
        }

        items.remove(0); //remove the header row
        return items
    }

    /**
     * pulls out device that is not associated with rack/cabinet, indicated by -1
     */

    def trimList(inputList){

        def outputList = new ArrayList<String []>();
        println("begin, master list size: " + inputList.size())
        while(!inputList.empty){
            def item = inputList.get(0)
            def val = Integer.parseInt(item[3])
            if (val < 1){
                println("REMOVED item: " + item.toString())
                inputList.remove(0)
            } else{
                outputList.add(item)
                println("ADDED item: " + item.toString())
                inputList.remove(0)
            }
        }
        println("end, master list size: " + outputList.size())
        return outputList
    }

/*****************************************************************
 * Import Servers From Production, CSV file parser
 ******************************************************************/

    /**
     * Note: This method is for development use
     * Import Servers From File physicalServer_list.csv
     */

    def importServersFromProduction (){
        println("waiting for server import to complete...")

        def serverList = createServerItemList("grails-app/views/dcimConnector/queryResults/physicalServer_list.csv");
        ArrayList <String> updatedList = new ArrayList<String>()

        for(int x = 0; x < serverList.size(); x++){

            PhysicalServer serverInstance = new PhysicalServer()

            def item = serverList.get(x)
            def itsId = item[0]
            def serverType = item[1]


            if(!(PhysicalServer.findByItsId(itsId))){
                serverInstance = new PhysicalServer(itsId: itsId, assetType: AssetType.findByName('Server - Hardware'), serverType: serverType).save(failOnError: true, flush:true)
            }
            else if (PhysicalServer.findByItsId(itsId)){
                serverInstance = PhysicalServer.findByItsId(itsId)
                //do nothing for now, allow updated from dcim
            }
            println(serverInstance.itsId)
            updatedList.add(serverInstance.itsId)
        }

        def updateStatus = updatedList.toString()

        updatePhysicalDevicesWithDCIM()
    }



    /**
     * support method for importing servers
     * @param fileUrl
     * @return
     */
    ArrayList createServerItemList(String fileUrl){

        def devices = new File(fileUrl)
        ArrayList items = new ArrayList();

        devices.eachLine{line ->
            String [] deviceAttributes = line.split(",");

            for(int x=0; x < deviceAttributes.size(); x++){
                def temp = deviceAttributes[x]
                deviceAttributes[x] = trimQuotes(temp)
            }
            items.add(deviceAttributes)
        }

        items.remove(0); //remove the header row
        return items
    }

    def trimQuotes(String item){
        String temp1 = item.substring(1, item.length())
        String temp2 = temp1.substring(0, temp1.length() -1)
        return temp2
    }


}
