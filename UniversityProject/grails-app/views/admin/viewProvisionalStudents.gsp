<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 3/26/14
  Time: 10:24 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>View Students</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
<table class="inner university-table-1-2" style="margin: auto;">
    <tr>
        <td>
            <g:select name="studyCenter" class="university-size-1-2" id="studyCenter" from="${studyCenterList}" optionKey="id" optionValue="name" noSelection="['null':' Select Study Center']" />
        </td>
        <td>
            <g:select name="programs" id="programs" class="university-size-1-2" from="${programList}" optionKey="id" optionValue="courseName" noSelection="['null':' Select Program']" onchange="getStudents()" />
        </td>
    </tr>
</table>

<table id="studentList" class="inner university-table-1-3">
    <thead></thead>
   <tbody></tbody>
</table>

<div id="msg"></div>
</div>
</body>
</html>