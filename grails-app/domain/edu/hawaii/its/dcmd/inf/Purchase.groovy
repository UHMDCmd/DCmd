package edu.hawaii.its.dcmd.inf

class Purchase extends SupportableObject{

    Integer fiscalYear
    PurchaseType purchaseType
    String paymentType
    String purchaseStatus

    Boolean hasSupportRenewal
    BigDecimal hawaiiTaxRate

    Person requestedBy
    Person facilitatedBy

    String vendorName
    Integer vendorCustomerId
    Integer vendorContractId
    Integer superQuoteId

    Boolean currentPurchase
//    String uhContractNumber
    String uhContractTitle

    Date uhContractBeginDate
    Date uhContractEndDate

 //   Date periodBeginDate
 //   Date periodEndDate
    Date anniversary

//    Boolean pcard
//    Boolean requisition

    Boolean multiyear

    String generalNote
    String supportableType = "purchase"

    static hasMany = [
            items: PurchaseItem
    ]
    static mapping = {
        items cascade: 'all-delete-orphan'
        tablePerHierarchy false
    }

    static constraints = {
        currentPurchase(nullable:true, default:false)
        fiscalYear(nullable:false)
        purchaseType(nullable:true)
        paymentType(nullable:true)
        purchaseStatus(nullable:true)

        hasSupportRenewal(nullable:true)
        hawaiiTaxRate(nullable:true, default:4.712)

        requestedBy(nullable:true)
        facilitatedBy(nullable:true)

        vendorName(nullable:true)
        vendorCustomerId(nullable:true)
        vendorContractId(nullable:true)
        superQuoteId(nullable:true)

//        uhContractNumber(nullable:true)
        uhContractTitle(nullable:true)

        uhContractBeginDate(nullable:true)
        uhContractEndDate(nullable:true)
//        periodBeginDate(nullable:true)
//        periodEndDate(nullable:true)
        anniversary(nullable:true)

        multiyear(nullable:true, default:false)
//        pcard(nullable:true)
//        requisition(nullable:true)
        generalNote(nullable:true)
    }

    String toString(){
        "FY" + fiscalYear + "-" + uhContractTitle
    }

    Integer getTotalQuantity() {
        Integer quantity = PurchaseItem.createCriteria().get() {
            eq("purchase.id", id)
            projections {
                sum('quantity')
            }
        }
        if(quantity != null)
            return quantity
        else
            return 0
    }

    BigDecimal getTotalTax() {
        BigDecimal tax = PurchaseItem.createCriteria().get() {
            eq("purchase.id", id)
            projections {
                sum('itemsCostTaxOnly')
            }
        }
        if(tax != null)
            return tax
        else
            return 0
    }

    BigDecimal getTotalCost() {
        BigDecimal cost = PurchaseItem.createCriteria().get() {
            eq("purchase.id", id)
            projections {
                sum('itemsCost')
            }
        }
        if(cost != null)
            return cost
        else
            return 0
    }
}
