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
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'multiselectable.css')}" type='text/css'>
</head>
<body>
<div id="main">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>

    <g:form controller="course" action="saveCourse" method="post" name="createCourse" id="createCourse">
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
                <td><g:select name="courseType" id="mode" optionKey="id" optionValue="courseTypeName" class="university-size-1-2" from="${CourseType.findAll()}" noSelection="['':' Select Course Type']" /></td>
            </tr>

            <tr>
                <td><label>Number of Terms/Semesters </label></td>
                <td><input type="text" id="terms" name="" maxlength="" class="university-size-1-2" onblur="showSelect()"/></td>
            </tr>
            <tr>
                <td><label>Course Code</label></td>
                <td><input type="text" name="courseCode" maxlength="" class="university-size-1-2" /></td>
            </tr>
            <tr>
                <td><label>Number of maximum available academic year</label></td>
                <td><input type="text" name="noOfAcademicYears" maxlength="" class="university-size-1-2" /></td>
            </tr>

            <tr>
                <td><label>	Number of papers</label></td>
                <td><input type="text" name="noOfPapers" maxlength="" class="university-size-1-2" /></td>
            </tr>
            <tr>
                <td><label>Total Marks</label></td>
                <td><input type="text" name="totalMarks" maxlength="" class="university-size-1-2" /></td>
            </tr>
            <tr>
                <td><label>Pass Marks(per paper)</label></td>
                <td><input type="text" name="" class="university-size-1-2" /></td>
            </tr>
            <tr>
                <td><label>Total Credit Points</label></td>
                <td><input type="text" name="totalCreditPoints" class="university-size-1-2"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div id="multiSelectDiv"></div>
                    <div id="mainDiv"></div>
                </td>
            </tr>
            <tr>
                <td><input type="submit" value="<g:message code="default.button.create"/>"  class="university-button" onclick="validate()"></td>
                <td><input type="reset" value="<g:message code="default.button.clear"/>" class="university-button" ></td>
            </tr>
        </table>
    </g:form>
</div>
</body>
</html>