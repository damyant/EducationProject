<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'feeDetails.label', default: 'FeeDetails')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>

        %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.js')}"></script>--}%

            <script type="text/javascript">
                // wait for dom to load
                    $(function(){
                        // set onblur event handler
                        $("#rollNo").blur(function(){
                            // if some username was entered - this == #username
                            if($(this).length > 0) {
                                // use create link so we don't have to hardcode context
                                var url = "${createLink(controller:'feeDetails', action:'checkStudentByRollNo')}"
                                // async ajax request pass username entered as id parameter
                                $.getJSON(url, {rollNo:$(this).val()}, function(json){
                                    if(!json.available) {
                                        alert("data is"+json.id+"status"+json.feeStatus)
                                        // highlight field so user knows there's a problem
                                        $("#feeType").prop('disabled',false);
                                        $("#paymentMode").prop('disabled',false);
                                        $("#draftNumber").prop('disabled',false);
                                        $("#datePick1").prop('disabled',false);
                                        $("#datePick").prop('disabled',false);
                                        $("#issuingBank").prop('disabled',false);
                                        $("#issuingBranch").prop('disabled',false);

                                    }
                                });
                            }
                        });
                    });
        </script>

	</head>
	<body>

		<div id="create-feeDetails" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${feeDetailsInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${feeDetailsInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:feeDetailsInstance, action:'saveFeeDetails']" >
				<fieldset class="form">

                    <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'Roll Number', 'error')} required">
                        <label for="feeType">
                            <g:message code="feeDetails.rollno.label" default="Roll Number" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:textField id="rollNo" name="rollNo" class="many-to-one"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'feeType', 'error')} required">
                        <label for="feeType">
                            <g:message code="feeDetails.feeType.label" default="Fee Type" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:select id="feeType" name="feeType.id" from="${examinationproject.FeeType.list()}" optionKey="id" disabled="disabled" required="" value="${feeDetailsInstance?.feeType?.id}" class="many-to-one"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentMode', 'error')} ">
                        <label for="paymentMode">
                            <g:message code="feeDetails.paymentMode.label" default="Payment Mode" />

                        </label>
                        <g:textField name="paymentMode" value="${feeDetailsInstance?.paymentMode}" disabled="disabled"/>
                    </div>


                    <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftNumber', 'error')} ">
                        <label for="draftNumber">
                            <g:message code="feeDetails.draftNumber.label" default="Draft Number" />

                        </label>
                        <g:textField name="draftNumber" value="${feeDetailsInstance?.draftNumber}" disabled="disabled"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentDate', 'error')} required">
                        <label for="paymentDate">
                            <g:message code="feeDetails.paymentDate.label" default="Payment Date" />
                            <span class="required-indicator">*</span>
                        </label>
                        <input type="text" name="paymentDate" maxlength="30"  id="datePick1" disabled="disabled"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftDate', 'error')} required">
                        <label for="draftDate">
                            <g:message code="feeDetails.draftDate.label" default="Draft Date" />
                            <span class="required-indicator">*</span>
                        </label>
                        <input type="text" name="draftDate" maxlength="30"  id="datePick" disabled="disabled"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'issuingBank', 'error')} ">
                        <label for="issuingBank">
                            <g:message code="feeDetails.issuingBank.label" default="Issuing Bank" />

                        </label>
                        <g:textField name="issuingBank"  id = "issuingBank" value="${feeDetailsInstance?.issuingBank}" disabled="disabled"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'issuingBranch', 'error')} ">
                        <label for="issuingBranch">
                            <g:message code="feeDetails.issuingBranch.label" default="Issuing Branch" />

                        </label>
                        <g:textField name="issuingBranch" id="issuingBranch" value="${feeDetailsInstance?.issuingBranch}"  disabled="disabled"/>
                    </div>

                </fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />


				</fieldset>
			</g:form>
		</div>
	</body>
</html>
