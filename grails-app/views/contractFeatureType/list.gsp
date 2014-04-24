
<%@ page import="edu.hawaii.its.dcmd.inf.ContractFeatureType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contractFeatureType.label', default: 'ContractFeatureType')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'contractFeatureType.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="type" title="${message(code: 'contractFeatureType.type.label', default: 'Type')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'contractFeatureType.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${contractFeatureTypeInstanceList}" status="i" var="contractFeatureTypeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${contractFeatureTypeInstance.id}">${fieldValue(bean: contractFeatureTypeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: contractFeatureTypeInstance, field: "type")}</td>
                        
                            <td>${fieldValue(bean: contractFeatureTypeInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${contractFeatureTypeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
