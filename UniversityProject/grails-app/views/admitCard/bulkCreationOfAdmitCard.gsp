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
        <form name="admitCardForm" id="admitCardForm">
            <div>
                <table class="university-table-1-3 inner" style="width: 60%;margin: auto;">
                    <tr>
                        <td><label>Select an Examination Centre </label></td>
                        <td>
                            <g:select name="examinationCenter" id="city" optionKey="id" class="university-size-1-1"
                                      optionValue="cityName" from="${examinationCenterList}" noSelection="['': ' Select City']"
                                      onchange="showExamVenueList()"/>
                        </td>
                     </tr>
                    <tr>
                        <td><label>Select a Course </label></td>
                        <td>
                            <g:select name="programList" class="university-size-1-1" optionKey="id"
                                      optionValue="courseName"
                                      from="${programList}" noSelection="['': ' Select Program']"
                                      onchange="getSemester()"/>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Select a Term </label></td>
                        <td>
                            <select name="programTerm" class="university-size-1-1" id="semesterList">
                                <option value="">Select Semester</option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Select a Session</label></td>
                        <td>
                            <select name="programSession" class="university-size-1-1" id="SessionList">
                                <option value="">Select Session</option>
                                <option value="">2012-2013</option>
                                <option value="">2013-2014</option>
                                <option value="">2014-2016</option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><label>Select Examination Venue </label></td>
                        <td>
                            <g:select name="examinationVenue" class="university-size-1-1" id="examCenterList" from="" onchange="showExamVenueCapacity()"
                                      noSelection="['': ' Select Examination Venue']"/>
                        </td>
                        <td><label>Max Capacity</label><input type="text" id="totalCapacity"  alue=""/></td>
                        <td>
                            <input type="button" class="university-button university-float-right"
                                   value="Show Candidates" onclick="getStudentsForAdmitCard()"/>
                        </td>
                    </tr>
                </table>
            </div>
        </form>
        <div class="university-List-View university-scrollable-y">
            <table id="admitCardTab" class="university-table-1-5 inner" style="width:98%;margin: 1px">
                <tr>
                    <th>Select</th>
                    <th>Sr. No.</th>
                    <th>Roll No.</th>
                    <th>Name</th>
                    <th>GU Registration No</th>
                </tr>
                <% def count = 12 %>
                <g:each in="${(1..count).toList()}" var="c">
                    <tr>
                        <td><input type="checkbox" name="student" id="student" /></td>
                        <td>${c}</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </g:each>
            </table>
        </div>

        <div class=""
             style="margin: 10px auto;width:94%;text-align: center; border: 1px solid; padding: 0.5%;border-radius: 2px;">

            <label class="university-left-right-margin"><strong>Print</strong></label>
            <label class="university-left-margin">from</label>
            <input type="text" value="0" width="7" onclick="this.value = ''" onkeypress="return isNumber(event)">
            <label class="university-left-right-margin">to</label>
            <input type="text" class="university-left-right-margin" value="0" width="7" onclick="this.value = ''"
                   onkeypress="return isNumber(event)">
            <label class="university-left-margin">OR</label>
            <label class="university-left-right-margin">All</label>
            <input type="checkbox" id="ck" name="ck" onchange="toggleChecked(this.checked)"/>
        </div>

        <div style="margin: 10px auto;width:94%;text-align: center;">
            <input type="button" value="Print" class="university-button">
        </div>
    </fieldset>
</div>
</body>
</html>