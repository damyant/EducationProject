<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 6/11/14
  Time: 12:41 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
    <script>

    </script>
</head>

<body>
<div id="main">
    <div id="errorMsgForRollNo" class="university-status-message"></div>
    <h3>HOME ASSIGNMENT SUBMISSION</h3>
    <g:form controller="feeDetails" action="savePostExamFee" name="postExamFee" id="postExamFee">
        <table>
            <tr>
                <td><label> Enter RollNumber</label></td>
                <td><input type="text" name="rollNumberInput" id="rollNumberInput" onchange="searchHomeAssignmentByRollNumber()" ></td>
            </tr>
            <tr>
                <td><label>Course</label></td>
                <td><input type="text" name="course" id="course" readonly></td>
            </tr>
            <tr>
                <td><label>Semester/ Term</label></td>
                <td>
                    <div id="checkTerms">


                    </div>
                </td>
            </tr>
            %{--<tr>--}%
                %{--<td><label>Select Term</label></td>--}%
                %{--<td><g:checkBox name="submitAssignment" id="submitAssignment" class="many-to-one university-size-1-2" /></td>--}%
            %{--</tr>--}%
            <tr>
                <td><input type="submit" value="Save" id="saveHomeAssignment" disabled> </td>
                %{--<td><input type="text"></td>--}%
            </tr>
        </table>
    </g:form>
</div>
</body>
</html>