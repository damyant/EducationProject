<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'feeDetails.label', default: 'FeeDetails')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>
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
                <div class="message" role="status"><div class="university-status-message">${flash.message}</div></div>
            </g:if>


            <g:hasErrors bean="${feeDetailsInstance}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${feeDetailsInstance}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
            <g:form url="[resource: feeDetailsInstance, action: 'saveFeeDetails']" id="createFeeDetail">
                <div class="university-size-1-1" style="margin-left: 4px;">
                    <label>
                        <h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory</h6>
                    </label>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'Roll Number', 'error')} required university-size-1-1">
                    <div class="university-size-1-3">
                        <label for="feeType">
                            <g:message code="feeDetails.rollno.label" default="Roll Number"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">

                        <g:textField id="rollNo" name="rollNo" onkeypress="return isNumber(event)" class="many-to-one university-size-1-2"/>
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
                                  disabled="disabled" optionValue="type"
                                  class="many-to-one university-size-1-2"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentMode', 'error')} ">
                    <div class="university-size-1-3">
                        <label>
                            <g:message code="feeDetails.paymentMode.label" default="Payment Mode"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:select  name="paymentMode" from="${paymentModeList}"
                                  optionKey="id"
                                  disabled="disabled" optionValue="paymentModeName"
                                  class="many-to-one university-size-1-2"/>
                    </div>
                </div>

                %{--<div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftNumber', 'error')} ">--}%
                    %{--<div class="university-size-1-3">--}%
                        %{--<label for="draftNumber">--}%
                            %{--<g:message code="feeDetails.draftNumber.label" default="Draft Number"/>--}%
                            %{--<span class="university-obligatory">*</span>--}%
                        %{--</label>--}%
                    %{--</div>--}%

                    %{--<div class="university-size-2-3">--}%
                        %{--<g:textField name="draftNumber" onkeypress="return isNumber(event)" value="${feeDetailsInstance?.draftNumber}" disabled="disabled"--}%
                                     %{--class="university-size-1-2"/>--}%
                    %{--</div>--}%
                %{--</div>--}%

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentDate', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="paymentDate">
                            <g:message code="feeDetails.paymentDate.label" default="Payment Date"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField name="paymentDate" maxlength="10" id="datePick1" class="university-size-1-2" value=""
                               disabled="disabled"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftDate', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="datePick">
                            <g:message code="feeDetails.draftDate.label" default="Draft Date"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <input type="text" name="draftDate" maxlength="10" id="datePick" class="university-size-1-2"
                               disabled="disabled"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'Bank', 'error')} ">
                    <div class="university-size-1-3">
                        <label for="issuingBank">
                            <g:message code="feeDetails.issuingBank.label" default="Issuing Bank"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField name="issuingBank" id="issuingBank" value="${feeDetailsInstance?.Bank}"
                                     disabled="disabled" class="university-size-1-2"/>
                    </div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'Branch', 'error')} ">
                    <div class="university-size-1-3">
                        <label for="issuingBranch">
                            <g:message code="feeDetails.issuingBranch.label" default="Issuing Branch"/>
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField name="issuingBranch" id="Branch"
                                     value="${feeDetailsInstance?.Branch}"
                                     disabled="disabled" class="university-size-1-2"/>
                    </div>
                </div>

                <div class="fieldcontain">
                    <div class="university-size-1-3">
                        &nbsp;
                    </div>

                    <div class="university-size-2-3">
                        <g:submitButton name="create" class="save university-button" onclick="validate()"
                                        value='Submit'/>
                    </div>
                </div>
            </g:form>
        </div>
    </fieldset>
</div>
</body>
</html>
