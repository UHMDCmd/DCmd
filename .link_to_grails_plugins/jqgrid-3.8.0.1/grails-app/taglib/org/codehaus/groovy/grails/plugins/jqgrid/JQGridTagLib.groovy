package org.codehaus.groovy.grails.plugins.jqgrid

class JQGridTagLib {

    static namespace = "jqgrid"

    /**
     * Include CSS.
     */
    def cssResources = { attrs, body ->
        out << render(plugin: 'jqgrid', template:"/templates/cssResources")
    }

    /**
     * Include JavaScript.
     */
    def scriptResources = { attrs, body ->
        out << render(plugin:'jqgrid',template:"/templates/scriptResources")
    }

    /**
     * Include CSS and JavaScript resources in head.
     */
    def resources = { attrs, body ->
        out << render(plugin:'jqgrid',template:"/templates/resources")
    }


    def wrapper = { attrs, body ->
        out << render(plugin:'jqgrid',template:"/templates/gridWrapper", model:[attrs:attrs])
    }


    /**
     * Generates the required javascript and html for jqGrid
     *
     * ** Note: This is not compatible with conf < 3.8. All properties now use the same case, etc. as the
     *          actual JQGrid javascript library. Please refer to http://www.trirand.com/jqgridwiki/doku.php?id=wiki:jqgriddocs
     *          for details
     */
    def grid = { attrs, body ->
        def ignored = ['id',
                       'groupText']

        def arrayAttrs = ['colModel', 'colNames', 'rowList']

        def output = new StringBuilder()

        out << "jQuery(\"#${attrs.id}Grid\").jqGrid({"
        attrs.each {k, v ->
          if (!(k in ignored)) {

            // Array Attributes
            if (k in arrayAttrs) {
              // Can't seem to escape the '[]' characters so add them here. Could be user error
              output << k
              output << ': ['
              output << v
              output << ']'

            } else if (k == 'groupingView') {
              // Grouping view needs to spit everything out and add the 'groupText' if given
              output << k
              output << ': {'
              output << translate(v)

              // Since groupText uses both '[]' and '{}' as valid characters we need to handle it correctly
              // groupText can also hold any valid html, make sure to decode
              if (attrs.groupText) {
                output << ', groupText: [\''
                output << attrs.groupText.decodeHTML()
                output << '\']'
              }

              output << '}'

            } else if (k == 'subGridModel') {
              // Subgrid Models use '[]' so we need to replace the '{}' with '[]'
              output << k
              output << ': [{'
              output << translate(v)
              output << '}]'

            } else if (k == 'showPager') {
              // The Pager div for our grid
              output << "pager: '#${attrs.id}GridPager'"

            } else {

              // Standard Attributes
              output << k
              output << ': '
              output << v
              
            }
            // We will remove the last ',' when we are done with our builder
            output << ','
          }
        }
        out << output.substring(0, output.size() - 1)
        out << "});"

        out << body()
    }

    def navigation = {attrs, body ->
        out << render(plugin:'jqgrid', template:"/templates/navigation", model:[attrs:attrs])
    }

    def filterToolbar = {attrs, body ->
        out << render(plugin:'jqgrid', template:"/templates/filterToolBar", model:[attrs:attrs])
    }


    def resize = {attrs, body ->
        out << render(plugin:'jqgrid', template:"/templates/resize", model:[attrs:attrs])
    }

    def addButton = {attrs, body ->
        out << render(plugin:'jqgrid', template:"/templates/addButton", model:[attrs:attrs])
    }

    def editButton = {attrs, body ->
        out << render(plugin:'jqgrid', template:"/templates/editButton", model:[attrs:attrs])
    }

    def searchButton = {attrs, body ->
        out << render(plugin:'jqgrid', template:"/templates/searchButton", model:[attrs:attrs])
    }

    def deleteButton = {attrs, body ->
        out << render(plugin:'jqgrid', template:"/templates/deleteButton", model:[attrs:attrs])
    }

    def customButton = {attrs, body ->
        out << render(plugin:'jqgrid', template:"/templates/customButton", model:[attrs:attrs])
    }
	
	def refreshButton = {attrs, body ->
		out << render(plugin:'jqgrid', template:"/templates/refreshButton", model:[attrs:attrs])
	}

    private translate = {value ->
      value.replaceAll('\\{', '[').replaceAll('\\}', ']')
    }

}
