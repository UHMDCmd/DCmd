<html>
<head>
    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label"/></title>
    <jqDT:resources jqueryUi="true" type="js" />
</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'denied', objectId:0, action:'show']" />

</div>

<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">


            <center><h3>Permission Denied</h3>
            <br><br>
            <g:if test="${params.username == 'anonymousUser'}">
                <h3>User not permitted access to DCmd.  Contact karsin@hawaii.edu to gain access.</h3>
            </g:if>
            <g:if test="${params.username != 'anonymousUser'}">
                <h3>User <b>${params.username}</b> not permitted to view this page.  Contact karsin@hawaii.edu to gain access.</h3>
            </g:if>

            </center>
        </div> <!-- end pagebody -->


    </article>
</div>

</body>
</html>

