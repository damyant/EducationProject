<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 9/6/14
  Time: 3:02 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Marks Entering Interface</title>
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
        <h3>Marks Entering Interface</h3>
        <g:form name="studentMarksForm" id="studentMarksForm">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
            <g:hiddenField name="btn"  id="btn" value=""/>
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
                                  value=""
                                  optionValue="courseName" from="${programList}" noSelection="['': ' Select Program']"
                                  onchange="getTabulatorSession(this)"
                        />
                    </td>
                </tr>

                <!----------------------------------------- Session Name --------------------------------------------->
                <tr>
                    <td>Program Session<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="SessionList" id="SessionList" optionKey="id" class="university-size-1-2"
                        value="" optionValue="session" from="" noSelection="['': ' Select Session']" disabled="true"  onchange="getTabulatorSemester(this)" />
                    </td>
                </tr>


                <!----------------------------------------- Semester Name --------------------------------------------->
                <tr>
                    <td>Semester<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="programTerm" id="semesterList" optionKey="" class="university-size-1-2" disabled="true"
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
                                  value="" optionValue="courseCode" from="" noSelection="['': ' Select Course']" disabled="true" onchange="loadMarksType(this),enableMarksType()"/>
                    </td>
                </tr>

                <tr>
                    <td>Marks Type<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="marksType" id="marksType" optionKey="" class="university-size-1-2"
                        value="" optionValue="" from="" noSelection="['': ' Select Marks Type']" disabled="true" onchange="enableButton()"/>

                    </td>
                </tr>

            </table>

            <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1">
                <input type="button" value="Show Students" id="showButton" onclick="populateStudentListForMarks()" class="ui-button university-size-1-4" style="margin: auto;" disabled="true">
                <input type="button" value="Set" id="setButton" onclick="disableAllSelectBox()" class="ui-button university-size-1-4" style="margin: auto;" disabled="true">
                <input type="button" value="Reset" id="resetButton" onclick="enableAllSelectBox()" class="ui-button university-size-1-4" style="margin: auto;" disabled="true">
            </div>

            <table class="inner" id="dataTable" style="visibility: hidden">
                <tr>
                    <td>List of Roll Numbers</td>
                    <td style="text-align: center">
                        <g:select name="rollNoList" id="rollNoList" optionKey="id" class="university-size-1-3" value="" optionValue="" from=""  />
                    </td>
                </tr>

                <tr>
                    <td>Enter Marks</td>
                    <td class="university-size-3-4" style="text-align: center">
                        <input type="text" class="university-size-1-3" id="marksValue" name="marksValue" maxlength="2" onkeypress="return isNumber(event)" onkeyup="matchMarks()"/>
                    </td>
                </tr>
            </table>

            <div style="text-align: center; margin: 10px auto;visibility: hidden;" id="buttonDiv" class="university-size-full-1-1">
                <input type="button" value="Save Data" class="ui-button university-size-1-4" style="margin: auto; " onclick="saveMarks()">
            </div>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>