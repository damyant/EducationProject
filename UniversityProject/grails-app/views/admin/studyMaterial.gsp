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
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
</head>

<body>
<div id="main">
        <form id="studyMaterialPage" name="studyMaterialPage">
            <g:hiddenField name="subjectList" id="subjectList" />
            <div id="msgDiv" class="university-status-message"></div>
            <div id="error" class="university-status-message" hidden="hidden">No Student Found</div>
        <table>
            <tr>  <td>Enter Roll Number <input type="text" name="studyMaterialText" id="studyMaterialText" onkeypress="return isNumber(event)"></td></tr>
           <tr> <td><input type="button" name="Search" value="Search"   onclick="studentForStudyMaterial()"></td>
            </tr>
        </table>
            <table id="studentRecord">
               <tbody></tbody>
            </table>

        </form>
    </div>
</body>
</html>