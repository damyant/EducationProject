<%@ page import="com.university.Role; com.university.UserRole; com.university.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
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
        <g:form url="[resource: userInstance, action: 'update']" method="PUT">
            <g:hiddenField name="version" value="${userInstance?.version}"/>
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

                <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">

                    <div><label for="role">
                        <g:message code="user.role.label" default="Role"/></label>
                        <span class="required-indicator">*</span></div>
                <div>
                    <g:if test="${UserRole.findByUser(userInstance) != null}">

                        <g:select from="${Role.list()}" optionKey="authority" optionValue="authority"
                                  value="${role.authority}" name="userRole" class="university-size-2-3"/>

                    </g:if>
                    <g:else>
                        <g:select from="${Role.list()}" optionKey="authority" optionValue="authority" value=""
                                  name="userRole" class="university-size-2-3" noSelection="['': '-Choose role-']"/>

                    </g:else>

                    </div>

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
            <fieldset class="buttons">
                <g:actionSubmit class="save university-button" action="update"
                                value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                <g:link controller="user" action="index"><input type="button" name="create"
                                                                class="save university-button"
                                                                value="${message(code: 'default.button.cancel', default: 'Cancel')}"/></g:link>
            </fieldset>
        </g:form>
    </div>
</div>
</body>
</html>
