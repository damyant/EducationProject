<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admit Card Print Preview</title>
    <g:resource dir="css" file="gu_stylesheet.css"/>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'gu_stylesheet.css')}" type='text/css'/>--}%
</head>

<body>

<div style="border: 1px solid; padding: 10px;">
    <div class="university-size-full-1-1" style="margin-bottom: 25px;"><div
            style="float: right"><lable>Challan No. </lable><label>${challanNo}</label></div>

        <div class="university-clear-both"></div>
    </div>

    <p style="width:100%; margin-left: 3px; margin-top: -6px; text-align: center;text-transform: uppercase;font-size: 14px">

        <div>INSTITUTE OF DISTANCE AND OPEN LEARNING</div>
        <div>GAUHATI UNIVERSITY</div>
        <div>Guwahati, Assam</div>
    </p>
    <table style="width: 80%;margin: auto; text-align: center;">
        <tr>
            <th style="width: 33%;">Student Name</th>
            <th style="width: 33%;">Roll No</th>
            <th style="width: 33%;">Amount</th>
        </tr>
        <g:each in="${0..studList.size() - 1}" var="index">
            <tr>
                <td>${studList[index].firstName} ${studList[index].lastName}</td>
                <td>${studList[index].rollNo}</td>
                <td>${miscellaneousFee[index]}</td>
            </tr>
        </g:each>
    </table>
    <table style="border: 1px solid;width: 80%;margin: auto;">

    </table>
    <table style="width: 80%;margin: auto; text-align: center;">
        <tr>
            <td style="width: 33%;">Grand Total</td>
            <td style="width: 33%;">  </td>
            <td style="width: 33%;">${totalFee}</td>
        </tr>
    </table>

</div>
</body>
</html>