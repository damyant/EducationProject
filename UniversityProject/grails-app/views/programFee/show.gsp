

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'programFee.label', default: 'ProgramFee')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>

		<div id="show-programFee" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list programFee">
			
				<g:if test="${programFeeInstance?.programDetail}">
				<li class="fieldcontain">
					<span id="programDetail-label" class="property-label"><g:message code="programFee.programDetail.label" default="Programme Detail" /></span>
					
						<span class="property-value" aria-labelledby="programDetail-label"><g:link controller="programDetail" action="show" id="${programFeeInstance?.programDetail?.id}">${programFeeInstance?.programDetail?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${programFeeInstance?.feeAmountAtIDOL}">
				<li class="fieldcontain">
					<span id="feeAmountAtIDOL-label" class="property-label"><g:message code="programFee.feeAmountAtIDOL.label" default="Fee Amount At IDOL" /></span>
					
						<span class="property-value" aria-labelledby="feeAmountAtIDOL-label"><g:fieldValue bean="${programFeeInstance}" field="feeAmountAtIDOL"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${programFeeInstance?.feeAmountAtSC}">
				<li class="fieldcontain">
					<span id="feeAmountAtSC-label" class="property-label"><g:message code="programFee.feeAmountAtSC.label" default="Fee Amount At SC" /></span>
					
						<span class="property-value" aria-labelledby="feeAmountAtSC-label"><g:fieldValue bean="${programFeeInstance}" field="feeAmountAtSC"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${programFeeInstance?.lateFeeAmount}">
				<li class="fieldcontain">
					<span id="lateFeeAmount-label" class="property-label"><g:message code="programFee.lateFeeAmount.label" default="Late Fee Amount" /></span>
					
						<span class="property-value" aria-labelledby="lateFeeAmount-label"><g:fieldValue bean="${programFeeInstance}" field="lateFeeAmount"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:programFeeInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${programFeeInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
