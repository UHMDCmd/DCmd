
<%@ page import="edu.hawaii.its.dcmd.inf.Person" %>
<html>
    <head>

        <meta content="main" name="layout" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Staff')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <jqDT:resources jqueryUi="true" type="js" />

      %{--pop up for row select--}%
        <style>
        .toggler { width: 500px; height: 200px; position: absolute}
        #button { padding: .5em 1em; text-decoration: none; }
        #effect { width: 240px; height: 135px; padding: 0.4em; position: absolute; float: left }
        #effect h3 { margin: 0; padding: 0.4em; text-align: center; }
        </style>



    <script>
        //call ldap update function
            ${remoteFunction(
                controller: 'Person',
                action:'updatePersonsWithLDAP'
        )}

            $(function() {
// run the currently selected effect
                $( "#effect" ).draggable();

                function runEffect() {
// run the effect
                    $( "#effect" ).show( "fold", 1000, callback);
                };
//callback function to bring a hidden box back
                function callback() {
                    setTimeout(function() {
                        $( "#effect:visible" ).removeAttr( "style" ).fadeOut();
                    }, 10000 );
                };
// set effect from select menu value
            //    $( "#button" ).click(function() {
                    $( "#button" ).click(function() {
                    runEffect();
                    return false;
                });
                $( "#effect" ).hide();
            });

            </script>


        <g:applyLayout name="breadcrumb_bar">
            <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'person']"/>
        </g:applyLayout>

    </head>
    <body>

    <div id="container" >
          <g:render template="../show_secondary" model="[pageType:'person', objectId:0, action:'list']" />
          <g:render template="../breadcrumbs" model="[pageType:'person', action:'list']"/>

    </div>

    <div class="pageBody"  id="outerElement">




    %{--<g:form action="updatePersonsWithLDAP">--}%
            %{--<g:submitButton name="refreshList" value="Refresh List"></g:submitButton>--}%
        %{--</g:form>--}%
        <g:render template="../toolTip" />

        <g:render template="listGrid" />

            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>

        </div>

    </body>
</html>
