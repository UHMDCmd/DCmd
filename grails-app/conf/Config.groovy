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

//package edu.hawaii.its.dcmd.inf

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

//def assetService

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

grails.app.context="its/dcmd"

grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
    xml: ['text/xml', 'application/xml'],
    text: 'text/plain',
    js: 'text/javascript',
    rss: 'application/rss+xml',
    atom: 'application/atom+xml',
    css: 'text/css',
    csv: 'text/csv',
    pdf: 'application/pdf',
    rtf: 'application/rtf',
    excel: 'application/vnd.ms-excel',
    ods: 'application/vnd.oasis.opendocument.spreadsheet',
    all: '*/*',
    json: ['application/json','text/json'],
    form: 'application/x-www-form-urlencoded',
    multipartForm: 'multipart/form-data'
]

//grails.plugin.springsecurity.useBasicAuth = true
//grails.plugin.springsecurity.basic.realmName = "API"

//grails.plugin.springsecurity.filterChain.chainMap = [
//        '/**':'securityContextPersistenceFilter,logoutFilter,basicAuthenticationFilter,securityContextHolderAwareRequestFilter,exceptionTranslationFilter,filterInvocationInterceptor'
        //'/**':'securityContextPersistenceFilter,logoutFilter,authenticationProcessingFilter,securityContextHolderAwareRequestFilter,rememberMeAuthenticationFilter,anonymousAuthenticationFilter,exceptionTranslationFilter,filterInvocationInterceptor'
//]

grails.stringchararrayaccessor.disabled=true

grails.databinding.useSpringBinder=true

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

grails.plugin.springsecurity.cas.loginUri = '/login'

grails.plugin.springsecurity.ldap.context.managerDn = '[dc=hawaii,dc=edu]'
grails.plugin.springsecurity.ldap.context.managerPassword = '[]'
grails.plugin.springsecurity.ldap.context.server = 'ldap://ldap1.its.hawaii.edu:389/'
grails.plugin.springsecurity.ldap.authorities.groupSearchBase =
    '[dc=hawaii,dc=edu,ou=People]'
grails.plugin.springsecurity.ldap.search.base = '[dc=hawaii,dc=edu,ou=People]'

/***************************************************************************************
 * Un-comment this for Production
 ***************************************************************************************/
//grails.plugins.springsecurity.cas.serviceUrl = 'http://www.hawaii.edu/its/dcmd/j_spring_cas_security_check'
//grails.plugins.springsecurity.cas.proxyCallbackUrl = 'http://www.hawaii.edu/its/dcmd/secure/receptor'
//grails.plugins.springsecurity.logout.afterLogoutUrl =
//    'https://login.its.hawaii.edu/cas/logout?url=http://www.hawaii.edu/its/dcmd/'

/***************************************************************************************
 * Un-comment this for Local Development
 ***************************************************************************************/
grails.plugin.springsecurity.cas.serviceUrl = 'http://localhost:8080/its/dcmd/j_spring_cas_security_check'
grails.plugin.springsecurity.cas.proxyCallbackUrl = 'http://localhost:8080/its/dcmd/secure/receptor'
grails.plugin.springsecurity.logout.afterLogoutUrl =
    'https://login.its.hawaii.edu/cas/logout?url=http://localhost:8080/its/dcmd/'

/***************************************************************************************
 * Un-comment this for Test
 ***************************************************************************************/
//grails.plugin.springsecurity.cas.serviceUrl = 'http:///dcm51.pvt.hawaii.edu:8080/its/dcmd/j_spring_cas_security_check'
//grails.plugin.springsecurity.cas.proxyCallbackUrl = 'http:///dcm51.pvt.hawaii.edu:8080/its/dcmd/secure/receptor'
//grails.plugin.springsecurity.logout.afterLogoutUrl =
//    'https://login.its.hawaii.edu/cas/logout?url=http:///dcm51.pvt.hawaii.edu:8080/its/dcmd/'




grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/user/home'
grails.plugin.springsecurity.cas.serverUrlPrefix = 'https://login.its.hawaii.edu/cas'

grails.plugin.springsecurity.cas.proxyReceptorUrl = '/secure/receptor'

grails.plugin.springsecurity.useSecurityEventListener = true

grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"

//grails.plugin.springsecurity.rejectIfNoRule = false
//grails.plugin.springsecurity.fii.rejectPublicInvocations = false


