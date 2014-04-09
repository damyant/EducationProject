<%@ page import="examinationproject.ProgramFee" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'programFee.label', default: 'ProgramFee')}" />
        <title><g:message code="default.edit.label" args="[entityName]"/></title>
    </head>
	<body>
    <div id="main">
        <div id="edit-programFee" class="content scaffold-edit" role="main">
            <h3><g:message code="default.edit.program.fee"/></h3>
            <g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${programFeeInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${programFeeInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            <g:form url="[resource: programFeeInstance, action: 'update']" method="PUT">
                <g:hiddenField name="version" value="${programFeeInstance?.version}"/>
				<fieldset class="form">
                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programDetail', 'error')} ">
                        <div class="university-size-1-3">
                            <label for="programDetail">
                                <g:message code="programFee.programDetail.label" default="Program Detail"/>

                        </label>
                        </div>

                        <div class="university-size-2-3">
                            <g:select id="programDetail" name="programDetail.id"
                                      from="${programFeeInstance.programDetail}" optionKey="id" optionValue="courseName"
                                      class="many-to-one university-size-1-2"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'feeAmountAtIDOL', 'error')} required">
                        <div class="university-size-1-3">
                            <label for="feeAmountAtIDOL">
                            <g:message code="programFee.feeAmountAtIDOL.label" default="Fee Amount At IDOL" />
                            <span class="required-indicator">*</span>
                        </label>
                        </div>

                        <div class="university-size-2-3">
                            <g:textField name="feeAmountAtIDOL" class="university-size-1-2" type="number"
                                         value="${programFeeInstance.feeAmountAtIDOL}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'feeAmountAtSC', 'error')} required">
                        <div class="university-size-1-3">
                            <label for="feeAmountAtSC">
                            <g:message code="programFee.feeAmountAtSC.label" default="Fee Amount At SC" />
                            <span class="required-indicator">*</span>
                        </label>
                        </div>

                        <div class="university-size-2-3">
                            <g:textField name="feeAmountAtSC" type="number" class="university-size-1-2"
                                         value="${programFeeInstance.feeAmountAtSC}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'lateFeeAmount', 'error')} required">
                        <div class="university-size-1-3">
                            <label for="lateFeeAmount">
                            <g:message code="programFee.lateFeeAmount.label" default="Late Fee Amount" />
                            <span class="required-indicator">*</span>
                        </label>
                        </div>

                        <div class="university-size-2-3">
                            <g:textField name="lateFeeAmount" type="number" class="university-size-1-2"
                                         value="${programFeeInstance.lateFeeAmount}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'lateFeeAmount', 'error')} required">
                        <div class="university-size-1-3">
                            <label for="examinationFee">
                                <g:message code="programFee.examinationFee.label" default="Examination Fee"/>
                                <span class="required-indicator">*</span>
                            </label>
                        </div>

                        <div class="university-size-2-3">
                            <g:textField name="examinationFee" class="university-size-1-2" type="number"
                                         value="${programFeeInstance.examinationFee}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'lateFeeAmount', 'error')} required">
                        <div class="university-size-1-3">
                            <label for="certificateFee">
                                <g:message code="programFee.certificateFee.label" default="Certificate Fee"/>
                                <span class="required-indicator">*</span>
                            </label>
                        </div>

                        <div class="university-size-2-3">
                            <g:textField name="certificateFee" class="university-size-1-2" type="number"
                                         value="${programFeeInstance.certificateFee}"/>
                        </div>
                    </div>

                    <div class="fieldcontain">
                        <div class="university-size-1-3"></div>

                        <div class="university-size-2-3">
                            <g:actionSubmit class="save university-button" action="update"
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
