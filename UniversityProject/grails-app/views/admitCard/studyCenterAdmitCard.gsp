<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 4/25/14
  Time: 12:36 PM
--%>

<%@ page import="com.university.User" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
</head>

<body>
<div id="main">
    <fieldset>
        <h3>STUDENT ADMIT CARD</h3>
        <g:form name="admitCardForm" id="admitCardForm" controller="admitCard" action="printAdmitCard">
            <g:hiddenField name="studyCenterId" id="studyCenterId" value="true"/>
            <div>
                <table class="university-table-1-3 inner" style="width: 80%;margin-left: 20px;">
                    %{--<tr>--}%
                        %{--<td><label>Select an Examination Centre</label></td>--}%
                        %{--<td>--}%
                            %{--<g:select name="examinationCenter" id="city" optionKey="id" class="university-size-1-1"--}%
                                      %{--optionValue="cityName" from="${examinationCenterList}"--}%
                                      %{--noSelection="['': ' Select Exam Centre']"--}%
                                      %{--onchange="showExamVenueList(),enableShowCandidate()"/>--}%
                        %{--</td>--}%
                    %{--</tr>--}%
                    <tr>
                        <td><label>Select a Course</label></td>
                        <td>
                            <g:select name="programList" class="university-size-1-1" optionKey="id"
                                      optionValue="courseName"
                                      from="${programList}" noSelection="['': ' Select Program']"
                                      onchange="getSemester(),enableShowCandidate()"/>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Select a Term</label></td>
                        <td>
                            <select name="programTerm" class="university-size-1-1" id="semesterList">
                                <option value="">Select Semester</option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Select a Session</label></td>
                        <td>
                            <g:select name="programSession" from="" class="university-size-1-1" id="SessionList"
                                      onchange="enableShowCandidate()" noSelection="['': ' Select Session']"/>

                        </td>
                        <td></td>
                    </tr>
                    %{--<tr>--}%
                        %{--<td><label>Select Examination Venue</label></td>--}%
                        %{--<td>--}%
                            %{--<g:select name="examinationVenue" class="university-size-1-1" id="examCenterList" from=""--}%
                                      %{--onchange="showExamVenueCapacity(),enableShowCandidate()"--}%
                                      %{--noSelection="['': ' Select Exam Venue']"/>--}%
                        %{--</td>--}%

                        %{--<td></td>--}%
                    %{--</tr>--}%
                    <tr>

                        <td>
                            <input type="SUBMIT" class="university-button" id="showCandidates" value="Download Admit Card"
                                    disabled/>
                        </td>

                    </tr>
                </table>
            </div>

            <div class="university-status-message"><label id="showErrorMessage" hidden=""></label></div>

        </g:form>
    </fieldset>
</div>
</body>
</html>