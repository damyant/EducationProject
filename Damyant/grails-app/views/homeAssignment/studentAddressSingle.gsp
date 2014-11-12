<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 6/3/2014
  Time: 2:00 PM
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
        <h3>
           Print Student Address
        </h3>
        <br/>
        %{--${rootImageFolder}--}%
<g:form name="identityCardForm" id="identityCardForm" controller="homeAssignment" action="printStudentAddress">
    <g:hiddenField name="type" id="studentList" value='single'/>
    <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
    </g:if>
            <table class="inner" style="margin: auto;">

                <tr>
                    <td class="university-size-1-5">
                        <label for="StudentRollNo">Enter Roll Number</label>
                    </td>
                    <td class="university-size-1-3">
                        <g:textField name="rollNo" id="StudentRollNo" maxlength="8" class="university-size-1-1"  onkeypress="return isNumber(event)" />
                    </td>

                    <td class="university-size-1-4">
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-5">
                    </td>
                    <td class="university-size-1-3">
                        <input type="submit" name="view" id="rollNo" value="Print" class="university-button"/>
                    </td>

                    <td class="university-size-1-4">

                    </td>
                    <td class="university-size-1-4"></td>
                </tr>

            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>