<%--
  Created by IntelliJ IDEA.
  User: Kuldeep
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="examinationproject.StudyCenter; java.text.SimpleDateFormat; javax.validation.constraints.Null; examinationproject.City; examinationproject.District; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            $(".dialog").dialog({
                autoOpen: false,
                draggable: false,
                position: ['center', 0],
                width: 850,
                resizable: false,
                height: 750,
                modal: true,
                title: 'Subject Selection',
                close: function (ev, ui) {
                    $.unblockUI();
                }
            });
        });
    </script>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>--}%
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>--}%
    <style type="text/css">
    </style>
    <script type="text/javascript">
        var gender = "${studInstance?.gender}"
        var category = "${studInstance?.category}"
        var nationality = "${studInstance?.nationality}"
        var isAppliedFor = "${studInstance?.isAppliedFor}"
        var state = "${studInstance?.state}"
        $('#studentRegister').ready(function () {
//            $('#submitButton').attr("disabled", false)
//    alert($("input.radioInput[name='nationality'][value="+nationality+"]").val())
            $("input[name='nationality'][value=" + nationality + "]").attr('checked', 'checked');
            $("input.radioInput[name='category'][value='" + category + "']").attr('checked', 'checked');
            $(".radioInput[name='gender'][value=" + gender + "]").attr('checked', 'checked');
            $(".radioInput[name='state'][value=" + state + "]").attr('checked', 'checked');
            if(isAppliedFor)  {
             $('#isAppliedFor').attr('checked', 'checked')
             $('#registrationNo1').attr('disabled', true)
             $('#registrationNo2').attr('disabled', true)
            }
            $( "#submitButton" ).click(function(event) {
                if($('#studentRegister').valid()) {
                    $(event.target).attr("hidden", "true");
                }
            });
        });
    </script>

</head>

<body>
<div id="main">
<% SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); %>
<g:if test="${flash.message}">
    <div class="message"><div class="university-status-message">${flash.message}</div></div>
</g:if>
<g:hasErrors bean="${studInstance}">
    <div class="errors">
        <g:renderErrors bean="${studInstance}" as="list"/>
    </div>
</g:hasErrors>


<g:if test="${!studyCentre}">
    <fieldset class="form">
        <div class='body'>
            <div class='errors'><div class="university-not-authorized">
                <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                        style="margin: auto;"/></p>

                <p><g:message code="registration.denied.message"/></p>
            </div></div>
        </div>
    </fieldset>
</g:if>
<g:elseif test="${!programList}">
    <fieldset class="form">
        <div class='body'>
            <div class='errors'><div class="university-not-authorized">
                <p><img src="${resource(dir: 'images', file: 'cancel.png')}" alt="Not Authorized"
                        style="margin: auto;"/></p>

                <p><g:message code="Admission.denied.message"/></p>
            </div></div>
        </div>
    </fieldset>
</g:elseif>
<g:else>
<fieldset class="form">
<g:uploadForm controller="student" action="submitRegistration" method='post' enctype="multipart/form-data"
              id="studentRegister" name="studentRegister">
<h3>STUDENT INFORMATION SHEET</h3>

<g:hiddenField name="studentId" value="${studInstance?.id}"/>

<sec:ifNotLoggedIn>
    <table class="university-size-1-1" cellspacing="0" cellpadding="0" style="border: 1px solid brown;margin: 15px auto;">
        <tr>
            <td style="line-height:10px;font-size: 10px;">
              <div><i>  * Demand Draft in favour of</i><b> IDOL, Gauhati University.</b></div>
              <div style="margin-top: 4px;"><i>* Take print of the Generated PDF and send to</i>
                <b>IDOL, Gauhati University</b><i> along with the </i><b>Bank Draft</b>.</div>
            </td>
        </tr>
    </table>
</sec:ifNotLoggedIn>

