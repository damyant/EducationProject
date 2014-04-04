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
    <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
    </g:if>

    <div id="statusMessage" style="display:none;" class="university-status-message"><g:message code="course.create.message"/></div>

    <g:form  method="post" name="createCourse" id="createCourse">
        <g:hiddenField name="courseId" id="courseId" />
        <table class="university-table" border="0">
            <tr>
                <td style="width: 40%"><label>Course Name<span style="color: red">*</span></label></td>
                <td style="width: 60%"><input type="text" id="courseName" name="courseName" maxlength="" class="university-size-1-2" onkeypress="return onlyAlphabets(event,this);"/></td>
            </tr>
            <tr>
                <td><label> Select Mode <span style="color: red">*</span></label></td>
                <td><g:select name="courseMode" id="modeName" optionKey="id" optionValue="modeName" class="university-size-1-2" from="${CourseMode.findAll()}" noSelection="['':' Select Mode']" /></td>
            </tr>
            <tr>
                <td><label> Select Course Type <span style="color: red">*</span></label></td>
                <td><g:select name="courseType" id="courseTypeName" optionKey="id" optionValue="courseTypeName" class="university-size-1-2" from="${CourseType.findAll()}" noSelection="['':' Select Course Type']" /></td>
            </tr>

            <tr>
                <td><label>Number of Terms/Semesters <span style="color: red">*</span></label></td>
                <td><input type="text" id="noOfTerms" name="noOfTerms" maxlength="" class="university-size-1-2" onkeypress="return isNumber(event)" onblur="semesterList()"/></td>
            </tr>
            <tr>
                <td><label>Course Code <span style="color: red">*</span></label></td>
                <td><input type="text" id="courseCode" name="courseCode" maxlength="" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td><label>Number of maximum available academic year <span style="color: red">*</span></label></td>
                <td><input type="text" id="noOfAcademicYears" name="noOfAcademicYears" maxlength="" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>

            <tr>
                <td><label>	Number of papers <span style="color: red">*</span></label></td>
                <td><input type="text" id="noOfPapers" name="noOfPapers" maxlength="" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td><label>Total Marks <span style="color: red">*</span></label></td>
                <td><input type="text" id="totalMarks" name="totalMarks" maxlength="" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td><label>Pass Marks(per paper) <span style="color: red">*</span></label></td>
                <td><input type="text" id="marksPerPaper" name="marksPerPaper" class="university-size-1-2" onkeypress="return isNumber(event)"/></td>
            </tr>
            <tr>
                <td><label>Total Credit Points <span style="color: red">*</span></label></td>
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
</div>
</body>
</html>