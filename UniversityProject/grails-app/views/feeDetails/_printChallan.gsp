<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admit Card Print Preview</title>
    <g:resource dir="css" file="gu_stylesheet.css"/>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'gu_stylesheet.css')}" type='text/css'/>--}%
</head>

<body>
<div id="main">
    <div style="width: 80%;margin: auto; text-align: right;">
        Challan No : ${studList[0].challanNo}
    </div>
    <table STYLE="width: 80%;margin: auto; text-align: center;">
        <tr>
            <th>Student Name</th>
            <th>Roll No</th>
            <th>Amount</th>
        </tr>
        <g:each in="${0..studList.size()-1}" var="index">
            <tr><td> ${studList[index].firstName} ${studList[index].lastName}</td>
            <td>${studList[index].rollNo}</td>
            <td>${addmissionFee[index].feeAmountAtIDOL}</td>
            </tr>
        </g:each>
    </table>
</div>
</body>
</html>