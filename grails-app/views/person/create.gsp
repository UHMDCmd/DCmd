

<%@ page import="edu.hawaii.its.dcmd.inf.Person" %>
<html>
<head>
    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label" /></title>
<jqDT:resources jqueryUi="true" type="js" />

</head>
    <body>

<g:form action="save" >
    <div id="container">
        <g:render template="../show_secondary" model="[pageType:'person', objectId:personId, action:'create']" />
        <g:render template="../breadcrumbs" model="[pageType:'person', action:'create']"/>
    </div>

    <div class="pageBodyNoMargin">
        <article class="module width_full">
            <div class="module_content">
                <h1>Create Person</h1>
                <g:if test="${flash.message}">
                    <div class="message">${flash.message}</div>
                </g:if>
                <g:hasErrors bean="${personInstance}">
                    <div class="errors">
                        <g:renderErrors bean="${personInstance}" as="list" />
                    </div>
                </g:hasErrors>
                <g:render template="../toolTip" />

                <g:render template="dialog" model="[action:'create']"/>
            <%--
                  <g:render template='hostSupporter' model="['hostsupporter':null,'i':'_clone','hidden':true]"/>
            --%>
        </article>                                                                                        l
    </div>
</g:form>
    </body>
</html>

