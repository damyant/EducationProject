<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/6/14
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Student Registration</title>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'register.css')}" type="text/css">
    %{--<g:javascript library="jquery" plugin="jquery"/>--}%
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.7.1.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.core.js')}"></script>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-1.7.1.min.js')}"></script>--}%
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'jquery-ui-1.8.15.custom.js')}"></script>--}%
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.ui.datepicker.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery.validate.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir:'js', file:'registerPage.js')}"></script>


</head>
<body>
<div id="main">
<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
</g:if>

<h3>STUDENT INFORMATION SHEET</h3>
<g:uploadForm controller="student" action="submitRegistration" method='post' enctype="multipart/form-data">

<table align="center" cellpadding = "10">

<!----- First Name ---------------------------------------------------------->
<tr>
    <td>Name of the applicant (In block letters only)</td>
    <td><input type="text" name="name" maxlength="30" class="textInput" required="true"/>

    </td>
</tr>
<!----- Date Of Birth -------------------------------------------------------->
<tr>
    <td>Date of Birth</td>


    <td><input type="text" name="date_of_birth" maxlength="30" class="textInput" id="datePick" required="true"/>
    </td>
</tr>

<!----- Last Name ---------------------------------------------------------->
<tr>
    <td>Program</td>
    <td><input type="text" name="program" maxlength="30" class="textInput" required="true"/>

    </td>
</tr>
<!----- category ----------------------------------------------------------->
<tr>
    <td>Category</td>
    <td >
        SC <input type="radio" name="category" value=" SC" class="radioInput" style="" required="true"/>
        S.T <input type="radio" name="category" value="S.T" class="radioInput" required="true"/>
        OBC <input type="radio" name="category" value="OBC" class="radioInput" required="true"/>
        MOBC <input type="radio" name="category" value="MOBC" class="radioInput" required="true"/>
        MINORITY COMMUNITY <input type="radio" name="category" value="MINORITY COMMUNITY" class="radioInput" required="true"/>
        General <input type="radio" name="category" value="General" class="radioInput" required="true"/>

    </td>
</tr>


<!----- Nationality ----------------------------------------------------------->
<tr>
    <td>Nationality</td>
    <td>
        Indian <input type="radio" name="nationality" value="Male" class="radioInput" required="true"/>
        Non-Indian <input type="radio" name="nationality" value="Female" class="radioInput" required="true"/>
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
        Male <input type="radio" name="gender" value="Male" class="radioInput" required="true"/>
        Female <input type="radio" name="gender" value="Female" class="radioInput" required="true"/>
    </td>
</tr>
<!----- State of Domicile ----------------------------------------------------------->
<tr>
    <td>State of Domicile</td>
    <td>
        Assam <input type="radio" name="state" value="Male"  class="radioInput" required="true"/>
        Others <input type="radio" name="state" value="Female" class="radioInput" required="true"/>
    </td>
</tr>

<tr>
    <!----- Mobile Number ---------------------------------------------------------->
    <td>Contact Mobile Number</td>
    <td>
        <input type="text" name="contactNo" maxlength="10" class="textInput" required="true" onkeypress="return isNumber(event)"/>

    </td>
</tr>
<tr>


    <!----- Contact centre/study centre ---------------------------------------------------------->
    <td>Contact centre/ Study centre</td>
    <td>
        <input type="text" name="contactCentre" maxlength="3" class="textInput" required="true" onkeypress="return isNumber(event)"/>

    </td>
</tr>
<tr>
    <!----- Preference of examination centre ---------------------------------------------------------->
    <td>Select Preference of examination centre</td>
    <td>
        %{--<input type="text" name="preference" maxlength="2" class="textInput" required="true" onkeypress="return isNumber(event)"/>--}%

        <select name="location" style="width: 260px" required="required">
            <option value="0">select location</option>
            <option value="Noida">Noida</option>
            <option value="Guahati">Guahati</option>
            <option value="Golaghat">Golaghat</option>
            <option value="Jaipur">Jaipur</option>
        </select >
    </td>
</tr>
<tr>
    <!----- GU Registration Number( If already registered in GU) ---------------------------------------------------------->
    <td>GU Registration Number (if already registered in GU)</td>
    <td>
        <input type="text" name="registrationNo1" maxlength="9" class="textInput" onkeypress="return isNumber(event)"/> Of
        <input type="text" name="registrationNo2" maxlength="6" class="textInput" onkeypress="return isNumber(event)"/>
    </td>
</tr>

