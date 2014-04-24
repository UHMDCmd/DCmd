// Place your Spring DSL code here
//import edu.hawaii.its.dcmd.inf.MyUserDetailsContextMapper
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider

beans = {
    ldapUserDetailsMapper(MyUserDetailsContextMapper) {
        // bean attributes
    }

}


//beans = {
//    userDetailsService(edu.hawaii.its.dcmd.inf.EmptyUserDetailsService)
//
//
//}
