<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 30/4/14
  Time: 11:18 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Printable Report</title>
    %{--<meta name="layout" content="/main">--}%
</head>

<body>
<div id="main">

    <g:if test="${totalListBySession}">
        <h3> Total Students In All Courses For ${sessionVal} Session</h3>
        <table style="width: 50%; text-align: center; margin-left: 5%">
            <th>Course Name</th>
            <th>No. Of Students</th>
        <g:each in="${totalListBySession}" var="item">
            <tr style="border: 1px solid black">
                <td style="border: 1px solid black">${item.key}</td>
                <td style="border: 1px solid black">${item.value}</td>
            </tr>
        </g:each>
    </g:if>

    <g:if test="${totalListByCourse}">
        <h3> Total Students In ${totalListByCourse.getAt(0).programDetail[0].courseName} For ${courseSession} Session</h3>
    </g:if>
    <g:if test="${totalListByCourse}">
        <table style=" text-align: center; margin-left: 5%">
            <th>Roll No</th>
            <th>Name</th>
            <th>Study Centre</th>
            <th>Examination Centre</th>
            <th>Mobile No.</th>
        <g:each in="${totalListByCourse}" var="student">
            <tr style="border: 1px solid black">
                <td style="border: 1px solid black">${student.rollNo}</td>
                <td style="border: 1px solid black">${student.studentName}</td>
                <td style="border: 1px solid black">${student.studyCentre[0].name}</td>
                <td style="border: 1px solid black">${student.examinationCentre[0].name}</td>
                <td style="border: 1px solid black">${student.mobileNo}</td>
            </tr>
        </g:each>
      </table>
    </g:if>
    <g:if test="${totalListByStudyCentre}">
        <h3> Total Students In All Courses For ${studyCentreSession} Session</h3>
        <table style="width: 50%; text-align: center; margin-left: 5%">
            <th>Course Name</th>
            <th>No. Of Students</th>
        <g:each in="${totalListByStudyCentre}" var="item">
            <tr style="border: 1px solid black">
                <td style="border: 1px solid black">${item.key}</td>
                <td style="border: 1px solid black">${item.value}</td>
            </tr>
        </g:each>
        </table>
    </g:if>

    <g:if test="${totalListByExaminationCentre}">
        <h3> Total Students In All Courses For ${examinationCentreSession} Session</h3>
        <table style="width: 50%; text-align: center; margin-left: 5%">
            <th>Course Name</th>
            <th>No. Of Students</th>
            <g:each in="${totalListByExaminationCentre}" var="item">
                <tr style="border: 1px solid black">
                    <td style="border: 1px solid black">${item.key}</td>
                    <td style="border: 1px solid black">${item.value}</td>
                </tr>
            </g:each>
        </table>
    </g:if>
</div>
</body>
</html>