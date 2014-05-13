<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 30/4/14
  Time: 11:18 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Printable Report</title>
    %{--<meta name="layout" content="/main">--}%
    <style type="text/css">
    table.gridtable {
        width: 90%;
        font-family: verdana,arial,sans-serif;
        font-size:11px;
        color:#333333;
        border-width: 1px;
        border-color: #666666;
        border-collapse: collapse;
    }
    table.gridtable th {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #666666;
        background-color: #dedede;
    }
    table.gridtable td {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #666666;
        background-color: #ffffff;
    }
    </style>
</head>

<body>
<div id="main">
      <g:if test="${totalListBySession}">
          <h3> Total Students In All Courses For ${sessionVal} Session</h3>
          <table style=" text-align: center" class="gridtable">
                     <th>Course Name</th>
                     <th>No. Of Students</th>
                     <g:each in="${totalListBySession}" var="item">
                         <tr>
                            <td >${item.key}</td>
                            <td >${item.value}</td>
                         </tr>
                     </g:each>
          </table>
      </g:if>


    <g:elseif test="${totalListBySessions}">
        <h3>Total Students In All Courses For Different Sessions</h3>
        <table style=" text-align: center" class="gridtable">
            <tr>
                    <th rowspan="2">Programme</th>
                    <th colspan="${sessionVal.size()}">Session </th>
            </tr>
            <tr>
                    <g:each in="${sessionVal}" var="item">
                       <th>${item}</th>
                    </g:each>
            </tr>
            <g:each in="${totalListBySessions}" var="item">
                <tr >
                    <td>${item.key}</td>
                    <g:each in="${item.value}" var="value">
                        <td> ${value}</td>
                    </g:each>
                </tr>
            </g:each>
        </table>
    </g:elseif>






      <g:elseif test="${totalListByCourse}">
          <h3> Total Students In ${totalListByCourse.getAt(0).programDetail[0].courseName} For ${courseSession} Session</h3>
           <table style=" text-align: center" class="gridtable">
                    <th>Roll No</th>
                    <th>Name</th>
                    <th>Study Centre</th>
                    <th>Examination Centre</th>
                    <th>Mobile No.</th>
                    <th>Status</th>
                   <g:each in="${totalListByCourse}" var="student">
                        <tr >
                            <td >${student.rollNo}</td>
                            <td >${student.studentName}</td>
                            <td >${student.studyCentre[0].name}</td>
                            <td >${student.examinationCentre[0].examinationCentreName}</td>
                            <td >91${student.mobileNo}</td>
                            <td >${student.status.status}</td>
                        </tr>
                   </g:each>
           </table>
      </g:elseif>
      <g:elseif test="${totalListByStudyCentre}">
            <h3> Total Students In All Courses For ${studyCentreSession} Session</h3>
            <table style=" text-align: center" class="gridtable">
                    <th>Course Name</th>
                    <th>No. Of Students</th>
                <g:each in="${totalListByStudyCentre}" var="item">
                    <tr >
                        <td>${item.key}</td>
                        <td >${item.value}</td>
                    </tr>
                </g:each>
            </table>
      </g:elseif>
      <g:elseif test="${totalListByExaminationCentre}">
        <h3> Total Students In All Courses For ${examinationCentreSession} Session</h3>
        <table style=" text-align: center" class="gridtable">
                <th>Course Name</th>
                <th>No. Of Students</th>
                <g:each in="${totalListByExaminationCentre}" var="item">
                    <tr style="border: 1px solid black">
                        <td >${item.key}</td>
                        <td >${item.value}</td>
                    </tr>
                </g:each>
        </table>
      </g:elseif>
      <g:elseif test="${totalListByCategory}">
            <h3> Total Students In All Courses For ${categorySession} Session In Different Category</h3>
            <table style=" text-align: center" class="gridtable">
                   <tr>
                       <th rowspan="2">Course Name</th>
                       <th colspan="6" >No. Of Students</th>
                   </tr>
                   <tr>
                           <th>General</th>
                           <th>MOBC</th>
                           <th>OBC</th>
                           <th>S.T</th>
                           <th>SC</th>
                           <th>MINORITY</th>
                   </tr>
                   <g:each in="${totalListByCategory}" var="item">
                          <tr >
                                <td>${item.key}</td>
                                   <g:each in="${item.value}" var="index">
                                            <td> ${index}</td>
                                   </g:each>
                          </tr>
                   </g:each>
            </table>
       </g:elseif>
      <g:elseif test="${totalListByCategoryGender}">

                <h3 style="width: 100%"> Total Students In All Courses For ${categoryGenderSession} Session In Different Category And Gender</h3>
                <table style=" text-align: center" class="gridtable">
                       <tr>
                           <th rowspan="3">Course Name</th>
                           <th colspan="12" >No. Of Students</th>
                       </tr>
                       <tr>
                           <th colspan="2">General</th>
                           <th colspan="2">MOBC</th>
                           <th colspan="2">OBC</th>
                           <th colspan="2">S.T</th>
                           <th colspan="2">SC</th>
                           <th colspan="2">MINORITY</th>
                       </tr>
                       <tr>
                           <th>Male</th>
                           <th>Female</th>
                           <th>Male</th>
                           <th>Female</th>
                           <th>Male</th>
                           <th>Female</th>
                           <th>Male</th>
                           <th>Female</th>
                           <th>Male</th>
                           <th>Female</th>
                           <th>Male</th>
                           <th>Female</th>
                       </tr>
                        <g:each in="${totalListByCategoryGender}" var="item">
                            <tr style="border: 1px solid black">
                                <td>${item.key}</td>
                                    <g:each in="${item.value}" var="index">
                                         <td> ${index}</td>
                                    </g:each>
                            </tr>
                        </g:each>
                 </table>
      </g:elseif>


    <g:elseif test="${totalListByAdmissionApprovedUnapproved}">
        <h3> Unapproved Students In ${totalListByAdmissionApprovedUnapproved.getAt(0).studyCentre[0].name} For ${admissionApprovedUnapprovedSession} Session</h3>
        <table style=" text-align: center" class="gridtable">
            <th>Roll No</th>
            <th>Name</th>
            <th>Course Code</th>
            <th>Examination Centre</th>
            <th>Mobile No.</th>
            <g:each in="${totalListByAdmissionUnapproved}" var="student">
                <tr >
                    <td >${student.rollNo}</td>
                    <td >${student.studentName}</td>
                    <td >${student.programDetail[0].courseCode}</td>
                    <td >${student.examinationCentre[0].examinationCentreName}</td>
                    <td >91${student.mobileNo}</td>
                </tr>
            </g:each>
        </table>
    </g:elseif>



    <g:elseif test="${totalListByStudyCentreFeePaid}">
        <h3> Fees Paid Report At ${totalListByStudyCentreFeePaid.getAt(0).studentId.studyCentre[0].name} Study Centre For ${studyCentreFeePaidSession} Session</h3>
        <table style=" text-align: center" class="gridtable">
            <th>Roll No</th>
            <th>Name</th>
            <th>Fees Type</th>
            <th>Payment Date</th>
            %{--<th>Mobile No.</th>--}%
            <g:each in="${totalListByStudyCentreFeePaid}" var="student">
                <tr>
                    <td >${student.studentId.rollNo}</td>
                    <td >${student.studentId.studentName}</td>
                    <td >${student.feeTypeId.type}</td>
                    <td ><g:formatDate format="yyyy-MM-dd" date="${student.paymentDate}"/></td>
                    %{--<td >91${student.mobileNo}</td>--}%
                </tr>
            </g:each>
        </table>
    </g:elseif>




