<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 8/26/14
  Time: 12:01 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:javascript src='postExamination.js'/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Home Assignment Upload</h3>
        <g:uploadForm controller="postExamination" action="uploadHomeAssignmentMarks" method='post'
                      enctype="multipart/form-data"
                      id="uploadHomeAssignmentMarks" name="uploadHomeAssignmentMarks">
            <table class="university-size-full-1-1 inner spinner">
                <tr>
                    <td class="university-size-1-3">Program<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3">
                        <g:select name="programId" id="programId" optionKey="id" class="university-size-3-4"
                                  value=""
                                  optionValue="courseName" from="${programList}" noSelection="['': ' Select Program']"
                                  onchange="getTabulatorSession(this)"
                        />
                    </td>
                    <td class="university-size-1-3">
                        Click <strong><a href="${resource(dir: 'homeAssignmentFormat', file: 'HomeAssignmentFormat.xls')}">here</a></strong> to download HA excel format.
                    </td>
                </tr>

                <!----------------------------------------- Session Name --------------------------------------------->
                <tr>
                    <td class="university-size-1-3">Program Session<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3">
                        <g:select name="SessionList" id="SessionList" optionKey="id" class="university-size-1-2"
                                  value="" optionValue="session" from="" noSelection="['': ' Select Session']" disabled="true"  onchange="loadSemester()" />
                    </td>
                <td class="university-size-1-3"></td>
                </tr>


                <!----------------------------------------- Semester Name --------------------------------------------->
                <tr>
                    <td class="university-size-1-3">Semester<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3">
                        <g:select name="programTerm" id="semesterList" optionKey="" class="university-size-1-2" disabled="true"
                                  value="" optionValue="" from="" noSelection="['': ' Select Semester']" />
                    </td>
                <td class="university-size-1-3"></td>
                </tr>

                <tr>
                    <td class="university-size-1-3">Upload Excel File<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3">
                        <input type='file' id="homeAssignment" class="university-button" onchange="checkFileExtension()"  name="homeAssignment"/>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" class="university-size-1-2 university-button" value="Load Marks"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </g:uploadForm>
    </fieldset>
</div>

</body>
</html>