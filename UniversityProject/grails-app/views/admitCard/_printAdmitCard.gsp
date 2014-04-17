<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/15/2014
  Time: 5:27 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admit Card Print Preview</title>
    <g:resource dir="css" file="gu_stylesheet.css" />
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'gu_stylesheet.css')}" type='text/css'/>--}%
</head>
<body>
<div id="main">
    <fieldset class="form">
          <div id="viewAdmit">
            <div class="university-Admit-Width">
                <div style="display: inline;width: 100%">
                <label style="float: left" >Gauhati University Schedule Ex-111, Form No. 11</label>
                <label style="float: right" >Serial No: </label>
                </div><br/><br/>
                <div class="university-clear-both"></div>
                <div style="width:100%;text-align: center;text-transform: uppercase;display: block;">

                   <div> <r:img uri="../../web-app/images/gu-logo.jpg" style="width: 100px; margin: auto;" class="logo-image" /></div><br/>
                    <div>
                        <span style="font-family: Calibri;font-size: 30px;font-style: normal;font-weight: bolder;text-align: center;margin-top: 15px; text-transform: uppercase;">
                            Gauhati University
                        </span>
                    </div>
                    <span style="font-family: Arial Black;font-size: 24px; font-weight:bolder; color: #ffffff; background-color: #000;margin-top: 20px;
                    text-align: center; padding: 2px 5px; text-transform: uppercase;">Admit</span>
                    <div style="margin-top: 10px;margin-bottom: 10px;">
                        <span style="font-family: Arial; font-size: 20px;  font-weight:bold; padding: 2px 5px; text-transform: capitalize">
                            <label id="studentName">Chandan Saikia</label>
                        </span>
                    </div>
                </div>
                <div style="width: 100%;text-transform: capitalize;font-size: 17px;line-height: 30px;">
                    <table >
                        <tr>
                            <td style="width: 75%;">
                                <div style="width: 100%;display: inline;">
                                    <div style="width: 25%;float: left;"><b>Roll No.</b></div>
                                    <strong>
                                        <div style="width: 70%;float: right;" id="rollNo">1212121</div>
                                    </strong>
                                </div>
                            </td>
                            <td rowspan="3" style="width: 25%;">
                                <div id="profile-image" style="float: right;"><img id="picture" src="" /></div>
                            </td>
                        </tr>
                        %{--<tr>--}%
                        %{--<td class="university-size-2-3">--}%
                        %{--<label class="university-size-1-2"><strong>Registration No.</strong></label>--}%
                        %{--<label class="university-size-1-2" id="regNo"></label>--}%
                        %{--</td>--}%
                        %{--</tr>--}%
                        <tr>
                            <td style="width: 75%;">
                                <div style="width: 100%;">To the <i><b> <label id="mode">Semester</label> Examination <label id="year">2014</label> </b></i>Under the Institute of Distance and Open Learning </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div style="width: 100%;">in <b><label id="courseType" style="">Master Degree</label> <label id="course">English</label>(<label id="term">Semester 1</label>)</b> </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table style="margin: auto;width: 100%;">
                                    <tr>
                                    <td> <i><b>To be held on</b></i></td>
                                    <td> <b> <div id="examDates">19/13/5000, 19/13/5000, 19/13/5000, 19/13/5000</div></b></td>
                                </tr></table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table style="margin: auto;width: 100%;"><tr>
                                    <td style="width: 25%;">Examination Centre</td>
                                    <td><label style="width: 75%;" id="examCentre">GU, Guwahati</label></td>
                                </tr></table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2"><br/><br/><br/>
                                <div style="width: 100%;display: inline-block">
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
    <div style="height: 300px;width:100%"></div>
</div>

</body>
</html>