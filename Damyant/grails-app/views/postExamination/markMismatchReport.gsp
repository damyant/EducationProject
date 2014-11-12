<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 3/6/14
  Time: 11:25 AM
--%>

<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Marks Mismatch Report</title>
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
        <h3>Marks Miss Match Report</h3>
        <g:form name="marksMissMatchForm" id="marksMissMatchForm" controller="postExamination" action="marksMissMatchData">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>

            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;" border="0">

                <!----------------------------------------- Program Name --------------------------------------------->
                <tr>
                    <td>Program<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2"
                               optionValue="courseName" from="${ProgramDetail.list(sort: 'courseCode')}" noSelection="['': ' Select Program']"
                                  onchange="loadSessionForMissMatch(this)" />
                    </td>
                </tr>

                <!----------------------------------------- Session Name --------------------------------------------->
                <tr>
                    <td>Program Session<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="session" id="session" optionKey="id" class="university-size-1-2" disabled="true"
                                 optionValue="session" from="" noSelection="['': ' Select Program Session']"
                                  onchange="loadSemesterForMissMatch()"/>
                    </td>
                </tr>

                <!----------------------------------------- Semester Name --------------------------------------------->
                <tr>
                    <td>Semester<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="programTerm" id="semesterList" optionKey="" class="university-size-1-2" disabled="true"
                                  optionValue="" from="" noSelection="['': ' Select Semester']" onchange="enableSession()"/>
                    </td>
                </tr>

                <tr>
                    <td>Student Session<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="studentSession" id="studentSession" optionKey="" class="university-size-1-2" disabled="true"
                                  optionValue="" from="" noSelection="['': ' Select Student Session']" onchange="enableButtonOfMissMatch()" />
                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: center">
                        <input type="button" id="missMatchedButton" value="Download Miss-Match Report" onclick="validate()" class="university-button" disabled="true">
                        <input type="button" id="resetButton" value="Cancel" onclick="resetImage()" class="university-button" disabled="true">
                    </td>
                </tr>


            </table>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>