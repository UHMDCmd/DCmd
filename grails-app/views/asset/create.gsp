<%@ page import="edu.hawaii.its.dcmd.inf.Asset"%>
<html>

<head>
    <g:set var="assetType" value="${fieldValue(bean:assetInstance, field: 'assetType')}" />

    <meta content="main" name="layout" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />

</head>
<body>
<g:form action="save">
	<g:javascript>
			$(function() {
				var ownerTypes = [
					"Leased",
					"Perpetual",
				];
				$( "#ownerTypes" ).autocomplete({
					source: ownerTypes
				});
			});
		</g:javascript>
  		<g:render template="../show_secondary" model="[pageType:'asset', objectId:assetId, action:'create']" />
        <g:render template="../breadcrumbs" model="[pageType:'asset', action:'create']"/>

    <div class="pageBodyNoMargin">
			<article class="module width_full">
				<div class="module_content">
             <h1>
                        Create Asset
		    </h1>
                    <dcmd:requiredInputFieldsReminder />
					<s:info />
					<g:hasErrors bean="${assetInstance}">
						<s:errorDiv>
							<s:renderErrors bean="${assetInstance}" as="list" />
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
