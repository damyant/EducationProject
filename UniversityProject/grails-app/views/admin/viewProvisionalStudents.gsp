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
</head>

<body>

<table>
    <tr>
        <td>
            <g:select name="studyCenter" from="${studyCenterList}" optionKey="id" optionValue="name" noSelection="['null':' Select Study Center']" />
        </td>
        <td>
            <g:select name="programs" from="${programList}" optionKey="id" optionValue="courseName" noSelection="['null':' Select Program']" />
        </td>
    </tr>
</table>

</body>
</html>