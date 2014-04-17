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
    <title>Admit Card Print Privew</title>
</head>
<body>
<div id="main">
    <fieldset class="form">
          <div id="viewAdmit">
            <div class="university-Admit-Width">

                <label class="university-float-left"  style="margin-left: 20px;">Gauhati University Schedule Ex-111, Form No. 11</label>
                <label class="university-float-right" style="margin-right: 20px;">Serial No: </label>
                <div class="university-clear-both"></div>
                <div class="university-Admit-Width" style="text-align: center;text-transform: uppercase;">
                    <img src="${resource(dir: 'images', file: 'gu-logo.jpg')}" style="width: 100px; margin: auto;" class="logo-image" />
                    <h1>Gauhati University</h1>
                    <span class="University-Admit-Sub-Header">Admit</span>
                    <div style="margin-top: 10px;margin-bottom: 10px;"><span class="University-Admit-Student-Name"><label id="studentName">Chandan Saikia</label></span></div>
                </div>
                <div class="university-Admit-Width" style="text-align: center; text-transform: capitalize;">
                    <table class="inner">
                        <tr>
                            <td class="university-size-3-4">
                                <div class="university-size-1-1">
                                    <div class="university-size-1-4 admitCardView-font university-float-left"><b>Roll No.</b></div>
                                    <strong>
                                        <div class="university-size-1-2 admitCardView-font university-float-right" id="rollNo">1212121</div>
                                    </strong>
                                </div>
                            </td>
                            <td rowspan="3" class="university-size-1-4">
                                <div id="profile-image" class="university-float-right"><img id="picture" src="" class="university-registration-photo"/></div>
                            </td>
                        </tr>
                        %{--<tr>--}%
                        %{--<td class="university-size-2-3">--}%
                        %{--<label class="university-size-1-2"><strong>Registration No.</strong></label>--}%
                        %{--<label class="university-size-1-2" id="regNo"></label>--}%
                        %{--</td>--}%
                        %{--</tr>--}%
                        <tr>
                            <td class="university-size-3-4">
                                <div class="university-size-1-1 admitCardView-font">To the <span class="university-font-style-italic"> <label id="mode">Semester</label> Examination <label id="year">2014</label> </span>Under the Institute of Distance and Open Learning </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div class="university-size-1-1 admitCardView-font">in <b><label id="courseType" style="margin-left: 30px;">Master Degree</label> <label id="course">English</label>(<label id="term">Semester 1</label>)</b> </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table class="inner" style="margin: auto;"><tr>
                                    <td class="university-size-1-4 admitCardView-font"> <span class="university-font-style-italic">To be held on</span></td>
                                    <td> <b> <div class="admitCardView-font" id="examDates">19/13/5000, 19/13/5000, 19/13/5000, 19/13/5000</div></b></td>
                                </tr></table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <table class="inner" style="margin: auto;"><tr>
                                    <td class="university-size-1-3 admitCardView-font">Examination Centre</td>
                                    <td><label class="university-size-2-3 admitCardView-font" id="examCentre">GU, Guwahati</label></td>
                                </tr></table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div style="margin-top: 70px;" class="admitCardView-font">
                                    <label class="university-size-1-3 university-float-left">
                                        <p>Officer-in-charge</p>
                                        <p >Date:</p>
                                    </label>
                                    <label class="university-size-1-3"></label>
                                    <label class="university-size-1-3 university-float-right university-text-align-centre">
                                        <p>Controller of Examination</p>
                                        <p>Gauhati University</p>
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

        </div>
    </fieldset>
    <div style="height: 300px">test div</div>
</div>

</body>
</html>