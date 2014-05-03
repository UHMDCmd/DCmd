grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

      // runtime 'mysql:mysql-connector-java:5.1.13'
        runtime "hsqldb:hsqldb:1.8.0.10"



    }


    plugins {

        runtime ':jquery:1.7'

        runtime:':jquery-ui:1.8.11'
        runtime ':jqgrid:3.8.0.1'


        runtime ':breadcrumbs:0.5.2'
        runtime ':export:1.5'
        runtime ':audit-logging:0.5.4'
        runtime ':google-visualization:0.6.2'
        runtime ':cache-headers:1.1.5'
        runtime ':calendar:1.2.1'
        runtime ':codenarc:0.12'
        runtime ':hibernate:2.1.1'
      //  runtime ':jquery:1.6.1.1'
        runtime ':jquery-datatables:1.7.5'
        runtime ':jquery-ui:1.8.11'
        runtime ':mysql-connectorj:5.1.22.1'
        runtime ':spring-security-cas:1.0.5'
        runtime ':spring-security-core:1.2.7.3'

        runtime ':spring-security-ldap:1.0.6'

       // 2.1.1 config
        runtime ':tomcat:2.1.1'
        runtime ':hibernate:2.1.1'
       runtime ':resources:1.1.6'
        runtime ':jquery:1.6.1.1'

//        //2.3.8 config
//        build ':tomcat:7.0.52.1'
//        runtime ':resources:1.2.7'
//        compile ':scaffolding:2.0.3'
//        runtime ':database-migration:1.4.0'
//        compile ":eclipse-scripts:1.0.7"
//



    }
}
