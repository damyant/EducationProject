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
        border-width:1px;
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
          <h3> Total Students in All Courses for ${sessionVal} Session in ${studyCentreName? studyCentreName:'All Study Centres'}</h3>
          <table style=" text-align: center" class="gridtable">
                     <th>Sr No.</th>
                     <th>Course Name</th>
                     <th>No. Of Students</th>
                     <g:each in="${totalListBySession}" var="item" status="i">
                         <tr>
                            <td >${i+1}</td>
                            <td >${item.key}</td>
                            <td >${item.value}</td>
                         </tr>
                     </g:each>
          </table>
      </g:if>


    <g:elseif test="${totalListBySessions}">
        <h3>Total Students in All Courses for Different Sessions</h3>
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
          <h3> Total Students in ${totalListByCourse.getAt(0).programDetail[0].courseName} for ${courseSession} Session</h3>
           <table style=" text-align: center" class="gridtable">
                    <th>Sr No.</th>
                    <th>Roll No</th>
                    <th>Name</th>
                    <th>Study Centre</th>
                    <th>Examination Centre</th>
                    <th>Mobile No.</th>
                    <th>Status</th>
                   <g:each in="${totalListByCourse}" var="student" status="i">
                        <tr >
                            <td >${i+1}</td>
                            <td >${student.rollNo? student.rollNo:'Not Generated'}</td>
                            <td >${student?.firstName} ${student?.middleName} ${student?.lastName} </td>
                            <td >${student.studyCentre[0].name}</td>
                            <td >${student.examinationCentre[0].examinationCentreName}</td>
                            <td >91${student.mobileNo}</td>
                            <td >${student.status.status}</td>
                        </tr>
                   </g:each>
           </table>
      </g:elseif>


<g:elseif test="${totalListDailyAdmission}">
    <h3> List of Students Who Have Taken Admission from Date ${fromDate} to ${toDate} at ${studyCentreName? studyCentreName:'All'} StudyCentre</h3>
    <table style=" text-align: center" class="gridtable">
        <th>Sr No.</th>
        <th>Roll No</th>
        <th>Name</th>
        <g:if test="${!studyCentreName}">
        <th>Study Centre</th>
        </g:if>
        <th>Examination Centre</th>
        <th>Mobile No.</th>
        <th>Status</th>
        <g:each in="${totalListDailyAdmission}" var="student" status="i">
            <tr>
                <td >${i+1}</td>
                <td >${student.rollNo? student.rollNo:'Not Generated'}</td>
                <td >${student?.firstName} ${student?.middleName} ${student?.lastName} </td>
                <g:if test="${!studyCentreName}">
                <td >${student.studyCentre[0].name}</td>
                </g:if>
                <td >${student.city[0].cityName}</td>
                <td >91${student.mobileNo}</td>
                <td >${student.status.status}</td>
            </tr>
        </g:each>
    </table>
</g:elseif>







      <g:elseif test="${totalListByStudyCentre}">
            <h3> Total Students in All Courses for ${studyCentreSession} Session in ${studyCentreName}</h3>
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
        <h3> Total Students in All Courses for ${examinationCentreSession} Session in ${examinationCentre} Centre</h3>
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
            <h3> Total Students in All Courses for ${categorySession} Session in Different Category</h3>
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
                <h3 style="width: 100%"> Total Students in All Courses for ${categoryGenderSession} Session in Different Category and Gender</h3>
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
        <g:if test="${value=='admissionUnapproved'}">
        <h3> Unapproved Students in ${studyCentre ? studyCentre+' Study Centres ' : (totalListByAdmissionApprovedUnapproved.getAt(0).studyCentre[0].name)} for ${admissionApprovedUnapprovedSession} Session</h3>
        </g:if>
        <g:if test="${value=='admissionApproved'}">
            <h3> Approved Students in ${studyCentre ? studyCentre+' Study Centres ' : (totalListByAdmissionApprovedUnapproved.getAt(0).studyCentre[0].name)} for ${admissionApprovedUnapprovedSession} Session</h3>
        </g:if>
        <table style=" text-align: center" class="gridtable">
            <th>Sr No.</th>
            <th>Roll No</th>
            <th>Name</th>
            <th>Course Code</th>
            <th>Examination Centre</th>
            <th>Mobile No.</th>
            <g:each in="${totalListByAdmissionApprovedUnapproved}" var="student" status="i">
                <tr >
                    <td >${i+1}</td>
                    <td >${student.rollNo? student.rollNo:'Not Generated'}</td>
                    <td >${student?.firstName} ${student?.lastName} ${student?.middleName}</td>
                    <td >${student.programDetail[0].courseCode}</td>
                    <td >${student.city[0]?.cityName}</td>
                    <td >91${student.mobileNo}</td>
                </tr>
            </g:each>
        </table>
    </g:elseif>



<g:elseif test="${totalListBySelfRegistration}">
        <h3> Self Registered Students for ${admissionSelfRegistrationSession} Session</h3>

    <table style=" text-align: center" class="gridtable">
        <th>Sr No.</th>
        <th>Roll No</th>
        <th>Name</th>
        <th>Course Code</th>
        <th>Examination Centre</th>
        <th>Mobile No.</th>
        <g:each in="${totalListBySelfRegistration}" var="student" status="i">
            <tr >
                <td >${i+1}</td>
                <td >${student.rollNo? student.rollNo:'Not Generated'}</td>
                <td >${student?.firstName} ${student?.lastName} ${student?.middleName}</td>
                <td >${student.programDetail[0].courseCode}</td>
                <td >${student.city[0]?.cityName}</td>
                <td >91${student.mobileNo}</td>
            </tr>
        </g:each>
    </table>
