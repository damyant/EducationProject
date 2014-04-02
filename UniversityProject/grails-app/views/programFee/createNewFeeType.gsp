<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'programFee.label', default: 'ProgramFee')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>

		<div id="create-programFee" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
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
			<g:form url="[resource:programFeeInstance, action:'save']" >
				<fieldset class="form">
                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programDetail', 'error')} ">
                        <label for="programDetail">
                            <g:message code="programFee.programDetail.label" default="Program Name" />

                        </label>
                        <g:select id="programDetail" name="programDetail" from="${examinationproject.ProgramDetail.list()}" optionKey="id" optionValue="courseName" class="many-to-one" noSelection="['':'Choose Program']"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'feeAmountAtIDOL', 'error')} required">
                        <label for="feeAmountAtIDOL">
                            <g:message code="programFee.feeAmountAtIDOL.label" default="Fee Amount At IDOL" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:textField name="feeAmountAtIDOL" type="number" value="${programFeeInstance.feeAmountAtIDOL}" required=""/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'feeAmountAtSC', 'error')} required">
                        <label for="feeAmountAtSC">
                            <g:message code="programFee.feeAmountAtSC.label" default="Fee Amount At SC" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:textField name="feeAmountAtSC" type="number" value="${programFeeInstance.feeAmountAtSC}" required=""/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'lateFeeAmount', 'error')} required">
                        <label for="lateFeeAmount">
                            <g:message code="programFee.lateFeeAmount.label" default="Late Fee Amount" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:textField name="lateFeeAmount" type="number" value="${programFeeInstance.lateFeeAmount}" required=""/>
                    </div>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                    <g:link controller="programFee" action="listOfFeeType"><input type="button" name="create" class="save university-button"
                                                                    value="${message(code: 'default.button.cancel', default: 'Cancel')}"/></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
