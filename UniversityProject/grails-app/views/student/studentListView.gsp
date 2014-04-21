<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/21/2014
  Time: 11:27 AM
--%>
<%@ page import="examinationproject.City; examinationproject.StudyCenter" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Students List</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
</head>

<body>
    <div id="main">
        <fieldset class="form">
            <table class="university-size-full-1-1 inner" style="margin: auto;">
                <tr>
                    <td class="university-size-1-4">
                        <label for="studyCenter">Select Study Center</label>
                    </td>
                    <td class="university-size-1-4">
                        <g:select name="studyCenter" class="university-size-1-1" id="studyCenter"
                                  from="${studyCenterList}" optionKey="id" optionValue="name"
                                  noSelection="['null': ' Select Study Center']" onchange="enableProgram(this)"/>
                    </td>
                    <td class="university-size-1-2">
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-4">
                        <label for="programId">Select Program</label>
                    </td>
                    <td class="university-size-1-4">
                        <g:select name="programId" id="programId" class="university-size-1-1" from="${programList}"
                                  optionKey="id" optionValue="courseName" noSelection="['null': ' Select Program']"
                                  onchange="getStudents()" disabled="true"/>
                    </td>
                    <td class="university-size-1-2"></td>
                </tr>
            </table>
            <table id="studentList" class="inner university-table-1-3">
                <thead></thead>
                <tbody></tbody>
            </table>
            <div id="msg"></div>
        </fieldset>
    </div>
</body>
</html>