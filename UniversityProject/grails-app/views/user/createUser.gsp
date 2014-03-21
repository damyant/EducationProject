<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		%{--<a href="#create-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>--}%
		<div id="create-user" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${userInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${userInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:userInstance, action:'saveUser']" >
				<fieldset class="form">
					%{--<g:render template="form"/>--}%

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
                        <label for="username">
                            <g:message code="user.username.label" default="Username" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:textField name="username" required="" value="${userInstance?.username}"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', '')} required">
                        <label for="password">
                            <g:message code="user.password.label" default="Password" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:passwordField name="password" required="" value="${userInstance?.password}"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">

                        <label for="email">
                            <g:message code="user.email.label" default="Email"/></label>
                        <span class="required-indicator">*</span>
                        <g:textField name="email" required="" value="${userInstance?.email}"/>

                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">

                     <label for="role">
                         <g:message code="role.label" default="Role"/></label>
                         <span class="required-indicator">*</span>
                        <g:select from="${roles}" name="userRole" optionKey="authority" optionValue="authority" style="width: 300px;height: 30px"/>

                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
                        <label for="accountExpired">
                            <g:message code="user.accountExpired.label" default="Account Expired" />

                        </label>
                        <g:checkBox name="accountExpired" value="${userInstance?.accountExpired}" />
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
                        <label for="accountLocked">
                            <g:message code="user.accountLocked.label" default="Account Locked" />

                        </label>
                        <g:checkBox name="accountLocked" value="${userInstance?.accountLocked}" />
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
                        <label for="enabled">
                            <g:message code="user.enabled.label" default="Enabled" />

                        </label>
                        <g:checkBox name="enabled" value="${userInstance?.enabled}" />
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
                        <label for="passwordExpired">
                            <g:message code="user.passwordExpired.label" default="Password Expired" />

                        </label>
                        <g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}" />
                    </div>

                </fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Save')}" />
                    <g:link controller="user" action="index"><input type="button" name="create" class="save" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}"/> </g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
