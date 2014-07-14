<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ page import="java.text.SimpleDateFormat;javax.validation.constraints.Null; examinationproject.City; examinationproject.District; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Student Details</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>--}%
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>--}%
    <style type="text/css">
    </style>
    <script>
        $(function () {
            var icons = {
                header: "ui-icon-circle-arrow-e",
                activeHeader: "ui-icon-circle-arrow-s"
            };
            $("#accordion").accordion({
                icons: icons,
                heightStyle: "content"
            });
        });
    </script>

</head>

<body>
<div id="main">
<fieldset class="form">
<h3>STUDENT INFORMATION</h3><br/>

<div id="accordion">
<h3>Personal Details</h3>

<div>
    <p>
    <table class="inner " style="width: 100%;margin: auto;">
        <tr>
            <td class="university-size-2-3">
                <div class="main-Content">
                    <g:if test="${studInstance?.firstName}">
                        <div class="label-header">Name</div>

                        <div class="label-content">${studInstance?.firstName} ${studInstance?.middleName} ${studInstance?.lastName}</div>
                    </g:if>
                    <g:if test="${studInstance?.dob}">
                        <div class="label-header">Date of Birth</div>

                        <div class="label-content"><g:formatDate format="dd-MMM-yyyy"
                                                                 date="${studInstance?.dob}"/></div>
                    </g:if>
                    <g:if test="${studInstance?.mobileNo}">
                        <div class="label-header">Mobile Number</div>

                        <div class="label-content">${studInstance?.mobileNo}</div>
                    </g:if>
                    <g:if test="${studInstance?.category}">
                        <div class="label-header">Catagory</div>

                        <div class="label-content">${studInstance?.category}</div>
                    </g:if>
                    <g:if test="${studInstance?.nationality}">
                        <div class="label-header">Nationality</div>

                        <div class="label-content">${studInstance?.nationality}</div>
                    </g:if>
                    <g:if test="${studInstance?.gender}">
                        <div class="label-header">Gender</div>

                        <div class="label-content">${studInstance?.gender}</div>
                    </g:if>
                    <g:if test="${studInstance?.state}">
                        <div class="label-header">State of Domicile</div>

                        <div class="label-content">${studInstance?.state}</div>
                    </g:if>
                    <g:if test="${studInstance?.parentsName}">
                        <div class="label-header">Parent's Name</div>

                        <div class="label-content">${studInstance?.parentsName}</div>
                    </g:if>
                    <g:if test="${studInstance?.studentAddress}">
                        <div class="label-header">Mailing Address</div>

                        <div class="label-content">
                            ${studInstance?.studentAddress}, ${studInstance?.addressTown}<br/><br/>${studInstance?.addressPO}, ${studInstance?.addressDistrict}<br/><br/>
                            ${studInstance?.addressState}-${studInstance?.addressPinCode}
                        </div>
                    </g:if>
                </div>
            </td>
            <td class="university-size-1-3">
                <g:if test="${studInstance?.studentImage}">
                    <img src="${createLink(controller: 'student', action: 'show', id: studInstance?.id, mime: 'image/jpeg')}"
                         class="university-registration-photo" id="picture1"/>
                </g:if>
            </td>
        </tr>
    </table>
</p>
</div>

<h3>Programme Details</h3>

