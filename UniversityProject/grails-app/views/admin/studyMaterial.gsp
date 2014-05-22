<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 5/22/14
  Time: 1:43 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Study Material</title>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
        <form id="studyMaterial">
        <table>
            <tr><td><input type="radio" name="studyMaterialRadio" value="Roll Number">Enter Roll Number</td>
                <td><input type="radio" name="studyMaterialRadio" value="Challan Number">Enter Challan Number</td></tr>
            <tr>  <td><input type="text" name="studyMaterialText" id="studyMaterialText"></td>
            <td><input type="button" name="Search" value="Search" onclick="studentForStudyMaterial()"></td>
            </tr>
        </table>

        </form>
    </div>
</body>
</html>