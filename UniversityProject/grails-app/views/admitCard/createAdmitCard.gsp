<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="examinationproject.City" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Student Admit Card</title>
    <meta name="layout" content="main"/>
</head>

<body>
<div id="main">
    <h3>STUDENT ADMIT CARD</h3>
    <g:uploadForm controller="admitCard" action="createAdmintCard" method='post' enctype="multipart/form-data">
        <div>
            <table class="university-table-1-3" style="width: 80%;margin: auto;">
                <tr>
                    <td><label>Select an Examination Centre :</label></td>
                    <td>
                        <g:select name="city" id="city" optionKey="id" class="university-size-2-3"
                                  optionValue="cityName" from="${City.findAll()}" noSelection="['': ' Select City']"
                                  onchange="showExamCentreList()"/>
                        %{--<g:select name="examinationCentre"   optionKey="id" optionValue="name" from="${City.findAll()}" noSelection="['':' Select Examination Centre']" />--}%
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td><label>Select a Course :</label></td>
                    <td>
                        <g:select name="programList" class="university-size-2-3" optionKey="id" optionValue="courseName"
                                  from="${programList}" noSelection="['': ' Select Program']" onchange="getSemester()"/>
                    </td>
                    <td></td>
                </tr>
                <tr><td><label>Select a Term :</label></td>
                    <td>
                        <select name="programTerm" class="university-size-2-3" id="semesterList">
                            <option value="">Select Semester</option>
                        </select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td><label>Select Examination Venue :</label></td>
                    <td>
                        <g:select name="examinationCenter" class="university-size-2-3" id="examCenterList" from=""
                                  noSelection="['': ' Select Examination Venue']"/>
                    </td>
                    <td>
                        <input type="button" class="university-button university-float-right" value="Show Candidates"/>
                    </td>
                </tr>
            </table>
        </div>
    </g:uploadForm>
</div>

</body>
</html>