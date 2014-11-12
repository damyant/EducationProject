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
    <fieldset class="form">
        <form id="studyMaterialPage" name="studyMaterialPage">

            <g:hiddenField name="subjectList" id="subjectList"/>
            <div class="university-status-message"> <div id="msgDiv"></div>

            <div id="error" class="university-status-message" hidden="hidden">No Student Found</div>
        </div>
            <table class="inner">
                %{--<tr><td><input type="radio" name="studyMaterialRadio" value="Roll Number">Enter Roll Number</td>--}%
                %{--<td><input type="radio" name="studyMaterialRadio" value="Challan Number">Enter Challan Number</td></tr>--}%
                <tr>
                    <td class="university-size-1-4">Enter Roll Number</td>
                    <td class="university-size-1-4">
                        <input type="text" class="university-size-1-1"  onkeypress="return isNumber(event)" name="studyMaterialText" id="studyMaterialText">
                    </td>
                    <td class="university-size-1-4">
                        <input type="button" class="university-button" name="Search" value="Search" onclick="studentForStudyMaterial()"></td>
                    <td class="university-size-1-4"></td>
                </tr>

            </table>
            <div id="studentRecordDiv"></div>
        </form>
    </fieldset>
</div>
</body>
</html>