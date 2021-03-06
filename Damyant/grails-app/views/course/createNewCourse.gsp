<%@ page import="examinationproject.Subject; examinationproject.CourseType; examinationproject.CourseMode" %>
<%--
  Created by IntelliJ IDEA.
  User: Sonali P gupta
  Date: 2/7/14
  Time: 10:37 AM
--%>


<html>
<head>
    <title>Create New Programme</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'multiselectable.js')}"></script>
    <script type="text/javascript">

  </script>
    <script type="text/javascript">

        %{--makeJson("${subjList}")--}%

        if (${updateFlag}) {
            %{--alert(${updateFlag})--}%
            $(window).bind("load", function () {

                updateInfo("${courseDetail}")
            })
        }

           </script>



</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${params.courseSessionId}">
            <h3>Update Programme</h3>
        </g:if>
        <g:else>
            <h3>Add New Programme</h3>
        </g:else>

        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>

        <div id="statusMessage" style="display:none;" class="university-status-message">
            <g:if test="${params.courseSessionId}">
                <g:message code="course.updated.message"/>
            </g:if>
            <g:else>
                <g:message code="course.create.message"/>

            </g:else>
        </div>

        <g:uploadForm method="post" name="createCourse" id="createCourse" enctype="multipart/form-data">
            <g:hiddenField name="courseId" id="courseId"/>
            <div style="margin-left: 10px;"><label><h6>All [<span
                    class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="university-table inner" border="0">
                <tr>
                    <td style="width: 40%"><label>Programme Name :<span class="university-obligatory">*</span></label>
                    </td>
                    <td style="width: 60%"><input type="text" id="courseName" name="courseName" maxlength="50"
                                                  class="university-size-1-2"
                                                  onkeypress="return onlyAlphabetsWithSplChar(event);"/></td>

                </tr>
                <tr>
                    <td style="width: 40%"><label>Select Session :<span class="university-obligatory">*</span></label>
                    </td>
                    <td style="width: 60%">
                        <select id="session" name="session" class="university-size-1-2" disabled >
                            <option value="0">Select Session</option>
                            <g:each in="${programSessions}" var="session">
                                <option value="${session.sessionOfProgram}">${session.sessionOfProgram}</option>
                            </g:each>
                        </select>
                        %{--<g:select from="${programSessions}"--}%
                        %{--optionKey="id"--}%
                        %{--optionValue="${{ book -> "${book.title} - ${book.isbn}" }}"--}%
                        %{--name="bookCustom"/>--}%
                    </td>

                </tr>
                <tr>
                    <td><label>Select Mode :<span class="university-obligatory">*</span></label></td>
                    <td><g:select name="courseMode" id="modeName" optionKey="id" optionValue="modeName" disabled="disabled"
                                  onchange="enableNoOfSem(this)" class="university-size-1-2"
                                  from="${CourseMode.findAll()}" noSelection="['0': ' Select Mode']"/></td>
                </tr>
                <tr>
                    <td><label>Select Programme Type :<span class="university-obligatory">*</span></label></td>
                    <td><g:select name="courseType" id="courseTypeName" optionKey="id" optionValue="courseTypeName" disabled="disabled"
                                  class="university-size-1-2" from="${CourseType.findAll()}"
                                  noSelection="['0': ' Select Programme Type']"/></td>
                </tr>
                <tr>
                    <td><label>Select Program Category :<span class="university-obligatory">*</span></label></td>
                    <td><g:select name="programType" id="programType" optionKey="id" optionValue="type"  disabled="disabled"
                                  class="university-size-1-2" from="${examinationproject.ProgramType.findAll()}"
                                  noSelection="['0': ' Select Programme Category']"/></td>
                </tr>
                <tr>
                    <td><label>Programme Code :<span class="university-obligatory">*</span></label></td>
                    %{--checkCourseCode()--}%
                    <td><input type="text" id="courseCode" name="courseCode" maxlength="10" onchange="" disabled
                               class="university-size-1-2" onkeypress="return isNumber(event)"/>
                        <label id="errorMsg" class="error1"></label>
                    </td>
                </tr>
                <tr>
                    <td><label>Number of Terms/Semesters :<span class="university-obligatory">*</span></label></td>
                    <td><input type="text" id="noOfTerms" name="noOfTerms" maxlength="2" class="university-size-1-2" disabled
                               onkeypress="return isNumber(event)" onblur="semesterList(this)" readonly/></td>

                </tr>

                <tr>
                    <td><label>Number of maximum available academic year :<span class="university-obligatory">*</span>
                    </label></td>
                    <td><input type="text" id="noOfAcademicYears" name="noOfAcademicYears" maxlength="10"
                               class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
                </tr>

                <tr>
                    <td><label>Number of papers :<span class="university-obligatory">*</span></label></td>
                    <td><input type="text" id="noOfPapers" name="noOfPapers" maxlength="5" class="university-size-1-2"
                               onkeypress="return isNumber(event)"/></td>
                </tr>
                <tr>
                    <td><label>Total Marks :<span class="university-obligatory">*</span></label></td>
                    <td><input type="text" id="totalMarks" name="totalMarks" maxlength="5" class="university-size-1-2"
                               onkeypress="return isNumber(event)"/></td>
                </tr>
                %{--<tr>--}%
                    %{--<td><label>Pass Marks(per paper) :<span class="university-obligatory">*</span></label></td>--}%
                    %{--<td><input type="text" id="marksPerPaper" name="marksPerPaper" class="university-size-1-2" maxlength="5"--}%
                               %{--onkeypress="return isNumber(event)"/></td>--}%
                %{--</tr>--}%
                <tr>
                    <td><label>Total Credit Points :<span class="university-obligatory">*</span></label></td>
                    <td><input type="text" id="totalCreditPoints" name="totalCreditPoints" class="university-size-1-2" maxlength="5"
                               onkeypress="return isNumber(event)"/>

                    </td>
                <tr><label id="worningMsg" class="university-status-message"></label></tr>

                </tr>
                <tr>
                    <td colspan="2">
                        <table id="multiSelectTab" name="multiSelectTab">
                            <tr>

                            </tr>
                        </table>
                    </td>
                </tr>
                %{--<tr>--}%
                %{--<td><label>Upload Syllabus :</label></td>--}%
                %{--<td><input type="file" id="uploadSyllabus" name="uploadSyllabus" class="university-size-1-2" onchange="checkFileType(event)"/></td>--}%
                %{--</tr>--}%
                <tr>
                    <td colspan="2" style="text-align: center">
                        <g:if test="${params.courseSessionId}">
                            <input type="button" id="submitForm" onclick="save();"
                                   value="<g:message code="default.button.update"/>" class="university-button"/>
                            <a href="/UniversityProject/course/listOfCourses?type=update" > <input type="button" class="university-button"  value="Back" /></a>
                        </g:if>
                        <g:else>
                            <input type="button" id="submitForm" onclick="save();"
                                   value="<g:message code="default.button.create"/>" class="university-button">
                            <input id="clear" onclick="clearField()" type="reset"
                                   value="<g:message code="default.button.clear"/>" class="university-button">
                        </g:else>
                    </td>

                </tr>
            </table>

            %{--<div id="groupDialog" class="dialog" hidden="hidden">--}%
                %{--<g:form method="post" name="groupsOfSubject" id="groupsOfSubject" enctype="multipart/form-data">--}%
                %{--<input id="addGroup" onclick="addGroups()" type="reset"--}%
                       %{--value="Add Subject Groups"  class="university-button">--}%
                %{--<table id="subjectGroup" name="subjectGroup">--}%
                    %{--<tr>--}%

                    %{--</tr>--}%
                    %{--<input type="button" value="Save Groups" onclick="saveSubjectGroup()" >--}%
                %{--</table>--}%
                %{--</g:form>--}%
            %{--</div>--}%
        </g:uploadForm>
    </fieldset>
</div>
</body>
</html>