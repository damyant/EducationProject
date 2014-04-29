<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/29/2014
  Time: 2:28 PM
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
        <h3>Upload Internal Marks</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
        <form id="uploadInternalMarks" name="uploadInternalMarks">
        <table class="inner university-size-full-1-1">
            <tr>
                <td class="university-size-1-4">
                    <label>Select Study Centre<span class="university-obligatory">*</span></label>
                </td>
                <td class="university-size-3-4">
                    <g:select name="examinationCenter" id="city" optionKey="id" class="university-size-1-3"
                              optionValue="name" from="${studyCentreList}"
                              noSelection="['': ' Select Study Centre']"
                              onchange=""/>
                </td>
            </tr>
            <tr>
                <td class="university-size-1-4">
                    <label>Select Course<span class="university-obligatory">*</span></label>
                </td>
                <td class="university-size-3-4">
                    <g:select name="programList" class="university-size-1-3" optionKey="id"
                              optionValue="courseName"
                              from="${programList}" noSelection="['': ' Select Program']"
                              onchange="getSemester()"/>
                </td>
            </tr>
            <tr>
                <td class="university-size-1-4">
                    <label>Select Term<span class="university-obligatory">*</span></label>
                </td>
                <td class="university-size-3-4">
                    <select name="programTerm" class="university-size-1-3" id="semesterList">
                        <option value="">Select Semester</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="university-size-1-4">
                    <label>Upload Internal Marks Sheet<span class="university-obligatory">*</span></label>
                </td>
                <td class="university-size-3-4">
                    <input type="file" class="university-size-1-3"  name="internalMarks" id="internalMarks">
                </td>
            </tr>
            <tr>
                <td class="university-size-1-4">

                </td>
                <td class="university-size-3-4">
                    <input type="submit" class="university-button" onchange="validate()" value="Submit"/>
                </td>
            </tr>
        </table>
        </form>
    </fieldset>
</div>

</body>
</html>