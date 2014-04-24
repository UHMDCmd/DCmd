package edu.hawaii.its.dcmd.inf

class Role {

    String authority

    static auditable = true


    static mapping = {
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }
}
