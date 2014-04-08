<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'feeDetails.label', default: 'FeeDetails')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>

    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.js')}"></script>--}%

    <script type="text/javascript">
        // wait for dom to load
        $(function () {
            // set onblur event handler
            $("#rollNo").blur(function () {
                // if some username was entered - this == #username
                if ($(this).length > 0) {
                    // use create link so we don't have to hardcode context
                    var url = "${createLink(controller:'feeDetails', action:'checkStudentByRollNo')}"
                    // async ajax request pass username entered as id parameter
                    $.getJSON(url, {rollNo: $(this).val()}, function (json) {
                        if (!json.available) {
                            alert("data is" + json.id + "status" + json.feeStatus)
                            // highlight field so user knows there's a problem
                            $("#feeType").prop('disabled', false);
                            $("#paymentMode").prop('disabled', false);
                            $("#draftNumber").prop('disabled', false);
                            $("#datePick1").prop('disabled', false);
                            $("#datePick").prop('disabled', false);
                            $("#issuingBank").prop('disabled', false);
                            $("#issuingBranch").prop('disabled', false);
                            $("input[name='studentId']").val(json.id);



                        }
                    });
                }
            });
        });
    </script>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <div id="create-feeDetails" class="content scaffold-create" role="main">
            <h3><g:message code="default.create.label" args="[entityName]"/></h3>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>


            <g:hasErrors bean="${feeDetailsInstance}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${feeDetailsInstance}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
            <g:form url="[resource: feeDetailsInstance, action: 'saveFeeDetails']">
                <div class="university-size-1-1" style="margin-left: 4px;">
                    <label>
                        <h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6>
                    </label>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'Roll Number', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="feeType">
                            <g:message code="feeDetails.rollno.label" default="Roll Number"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField id="rollNo" name="rollNo" class="many-to-one university-size-2-3" required="true"/>
                        <g:hiddenField name="studentId" id="studentId" value=""/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'feeType', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="feeType">
                            <g:message code="feeDetails.feeType.label" default="Fee Type"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:select id="feeType" name="feeType" from="${examinationproject.FeeType.list()}"
                                  optionKey="id"
                                  disabled="disabled" required="required"  optionValue="type"
                                  class="many-to-one university-size-2-3"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentMode', 'error')} ">
                    <div class="university-size-1-3">
                        <label for="paymentMode">
                            <g:message code="feeDetails.paymentMode.label" default="Payment Mode"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField name="paymentMode" value="${feeDetailsInstance?.paymentMode}" disabled="disabled"
                                     class="university-size-2-3" required="true"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftNumber', 'error')} ">
                    <div class="university-size-1-3">
                        <label for="draftNumber">
                            <g:message code="feeDetails.draftNumber.label" default="Draft Number"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField name="draftNumber" value="${feeDetailsInstance?.draftNumber}" disabled="disabled"
                                     class="university-size-2-3" required="true"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentDate', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="paymentDate">
                            <g:message code="feeDetails.paymentDate.label" default="Payment Date"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField name="paymentDate" maxlength="30" id="datePick1" class="university-size-2-3" value=""
                               disabled="disabled" required="true"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftDate', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="draftDate">
                            <g:message code="feeDetails.draftDate.label" default="Draft Date"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <input type="text" name="draftDate" maxlength="30" id="datePick" class="university-size-2-3"
                               disabled="disabled" required="true"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'issuingBank', 'error')} ">
                    <div class="university-size-1-3">
                        <label for="issuingBank">
                            <g:message code="feeDetails.issuingBank.label" default="Issuing Bank"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField name="issuingBank" id="issuingBank" value="${feeDetailsInstance?.issuingBank}"
                                     disabled="disabled" class="university-size-2-3" required="true"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'issuingBranch', 'error')} ">
                    <div class="university-size-1-3">
                        <label for="issuingBranch">
                            <g:message code="feeDetails.issuingBranch.label" default="Issuing Branch"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField name="issuingBranch" id="issuingBranch"
                                     value="${feeDetailsInstance?.issuingBranch}"
                                     disabled="disabled" class="university-size-2-3" required="true"/>
                    </div>
                </div>

                <div class="fieldcontain">
                    <div class="university-size-1-3">
                        &nbsp;
                    </div>

                    <div class="university-size-2-3">
                        <g:submitButton name="create" class="save university-button"
                                        value="${message(code: 'default.button.create.label', default: 'Create')}"/>
                    </div>
                </div>
            </g:form>
        </div>
    </fieldset>
</div>
</body>
</html>
