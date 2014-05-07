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
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${params?.type}">
            <h3>Study Centre Post Admission Fee Entry</h3>
        </g:if>
        <g:else>
            <h3>Study Centre Admission Fee Entry</h3>
        </g:else>
        <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
        <table class="inner university-size-full-1-1" style="margin: auto">
            <tr>
                <td class="university-size-1-3"><label>Select a Program</label></td>
                <td class="university-size-2-3" style="padding-left: 20px;">
                    <g:select name="programList" class="university-size-1-2" id="programId" optionKey="id"
                              optionValue="courseName"
                              from="${programList}" noSelection="['': ' Select Program']"
                              onchange="populateStudents(this)"/>
                </td>
            </tr>
        </table>
        <br/><br/>
        <table id="studyCenterFeeEntryTable" class="university-size-full-1-1" style="margin: auto;border:1px solid #dddddd; " hidden="hidden">
            <thead>
            <tr>
                <th class="university-size-1-3">Roll No</th>
                <th class="university-size-1-3">Type of Fees</th>
                <th class="university-size-1-3">Amount</th>

            </tr>
            </thead>
            <tbody></tbody>
        </table>

        <div class="university-size-1-2" style="margin: auto;padding: 10px;" id="rangeRadioButtons" hidden="hidden">
            <div class="university-size-1-3 university-display-inline"><input type="radio" id="rangeEntry" name="entry" value="Range"> <label for="rangeEntry">Enter Fee By Range</label> </div>
            <div class="university-size-1-3 university-display-inline"><input type="radio" id="individualEntry" name="entry" value="Range"> <label for="individualEntry">Enter Fee Individually</label></div>
        </div>

        <table id="paymentDetails" hidden="hidden">

        </table>

        <input type="button" class="university-size-1-4 ui-button" id="feeSubmitButton" value="Submit" style="display: none;margin: 10px"/>
    </fieldset>
</div>
</body>
</html>