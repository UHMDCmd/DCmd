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

grails.stringchararrayaccessor.disabled=true


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

grails.plugins.springsecurity.cas.loginUri = '/login'

grails.plugins.springsecurity.ldap.context.managerDn = '[dc=hawaii,dc=edu]'
grails.plugins.springsecurity.ldap.context.managerPassword = '[]'
grails.plugins.springsecurity.ldap.context.server = 'ldap://ldap1.its.hawaii.edu:389/'
grails.plugins.springsecurity.ldap.authorities.groupSearchBase =
    '[dc=hawaii,dc=edu,ou=People]'
grails.plugins.springsecurity.ldap.search.base = '[dc=hawaii,dc=edu,ou=People]'

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
grails.plugins.springsecurity.cas.serviceUrl = 'http://localhost:8080/its/dcmd/j_spring_cas_security_check'
grails.plugins.springsecurity.cas.proxyCallbackUrl = 'http://localhost:8080/its/dcmd/secure/receptor'
grails.plugins.springsecurity.logout.afterLogoutUrl =
    'https://login.its.hawaii.edu/cas/logout?url=http://localhost:8080/its/dcmd/'

/***************************************************************************************
 * Un-comment this for Test
 ***************************************************************************************/
//grails.plugins.springsecurity.cas.serviceUrl = 'http:///dcm51.pvt.hawaii.edu:8080/its/dcmd/j_spring_cas_security_check'
//grails.plugins.springsecurity.cas.proxyCallbackUrl = 'http:///dcm51.pvt.hawaii.edu:8080/its/dcmd/secure/receptor'
//grails.plugins.springsecurity.logout.afterLogoutUrl =
//    'https://login.its.hawaii.edu/cas/logout?url=http:///dcm51.pvt.hawaii.edu:8080/its/dcmd/'


grails.plugins.springsecurity.successHandler.defaultTargetUrl = '/user/home'
grails.plugins.springsecurity.cas.serverUrlPrefix = 'https://login.its.hawaii.edu/cas'

grails.plugins.springsecurity.cas.proxyReceptorUrl = '/secure/receptor'

grails.plugins.springsecurity.useSecurityEventListener = true

grails.plugins.springsecurity.securityConfigType = "InterceptUrlMap"

grails.plugins.springsecurity.rejectIfNoRule = false

grails.plugins.springsecurity.interceptUrlMap = [
//        '/**':    ['IS_AUTHENTICATED_REMEMBERED']
        '/login/**': ['IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY' ],
        '/logout/**': ['IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY'],

//        '/**': ['ROLE_USER']

//        '/audit/**': ['ROLE_ADMIN'],
        '/user/**': ['ROLE_ADMIN'],
        '/**/home': ['ROLE_READ', 'ROLE_WRITE', 'ROLE_ADMIN'],
        '/**/list': ['ROLE_READ', 'ROLE_WRITE','ROLE_ADMIN'],
        '/**/show': ['ROLE_READ', 'ROLE_WRITE','ROLE_ADMIN'],
        '/**/edit': ['ROLE_WRITE', 'ROLE_ADMIN'],
        '/**/create': ['ROLE_WRITE', 'ROLE_ADMIN'],
        '/**/supportList': ['ROLE_WRITE', 'ROLE_ADMIN']


]

// set per-environment serverURL stem for creating absolute links
environments {
    test {
        grails.serverURL = "http://dcm51.pvt.hawaii.edu:8080/its/${appName}"
    }
    development {


       grails.serverURL = "http://localhost:8080/its/${appName}"
        log4j {
            logger {
                grails.app.domain="info,stdout"
            }
        }
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
grails.plugins.springsecurity.ldap.context.server = 'ldap://ldap1.hawaii.edu:389'

//log4j configuration
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


grails.resources.modules = {
	
		application_theme {
//			dependsOn 'jquery'
            resource url :'/css/BreadCrumb.css'
			resource url :'css/admin_theme/ie.css'
			resource url :'css/admin_theme/layout.css'
		}

        tabletools {
            resource url:'/js/TableTools-2.0.1/media/js/ZeroClipboard.js'
            resource url:'/js/TableTools-2.0.1/media/js/TableTools.js'
        }

        asset {
            resource url:'/js/asset.js'
        }

        application {
            resource url:'/js/application.js'
        }
		
		ui_tables{
            dependsOn: 'jquery'
			dependsOn: 'jqueryUi'
			resource url: '/js/jquery.dataTables.js'
			
		}
		ui {
			resource url: '/css/grape-theme/jquery-ui-1.8.15.custom.css'
			resource url: '/js/jquery-ui-1.8.15.custom.min.js'
            resource url: '/css/BreadCrumb.css'
//			resource url: '/js/jquery.dataTables.js'
        //          resource url: '/js/tabs.js'
    }

    menu {
        resource url: '/css/slide_menu/slide_menu.css'
        resource url: '/js/jquery.easing.1.3.js'
    }
    footer{
//			resource url: '/css/slide_menu/footer_bar.css'
//			resource url: '/js/floating_footer.js'
//			resource url: '/css/slide_menu/images/house.png'
//			resource url: '/css/slide_menu/images/database_table.png'
//			resource url: '/css/slide_menu/images/database_add.png'
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
grails.plugins.springsecurity.userLookup.userDomainClassName = 'edu.hawaii.its.dcmd.inf.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'edu.hawaii.its.dcmd.inf.UserRole'
grails.plugins.springsecurity.authority.className = 'edu.hawaii.its.dcmd.inf.Role'


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
