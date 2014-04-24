

<%@ page import="edu.hawaii.its.dcmd.inf.Contractmin" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contractmin.label', default: 'Contractmin')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <s:info/>
            <g:hasErrors bean="${contractminInstance}">
            <s:errorDiv>
                <s:renderErrors bean="${contractminInstance}" as="list" />
            </s:errorDiv>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name"><dcmd:requiredInputFieldFlag/><label
                                    for="uhContractNo"><g:message
                                            code="contract.uhContractNo.label" default="Uh Contract No" /> </label>
                                </td>
                                <td valign="top"
                                    class="value ${hasErrors(bean: contractInstance, field: 'uhContractNo', 'errors')}">
                                    <g:textField name="uhContractNo"
                                        value="${contractInstance?.uhContractNo}" /></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
