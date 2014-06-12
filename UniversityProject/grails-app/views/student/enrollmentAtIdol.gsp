<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/23/2014
  Time: 5:13 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Enrollment At Idol</title>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'jquery.ui.datepicker.js')}"></script>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>--}%
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>--}%

</head>
<script>

</script>
<body>

<div id="main">
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
    %{--<g:set var="index" value="1"/>--}%
        <fieldset class="form">
           <h3>Student Enrollment</h3>
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
            <div class="university-status-message"><div id="errorMessage"></div></div>
            <form id="tempEnrollment">
                <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
                <table class="inner university-size-full-1-1">
                    <tr>
                        <td class="university-size-1-3">Name of the applicant <span class="university-obligatory">*</span></td>
                        <td class="university-size-2-3">
                            <table class="inner university-table-1-3 university-size-1-1" style="vertical-align: top;">
                                <tr>
                                    <td>
                                        <input type="text" tabindex="2" name="firstName" style="margin-left: -10px;text-transform: capitalize;"
                                               onkeypress="return onlyAlphabets(event);"
                                               maxlength="50" class="university-size-1-1" value="" placeholder="First Name"/>

                                    </td>
                                    <td style="vertical-align: top;">
                                        <input type="text" tabindex="2" name="middleName" style="margin-left: -10px;text-transform: capitalize;"
                                               onkeypress="return onlyAlphabets(event);"
                                               maxlength="50" class="university-size-1-1" value="" placeholder="Middle Name"/>
                                    </td>
                                    <td>
                                        <input type="text" tabindex="3" name="lastName" style="margin-left: -10px;text-transform: capitalize;"
                                               onkeypress="return onlyAlphabets(event);"
                                               maxlength="50" class="university-size-1-1" value=""  placeholder="Last Name"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="university-size-1-3">Application Number <span class="university-obligatory">*</span></td>
                        <td class="university-size-2-3">
                            <input type="text" name="applicationNo" tabindex="4" onchange="checkApplicationNumber(this)" onkeypress="return isNumber(event)" maxlength="5" class="university-size-1-2"/>
                            <label id="errorMsg" class="error1"></label>
                        </td>
                    </tr>
                    <tr>
                        <td>Program<span class="university-obligatory">*</span></td>
                        <td>
                            <g:select name="programId" id="programId" optionKey="id" tabindex="5" onchange="checkLastDate(this)" class="university-size-1-2"
                                      optionValue="courseName" from="${programList}" noSelection="['': ' Select Program']"/>
                            <g:hiddenField name="idol" value="idol"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Date of Birth <span class="university-obligatory">*</span></td>
                        <td>
                            %{--<input type="text" name="d_o_b" maxlength="10" class="university-size-1-2" id="datePick"/>--}%
                            <input type="text" name="d_o_b" maxlength="10" tabindex="6" PLACEHOLDER="DD/MM/YYYY" class="university-size-1-2" id="datepicker2">

                        </td>
                    </tr>
                    <tr>
                        <!----- Contact centre/study centre ---------------------------------------------------------->
                        <td>Study centre <span class="university-obligatory">*</span></td>
                        <td>
                            <input type="text" name="studyCentre" class="university-size-1-2" value="${studyCentre?.name}" readonly/>
                            <input type="hidden" name="studyCentreCode" value="${studyCentre?.centerCode}">
                        </td>
                    </tr>
                    <tr>
                        <td>Category <span class="university-obligatory">*</span></td>
                        <td>
                            <div class="radio_options">
                                <label><span>General</span><input type="radio" tabindex="7" name="category" value="General" class="radioInput"/></label>

                                <label><span>MOBC</span><input type="radio" name="category" value="MOBC" class="radioInput"/></label>

                                <label><span>OBC</span><input type="radio" name="category" value="OBC" class="radioInput"/></label>

                                <label><span>SC</span><input type="radio" name="category" value=" SC" class="radioInput" style=""/></label>

                                <label><span>ST</span><input type="radio" name="category" value="S.T" class="radioInput"/></label>

                                <label><span>MINORITY</span><input type="radio" name="category" value="MINORITY COMMUNITY"
                                                                   class="radioInput"/>
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>Gender <span class="university-obligatory">*</span></td>
                        <td>
                            <div class="radio_options">
                                <label><span>Male</span><input type="radio" tabindex="8" name="gender" value="Male" class="radioInput"/></label>
                                <label><span>Female</span><input type="radio" name="gender" value="Female" class="radioInput"/></label>
                                <label><span>Other</span><input type="radio" name="gender" value="Other" class="radioInput"/></label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>Mobile Number <span class="university-obligatory">*</span></td>
                        <td>
                            <input type="text" id="mobileNoCntryCode"  name="mobileNoCntryCode" maxlength="3" value="+91" readonly> - <input
                                type="text" id="mobileNo" name="mobileNo" maxlength="10" tabindex="9"
                                onkeypress="return isNumber(event)"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Select District<span class="university-obligatory">*</span></td>
                        <td>
                            <g:select name="examDistrict" id="district" tabindex="10" optionKey="id"
                                      value=""
                                      class="university-size-1-2"
                                      onChange="showExamCenterList()" optionValue="districtName"
                                      from="${districtList}" noSelection="['': ' Select District']"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Select Preference of examination Centre <span class="university-obligatory">*</span></td>
                        <td>
                            <g:select name="examinationCentre" tabindex="11" id="examinationCentre" class="university-size-1-2" from=" " noSelection="['': 'Select Examination Centre']"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>

                            <input type="button" value="Submit" tabindex="12" onclick="submitTempRegistration()" class="university-button">
                            <input type="reset" value="Reset" tabindex="13" onclick="resetImage()" class="university-button">
                        </td>
                    </tr>
                </table>
            </form>
            %{--kuldeep's code start from here................................................................--}%
            <div id="challanDiv" class="dialog">
                <div id="feeChallanDiv">
                    %{--<div style="width: 100%; margin: 6px auto; display: inline-block;text-align: center;">----------------------------------------BANK COPY----------------------------------------</div>--}%

                    <div style="border: 1px solid; padding: 10px;">
                        <div class="university-size-full-1-1" style="margin-bottom: 25px;"> <div style="float: right"><lable>Challan. No. </lable><label id="challanNo"></label></div>
                            <div class="university-clear-both"></div>
                        </div>

                        <div style="width:100%; margin-left: 3px; margin-top: -6px; text-align: center;text-transform: uppercase;font-size: 14px">

                        <div>State Bank of India</div>

                        <div>Gauhati University Branch (CODE-4332)</div>

                        <div>A/C No. 57846586846</div>

                        <div>INSTITUTE OF DISTANCE AND OPEN LEARNING</div>

                        <div>GAUHATI UNIVERSITY</div>
                    </div>
                        <div style="clear: both; margin-bottom: 10px;"></div>
                        <table width="100%" class="university-table-1-2">
                            <tr><td><lable>Name:</lable></td><td><label id="studentName"></label></td></tr>
                            <tr><td><lable>Roll No:</lable></td><td><label id="studentRollNo"></label></td></tr>
                            <tr><td>Type Of Fee:</td><td><label id="feeType"></label></td></tr>
                            <tr><td><lable>Amount:</lable></td><td><label id="amount"></label>
                                %{--<g:if test="${lateFee!=0}">--}%
                                    <label style="font-size: 13px;display: block" id="lateFee"></label>
                                %{--</g:if>--}%
                            </td></tr>

                            <tr><td style="vertical-align: bottom;">${new Date()}</td><td
                                    style="vertical-align: bottom;height: 63px;"><div
                                        style="width: 100%;text-align: right; bottom: 2px;">Cashier's Signature</div></td></tr>
                        </table>
                    </div>
                </div>
            <input type="button" id="print" value="Print" onclick="printFeeChallan('#feeChallanDiv')">
             </div>

        </fieldset>
      </g:else>
<script type="text/javascript">
    $(function () {
        $(function () {
            $("#datepicker2").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
        });
        $(".dialog").dialog({
            autoOpen: false,
            draggable: false,
            position: ['center',0],
//        maxWidth:600,
//        maxHeight: 500,
            width: 750,
            resizable: false,
            height: 550,
            modal: true,
            title:'Fee Voucher',
            close: function(ev, ui) {
                $.unblockUI();
//            getStudentsList()
            }

        });
    });

</script>

</div>
</body>
</html>