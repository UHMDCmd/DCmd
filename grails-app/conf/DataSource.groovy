dataSource {
    pooled = true

    // Un-comment for Test/Production
   // driverClassName = "com.mysql.jdbc.Driver"
   // username = "dcmd"
   // password = "***REMOVED***"

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

// environment specific settings
environments {
    development {
        dataSource {
			
			dbCreate = "create" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:mem:devDB"
           // logSql = true
        }

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
