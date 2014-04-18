<%--
  Created by IntelliJ IDEA.
  User: Shweta
  Date: 31/3/14
  Time: 12:56 PM
--%>
<%@ page import="examinationproject.City" contentType="text/html;charset=UTF-8" %>
<%@ page import="examinationproject.ExaminationCentre; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
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
            <g:hiddenField name="studentList" id="studentList"/>
            <div>
                <table class="university-table-1-3 inner" style="width: 80%;margin-left: 20px;">
                    <tr>
                        <td><label>Select an Examination Centre</label></td>
                        <td>
                            <g:select name="examinationCenter" id="city" optionKey="id" class="university-size-1-1"
                                      optionValue="cityName" from="${examinationCenterList}"
                                      noSelection="['': ' Select Exam Centre']"
                                      onchange="showExamVenueList(),enableShowCandidate()"/>
                        </td>
                    </tr>
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
                    <tr>
                        <td><label>Select Examination Venue</label></td>
                        <td>
                            <g:select name="examinationVenue" class="university-size-1-1" id="examCenterList" from=""
                                      onchange="showExamVenueCapacity(),enableShowCandidate()"
                                      noSelection="['': ' Select Exam Venue']"/>
                        </td>

                        <td></td>
                    </tr>
                    <tr>
                        <td>
                            <div id="maxCapacityBox" hidden="">
                                <b><label>Maximum Capacity </label><input type="text" class="university-size-1-2"
                                                                       id="totalCapacity" style="text-align: center;" readonly/></b>
                            </div>
                        </td>
                        <td>
                            <input type="button" class="university-button" id="showCandidates" value="Show Candidates"
                                   onclick="getStudentsForAdmitCard()" disabled/>
                        </td>
                        <td>
                            <div id="remainingCapacityBox" hidden="">
                                <b><label>Available Capacity </label><input type="text" class="university-size-1-2"
                                                                         id="remainingCapacity" style="text-align: center;" readonly/></b>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <div class="university-status-message"><label id="showErrorMessage" hidden=""></label></div>

            <div id="studentListTable" class="university-List-View university-scrollable-y" hidden="">
                <table id="admitCardTab" class="inner" style="width:100%;margin:auto">
                    <tr>
                        <th style="width: 17%;padding-left: 10px;"><input type="checkbox" id="selectAll"
                                                                          name="selectAll"/> Select All</th>
                        <th style="width: 27%;">Sr. No.</th>
                        <th style="width: 27%;">Roll No.</th>
                        <th style="width: 29%;">Name</th>
                        %{--<th class="university-size-1-5">GU Registration No</th>--}%
                    </tr>
                    %{--<<<<<<< HEAD--}%
                    %{--<% def count = 12 %>--}%
                    %{--<g:each in="${(1..count).toList()}" var="c">--}%
                    %{--<tr>--}%
                    %{--<td><input type="checkbox" class="student" name="student" id="student${c}" /></td>--}%
                    %{--<td>${c}</td>--}%
                    %{--<td></td>--}%
                    %{--<td></td>--}%
                    %{--<td></td>--}%
                    %{--</tr>--}%
                    %{--</g:each>--}%
                    %{--=======--}%

                    %{-->>>>>>> 5f682d0d04746e079348f66d555177e37546a443--}%
                </table>
            </div>

            <div id="studentListPrint"
                 style="margin: 5px auto;width:94%;text-align: center;vertical-align: middle; border: 1px solid #BDBDBD; padding: 0.5%;border-radius: 4px;"
                 hidden="">

                <label class="university-left-right-margin">
                    <img src="${resource(dir: 'images', file: 'Printer-icon.png')}"
                         style="width: 30px;vertical-align: bottom; margin: auto 20px;" class="logo-image"/>
                </label>
                <label class="university-left-margin" style="color: #000; font-size: 17px;"><b>From</b></label>
                <input type="text" value="Enter RollNo" width="7" onclick="this.value = ''" onkeypress="return isNumber(event)"
                       style="width: 80px;margin: auto 20px;text-align: center;border-radius: 2px;">
                <label class="university-left-right-margin" style="color: #000;font-size: 17px;"><b>To</b></label>
                <input type="text" class="university-left-right-margin" value="Enter RollNo" width="7" onclick="this.value = ''"
                       onkeypress="return isNumber(event)"
                       style="width: 80px;margin: auto 20px;text-align: center;border-radius: 2px;">

            </div>

            <div id="studentListPrintButton" style="margin: 10px auto;width:94%;text-align: center;" hidden="">
                <input type="button" value="Print" onclick="generateAdmitCard()" class="university-button">
            </div>
        </g:form>
    </fieldset>
</div>
</body>
</html>