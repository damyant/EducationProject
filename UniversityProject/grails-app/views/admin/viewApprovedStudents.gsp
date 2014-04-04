<%--
  Created by IntelliJ IDEA.
  User: sonali
  Date: 27/3/14
  Time: 5:53 PM
--%>


<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>View Provisional Students</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
    %{--<h3>Approved Student List</h3>--}%
        <g:form controller="admin" action="generateRollNo" id="generateRollNo" name="generateRollNo">
            <g:hiddenField name="studentId" id="studentId"/>
            <g:hiddenField name="pageType" id="pageType" value="Approve RollNo"/>
            <table class="inner" style="margin: auto;">
                <tr>
                    <td style="min-width: 12%">
                        <label for="studyCenter">Select Study Center</label>
                    </td>
                    <td style="width: 33%">
                        <g:select name="studyCenter" class="university-size-1-2" id="studyCenter"
                                  from="${studyCenterList}" optionKey="id" optionValue="name"
                                  noSelection="['null': ' Select Study Center']" onchange="enableProgram(this)"/>
                    </td>
                    <td style="min-width: 10%">
                        <label for="programId">Select Program</label>
                    </td>
                    <td style="width: 33%">
                        <g:select name="programId" id="programId" class="university-size-1-2" from="${programList}"
                                  optionKey="id" optionValue="courseName" noSelection="['null': ' Select Program']"
                                  onchange="getStudents()" disabled="true"/>
                    </td>
                    <td style="width: 10%"></td>
                </tr>
            </table>
            <table id="studentList" class="inner university-table-1-3">
                <thead></thead>
                <tbody></tbody>
            </table>
        </g:form>
        <div id="msg"></div>
    </fieldset>
</div>
</body>
</html>