<div style="margin-left: 10px;"><label><h6>All [<span
        class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
<table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;">
<!----- First Name ---------------------------------------------------------->
<tr>
    <td>Name of the applicant <span class="university-obligatory">*</span></td>
    <td>
        <table class="inner university-table-1-3 university-size-1-1" style="vertical-align: top;">
            <tr>
                <td>
                    <input type="text" placeholder="First Name" name="firstName"
                           style="margin-left: -10px;"
                           onkeypress="return onlyAlphabets(event);"
                           maxlength="50" class="university-size-1-1" value="${studInstance?.firstName}"/>
                </td>
                <td style="vertical-align: top;">
                    <input type="text" placeholder="Middle Name" name="middleName"
                           style="margin-left: -10px;"
                           onkeypress="return onlyAlphabets(event);"
                           maxlength="50" class="university-size-1-1" value="${studInstance?.middleName}"/>
                </td>
                <td>
                    <input type="text" placeholder="Last Name" name="lastName"
                           style="margin-left: -10px;"
                           onkeypress="return onlyAlphabets(event);"
                           maxlength="50" class="university-size-1-1" value="${studInstance?.lastName}"/>
                </td>
            </tr>
        </table>

    </td>
</tr>
<g:if test="${studInstance?.rollNo}">
    <tr>
        <td>Roll Number <span class="university-obligatory">*</span></td>
        <td>
            <input type="text" name="rollNo" readonly
                   class="university-size-1-1" value="${studInstance?.rollNo}"/>

        </td>
    </tr>
</g:if>
<!----- Program Name ---------------------------------------------------------->
<tr>
    <td>Programme<span class="university-obligatory">*</span></td>
    %{--<td><input type="text" name="program" maxlength="30" class="university-size-1-2"/>--}%
    <td>
        <sec:ifNotLoggedIn>
            <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2"
                      value="${studInstance?.programDetail?.id?.get(0)}"
                      optionValue="courseName" onchange="loadProgramFeeAmount(this),checkCourseCodeLength(this)" from="${programList}"
                      noSelection="['': ' Select Programme']"/>
        </sec:ifNotLoggedIn>
        <sec:ifLoggedIn>
            <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2"
                      value="${studInstance?.programDetail?.id?.get(0)}"
                      optionValue="courseName" onchange="enableApplicationNo(),checkCourseCodeLength(this)" from="${programList}" noSelection="['': ' Select Programme']"/>
        </sec:ifLoggedIn>
        <label id="courseCodeLength" class="error"></label>
    </td>
</tr>
<sec:ifLoggedIn>
    <g:if test="${studInstance}">
        <g:if test="${studInstance.applicationNo}">
            <tr>
                <td class="university-size-1-3">Application Number <span class="university-obligatory">*</span></td>
                <td class="university-size-2-3">
                    <input type="text" name="applicationNo" value="${studInstance.applicationNo.substring(4)}"
                           onchange="return checkApplicationNumber(this)" onkeypress="return isNumber(event)" maxlength="5"
                           class="university-size-1-2"/>

                    <label id="errorMsg" class="error1"></label>
                </td>
            </tr>
        </g:if>
    </g:if>
    <g:else>
        <tr>
            <td class="university-size-1-3">Application Number <span class="university-obligatory">*</span></td>
            <td class="university-size-2-3">
                <input type="text" name="applicationNo" id="applicationNo" value="${studInstance?.applicationNo}" disabled="true"
                       onchange="checkApplicationNumber(this)" onkeypress="return isNumber(event)" maxlength="10"
                       class="university-size-1-2"/>
                <label id="errorMsg" class="error1"></label>
            </td>
        </tr>
    </g:else>
</sec:ifLoggedIn>
<!----- Date Of Birth -------------------------------------------------------->
<tr>
    <td>Date of Birth <span class="university-obligatory">*</span></td>



    <td>
        %{--<input type="text" name="d_o_b" maxlength="10" class="university-size-1-2" id="datePick"/>--}%
        <input type="text" name="d_o_b" maxlength="10" PLACEHOLDER="DD/MM/YYYY" class="university-size-1-2"
               id="datepicker"
               value="<g:formatDate format="dd/MM/yyyy" date="${studInstance?.dob}"/>">

    </td>
</tr>



<!----- category ----------------------------------------------------------->
<tr>
    <td>Category <span class="university-obligatory">*</span></td>
    <td>
        <div class="radio_options">
            <label><span>General</span><input type="radio" name="category" value="General" class="radioInput"/></label>

            <label><span>MOBC</span><input type="radio" name="category" value="MOBC" class="radioInput"/></label>

            <label><span>OBC</span><input type="radio" name="category" value="OBC" class="radioInput"/></label>

            <label><span>SC</span><input type="radio" name="category" value="SC" class="radioInput" style=""/></label>

            <label><span>ST</span><input type="radio" name="category" value="S.T" class="radioInput"/></label>

            <label><span>MINORITY</span><input type="radio" name="category" value="MINORITY COMMUNITY"
                                               class="radioInput"/>
            </label>
        </div>
    </td>
</tr>


<!----- Nationality ----------------------------------------------------------->
<tr>
    <td>Nationality <span class="university-obligatory">*</span></td>
    <td>
        <div class="radio_options">
            <label><span>Indian</span><input type="radio" name="nationality" value="Indian" class="radioInput"/></label>
            <label><span>Non-Indian</span><input type="radio" name="nationality" value="Non-Indian" class="radioInput"/>
            </label>
        </div>
    </td>
</tr>



<!----- Gender ----------------------------------------------------------->
<tr>
    <td>Gender <span class="university-obligatory">*</span></td>
    <td>
        <div class="radio_options">
            <label><span>Male</span><input type="radio" name="gender" value="Male" class="radioInput"/></label>
            <label><span>Female</span><input type="radio" name="gender" value="Female" class="radioInput"/></label>
            %{--<label><span>Other</span><input type="radio" name="gender" value="Other" class="radioInput"/></label>--}%

        </div>
    </td>
</tr>
<!----- State of Domicile ----------------------------------------------------------->
<tr>
    <td>State of Domicile <span class="university-obligatory">*</span></td>
    <td>
        <div class="radio_options">
            <label><span>Assam</span><input type="radio" name="state" value="Assam" class="radioInput"/></label>
            <label><span>Others</span><input type="radio" name="state" value="Others" class="radioInput"/></label>
        </div>
    </td>
</tr>

<tr>
    <!----- Mobile Number ---------------------------------------------------------->
    <td>Mobile Number <span class="university-obligatory">*</span></td>
    <td>
        <input type="text" id="mobileNoCntryCode" name="mobileNoCntryCode" maxlength="3" value="+91" readonly> - <input
            type="text" id="mobileNo" name="mobileNo" maxlength="10" value="${studInstance?.mobileNo}"
            onkeypress="return isNumber(event)"/>
    </td>
</tr>
<tr>

    <!----- Contact centre/study centre ---------------------------------------------------------->
   <td>Study centre <span class="university-obligatory">*</span></td>
    <g:if test="${studInstance}">
           <sec:ifAnyGranted roles="ROLE_ADMIN">
               <td>
                   <g:select name="studyCentre" id="studyCentre" optionKey="id" class="university-size-1-2"
                             value="${studInstance?.studyCentre?.id?.get(0)}"
                             optionValue="name" from="${StudyCenter.list()}" noSelection="['': ' Select StudyCentre']"/>
               </td>
           </sec:ifAnyGranted>
            <sec:ifNotGranted roles="ROLE_ADMIN" >
                <td>
                    <input type="text" name="studyCentre" class="university-size-1-2" value="${studInstance?.studyCentre?.name?.get(0)}" readonly/>
                </td>
            </sec:ifNotGranted>
    </g:if>
    <g:if test="${!studInstance}">
         <td>
            <input type="text" name="studyCentre" class="university-size-1-2" value="${studyCentre?.name}" readonly/>
        </td>
    </g:if>
</tr>
<tr>

    <!----- Preference of examination centre ---------------------------------------------------------->
    <td>Select Preference of examination Centre<span class="university-obligatory">*</span></td>
    <td>

        <table id="examCenterSelect">
            <tr>
                <td class="university-size-1-2">

                    <g:select name="examDistrict" id="district" optionKey="id"
                              value="${studInstance?.city?.district?.id?.get(0)}"
                              class="university-size-1-2"
                              onChange="showExamCenterList()" optionValue="districtName"
                              from="${districtList}" noSelection="['': ' Select District']"/>
                </td>
            </tr>
            <tr>
                <td>
                    <g:if test="${studInstance}">
                        <g:select name="examinationCentre" id="examinationCentre" class="university-size-1-2"
                                  optionKey="id"
                                  optionValue="cityName"
                                  from="${City.findAllByDistrict(District.get(studInstance?.city?.district?.id?.get(0)))}"
                                  value="${studInstance?.city?.id?.get(0)}"
                                  noSelection="['': 'Select District']"/>
                    </g:if>
                    <g:else>
                        <g:select name="examinationCentre" id="examinationCentre" class="university-size-1-2" from=" "
                                  noSelection="['': 'Select Examination Centre']"/>
                    </g:else>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <!----- GU Registration Number( If already registered in GU) ---------------------------------------------------------->
    <td>GU Registration Number (if already registered in GU)</td>
    <td>
        <input type="text" name="registrationNo1" id="registrationNo1" maxlength="9" onchange="enableDisableCheckbox()"
               class="university-size-1-4"  value="${studInstance?.registrationNo1}"
               onkeypress="return isNumber(event)"/> Of
        <input type="text" name="registrationNo2" id="registrationNo2" maxlength="7" class="university-size-1-4"
               onkeypress="return isNumberWithDash(event)"  value="${studInstance?.registrationNo2}"/>

         <g:if test="${!studInstance?.registrationNo2}">
             &nbsp;&nbsp;&nbsp;Or&nbsp;&nbsp;&nbsp;
        <label style="text-align: left">
        <input type="checkbox" value="Y" onclick="enableDisableTextBox()" name="isAppliedFor"
                                                                                     id="isAppliedFor"
                                                                                     class="university-size-1-4"/>A/F
    </label>
             <input type="text" name="regNoCheck" id="regNoCheck" value="" style="width: 1px;height: 1px;border: 0px;"/>
             </g:if>
    </td>
</tr>

<!----- Address ---------------------------------------------------------->
<tr>
    <!----- Mobile Number ---------------------------------------------------------->
    <td>Parent's Name<span class="university-obligatory">*</span></td>
    <td><input type="text" id="parentsName" class="university-size-1-2" name="parentsName" maxlength="100"
               value="${studInstance?.parentsName}"
               onkeypress="return onlyAlphabets(event);"/>
    </td>
</tr>
<tr>
    <td>Complete Mailing Address of Candidate<span class="university-obligatory">*</span><br/><br/><br/></td>
    <td>
        <table style="width: 100%" id="examCenterAddress">
            <tr>

                <td style="width: 30%;">Address:</td>
                <td style="width: 70%;"><input type="text" id="studentAddress" name="studentAddress" maxlength="30"
                                               class="university-size-1-2" value="${studInstance?.studentAddress}"/>
                </td>
            </tr>
            <tr>
                <td style="width: 30%;">Village/Town:</td>
                <td style="width: 70%;"><input type="text" id="townName" name="addressTown" maxlength="30"
                                               value="${studInstance?.addressTown}"
                                               onkeypress="return isAlphaNumeric(event);"
                                               class="university-size-1-2"/></td>
            </tr>
            <tr>

                <td style="width: 30%;">Post Office:</td>
                <td style="width: 70%;"><input type="text" id="poAddress" name="addressPO"
                                               onkeypress="return onlyAlphabets(event);"
                                               value="${studInstance?.addressPO}"
                                               maxlength="30" class="university-size-1-2"/>
                </td>
            </tr>
            <tr>
                <td style="width: 30%;">District:</td>

                <td style="width: 70%;"><input type="text" value="${studInstance?.addressDistrict}"
                                               name="addressDistrict" onkeypress="return onlyAlphabets(event);"
                                               maxlength="30" id="addDistrict"
                                               class="university-size-1-2"/></td>
            </tr>
            <tr>
                <td style="width: 30%;">State:</td>
                <td style="width: 70%;"><input type="text" value="${studInstance?.addressState}" name="addressState"
                                               onkeypress="return onlyAlphabets(event);"
                                               maxlength="30" id="stateAddress"
                                               class="university-size-1-2"/>
                </td>
            </tr>
            <tr>
                <td style="width: 30%;">Pincode:</td>
                <td style="width: 70%;"><input type="text" value="${studInstance?.addressPinCode}" name="addressPinCode"
                                               id="pincode"
                                               maxlength="6"
                                               class="university-size-1-2"
                                               onkeypress="return isNumber(event)"/></td>
            </tr>
        </table>
    </td>
</tr>
<sec:ifNotLoggedIn>
    <tr>
        <td>
            Upload recent Passport size Photograph ( black & white, Resolution: [200 X 150] and Size: Less then 50KB )<span
                class="university-obligatory">*</span>
        </td>
        <td>
        %{--<input type='file' onchange="readURL(this);" />--}%

            <g:if test="${studInstance}">
                <img src="${createLink(controller: 'student', action: 'show', id: studInstance?.id
                        , mime: 'image/jpeg')}" class="university-registration-photo" id="picture1"/>
                <input type='file' id="profileImage" onchange="readURL(this, 'picture1');" class="university-button"
                       name="photograph"/>
            </g:if>
            <g:else>
                <div id="profile-image"><img src="" alt="Space for Photograph "
                                             class="university-registration-photo" id="picture2"/></div>
                <input type='file' id="profileImage" onchange="readURL(this, 'picture2');" class="university-button"
                       name="photograph"/>
            </g:else>
            <input type="text" id="imageValidate" name="imageValidate" value="" style="width: 1px;height: 1px;border: 0px;"/>

        </td>
    </tr>
