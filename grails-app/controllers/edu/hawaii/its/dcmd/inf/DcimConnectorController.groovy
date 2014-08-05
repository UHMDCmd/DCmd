package edu.hawaii.its.dcmd.inf

import org.apache.ivy.core.settings.Validatable
import org.apache.tools.ant.types.Description
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsHibernateUtil
import ssh.*

import javax.activation.DataSource


class DcimConnectorController {

    def sshConfig
    def port=22;
    AssetService assetService
    def sessionFactory


    def index() {}

    /**
     *   retrieve all devices, cabinets and datacenters via ssh connection to dcim.its.hawaii.edu/keller
     */
    def runQuery = {
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
        render(view: "index", model: [ak: result, datacenters: dataCenterList.size(), cabinets: cabinetList.size(), devices:deviceList.size()])
    }

    /**********************************************************************
     * Update Device Controller Action
     *********************************************************************/

    /**
     * Update the DCmd servers list through file saved in queryResults directory in dcimConnector view dir.
     */
    def updatePhysicalDevicesWithDCIM =  {
        //servers, dataCenterList, cabinetList, deviceList
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
        addDevicesToRacks(masterList)

        //create another def to update rack attributes, send rackupdatelist to this

        def updateStatus = "found " + size + " out of " + servers.size()
        render(view:"updatePhysicalDevicesWithDCIM", model:[updateStatus:updateStatus, notFound: notFound.toString()])

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
            PhysicalServer temp = PhysicalServer.findByItsId(deviceLabel)
            def serverInstance = Asset.findById(temp.id)
            RackUnit rackUnitInstance = rackInstance.getUnitBySlot(rackInstance, slotPosition)

            //update the server attributes
            serverInstance = updateServerInstance(serverInstance,item)

            if(!serverInstance.hasErrors()){
                serverInstance.save(failOnError: true, flush:true)
                println("++updated server++")
            }
            else{
                println("errors occured during server save, on server: " + serverInstance.itsId)
            }

//            update rack unit on current rack to take server
            rackUnitInstance.properties['label'] = deviceLabel
            rackUnitInstance.properties['RUstatus'] = 'Filled'
            rackUnitInstance.properties['filledBy'] = serverInstance
            rackUnitInstance.properties['onRack'] = rackInstance
            rackUnitInstance.properties['ru_slot'] = slotPosition

            rackUnitInstance.save(failOnError: true, flush: true)

            rackInstance.save(failOnError:true, flush:true)

            println("server rack assignment: " + serverInstance.getRackAssignment())
            println("server instance filled: " + rackUnitInstance.filledBy)
            println("rack unit on rack: " + rackUnitInstance.onRack.itsId)
            println("rack units" + rackInstance.RUs.toString())

            if(rackInstance.RUs.contains(rackUnitInstance)){
                println("rackunit is on rack")
            }
            else{
                println("unit is not on rack")
            }
        }
    }

    def updateServerInstance(serverInstance, parameters){

        serverInstance.properties['RU_size'] = 1;
        serverInstance.properties['RU_begin'] = Integer.parseInt(parameters[4]); //slot position
        serverInstance.properties['serialNo'] = parameters[2];

        return serverInstance
    }
/****************************************************************************
 * Update DataCenters
 ****************************************************************************/
    def updateDataCenters = {

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

        render(view:'updateDataCenters', model:[updateStatus:updateStatus])

    }


/*****************************************************************************
 * Update Racks with DCIM attributes
 *****************************************************************************/

    def updateRackAttributes ={
        println('updating rack attributes...')

        def cabinetList = createItemList("grails-app/views/dcimConnector/queryResults/cabinets.txt");

        ArrayList <String> cabinetLabels = new ArrayList();

        for(int i = 0; i < cabinetList.size(); i++){
            def item = cabinetList.get(i)
            def label = item[0].toString()
            cabinetLabels.add(label)
        }

        ArrayList<Rack> rackList = Rack.getAll()
        ArrayList<String []> attributesToUpdateFrom = new ArrayList();

        //do cross check of racks in DCIM
        for(int x = 0; x < rackList.size(); x++){
            def itsId = rackList.get(x).itsId
            if(cabinetLabels.contains(itsId)){
                def index = cabinetLabels.indexOf(itsId)
                def attributes = cabinetList.get(index)
                Rack rackInstance = Rack.findByItsId(itsId)
                def dataCenterId = Integer.parseInt(attributes[1])
                def cabLocation = attributes[2]
                def zoneId = Integer.parseInt(attributes[3])
                def rowId = attributes[4]

                Location dataCenter = Location.findByDataCenterID(dataCenterId)

                rackInstance.properties['location'] = dataCenter //location in reference to datacenter
                rackInstance.properties['cabLocation'] = cabLocation
                rackInstance.properties['rowId'] = rowId
                rackInstance.properties['zoneId'] = zoneId
                rackInstance.save(failOnError: true, flush: true)

            }
        }


        def updateStatus = attributesToUpdateFrom.toString()
        render(view:"updateRackAttributes", model:[updateStatus:updateStatus])

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




}
