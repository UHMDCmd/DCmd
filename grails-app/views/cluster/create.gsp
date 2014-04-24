<%@ page import="edu.hawaii.its.dcmd.inf.Cluster"%>
<html>

<head>

    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

</head>
<body>
<g:form action="save">

  		<g:render template="../show_secondary" model="[pageType:'cluster', objectId:clusterId, action:'create']" />
    <g:render template="../breadcrumbs" model="[pageType:'cluster', action:'create']"/>

    <div class="pageBodyNoMargin">
			<article class="module width_full">
				<div class="module_content">
             <h1>
                        Create Cluster
		    </h1>
                    <dcmd:requiredInputFieldsReminder />
					<s:info />
					<g:hasErrors bean="${clusterInstance}">
						<s:errorDiv>
							<s:renderErrors bean="${clusterInstance}" as="list" />
						</s:errorDiv>
					</g:hasErrors>
                    <g:render template="../toolTip" />
                    <g:render template="dialog" model="[action:'create']"/>
					</div>
			</article>
			
		</div>

	</g:form>
</body>
</html>
