
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 5/2/2014
  Time: 12:12 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <g:javascript src='validation.js'/>
    <title></title>
</head>

<body>
<div id="main">
    <g:hasErrors bean="${marksTypeInstance}">
        <div class="errors">
            <g:renderErrors bean="${marksTypeInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <fieldset class="form">
        <h3>${title}</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="marksType" action="saveMarksType" method='post'  enctype="multipart/form-data" id="marksTypeForm" name="marksTypeForm">
            <div style="margin-left: 10px;margin-top: 20px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="university-size-full-1-1 inner">
            <tr>

            <tr>
                <td class="university-size-1-3"><label for="marksTypeName">MarksType <span class="university-obligatory">*</span></label></td>
                <td class="university-size-2-3 ">
                    <input type="text" class="university-size-1-2" value="${marksTypeInstance?.marksTypeName}" name="marksTypeName" id="marksTypeName"/>

                </td>
            </tr>
                <g:hiddenField name="marksTypeId" value="${marksTypeInstance?.id}"/>
        <tr>
                <td class="university-size-1-3 ">&nbsp;</td>
                <td class="university-size-2-3 "><input type="submit" value="Submit" onclick="validate()" class="university-size-1-4 ui-button"/></td>
            </tr>
        </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>
