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

</script>

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
        <g:form name="absenteeProcess" id="absenteeProcess" controller="postExamination" action="saveAbsentee">

            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;" border="0">

                <!----------------------------------------- Program Name --------------------------------------------->
                <tr>
                    <td>Exam Venue<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="examVenue" id="examVenue" optionKey="id" class="university-size-1-2"
                                  value="id" optionValue="name" from="${examVenueList}" onchange="loadVenueProgram(this)" noSelection="['': ' Select Exam Venue']"/>
                    </td>
                </tr>

                <!----------------------------------------- Program Name --------------------------------------------->
                <tr>
                    <td>Program<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="programId" id="programId" optionKey="" class="university-size-1-2"
                                  value=""
                                  optionValue="" from="" noSelection="['': ' Select Program']"
                                  onchange="getTabulatorSession(this)"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Program Session<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="SessionList" id="SessionList" optionKey="id" class="university-size-1-2"
                                  value="" optionValue="session" from="" noSelection="['': ' Select Session']" disabled="true"  onchange="loadSemester(this)" />
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
                                  value="" optionValue="courseCode" from="" noSelection="['': ' Select Course']" disabled="true" onchange="loadMarksType(this),enableMarksType()" />
                    </td>
                </tr>

                <tr>
                    <td>Marks Type<span class="university-obligatory">*</span></td>
                    <td>
                        <g:select name="marksType" id="marksType" optionKey="" class="university-size-1-2"
                                  value="" optionValue="" from="" noSelection="['': ' Select Marks Type']" disabled="true" onchange="enableLoadAbsentee()"/>

                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="button" disabled id="loadAbsentee" value="Load Roll Numbers" class="university-size-1-4 university-button"onclick="loadAbsenteeRollNo()"/>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: center">
                        <div style="margin: 10px;">All Roll Number</div>
                    <div  style="line-height: 50px;">
                        <select id='canselect_code' name='canselect_code' disabled multiple class="university-size-1-3">

                        </select>
                        <input type='button' id="add" style="vertical-align: middle;margin-top: -40px; border-radius: 6px;" disabled id='btnRight_code' value='==>>' class="university-button" onclick="valueInBox()"/>
                    </div>
                    </td>
                    <td style="text-align: center">
                        <div style="margin: 10px;">Absentee Roll Number</div>
                    <div style="line-height: 50px;">
                        <input type='button' id="remove" style="vertical-align:middle;margin-top: -40px;border-radius: 6px;" id='btnLeft_code' disabled value='<<==' class="university-button" onclick="valueOutBox()"/>
                        <select id='isselect_code' disabled name='isselect_code' multiple class="university-size-1-3">

                        </select>
                        <input type="hidden" id="studentAddedList" value="">
                    </div>
                    </td>
                </tr>

                <tr>
                    <td colspan="2" style="text-align: center">
                        <input type="submit" disabled id="absenteeSave" value="Save" onclick="validate()" class="university-button">
                        <input type="reset" disabled id="absenteeCancel" value="Cancel" onclick="resetImage()" class="university-button">
                    </td>
                </tr>


            </table>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>