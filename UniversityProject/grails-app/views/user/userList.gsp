<%@ page import="com.university.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}"/>

</head>

<body>
%{--<a href="#list-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><g:link class="create" action="createUser"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>--}%
<div id="main">
    <div id="list-user" class="content scaffold-list" role="main">
        <h3><g:message code="default.list.label" args="[entityName]"/></h3>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <table class="university-table-1-7">
            <thead>
            <tr>

                <g:sortableColumn property="username"
                                  title="${message(code: 'user.username.label', default: 'Username')}"/>

                <g:sortableColumn property="email" title="${message(code: 'user.email.label', default: 'Email')}"/>

                <g:sortableColumn property="accountExpired"
                                  title="${message(code: 'user.accountExpired.label', default: 'Account Expired')}"/>

                <g:sortableColumn property="accountLocked"
                                  title="${message(code: 'user.accountLocked.label', default: 'Account Locked')}"/>

                <g:sortableColumn property="enabled"
                                  title="${message(code: 'user.enabled.label', default: 'Enabled')}"/>

                <g:sortableColumn property="passwordExpired"
                                  title="${message(code: 'user.passwordExpired.label', default: 'Password Expired')}"/>
                <g:sortableColumn property="edit" title="${message(code: 'user.enabledited.label', default: 'Edit')}"/>

            </tr>
            </thead>
            <tbody>
            <g:each in="${userInstanceList}" status="i" var="userInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                    <td>${fieldValue(bean: userInstance, field: "username")}</td>

                    <td>${fieldValue(bean: userInstance, field: "email")}</td>

                    <td><g:formatBoolean boolean="${userInstance.accountExpired}"/></td>

                    <td><g:formatBoolean boolean="${userInstance.accountLocked}"/></td>

                    <td><g:formatBoolean boolean="${userInstance.enabled}"/></td>

                    <td><g:formatBoolean boolean="${userInstance.passwordExpired}"/></td>

                    <td><button class="university-button"> <g:link class="university-text-decoration-none" action="editUser" id="${userInstance.id}">Edit</g:link></button></td>

                </tr>
            </g:each>
            </tbody>
        </table>

        <div class="pagination">
            <g:paginate total="${userInstanceCount ?: 0}"/>
        </div>
    </div>
</div>
</body>
</html>
