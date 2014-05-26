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
        <table>
            %{--<tr><td><input type="radio" name="studyMaterialRadio" value="Roll Number">Enter Roll Number</td>--}%
                %{--<td><input type="radio" name="studyMaterialRadio" value="Challan Number">Enter Challan Number</td></tr>--}%
            <tr>  <td>Enter Roll Number <input type="text" name="studyMaterialText" id="studyMaterialText"></td></tr>
           <tr> <td><input type="button" name="Search" value="Search" onclick="studentForStudyMaterial()"></td>
            </tr>
        </table>
            <div id="msgDiv"></div>
            <table id="studentRecord">
               <tbody></tbody>
            </table>

        </form>
    </div>
</body>
</html>