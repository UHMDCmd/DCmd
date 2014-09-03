package edu.hawaii.its.dcmd.inf

import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.plugin.springsecurity.userdetails.GrailsUserDetailsService
import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * Dumb service which just returns a UserDetails object with the username set.
 * @author esword
 */
class EmptyUserDetailsService implements GrailsUserDetailsService {

    /**
     * Taken from GormUserDetailsService: Some Spring Security classes expect at least one
     * role, so we give a user with no granted roles this one which gets past that restriction
     * but doesn't grant anything.
     */
    static final List NO_ROLES = [new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)]

    UserDetails loadUserByUsername(String username, boolean loadRoles) throws UsernameNotFoundException{
        return loadUserByUsername(username)
    }

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User.withTransaction { status ->
            System.out.println(username)
            System.out.println(User.all)
            User user = User.findByUsername(username)


            if (!user) {
//                throw new UsernameNotFoundException('User not permitted', username)
    //            System.out.println("User not found!")
                return new edu.hawaii.its.dcmd.inf.EmptyUserDetails('failed', NO_ROLES)
            }

            System.out.println("User Found!")
            def authorities = user.getAuthorities().collect() {
                new GrantedAuthorityImpl(it.authority)
            }
            return new edu.hawaii.its.dcmd.inf.EmptyUserDetails(user.username, authorities ?: NO_ROLES)
        }
    }
}