<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 25/4/14
  Time: 1:17 PM
--%>

<%@ page import=" javax.validation.constraints.Null; examinationproject.City;examinationproject.ExaminationCentre;examinationproject.ProgramDetail; examinationproject.District" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Download Attendance Sheet</title>
    <meta name='layout' content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>

</head>

<body>
<div id="main">
  <g:form controller="admin" action="downloadAttendanceSheet">
  <table>
      <tr>
          <td>Select Examination Venue</td>
          <td>
              <table id="examCenterSelect">
                <tr>
                    <td style="width: 50%">
                       <g:select name="district" id="district" optionKey="id"
                          value="${studInstance?.examinationCentre?.city?.district?.id?.get(0)}" class="university-size-1-1"
                          onChange="showCityList()" optionValue="districtName"
                          from="${District.list(sort:'districtName')}" noSelection="['': ' Select District']"/>
                    </td>
                    <td style="width: 50%">
                        <g:select name="city" id="city" optionKey="id" class="university-size-1-1"
                              optionValue="cityName"
                              from="" onchange="showCentreList(this)"
                              noSelection="['': ' Select Examination Centre']"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <g:select name="examinationCentre" id="examinationCentre" optionKey="id" optionValue="name"
                                  class="university-size-1-1" from="" onchange="showProgrammeList()"
                                  noSelection="['': 'Select Examination Venue']"/>


                    </td>
                    <td>

                    </td>
                </tr>
              </table>
          </td>
      </tr>
      <tr>
          <td>Select Programme</td>
          <td>
              <table><tr>
             <td style="width: 50%">
              <g:select name="programList" id="programList" optionKey="id" class="university-size-1-1"
                        optionValue="courseName" from="" noSelection="['': ' Select Program']" onchange="getSemester()"/>

          </td>

                  <td>
                      <select name="programTerm" class="university-size-1-1" id="semesterList">
                          <option value="">Select Semester</option>
                      </select>
                  </td>

         </tr>
                  <tr>
                      <td>
                          <g:select name="programSession" from="" class="university-size-1-1" id="SessionList"
                                    onchange="enableShowCandidate()" noSelection="['': ' Select Session']"/>

                      </td>
                      <td></td>
                  </tr>
         </table>
      </tr>
      <tr>
          <td>
              <g:submitButton name="submit" id="submit" value="Download" style="display:none">Download</g:submitButton>
          </td>
      </tr>
  </table>
  </g:form>
</div>
</body>
</html>