<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 3/6/14
  Time: 11:25 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Download Marks Foil Sheet</title>
    <g:javascript src='postExamination.js'/>
</head>

<body>
<script>
    $(document).ajaxStart(function(){
        $.blockUI({ css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 5,
            color: '#fff'
        } });
    }).ajaxStop($.unblockUI);
</script>

<div id="main">
    <fieldset class="form">
        <h3>Marks Foil Sheet Generation</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <div style="margin-left: 10px;"><label><h6>All [<span
                class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

        <table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;" border="0">

    <!----------------------------------------- Program Name --------------------------------------------->
        <tr>
            <td>Program<span class="university-obligatory">*</span></td>
            <td>
                    <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2"
                              value="${studInstance?.programDetail?.id?.get(0)}"
                              optionValue="courseName" from="${programList}" noSelection="['': ' Select Program']"
                              onchange="loadSession(this)"
                    />
            </td>
        </tr>

            <!----------------------------------------- Session Name --------------------------------------------->
            <tr>
                <td>Session<span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="session" id="session" optionKey="id" class="university-size-1-2"
                              value=""
                              optionValue="session" from="" noSelection="['': ' Select Session']"
                              onchange="loadSemester(this)"
                    />
                </td>
            </tr>

            <!----------------------------------------- Semester Name --------------------------------------------->
            <tr>
                <td>Semester<span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="programTerm" id="semesterList" optionKey="" class="university-size-1-2"
                              value=""
                              optionValue="" from="" noSelection="['': ' Select Semester']"
                              onchange="loadCourse(this)"
                    />
                </td>
            </tr>

            <!----------------------------------------- Course Name --------------------------------------------->
            <tr>
                <td>Course<span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="courseCode" id="courseCode" optionKey="id" class="university-size-1-2"
                              value=""
                              optionValue="courseCode" from="" noSelection="['': ' Select Course']"/>
                </td>
            </tr>

            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Download Marks Foil Sheet" onclick="validate()" class="university-button">
                    <input type="reset" value="Cancel" onclick="resetImage()" class="university-button">
                </td>
            </tr>


        </table>

        <table id="studyCenterTab" class="university-table-1-4">
            <thead>
            </thead>
            <tbody>
            </tbody>
        </table>
        <g:if test="${params.city}">
            <script type="text/javascript">
                $(document).ready(function () {
                    showStudyCenterList()
                })
            </script>
        </g:if>
        <div id="msgDiv"></div>
    </fieldset>
</div>
</body>
</html>