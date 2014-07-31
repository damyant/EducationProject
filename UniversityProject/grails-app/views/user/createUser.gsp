<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
    <meta name="layout" content="main">
    <g:javascript src='tabulator.js'/>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'tabulator.css')}" type='text/css'>--}%

    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>

</head>

<body>

<div id="main">
    <fieldset class="form">
        <div id="create-user" class="content scaffold-create" role="main">
            <h3><g:message code="default.create.label" args="[entityName]"/></h3>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${userInstance}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                                error="${error}"/></li>
                    </g:eachError>
                </ul>
            </g:hasErrors>
            <g:form url="[resource: userInstance, action: 'save']">
                <fieldset class="form">
                    <div class="myclass"><%@ page import="examinationproject.StudyCenter; com.university.Role; com.university.UserRole; com.university.User" %>



                        <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
                            <div class="university-size-1-4"><label for="username">
                                <g:message code="user.username.label" default="Username"/>
                                <span class="required-indicator">*</span>
                            </label></div>

                            <div class="university-size-1-3"><g:textField class="university-size-2-3" name="username"
                                                                          required=""
                                                                          value="${userInstance?.username}"/></div>
                        </div>

                        <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', '')} required">
                            <div class="university-size-1-4"><label for="password">
                                <g:message code="user.password.label" default="Password"/>
                                <span class="required-indicator">*</span>
                            </label></div>

                            <div class="university-size-1-3"><g:passwordField name="password"
                                                                              class="university-size-2-3" required=""
                                                                              value="${userInstance?.password}"/></div>
                        </div>

                        <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">

                            <div class="university-size-1-4"><label for="email">
                                <g:message code="user.email.label" default="Email"/></label>
                                <span class="required-indicator">*</span></div>

                            <div class="university-size-1-3"><g:textField name="email" class="university-size-2-3"
                                                                          required=""
                                                                          value="${userInstance?.email}"/></div>

                        </div>


                        <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
                            <div class="university-size-1-4"><label for="accountExpired">
                                <g:message code="user.accountExpired.label" default="Account Expired"/>

                            </label></div>

                            <div class="university-size-1-3"><g:checkBox name="accountExpired"
                                                                         value="${userInstance?.accountExpired}"/></div>
                        </div>

                        <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
                            <div class="university-size-1-4"><label for="accountLocked">
                                <g:message code="user.accountLocked.label" default="Account Locked"/>

                            </label></div>

                            <div class="university-size-1-3"><g:checkBox name="accountLocked"
                                                                         value="${userInstance?.accountLocked}"/></div>
                        </div>

                        <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
                            <div class="university-size-1-4"><label for="enabled">
                                <g:message code="user.enabled.label" default="Enabled"/>

                            </label></div>

                            <div class="university-size-1-3"><g:checkBox name="enabled"
                                                                         value="${userInstance?.enabled}"/></div>
                        </div>

                        <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
                            <div class="university-size-1-4"><label for="passwordExpired">
                                <g:message code="user.passwordExpired.label" default="Password Expired"/>

                            </label></div>

                            <div class="university-size-1-3"><g:checkBox name="passwordExpired"
                                                                         value="${userInstance?.passwordExpired}"/></div>
                        </div>

                    </div>
                </fieldset>
                <fieldset class='roleFieldSet'>
                           <legend>
                            Please Select Roles
                           </legend>

                <g:each in="${roles}" status="i" var='roleInstance'>

                    <div class="fieldcontain1 ${hasErrors(bean: roleInstance, field: 'authority', 'error')} ">
                        <div class="university-size-1-4">
                            <label>${fieldValue(bean: roleInstance, field: "authority")}</label>
                        </div>


                        <g:if test="${roleInstance.authority == 'ROLE_TABULATOR1'}">
                            <div class="university-size-1-3"><g:checkBox name="myCheckbox" id="tab1"
                                                                         value="${roleInstance.id}"
                                                                         checked="" onchange="openTabulator()"/></div>

                            <div id="viewSelected1" class="university-size-1-3"><select class="university-size-2-3"
                                                                                        multiple="true"></select>
                                <input type="hidden" value="" id="tab1Program" name="tab1Program">
                                <input type="hidden" value="" id="tab1Semester" name="tab1Semester">
                            </div>
                        </g:if>
                        <g:elseif test="${roleInstance.authority == 'ROLE_TABULATOR2'}">
                            <div class="university-size-1-3"><g:checkBox name="myCheckbox" id="tab2"
                                                                         value="${roleInstance.id}"
                                                                         checked="" onchange="openTabulator()"/></div>

                            <div id="viewSelected2" class="university-size-1-3"><select class="university-size-2-3"
                                                                                        multiple="true"></select>
                                <input type="hidden" value="" id="tab2Program" name="tab2Program">
                                <input type="hidden" value="" id="tab2Semester" name="tab2Semester">
                            </div>
                        </g:elseif>
                        <g:else>
                            <div class="university-size-1-3"><g:checkBox name="myCheckbox" value="${roleInstance.id}"
                                                                         checked=""/></div>

                            <div class="university-size-1-3"></div>
                        </g:else>

                    </div>
                </g:each>

                <div class="fieldcontain1 required" id="studyCentreDiv" style="visibility: hidden">

                    <div class="university-size-1-4"><label for="email">
                        Select Study Centre</label>
                        <span class="required-indicator">*</span></div>

                    <div class="university-size-1-3">
                        <g:select id="studyCentreId" name="studyCentreId" from="${stydyCentreList}"
                                  optionKey="id" optionValue="name"
                                  value="${StudyCenter.findAllById(userInstance?.studyCentreId)}"
                                  class="university-size-2-3" disabled="true"
                                  noSelection="['': ' Select Study Centre']"/>
                    </div>

                </div>

            %{--</tbody>--}%
            %{--</table>--}%
                <fieldset class="buttons">
                    <div class="university-size-1-3"></div>

                    <div class="university-size-1-3"><g:submitButton name="create" class="save university-button"
                                                                     value="${message(code: 'default.button.create.label', default: 'Save')}"/>
                        <g:link controller="user" action="index"><input type="button" name="create"
                                                                        class="save university-button"
                                                                        value="${message(code: 'default.button.cancel', default: 'Cancel')}"/></g:link>
                    </div>
                </fieldset>
            </g:form>
        </div>
        <div id="coursePopup">
            <g:form>
                <div class="dialogTab1" id="dialogTab" style="height: 650px; display: none;">

                </div>
            </g:form>
        </div>
    </fieldset>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        document.getElementById("studyCentreId").multiple = false;
        $(".dialog").dialog({
            autoOpen: false,
            draggable: false,
            position: ['center', 0],
            width: 550,
            resizable: false,
            height: 400,
            modal: true,
            title: 'Assign Semesters',
            close: function (ev, ui) {
                $.unblockUI();
            }

        });
    })
    $('input[name="myCheckbox"]').change(function () {
        if ($(this).is(':checked')) {
            if ($(this).val() == 3) {
                document.getElementById("studyCentreDiv").style.visibility = "visible";
                $('#studyCentreId').prop('disabled', false);
                $('#studyCentreId').prop('required', true);
            }
            else if ($(this).val() == 8) {
                $('#button8').show();
            }
            else if ($(this).val() == 9) {
                $('#button9').show();
            }

        }
        else {
            if ($(this).val() == 3) {
                document.getElementById("studyCentreDiv").style.visibility = "hidden";
                $('#studyCentreId').prop('disabled', true);
                $('#studyCentreId').prop('required', false);
            }
            else if ($(this).val() == 8) {
                $('#button8').hide();
            }
            else if ($(this).val() == 9) {
                $('#button9').hide();
            }
        }
    })
</script>
</body>

</html>
