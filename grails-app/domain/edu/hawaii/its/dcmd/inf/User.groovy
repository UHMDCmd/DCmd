package edu.hawaii.its.dcmd.inf

import com.logicmpls.Crumb

class User {

    transient springSecurityService

    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    int themeVal


    ArrayList <Crumb> BreadCrumbs = new ArrayList<Crumb>();

    static auditable = true


    static constraints = {
        username blank: false, unique: true
        password blank: false
        enabled(default:true)
        themeVal (nullable: true)

    }

    static mapping = {
        password column: '`password`'
        version false
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }
    UserRole getAuthority() {
        UserRole.findAllByUser(this).first()    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }



}
