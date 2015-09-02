package edu.hawaii.its.dcmd.inf

class PurchaseType {

    String abbreviation
    String fullName

    static constraints = {
        abbreviation(nullable: true)
        fullName(nullable: true)
    }

    String toString() {
        abbreviation
    }
}
