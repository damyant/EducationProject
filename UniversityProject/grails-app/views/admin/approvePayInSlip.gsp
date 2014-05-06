<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 5/5/2014
  Time: 2:24 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Approve Pay-In-Slip</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <form id="approvePayInSlip">
        <table class="inner university-size-full-1-1">
            <tr>
                <td class="university-size-1-4">Pay-In-Slip Challan No</td>
                <td class="university-size-3-4"><input type="text" class="university-size-1-3" onchange="populateChallanDetail(this)" name="payInSlipNo" id="payInSlipNo"></td>
            </tr>
        </table>
        <br/>
        <div style="margin-bottom: 10px"> <label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

        <table class=" university-size-full-1-1">
            <tr>
                <td class="university-size-1-3">Roll No</td>
                <td class="university-size-1-3"><input type="text" class="university-size-1-1" name="RollNo" id="rollNo" disabled/></td>
                <td class="university-size-1-3" rowspan="6" style="vertical-align: middle; text-align: center;">
                    <input type="button" value="Approve" class="ui-button university-size-1-2" onclick="approvePayInSlip()" />
                    <input type="hidden" value=""/>
                </td>
            </tr>
            <tr>
                <td class="university-size-1-3">Fee Type<span class="university-obligatory">*</span></td>
                <td class="university-size-1-3"> <g:select name="programList" class="university-size-1-1" optionKey="id"
                                                           optionValue="type" value=""
                                                           from="${examinationproject.FeeType.findById(1)}" disabled=""
                                                           onchange=""/></td>
            </tr>
            <tr>
                <td class="university-size-1-3">Amount</td>
                <td class="university-size-1-3"><input type="text" class="university-size-1-1" name="feeAmount" id="feeAmount" disabled></td>
            </tr>
            <tr>
                <td class="university-size-1-3">Payment Date<span class="university-obligatory">*</span></td>
                <td class="university-size-1-3"><input type="text" class="university-size-1-1" id="datePick" name="paymentDate"></td>
            </tr>
            %{--<tr>--}%
                %{--<td class="university-size-1-3">Bank<span class="university-obligatory">*</span></td>--}%
                %{--<td class="university-size-1-3"><g:select name="bankName" optionKey="id" class="university-size-1-1"--}%
                                                          %{--optionValue="bankName" from="${bankList}" noSelection="['': ' Select Bank']"--}%
                                                          %{--onchange="loadBranch(this)"/></td>--}%
            %{--</tr>--}%
            %{--<tr>--}%
                %{--<td class="university-size-1-3">Branch<span class="university-obligatory">*</span></td>--}%
                %{--<td class="university-size-1-3">--}%
                    %{--<g:select name="branchName" optionKey="" class="university-size-1-1"--}%
                              %{--optionValue="" from="" noSelection="['': ' Select Branch']"--}%
                              %{--id="branchLocation"/>--}%
                %{--</td>--}%
            %{--</tr>--}%
        </table>
        </form>
    </fieldset>
</div>
</body>
</html>