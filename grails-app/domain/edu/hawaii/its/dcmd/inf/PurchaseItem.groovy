package edu.hawaii.its.dcmd.inf

import java.text.DecimalFormat
import java.text.NumberFormat

class PurchaseItem {

    Purchase purchase

    String itemType
    String description

    Integer quantity

    Asset asset

    BigDecimal itemUnitListPrice
    BigDecimal itemsCostBeforeTax
    BigDecimal itemsCostTaxOnly
    BigDecimal itemsCost

    Date beginDate
    Date endDate

    String generalNote

    static constraints = {
        purchase(nullable:true)
        itemType(nullable:true)
        description(nullable:true)
        quantity(nullable:true)
        asset(nullable:true)
        itemUnitListPrice(nullable:true, scale:2)
        itemsCostBeforeTax(nullable:true, scale:2)
        itemsCostTaxOnly(nullable:true, scale:2)
        itemsCost(nullable:true, scale:2)
        beginDate(nullable:true)
        endDate(nullable:true)
        generalNote(nullable:true)
    }
    static belongsTo = [purchase: Purchase] // Deletes and saves cascade from Purchase to PurchaseItems

    String assetToString() {
        if(asset)
            return asset.toString()
        else
            return ""
    }

    String getUnitPriceString() {
        NumberFormat formatter = new DecimalFormat("0.00")
        if(itemUnitListPrice)
            return formatter.format(itemUnitListPrice)
        else
            return ""
    }
    String getPreTaxString() {
        NumberFormat formatter = new DecimalFormat("0.00")
        if(itemsCostBeforeTax)
            return formatter.format(itemsCostBeforeTax)
        else
            return ""
    }
    String getTaxCostString() {
        NumberFormat formatter = new DecimalFormat("0.00")
        if(itemsCostTaxOnly)
            return formatter.format(itemsCostTaxOnly)
        else
            return ""
    }
    String getTotalCostString() {
        NumberFormat formatter = new DecimalFormat("0.00")
        if(itemsCost)
            return formatter.format(itemsCost)
        else
            return ""
    }
}
