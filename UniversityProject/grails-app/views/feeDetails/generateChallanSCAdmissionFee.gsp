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
    <script type="text/javascript">
    $(window).bind("load", function () {

        var challanNo = "${challanNo}"
        if (challanNo) {

            url = "http://localhost:9093/UniversityProject/student/registration"
            window.location.href = url;
            window.open('/UniversityProject/student/applicationPrintPreview/?studentID=' + studentId);

        }

    })
    </script>
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
    <g:form name="challanForStudyCenter" id="challanForStudyCenter" controller="feeDetails" action="challanForStudyCenterStu">
        <g:hiddenField name="studentListId" id="studentListId" value="" />
        <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
        <table class="inner university-size-full-1-1" style="margin: auto">
            <tr><td><label>Select Program Catagory</label></td>
                <td>
                    <g:select name="programCategory" class="university-size-1-1" id="programCategory" optionKey="id"
                              optionValue="type"
                              from="${programCategory}" noSelection="['': ' Select Program Category']"
                              onchange="loadProgram(this)"/>
                </td>
                <td  style="text-align: center;"></td>
                <td></td>
            </tr>
            <tr>
                <td class="university-size-1-4"><label>Select a Program</label></td>
                <td class="university-size-1-4">
                    <g:select name="programList" class="university-size-1-1" id="programList" optionKey="id"
                              optionValue="courseName"
                              from="" noSelection="['': ' Select Program']"
                              onchange="getSemester(this)"/>
                </td>
                <td class="university-size-1-4" style="text-align: center;">OR</td>
                <td class="university-size-1-4"><input type="checkbox" id="allProgram" name="allProgram"/><label for="allProgram">All Programs</label></td>
            </tr>
            <tr><td><label>Select a Term</label></td>
                <td>

                    <select name="semesterList" class="university-size-1-1" id="semesterList" >
                        <option value="">Select Semester</option>
                    </select>
                </td>
                <td  style="text-align: center;"></td>
                <td></td>
            </tr>
        </table>
       <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1"> <input type="button" value="Show Students" onclick="populateStudentList()" class="ui-button university-size-1-4" style="margin: auto;"></div>
        <table id="studyCenterFeeEntryTable" class="university-size-full-1-1" style="margin: auto;border:1px solid #dddddd; " hidden="hidden">
            <thead>
            <tr>
                %{--<th style="width: 10%;padding-left: 10px;"><input type="checkbox" name="selectAll" id="selectAll"><label for="selectAll">Select All</label></th>--}%
                <th  style="width: 10%;">Serial No</th>
                <th style="width: 26.6%;">Roll No</th>
                <th style="width: 26.6%;">Student Name</th>
                <th style="width: 26.6%;">Amount <b>[&#x20B9;]</b> </th>
                <th style="width: 10%;">Semester</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
        <br/>
        <div class="university-size-1-2"  style="margin: 5px auto;width:98%;text-align: center;vertical-align: middle; border: 1px solid #BDBDBD; padding: 0.5%;border-radius: 4px;" id="rangeRadioButtons" hidden="hidden">
            <div class="university-size-1-3 university-display-inline"><input type="radio" id="rangeEntry" name="entry" value="Range"> <label for="rangeEntry">Generate challan By Range</label> </div>
            <div class="university-size-1-3 university-display-inline"><input type="radio" id="individualEntry" name="entry" value="Range"> <label for="individualEntry">Generate challan Individually</label></div>
        </div>
        <br/>
        <table id="paymentDetails" class="university-size-full-1-1" style="margin: auto;border:1px solid #dddddd;display: none; " >

        </table>
        <br/>
        <div style="width:100%;margin:auto; text-align: center;">
                <input type="button" class="university-size-1-3 ui-button" id="generateFeeChallan" onclick="generateChallanForRange()" value="Generate Fee Challan" style="display: none;margin-left: 33%;"/>
        </div>
    </g:form>
        </fieldset>

</div>
</body>
</html>