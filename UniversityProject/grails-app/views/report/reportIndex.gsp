<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 29/4/14
  Time: 3:09 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" import="examinationproject.District; examinationproject.ProgramDetail;examinationproject.StudyCenter"%>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'report.js')}"></script>
    <g:javascript src='studyCenter.js'/>
</head>

<body>
<div id="main">
    <div style="width: 30%">
            <ul>
                <div id="session"> <a href="#"> <li>By Session</li></a></div>
                <div id="sessions"> <a href="#"> <li>By Sessions</li></a></div>
                <div id="course"> <a href="#"> <li>By Course</li></a></div>
                <div id="studyCentre"> <a href="#"> <li>By Study Centre</li></a></div>
                <div id="comparativeReport"> <a href="#">   <li>Comparative Enrolment Report</li></a></div>
                <div id="examinationCentreDiv"> <a href="#"> <li>By Examination Centre</li></a></div>
                %{--<div id="categoryDiv"> <a href="#"> <li>By Category</li></a></div>--}%
                %{--<div id="categoryGenderDiv"> <a href="#"> <li>By Category And Gender</li></a></div>--}%
                <div >
                    <a href="#">
                        <li >By Category</li>
                        <ul>
                            <li id="categoryDiv">Course wise </li>

                            <li id="categoryGenderDiv"> Course wise with Gender Break-up
                            </li>
                        </ul>
                    </a>
                </div>
                <div id="admissionReports">
                  <a href="#">
                    <li>Admission Reports</li>
                       <ul>
                           <li id="admissionReportUnapproved">Unapproved Roll Nos</li>

                           <li id="admissionReportSelfRegister"> List of all Self Registrations</li>
                       </ul>
                  </a>
                </div>

            </ul>

    </div>
   <div id="sessionDialog" class="dialog">
       <g:form  id="createFeeDetail" controller="report" action="generateReport">

           <table class="inner" style="margin: auto;" id="fee-table">
               <tr id="bySession">
                   <td style="width: 18%">
                       <label for="filterType">Select Session:</label>
                   </td>
                   <td style="width: 18%" >
                       <g:select name="session" class="university-size-1-1 allSession" id="filterType"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>

               </tr>
               <tr id="bySessions">
                   <td style="width: 18%">
                       <label for="filterType">Select Sessions:</label>
                   </td>
                   <td style="width: 18%" >
                       <g:select name="fromSession" class="university-size-1-1 allSession" id="fromSession"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>
                   <td style="width: 18%" >
                       <g:select name="toSession" class="university-size-1-1 allSession" id="toSession"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>
               </tr>
               <tr id="byCourse">
                   <td style="width: 18%">
                       <label for="filterType">Select Course:</label>
                   </td>
                   <td style="width: 18%" >
                       <g:select name="course" class="university-size-1-1" id="filterType"
                                 from="${ProgramDetail.list([sort: 'courseName'])}" optionKey="id" optionValue="courseName"
                                 noSelection="['null': ' Select Course']" />
                   </td>
                   <td style="width: 18%" >
                       <g:select name="courseSession" class="university-size-1-1 allSession" id="filterType"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>
               </tr>
               <tr id="byStudyCentre">
                   <td style="width: 18%">
                       <label for="filterType">Select Study Centre:</label>
                   </td>
                   <td style="width: 18%" >
                       <g:select name="studyCentre" class="university-size-1-1" id="filterType"
                                 from="${StudyCenter.list([sort: 'name'])}" optionKey="id" optionValue="name"
                                 noSelection="['null': ' Select Study Centre']" />
                   </td>
                   <td style="width: 18%" >
                       <g:select name="studyCentreSession" class="university-size-1-1 allSession" id="filterType"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>

               </tr>
               <tr id="byCategory">
                   <td style="width: 18%">
                       <label for="filterType">Select Session:</label>
                   </td>
                   %{--<td style="width: 18%" >--}%
                       %{--<g:select name="course" class="university-size-1-1" id="filterType"--}%
                                 %{--from="${ProgramDetail.list([sort: 'courseName'])}" optionKey="id" optionValue="courseName"--}%
                                 %{--noSelection="['null': ' Select Course']" />--}%
                   %{--</td>--}%
                   <td style="width: 18%" >
                       <g:select name="categorySession" class="university-size-1-1 allSession" id="categorySession"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>
               </tr>


               <tr id="byAdmissionUnapprovedReport">
                   <td style="width: 18%">
                       <label for="filterType">Select Study Centre:</label>
                   </td>
                   <td style="width: 18%" >
                       <g:select name="admissionUnapprovedStudyCentre" class="university-size-1-1" id="filterType"
                                 from="${StudyCenter.list([sort: 'name'])}" optionKey="id" optionValue="name"
                                 noSelection="['null': ' Select Study Centre']" />
                   </td>
                   <td style="width: 18%" >
                       <g:select name="admissionUnapprovedSession" class="university-size-1-1 allSession" id="admissionUnapprovedSession"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>

               </tr>


               <tr id="byCategoryGender">
                   <td style="width: 18%">
                       <label for="filterType">Select Session:</label>
                   </td>
                   %{--<td style="width: 18%" >--}%
                   %{--<g:select name="course" class="university-size-1-1" id="filterType"--}%
                   %{--from="${ProgramDetail.list([sort: 'courseName'])}" optionKey="id" optionValue="courseName"--}%
                   %{--noSelection="['null': ' Select Course']" />--}%
                   %{--</td>--}%
                   <td style="width: 18%" >
                       <g:select name="categoryGenderSession" class="university-size-1-1 allSession" id="categoryGenderSession"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>
               </tr>


               <tr id="byExaminationCentre">

                   <table id="examCenterSelect" style="border: 0px solid black">
                       <tr>
                           <td style="width: 50%">

                               <g:select name="examDistrict" id="district" optionKey="id"
                                         value="" class="university-size-1-1"
                                         onChange="showCityList()" optionValue="districtName"
                                         from="${District.list([sort: 'districtName'])}" noSelection="['': ' Select District']"/>

                           </td>
                           <td style="width: 50%">


                                   <g:select name="examCity" id="city" optionKey="id" class="university-size-1-1"
                                             optionValue="cityName"
                                             from="" onchange="showCentreList(this)"
                                             noSelection="['': 'Select Examination Centre']"/>

                           </td>
                       </tr>
                       <tr>
                       <td>
                           <g:select name="examinationCentre" id="examinationCentre" class="university-size-1-1" from=" "
                                     noSelection="['': 'Select Examination Venue']"/>

                       </td>
                           <td>
                               <g:select name="examinationCentreSession" class="university-size-1-1 allSession" id="examinationCentreSession"
                               from="${filterType}" optionKey="" optionValue=""
                               noSelection="['null': ' Select Session']" />
                           </td>
                   </tr>
                   </table>

               </tr>

               <tr id="submitButton">
                   <td>
                       <g:hiddenField name="value" id="flagValue" value=""/>
                   </td>
                   <td>
                   <g:submitButton name="create" class="save university-button"
                          value='Submit'  />
                   </td>
               </tr>

        </table>
       </g:form>
   </div>

 </div>
</body>
</html>