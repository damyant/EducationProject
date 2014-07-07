<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 7/2/14
  Time: 6:07 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='admitCard.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Generate Admit Card</h3>
        <g:if test="${flash.message}">
            <div class="university-status-message"><label class="error">${flash.message}</label></div>
        </g:if>
        <g:form controller="admitCard" action="generateSingleAdmitCard" name="generateSingleAdmitCard" id="generateSingleAdmitCard">
        <table class="inner university-size-full-1-1">
            <tr>
                <td class="university-size-1-4">Enter Roll Number</td>
                <td class="university-size-1-3"><input type="text" class="university-size-2-3" maxlength="8"
                                                       onchange="loadTermAndVenueFromRollNo(this)"
                                                       onkeypress="return isNumber(event)" name="rollNoForFeeStatus"
                                                       id="rollNoForFeeStatus"/></td>
                <td class="university-size-1-4"><input type="button" class="university-button" value="Check Fee Details"
                                                       onclick="checkFeeStatusForRollNo()"/></td>
                <td class="university-size-1-7"><label id="errorLabel" class="error"></label></td>
            </tr>
        </table>

        <div class="university-size-full-1-1" id="showStatusForRollNo"></div>
        <table class="inner university-size-full-1-1">
            <tr>
                <td class="university-size-1-4"><label>Fee Exempt</label></td>
                <td class="university-size-3-4" style="padding-left: 25px;"><input type="checkbox" name="feeExempt"
                                                                                   id="feeExempt"/></td>
            </tr>
            <tr>
                <td><label for="semesterList">Select Semester/Term</label></td>
                <td style="padding-left: 25px;">
                    <select name="semesterList" class="university-size-1-3" id="semesterList">
                        <option value="">Select Semester</option>
                    </select>
                </td>
            </tr>
            %{--<tr><td><label>Select a Session</label></td>--}%
                %{--<td>--}%
                    %{--<g:select name="programSession" from="" class="university-size-1-1" id="SessionList"--}%
                              %{--onchange="enableShowCandidate()" noSelection="['': ' Select Session']"/>--}%

                %{--</td>--}%
            %{--</tr>--}%
            <tr>
                <td><label for="semesterList">Select Examination Venue</label></td>
                <td style="padding-left: 25px;">
                    <g:select name="examinationVenue" class="university-size-1-3" id="examCenterList" from=""
                              onchange="showExamVenueCapacity(),enableShowCandidate()"
                              noSelection="['': ' Select Exam Venue']"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div id="maxCapacityBox" hidden="">
                        <b><label style="font-size: 10px;">Maximum Capacity</label><input type="text" class="university-size-1-3"
                                                             id="totalCapacity" style="text-align: center;font-size: 10px;" readonly/>
                        </b>
                    </div>
                    <div id="remainingCapacityBox" hidden="">
                        <b><label style="font-size: 10px;">Available Capacity</label><input type="text" class="university-size-1-3"
                                                                                            id="remainingCapacity" style="text-align: center;font-size: 10px;"
                                                                                            readonly/>
                        </b>

                    </div>


                </td>
                <td style="padding-left: 25px;"><input type="submit" value="Generate Admit Card" class="university-button" onclick="validateProgramFee()"/>
               </td>
            </tr>

        </table>
</g:form>
    </fieldset>
</div>
</body>
</html>