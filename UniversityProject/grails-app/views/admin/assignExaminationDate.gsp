<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/11/2014
  Time: 4:57 PM
--%>
<%@ page import="examinationproject.ExaminationCentre; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Assign Examination Date</title>
    <g:javascript src='admin.js'/>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <form id="assignDate">
        <table class="inner" style="width: 95%;margin: auto">
            <tr>
                <td class="university-size-1-4"><label>Select a Course</label></td>
                <td class="university-size-1-4">
                    <g:select name="programList" id="programList" class="university-size-1-1" optionKey="id"
                              optionValue="courseName"
                              from="${programList}" noSelection="['': ' Select Program']"
                              onchange="getSemesterAndSubjectList()"/>
                </td>
                <td class="university-size-1-2"></td>
            </tr>
        </table>

            <table style="width: 95%;margin: auto" id="subjectList">

            </table>
            <div id="msgDiv"></div>
        </form>
    </fieldset>
</div>

</body>
</html>
