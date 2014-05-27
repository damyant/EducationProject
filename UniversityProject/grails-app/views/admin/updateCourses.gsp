<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 21/5/14
  Time: 4:47 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Update Courses</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <g:form id="updateCoursesFrm" name="updateCoursesFrm" controller="course" action="updateCourses">
            <table class="inner university-size-full-1-1">
                <tr>
                    <td class="university-size-1-4">Course Id : </td>
                    <td class="university-size-1-4"><input type="text" class="university-size-1-1" name="courseId" id="courseId"></td>
                    <td class="university-size-1-2"><input type="button" value="Show Courses" onclick="populateCourseDetail()"/>  <div id="error" class="error5" hidden="hidden">No Course Found </div></td>
                </tr>

            </table>


            <table id="allCourseList" class="inner">
                <tbody>

                </tbody>


            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>