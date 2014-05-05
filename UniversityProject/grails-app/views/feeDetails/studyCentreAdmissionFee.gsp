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
        <h3>Study Centre Admission Fee Entry</h3>
        <table class="inner university-size-full-1-1" style="margin: auto">
            <tr>
                <td class="university-size-1-3"><label>Select a Program</label></td>
                <td class="university-size-2-3" style="padding-left: 20px;">
                    <g:select name="programList" class="university-size-1-2" optionKey="id"
                              optionValue="courseName"
                              from="${programList}" noSelection="['': ' Select Program']"
                              onchange="populateStudents(this)"/>
                </td>
            </tr>
        </table>
        <br/><br/>
        <table id="studyCenterFeeEntryTable" class="inner university-size-full-1-1" style="margin: auto">
            <tr>
                <th class="university-size-1-8">Roll No</th>
                <th class="university-size-1-8">Type of Fees</th>
                <th class="university-size-1-8">Amount</th>
                <th class="university-size-1-8">Payment Mode</th>
                <th class="university-size-1-8">Payment Date</th>
                <th class="university-size-1-8">Bank</th>
                <th class="university-size-1-8">Branch</th>
                <th class="university-size-1-8">Action</th>
            </tr>
            <tr id="studyCenterStudentList">

            </tr>
        </table>
    </fieldset>
</div>
</body>
</html>