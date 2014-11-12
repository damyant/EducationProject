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
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <title></title>
</head>

<body>
<div id="main">
    <g:hasErrors bean="${feeInstance}">
        <div class="errors">
            <g:renderErrors bean="${feeInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <fieldset class="form">
        <h3>Creation of Fee Type</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="programFee" action="saveNewFee" method='post' enctype="multipart/form-data"
                id="addNewFeeType" name="addNewFeeType">
            <div style="margin-left: 10px;margin-top: 20px;""><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="university-size-full-1-1 inner">
            <tr>
                <td class="university-size-1-3"><label for="feeType">Add Fee Type Name <span class="university-obligatory">*</span></label></td>
                <td class="university-size-2-3 ">
                    <input type="text" class="university-size-1-2" value="${feeInstance?.type}" name="type" id="feeType"/>
                    <input type="hidden" value="${feeInstance?.id}" name="feeId"/>
                </td>
            </tr>
            %{--<tr>--}%
                %{--<td class="university-size-1-3"><label for="programList">Select Program <span class="university-obligatory">*</span></label></td>--}%
                %{--<td class="university-size-2-3 ">--}%
                    %{--<g:select name="programList" id="programList" class="university-size-1-2" optionKey="id"--}%
                              %{--optionValue="courseName"--}%
                              %{--from="${programList}" noSelection="['': ' Select Program']"--}%
                              %{--onchange=""/>--}%
                %{--</td>--}%
            %{--</tr>--}%
            %{--<tr>--}%
                %{--<td class="university-size-1-3"><label for="feeAmount">Fee Amount <b>[&#8377;]</b><span class="university-obligatory"> *</span></label></td>--}%
                %{--<td class="university-size-2-3 ">--}%
                    %{--<input type="text" class="university-size-1-2" value="" name="feeAmount" id="feeAmount"/>--}%
                %{--</td>--}%
            %{--</tr>--}%
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