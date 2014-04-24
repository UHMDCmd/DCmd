package edu.hawaii.its.dcmd.inf

import grails.converters.JSON

class ItcRackController {
    /*
    def assetService
    def generalService

    def index() { }
    def list ={}
    def list(){}

    def listAllRack = {
        def jsonData = assetService.listAllRack(params)
        render jsonData as JSON
    }

    def getRackData = {
        def itsId = params.rackId
        println("rack id " + itsId)
        def rack = Rack.findByItsId(itsId)
        println("retrieved rack: " + rack.itsId)

//        def jsonData =  currentRack.RUs.toListString()
        def jsonData
//        jsonData = rack.collect{[it.,it.voltage, it.pole_1, it.pole_2, [it.strips.itsId]]}

        def imageIds = getImageIds(rack)
        def slot_occ = getSlotOcc(rack) //number of rackunits a device occupies at one time

        def rackSlots = new ArrayList<Integer>()

        for (int i = 0; i < rack.RUs.size(); i++){
            RackUnit ru = new RackUnit()
            ru = rack.RUs.get(i)
            rackSlots.add(ru.ru_slot)

            println("retrieved ru: " + ru.ru_slot)
        }

        jsonData =  [rack.itsId,rack.RUs, imageIds, rackSlots, slot_occ]

        render jsonData as JSON

    }

    def saveRackUnit = {
        def rack = Rack.findByItsId(params.itsId)
        def slot_num = Integer.parseInt(params.ruId)
        def imageId = Integer.parseInt(params.imageId)
        def slot_size = Integer.parseInt(params.slot_size)
        def rackUnitInstance

        def jsonData

        RackUnit oldUnit = rack.getUnitBySlot(rack, slot_num) //checks item in list, match for slotnum attribute

        if (oldUnit != null){
            println("found old unit, removing from collection...")
            rack.removeFromRUs(oldUnit)
            oldUnit.delete()
            rack.save(flush: true, failOnError: true)

            if (rack.RUs.contains(oldUnit)){
                println('the unit was not removed')
            }
            else{
                println("old unit removed!!")
                println("ru size: " + rack.RUs.size())

            }
        }
        else{
            println("there was no previous unit, creating new unit instance")
        }


        println("imageId: " + imageId)
        if(imageId != 0){
            rackUnitInstance = new RackUnit(onRack:rack, ru_slot: slot_num , imageId: params.imageId, slotSize:slot_size)



            if(!rackUnitInstance.hasErrors() && rackUnitInstance.save(flush: true, failOnError: true)){
                rack.addToRUs(rackUnitInstance)
                //rack.RUs.add(slot_num,rackUnitInstance)
                println("rack size " + rack.RUs.size())
                println("Added new rack instance")
                println("Rack: " + rack.itsId + " ; new Rack unit on: " + rackUnitInstance.onRack.itsId + " ; ru_slot: " + rackUnitInstance.ru_slot + " ; slot_size: "+ rackUnitInstance.slotSize +" ; imageId: " + rackUnitInstance.imageId)
            }
            else{
                println("There was an error in creating new rack instance.")
            }

        }

        if (!rack.hasErrors() && rack.save(flush: true, failOnError: true)){
            rack.RUs.sort({a,b -> b.ru_slot <=> a.ru_slot} as Comparator)
            println("rack saved and updated")
        }
        else{
            println("error in save")
        }


//        getImageIds(rack)
//
//        jsonData =  [rack.itsId,rack.RUs]
//        render jsonData as JSON
    }

    ArrayList getImageIds (Rack rack){
        def idList = new ArrayList()
        def RUs = rack.RUs.toArray()
        for(int x = 0; x < RUs.size(); x++){
            RackUnit rackUnit = RUs[x]

            println("unit: " + rackUnit.ru_slot + " imageId: " + rackUnit.imageId)

            idList.add(rackUnit.imageId)
        }

        return idList
    }

    ArrayList getSlotOcc (Rack rack){
        def idList = new ArrayList()
        def RUs = rack.RUs.toArray()
        for(int x = 0; x < RUs.size(); x++){
            RackUnit rackUnit = RUs[x]

            println("unit: " + rackUnit.ru_slot + " slot occ: " + rackUnit.slotSize)

            idList.add(rackUnit.slotSize)
        }

        return idList
    }

    def upload() {
        println('upload action called')
        def img = new DeviceImage(params)
        img.save(failOnError: true, flush: true)

        println(DeviceImage.getAll().toListString())

    }

    /*
     TODO: Create input for pdu select type
     */

}
