<%@ page import="com.university.Role; com.university.UserRole; com.university.User" %>



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
        <g:message code="email.label" default="Email"/></label>
    <span class="required-indicator">*</span>
    <g:textField name="email" required="" value="${userInstance?.email}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">

       %{-- <label for="role">
            <g:message code="role.label" default="Role"/></label>
            <span class="required-indicator">*</span>
           <g:select from="${roles}" name="userRole" optionKey="authority" optionValue="authority" style="width: 300px;height: 30px"/>--}%

    <g:if test="${roles!=null}" >
        <td valign="top">
        <g:message code="email.label" default="Role"/></label>
        <span class="required-indicator">*</span>
            <g:select from="${Role.list()}" optionKey="authority" optionValue="authority" value="" name="userRole"  noSelection="['':'-Choose role-']"
                      style="width: 300px;"/>
        </td>
    </g:if>
    <g:else>
        <td valign="top">
            <g:select from="${Role.list()}" optionKey="authority" optionValue="authority" value="" name="userRole"  noSelection="['':'-Choose role-']"
                      style="width: 300px;"/>
        </td>
    </g:else>

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