</sec:ifNotLoggedIn>
<sec:ifLoggedIn>
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
            <g:else>
                <div id="profile-image" class='registration-image-div'><img src="" alt="Space for Photograph "
                                                                            class="university-registration-photo"
                                                                            id="picture"/></div>
                <input type='file' id="profileImage" onchange="readURL(this, 'picture');" class="university-button"
                       name="photograph"/>
            </g:else>

        </td>
    </tr>
</sec:ifLoggedIn>

<sec:ifNotLoggedIn>
    <tr>
        <td colspan="2">
            <fieldset>
                <legend>Fee Details</legend>
                <table class="inner">
                    <tr>
                        <td>Payment Mode<span class="university-obligatory">*</span></td>
                        <td><g:select name="paymentMode" class="university-size-1-2" optionKey="id"
                                      optionValue="paymentModeName" id="paymentMode"
                                      from="${paymentMode}" noSelection="['': ' Select PaymentMode']"/></td>
                    </tr>
                    <tr>
                        <td>Bank Name<span class="university-obligatory">*</span></td>
                        <td><g:select name="bankName" class="university-size-1-2" id="bankName" optionKey="id"
                                      optionValue="bankName"
                                      from="${bankName}" noSelection="['': ' Select Bank']"
                                      onchange="loadBranch(this)"/>
                            <input type="text" disabled name="bankName" hidden="true" id="otherBank" class="university-size-1-2"/>
                            <label style="margin-left: 10px;">
                            <input type="checkbox" id="bankCheckBox" name="bankCheckBox" onclick="putOtherBank()" value="other"/>Other</label>
                        </td>
                    </tr>
                    <tr>
                        <td>Branch Name<span class="university-obligatory">*</span></td>
                        <td><g:select name="branchName" class="university-size-1-2" optionKey=""
                                      optionValue="" id="branchLocation"
                                      from="" noSelection="['': ' Select Branch']"/>
                            <input type="text" disabled name="branchName" hidden="true" id="otherBankBranch" class="university-size-1-2"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Admission Fee Amount<span class="university-obligatory">*</span></td>
                        <td><input type="text" name="admissionFeeAmount" class="university-size-1-2"
                                   id="admissionFeeAmount" readonly/></td>
                    </tr>

                    <tr>
                        <td>DD/RTGS/NEFT Number<span class="university-obligatory">*</span></td>
                        <td><input type="text" name="feeReferenceNumber" maxlength="8" class="university-size-1-2"
                                   onkeypress="return isNumber(event)"
                                   id="feeReferenceNumber"/></td>
                    </tr>
                    <tr>
                        <td>Payment Date<span class="university-obligatory">*</span></td>
                        <td><input type="text" name="paymentDate" onkeypress="disableKeyInput(this)"
                                   class="university-size-1-2" id="paymentDate"/></td>
                    </tr>
                </table>
            </fieldset>
        </td>
    </tr>
