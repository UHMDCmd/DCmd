package edu.hawaii.its.dcmd.inf

import grails.converters.JSON

class PurchaseController {

    def purchaseService

    def scaffold = Purchase

    def static Boolean elapsingFilter

    def listAllPurchases = {

        params.elapsingFilter = elapsingFilter
        def jsonData = purchaseService.listAllPurchases(params)
        render jsonData as JSON
    }

    def listAllPurchaseSubGrid= {
        def jsonData = purchaseService.listAllPurchaseSubGrid(params)
        render jsonData as JSON
    }

    def editPurchase = {
        // Convert incoming JSON to params structure
        request.JSON.each { k,v ->
            params[k] = v
        }

        def item = null
        def message = ""
        def state = "FAIL"
        def id
        def contractTitle = params.uhContractTitle

        if(params.uhContractBeginDate)
            params.uhContractBeginDate = Date.parse('MM/dd/yyyy', params.uhContractBeginDate)
        if(params.uhContractEndDate)
            params.uhContractEndDate = Date.parse('MM/dd/yyyy', params.uhContractEndDate)
        /*
        if(params.periodBeginDate)
            params.periodBeginDate = Date.parse('mm/dd/yyyy', params.periodBeginDate)
        if(params.periodEndDate)
            params.periodEndDate = Date.parse('mm/dd/yyyy', params.periodEndDate)
            */

        if(params.anniversary)
            params.anniversary = Date.parse('MM/dd', params.anniversary)

        if(params.oper == 'pageEdit') {
            if (params.purchaseType)
                params.purchaseType = PurchaseType.get(params.purchaseType)
            params.oper = 'edit'
        }
        else {
            if (params.purchaseType)
                params.purchaseType = PurchaseType.findByAbbreviation(params.purchaseType)
        }

        def response = [state:"FAIL", wasUpdate:false]
        def updateFlag=false
        // determine our action
        switch(params.oper) {
            case 'edit':
                item = Purchase.get(params.id)
                if(params.fiscalYear != item.fiscalYear)
                    updateFlag=true

                item.properties = params
                if (!item.hasErrors() && item.save()) {
                    response.id = item.id
                    response.state = "OK"
                    contractTitle = item.uhContractTitle
                } else {
                    response.message = item.errors.errorCount
                }
            break
            case 'add':
                updateFlag=true
                params.hawaiiTaxRate = 4.712 // Default Tax Rate
                item = new Purchase(params)
                if (!item.hasErrors() && item.save()) {
                    response.id = item.id
                    response.state = "OK"
                    response.object = item
                    contractTitle = item.uhContractTitle
                } else {
                    println("Add failed")
                    response.message = item.errors.errorCount
                }
            break
            case 'del':
                updateFlag=true
                item = Purchase.get(params.id)
                contractTitle = item.uhContractTitle
                if(item) {
                    item.delete(flush:true)
                    response.state = "OK"
                }
                else
                    response.message = item.errors.errorCount
            break
        }
        if(updateFlag) {
            response.wasUpdated = purchaseService.updateCurrentPurchase(contractTitle)
        }

        render response as JSON
    }

    def exportListAll = {
        params.rows = Integer.MAX_VALUE

        def theList = []
        def jsonData = purchaseService.listAllPurchases(params)
        jsonData.rows.each { theRow ->

            def tempVals = [
                    'F/Y': theRow.cell[1],
                    'Contract/ID': theRow.cell[2],
                    'Vendor name': theRow.cell[3],
                    'Status': theRow.cell[4],
                    'Type': theRow.cell[5],
                    'Payment Type': theRow.cell[6],
                    'Items': theRow.cell[7],
                    'Total Tax': theRow.cell[8],
                    'Total Cost': theRow.cell[9],
                    'Anniversary': theRow.cell[10],
                    'Notes': theRow.cell[11]
            ]
            theList.add(tempVals)
        }
        render theList as JSON
    }

    def getPurchaseDetails = {

        def purchase = Purchase.get(params.Id)
        def response = purchase as JSON
        //def response = [retVal: true, itsId:server.itsId, status:server.status?.abbreviation]
        //render response as JSON
        render response

    }

    def getItemsByPurchase = {

        def items = PurchaseItem.createCriteria().list() {
            eq('purchase.id', params.purchaseId.toLong())
        }
        items.each { item ->
            //item.metaClass.assetName = [id:item.asset?.id, name:item.asset?.itsId]
            item.metaClass.assetName = item.asset?.itsId
        }
        render items as JSON
    }

    def getPurchaseTypeList = {
        def lst = PurchaseType.createCriteria().list {
            order('abbreviation', 'asc')
        }

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.toString()}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def cloneRow = {
        def original = Purchase.get(params.id)
        def newPurchase = new Purchase(original.properties)
        newPurchase.fiscalYear = original.fiscalYear+1
        newPurchase.currentPurchase=true
        newPurchase.save(failOnError:true)

        def purchaseItem
        PurchaseItem.findAll{
            purchase.id == original.id
        }.each { item ->
            purchaseItem = new PurchaseItem(item.properties)
            purchaseItem.purchase = newPurchase
            purchaseItem.save(failOnError:true)
        }
        def supportRole
        SupportRole.findAll{
            supportedObject.id == original.id
        }.each { role ->
            supportRole = new SupportRole(role.properties)
            supportRole.supportedObject = newPurchase
            supportRole.save(failOnError:true)
        }

        def response = [retVal:true, purchase: newPurchase, purchaseType: newPurchase.purchaseType?.abbreviation]
        render response as JSON
    }

