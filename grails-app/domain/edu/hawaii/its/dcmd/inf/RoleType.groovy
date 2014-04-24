package edu.hawaii.its.dcmd.inf

class RoleType {

    String roleName

    static constraints = {
        roleName(nullable:false)
    }

    String toString() {
        roleName
    }
}
