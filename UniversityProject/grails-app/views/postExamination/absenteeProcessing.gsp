<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 11/6/14
  Time: 1:34 PM
--%>

<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Absentee Processing</title>
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
        <h3>Absentee Processing</h3>
        <g:form name="" id="" controller="postExamination" action="">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>

            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;" border="1">

                <!----------------------------------------- Program Name --------------------------------------------->
                <tr>
                    <td>Exam Venue<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="examVenue" id="examVenue" optionKey="id" class="university-size-1-2"
                                  value=""
                                  optionValue="" from="" noSelection="['': ' Select Exam Venue']"
                        />
                    </td>
                </tr>

                <!----------------------------------------- Program Name --------------------------------------------->
                <tr>
                    <td>Program<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2"
                                  value=""
                                  optionValue="courseName" from="${ProgramDetail.list(sort: 'courseCode')}" noSelection="['': ' Select Program']"
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

                %{--<table class="inner" id="dataTable" style="visibility: hidden">--}%
                <tr>
                    %{--<td>All Roll Numbers<span class="university-obligatory">*</span></td>--}%

                    <td style="text-align: center">
                All Roll Numbers
                        <g:select name="selectBox" id="selectBox" optionKey="id" class="university-size-1-3" value="" optionValue="" from=""  multiple="true" />
                    </td>

                    <td style="text-align: center">
                        Absentee Roll Numbers
                        <g:select name="selectBox" id="selectBox" optionKey="id" class="university-size-1-3" value="" optionValue="" from=""  multiple="true" />
                    </td>
                </tr>

                %{--<tr>--}%
                    %{--<td>Absentee Roll Numbers<span class="university-obligatory">*</span></td>--}%
                    %{--Absentee Roll Numbers--}%
                    %{--<td style="text-align: center">--}%
                        %{--<g:select name="selectBox" id="selectBox" optionKey="id" class="university-size-1-3" value="" optionValue="" from=""  multiple="true" />--}%
                    %{--</td>--}%
                %{--</tr>--}%


                <tr>
                    <td colspan="2" style="text-align: center">
                        <input type="button" value="Save" onclick="validate()" class="university-button">
                        <input type="reset" value="Cancel" onclick="resetImage()" class="university-button">
                    </td>
                </tr>


            </table>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>