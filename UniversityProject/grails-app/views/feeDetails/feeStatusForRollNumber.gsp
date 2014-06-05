<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 6/4/2014
  Time: 11:01 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Check Fee Status</h3>
        <table class="inner">
            <tr>
                <td class="university-size-1-5">Enter Roll Number</td>
                <td class="university-size-1-3"><input type="text" class="university-size-2-3" maxlength="8" onkeypress="return isNumber(event)"  name="rollNoForFeeStatus" id="rollNoForFeeStatus"/> </td>
                <td class="university-size-1-4"><input type="button" class="university-button" value="Check Fee Status" onclick="checkFeeStatusForRollNo()"/> </td>
                <td class="university-size-1-3"><label id="errorLabel" class="error"></label></td>
            </tr>
        </table>
        <div class="university-size-full-1-1" id="showStatusForRollNo"></div>
    </fieldset>
</div>
</body>
</html>