grails.plugin.springsecurity.interceptUrlMap = [
        '/': ['IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY'],
        '/api/**':    ['IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY'],
        '/login/**': ['IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY' ],
        '/logout/**': ['IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**/js/**':       ['IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**/css/**':      ['IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**/images/**':   ['IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY'],

    //        '/**': ['ROLE_USER']

    //        '/audit/**': ['ROLE_ADMIN'],
        '/user/**': ['ROLE_ADMIN'],
        '/**/home': ['ROLE_READ', 'ROLE_WRITE', 'ROLE_ADMIN'],
        '/**/list': ['ROLE_READ', 'ROLE_WRITE','ROLE_ADMIN'],
        '/**/show': ['ROLE_READ', 'ROLE_WRITE','ROLE_ADMIN'],
        '/**/edit': ['ROLE_WRITE', 'ROLE_ADMIN'],
        '/**/create': ['ROLE_WRITE', 'ROLE_ADMIN'],
        '/**/supportList': ['ROLE_WRITE', 'ROLE_ADMIN'],
        '/**':['ROLE_READ', 'ROLE_WRITE','ROLE_ADMIN']
]


// set per-environment serverURL stem for creating absolute links
environments {
    test {
        grails.serverURL = "http://dcm51.pvt.hawaii.edu:8080/its/${appName}"
    }
    development {


        grails.serverURL = "http://localhost:8080/its/${appName}"
        /*
        log4j {
        logger {
        grails.app.domain="info,stdout"
        }
        }
         */
    }
    production {
        grails.serverURL =  "http://www.hawaii.edu/its/${appName}"
    }

}

ldapServers {
    d1 {
        base = "dc=hawaii,dc=edu,ou=People"
        port = 389
        indexed = ["objectClass", "uid", "mail", "userPassword"]
    }
}
grails.plugin.springsecurity.ldap.context.server = 'ldap://ldap1.hawaii.edu:389'

//log4j configuration
/*
log4j = {
// Example of changing the log pattern for the default console
// appender:
//

//debug 'org.hibernate.SQL'
//trace 'org.hibernate.type'

warn 'grails.app'
info 'grails.app.controller'
//    debug 'grails.app.service.BarService'

//    debug 'org.codehaus.groovy.grails.plugins.springsecurity',
//            'grails.plugins.springsecurity',
//            'org.springframework.security'

appenders {
console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
root {
info()
}
//        file name:'file', file:'C:\\Users\\bkarsin\\Desktop\\mylog.log'
}
root {
error 'stdout'
}

info 'org.'


error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
'org.codehaus.groovy.grails.web.pages', //  GSP
'org.codehaus.groovy.grails.web.sitemesh', //  layouts
'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
'org.codehaus.groovy.grails.web.mapping', // URL mapping
'org.codehaus.groovy.grails.commons', // core / classloading
'org.codehaus.groovy.grails.plugins', // plugins
'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
'org.springframework',
'org.hibernate',
'net.sf.ehcache.hibernate'

warn   'org.mortbay.log'

}
 */
grails.resources.debug = true



grails.resources.modules = {
	
    boiler_plate {
        resource url: '/js/jquery-ui-1.8.15.custom.min.js'
        resource url: '/css/grape-theme/jquery-ui-1.8.15.custom.css'
        resource url: '/css/BreadCrumb.css'
        resource url: '/css/slide_menu/slide_menu.css'

        resource url :'css/admin_theme/ie.css'
        resource url :'css/admin_theme/layout.css'
        
        //table tools
        resource url:'/js/TableTools-2.0.1/media/js/ZeroClipboard.js'
        resource url:'/js/TableTools-2.0.1/media/js/TableTools.js'

        //menu
        resource url: '/css/slide_menu/slide_menu.css'
        resource url: '/js/jquery.easing.1.3.js'

    }

    asset {
        resource url:'/js/asset.js'
    }

    application {
        resource url:'/js/application.js'
    }

    select2 {
        resource url:'/css/select2.css'
        resource url: '/js/select2/select2.js'
    }

    //jsplumb is used to draw diagrams for relationships
    jsPlumb {
        resource url: '/js/jquery.jsPlumb-1.4.1-all.js'
    }

    //modal styling
    bootstrap{
        resource url:'/js/bootstrap/css/bootstrap-theme.css'
        resource url:'/js/bootstrap/css/bootstrap.css'
        resource url:'/js/bootstrap/js/bootstrap.js'
    }
    //centralized alert system
    message_alert{
        resource url: '/css/flash_messages/messages.css'
        resource url: '/css/flash_messages/messages.js'

        //notify jquery
        resource url: '/js/notify/jquery.notify.js'
        resource url: '/js/notify/notify.css'
    }

}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'edu.hawaii.its.dcmd.inf.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'edu.hawaii.its.dcmd.inf.UserRole'
grails.plugin.springsecurity.authority.className = 'edu.hawaii.its.dcmd.inf.Role'


////cookie session plugin config
//grails.plugin.cookiesession.enabled = true
//grails.plugin.cookiesession.encryptcookie = true
//grails.plugin.cookiesession.cryptoalgorithm = "Blowfish"
//grails.plugin.cookiesession.secret = "This is my secret."
//grails.plugin.cookiesession.cookiecount = 10
//grails.plugin.cookiesession.maxcookiesize = 2048  // 2kb
//grails.plugin.cookiesession.sessiontimeout = 3600 // one hour
//grails.plugin.cookiesession.cookiename = 'gsession'
//grails.plugin.cookiesession.condenseexceptions = false
//grails.plugin.cookiesession.serializer = 'kryo'
//grails.plugin.cookiesession.springsecuritycompatibility = true
// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line 
// GSP settings
grails {
views {
gsp {
encoding = 'UTF-8'
htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
codecs {
expression = 'html' // escapes values inside null
scriptlet = 'none' // escapes output from scriptlets in GSPs
taglib = 'none' // escapes output from taglibs
staticparts = 'none' // escapes output from static template parts
}
}
// escapes all not-encoded output at final stage of outputting
filteringCodecForContentType {
//'text/html' = 'html'
}
}
}
remove this line */
