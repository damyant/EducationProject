
<%@ page import="examinationproject.FeeDetails" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'feeDetails.label', default: 'FeeDetails')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-feeDetails" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-feeDetails" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="feeDetails.feeType.label" default="Fee Type" /></th>
					
						<g:sortableColumn property="paymentMode" title="${message(code: 'feeDetails.paymentMode.label', default: 'Payment Mode')}" />
					
						<g:sortableColumn property="paymentDate" title="${message(code: 'feeDetails.paymentDate.label', default: 'Payment Date')}" />
					
						<g:sortableColumn property="draftNumber" title="${message(code: 'feeDetails.draftNumber.label', default: 'Draft Number')}" />
					
						<g:sortableColumn property="draftDate" title="${message(code: 'feeDetails.draftDate.label', default: 'Draft Date')}" />
					
						<g:sortableColumn property="issuingBank" title="${message(code: 'feeDetails.issuingBank.label', default: 'Issuing Bank')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${feeDetailsInstanceList}" status="i" var="feeDetailsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${feeDetailsInstance.id}">${fieldValue(bean: feeDetailsInstance, field: "feeType")}</g:link></td>
					
						<td>${fieldValue(bean: feeDetailsInstance, field: "paymentMode")}</td>
					
						<td><g:formatDate date="${feeDetailsInstance.paymentDate}" /></td>
					
						<td>${fieldValue(bean: feeDetailsInstance, field: "draftNumber")}</td>
					
						<td><g:formatDate date="${feeDetailsInstance.draftDate}" /></td>
					
						<td>${fieldValue(bean: feeDetailsInstance, field: "issuingBank")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${feeDetailsInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
