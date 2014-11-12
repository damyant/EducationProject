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

    <div style="text-align: center; width: 96%; font-size: 14px"><b>GAUHATI UNIVERSITY</b></div>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px">IDOL(Semester) Examinations, 2014</div>
    <div style="text-align: left; float: left; width: 48%; font-size: 12px"><b>Subject</b>: ${program}</div>
    <div style="text-align: left; float: right; width: 48%; font-size: 12px"><b>Semester</b>: ${semester}</div>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px"><b>Paper</b>: ${subjectName}</div>
    <div style="text-align: left; float: left; width: 48%; font-size: 12px">1st Half/2nd Half</div>
    <div style="text-align: left; float: right; width: 48%; font-size: 12px"><b>Total Marks</b>:........................</div>

    <div style="clear: both"></div>

    <table cellspacing="0" style="width: 98%;margin: auto;">
        <g:each var="rollNoList" status="i" in="${stuList}">

            <tr>
                <td style="width: 50%;border: 1px solid #000000;height: 20px;">${rollNoList} </td>
                <td style="width: 50%;border: 1px solid #000000;height: 20px;"></td>
            </tr>
        </g:each>
    </table>

    <br/>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px">Examined by :</div>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px">Scrutinised by :</div>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px">Head examiners' signature :</div>

</div>

<div style="width: 48%;float:right;border:0px solid #000000;min-height: 100px;">
    <g:set var="today" value="${new Date()}"/>
    <div style="text-align: center; width: 96%; font-size: 14px"><b>GAUHATI UNIVERSITY</b></div>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px">IDOL(Semester) Examinations, ${today[Calendar.YEAR]}</div>
    <div style="text-align: left; float: left; width: 48%; font-size: 12px"><b>Subject</b>: ${program}</div>
    <div style="text-align: left; float: right; width: 48%; font-size: 12px"><b>Semester</b>: ${semester}</div>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px"><b>Paper</b>: ${subjectName}</div>
    <div style="text-align: left; float: left; width: 48%; font-size: 12px">1st Half/2nd Half</div>
    <div style="text-align: left; float: right; width: 48%; font-size: 12px"><b>Total Marks</b>:........................</div>

    <div style="clear: both"></div>

    <table cellspacing="0" style="width: 98%;margin: auto;">
        <g:each var="rollNoList" status="i" in="${stuList}">

            <tr>
                <td style="width: 50%;border: 1px solid #000000;height: 20px;">${rollNoList} </td>
                <td style="width: 50%;border: 1px solid #000000;height: 20px;"></td>
            </tr>
        </g:each>
    </table>

    <br/>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px">Examined by :</div>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px">Scrutinised by :</div>
    <div style="text-align: left; float: left; width: 96%; font-size: 12px">Head examiners' signature :</div>

</div>

</body>
</html>