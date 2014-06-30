<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 6/27/14
  Time: 3:33 PM
--%>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <title></title>
    <script type="text/javascript">
    $('#createAdmissionFee').ready(function () {
    if(${params.id}){
        $('#session').val('${sessionValue}')
        loadProgramTerm()
        %{--alert(${admissionFeeInst?.term})--}%
        %{--$('#semesterList').val('${admissionFeeInst?.term}')--}%
    }
    })
    </script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <div id="create-programFee" class="content scaffold-create" role="main">

            <g:if test="${params.id}">
                <h3><g:message code="default.edit.Admission.fee"/></h3>
            </g:if><g:else>
            <h3><g:message code="default.create.Admission.Fee"/></h3>
            </g:else>

            <g:if test="${flash.message}">
                <div class="message university-status-message" role="status">${flash.message}</div>
            </g:if>
            <div id="statusMessage" style="visibility: hidden" class="university-status-message"></div>
            <g:hasErrors bean="${programFeeInstance}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${programFeeInstance}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
        <g:form method='post' controller="programFee" action="saveAdmissionFee" id="createAdmissionFee" name="createAdmissionFee">
            <form id="createNewFee" name="createNewFee">
                <div style="margin-left: 5px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

                <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programDetail', 'error')} university-size-1-1">
                    <div class="university-size-1-3"><label for="programDetail">
                        <g:message code="programFee.programDetail.label" default="Program Name"/><span
                                class="university-obligatory">*</span>
                    </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:select id="programDetail" name="programDetailId"
                                  from="${programDetailList}" optionKey="id" value="${programInst?.programDetailId?.id}"
                                  optionValue="courseName" class="many-to-one university-size-1-2"
                                  noSelection="['': 'Select Program']" onclick="loadProgramTerm()" />

                    </div>
                </div>


                <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programSession', 'error')} university-size-1-1">
                    <div class="university-size-1-3"><label for="programSession">
                        <g:message code="programFee.programSession.label" default="Program Session"/><span
                                class="university-obligatory">*</span>
                    </label>
                    </div>
                    <div class="university-size-2-3">
                        <select id="session" name="programSessionId" class="university-size-1-2">
                            <option value="">Select Session</option>
                            <g:each in="${programSessions}" var="session">
                                <option value="${session.sessionOfProgram}">${session.sessionOfProgram}</option>
                            </g:each>
                        </select>
                    </div>
                </div>
                 <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'semesterList', 'error')} university-size-1-1">
                    <div class="university-size-1-3"><label for="semesterList">
                        <g:message code="programFee.term.label" default="Term"/><span
                                class="university-obligatory">*</span>
                    </label>
                    </div>
                    <div class="university-size-2-3">
                        <g:if test="${params.id}">
                            %{--<g:select name="semesterList" class="university-size-1-2" onchange="loadAdmissionFeeDetails()" id="semesterList"--}%
                                      %{--from="${allTerm}" optionKey="semesterNo" value="${termList}"--}%
                                      %{--optionValue="semesterNo" noSelection="['': 'Select term']" />--}%
                            <input type="text" class="university-size-1-2" value="${termList}" onchange="loadAdmissionFeeDetails()" readonly/><label id="termError"></label>

                        </g:if>
                        <g:else>
                        <select name="semesterList" class="university-size-1-2" onchange="loadAdmissionFeeDetails()" id="semesterList" >
                            <option value="">Select Term</option>
                        </select>
                        </g:else>
                    </div>
                </div>
                <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'feeAmountAtIDOL', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="feeAmountAtIDOL">
                            <g:message code="programFee.feeAmountAtIDOL.label" default="Fee Amount At IDOL"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>
                    <div class="university-size-2-3">
                        <g:textField name="feeAmountAtIDOL" class="university-size-1-2" type="number"
                                     value="${admissionFeeInst?.feeAmountAtIDOL}"
                                     onkeypress="return isNumber(event)"/>
                    </div>
                </div>


                <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'feeAmountAtSC', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="feeAmountAtSC">
                            <g:message code="programFee.feeAmountAtSC.label" default="Fee Amount At SC"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>


                    <div class="university-size-2-3">
                        <g:textField name="feeAmountAtSC"
                                     class="university-size-1-2"
                                     type="number"
                                     value="${admissionFeeInst?.feeAmountAtSC}"
                                     onkeypress="return isNumber(event)"/>
                    </div>
                </div>


                <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'lateFeeAmount', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="lateFeeAmount">
                            <g:message code="programFee.lateFeeAmount.label" default="Late Fee Amount"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>


                    <div class="university-size-2-3">
                        <g:textField name="lateFeeAmount"
                                     class="university-size-1-2"
                                     type="number"
                                     value="${admissionFeeInst?.lateFeeAmount}" onclick="this.value = ''"
                                     onkeypress="return isNumber(event)"/>
                    </div>
                </div>

                               <div class="fieldcontain">
                    <div class="university-size-1-3">&nbsp;</div>

                    <div class="university-size-2-3" style="margin: auto;">
                        <input type="submit"  name="create" class="save university-button" onclick="validateProgramFee()" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
                        %{--<g:submitButton name="create" class="save university-button"--}%
                        %{--onclick="validate()" value="${message(code: 'default.button.create.label', default: 'Create')}"/>--}%
                        <g:link controller="programFee" class="university-text-decoration-none"
                                action="listOfFeeType"><input type="button" name="create"
                                                              class="save university-button"
                                                              value="${message(code: 'default.button.cancel', default: 'Cancel')}"/></g:link>
                    </div>
                </div>

            </g:form>
        </div>
    </fieldset>
</div>
<script type="text/javascript">
    $('#createAdmissionFee').ready(function () {
        if(${params.id}){
            $('#semesterList').val('${termList}')
        }
    })
</script>
</body>
</html>
