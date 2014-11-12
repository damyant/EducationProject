<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 9/19/14
  Time: 1:18 PM
--%>

<%@ page import="java.text.SimpleDateFormat" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Employee Page</title>
    <script type="text/javascript">
        var category = "${studInstance?.category}"
        var nationality = "${studInstance?.nationality}"
        var isAppliedFor = "${studInstance?.isAppliedFor}"
        var state = "${studInstance?.state}"
        $('#document').ready(function () {
            if(${params.id}){
               var gender = "${employeeObj?.gender}"
                $("input[name='gender'][value=" + gender + "]").attr('checked', 'checked');
            }
        });
    </script>

</head>

<body>

<div id="main">
    <fieldset class="form">
        %{--<h3>Empolyee </h3>--}%
<g:form  controller="employe" action="saveEmployee">
    <div style="margin-left: 10px;"><label><h6>All [<span
            class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
    <table class="inner university-size-full-1-1">
        <tr>
            <g:hiddenField name="employeeId" value="${employeeObj?.id}" />
            <td class="university-size-1-3">Name of the Employee <span class="university-obligatory">*</span></td>
            <td class="university-size-2-3">
                <table class="inner university-table-1-3 university-size-1-1" style="vertical-align: top;">
                    <tr>
                        <td>
                            <input type="text" tabindex="1" name="firstName"
                                   style="margin-left: -10px;"
                                   onkeypress="return onlyAlphabets(event);"
                                   maxlength="50" class="university-size-1-1" value="${employeeObj?.firstName}" placeholder="First Name"/>

                        </td>
                        <td style="vertical-align: top;">
                            <input type="text" tabindex="2" name="middleName"
                                   style="margin-left: -10px;"
                                   onkeypress="return onlyAlphabets(event);"
                                   maxlength="50" class="university-size-1-1" value="${employeeObj?.middleName}" placeholder="Middle Name"/>
                        </td>
                        <td>
                            <input type="text" tabindex="3" name="lastName"
                                   style="margin-left: -10px;"
                                   onkeypress="return onlyAlphabets(event);"
                                   maxlength="50" class="university-size-1-1" value="${employeeObj?.lastName}" placeholder="Last Name"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>

        <tr>
            <td class="university-size-1-3">Employee Code <span class="university-obligatory">*</span></td>
            <td class="university-size-2-3">
                <input type="text" name="employeeCode" id="employeeCode" tabindex="5" value="${employeeObj?.employeeCode}"
                       onkeypress="return isNumber(event)" maxlength="10" class="university-size-1-2" />
                <label id="errorMsg" class="error1"></label>
            </td>
        </tr>

        <tr>
            <td>Date of Birth <span class="university-obligatory">*</span></td>
            <td>
                <input type="text" name="dob" maxlength="10" tabindex="6" PLACEHOLDER="DD/MM/YYYY" value="<g:formatDate format="MM/dd/yyyy" date="${employeeObj?.dob}"/>"
                       class="university-size-1-2" id="datepicker2">

            </td>
        </tr>
        <tr>
            <td>Gender <span class="university-obligatory">*</span></td>
            <td>
                <div class="radio_options">
                    <label><span>Male</span><input type="radio" tabindex="8" name="gender" value="Male"
                                                   class="radioInput"/></label>
                    <label><span>Female</span><input type="radio" name="gender" value="Female" class="radioInput"/>
                    </label>
                    <label><span>Other</span><input type="radio" name="gender" value="Other" class="radioInput"/>
                    </label>
                </div>
            </td>
        </tr>
        <tr>
            <td>Designation <span class="university-obligatory">*</span></td>
            <td>
                <input type="text" name="designation" maxlength="10" tabindex="6"
                       class="university-size-1-2" value="${employeeObj?.designation}">

            </td>
        </tr>
        <tr>
            <td class="university-size-1-3">Current Address <span class="university-obligatory">*</span></td>
            <td class="university-size-2-3">
                <g:textArea  name="currentAddress" id="currentAddress" tabindex="5" rows="5" value="${employeeObj?.currentAddress}"
                         class="university-size-1-2" />
                <label id="errorMsg" class="error1"></label>
            </td>
        </tr>
        <tr>
            <td class="university-size-1-3">Permanent Address <span class="university-obligatory">*</span></td>
            <td class="university-size-2-3">
                <g:textArea name="permanentAddress" id="permanentAddress" tabindex="5" cols="5" rows="5" value="${employeeObj?.permanentAddress}"
                       class="university-size-1-2" />
                <label id="errorMsg" class="error1"></label>
            </td>
        </tr>
        <tr>
            <td>Mobile Number <span class="university-obligatory">*</span></td>
            <td>
                <input type="text" id="mobileNoCntryCode" name="mobileNoCntryCode" maxlength="3" value="+91"
                       readonly> - <input
                    type="text" id="mobileNo" name="mobileNo" maxlength="10" tabindex="9" value="${employeeObj?.mobileNo}"
                    onkeypress="return isNumber(event)"/>
            </td>
        </tr>
        <tr>
            <td>Date of Joining <span class="university-obligatory">*</span></td>
            <td>
                <input type="text" name="dateOfJoining" maxlength="10" tabindex="6" PLACEHOLDER="DD/MM/YYYY"
                       class="university-size-1-2" id="datepicker3" value="<g:formatDate format="MM/dd/yyyy" date="${employeeObj?.dateOfJoining}"/>">

            </td>
        </tr>
        <tr>
            <td>PAN Card Number <span class="university-obligatory">*</span></td>
            <td>
                <input type="text" name="panNumber" maxlength="10" tabindex="6"
                       class="university-size-1-2" id="panNumber" value="${employeeObj?.panNumber}">

            </td>
        </tr>
        %{--<tr>--}%
            %{--<td>Select District<span class="university-obligatory">*</span></td>--}%
            %{--<td>--}%
                %{--<g:select name="examDistrict" id="district" tabindex="10" optionKey="id"--}%
                          %{--value=""--}%
                          %{--class="university-size-1-2"--}%
                          %{--onChange="showExamCenterList()" optionValue="districtName"--}%
                          %{--from="${districtList}" noSelection="['': ' Select District']"/>--}%
            %{--</td>--}%
        %{--</tr>--}%
        %{--<tr>--}%
            %{--<td>Select Preference of examination Centre <span class="university-obligatory">*</span></td>--}%
            %{--<td>--}%
                %{--<g:select name="examinationCentre" tabindex="11" id="examinationCentre" class="university-size-1-2"--}%
                          %{--from=" " noSelection="['': 'Select Examination Centre']"/>--}%
            %{--</td>--}%
        %{--</tr>--}%
        <tr>
            <td></td>
            <td>

                <input type="SUBMIT" value="Submit" id="" tabindex="12"
                        class="university-button">
                <input type="reset" value="Reset" tabindex="13" onclick="resetImage()" class="university-button">
            </td>
        </tr>
    </table>
</g:form>


    </fieldset>
        %{--</g:else>--}%

    %{--</div>--}%


</div>
<script type="text/javascript">
    $(function () {
        $(function () {
            $("#datepicker2").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
            $("#datepicker3").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });

        });
        $(".dialog").dialog({
            autoOpen: false,
            draggable: false,
            position: ['center', 0],
//        maxWidth:600,
//        maxHeight: 500,
            width: 750,
            resizable: false,
            height: 550,
            modal: true,
            title: 'Fee Voucher',
            close: function (ev, ui) {
                $.unblockUI();
//            getStudentsList()
            }

        });
    });

</script>
</body>
</html>