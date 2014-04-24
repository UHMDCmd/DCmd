
<%@ page import="edu.hawaii.its.dcmd.inf.UnitType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'unitType.label', default: 'UnitType')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <%--<g:sortableColumn property="id" title="${message(code: 'unitType.id.label', default: 'Id')}" />--%>
                        
                            <g:sortableColumn property="unit" title="${message(code: 'unitType.unit.label', default: 'Unit')}" />
                        
                            <g:sortableColumn property="unitDescription" title="${message(code: 'unitType.unitDescription.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${unitTypeInstanceList}" status="i" var="unitTypeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <%--<td><g:link action="show" id="${unitTypeInstance.id}">${fieldValue(bean: unitTypeInstance, field: "id")}</g:link></td>--%>
                        
                            <td><g:link action="show" id="${unitTypeInstance.id}">${fieldValue(bean: unitTypeInstance, field: "unit")}</g:link></td>
                        
                            <td><g:link action="show" id="${unitTypeInstance.id}">${fieldValue(bean: unitTypeInstance, field: "unitDescription")}</g:link></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${unitTypeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
