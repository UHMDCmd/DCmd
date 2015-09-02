grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

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
          mavenLocal()
          mavenCentral()
          mavenRepo "https://repo.grails.org/grails/plugins"
          mavenRepo "http://snapshots.repository.codehaus.org"
          mavenRepo "http://repository.codehaus.org"
          mavenRepo "http://download.java.net/maven/2/"
          mavenRepo "http://repository.jboss.com/maven2/"
          mavenRepo "http://repo.spring.io/milestone/"

        //uncomment to install ssh plugin dependency
         mavenRepo("http://repo1.maven.org/maven2/")
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.13'
         runtime "hsqldb:hsqldb:1.8.0.10"
        compile 'com.vmware:vijava:5.1'

    }


    plugins {

        build ":tomcat:7.0.52.1"
        runtime ":hibernate:3.6.10.14"

        runtime ':jquery:1.7'
        runtime:':jquery-ui:1.10.4'
        runtime ':jqgrid:3.8.0.1'

        runtime ':breadcrumbs:0.5.2'
        runtime ':export:1.5'
        compile ':audit-logging:1.0.0'
        runtime ':google-visualization:0.6.2'
        runtime ':cache-headers:1.1.5'
        runtime ':calendar:1.2.1'
        runtime ':codenarc:0.12'

        // compile ':tooltip:0.8'
        runtime ':jquery-datatables:1.7.5'
        runtime ':jquery-ui:1.8.11'
        runtime ':mysql-connectorj:5.1.22.1'
        runtime ':resources:1.2.8'
        runtime ":cookie-session:2.0.14"

        compile ':spring-security-core:2.0-RC2'
        compile ':spring-security-cas:2.0-RC1'
        compile ':spring-security-ldap:2.0-RC2'

        compile ':scaffolding:1.0.0'
        compile ':remote-ssh:0.2'

        compile ":quartz:1.0.2"

        compile ":backbonejs:1.0.0"

        runtime ":directory-service:0.10.1"

        // 2.1.1 config

//        //2.3.8 config
//        build ':tomcat:7.0.52.1'
//        runtime ':resources:1.2.7'
//        compile ':scaffolding:2.0.3'
//        runtime ':database-migration:1.4.0'
//        compile ":eclipse-scripts:1.0.7"
//
    }
}
