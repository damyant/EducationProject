<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 3/26/14
  Time: 10:24 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Generate Fee Voucher</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
    <g:if test="${params.rollNo=='generated'}">
        <div class="message"><div class="university-status-message"><g:message code="rollNo.Generated.message"/></div></div>
    </g:if>
    <g:form  id="generateFeeVoucher" name="generateFeeVoucher">
        %{--<g:hiddenField name="studentId" id="studentId"/>--}%
        %{--<g:hiddenField name="pageType" id="pageType" value="Assign RollNo"/>--}%
        <table class="inner" style="margin: auto;">
            <tr>
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <g:hiddenField name="roleType" id="roleType" value="admin" />
                    <td style="min-width: 12%">
                        <label for="studyCenter">Enter Role Number</label>
                    </td>
                    <td style="width: 33%">
                        <g:textField name="rollNo" class="university-size-1-1" id="rollNo" onchange="enableProgram(this)"/>
                    </td>

                    <td style="min-width: 10%">
                        <label for="programId">Select Semester</label>
                    </td>
                    <td style="width: 33%">
                        <g:select name="programId" id="programId" class="university-size-1-1" from="${programList}" optionKey="id" optionValue="courseName" noSelection="['null':' Select Program']" onchange="getStudents()" disabled="true"/>
                    </td>
                    <td style="width: 10%"></td>
                </sec:ifAnyGranted>
                <sec:ifAnyGranted roles="ROLE_STUDYCENTRE">
                    <td style="min-width: 10%">
                        <label for="programId">Select Program</label>
                    </td>
                    <td style="width: 33%">
                        <g:select name="programId" id="programId" class="university-size-1-1" from="${programList}" optionKey="id" optionValue="courseName" noSelection="['null':' Select Program']" onchange="getStudents()" />
                    </td>
                    <td style="width:50%"></td>
                </sec:ifAnyGranted>

            </tr>
        </table>

        <table id="studentList" class="inner university-table-1-3">
            <thead></thead>
            <tbody></tbody>
        </table>
    </g:form>
    <div id="msg"></div>
</div>
</body>
</html>