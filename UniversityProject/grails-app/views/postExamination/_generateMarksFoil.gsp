<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 3/6/14
  Time: 11:25 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Marks Foil Generation Preview</title>
    <g:resource dir="css" file="gu_stylesheet.css"/>
</head>

<body>
<div style="width: 48%;float:left;border:0px solid #000000;min-height: 100px;">

    <div style="text-align: center; width: 96%"><b>GAUHATI UNIVERSITY</b></div>
    <div style="text-align: left; float: left; width: 96%">IDOL(Semester) Examinations, 2014</div>
    <div style="text-align: left; float: left; width: 48%"><b>Subject</b>: ${program}</div>
    <div style="text-align: left; float: right; width: 48%"><b>Semester</b>: ${semester}</div>
    <div style="text-align: left; float: left; width: 96%"><b>Paper</b>:..................................................................</div>
    <div style="text-align: left; float: left; width: 48%">1st Half/2nd Half</div>
    <div style="text-align: left; float: right; width: 48%"><b>Total Marks</b>:............</div>

    <div style="clear: both"></div>

    <table cellspacing="0" style="width: 98%;margin: auto;">
        <g:each var="test" in="${1..40}">

            <tr>
                <td style="width: 50%;border: 1px solid #000000;height: 20px;"> </td>
                <td style="width: 50%;border: 1px solid #000000;height: 20px;"></td>
            </tr>
        </g:each>
    </table>


    <div style="text-align: left; float: left; width: 96%">Examined by :</div>
    <div style="text-align: left; float: left; width: 96%">Scrutinised by :</div>
    <div style="text-align: left; float: left; width: 96%">Head examiners' signature :</div>

</div>

<div style="width: 48%;float:right;border:0px solid #000000;min-height: 100px;">
    <div style="text-align: center; width: 96%"><b>GAUHATI UNIVERSITY</b></div>
    <div style="text-align: left; float: left; width: 96%">IDOL(Semester) Examinations, 2014</div>
    <div style="text-align: left; float: left; width: 48%"><b>Subject</b>:</div>
    <div style="text-align: left; float: right; width: 48%"><b>Semester</b>:</div>
    <div style="text-align: left; float: left; width: 96%"><b>Paper</b>:..................................................................</div>
    <div style="text-align: left; float: left; width: 48%">1st Half/2nd Half</div>
    <div style="text-align: left; float: right; width: 48%"><b>Total Marks</b>:............</div>

    <div style="clear: both"></div>

    <table cellspacing="0" style="width: 98%;margin: auto;">
        <g:each var="test" in="${1..40}">

            <tr>
                <td style="width: 50%;border: 1px solid #000000;height: 20px;"> </td>
                <td style="width: 50%;border: 1px solid #000000;height: 20px;"></td>
            </tr>
        </g:each>
    </table>


    <div style="text-align: left; float: left; width: 96%">Examined by :</div>
    <div style="text-align: left; float: left; width: 96%">Scrutinised by :</div>
    <div style="text-align: left; float: left; width: 96%">Head examiners' signature :</div>

</div>

</body>
</html>