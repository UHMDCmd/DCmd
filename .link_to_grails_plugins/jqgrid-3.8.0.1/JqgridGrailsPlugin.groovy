class JqgridGrailsPlugin {
    // the plugin version (Relates to version of jqgrid being used)
    def version = "3.8.0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.5 > *"
    // the other plugins this plugin depends on
    def dependsOn = ['jqueryUi': "1.8.2.4 > *"]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
			"test",
			"grails-app/i18n/**",
			"grails-app/conf/**",
            "grails-app/domain/**",
			"grails-app/controllers/**",
			"grails-app/services/**",
			"grails-app/views/index.gsp",
			"grails-app/views/contact/**",
			"grails-app/views/layouts/**",
			"web-app/WEB-INF",
			"web-app/images/**",
			"web-app/js/application.js",
			"web-app/js/prototype/**",
			"web-app/css/main.css",
            "**/.svnignore",
			"**/.gitignore"
    ]

    // TODO Fill in these fields
    def author = "Aaron Oathout"
    def authorEmail = "aoathout@gmail.com"
    def title = "JQGrid Plugin"
    def description = '''\\
Provides easy integration with the jqgrid jquery library.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/jqgrid"

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
