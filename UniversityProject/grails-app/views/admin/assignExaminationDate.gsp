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
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <table class="inner" style="width: 95%;margin: auto">
            <tr>
                <td class="university-size-1-4"><label>Select a Course</label></td>
                <td class="university-size-1-4">
                    <g:select name="programList" class="university-size-1-1" optionKey="id"
                              optionValue="courseName"
                              from="${programList}" noSelection="['': ' Select Program']"
                              onchange="getSemesterAndSubjectList()"/>
                </td>
                <td class="university-size-1-2"></td>
            </tr>
        </table>
        <% def count = 3 %>
        <% def count1 = 4 %>
        <g:each in="${(1..count).toList()}" var="c">
            <label><h3>Semester-${c}</h3></label>
            <table style="width: 95%;margin: auto">
                <tr>
                    <th class="university-size-1-2" style="padding-left: 10px;">Subject</th>
                    <th class="university-size-1-2" style="padding-left: 10px;">Examination Date</th>
                </tr>
            <g:each in="${(1..count1).toList()}" var="d">
                <tr>
                    <td style="padding-left: 10px;">${d}</td>
                    <td style="padding-left: 10px;"><input type="text" class="datepicker university-size-1-2"></td>
                </tr>
            </g:each>
            </table>
        </g:each>
    </fieldset>
</div>
<script>
    $(function() {
        $(".datepicker").datepicker({
            changeMonth: true,
            changeYear: true,
            dateFormat: "mm/dd/yy"
        });
    });
</script>
</body>
</html>
