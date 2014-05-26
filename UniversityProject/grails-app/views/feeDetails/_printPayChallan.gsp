<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admit Card Print Preview</title>
    <g:resource dir="css" file="gu_stylesheet.css"/>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'gu_stylesheet.css')}" type='text/css'/>--}%
</head>

<body>

<div style="border: 2px solid; padding: 10px;">
    <div class="university-size-full-1-1" style="margin-bottom: 25px;"><div
            style="float: right"><lable>Challan No. </lable><label>${challanNo}</label></div>

        <div class="university-clear-both"></div>
    </div>

    <p style="width:100%; margin-left: 3px; margin-top: -6px; text-align: center;text-transform: uppercase;font-size: 14px">

    <div>INSTITUTE OF DISTANCE AND OPEN LEARNING</div>

    <div>GAUHATI UNIVERSITY</div>

    <div>Guwahati, Assam</div>
</p>
    <table style="width: 80%;border: 1px dotted;margin: auto; text-align: center;">
        <tr>
            <th style="width: 33%;">Student Name</th>
            <th style="width: 33%;">Roll No</th>
            <th style="width: 33%;">Amount</th>
        </tr>
        <g:each in="${0..stuList.size()-1}" var="index">
            <tr>
                <td>${stuList.getAt(index)?.firstName} ${stuList.getAt(index)?.lastName}</td>
                <td>${stuList.getAt(index)?.rollNo}</td>
                <td>${courseFee.getAt(index)}
                    <g:if test="${lateFee!=0}">
                        <label style="font-size: 13px;display: block">(with late fee ${lateFee})</label>
                    </g:if>
                </td>
            </tr>
        </g:each>
    </table>
    <br/>
    <table style="width: 80%;border: 1px dotted;margin: auto; text-align: center;">
        <tr><td style="width: 33%;">Total Fee</td><td style="width: 33%;"></td><td style="width: 33%;">${totalFee}</td></tr>
        <tr><td>Payment Mode</td><td></td><td>${paymentModeName.paymentModeName}</td></tr>
        <tr><td>Payment Date</td><td></td><td>${paymentDate}</td></tr>
        <tr><td>Payment Ref Number</td><td></td><td>${paymentReferenceNumber}</td></tr>
        <tr><td>Bank</td><td></td><td>${bank.bankName}</td></tr>
        <tr><td>Branch</td><td></td><td>${branch.branchLocation}</td></tr>
    </table>

</div>
</body>
</html>