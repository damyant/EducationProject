
<%@ page import="examinationproject.FeeDetails" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'feeDetails.label', default: 'FeeDetails')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-feeDetails" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-feeDetails" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list feeDetails">
			
				<g:if test="${feeDetailsInstance?.feeType}">
				<li class="fieldcontain">
					<span id="feeType-label" class="property-label"><g:message code="feeDetails.feeType.label" default="Fee Type" /></span>
					
						<span class="property-value" aria-labelledby="feeType-label"><g:link controller="feeType" action="show" id="${feeDetailsInstance?.feeType?.id}">${feeDetailsInstance?.feeType?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${feeDetailsInstance?.paymentMode}">
				<li class="fieldcontain">
					<span id="paymentMode-label" class="property-label"><g:message code="feeDetails.paymentMode.label" default="Payment Mode" /></span>
					
						<span class="property-value" aria-labelledby="paymentMode-label"><g:fieldValue bean="${feeDetailsInstance}" field="paymentMode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feeDetailsInstance?.paymentDate}">
				<li class="fieldcontain">
					<span id="paymentDate-label" class="property-label"><g:message code="feeDetails.paymentDate.label" default="Payment Date" /></span>
					
						<span class="property-value" aria-labelledby="paymentDate-label"><g:formatDate date="${feeDetailsInstance?.paymentDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${feeDetailsInstance?.draftNumber}">
				<li class="fieldcontain">
					<span id="draftNumber-label" class="property-label"><g:message code="feeDetails.draftNumber.label" default="Draft Number" /></span>
					
						<span class="property-value" aria-labelledby="draftNumber-label"><g:fieldValue bean="${feeDetailsInstance}" field="draftNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feeDetailsInstance?.draftDate}">
				<li class="fieldcontain">
					<span id="draftDate-label" class="property-label"><g:message code="feeDetails.draftDate.label" default="Draft Date" /></span>
					
						<span class="property-value" aria-labelledby="draftDate-label"><g:formatDate date="${feeDetailsInstance?.draftDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${feeDetailsInstance?.issuingBank}">
				<li class="fieldcontain">
					<span id="issuingBank-label" class="property-label"><g:message code="feeDetails.issuingBank.label" default="Issuing Bank" /></span>
					
						<span class="property-value" aria-labelledby="issuingBank-label"><g:fieldValue bean="${feeDetailsInstance}" field="issuingBank"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${feeDetailsInstance?.issuingBranch}">
				<li class="fieldcontain">
					<span id="issuingBranch-label" class="property-label"><g:message code="feeDetails.issuingBranch.label" default="Issuing Branch" /></span>
					
						<span class="property-value" aria-labelledby="issuingBranch-label"><g:fieldValue bean="${feeDetailsInstance}" field="issuingBranch"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:feeDetailsInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="editFeeDetails" resource="${feeDetailsInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="deleteFeeDetails" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
