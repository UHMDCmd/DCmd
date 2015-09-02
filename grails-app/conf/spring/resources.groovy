/*
 * Copyright (c) 2014 University of Hawaii
 *
 * This file is part of DataCenter metadata (DCmd) project.
 *
 * DCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DCmd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCmd.  It is contained in the DCmd release as LICENSE.txt
 * If not, see <http://www.gnu.org/licenses/>.
 */

// Place your Spring DSL code here
//import edu.hawaii.its.dcmd.inf.MyUserDetailsContextMapper
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider

beans = {
//    ldapUserDetailsMapper(MyUserDetailsContextMapper) {
        // bean attributes
//    }

}


beans = {
    userDetailsService(edu.hawaii.its.dcmd.inf.EmptyUserDetailsService)

}