</sec:ifNotLoggedIn>
<g:if test="${!studInstance}">
<tr>
    <td colspan="2">
        <label id="declaration-label"><input type="checkbox" id="declaration" name="declaration"/>
            I hereby declare that the information as indicated above is true to the best of my knowledge. <span
                class="university-obligatory">*</span>
        </label>
    </td>
</tr>
</g:if>



<!----- Submit and Reset ------------------------------------------------->
<tr>
    <td colspan="2" align="center">
        <input type="submit" value="Submit" id="submitButton" onclick="validate()" class="university-button">
        <input type="reset" value="Reset" onclick="resetImage()" class="university-button">
    </td>
</tr>
<tr>
    <g:if test="${studInstance}">
        <td>
            <input type="hidden" name="studyCentreCode" value="${studInstance?.studyCentre?.id?.get(0)}">
        </td>
    </g:if>
    <g:else>
        <td>
            <input type="hidden" name="studyCentreCode" value="${studyCentre?.centerCode}">
        </td>
    </g:else>
</tr>
</table>


<div id="groupDialog" class="dialog">
    <div>
        <table>
            <tr>
                <td>Mandatory Subjects</td>
            </tr>
            <tr>
                <td><div>Semester 1</div><select multiple="multiple" style="height: 100px; width: 150px;">
                    <option>Introduction to Mass communication</option>
                    <option>Journalism (Reporting / Editing)</option>
                    <option>Advertising  </option>
                    <option>Public Relations</option>
                </select></td></tr>
                <tr>
                <td><div>Semester 2</div><select multiple="multiple" style="height: 100px; width: 150px;">
                    <option>Introduction to Mass communication</option>
                    <option>Journalism (Reporting / Editing)</option>
                    <option>Advertising  </option>
                    <option>Public Relations</option>
                </select></td>
                 </tr>
            <tr>
                <td><div>Semester 3</div><select multiple="multiple" style="height: 100px; width: 150px;">
                    <option>Introduction to Mass communication</option>
                    <option>Journalism (Reporting / Editing)</option>
                    <option>Advertising  </option>
                    <option>Public Relations</option>
                </select>
                </td>
            </tr>
            <tr>
                <td><div>Semester 4</div><select multiple="multiple" style="height: 100px; width: 150px;">
                    <option>Introduction to Mass communication</option>
                    <option>Journalism (Reporting / Editing)</option>
                    <option>Advertising  </option>
                    <option>Public Relations</option>
                </select>
                </td>
            </tr>
            <tr>
                <td><div>Semester 5</div><select multiple="multiple" style="height: 100px; width: 150px;">
                    <option>Introduction to Mass communication</option>
                    <option>Journalism (Reporting / Editing)</option>
                    <option>Advertising  </option>
                    <option>Public Relations</option>
            </select>
            </td>
                <td><div>Group A Subjects</div><select multiple="multiple" style="height: 100px; width: 150px;">
                    <option>Group A: Comparative Literature and Translation Studies</option>
                    <option>Java</option>
                    <option>Group A: Comparative Literature and Translation Studies</option>
                    <option>Java</option>
                </select>
                    <div><input type="radio" name="opt"></div>
                </td>
                <td><div>Group B Subjects</div><select multiple="multiple" style="height: 100px; width: 150px;">
                    <option>Group B: Linguistics and Its Fields of Study</option>
                    <option>Computer Scince</option>
                    <option>Group B: Linguistics and Its Fields of Study</option>
                    <option>Computer Scince</option>
                </select>
                    <div><input type="radio" name="opt"></div>
                </td>
            </tr>
            <tr>
                <td><div>Semester 6</div><select multiple="multiple" style="height: 100px; width: 150px;">
                    <option>Introduction to Mass communication</option>
                    <option>Journalism (Reporting / Editing)</option>
                    <option>Advertising  </option>
                    <option>Public Relations</option>
                </select>
                </td>
            </tr>
        </table>
    </div>



</div>

</g:uploadForm>
</fieldset>
</g:else>
</div>
<script>
    $('#signatureFile').bind('change', function () {
//    alert('This file size is: ' + this.files[0].size/1024/1024 + "MB");
    })
    function resetImage() {
        $("#signature").attr('src', '#')
        $("#picture").attr('src', '#')
    }
    $(function () {
        $(function () {
            $("#datepicker").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
            $("#paymentDate").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
        });
    });
    $('#submitButton').on('click', function () {
        if ($('#studentRegister').valid()) {
            setTimeout(function () {
                $('#studentRegister')[0].reset();
                var abc = $('#picture2').remove();
                $('#profile-image').append('<img src="" alt="Space for Photograph " class="university-registration-photo" id="picture2"/>')
//                  location.reload()
            }, 1000)

        }
    })
</script>
</body>
</html>