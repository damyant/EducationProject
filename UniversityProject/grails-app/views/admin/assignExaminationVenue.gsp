<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/11/2014
  Time: 6:26 PM
--%>
<%@ page import="examinationproject.City" contentType="text/html;charset=UTF-8" %>
<%@ page import="examinationproject.ExaminationCentre; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Assign Examination Venue</title>
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
            <tr>
                <td class="university-size-1-4"><label>Select an Examination Centre </label></td>
                <td class="university-size-1-4">
                    <g:select name="city" id="city" optionKey="id" class="university-size-1-1"
                              optionValue="cityName" from="${examinationCenterList}" noSelection="['': ' Select City']"
                              onchange="showExamVenueList()"/>
                </td>
                <td class="university-size-1-2"></td>
            </tr>
            <tr>
                <td class="university-size-1-4"><label>Select Examination Venue </label></td>
                <td class="university-size-1-4">
                    <g:select name="examinationCenter" class="university-size-1-1" id="examCenterList" from=""
                              noSelection="['': ' Select Examination Venue']"/>
                </td>
                <td class="university-size-1-2"></td>
            </tr>

        </table>

            <table style="width: 95%;margin: auto">
                <tr>
                    <th class="university-size-1-2" style="padding-left: 10px;">Y</th>
                    <th class="university-size-1-2" style="padding-left: 10px;">Z</th>
                </tr>
            </table>
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
