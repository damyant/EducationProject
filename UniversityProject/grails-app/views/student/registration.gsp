<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="examinationproject.ExaminationCentre; javax.validation.constraints.Null; examinationproject.City; examinationproject.District; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>

    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'registerPage.js')}"></script>--}%
    <style type="text/css">
    </style>
    <script type="text/javascript">
        var gender = "${studInstance?.gender}"
        var category = "${studInstance?.category}"
        var nationality = "${studInstance?.nationality}"

        var state = "${studInstance?.state}"

        $(window).bind("load", function () {

            var flag = "${registered}"
            var studentId = "${studentID}"

            if (flag == 'registered') {
                window.open('/UniversityProject/student/applicationPrintPreview/?studentID=' + studentId);
            }
            //a(studentId)
        })

        function a(id) {
            $.ajax({
                type: "post",
                url: url('student', 'applicationPrintPreview', ''),
                data: {studentId: id},
                success: function (data) {

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                }
            });
        }

    </script>

</head>

<body>
<div id="main">
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
<g:else>
<fieldset class="form">
<g:uploadForm controller="student" action="submitRegistration" method='post' enctype="multipart/form-data"
              id="studentRegister" name="studentRegister">
<h3>STUDENT INFORMATION SHEET</h3>
<div style="margin-left: 10px"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
<table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;">
<!----- First Name ---------------------------------------------------------->
<tr>
    <td>Name of the applicant <span class="university-obligatory">*</span></td>
    <td><input type="text" name="name" style="text-transform:uppercase" onkeypress="return onlyAlphabets(event, this);"
               maxlength="50" class="university-size-1-2" value="${studInstance?.name}"/>

    </td>
</tr>
<!----- Date Of Birth -------------------------------------------------------->
<tr>
    <td>Date of Birth <span class="university-obligatory">*</span></td>



    <td>
        %{--<input type="text" name="d_o_b" maxlength="10" class="university-size-1-2" id="datePick"/>--}%
        <input type="text" name="d_o_b" maxlength="10" class="university-size-1-2" id="datepicker"
               value="<g:formatDate format="MM/dd/yy" date="${studInstance?.dob}"/>">

    </td>
</tr>

<!----- Last Name ---------------------------------------------------------->
<tr>
    <td>Program <span class="university-obligatory">*</span></td>
    %{--<td><input type="text" name="program" maxlength="30" class="university-size-1-2"/>--}%
    <td>
        <g:select name="programId" id="programId" optionKey="id" class="university-size-1-2" value="${studInstance?.programDetail?.id}"
                  optionValue="courseName" from="${programList}" noSelection="['': ' Select Program']"/>

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

            <label><span>SC</span><input type="radio" name="category" value=" SC" class="radioInput" style=""/></label>

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
    <td>
        <input type="text" name="studyCentre" class="university-size-1-2" value="${studyCentre?.name}" readonly/>


    </select>
    </td>
</tr>
<tr>
    <!----- Preference of examination centre ---------------------------------------------------------->
    <td>Select Preference of examination centre <span class="university-obligatory">*</span></td>
    <td>

        <table id="examCenterSelect">
            <tr>
                <td style="width: 50%">

                    <g:select name="district" id="district" optionKey="id"
                              value="${studInstance?.examinationCentre?.city?.district?.id}" class="university-size-1-1"
                              onChange="showCityList()" optionValue="districtName"
                              from="${districtList}" noSelection="['': ' Select District']"/>

                </td>
                <td style="width: 50%">
                    <g:if test="${studInstance}">
                        <g:select name="city" id="city" optionKey="id" class="university-size-1-1"
                                  optionValue="cityName" value="${studInstance?.examinationCentre?.city?.id}"
                                  from="${City.findAllByDistrict(District.get(studInstance?.examinationCentre?.city?.district?.id))}"
                                  onchange="showCentreList()"
                                  noSelection="['': ' Select City']"/></g:if>
                    <g:else>
                        <g:select name="city" id="city" optionKey="id" class="university-size-1-1"
                                  optionValue="cityName"
                                  from="" onchange="showCentreList()"
                                  noSelection="['': ' Select City']"/>
                    </g:else>
                </td>
            </tr><tr>
            <td>
                <g:if test="${studInstance}">
                    <g:select name="examiNationCentre" id="examinationCentre" class="university-size-1-1"
                              from="${ExaminationCentre.findAllByCity(City.get(studInstance?.examinationCentre?.city?.id))}"
                              noSelection="['': 'Select Examination Centre']"/>
                </g:if>
                <g:else>
                    <g:select name="examiNationCentre" id="examinationCentre" class="university-size-1-1" from=" "
                              noSelection="['': 'Select Examination Centre']"/>
                </g:else>
            </td><td></td>
        </tr>
        </table>
    </td>