<g:elseif test="${totalListBySessionComprative}">
    <h3>Total Students In All Courses For Different Sessions</h3>
    <table style=" text-align: center" class="gridtable">
        <tr>
            <th rowspan="2">Name of the Study Centre
            </th>
            <th colspan="${sessionVal.size()}">Session </th>
        </tr>
        <tr>
            <g:each in="${sessionVal}" var="item">
                <th>${item}</th>
            </g:each>
        </tr>
        <g:each in="${totalListBySessionComprative}" var="item">
            <tr >
                <td>${item.key}</td>
                <g:each in="${item.value}" var="value">
                    <td> ${value}</td>
                </g:each>
            </tr>
        </g:each>
    </table>
</g:elseif>


<g:elseif test="${totalListApprovedUnapprovedRollNo}">
%{--<h3> Total Students In ${totalListByCourse.getAt(0).programDetail[0].courseName} For ${admissionUnapprovedSession} Session</h3>--}%
    <table style=" text-align: center" class="gridtable">
        <th>Roll No</th>
        <th>Name</th>
        <th>Examination Centre</th>
        <th>Mobile No.</th>
        <g:each in="${totalListApprovedUnapprovedRollNo}" var="student">
            <tr >
                <td >${student.rollNo}</td>
                <td >${student.studentName}</td>
                <td >${student.examinationCentre[0].examinationCentreName}</td>
                <td >91${student.mobileNo}</td>
            </tr>
        </g:each>
    </table>
</g:elseif>
<g:else>
    <h3>No Record Found</h3>
</g:else>
 </div>
</body>
</html>