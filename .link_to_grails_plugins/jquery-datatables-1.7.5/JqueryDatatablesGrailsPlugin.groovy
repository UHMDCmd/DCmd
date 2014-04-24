class JqueryDatatablesGrailsPlugin {
    // the plugin version
    def version = "1.7.5"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2.2 > *"
    // the other plugins this plugin depends on
    def dependsOn = [jquery:'1.3.2 > *']
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Lim Chee Kin"
    def authorEmail = "limcheekin@vobject.com"
    def title = "JQuery DataTables Plugin"
    def description = '''\
This plugin simply supplies jQuery DataTables plugin resources, and depends on the jQuery plugin to include the core jquery libraries.
Use this plugin in your own apps and plugins to avoid resource duplication and conflicts.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/jquery-datatables"

}