<!----- Address ---------------------------------------------------------->
<tr>
    <td>Candidate Name & Complete Mailing Address (Write in Capital Letter) <br /><br /><br /></td>
    <td>
        <table class="innerTable" style="width: 100%">

            %{--<tr>--}%
                %{--<td align="center"><b>Sl.No.</b></td>--}%
                %{--<td align="center"><b>Examination</b></td>--}%
                %{--<td align="center"><b>Board</b></td>--}%
                %{--<td align="center"><b>Percentage</b></td>--}%
                %{--<td align="center"><b>Year of Passing</b></td>--}%
            %{--</tr>--}%

            <tr>

                <td>Name:</td>
                <td><input type="text" name="studentName" maxlength="30" class="innerTableInput" required="true"/></td>
                %{--<td><input type="text" name="ClassX_Percentage" maxlength="30" /></td>--}%
                %{--<td><input type="text" name="ClassX_YrOfPassing" maxlength="30" /></td>--}%
            </tr>

            <tr>
                %{--<td>2</td>--}%
                <td>Village/Town:</td>
                <td><input type="text" name="town" maxlength="30" class="innerTableInput" required="true" /></td>
                %{--<td><input type="text" name="ClassXII_Percentage" maxlength="30" /></td>--}%
                %{--<td><input type="text" name="ClassXII_YrOfPassing" maxlength="30" /></td>--}%
            </tr>

            <tr>
                %{--<td>3</td>--}%
                <td>P.O.:</td>
                %{--<td><input type="text" name="Graduation_Board" maxlength="30" /></td>--}%
                %{--<td><input type="text" name="Graduation_Percentage" maxlength="30" /></td>--}%
                <td><input type="text" name="po" maxlength="30" class="innerTableInput" required="true" /></td>
            </tr>

            <tr>
                %{--<td>4</td>--}%
                <td>District:</td>
                %{--<td><input type="text" name="Masters_Board" maxlength="30" /></td>--}%
                %{--<td><input type="text" name="Masters_Percentage" maxlength="30" /></td>--}%
                <td><input type="text" name="district" maxlength="30" class="innerTableInput" required="true"/></td>
            </tr>
            <tr>
                %{--<td>4</td>--}%
                <td>State:</td>
                %{--<td><input type="text" name="Masters_Board" maxlength="30" /></td>--}%
                %{--<td><input type="text" name="Masters_Percentage" maxlength="30" /></td>--}%
                <td><input type="text" name="state" maxlength="30" class="innerTableInput" required="true"/></td>
            </tr>
            <tr>
                %{--<td>4</td>--}%
                <td>Pincode:</td>
                %{--<td><input type="text" name="Masters_Board" maxlength="30" /></td>--}%
                %{--<td><input type="text" name="Masters_Percentage" maxlength="30" /></td>--}%
                <td><input type="text" name="pinCode" maxlength="6" class="innerTableInput" required="true" onkeypress="return isNumber(event)"/></td>
            </tr>


        </table>

    </td>
</tr>
    <tr>
        <td> Upload Your Photo(upload one recent Passport size black & white Photograph)</td>
        <td>
        %{--<input type='file' onchange="readURL(this);" />--}%
        <img id="picture" src="#" alt="Space for Photograph " style="height: 200px; width:150px; border:1px solid black; background-color: white;display: block" required="true">
        </img>
            <input type='file' onchange="readURL(this,'picture');"  name="photograph"/>

        </td>
    </tr>
%{--<tr>--}%
    %{--<td> Signature of The Applicant</td>--}%
    %{--<td>--}%
        %{--<input type='file' onchange="readURL(this);" />--}%
        %{--<img id='signature' src="#" alt="Space for Signature" style="height: 80px; width:250px; border:1px solid black; background-color: white;display: block" required="true">  </img>--}%
        %{--<input type='file' onchange="readURL(this,'signature');" name="signature" id="signatureFile"/>--}%

    %{--</td>--}%
%{--</tr>--}%
<tr>
   <td colspan="2">
       <input type="checkbox" name="declaration" id="declaration" required="true" />
    %{--</td>--}%
    %{--<td>--}%
        <label>I hereby declare that the information as indicated above is true to the best of my knowledge.</label>
    </td>
</tr>



<!----- Submit and Reset ------------------------------------------------->
<tr>
    <td colspan="2" align="center">
        <input type="submit" value="Submit" onclick=" ">
        <input type="reset" value="Reset" onclick="resetImage()">
    </td>
</tr>
</table>

</g:uploadForm>
</div>
<script>



$('#signatureFile').bind('change', function() {
//    alert('This file size is: ' + this.files[0].size/1024/1024 + "MB");
});


    function resetImage(){
        $("#signature").attr('src','#')
        $("#picture").attr('src','#')
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