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
            var ab = 0
            ab = $('#courseId').val()
            $(window).bind("load", function () {

                viewCourseInfo("${courseDetail}")
            })

        </script>
    </head>

    <body>
        <div id="main">
            <fieldset class="form">
                <g:if test="${flash.message}">
                    <div class="message"><div class="university-status-message">${flash.message}</div></div>
                </g:if>

                <div id="statusMessage" style="display:none;" class="university-status-message"><g:message
                        code="course.create.message"/></div>

                <g:form method="post" name="createCourse" id="createCourse">
                    <g:hiddenField name="courseId" id="courseId"/>
                    <table class="inner" style="width: 100%;margin: auto;">
                        <tr>
                            <td style="width: 40%;"><label style="padding-left: 8px;">Course Name</label></td>
                            <td style="width: 60%"><label id="courseName"></label></td>
                        </tr>
                        <tr>
                            <td><label style="padding-left: 8px;">Mode</label></td>
                            <td style="width: 60%"><label id="modeName"></label></td>
                        </tr>
                        <tr>
                            <td><label style="padding-left: 8px;">Course Type</label></td>
                            <td style="width: 60%"><label id="courseTypeName"></label></td>
                        </tr>

                        <tr>
                            <td><label style="padding-left: 8px;">Number of Terms/Semesters</label></td>
                            <td style="width: 60%"><label id="noOfTerms"></label></td>
                        </tr>
                        <tr>
                            <td><label style="padding-left: 8px;">Course Code</label></td>
                            <td style="width: 60%"><label id="courseCode"></label></td>
                        </tr>
                        <tr>
                            <td><label style="padding-left: 8px;">Number of maximum available academic year</label></td>
                            <td style="width: 60%"><label id="noOfAcademicYears"></label></td>
                        </tr>

                        <tr>
                            <td><label style="padding-left: 8px;">Number of papers</label></td>
                            <td style="width: 60%"><label id="noOfPapers"></label></td>
                        </tr>
                        <tr>
                            <td><label style="padding-left: 8px;">Total Marks</label></td>
                            <td style="width: 60%"><label id="totalMarks"></label></td>
                        </tr>
                        <tr>
                            <td><label style="padding-left: 8px;">Pass Marks(per paper)</label></td>
                            <td style="width: 60%"><label id="marksPerPaper"></label></td>
                        </tr>
                        <tr>
                            <td><label style="padding-left: 8px;">Total Credit Points</label></td>
                            <td style="width: 60%"><label id="totalCreditPoints"></label></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table id="multiSelectTab" style="border: 0px;">
                                    %{--<tr id="Subjects">--}%
                                    %{--</tr>--}%
                                    %{--<tr><td></td></tr>--}%

                                    %{--<tr>--}%
                                    %{--<td style="text-align: center" colspan="2"><input type="button" value="Back" class="university-button"  onClick="history.go(-1);return true;" ></td>--}%
                                    %{--</tr>--}%
                                </table>
                            </td>
                        </tr>
                    </table>

                </g:form>
            </fieldset>
        </div>
    </body>
</html>