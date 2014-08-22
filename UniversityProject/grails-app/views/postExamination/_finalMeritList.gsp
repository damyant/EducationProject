<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 8/8/2014
  Time: 7:10 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<div style="width: 100%;text-align: center;font-size: 18px;"><b>GAUHATI UNIVERSITY</b></div>

<div style="width: 100%;text-align: center;font-size: 15px;text-decoration: underline">Result of ${courseName} semester ${semester} Under Gauhati University Institute of Distance and Open Learning</div>

<div style="width: 100%;text-align: center;font-size: 15px;"><b> ${courseName} ${semester} Semester</b></div>
<div style="width: 100%;text-align: left; font-size: 14px;">Appeared :  ${totalStudentsAppeared}</div>
<div style="width: 100%;text-align: left; font-size: 14px;">List of successful candidates (in order of merit)</div>
<div style="width: 100%;text-align: left; font-size: 14px;">Total :  ${studentMeritList.size()}</div>


    <g:if test="${studentMeritList}">
        <table>
            <tr>
            <th>S.No</th>
            <th>Roll No</th>
            <th>Name</th>
            <th>Grand Total</th>
            </tr>
            <% def counter=1 %>
        <g:each in="${studentMeritList.reverse()}" var="stud" status="i">

            <tr>
                <td>${counter}</td>
                <td>${stud.rollNo}</td>
                <td>${stud.firstName} ${stud.middleName} ${stud.lastName} </td>
                <%
                    def getBackJsonObj = grails.converters.JSON.parse(stud.totalMarks)
                     def marks= getBackJsonObj[stud.semester.toString()]
                %>
                <td>${marks}</td>
            </tr>
            <% ++counter %>

        </g:each>
        </table>
    </g:if>
    <g:else>
        NO STUDENT CLEARED IN ALL SUBJECTS
    </g:else>



</body>
</html>