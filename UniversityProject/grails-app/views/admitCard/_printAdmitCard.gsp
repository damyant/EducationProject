<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/15/2014
  Time: 5:27 PM
--%>

<%@ page import="examinationproject.ProgramType" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admit Card Print Preview</title>
    <g:resource dir="css" file="gu_stylesheet.css" />
    <style  type="text/css">
    @page {
        size: 200mm 190mm;
    }
    div.break {
    page-break-after:always;
    }
    </style>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'gu_stylesheet.css')}" type='text/css'/>--}%
</head>
<body>
<g:each in="${studentInstance}" var="student">
    <div id="main">
        <fieldset class="form">
            <div id="viewAdmit" style="height: 100%">
                <div class="university-Admit-Width">
                    <div style="display: inline;width: 100%; ">
                        <label style="float: left" >Gauhati University Schedule Ex-111, Form No. 11</label>
                        <label style="float: right;margin-right: 100px;" >Serial No: </label>

                    </div><br/><br/>
                    <div class="university-clear-both"></div>
                    <div style="width:100%;text-align: center;text-transform: uppercase;display: block;">
                        <rendering:inlineJpeg bytes="${guLogo}" class="university-registration-photo" style="margin:auto; width: 70px;"/>
                        <div>
                            <span style="font-family: Calibri;font-size: 20px;font-style: normal;font-weight: bolder;text-align: center;margin-top: 15px; text-transform: uppercase;">
                                Gauhati University
                            </span>
                        </div>
                        <span style="font-family: Arial Black;font-size: 15px; font-weight:bolder; color: #ffffff; background-color: #000;margin-top: 20px;
                        text-align: center; padding: 2px 5px; text-transform: uppercase;">Admit</span>
                    </div>
                    %{--<div style="width: 100%;height: 264px;"></div>--}%
                    <div style="width: 100%;text-transform: capitalize;font-size: 17px;line-height: 30px;">
                        <table >
                            <tr>
                                <td style="width: 80%;">
                                    <div style="width: 100%;display: inline;">
                                        <div style="width: 25%;float: left;font-size: 11px;"><b>Name of the Candidate</b></div>
                                        <strong>
                                            <div style="width: 70%;float: right;" id="studentName">${student?.firstName} ${student?.middleName} ${student?.lastName}</div>
                                        </strong>
                                    </div>
                                </td>
                                <td style="width: 20%;"></td>
                            </tr>
                            <tr>
                                <td style="width: 80%;">
                                    <div style="width: 100%;display: inline;">
                                        <div style="width: 25%;float: left;font-size: 11px;"><b>Roll Number:</b></div>
                                        <strong>
                                            <div style="width: 70%;float: right;" id="rollNo">${student.rollNo}</div>
                                        </strong>
                                    </div>
                                </td>
                                <td rowspan="3" style="width: 20%;">
                                    <div id="profile-image" style="float: right;">
                                        <g:if test="${student?.studentImage}">
                                        <rendering:inlineJpeg bytes="${student?.getStudentImage()}" class="university-registration-photo" style="margin:auto; width: 110px;"/>
                                        </g:if>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div style="width: 100%;display: inline;">
                                        <div style="width: 25%;float: left;font-size: 12px;"><b>Registration Number:</b></div>
                                        <strong>
                                            <div style="width: 70%;float: right;" id="registrationlNo">
                                                <g:if test="${student.registrationNo1}">
                                                ${student?.registrationNo1}-${student?.registrationNo2}
                                                </g:if>
                                                <g:else>
                                                    A/F
                                                </g:else>
                                            </div>
                                        </strong>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                            <tr>
                                <td style="width: 80%;">
                                    <div style="width: 100%;">
                                        <div style="width: 25%;float: left;font-size: 12px;"><b>Examination: </b></div>
                                        <i><b> <label id="mode">Previous/Semester</label> Examination <label id="year">${year}</label> </b><label style="font-size: 14px;">Under the Institute of Distance and Open Learning for </label><b>${student.programDetail[0].courseName}
                                        <g:if test="${student?.programDetail?.programType==ProgramType?.findById(1)}"><label> Term ${student.programDetail[0].noOfAcademicYears}</label> </g:if><g:else><label> Semester ${student.programDetail[0].noOfTerms}</label> </g:else>
                                        </b></i></div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div style="width: 100%;">
                                        <div style="width: 25%;float: left;font-size: 12px;"><b>Date of Examination: </b></div>
                                       <b> <div id="examDates">${examDate}</div></b>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div style="width: 100%;">
                                        <div style="width: 25%;float: left;font-size: 12px;"><b>Time of Examination: </b></div>
                                        <b> <div id="examTimes">${examTime}</div></b>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <table style="margin: auto;width: 100%;"><tr>
                                        <td style="width: 25%;font-size: 12px;"><b>Examination Centre/Venue</b></td>
                                        <td><label style="width: 75%;" id="examCentre"><b>${studentInstance[0]?.examinationVenue?.name}</b></label></td>
                                    </tr></table>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <g:if test="${admitInst?.signatureImg}">
                                        <rendering:inlineJpeg bytes="${admitInst.getSignatureImg()}" style="margin:auto; width: 150px; height: 70px"/>
                                    </g:if>
                                    <g:else>
                                        <br/><br/><br/>
                                    </g:else>
                                    %{--height:226px;--}%
                                    <div style="width: 100%;display: inline-block;">
                                        <label style="width: 30%;text-align: left;float: left;">
                                            <div>Officer-in-charge</div>
                                            <div >Date:</div>
                                        </label>
                                        <label style="width: 30%;"></label>
                                        <label style="width: 30%;text-align: center;float: right;">
                                            <div>Controller of Examination</div>
                                            <div>Gauhati University</div>
                                        </label>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

            </div>
        </fieldset>
    </div>
    <div class="break"/>
</g:each>
</body>
</html>