</tr>
%{--<tr>--}%
%{--<!----- GU Registration Number( If already registered in GU) ---------------------------------------------------------->--}%
%{--<td>GU Registration Number (if already registered in GU)</td>--}%
%{--<td>--}%
%{--<input type="text" name="registrationNo1" maxlength="9" class="university-size-1-3"--}%
%{--onkeypress="return isNumber(event)"/> Of--}%
%{--<input type="text" name="registrationNo2" maxlength="6" class="university-size-1-3"--}%
%{--onkeypress="return isNumber(event)"/>--}%
%{--</td>--}%
%{--</tr>--}%

<!----- Address ---------------------------------------------------------->
<tr>
    <td>Complete Mailing Address of Candidate<br/><br/><br/></td>
    <td>
        <table style="width: 100%" id="examCenterAddress">
            <tr>

                <td style="width: 30%;">Address:</td>
                <td style="width: 70%;"><input type="text" name="addressStudentName" maxlength="30"
                                               class="university-size-1-2" value="${studInstance?.addressStudentName}"
                                               onkeypress="return isAlphaNumeric(event, this);"/></td>
            </tr>
            <tr>
                <td style="width: 30%;">Village/Town:</td>
                <td style="width: 70%;"><input type="text" name="addressTown" maxlength="30"
                                               value="${studInstance?.addressTown}"
                                               class="university-size-1-2"/></td>
            </tr>
            <tr>

                <td style="width: 30%;">Post Office:</td>
                <td style="width: 70%;"><input type="text" name="addressPO" value="${studInstance?.addressPO}"
                                               maxlength="30" class="university-size-1-2"/>
                </td>
            </tr>
            <tr>
                <td style="width: 30%;">District:</td>

                <td style="width: 70%;"><input type="text" value="${studInstance?.addressDistrict}"
                                               name="addressDistrict" maxlength="30"
                                               class="university-size-1-2"/></td>
            </tr>
            <tr>
                <td style="width: 30%;">State:</td>
                <td style="width: 70%;"><input type="text" value="${studInstance?.addressState}" name="addressState"
                                               maxlength="30"
                                               class="university-size-1-2"/>
                </td>
            </tr>
            <tr>
                <td style="width: 30%;">Pincode:</td>
                <td style="width: 70%;"><input type="text" value="${studInstance?.addressPinCode}" name="addressPinCode"
                                               maxlength="6"
                                               class="university-size-1-2"
                                               onkeypress="return isNumber(event)"/></td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        Upload recent Passport size Photograph ( black & white, Resolution: [200 X 150] and Size: Less then 30KB )
    </td>
    <td>
    %{--<input type='file' onchange="readURL(this);" />--}%

        <g:if test="${studInstance}">
            <img src="${createLink(controller: 'student', action: 'show', id: studInstance?.id
            , mime: 'image/jpeg')}" class="university-registration-photo" id="picture"/>

        </g:if>
        <g:else>
            <div id="profile-image"><img src="" alt="Space for Photograph "
                                         class="university-registration-photo"/></div>
        </g:else>
            <input type='file' id="profile-image-upload" onchange="readURL(this, 'picture');" class="university-button"
                   name="photograph"/>

    </td>
</tr>
<tr>
    <td colspan="2">
        <label id="declaration-label"><input type="checkbox" id="declaration" name="declaration"/>
            I hereby declare that the information as indicated above is true to the best of my knowledge. <span
                class="university-obligatory">*</span>
        </label>
    </td>
</tr>



<!----- Submit and Reset ------------------------------------------------->
<tr>
    <td colspan="2" align="center">
        <input type="submit" value="Submit" onclick="validate()" class="university-button">
        <input type="reset" value="Reset" onclick="resetImage()" class="university-button">
    </td>
</tr>
<tr>
    <td>
        <input type="hidden" name="studyCentreCode" value="${studyCentre?.centerCode}">
    </td>
</tr>
</table>

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
    //    function checkDeclaration(){
    //        if($("$declaration").is(':checked')){
    //            alert("in if true statement")
    //            return true
    //        }
    //        else{
    //            alert("in else statement")
    //            return false
    //        }
    //}
    $(function () {
        $(function () {
            $("#datepicker").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "mm/dd/yy"
            });
        });
    });
</script>
</body>
</html>