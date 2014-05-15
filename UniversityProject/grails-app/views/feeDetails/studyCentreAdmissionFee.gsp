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
    <g:javascript src='admin.js'/>
    <g:javascript src='admitCard.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
</head>

<body>
<div id="main">

    <fieldset class="form">
        <h3>Pay Admission Fee Entry</h3>
        <table class="inner university-size-full-1-1" style="margin: auto">
            <tr>
                <td class="university-size-1-4"><label>Enter Challan Number</label></td>
                <td class="university-size-3-4" style="padding-left: 20px;">
                    <g:textField name="challanNumber" id="challanNumber" onchange="loadStudents(this)"/>
                </td>
            </tr>

        </table>
        <br/><br/>
        <table class="inner university-size-full-1-1" style="margin: auto" id="studyCenterFeeEntryTable">
            <thead>
            <tr>

                <th class="university-size-1-8">Name</th>
                <th class="university-size-1-8">Roll No</th>
                <th class="university-size-1-8">Amount</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </fieldset>
</div>
</body>
</html>