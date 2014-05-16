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
        Challan No : ${challanNo}
    </div>
    <table STYLE="width: 80%;margin: auto; text-align: center;">
        <tr>
            <th>Student Name</th>
            <th>Roll No</th>
            <th>Amount</th>
        </tr>
        <g:each in="${0..stuList.size()}" var="index">
            <tr><td> ${stuList.getAt(index)?.firstName} ${stuList.getAt(index)?.lastName}</td>
                <td>${stuList.getAt(index)?.rollNo}</td>
                <td>${courseFee.getAt(index)}</td>
            </tr>
        </g:each>
    </table>
    <table STYLE="width: 80%;margin: auto; text-align: center;">
        <tr><td>Grand Total</td><td></td><td>${totalFee}</td></tr>
        <tr><td>Payment Mode</td><td></td><td>${paymentModeName.paymentModeName}</td></tr>
        <tr><td>Payment Date</td><td></td><td>${paymentDate}</td></tr>
        <tr><td>Bank</td><td></td><td>${bank.bankName}</td></tr>
        <tr><td>Branch</td><td></td><td>${branch.branchLocation}</td></tr>
    </table>

</div>
</body>
</html>