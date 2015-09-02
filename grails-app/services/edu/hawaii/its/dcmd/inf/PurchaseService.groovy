package edu.hawaii.its.dcmd.inf
import grails.gorm.DetachedCriteria
import org.hibernate.Criteria
import org.hibernate.criterion.Projections

import java.text.DecimalFormat
import java.text.NumberFormat

class PurchaseService {

    static transactional = true

    def listAllPurchases(params) {

        def sortIndex = params.sidx ?: 'id'
        def sortOrder  = params.sord ?: 'asc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        //def titles = Purchase.createCriteria().list().unique() {

        //}
//        def titles = Purchase.list().sort {a,b-> b.fiscalYear <=> a.fiscalYear }.unique() {it.uhContractTitle}

//        println(titles)

        def today = new Date()

        def purchases = Purchase.createCriteria().list(max: maxRows, offset: rowOffset) {

            if(params.fiscalYear) eq('fiscalYear', params.fiscalYear.toInteger())
            if (params.uhContractTitle) ilike('uhContractTitle', "%${params.uhContractTitle}%")
            if(params.vendorName) ilike('vendorName', "%${params.vendorName}%")
            if (params.purchaseType)
                purchaseType {
                    ilike('abbreviation', "%${params.purchaseType}%")
                }
            if (params.paymentType) ilike('paymentType', "%${params.paymentType}%")
            if (params.purchaseStatus) ilike('purchaseStatus', "%${params.purchaseStatus}%")
            if (params.anniversary) eq('anniversary', Date.parse( 'MM/dd', params.anniversary))
            if (params.generalNote) ilike('generalNote', "%${params.generalNote}")

        //    order('fiscalYear', "desc")
            eq('currentPurchase', true)

            if(params.elapsingFilter == true) {
                and {
                    or {
                        items {
                            between('endDate', today, today + 90)
                        }
                        and {
                            eq('multiyear', true)
                            items {
                                between('endDate', today, today + 270)
                            }
                        }
                    }
                    and {
                        notEqual('purchaseStatus', 'To Terminate')
                        notEqual('purchaseStatus', 'Terminated')
                    }
                }
            }

            order(sortIndex, sortOrder)

            resultTransformer Criteria.DISTINCT_ROOT_ENTITY
        }
        /*
        purchases.unique() {it.uhContractTitle}
        if(sortOrder == 'asc')
            purchases.sort() {it.getProperty(sortIndex)}
        else
            purchases.sort() {-it.getProperty(sortIndex)}

*/
        def totalRows = purchases.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)
        NumberFormat formatter = new DecimalFormat("0.00")
        def results = purchases?.collect { [ cell: [
                '',
                it.fiscalYear,
                it.uhContractTitle,
                it.vendorName,
                it.purchaseStatus,
                it.purchaseType?.abbreviation,
                it.paymentType,
                it.getTotalQuantity(),
                formatter.format(it.getTotalTax()),
                formatter.format(it.getTotalCost()),
                it.anniversary?.format('MM/dd'),
                it.generalNote, it.id], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def listAllPurchaseSubGrid(params) {

        def sortIndex = params.sidx ?: 'fiscalYear'
        def sortOrder  = params.sord ?: 'desc'
        def maxRows = Integer.valueOf(params.rows)
        def currentPage = Integer.valueOf(params.page) ?: 1
        def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

        def thePurchase = Purchase.get(params.id)

        def purchases = Purchase.createCriteria().list(max: maxRows, offset: rowOffset) {

            ilike('uhContractTitle', thePurchase.uhContractTitle)
//            eq('currentPurchase', false)
            order('fiscalYear', "desc")
        }

        def totalRows = purchases.totalCount
        def numberOfPages = Math.ceil(totalRows / maxRows)

        NumberFormat formatter = new DecimalFormat("0.00")
        def results = purchases?.collect { [ cell: [
                '',
                it.fiscalYear,
                it.purchaseStatus,
                it.purchaseType?.abbreviation,
                it.paymentType,
                it.getTotalQuantity(),
                formatter.format(it.getTotalTax()),
                formatter.format(it.getTotalCost()),
//                it.periodBeginDate?.format('mm/dd/yyyy'),
//                it.periodEndDate?.format('mm/dd/yyyy'),
                it.anniversary?.format('MM/dd') ,
                it.generalNote,
                it.id], id: it.id ] }

        def jsonData = [rows: results, page: currentPage, records: totalRows, total: numberOfPages]
        return jsonData
    }

    def updateCurrentPurchase(title) {
        def purchaseList = Purchase.createCriteria().list() {
            ilike('uhContractTitle', title)
        }


        def maxYear = purchaseList.max {it.fiscalYear}
        def updated=false;

        if (maxYear != null) {
            //        println(maxYear.fiscalYear)
            purchaseList.each {
                if (it.currentPurchase && it.fiscalYear < maxYear.fiscalYear) {
                    it.currentPurchase = false
                    it.save(flush: true)
                    updated = true
                }
            }
            maxYear.currentPurchase = true
            maxYear.save(flush: true)
        }
        return updated
    }

}
