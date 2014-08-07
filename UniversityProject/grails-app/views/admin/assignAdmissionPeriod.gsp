<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 5/28/2014
  Time: 2:01 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Assign and Update Admission Date</h3>
        <g:if test="${flash.message}">
            <div class="message" role="status"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="admin" action="saveAdmissionFeePeriod" name="assignAdmissionPeriod" id="assignAdmissionPeriod">
            <div style="margin-left: 10px;"><label><h6>All [<span
                    class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="inner" class="university-size-full-1-1">
                <tr>
                    <td class="university-size-1-4">Select Programme Category <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <g:select name="programCategory" class="university-size-1-2" id="programCategory" optionKey="id"
                                  optionValue="type"
                                  from="${programCategory}" noSelection="['': ' Select Programme Category']"
                                  onchange="filterProgramsForSelect(this)"/>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Select Programme <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <g:select name="program" class="university-size-1-2" id="program" optionKey="id"
                                  optionValue="type"
                                  from="" noSelection="['': ' Select Programme']"
                                  onchange="loadAdmissionDate(this)"/>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Select Session <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <select name="admissionYear" class="university-size-1-2" id="admissionYear">
                        <option value="">Select Session</option>
                            <g:each in="${sessionList}" var="year">
                                <option value="${year}">${year}-${year+1}</option>
                            </g:each>
                            </select>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Admission Start Date <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <input type="text" name="startAdmission_D" id="startAdmission_D" onkeypress="disableKeyInput(this)" maxlength="10" class="university-size-1-2" value=""/>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Admission End Date <span class="university-obligatory">*</span></td>
                    <td class="university-size-1-2">
                        <input type="text" name="endAdmission_D" id="endAdmission_D" onkeypress="disableKeyInput(this)" maxlength="10" class="university-size-1-2" value=""/>
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4"></td>
                    <td class="university-size-1-2">
                        <input type="submit" value="Submit" onclick="checkValidation()" class="university-button" />
                    </td>
                    <td class="university-size-1-4"></td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>
<script>
    $(function () {
        $(function () {
            $("#startAdmission_D").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "mm/dd/yy",
                minDate: 0
            });

            $("#endAdmission_D").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "mm/dd/yy",
                minDate: 0
            });
        });
    });
</script>
</body>
</html>