<div>
    <p>
    <table class="inner " style="width: 100%;margin: auto;">
        <tr>
            <td class="university-size-1-2">
                <div class="main-Content">
                    <g:if test="${studInstance?.programDetail}">
                        <div class="label-header">Programme</div>

                        <div class="label-content">${studInstance?.programDetail[0]?.courseName}</div>

                        <div class="label-header">Roll Number</div>

                        <div class="label-content"><g:if
                                test="${studInstance?.rollNo}">${studInstance?.rollNo}</g:if><g:else>Not Generated</g:else></div>

                        <div class="label-header">Current Semester</div>

                        <div class="label-content">${studInstance?.semester}</div>

                        <div class="label-header">Admission Year</div>

                        <div class="label-content">${studInstance?.registrationYear}</div>

                        <div class="label-header">GU Registration Number</div>

                        <div class="label-content"><g:if
                                test="${studInstance?.registrationNo1}">${studInstance?.registrationNo1} of ${studInstance?.registrationNo2}</g:if><g:else>A/F</g:else></div>
                    </g:if>

                </div>
            </td>
            <td class="university-size-1-2">
                <g:if test="${studInstance?.studyCentre}">
                    <div class="label-header">Study Centre Name</div>
                    <div class="label-content">${studInstance?.studyCentre[0]?.name}</div>
                    <div class="label-header">Study Centre Address</div>
                    <div class="label-content">${studInstance?.studyCentre[0]?.address}</div>
                </g:if>
                <g:if test="${studInstance?.city}">
                    <div class="label-header">Preferred Examination Centre</div>
                    <div class="label-content">${studInstance?.city?.district[0]?.districtName}</div>
                    <div class="label-header">Examination Centre District</div>
                    <div class="label-content">${studInstance?.studyCentre[0]?.address}</div>
                </g:if>
            </td>
        </tr>
    </table>
</p>
</div>

<h3>Fees Details</h3>

<div>
<p>
    <g:if test="${feeDetails?.size() > 0}">
        <table class="inner" style="text-align: center;">
            <tr><th>Challan No</th><th>Fee Type</th><th>Term</th><th>Fee Paid Date</th><th>Amount</th><th>Status</th>
            </tr>
            <g:each in="${feeDetails}" var="feeInst">
                <tr><td>${feeInst?.challanNo}</td><td>${feeInst?.feeType?.type}</td><td>${feeInst?.semesterValue}</td><td><g:if
                        test="${feeInst?.paymentDate}"><g:formatDate format="dd-MMM-yyyy"
                                                                     date="${feeInst?.paymentDate}"/></g:if><g:else>Not Paid</g:else></td><td>${feeInst?.paidAmount}</td><td>${feeInst?.isApproved?.status}</td>
                </tr>
            </g:each>
        </table>
    </g:if>
    <g:else>
        <div class="university-status-message">No Fees Paid</div>
    </g:else>
</p>
</div>

<h3>Examination Details</h3>

<div>
    <p>Not Available</p>
</div>

<h3>Other Details</h3>

<div>
    <p>
    <h6 style="text-align: center;text-decoration: underline;margin-bottom: 10px;">Study Materials</h6><br/>
    <g:if test="${studyMaterials}">
        <table class="inner" style="text-align: center;width: 60%;margin: auto;">
            <tr><th style="text-align: center;">Course Name</th></tr>
            <g:each in="${studyMaterials}" var="studyMaterialsInst">
                <tr><td style="text-align: center;">${studyMaterialsInst?.subjectId?.subjectName}</td></tr>
            </g:each>
        </table>
    </g:if>
    <g:else>
        <div style="text-align: center;font-weight: bold;font-size: 12px;">Not Assigned for any Course.</div>
    </g:else>
</p>
<hr style="width: 60%;margin: auto;"/>
    <p>
    <h6 style="text-align: center;text-decoration: underline;margin-bottom: 10px;">Home Assignments</h6>
    <g:if test="${homeAssignment}">
        <table class="inner" style="text-align: center;width: 60%;margin: auto;">
            <tr><th>Semester/Term</th><th>Submission Date</th></tr>
            <g:each in="${homeAssignment}" var="homeAssignmentInst">
                <tr><td>${homeAssignmentInst?.semester}</td><td><g:formatDate format="dd-MMM-yyyy"
                                                                              date="${homeAssignmentInst?.submissionDate}"/></td>
                </tr>
            </g:each>
        </table>
    </g:if>
    <g:else>
        <div style="text-align: center;font-weight: bold;font-size: 12px;">Not submitted for Any semester</div>
    </g:else>

</p>

</div>
</div>
</fieldset>

</div>

</body>
</html>