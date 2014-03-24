<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="javax.validation.constraints.Null; examinationproject.City; examinationproject.District; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'register.css')}" type="text/css">
    %{--<g:javascript library="jquery" plugin="jquery"/>--}%
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.7.1.js')}"></script>--}%
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.core.js')}"></script>--}%
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.datepicker.js')}"></script>--}%
    <g:javascript src='validate.js'/>
    <g:javascript src='studyCenter.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'registerPage.js')}"></script>
    <style type="text/css">

    </style>
</head>

<body>
<div id="main">
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>

<h3>STUDENT INFORMATION SHEET</h3>
<g:uploadForm controller="student" action="submitRegistration" method='post' enctype="multipart/form-data"
              id="studentRegister" name="studentRegister">

<table align="center" cellpadding="10">

    <!----- First Name ---------------------------------------------------------->
    <tr>
        <td>Name of the applicant (In block letters only)</td>
        <td><input type="text" name="name" maxlength="30" class="university-size-1-2"/>

        </td>
    </tr>
    <!----- Date Of Birth -------------------------------------------------------->
    <tr>
        <td>Date of Birth</td>



        <td><input type="text" name="d_o_b" maxlength="30" class="university-size-1-2" id="datePick"/>

        </td>
    </tr>

    <!----- Last Name ---------------------------------------------------------->
    <tr>
        <td>Program</td>
        %{--<td><input type="text" name="program" maxlength="30" class="university-size-1-2"/>--}%
        <td>
        <g:select name="programDetail" id="programDetail" optionKey="id" class="university-size-1-3"  optionValue="courseName" from="${ProgramDetail.findAll()}" noSelection="['':' Select Program']" />

        </td>
    </tr>
    <!----- category ----------------------------------------------------------->
    <tr>
        <td>Category</td>
        <td>
            <div class="radio_options">
                <label>General <input type="radio" name="category" value="General" class="radioInput"/></label>
                <label>MOBC <input type="radio" name="category" value="MOBC" class="radioInput"/></label>
                <label>OBC <input type="radio" name="category" value="OBC" class="radioInput"/></label>
                <label>SC <input type="radio" name="category" value=" SC" class="radioInput" style=""/></label>
                <label>ST <input type="radio" name="category" value="S.T" class="radioInput"/></label>
                <label>MINORITY<input type="radio" name="category" value="MINORITY COMMUNITY" class="radioInput"/></label>
            </div>
        </td>
    </tr>


    <!----- Nationality ----------------------------------------------------------->
    <tr>
        <td>Nationality</td>
        <td>
            <div class="radio_options">
                <label> Indian <input type="radio" name="nationality" value="Indian" class="radioInput"/></label>
                <label>Non-Indian <input type="radio" name="nationality" value="Non-Indian" class="radioInput"/></label>
            </div>
        </td>
    </tr>




    %{--<!----- Email Id ---------------------------------------------------------->--}%
    %{--<tr>--}%
    %{--<td>EMAIL ID</td>--}%
    %{--<td><input type="text" name="Email_Id" maxlength="100"  class="textInput"/></td>--}%
    %{--</tr>--}%




    <!----- Gender ----------------------------------------------------------->
    <tr>
        <td>Gender</td>
        <td>
            <div class="radio_options">
                <label>Male <input type="radio" name="gender" value="Male" class="radioInput"/></label>
                <label>Female <input type="radio" name="gender" value="Female" class="radioInput"/></label>
            </div>
        </td>
    </tr>
    <!----- State of Domicile ----------------------------------------------------------->
    <tr>
        <td>State of Domicile</td>
        <td>
            <div class="radio_options">
                <label> Assam <input type="radio" name="state" value="Assam" class="radioInput"/></label>
                <label>Others <input type="radio" name="state" value="Others" class="radioInput"/></label>
            </div>
        </td>
    </tr>

    <tr>
        <!----- Mobile Number ---------------------------------------------------------->
        <td>Contact Mobile Number</td>
        <td>
                <input type="text" name="mobileNo" maxlength="10" class="university-size-1-2"
                   onkeypress="return isNumber(event)"/>
        </td>
    </tr>
    <tr>

        <!----- Contact centre/study centre ---------------------------------------------------------->
        <td>Contact centre/ Study centre</td>
        <td>
            <input type="text" name="studyCentre"  class="university-size-1-2" value="${studyCentre?.name}" readonly/>


        </select>
        </td>
    </tr>
    <tr>
        <!----- Preference of examination centre ---------------------------------------------------------->
        <td>Select Preference of examination centre</td>
        <td>
            %{--<input type="text" name="preference" maxlength="2" class="textInput" required="true" onkeypress="return isNumber(event)"/>--}%

            %{--<select name="location" class="university-size-1-2">--}%
                %{--<option value="">select location</option>--}%
                %{--<option value="Noida">Noida</option>--}%
                %{--<option value="Guahati">Guahati</option>--}%
                %{--<option value="Golaghat">Golaghat</option>--}%
                %{--<option value="Jaipur">Jaipur</option>--}%
            %{--</select>--}%
            <g:select name="district" id="district" optionKey="id"  class="university-size-1-3" onchange="showCityList()" optionValue="districtName" from="${District.findAll()}" noSelection="['':' Select District']" />
            <g:select name="city" id="city" optionKey="id"  class="university-size-1-3"  optionValue="cityName" from="" onchange="showCentreList()" noSelection="['':' Select City']"/>
            <g:select name="examiNationCentre" id="examinationCentre"  class="university-size-1-3" from=" "  noSelection="['':' Select Examination Centre']"/>
        </td>
    </tr>
    <tr>
        <!----- GU Registration Number( If already registered in GU) ---------------------------------------------------------->
        <td>GU Registration Number (if already registered in GU)</td>
        <td>
            <input type="text" name="registrationNo1" maxlength="9" class="university-size-1-3"
                   onkeypress="return isNumber(event)"/> Of
            <input type="text" name="registrationNo2" maxlength="6" class="university-size-1-3"
                   onkeypress="return isNumber(event)"/>
        </td>
    </tr>

    <!----- Address ---------------------------------------------------------->
    <tr>
        <td>Candidate Name & Complete Mailing Address (Write in Capital Letter) <br/><br/><br/></td>
        <td>
            <table class="innerTable" style="width: 100%">
                <tr>

                    <td>Name:</td>
                    <td><input type="text" name="addressStudentName" maxlength="30" class="university-size-1-2"/></td>
                </tr>
                <tr>
                    <td>Village/Town:</td>
                    <td><input type="text" name="addressTown" maxlength="30" class="university-size-1-2"/></td>
                </tr>
                <tr>

                    <td>P.O.:</td>
                    <td><input type="text" name="addressPO" maxlength="30" class="university-size-1-2"/></td>
                </tr>
                <tr>
                    <td>District:</td>

                    <td><input type="text" name="addressDistrict" maxlength="30"
                               class="university-size-1-2"/></td>
                </tr>
                <tr>
                    <td>State:</td>
                    <td><input type="text" name="addressState" maxlength="30" class="university-size-1-2"/>
                    </td>
                </tr>
                <tr>
                    <td>Pincode:</td>
                    <td><input type="text" name="addressPinCode" maxlength="6" class="university-size-1-2"
                               onkeypress="return isNumber(event)"/></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>Upload Your Photo(upload one recent Passport size black & white Photograph)</td>
        <td>
            %{--<input type='file' onchange="readURL(this);" />--}%
            <img id="picture" src="#" alt="Space for Photograph " class="university-registration-photo">
        </img>
            <input type='file' onchange="readURL(this, 'picture');" class="university-button"
                   name="photograph"/>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <label id="declaration-label"><input type="checkbox"  id="declaration"  name="declaration"/>
            I hereby declare that the information as indicated above is true to the best of my knowledge.</label>
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

</script>
</body>
</html>