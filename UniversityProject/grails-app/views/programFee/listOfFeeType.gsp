
<%@ page import="examinationproject.ProgramFee" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'programFee.label', default: 'ProgramFee')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
    <div id="main">
        <div id="list-programFee" class="content scaffold-list" role="main">
            <h3><g:message code="default.list.program.fee"/></h3>
            <g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
            <table class="university-table-1-5">
                <thead>
					<tr>
					
						<th><g:message code="programFee.programDetail.label" default="Program Detail" /></th>
					
						<g:sortableColumn property="feeAmountAtIDOL" title="${message(code: 'programFee.feeAmountAtIDOL.label', default: 'Fee Amount At IDOL')}" />
					
						<g:sortableColumn property="feeAmountAtSC" title="${message(code: 'programFee.feeAmountAtSC.label', default: 'Fee Amount At SC')}" />
					
						<g:sortableColumn property="lateFeeAmount" title="${message(code: 'programFee.lateFeeAmount.label', default: 'Late Fee Amount')}" />

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

                        <td>
                            <g:link action="deleteFeeType" id="${programFeeInstance.id}"
                                    class="university-text-decoration-none"><button
                                    class="university-button">Delete</button></g:link>
                            <g:link action="editFeeType" id="${programFeeInstance.id}"
                                    class="university-text-decoration-none"><button
                                    class="university-button">Edit</button></g:link>
                        </td>

                    </tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${programFeeInstanceCount ?: 0}" />
			</div>
		</div>
    </div>
    </body>
</html>
