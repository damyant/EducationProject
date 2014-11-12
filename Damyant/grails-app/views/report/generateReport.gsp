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
    <meta name="layout" content="/main">
</head>

<body>
<div id="main">

    <g:if test="${session}">
    <h3> Total Students In All Courses For ${session} Session</h3>
    </g:if>
    <g:if test="${totalList}">
    <table style="width: 50%; text-align: center; margin-left: 20%">
        <th>Course Name</th>
        <th>No. Of Students</th>
        <g:each in="${totalList}" var="item">
            <tr style="border: 1px solid black">
                 <td style="border: 1px solid black">${item.key}</td>
                 <td style="border: 1px solid black">${item.value}</td>
            </tr>
        </g:each>
    </g:if>
    </table>




    %{--<g:each in="${totalList}" var="item">--}%
        %{--<dl>--}%
            %{--<dt>${item.key}</dt>--}%
            %{--<dd>${item.value}</dd>--}%
        %{--</dl>--}%
    %{--</g:each>--}%
</div>
</body>
</html>