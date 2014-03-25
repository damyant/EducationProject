<%@ page import="examinationproject.Subject; examinationproject.CourseType; examinationproject.CourseMode" %>
<%--
  Created by IntelliJ IDEA.
  User: Sonali P gupta
  Date: 2/7/14
  Time: 10:37 AM
--%>


<html>
<head>
    <title>Create New Course</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'multiselectable.js')}"></script>
     <script type="text/javascript">

        makeJson("${subjList}")


        </script>
</head>
<body>
<div id="main">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

    <div id="statusMessage" style="display:none;" class="university-status-message"><g:message code="course.create.message"/></div>

    <g:form  method="post" name="createCourse" id="createCourse">
        %{--<g:hiddenField name="subList" id="subList" value="${subjList}"/>--}%
        <table class="university-table">
            <tr>
                <td><label>Course Name</label></td>
                <td><input type="text" id="courseName" name="courseName" maxlength="" class="university-size-1-2" /></td>
            </tr>
            <tr>
                <td><label> Select Mode </label></td>
                <td><g:select name="courseMode" id="mode" optionKey="id" optionValue="modeName" class="university-size-1-2" from="${CourseMode.findAll()}" noSelection="['':' Select Mode']" /></td>
            </tr>
            <tr>
                <td><label> Select Course Type </label></td>
                <td><g:select name="courseType" id="type" optionKey="id" optionValue="courseTypeName" class="university-size-1-2" from="${CourseType.findAll()}" noSelection="['':' Select Course Type']" /></td>
            </tr>

            <tr>
                <td><label>Number of Terms/Semesters </label></td>
                <td><input type="text" id="terms" name="noOfTerms" onkeypress="return isNumber(event)" maxlength="" class="university-size-1-2" onblur="semesterList()"/></td>
            </tr>
            <tr>
                <td><label>Course Code</label></td>
                <td><input type="text" name="courseCode" maxlength=""onkeypress="return isNumber(event)" class="university-size-1-2" /></td>
            </tr>
            <tr>
                <td><label>Number of maximum available academic year</label></td>
                <td><input type="text" name="noOfAcademicYears" maxlength="" onkeypress="return isNumber(event)"class="university-size-1-2" /></td>
            </tr>

            <tr>
                <td><label>	Number of papers</label></td>
                <td><input type="text" name="noOfPapers" maxlength=""onkeypress="return isNumber(event)" class="university-size-1-2" /></td>
            </tr>
            <tr>
                <td><label>Total Marks</label></td>
                <td><input type="text" name="totalMarks" maxlength=""onkeypress="return isNumber(event)" class="university-size-1-2" /></td>
            </tr>
            <tr>
                <td><label>Pass Marks(per paper)</label></td>
                <td><input type="text" name="passMarks" onkeypress="return isNumber(event)"class="university-size-1-2" /></td>
            </tr>
            <tr>
                <td><label>Total Credit Points</label></td>
                <td><input type="text" name="totalCreditPoints"onkeypress="return isNumber(event)" class="university-size-1-2"/></td>
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
                <td><input type="button" id="submitForm" onclick="save();" value="<g:message code="default.button.create"/>" class="university-button"></td>
                <td><input id="clear" onclick="clearField()" type="reset" value="<g:message code="default.button.clear"/>" class="university-button" ></td>
            </tr>
        </table>
    </g:form>
</div>
</body>
</html>