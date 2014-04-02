
<%@ page import="examinationproject.ProgramFee" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'programFee.label', default: 'ProgramFee')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>

		<div id="list-programFee" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="programFee.programDetail.label" default="Program Detail" /></th>
					
						<g:sortableColumn property="feeAmountAtIDOL" title="${message(code: 'programFee.feeAmountAtIDOL.label', default: 'Fee Amount At IDOL')}" />
					
						<g:sortableColumn property="feeAmountAtSC" title="${message(code: 'programFee.feeAmountAtSC.label', default: 'Fee Amount At SC')}" />
					
						<g:sortableColumn property="lateFeeAmount" title="${message(code: 'programFee.lateFeeAmount.label', default: 'Late Fee Amount')}" />

                        <th></th>
                        <th></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${programFeeInstanceList}" status="i" var="programFeeInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td>${fieldValue(bean: programFeeInstance, field: "programDetail.courseName")}</td>
					
						<td>${fieldValue(bean: programFeeInstance, field: "feeAmountAtIDOL")}</td>
					
						<td>${fieldValue(bean: programFeeInstance, field: "feeAmountAtSC")}</td>
					
						<td>${fieldValue(bean: programFeeInstance, field: "lateFeeAmount")}</td>

                        <td><g:link action="deleteFeeType" id="${programFeeInstance.id}">Delete</g:link></td>
                        <td><g:link action="editFeeType" id="${programFeeInstance.id}">Edit</g:link></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${programFeeInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
