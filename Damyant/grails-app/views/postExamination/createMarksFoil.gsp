<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 3/6/14
  Time: 11:25 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

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
        <g:form name="marksFoilId" id="marksFoilId" controller="postExamination" action="generateMarksFoilSheet">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
            <g:hiddenField name="btn"  id="btn" value=""/>

        <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

        <table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;" border="0">

    <!----------------------------------------- Program Name --------------------------------------------->
        <tr>
            <td>Program<span class="university-obligatory">*</span></td>
            <td>
                    <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2"
                              value="${studInstance?.programDetail?.id?.get(0)}"
                              optionValue="courseName" from="${programList.programDetailId}" noSelection="['': ' Select Program']" onchange="getSemester(this)"/>
            </td>
        </tr>

            <!----------------------------------------- Session Name --------------------------------------------->
            <tr>
                <td>Session<span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="programSessionId" id="SessionList" optionKey="id" class="university-size-1-2" disabled="true"
                            value="" optionValue="session" from="" noSelection="['': ' Select Session']" onchange="loadSemester(this)"/>
                </td>
            </tr>

            <tr>
                <td>Semester<span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="semester" id="semesterList" optionKey="" class="university-size-1-2" disabled="true"
                              value="" optionValue="" from="" noSelection="['': ' Select Semester']" onchange="loadGroup()" />
                </td>
            </tr>

            <tr>
                <td>Group<span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="groupId" id="groupList" optionKey="" class="university-size-1-2"
                              value="" optionValue="" from="" noSelection="['': ' Select Group']" onchange="loadCourse()" disabled="true" />
                </td>
            </tr>

            <!----------------------------------------- Course Name --------------------------------------------->
            <tr>
                <td>Course<span class="university-obligatory">*</span></td>
                <td>
                    <g:select name="courseCode" id="courseCode" optionKey="id" class="university-size-1-2"
                              value="" optionValue="courseCode" from="" noSelection="['': ' Select Course']" disabled="true" onchange="enableMarksType()"/>
                </td>
            </tr>

            <tr>
                <td colspan="2" align="center">
                    <input type="button" id="pdfButton" value="Download Marks in pdf" onclick="validate()" class="university-button" tag="1" disabled="true">
                    <input type="button" id="excelButton" value="Download Marks in excel" onclick="validate()" class="university-button" tag="2" disabled="true">
                    <input type="button"  id="cancelButton"value="Reset" onclick="resetData()" class="university-button" disabled="true">
                </td>
            </tr>


        </table>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>