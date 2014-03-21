<%--
  Created by IntelliJ IDEA.
  User: Sonali P gupta
  Date: 2/7/14
  Time: 10:37 AM
--%>

<%@ page import="javax.validation.constraints.Null; examinationproject.City; examinationproject.District" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Create Study Center</title>
    <meta name="layout" content="main"/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <title>Create Study Center</title>
</head>

<body>
<div id="main">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${rollNoInstance}">
        <div class="errors">
            <g:renderErrors bean="${rollNoInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:if test="${params.status=='created'}"><div class="university-status-message"><g:message code="studyCenter.create.message"/></div></g:if>
    <g:elseif test="${params.status=='updated'}"><div class="university-status-message"><g:message code="studyCenter.update.message"/></div></g:elseif>
    <g:form controller="rollNo" action="issueRollNo" method="post" name="createStudyCenter" id="createStudyCenter">
        <table class="university-table-1-2">
            <tr>
                <td><label><g:message code="default.createRollNo.nameOfApplicante"/></label></td>
                <td><input type="text" name="applicantName" id="name" value="${rollNoInstance?.nameOfApplicant}" class="university-size-1-3"/></td>
            </tr>
            <tr>
                <td><label><g:message code="default.createRollNo.course"/></label></td>
                <td><input type="text" name="course" value="${rollNoInstance?.course}" id="address" maxlength="" class="university-size-1-3"/></td>
            </tr>
            <tr>
                <td><label><g:message code="default.createRollNo.session"/></label></td>
                <td><input type="text" name="session" value="${rollNoInstance?.session}"  maxlength="" class="university-size-1-3"/></td>

            </tr>

            <tr>
                <td><label><g:message code="default.createRollNo.omrFormNo"/></label></td>
                <td><input type="text" name="omrFormNo" value="${rollNoInstance?.omrFormNo}"  maxlength="" class="university-size-1-3"/></td>
            </tr>
            <tr>
                <td><label><g:message code="default.createRollNo.mobileNo"/></label></td>
                <td><input type="text" name="mobileNo" value="${rollNoInstance?.contactNo}"  maxlength="" class="university-size-1-3"/></td>
            </tr>
            <tr>
                <td><label><g:message code="default.createRollNo.examinationCentre"/></label></td>
                <td><input type="text" name="examinationCentre" value="${rollNoInstance?.examinationCentre}"  maxlength="" class="university-size-1-3"/></td>
            </tr>

            <tr>
                <td><input type="hidden" value="${rollNoInstance?.id}" name="rollNoId" >
                    <input type="submit" value="<g:if test="${params.type!='edit'}"><g:message code="default.button.create"/></g:if><g:else><g:message code="default.button.save"/></g:else>"  class="university-button" onclick="validate()"></td>
                <td><input type="button" value="<g:message code="default.button.clear"/>" class="university-button" ></td>
            </tr>
        </table>
    </g:form>
</div>
</body>
</html>