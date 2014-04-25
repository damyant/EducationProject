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
<div id="main">
    <fieldset class="form">
        <h3>Download Admit Card</h3>
<g:if test="${flash.message}">
    <div class="error">${flash.message}</div>
</g:if>
<g:form name="abc1" id="abc1" controller="admitCard" action="printAdmitCard">
    <div class="">
    <p><bold>Please fill the information to download the Admit Card</bold></p>

    <div class="input university-bottom-margin">
        <label for="rollNo" class="university-right-margin">Enter Roll Number</label><input type="text" name="rollNumber" id="rollNo" class="university-size-1-3"  placeholder="Roll Number"/><label id="rollMsg" class="error"></label>
    </div>
    <div class="input university-bottom-margin">
        <label for="dob" class="university-right-margin">Enter Date of Birth</label><input type="text" class="university-size-1-3" name="dob" id="dob" placeholder="Date of Birth" /><label id="dobMsg"></label>
    </div>
    <input type="button"  value="Submit" class="ui-button" onclick="downloadAdmitCard();" />
    </div>
%{--<div id="statusofApp" style="display: none">--}%

%{--</div>--}%
</g:form>
        </fieldset>
    </div>
</body>
</html>