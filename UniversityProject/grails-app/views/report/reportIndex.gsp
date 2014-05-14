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
 <fieldset class="form">
    <div style="width: 30%">

      <ul>
        <sec:ifAnyGranted roles="ROLE_IDOL_USER, ROLE_STUDY_CENTRE ">
                <div id="session"> <a href="#"> <li>By Session </li></a></div>
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_STUDY_CENTRE ">
            <div id="sessionStudentList"> <a href="#"> <li>By Session Student List</li></a></div>
            <div id="byCourseUnapproved"> <a href="#"> <li>By Course Unapproved Roll No
            </li></a></div>
            <div id="byCourseApproved"> <a href="#"> <li>By Course Approved Roll No
            </li></a></div>
        </sec:ifAnyGranted>
        <sec:ifAnyGranted roles="ROLE_IDOL_USER">
                <div id="sessions"> <a href="#"> <li>By Sessions</li></a></div>
                <div id="course"> <a href="#"> <li>By Course</li></a></div>
                <div id="comparativeReport"> <a href="#">   <li>Comparative Enrolment Report</li></a></div>
                <div id="studyCentre"> <a href="#">
                    <li>By Study Centre</li>
                    <ul>
                        <li id="studyCentreNoOfStudents">Course Wise No Of Students </li>

                        <li id="studyCentreStudentList"> Course Wise Student List</li>
                    </ul></a>
                </div>
                <div id="examinationCentreDiv"> <a href="#">
                    <li>By Examination Centre</li>
                    <ul>
                        <li id="examinationCentreNoOfStudents">Course Wise No Of Students </li>

                        <li id="examinationCentreStudentList"> Course Wise Student List</li>
                    </ul>
                </a></div>

                <div >
                    <a href="#">
                        <li >By Category</li>
                        <ul>
                            <a href="#">    <li id="categoryDiv">Course Wise </li>         </a>

                            <a href="#">  <li id="categoryGenderDiv"> Course Wise With Gender Break-up
                            </li> </a>
                            <a href="#">      <li id="categoryGenderStudentList"> Course Wise Students List
                            </li></a>
                        </ul>
                    </a>
                </div>
                <div id="admissionReports">
                  <a href="#">
                    <li>Admission Reports</li>
                       <ul>
                           <a href="#">  <li id="admissionApproved"> Approved Roll Nos
                           </li></a>
                           <a href="#">   <li id="admissionReportUnapproved">Unapproved Roll Nos</li></a>

                           <a href="#">  <li id="admissionReportSelfRegister"> List of all Self Registrations</li></a>

                       </ul>
                  </a>
                </div>
                <div id="feePaidReports">
                    <a href="#">
                        <li>Fees Paid Report:
                        </li>
                        <ul>
                            <a href="#">  <li id="feePaidStudyCentre"> Study Centre Wise Fee Paid Statement
                            </li>      </a>
                            <a href="#">   <li id="dailyFeeReport">Daily Fees Report
                            </li></a>


                        </ul>
                    </a>
                </div>

      </ul>
      </sec:ifAnyGranted>
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
           <tr id="bySessionStudentList">
               <td style="width: 18%">
                   <label for="filterType">Select Session:</label>
               </td>
               <td style="width: 18%" >
                   <g:select name="sessionStudentList" class="university-size-1-1 allSession" id="sessionStudentList"
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

           <tr id="bySessionsComparative">
               <td style="width: 18%">
                   <label for="filterType">Select Sessions:</label>
               </td>
               <td style="width: 18%" >
                   <g:select name="fromSessionComparative" class="university-size-1-1 allSession" id="fromSessionComparative"
                             from="${filterType}" optionKey="" optionValue=""
                             noSelection="['null': ' Select Session']" />
               </td>
               <td style="width: 18%" >
                   <g:select name="toSessionComparative" class="university-size-1-1 allSession" id="toSessionComparative"
                             from="${filterType}" optionKey="" optionValue=""
                             noSelection="['null': ' Select Session']" />
               </td>
           </tr>




               <tr id="byCourse">
                   <td style="width: 18%">
                       <label for="filterType">Select Course:</label>
                   </td>
                   <td style="width: 25%" >
                       <g:select name="course" class="university-size-1-1" id="filterType"
                                 from="${ProgramDetail.list([sort: 'courseName'])}" optionKey="id" optionValue="courseName"
                                 noSelection="['null': ' Select Course']" />
                   </td>
                   <td style="width: 10%" >
                       <g:select name="courseSession" class="university-size-1-1 allSession" id="filterType"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>
               </tr>

           <tr id="courseUnapprovedStudyCentre">
               <td style="width: 18%">
                   <label for="filterType">Select Course:</label>
               </td>
               <td style="width: 25%" >
                   <g:select name="courseUnapproved" class="university-size-1-1" id="courseUnapproved"
                             from="${ProgramDetail.list([sort: 'courseName'])}" optionKey="id" optionValue="courseName"
                             noSelection="['null': ' Select Course']" />
               </td>
               <td style="width: 10%" >
                   <g:select name="courseUnapprovedSession" class="university-size-1-1 allSession" id="courseUnapprovedSession"
                             from="${filterType}" optionKey="" optionValue=""
                             noSelection="['null': ' Select Session']" />
               </td>
           </tr>


           <tr id="courseApprovedStudyCentre">
               <td style="width: 18%">
                   <label for="filterType">Select Course:</label>
               </td>
               <td style="width: 25%" >
                   <g:select name="courseApproved" class="university-size-1-1" id="courseApproved"
                             from="${ProgramDetail.list([sort: 'courseName'])}" optionKey="id" optionValue="courseName"
                             noSelection="['null': ' Select Course']" />
               </td>
               <td style="width: 10%" >
                   <g:select name="courseApprovedSession" class="university-size-1-1 allSession" id="courseApprovedSession"
                             from="${filterType}" optionKey="" optionValue=""
                             noSelection="['null': ' Select Session']" />
               </td>
           </tr>





               <tr id="byStudyCentre">
                   <td style="width: 18%">
                       <label for="filterType">Select Study Centre:</label>
                   </td>
                   <td style="width: 30%" >
                       <g:select name="studyCentre" class="university-size-1-1" id="filterType"
                                 from="${StudyCenter.list([sort: 'name'])}" optionKey="id" optionValue="name"
                                 noSelection="['null': ' Select Study Centre']" />
                   </td>
                   <td style="width: 10%" >
                       <g:select name="studyCentreSession" class="university-size-1-1 allSession" id="filterType"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>

               </tr>

               <tr id="byStudyCentreFeePaid">
                   <td style="width: 18%">
                       <label for="filterType">Select Study Centre:</label>
                   </td>
                   <td style="width: 30%" >
                       <g:select name="feePaidStudyCentre" class="university-size-1-1" id="feePaidStudyCentre"
                                 from="${StudyCenter.list([sort: 'name'])}" optionKey="id" optionValue="name"
                                 noSelection="['null': ' Select Study Centre']" />
                   </td>
                   <td style="width: 10%" >
                       <g:select name="studyCentreFeePaidSession" class="university-size-1-1 allSession" id="studyCentreFeePaidSession"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>

               </tr>

               <tr id="byDailyFeeReport">
                   <td style="width: 18%">
                       <label for="filterType">From Date:</label>
                   </td>
                   <td style="width: 30%" >
                       <input type="text" name="feeFromDate" class="university-size-1-2" id="feeFromDate"/>
                   </td>
                   <td style="width: 18%">
                       <label for="filterType">To Date:</label>
                   </td>
                   <td style="width: 30%" >
                       <input type="text" name="feeToDate" class="university-size-1-2" id="feeToDate"/>
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



           <tr id="categoryStudentList">
               <td style="width: 18%">
                   <label for="filterType">Select Session:</label>
               </td>

               <td style="width: 18%" >
                   <g:select name="categoryStudentListSession" class="university-size-1-1 allSession" id="categoryStudentListSession"
                             from="${filterType}" optionKey="" optionValue=""
                             noSelection="['null': ' Select Session']" />
               </td>
               <td style="width: 18%">
                   <label for="filterType">Select Category:</label>
               </td>
               <td style="width: 30%">
                   <select name="studentCategory" id="studentCategory">
                       <option value="General">General</option>
                       <option value="MOBC">MOBC</option>
                       <option value="OBC">OBC</option>
                       <option value=" SC"> SC</option>
                       <option value=" ST"> ST</option>
                       <option value="MINORITY COMMUNITY"> MINORITY</option>
                   </select>
               </td>
           </tr>



               <tr id="byAdmissionUnapprovedReport">
                   <td style="width: 18%">
                       <label for="filterType">Select Study Centre:</label>
                   </td>
                   <td style="width: 30%" >
                       <g:select name="admissionUnapprovedStudyCentre" class="university-size-1-1" id="filterType"
                                 from="${StudyCenter.list([sort: 'name'])}" optionKey="id" optionValue="name"
                                 noSelection="['null': ' Select Study Centre']" />
                   </td>
                   <td style="width: 10%" >
                       <g:select name="admissionUnapprovedSession" class="university-size-1-1 allSession" id="admissionUnapprovedSession"
                                 from="${filterType}" optionKey="" optionValue=""
                                 noSelection="['null': ' Select Session']" />
                   </td>

               </tr>



           <tr id="byAdmissionApprovedReport">
               <td style="width: 18%">
                   <label for="filterType">Select Study Centre:</label>
               </td>
               <td style="width: 30%" >
                   <g:select name="admissionApprovedStudyCentre" class="university-size-1-1" id="admissionApprovedStudyCentre"
                             from="${StudyCenter.list([sort: 'name'])}" optionKey="id" optionValue="name"
                             noSelection="['null': ' Select Study Centre']" />
               </td>
               <td style="width: 10%" >
                   <g:select name="admissionApprovedSession" class="university-size-1-1 allSession" id="admissionApprovedSession"
                             from="${filterType}" optionKey="" optionValue=""
                             noSelection="['null': ' Select Session']" />
               </td>

           </tr>



           <tr id="byAdmissionSelfRegistration">
               <td style="width: 18%">
                   <label for="filterType">Select Session:</label>
               </td>

               <td style="width: 10%" >
                   <g:select name="admissionSelfRegistrationSession" class="university-size-1-1 allSession" id="admissionSelfRegistrationSession"
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
                       <g:hiddenField name="inExcel" id="inExcel" value=""/>
                   </td>
                   <td>
                   <g:submitButton name="create" class="save university-button"
                          value='Submit'  />
                   </td>
               </tr>

        </table>
       </g:form>
   </div>
</fieldset>
 </div>
</body>
</html>