<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/29/2014
  Time: 11:45 AM
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
        <h3>Creation of Category</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <div class="university-size-1-1" style="margin-left: 10px;">
            <label>
                <h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory</h6>
            </label>
        </div>
        <g:form controller="category" action='saveNewCategory'  method='post'  name='crateNewCategory' id='crateNewCategory'>
            <table class="inner">
                %{--<tr>--}%
                    %{--<td class="university-size-1-4">--}%
                        %{--<label>District <span class="university-obligatory">*</span></label>--}%
                    %{--</td>--}%
                    %{--<td class="university-size-3-4">--}%
                        %{--<g:select name="district" id="district" onselect="" optionKey="id"--}%
                                  %{--class="university-size-1-3" optionValue="districtName" value="${cityInst?.district?.id}"--}%
                                  %{--from="${districtList}" noSelection="['':' Select District']"/>--}%
                        %{--<input type="hidden" name="isCity" value="city"/>--}%
                    %{--</td>--}%
                %{--</tr>--}%
                <tr>
                    <td class="university-size-1-4">
                        <label>Category Name <span class="university-obligatory">*</span></label>
                    </td>
                    <td class="university-size-3-4">
                        <input type="text" class="university-size-1-3" id="categoryName" value="${programType?.type}" name="categoryName"/>
                        <input type="hidden" name="categoryId" value="${programType?.id}"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td> <input type="submit" id="submitButton" value="Submit" onclick="validate()"  onkeypress="return onlyAlphabets(event);"  class="university-size-1-3" style="height: 40px;font-weight: bolder;"></td>
                </tr>
            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>