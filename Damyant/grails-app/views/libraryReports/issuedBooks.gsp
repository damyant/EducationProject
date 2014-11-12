<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 10/6/14
  Time: 1:05 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='bookIssue.js'/>
</head>

<body>


<div id="main">
    <fieldset class="form">

            <table class="university-size-full-1-1 inner spinner">

                <tr>

                    <td class="university-size-1-3">StudentID/ FacultyID<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="studentId" id="textBoxId" class="university-size-1-2"></td>
                </tr>
                %{--<tr>--}%
                    %{--<td></td><td>OR</td>--}%
                %{--</tr>--}%
                %{--<tr>--}%

                    %{--<td class="university-size-1-3">Faculty ID<span class="university-obligatory">*</span></td>--}%
                    %{--<td class="university-size-1-3"><input type="text" name="employeeId" class="university-size-1-2" ></td>--}%
                %{--</tr>--}%
                <tr>
                    <td></td>
                    <td>
                        <input type="button" class="university-size-1-2 university-button" value="Show" onclick="showIssuedBooks()"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        <table class="university-size-full-1-1 inner spinner" id="bookList">
            <div id="errorMsg" class="university-status-message"></div>

        </table>

    </fieldset>
</div>


</body>
</html>