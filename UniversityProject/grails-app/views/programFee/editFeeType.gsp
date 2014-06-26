<%@ page import="examinationproject.AdmissionFee" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'programFee.label', default: 'ProgramFee')}" />
        <title><g:message code="default.edit.label" args="[entityName]"/></title>
        <g:javascript src='admin.js'/>
        <script type="text/javascript">
                $(window).bind("load", function () {

                    fillFeeInfoUpdate("${programFeeInstance.sessionOfFee}")
                })

        </script>
    </head>
	<body>
    <div id="main">
        <div id="edit-programFee" class="content scaffold-edit" role="main">
            <h3><g:message code="default.edit.program.fee"/></h3>
            <g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
            <div id="statusMessage" style="visibility: hidden" class="university-status-message"></div>
			<g:hasErrors bean="${programFeeInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${programFeeInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            <g:form id="updateFee" name="updateFee">
                <g:hiddenField name="version" value="${programFeeInstance?.version}"/>
				<fieldset class="form">
                    <div style="margin-left: 5px;"><label><h6>All${programFeeInstance.admissionFee[0].id}${programFeeInstance.programDetailId.id} [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programDetail', 'error')} ">
                        <div class="university-size-1-3">
                            <label for="programDetail">
                                <g:message code="programFee.programDetail.label" default="Program Detail"/>
                                <span class="university-obligatory">*</span>
                        </label>
                        </div>

                        <div class="university-size-1-3">
                            <g:select id="programDetail" name="programDetail"
                                      from="${programFeeInstance.programDetailId}" optionKey="id" optionValue="courseName"
                                      class="many-to-one university-size-1-2" onchange="loadSession(this)"/>

                            <g:hiddenField name="programDetailId" id="programDetailId" value="${programFeeInstance.programDetailId.id}"></g:hiddenField>
                            <g:hiddenField name="admissionFee" id="admissionFee" value="${programFeeInstance.admissionFee[0].id}"></g:hiddenField>
                        </div>
                    </div>
                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programDetail', 'error')} ">
                    <div class="university-size-2-3"><label for="programDetail">
                        <g:message code="programFee.programSession.label" default="Programme Session"/><span
                                class="university-obligatory">*</span>
                    </label>
                    </div>

                    <div class="university-size-2-3">
                        %{--<g:textField id="session" name="programSession" readonly="readonly" value="${programFeeInstance.sessionOfFee}"/>--}%
                        <select id="session" name="programSessionId" class="university-size-1-2">
                            %{--<option value="">Select Session</option>--}%
                            <g:each in="${programSessions}" var="session">
                                <option value="${session.sessionOfProgram}">${session.sessionOfProgram}</option>
                            </g:each>
                        </select>
                    </div>
                        </div>
                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'feeAmountAtIDOL', 'error')} required">
                        <div class="university-size-1-3">
                            <label for="feeAmountAtIDOL">
                            <g:message code="programFee.feeAmountAtIDOL.label" default="Fee Amount At IDOL" />
                                <span class="university-obligatory">*</span>
                        </label>
                        </div>

                        <div class="university-size-2-3">
                            <g:textField name="feeAmountAtIDOL" class="university-size-1-2" type="number"
                                         value="${programFeeInstance?.admissionFee?.feeAmountAtIDOL[0]}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'feeAmountAtSC', 'error')} required">
                        <div class="university-size-1-3">
                            <label for="feeAmountAtSC">
                            <g:message code="programFee.feeAmountAtSC.label" default="Fee Amount At SC" />
                                <span class="university-obligatory">*</span>
                        </label>
                        </div>

                        <div class="university-size-2-3">
                            <g:textField name="feeAmountAtSC" type="number" class="university-size-1-2"
                                         value="${programFeeInstance?.admissionFee?.feeAmountAtSC[0]}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'lateFeeAmount', 'error')} required">
                        <div class="university-size-1-3">
                            <label for="lateFeeAmount">
                            <g:message code="programFee.lateFeeAmount.label" default="Late Fee Amount" />
                                <span class="university-obligatory">*</span>
                        </label>
                        </div>

                        <div class="university-size-2-3">
                            <g:textField name="lateFeeAmount" type="number" class="university-size-1-2"
                                         value="${programFeeInstance?.admissionFee?.lateFeeAmount[0]}"/>
                        </div>
                    </div>
              <g:if test ="${miscFee}">
                    <g:each in="${0..miscFee.size()-1}" var="index">
                        <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'lateFeeAmount', 'error')} required">
                            <div class="university-size-1-3">
                                <label for="feeType">
                                    ${miscFee[index]?.type}
                                    <span class="university-obligatory">*</span>
                                </label>
                            </div>

                            <div class="university-size-2-3">
                                <g:textField name="feeTypeAmount" class="university-size-1-2" type="number" onkeypress="return isNumber(event)"
                                             value="${miscellaneousFeeList[index]?.amount}"/>
                            </div>
                        </div>
                        <g:javascript>
                         feeTypeList.push(${miscFee[index]?.id})
                        </g:javascript>

                    </g:each>
              </g:if>



                    <div class="fieldcontain">
                        <div class="university-size-1-3"></div>

                        <div class="university-size-2-3">
                            <input type="button" name="create" class="save university-button" onclick="updateProgramFee()"
                                            onclick="validate()" value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                            <g:link controller="programFee" class="university-text-decoration-none"
                                    action="listOfFeeType"><input type="button" name="create"
                                                                  class="save university-button"
                                                                  value="${message(code: 'default.button.cancel', default: 'Cancel')}"/></g:link>
                        </div>
                    </div>
                </fieldset>
			</g:form>
		</div>
    </div>
    </body>
</html>
