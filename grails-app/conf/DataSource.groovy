dataSource {
    pooled = true

    // Un-comment for Test/Production
    //driverClassName = "com.mysql.jdbc.Driver"
    //username = "dcmd"
    //password = "***REMOVED***"

    // Un-comment for Local Development
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""

}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}

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

// environment specific settings
environments {
    development {
        dataSource {
			
			dbCreate = "create" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:mem:devDB"
           // logSql = true
        }

//        dataSource_dcimlookup {
//            dialect = org.hibernate.dialect.MySQLInnoDBDialect
//            driverClassName = 'com.mysql.jdbc.Driver'
//            username = 'keller'
//            password = 'keller'
//           // url = 'jdbc:mysql://localhost/lookup'
//            url = "jdbc:mysql://dcim.its.hawaii.edu/"
//            dbCreate = 'update'
//        }

          /**
           *   
           *dbCreate = "create" // one of 'create', 'create-drop','update'
			driverClassName = "com.mysql.jdbc.Driver"
			username = "grails"
			password = "server"
			url = "jdbc:mysql://localhost:3306/dcmd-dev?autoreconnect=true"
           */

    }
    test {
        dataSource {
            pooled=true
            dbCreate = "update"
            url = "jdbc:mysql://mdb74.pvt.hawaii.edu:3306/dcmd?autoReconnect=true"
            username = "dcmd"
            password = "***REMOVED***"
//            logSql = true

            properties {
                maxActive = 50
                maxIdle = 25
                minIdle = 1
                initialSize = 1
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                numTestsPerEvictionRun = 3
                maxWait = 10000

                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = true

                validationQuery = "select now()"
            }

        }
    }
    production {
        dataSource {
            pooled=true
            dbCreate = "update"
            url = "jdbc:mysql://mdb40.pvt.hawaii.edu:3306/dcmd?autoReconnect=true"
            username = "dcmd"
            password = "***REMOVED***"

            properties {
                maxActive = 50
                maxIdle = 25
                minIdle = 1
                initialSize = 1
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                numTestsPerEvictionRun = 3
                maxWait = 10000

                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = true

                validationQuery = "select now()"
            }

        }
    }
}
