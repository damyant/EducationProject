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
                <td class="university-size-1-4"><label>Select a Program</label></td>
                <td class="university-size-1-4">
                    <g:select name="programList" class="university-size-1-1" id="programId" optionKey="id"
                              optionValue="courseName"
                              from="${programList}" noSelection="['': ' Select Program']"
                              onchange="populateStudents(this)"/>
                </td>
                <td class="university-size-1-4" style="text-align: center;">OR</td>
                <td class="university-size-1-4"><input type="checkbox" id="allProgram" name="allProgram" onclick="populateStudentsForAllProgram(this)"/><label for="allProgram">All Programs</label></td>
            </tr>
        </table>
        <br/>
        <table id="studyCenterFeeEntryTable" class="university-size-full-1-1" style="margin: auto;border:1px solid #dddddd; " hidden="hidden">
            <thead>
            <tr>
                <th style="width: 10%;padding-left: 10px;"><input type="checkbox" name="selectAll" id="selectAll"><label for="selectAll">Select All</label></th>
                <th  style="width: 10%;">Serial No</th>
                <th style="width: 26.6%;">Roll No</th>
                <th style="width: 26.6%;">Student Name</th>
                <th style="width: 26.6%;">Amount <b>[&#x20B9;]</b> </th>

            </tr>
            </thead>
            <tbody></tbody>
        </table>
        <br/>
        <div class="university-size-1-2"  style="margin: 5px auto;width:98%;text-align: center;vertical-align: middle; border: 1px solid #BDBDBD; padding: 0.5%;border-radius: 4px;" id="rangeRadioButtons" hidden="hidden">
            <div class="university-size-1-3 university-display-inline"><input type="radio" id="rangeEntry" name="entry" value="Range"> <label for="rangeEntry">Enter Fee By Range</label> </div>
            <div class="university-size-1-3 university-display-inline"><input type="radio" id="individualEntry" name="entry" value="Range"> <label for="individualEntry">Enter Fee Individually</label></div>
        </div>
        <br/>
        <table id="paymentDetails" hidden="hidden" style="margin: auto;border:1px solid #dddddd; " >

        </table>
        <br/>
        <div style="width:50%;margin:auto;">
                <input type="button" class="university-size-1-3 ui-button" id="generateFeeChallan" value="Generate Fee Challan" style="display: none; float: left;"/>
                <input type="button" class="university-size-1-3 ui-button" id="PayByChallan" value="Pay" style="display: none;float:  right;"/>

        </div>
        </fieldset>
</div>
</body>
</html>