</g:elseif>



    <g:elseif test="${totalListByStudyCentreFeePaid}">
        <h3> Fees Paid Report at ${studyCentreName? studyCentreName:'All'} Study Centre Between ${fromDate} to ${toDate}</h3>
        <table style=" text-align: center; border: 0px" class="gridtable">
            <th style="border: 0px">Name</th>
            <th style="border: 0px">Roll No</th>
            <th style="border: 0px">Challan No</th>
            <th style="border: 0px">Amount</th>

            %{--<th>Mobile No.</th>--}%
            <g:each in="${totalListByStudyCentreFeePaid}" var="feeObj" status="i">
              <g:if test="${ (i % 2) == 0}">
                  %{--<tr>${i}</tr>--}%
                  <tr><b>Fee Type: ${feeObj[0].feeType.type}</b></tr>
                  <g:each in="${0..feeObj.size()-1}" var='j'>
                      %{--<tr >Fee Type: ${feeObj[j].feeType.type}</tr>--}%
                      <tr>
                          <td style="border: 0px">${feeObj[j].student.firstName} ${feeObj[j].student.middleName? feeObj[j].student.middleName:''} ${feeObj[j].student.lastName? feeObj[j].student.lastName:''}</td>
                          <td style="border: 0px" >${feeObj[j].student.rollNo}</td>
                          <td style="border: 0px">${feeObj[j].challanNo}</td>
                          <td style="border: 0px">${feeObj[j].paidAmount}</td>
                      </tr>
                  </g:each>
              </g:if>
                <g:if test="${ (i % 2) != 0}">
                <tr><td style="border: 0px"></td><td style="border: 0px"></td><td style="border: 0px"><b>Total for the group</b></td><td style="border: 0px"><b>${feeObj}</b></td></tr>
                </g:if>
            </g:each>
        </table>
    </g:elseif>



<g:elseif test="${totalListByPaymentMode}">
    <h3> Fees Paid Report Between ${fromDate} to ${toDate} and Payment Mode is ${paymentMode}</h3>
    <table style=" text-align: center; border: 0px" class="gridtable">
        <th style="border: 0px">Sr No</th>
        <th style="border: 0px">Name</th>
        <th style="border: 0px">Roll No</th>
        <th style="border: 0px">Challan No</th>
        <th style="border: 0px">Amount</th>
        <th style="border: 0px">Fee Type</th>
        <g:each in="${totalListByPaymentMode}" var="feeObj" status="i">
                    <tr>
                        <td style="border: 0px" >${i+1}</td>
                        <td style="border: 0px">${feeObj.student.firstName} ${feeObj.student.middleName? feeObj.student.middleName:''} ${feeObj.student.lastName? feeObj.student.lastName:''}</td>
                        <td style="border: 0px" >${feeObj.student.rollNo}</td>
                        <td style="border: 0px">${feeObj.challanNo}</td>
                        <td style="border: 0px">${feeObj.paidAmount}</td>
                        <td style="border: 0px">${feeObj.feeType.type}</td>
                    </tr>
        </g:each>
    </table>
</g:elseif>

<g:elseif test="${totalListBySessionComprative}">
    <h3>Total Students in All Courses for Different Sessions</h3>
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
<h3> Total ${type} Students in ${totalListApprovedUnapprovedRollNo.getAt(0).studyCentre[0].name} for ${approvedUnapprovedSessionVal} Session</h3><br></br>
    <h3>Course Name: ${program? program : 'All Programmes'} </h3>
    <table style=" text-align: center" class="gridtable">
        <th>Sr No</th>
        <th>Roll No</th>
        <th>Name</th>
        <th>Examination Centre</th>
        <th>Mobile No.</th>
        <g:each in="${totalListApprovedUnapprovedRollNo}" var="student" status="i">
            <tr >
                <td >${i+1}</td>
                <td >${student.rollNo? student.rollNo:'Not Generated'}</td>
                <td >${student.firstName} ${student.middleName} ${student.lastName}</td>
                <td >${student.city[0].cityName}</td>
                <td >91${student.mobileNo}</td>
            </tr>
        </g:each>
    </table>
</g:elseif>

%{--Added By DIgvijay--}%
<g:elseif test="${totalListByDailyFeePaid}">
    <h3 style="text-align: center">Daily Fees Report of as on 19/05/2014  ${params.feeToDate}/> </h3>

    <table style=" text-align: left" class="gridtable">
        <th>Payment Type</th>
        <th>Payment Mode</th>
        <g:each in="${totalListByDailyFeePaid}" var="feeDetails">
            <tr >
                <td >${feeDetails.feeTypeId.type}</td>
                <td >${feeDetails.paymentModeId.paymentModeName}</td>
            </tr>
        </g:each>
    </table>

    <table style=" text-align: center" class="gridtable">
        <th>Sr No</th>
        <th>Student Name</th>
        <th>Roll No</th>
        <th>Challan No</th>
        <th>Amount</th>
        <g:each in="${totalListByDailyFeePaid}" var="feeDetails" status="i">
            <tr >
                <td >${i+1}</td>
                <td >${feeDetails.studentId.firstName} ${feeDetails.studentId.middleName} ${feeDetails.studentId.lastName}</td>
                <td >${feeDetails.studentId.rollNo}</td>
                <td >${feeDetails.studentId.challanNo}</td>
                <td >${feeDetails.studentId.lastName}</td>
            </tr>
        </g:each>
    </table>

    <table style=" text-align: right" class="gridtable">
        <th>Group Total</th>
        <tr>
            <td>Add all Amount</td>
        </tr>
    </table>
</g:elseif>

<g:else>
    <h3>No Record Found</h3>
</g:else>
 </div>
</body>
</html>