    def clonePurchaseItem = {

        def original = PurchaseItem.get(params.id)
        def newItem = new PurchaseItem(original.properties)
        newItem.description = original.description + " (copy)"
        newItem.save(failOnError:true)

        def response = [retVal:true, purchaseItem: newItem]
        render response as JSON
    }


    def getPurchaseTypes = {

        def typeList = PurchaseType.getAll()

        render typeList as JSON
    }

    def getPurchaseStatuses = {
        def statusList = []

        statusList.add(['id':'Draft', 'text':'Draft'])
        statusList.add(['id':'Pending', 'text':'Pending'])
        statusList.add(['id':'Completed', 'text':'Completed'])
        statusList.add(['id':'Canceled', 'text':'Canceled'])
        statusList.add(['id':'To Terminate', 'text':'To Terminate'])
        statusList.add(['id':'Terminated', 'text':'Terminated'])

        render statusList as JSON
    }

    def getPaymentTypes = {
        def typeList = []

        typeList.add(['id':'Contract mod', 'text':'Contract mod'])
        typeList.add(['id':'PO', 'text':'PO'])
        typeList.add(['id':'pCard', 'text':'pCard'])

        render typeList as JSON
    }

    def getPurchaseItemTypes = {
        def typeList = []

        typeList.add(['id':'Server', 'text':'Server'])
        typeList.add(['id':'Storage', 'text':'Storage'])
        typeList.add(['id':'Software', 'text':'Software'])
        typeList.add(['id':'Support', 'text':'Support'])
        typeList.add(['id':'Membership', 'text':'Membership'])
        typeList.add(['id':'Misc', 'text':'Misc'])

        render typeList as JSON
    }

    def listAllPurchaseItems = {

        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows


        def purchaseItems = PurchaseItem.createCriteria().list(max: maxRows, offset: rowOffset) {
            purchase {
                eq('id', params.Id.toLong())
            }
            //if (params.purchaseStatus) ilike('purchaseStatus', "%${params.purchaseStatus}%")
            order(sortIndex, sortOrder)
        }

        def totalRows = purchaseItems.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        def results = purchaseItems?.collect { [ cell: [
                '',
                it.description,
                it.itemType,
                it.assetToString(),
                it.asset?.id,
                it.quantity,
                it.itemUnitListPrice,
                it.itemsCostBeforeTax,
                it.itemsCostTaxOnly,
                it.itemsCost,
                it.beginDate?.format('MM/dd/yyyy'),
                it.endDate?.format('MM/dd/yyyy'),
                it.generalNote, it.id], id: it.id ] }


        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        render jsonData as JSON
    }

    def editPurchaseItem = {
        def item = null
        def message = ""
        def state = "FAIL"
        def id
        def thePurchase = Purchase.get(params.purchase)

        if(params.assetName)
            params.asset = Asset.get(params.assetName)
        if(params.purchase)
            params.purchase = Purchase.get(params.purchase)
        if(params.beginDate)
            params.beginDate = Date.parse('MM/dd/yyyy', params.beginDate)
        if(params.endDate)
            params.endDate = Date.parse('MM/dd/yyyy', params.endDate)


        def response = [state:"FAIL"]
        // determine our action
        switch (params.oper) {
            case 'add':
                params.beginDate = params.purchase?.uhContractBeginDate
                params.endDate = params.purchase?.uhContractEndDate
                item = new PurchaseItem(params)
                if (! item.hasErrors() && item.save()) {
                    response.id = item.id
                    response.object = item.properties
                    response.beginDate = item.beginDate?.format('MM/dd/yyyy')
                    response.endDate = item.endDate?.format('MM/dd/yyyy')

                    response.state = "OK"
                } else {
                    response.message = item.errors.fieldErrors[0]
                }
                break;
            case 'edit':
                item = PurchaseItem.get(params.id)
                item.properties = params
                if (! item.hasErrors() && item.save()) {

                    response.id = item.id
                    response.state = "OK"
                } else {
                    response.message = item.errors.errorCount
                }
                break;
            case 'del':
                item = PurchaseItem.get(params.id)
                if (item) {
                    response.id = item.id
                    if (! item.hasErrors() && item.delete()) {
//                        message = "Replacement ${params.replacement} for Asset ${params.main_asset} Deleted."
                        response.state = "OK"
                    }
                }
                break;
        }
//        response = [message:message,state:state,id:id]

        render response as JSON
    }

    def getAssetIdByPurchaseItem = {
        render PurchaseItem.get(params.id)?.asset?.id
    }

    def setElapsingFilter = {
        elapsingFilter = true
        def response = [filterVal:true, state: "OK", id: 1]
        render response as JSON
    }


    def clearElapsingFilter = {
        elapsingFilter = false
        def response = [filterVal:false, state: "OK", id: 1]
        render response as JSON
    }

}