quartz {
    jdbcStore = false
    autoStartup = true

}

environments {
    test {
        quartz {
            autoStartup = true
        }
    }
    development {
        quartz {
            autoStartup = true
        }
    }
}