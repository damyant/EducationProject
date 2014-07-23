<%@ page import="examinationproject.AdmissionFee" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'programFee.label', default: 'ProgramFee')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <div id="list-programFee" class="content scaffold-list" role="main">
            <h3><g:message code="default.list.program.fee"/></h3>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table class="university-size-full-1-1 inner" style="margin: auto;width: 100%;">
                <thead>
                <tr>

                    <th><g:message code="programFee.programDetail.label" default="Programme Detail"/></th>

                    <th><g:message code="programFee.session.label" default="Program Fee Session"/></th>
                     <th>${resultMap.examFeeName}</th>
                     <th>${resultMap.certFeeName}</th>
                    <th></th>

                </tr>
                </thead>
                <tbody>
                <g:if test="${programFeeInstanceList}">
                    <g:each in="${0..resultMap.count}" status="i" var="index">
                        <tr>

                            <td>${resultMap.pName[index]}</td>
                            <td>${resultMap.session[index]}</td>
                            <td>${resultMap.examFee[index]}</td>
                            <td>${resultMap.certFee[index]}</td>

                            <td>
                                <g:link action="deleteFeeType" id="${resultMap.feeId[index]}"
                                        class="university-text-decoration-none"><button
                                        class="university-button">Delete</button></g:link>
                                <g:link action="editFeeType" id="${resultMap.feeId[index]}"
                                        class="university-text-decoration-none"><button
                                        class="university-button">Edit Miscellaneous</button></g:link>
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
                <g:paginate controller="programFee" action="listOfFeeType" total="${admissionFeeTotal}"/>
                %{--</g:else>--}%
            </div>
        </div>

    </fieldset>
</div>
</body>
</html>
