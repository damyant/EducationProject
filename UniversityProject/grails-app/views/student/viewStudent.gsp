<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.text.SimpleDateFormat;  javax.validation.constraints.Null; examinationproject.City; examinationproject.District; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>

    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>--}%
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>--}%
    <style type="text/css">
    </style>


</head>

<body>
<div id="main">
<% SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); %>

<g:hasErrors bean="${studInstance}">
    <div class="errors">
        <g:renderErrors bean="${studInstance}" as="list"/>
    </div>
</g:hasErrors>




<fieldset class="form">
<g:uploadForm controller="student" action="submitRegistration" method='post' enctype="multipart/form-data"
              id="studentRegister" name="studentRegister">
<h3>STUDENT INFORMATION SHEET</h3>

<g:hiddenField name="studentId" value="${studInstance?.id}"/>



<table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;">
<!----- First Name ---------------------------------------------------------->
<tr>
    <td>Name of the applicant <span class="university-obligatory"></span></td>
    <td>
        <table class="inner university-table-1-3 university-size-1-1" style="vertical-align: top;">
            <tr>
                <td>
                    <input type="text"  style="margin-left: -10px;text-transform: capitalize;" readonly
                           value="${studInstance?.firstName} ${studInstance?.middleName} ${studInstance?.lastName}"/>
                </td>

            </tr>
        </table>

    </td>
</tr>
<!----- Date Of Birth -------------------------------------------------------->
<tr>
    <td>Date of Birth <span class="university-obligatory"></span></td>
   <td>
        %{--<input type="text" name="d_o_b" maxlength="10" class="university-size-1-2" id="datePick"/>--}%
        <input type="text"  readonly
               value="<g:formatDate format="MM/dd/yyyy" date="${studInstance?.dob}"/>">

    </td>
</tr>

<!----- Program Name ---------------------------------------------------------->
<tr>
    <td>Program<span class="university-obligatory"></span></td>
    %{--<td><input type="text" name="program" maxlength="30" class="university-size-1-2"/>--}%
    <td>

            <input type="text" class="university-size-1-2" readonly
                      value="${studInstance?.programDetail[0].courseName}"/>



    </td>
</tr>
<!----- category ----------------------------------------------------------->
<tr>
    <td>Category <span class="university-obligatory"></span></td>
    <td>
        <div class="radio_options">
            <label><span>${studInstance?.category}</span></label>


        </div>
    </td>
</tr>


<!----- Nationality ----------------------------------------------------------->
<tr>
    <td>Nationality <span class="university-obligatory"></span></td>
    <td>
        <div class="radio_options">
            <label><span>${studInstance?.nationality}</span></label>

            </label>
        </div>
    </td>
</tr>



<!----- Gender ----------------------------------------------------------->
<tr>
    <td>Gender <span class="university-obligatory"></span></td>
    <td>
        <div class="radio_options">
            <label><span>${studInstance?.gender}</span></label>

        </div>
    </td>
</tr>
<!----- State of Domicile ----------------------------------------------------------->
<tr>
    <td>State of Domicile <span class="university-obligatory"></span></td>
    <td>
        <div class="radio_options">
            <label><span>${studInstance?.state}</span></label>

        </div>
    </td>
</tr>

<tr>
    <!----- Mobile Number ---------------------------------------------------------->
    <td>Mobile Number <span class="university-obligatory"></span></td>
    <td>
         <input
            type="text" id="mobileNo" name="mobileNo" readonly maxlength="10" value="${studInstance?.mobileNo}"
           />
    </td>
</tr>
<tr>

    <!----- Contact centre/study centre ---------------------------------------------------------->
    <td>Study centre <span class="university-obligatory"></span></td>
    <td>
        <input type="text" readonly class="university-size-1-2" value="${studInstance.studyCentre[0]?.name}" readonly/>
    </td>
</tr>
<tr>
    <!----- Preference of examination centre ---------------------------------------------------------->
    <td>Preference of examination Centre<span class="university-obligatory"></span></td>
    <td>


            <g:if test="${studInstance?.addressDistrict}">
                    <input type="text"  readonly value="${studInstance?.addressDistrict}"/>
            </g:if>
                    <input type="text"  readonly value="${studInstance?.city[0]?.cityName}"/>


    </td>
</tr>

<g:if test="${studInstance}">
<tr>
    <td>Parent's Name</td>
    <td><input type="text" readonly class="university-size-1-2" value="${studInstance?.parentsName}"/>
    </td>
