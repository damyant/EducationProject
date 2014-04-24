<%--
  Created by IntelliJ IDEA.
  User: aman
  Date: 9/25/12
  Time: 12:11 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'userProfile.label', default: 'UserProfile')}"/>
    <script type="text/javascript">
        function validateForm(thisForm) {

            var newPwd = thisForm.newPwd;
            var reNewPwd = thisForm.reNewPwd;

            if (!newPwd.value || newPwd.value == '') {
                alert("New Password Should not be Null !");
                newPwd.focus();
                return false;
            }
            if (!reNewPwd.value || reNewPwd.value == '') {
                alert("Confirm Password Should not be Null !");
                reNewPwd.focus();
                return false;
            }
            if (newPwd.value != reNewPwd.value) {
                alert("New password & Confirm password not Matched !");
                reNewPwd.focus();
                return false;
            }

            return true;
        }
    </script>
</head>

<body>
<div id="main">
    %{--<fieldset class="form">--}%
    <div class="mainContent1" style="height: 300px">
        <div class="university-reset-pass-header">
            <h2 style="padding:10px;margin-left:10px;color: #ffffff;font-weight: bold;text-transform: uppercase;">
                <span style="float: left">
                    Reset Password
                </span>

            </h2>
        </div>

        <div style="font-size: 20px;">
            <div class="body" style="padding: 5%;">
                <g:if test="${flash.message}">
                    <div class="reset-message">${flash.message}</div>
                </g:if>
                <g:form method="post" controller="user" action="updatePwd" onsubmit="return validateForm(this)">
                    <g:hiddenField name="id" value="${params.id}"/>
                    <table id="resetPasswordContainer">
                        <tr>
                            <td>
                                New Password
                            </td>
                            <td>
                                <input type="password" value="" name="newPwd" class="university-size-1-1">
                            </td>
                        </tr>

                        <tr>
                            <td>Confirm Password</td>
                            <td><input type="password" value="" name="reNewPwd" class="university-size-1-1"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div class="reset-button">
                                    <g:submitButton name="changePwd" value="Submit" class="university-button"/>
                                    <g:link action="userList">
                                        <input type="button" value="Cancel" class="university-button">
                                    </g:link>
                                </div>
                            </td>
                        </tr>
                    </table>
                </g:form>
            </div>
        </div>
    </div>
    %{--</fieldset>--}%
</div>
</body>
</html>
