<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 6/30/14
  Time: 10:03 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title></title>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programDetail', 'error')} university-size-1-1">
            <div class="university-size-1-3"><label for="programDetail">
                <g:message code="programFee.programDetail.label" default="Program Name"/><span
                        class="university-obligatory">*</span>
            </label>
            </div>

            <div class="university-size-2-3">
                <g:select id="programDetail" name="programDetailId" onchange="clearAllFields(this)"
                          from="${programDetailList}" optionKey="id" value="${programInst?.programDetailId?.id}"
                          optionValue="courseName" class="many-to-one university-size-1-2"
                          noSelection="['': 'Select Program']" />

            </div>
        </div>
        <div class="fieldcontain ${hasErrors(bean: programFeeInstance, field: 'programSession', 'error')} university-size-1-1">
            <div class="university-size-1-3"><label for="session">
                <g:message code="programFee.programSession.label" default="Program Session"/><span
                        class="university-obligatory">*</span>
            </label>
            </div>
            <div class="university-size-2-3">
                <select id="session" name="programSessionId"  onchange="loadAdmissionFeeDetailsView(this)"
                        class="university-size-1-2" disabled>
                    <option value="">Select Session</option>
                    <g:each in="${programSessions}" var="session">
                        <option value="${session.sessionOfProgram}">${session.sessionOfProgram}</option>
                    </g:each>
                </select>
            </div>
        </div>

        <table id="admissionFeeTable" class="inner university-size-full-1-1">
            <thead>

            </thead>
            <tbody>

            </tbody>
        </table>
    </fieldset>
</div>
</body>
</html>