</tr>
   </g:if>
<tr>
    <td>Complete Mailing Address of Candidate<br/><br/><br/></td>
    <td>
        <table style="width: 100%" border="0px" id="examCenterAddress">
           <g:if test="${studInstance?.studentAddress}">
            <tr>

                <td style="width: 30%;">Address:</td>
                <td style="width: 70%;"><input type="text" readonly
                                               class="university-size-1-2" value="${studInstance?.studentAddress}"/>
                </td>
            </tr>
           </g:if>
            <g:if test="${studInstance?.addressTown}">
            <tr>
                <td style="width: 30%;">Village/Town:</td>
                <td style="width: 70%;"><input type="text" readonly
                                               value="${studInstance?.addressTown}"
                                               class="university-size-1-2"/></td>
            </tr>
          </g:if>
           <g:if test="${studInstance?.addressPO}">
            <tr>

                <td style="width: 30%;">Post Office:</td>
                <td style="width: 70%;"><input type="text" name="addressPO" value="${studInstance?.addressPO}"
                                               readonly maxlength="30" class="university-size-1-2"/>
                </td>
            </tr>
               </g:if>
           <g:if test="${studInstance?.addressPO}">
            <tr>
                <td style="width: 30%;">District:</td>

                <td style="width: 70%;"><input type="text" value="${studInstance?.addressDistrict}" readonly
                                               "/></td>
            </tr>
           </g:if>
            <g:if test="${studInstance?.addressState}">
            <tr>
                <td style="width: 30%;">State:</td>
                <td style="width: 70%;"><input type="text" value="${studInstance?.addressState}" name="addressState" readonly
                                               maxlength="30"
                                               class="university-size-1-2"/>
                </td>
            </tr>
                </g:if>
            <g:if test=" ${studInstance?.addressState}">
            <tr>
                <td style="width: 30%;">Pincode:</td>
                <td style="width: 70%;"><input type="text" value="${studInstance?.addressPinCode}" name="addressPinCode" readonly
                                               maxlength="6"
                                               class="university-size-1-2"
                                               /></td>
            </tr>
            </g:if>
        </table>
    </td>
</tr>
<tr>
    <td>
        Upload recent Passport size Photograph ( black & white, Resolution: [200 X 150] and Size: Less then 50KB )
    </td>
    <td>
    %{--<input type='file' onchange="readURL(this);" />--}%

        <g:if test="${studInstance}">
            <img src="${createLink(controller: 'student', action: 'show', id: studInstance?.id
                    , mime: 'image/jpeg')}" class="university-registration-photo" id="picture1"/>
            <input type='file' id="profileImage" onchange="readURL(this, 'picture1');" class="university-button"
                   name="photograph"/>
        </g:if>


    </td>
</tr>

    <tr>

        <td colspan="2">
            <fieldset>
                <legend>Fee Details</legend>
                <table class="inner">
                    <tr>
                        <td>Bank Name</td>
                        <td><input type="text" value="${feeDetails?.bankId?.bankName}" readonly/></td>
                    </tr>
                    <tr>
                        <td>Branch Name</td>
                        <td><input type="text" value="${feeDetails?.branchId?.branchLocation}" readonly/></td>
                    </tr>
                    %{--<tr>--}%
                        %{--<td>Admission Fee Amount</td>--}%
                        %{--<td><input type="text" value="${feeAmount}"/></td>--}%
                    %{--</tr>--}%
                    <tr>
                        <td>Payment Mode</td>

                        <td><input type="text" value="${feeDetails?.paymentModeId?.paymentModeName}" readonly/></td>

                    </tr>
                    %{--<tr>--}%
                        %{--<td>Reference Number</td>--}%
                        %{--<td><input type="text" value="${feeDetails?.paymentReferenceNumber}" readonly/></td>--}%
                    %{--</tr>--}%
                    <tr>
                        <td>Payment Date</td>
                        <td><input type="text" readonly value="<g:formatDate format="MM/dd/yyyy" date="${feeDetails?.paymentDate}"/>" class="university-size-1-2" id="paymentDate"/></td>
                    </tr>
                </table>
            </fieldset>
        </td>
    </tr>





<!----- Submit and Reset ------------------------------------------------->


</table>

</g:uploadForm>
</fieldset>

</div>

</body>
</html>