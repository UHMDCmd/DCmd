<%@ page import="edu.hawaii.its.dcmd.inf.Host"%>
<html>
<head>

    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

</head>
<body>

<g:form method="post" controller="host">
	<%--
		<g:render template="../nav" model="[entityName: entityName]" />
		--%>
	<div id="container">

    <g:hiddenField name="id" value="${hostInstance?.id}" />
    <g:hiddenField name="version" value="${hostInstance?.version}" />

    <g:render template="../show_secondary" model="[pageType:'host', objectId:hostId, action:'edit']" />
    <g:render template="../breadcrumbs" model="[pageType:'host', action:'edit']"/>


    </div>


	<div class="pageBodyNoMargin">
		<article class="module width_full">
			<div class="module_content">

				<g:render template="../content_title"
					model="[entityName: 'Host', code:'default.edit.label']" />
				<dcmd:requiredInputFieldsReminder />
				<s:info />
				<g:hasErrors bean="${hostInstance}">
					<s:errorDiv>
						<s:renderErrors bean="${hostInstance}" as="list" />
					</s:errorDiv>
				</g:hasErrors>



                <g:render template="../toolTip" />
					<g:render template="dialog" />


			</div>
		</article>
	</div>
	
	</g:form>

    <div class="pageBodyNoMargin" style="margin-top:-125px">
        <article class="module width_full">
            <div class="module_content">
                <g:render template="tabs" model="[actionName:'edit']"/>
            </div>
        </article>
    </div>

</body>
</html>
