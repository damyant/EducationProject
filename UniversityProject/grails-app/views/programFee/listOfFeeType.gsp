
<%@ page import="examinationproject.AdmissionFee" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'programFee.label', default: 'ProgramFee')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
    <div id="main">
        <fieldset class="form">
            <div id="list-programFee" class="content scaffold-list" role="main">
                <h3><g:message code="default.list.program.fee"/></h3>
            <g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
                <table class="university-table-1-5 inner" style="margin: auto;width: 100%;">
                    <thead>
                    <tr>
					
						<th><g:message code="programFee.programDetail.label" default="Program Detail" /></th>

                        <g:sortableColumn property="feeAmountAtIDOL"
                                          title="${message(code: 'programFee.feeAmountAtIDOL.label', default: 'Fee Amount At IDOL')}"/>

                        <g:sortableColumn property="feeAmountAtSC"
                                          title="${message(code: 'programFee.feeAmountAtSC.label', default: 'Fee Amount At SC')}"/>

                        <g:sortableColumn property="lateFeeAmount"
                                          title="${message(code: 'programFee.lateFeeAmount.label', default: 'Late Fee Amount')}"/>

                        <th></th>
					
					</tr>
				</thead>
				<tbody>
                <g:if test="${programFeeInstanceList}">
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
                </g:if>
                <g:else>
                    <tr>
                        <td colspan="5" style="text-align: center;">
                            <label><h5>No Existing Fees Type.</h5></label>
                        </td>
                    </tr>
                </g:else>
				</tbody>
			</table>
                <div class="paginateButtons">
                    %{--<g:if test="${params.type == 'update'}">--}%
                        %{--<g:paginate controller="programFee" action="listOfFeeType"  total="${programFeeInstanceList}" params="['type':'update']"/>--}%
                    %{--</g:if>--}%
                    %{--<g:else>--}%
                        <g:paginate controller="programFee" action="listOfFeeType"  total="${admissionFeeTotal}" />
                    %{--</g:else>--}%
                </div>
		</div>

        </fieldset>
    </div>
    </body>
</html>
