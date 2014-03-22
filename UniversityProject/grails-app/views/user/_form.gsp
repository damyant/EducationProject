<%@ page import="com.university.Role; com.university.UserRole; com.university.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
<div class="university-size-1-4"> <label for="username">
		<g:message code="user.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label></div>
	<div class="university-size-1-3"> <g:textField  class="university-size-2-3" name="username" required="" value="${userInstance?.username}"/></div>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', '')} required">
<div class="university-size-1-4"><label for="password">
		<g:message code="user.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label></div>
<div class="university-size-1-3"><g:passwordField name="password"  class="university-size-2-3" required="" value="${userInstance?.password}"/></div>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">


<div class="university-size-1-4"> <label for="email">
        <g:message code="email.label" default="Email"/></label>
    <span class="required-indicator">*</span></div>
<div class="university-size-1-3"><g:textField name="email"  class="university-size-2-3" required="" value="${userInstance?.email}"/></div>


</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">

        <div><label for="role">
            <g:message code="user.role.label" default="Role"/></label>
            <span class="required-indicator">*</span></div>
          <div class="university-size-1-3"> <g:select from="${Role.list()}" class="university-size-2-3" optionKey="authority" optionValue="authority" value="" name="userRole"  noSelection="['':'-Choose role-']"/></div>

</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
<div class="university-size-1-4"><label for="accountExpired">
		<g:message code="user.accountExpired.label" default="Account Expired" />
		
	</label></div>
<div class="university-size-1-3"><g:checkBox name="accountExpired" value="${userInstance?.accountExpired}" /></div>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
<div class="university-size-1-4"><label for="accountLocked">
		<g:message code="user.accountLocked.label" default="Account Locked" />
		
	</label></div>
<div class="university-size-1-3"><g:checkBox name="accountLocked" value="${userInstance?.accountLocked}" /></div>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
<div class="university-size-1-4"><label for="enabled">
		<g:message code="user.enabled.label" default="Enabled" />
		
	</label></div>
<div class="university-size-1-3"><g:checkBox name="enabled" value="${userInstance?.enabled}" /></div>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
<div class="university-size-1-4"><label for="passwordExpired">
		<g:message code="user.passwordExpired.label" default="Password Expired" />
		
	</label></div>
<div class="university-size-1-3"><g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}" /></div>
</div>

