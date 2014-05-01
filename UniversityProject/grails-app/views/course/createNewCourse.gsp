<%@ page import="examinationproject.Subject; examinationproject.CourseType; examinationproject.CourseMode" %>
<%--
  Created by IntelliJ IDEA.
  User: Sonali P gupta
  Date: 2/7/14
  Time: 10:37 AM
--%>


<html>
<head>
    <title>Create New Program</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'multiselectable.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'fileUpload.js')}"></script>
     <script type="text/javascript">

        makeJson("${subjList}")

      if(${updateFlag}){
          %{--alert(${updateFlag})--}%
        $(window).bind("load",function(){

            updateInfo("${courseDetail}")
        })
      }
        </script>
</head>
<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${params.courseId}">
            <h3>Update Program</h3>
        </g:if>
        <g:else>
            <h3>Add New Program</h3>
        </g:else>

        <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
    </g:if>

    <div id="statusMessage" style="display:none;" class="university-status-message">
        <g:if test="${params.courseId}">
            <g:message code="course.updated.message"/>
        </g:if>
        <g:else>
            <g:message code="course.create.message"/>
        </g:else>
    </div>

    <g:form  method="post" name="createCourse" id="createCourse">
        <g:hiddenField name="courseId" id="courseId" />
        <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
        <table class="university-table inner" border="0">
            <tr>
                <td style="width: 40%"><label>Program Name :<span class="university-obligatory">*</span></label></td>
                <td style="width: 60%"><input type="text" id="courseName" name="courseName" maxlength="" class="university-size-1-2" onkeypress="return onlyAlphabetsWithSplChar(event);"/></td>

            </tr>
            <tr>
                <td><label>Select Mode :<span class="university-obligatory">*</span></label></td>
                <td><g:select name="courseMode" id="modeName" optionKey="id" optionValue="modeName" onchange="enableNoOfSem(this)" class="university-size-1-2" from="${CourseMode.findAll()}" noSelection="['':' Select Mode']" /></td>
            </tr>
            <tr>
                <td><label>Select Program Type :<span class="university-obligatory">*</span></label></td>
                <td><g:select name="courseType" id="courseTypeName" optionKey="id" optionValue="courseTypeName" class="university-size-1-2" from="${CourseType.findAll()}" noSelection="['':' Select Program Type']" /></td>
            </tr>

            <tr>
                <td><label>Number of Terms/Semesters :<span class="university-obligatory">*</span></label></td>
                <td><input type="text" id="noOfTerms" name="noOfTerms" maxlength="" class="university-size-1-2" onkeypress="return isNumber(event)" onblur="semesterList()" readonly/></td>

            </tr>
            <tr>
                <td><label>Program Code :<span class="university-obligatory">*</span></label></td>
                <td><input type="text" id="courseCode" name="courseCode" maxlength="" onchange="checkCourseCode()" class="university-size-1-2" onkeypress="return isNumber(event)"/>
                    <label id="errorMsg" class="error1"></label>
                </td>
            </tr>
            <tr>
                <td><label>Number of maximum available academic year :<span class="university-obligatory">*</span>
                </label></td>
                <td><input type="text" id="noOfAcademicYears" name="noOfAcademicYears" maxlength="" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>

            <tr>
                <td><label>Number of papers :<span class="university-obligatory">*</span></label></td>
                <td><input type="text" id="noOfPapers" name="noOfPapers" maxlength="" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td><label>Total Marks :<span class="university-obligatory">*</span></label></td>
                <td><input type="text" id="totalMarks" name="totalMarks" maxlength="" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td><label>Pass Marks(per paper) :<span class="university-obligatory">*</span></label></td>
                <td><input type="text" id="marksPerPaper" name="marksPerPaper" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td><label>Total Credit Points :<span class="university-obligatory">*</span></label></td>
                <td><input type="text" id="totalCreditPoints" name="totalCreditPoints" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table id="multiSelectTab" name="multiSelectTab">
                    <tr>

                    </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center"><input type="button" id="submitForm" onclick="save();" value="<g:message code="default.button.create"/>" class="university-button">
                <input id="clear" onclick="clearField()" type="reset" value="<g:message code="default.button.clear"/>" class="university-button" ></td>
            </tr>
        </table>
    </g:form>
        %{--<script type="text/javascript">--}%
            %{--$(function() {--}%
                %{--$("#dropbox, #multiple").html5Uploader({--}%
                    %{--name: "foo",--}%
                    %{--postUrl: "bar.aspx"--}%
                %{--});--}%
            %{--});--}%
        %{--</script>--}%
       <g:uploadForm controller="course" action="uploadSyllabus" method="post" enctype="multipart/form-data" name="syllabusUploadForm" id="syllabusUploadForm">
            <input type="file" name="syllabusFile" id="syllabusFile" style="visibility: hidden;">
            <input type="hidden" name="syllabusCourse" id="syllabusCourse" value="">
            <input type="hidden" name="syllabusOfSubject" id="syllabusOfSubject" value="">
            <input type="hidden" name="syllabusOfSemester" id="syllabusOfSemester" value=="">
        </g:uploadForm>
        %{--<input class="multi" accept="pdf"/>--}%

    </fieldset>
</div>
</body>
</html>