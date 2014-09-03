
<%@ page import="examinationproject.StudyCenter; com.university.Role; com.university.UserRole; com.university.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:javascript src='tabulator.js'/>
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div id="main">
    %{--<div class="nav" role="navigation">
        <ul>
            <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            <li><g:link class="create" action="createUser"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
        </ul>
    </div>--}%
    <div id="edit-user" class="content scaffold-edit" role="main">
        <h3><g:message code="default.edit.label" args="[entityName]"/></h3>
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
    <g:form name="edit" action='updateUser'>
        <g:hiddenField name="version" value="${userInstance?.version}"/>
            <g:hiddenField name="id" value="${userInstance?.id}"/>
            <fieldset class="form">

                <%@ page import="com.university.Role; com.university.UserRole; com.university.User" %>



                <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
                    <div class="university-size-1-4"><label for="username">
                        <g:message code="user.username.label" default="Username"/>
                        <span class="required-indicator">*</span>
                    </label></div>

                    <div class="university-size-1-3"><g:textField class="university-size-2-3" name="username"
                                                                  required="" value="${userInstance?.username}"/></div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', '')} required">
                    <div class="university-size-1-4"><label for="password">
                        <g:message code="user.password.label" default="Password"/>
                        <span class="required-indicator">*</span>
                    </label></div>

                    <div class="university-size-1-3"><g:passwordField name="password" class="university-size-2-3"
                                                                      required=""
                                                                      value="${userInstance?.password}"/></div>
                </div>

                <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">

                    <div class="university-size-1-4"><label for="email">
                        <g:message code="user.email.label" default="Email"/></label>
                        <span class="required-indicator">*</span></div>

                    <div class="university-size-1-3"><g:textField name="email" class="university-size-2-3" required=""
                                                                  value="${userInstance?.email}"/></div>

                </div>


                %{--<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">--}%

                    %{--<div><label for="role">--}%
                        %{--<g:message code="user.role.label" default="Role"/></label>--}%
                        %{--<span class="required-indicator">*</span></div>--}%
                %{--<div>--}%
                    %{--<g:if test="${UserRole.findByUser(userInstance) != null}">--}%

                        %{--<g:select from="${Role.list()}" optionKey="authority" optionValue="authority"--}%
                                  %{--value="${role.authority}" name="userRole" class="university-size-2-3"/>--}%

                    %{--</g:if>--}%
                    %{--<g:else>--}%
                        %{--<g:select from="${Role.list()}" optionKey="authority" optionValue="authority" value=""--}%
                                  %{--name="userRole" class="university-size-2-3" noSelection="['': '-Choose role-']"/>--}%

                    %{--</g:else>--}%

                    %{--</div>--}%

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
                        <div><label for="accountExpired">
                            <g:message code="user.accountExpired.label" default="Account Expired"/>

                        </label></div>

                        <div>
                            <g:checkBox name="accountExpired" value="${userInstance?.accountExpired}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
                        <div><label for="accountLocked">
                            <g:message code="user.accountLocked.label" default="Account Locked"/>
                        </label></div>

                        <div>
                            <g:checkBox name="accountLocked" value="${userInstance?.accountLocked}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
                        <div><label for="enabled">
                            <g:message code="user.enabled.label" default="Enabled"/>

                        </label></div>

                        <div>
                            <g:checkBox name="enabled" value="${userInstance?.enabled}"/>
                        </div>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
                        <div class="university-size-1-4"><label for="passwordExpired">
                            <g:message code="user.passwordExpired.label" default="Password Expired"/>

                        </label></div>

                        <div><g:checkBox name="passwordExpired"
                                         value="${userInstance?.passwordExpired}"/></div>
                    </div>
            </fieldset>
            <fieldset class='editRoleFieldSet'>
                <legend>
                    Please Select Roles
                </legend>
                <table style='border:0px'>
                    <tbody>
                    <g:each in="${roles}" status="i" var='roleInstance'>

                        <tr>
                            <td class='name university-size-1-4' >
                                <label> ${fieldValue(bean: roleInstance, field: "authority")} </label>
                            </td>
                            <td class='value university-size-3-4'>
                                <g:set var="userRoleIds" value="${userRoles.id}"/>

                                <g:if test="${userRoleIds.contains(roleInstance.id)}">
                                    <g:if test="${roleInstance.authority=='ROLE_TABULATOR1'}">
                                        <div class="university-size-1-3" ><g:checkBox name="myCheckbox" id="tab1" value="${roleInstance.id}"
                                                                                      checked="true" onchange="openTabulator()" /></div>
                                        <div id="viewSelected1" class="university-size-1-3">
                                            <select class="university-size-2-3"  multiple="true">

                                        <g:if test="${tab1OptionValue.size()>0}">
                                                <g:each in="${0..tab1OptionValue.size()-1}" var="index">
                                                    <option value="${tab1OptionValue.get(index)}">${tab1OptionText.get(index)}</option>
                                                </g:each>
                                        </g:if>
                                            </select>
                                            <input type="hidden" value="${tab1HProgList}" id="tab1Program" name="tab1Program">
                                            <input type="hidden" value="${tab1HSemList}" id="tab1Semester" name="tab1Semester">
                                        </div>
                                    </g:if>
                                    <g:elseif test="${roleInstance.authority=='ROLE_TABULATOR2'}">
                                        <div class="university-size-1-3" ><g:checkBox name="myCheckbox" id="tab2" value="${roleInstance.id}"
                                                                                      checked="true" onchange="openTabulator()" /></div>
                                        <div id="viewSelected2" class="university-size-1-3">
                                            <select class="university-size-2-3"  multiple="true">
                                            <g:if test="${tab2OptionValue.size()>0}">
                                                <g:each in="${0..tab2OptionValue.size()-1}" var="index">
                                                    <option value="${tab2OptionValue.get(index)}">${tab2OptionText.get(index)}</option>
                                                </g:each>
                                            </g:if>
                                            </select>
                                            <input type="hidden" value="${tab2HProgList}" id="tab2Program" name="tab2Program">
                                            <input type="hidden" value="${tab2HSemList}" id="tab2Semester" name="tab2Semester">
                                        </div>
                                    </g:elseif>
                                    <g:else>
                                    <g:checkBox name="myCheckbox" value="${roleInstance.id}" checked="true"/>
                                    </g:else>
                                </g:if>
                                <g:else>
                                    <g:if test="${roleInstance.authority=='ROLE_TABULATOR1'}">
                                        <div class="university-size-1-3" ><g:checkBox name="myCheckbox" id="tab1" value="${roleInstance.id}"
                                                                                      checked="false" onchange="openTabulator()" /></div>
                                        <div id="viewSelected1" class="university-size-1-3"><select class="university-size-2-3" multiple="true"></select>
                                            <input type="hidden" value="" id="tab1Program" name="tab1Program">
                                            <input type="hidden" value="" id="tab1Semester" name="tab1Semester">
                                        </div>
                                    </g:if>
                                    <g:elseif test="${roleInstance.authority=='ROLE_TABULATOR2'}">
                                        <div class="university-size-1-3" ><g:checkBox name="myCheckbox" id="tab2" value="${roleInstance.id}"
                                                                                      checked="false" onchange="openTabulator()" /></div>
                                        <div id="viewSelected2" class="university-size-1-3"><select class="university-size-2-3"  multiple="true"></select>
                                            <input type="hidden" value="" id="tab2Program" name="tab2Program">
                                            <input type="hidden" value="" id="tab2Semester" name="tab2Semester">
                                        </div>
                                    </g:elseif>
                                    <g:else>
                                    <g:checkBox name="myCheckbox" value="${roleInstance.id}" checked="false"/>
                                </g:else>
                        </g:else>
                            </td>

                        </tr>

                    </g:each>
                    </tbody>
                </table>
                %{--<g:if test="${studyCentre!=null}">--}%
                    <div class="fieldcontain required" id="studyCentreDiv">


                        <div class="university-size-1-4"> <label for="email">
                            Select Study Centre </label>
                            <span class="required-indicator">*</span></div>
                        <div class="university-size-1-3">
                            %{--<g:select id="studyCentreId" name="studyCentreId" from="${studyCentreList}"--}%
                                      %{--optionKey="id" optionValue="name" value="${studyCentre}"--}%
                                      %{--class="university-size-2-3" noSelection="['0': ' Select Study Centre']"/>--}%
                            <g:select id="studyCentreId" name="studyCentreId" from="${studyCentreList}"
                                      optionKey="id" optionValue="name"    value="${studyCentre}"
                                      class="university-size-2-3" disabled="true"
                                      noSelection="['': ' Select Study Centre']"/>
                        </div>


                    </div>
                %{--</g:if>--}%
            </fieldset>
            <fieldset class="buttons">
               <input type="SUBMIT" class="university-button" value="Update" id="updateUser"/>
                <g:link controller="user" action="index"><input type="button" name="create"
                                                                class="save university-button"
                                                                value="${message(code: 'default.button.cancel', default: 'Cancel')}"/></g:link>
            </fieldset>
        </g:form>
    </div>
<div id="coursePopup">
    <g:form>
        <div class="dialogTab1" id="dialogTab" style="height: 650px; display: none;">

        </div>
    </g:form>
</div>
</div>
<script type="text/javascript">
            $(document).ready(function () {
                document.getElementById("studyCentreId").multiple = false;
                $(".dialog").dialog({
                    autoOpen: false,
                    draggable: false,
                    position: ['center',0],
                    width: 550,
                    resizable: false,
                    height: 400,
                    modal: true,
                    title:'Assign Semesters',
                    close: function(ev, ui) {
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

        }
        else{
            if ($(this).val() == 3) {
                document.getElementById("studyCentreDiv").style.visibility = "hidden";
                $('#studyCentreId').prop('disabled', true);
                $('#studyCentreId').prop('required', false);
            }
        }
    })
</script>
</body>
</html>
