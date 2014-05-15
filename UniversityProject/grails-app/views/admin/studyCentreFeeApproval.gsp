<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 5/5/2014
  Time: 11:21 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Study Centre Admission Fee Approval</h3>
        <table class="inner university-size-full-1-1" style="margin: auto">
            <tr>
                <td class="university-size-1-4"><label>Enter Challan Number</label></td>
                <td class="university-size-3-4" style="padding-left: 20px;">
                    <g:textField name="challanNumber" id="challanNumber"/>
                </td>
            </tr>

        </table>
        <br/><br/>
        <table class="inner university-size-full-1-1" style="margin: auto" id="studyCenterFeeEntryTable">
            <thead>
            <tr>
                <th class="university-size-1-8">Roll No</th>
                <th class="university-size-1-8">Name</th>
                <th class="university-size-1-8">Type of Fees</th>
                <th class="university-size-1-8">Amount</th>
                <th class="university-size-1-8">Payment Mode</th>
                <th class="university-size-1-8">Payment Date</th>
                <th class="university-size-1-8">Bank</th>
                <th class="university-size-1-8">Branch</th>
                <th class="university-size-1-8">Action</th>
            </tr>
            </thead>
            <tbody></tbody>
            <tr>
                %{--<td><input type="text" class="university-size-1-1" name="rollNo" value="" readonly></td>--}%
                %{--<td>--}%
                    %{--<g:select id="feeType" name="feeType" from="${examinationproject.FeeType.list()}"--}%
                              %{--optionKey="id"--}%
                              %{--disabled="disabled" optionValue="type" value="${examinationproject.FeeType.findById(1)}"--}%
                              %{--class="many-to-one university-size-1-2" readonly=""/>--}%

                %{--</td>--}%
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </fieldset>
</div>
</body>
</html>