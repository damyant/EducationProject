<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'programFee.label', default: 'ProgramFee')}"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='admin.js'/>

    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <div id="create-programFee" class="content scaffold-create" role="main">
            <h3><g:message code="default.create.Program.Fee"/></h3>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${programFeeInstance}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${programFeeInstance}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
            <g:form url="[resource: programFeeInstance, action: 'save']" id="createNewFee" name="createNewFee">
                <div style="margin-left: 5px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

                <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programDetail', 'error')} university-size-1-1">
                    <div class="university-size-1-3"><label for="programDetail">
                        <g:message code="programFee.programDetail.label" default="Program Name"/><span
                                class="university-obligatory">*</span>
                    </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:select id="programDetail" name="programDetail"
                                  from="${examinationproject.ProgramDetail.list()}" optionKey="id"
                                  optionValue="courseName" class="many-to-one university-size-1-2"
                                  noSelection="['': 'Choose Type']" onchange="loadSession(this)"/>
                    </div>
                </div>


                <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programDetail', 'error')} university-size-1-1">
                    <div class="university-size-1-3"><label for="programDetail">
                        <g:message code="programFee.programSession.label" default="Program Session"/><span
                                class="university-obligatory">*</span>
                    </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:select id="programSession" name="programSession"
                                  from="" optionKey=""
                                  optionValue="" class="many-to-one university-size-1-2"
                                  noSelection="['': 'Choose Session']" />
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
                                     value=""
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
                                     value=""
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
                                     value="" onclick="this.value = ''"
                                     onkeypress="return isNumber(event)"/>
                    </div>
                </div>

                <g:each in="${feeType}" var="fee">
                <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'lateFeeAmount', 'error')} required">
                    <div class="university-size-1-3">
                        <label for="feeType">
                            ${fee?.type}
                            <span class="university-obligatory">*</span>
                        </label>
                    </div>

                    <div class="university-size-2-3">
                        <g:textField name="feeType" class="university-size-1-2" type="number" onkeypress="return isNumber(event)"
                                     value=""/>
                    </div>
                </div>
                </g:each>



                <div class="fieldcontain">
                    <div class="university-size-1-3">&nbsp;</div>

                    <div class="university-size-2-3" style="margin: auto;">
                        <g:submitButton name="create" class="save university-button"
                                        onclick="validate()" value="${message(code: 'default.button.create.label', default: 'Create')}"/>
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
</body>
</html>
