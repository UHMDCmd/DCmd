package edu.hawaii.its.dcmd.inf

class Replacement {

    Asset replacement
    Asset main_asset

    Integer priority
    Date ready_date
    String replacement_notes

    static auditable = true


//    static belongsTo = [main_asset:Asset]
    static constraints = {
        main_asset(nullable: false)
        replacement(nullable: false)
        priority(nullable: true)
        ready_date(nullable: true)
        replacement_notes(nullable: true, maxSize: 1024)
    }
    static belongsTo = [main_asset: Asset]

    String toString(){
        "${replacement.toString()}"
    }
}
