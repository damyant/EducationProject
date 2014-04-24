<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 4/24/14
  Time: 4:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
</head>

<body>
<g:if test="${flash.message}">
    <div class="error">${flash.message}</div>
</g:if>
<g:form name="abc1" id="abc1" controller="admitCard" action="printAdmitCard">
    <p><bold>Please fill the information to download the Admit Card</bold></p>
    <div class="input">
        <input type="text" name="rollNumber" id="rollNo"  placeholder="Roll Number"/><label id="rollMsg" class="error"></label>
    </div>
    <div class="input">
        <input type="text" name="dob" id="dob" placeholder="Date of Birth" /><label id="dobMsg"></label>
    </div>
    <input type="button"  value="Submit" onclick="downloadAdmitCard();" />

%{--<div id="statusofApp" style="display: none">--}%

%{--</div>--}%
</g:form>
</body>
</html>