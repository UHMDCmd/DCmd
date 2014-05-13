package edu.hawaii.its.dcmd.inf

import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class EmptyUserDetails extends GrailsUser {

    EmptyUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities, long id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, id)
    }
    EmptyUserDetails(String username, Collection<GrantedAuthority> authorities) {
        super(username, '', true, true, true, true, authorities, 1)
    }
}

