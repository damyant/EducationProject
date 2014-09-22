<%--
  Created by IntelliJ IDEA.
  User: damyant
  Date: 9/18/14
  Time: 1:24 PM
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
        <h3>Notice Upload</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:uploadForm id="ntc1" controller="admin" action="noticeBoardSave">

            <table class="university-size-full-1-1 inner spinner">
                <tr>
                    <g:if test="${noticeIns}">
                        <input type="hidden" value="${noticeIns?.id}" name="noticeUpdate"/>
                    </g:if>
                    <td class="university-size-1-3">Name<span class="university-obligatory">*</span></td>
                    <td class="university-size-1-3"><input type="text" name="noticeHeader" class="university-size-1-2" value="${noticeIns?.noticeHeader}"></td>
                </tr>
                <tr>
                    <g:if test="${noticeIns}">
                        <td class="university-size-1-3">Upload Pdf File</td>
                    </g:if>
                    <g:else>
                    <td class="university-size-1-3">Upload Pdf File<span class="university-obligatory">*</span></td>
                    </g:else>
                        <td class="university-size-1-3">
                        <input type='file' id="fle" name="fle" >
                    </td>

                </tr>
                <g:if test="${noticeIns}">
                    <tr>
                        <td class="university-size-1-3">Notice Status<span class="university-obligatory">*</span></td>
                        <td class="university-size-1-3">
                            <label><input type='radio' id="Active" value="Active" name="noticeStatus" /> Active</label>
                            <label> <input type='radio' id="Archive" value="Archive"  name="noticeStatus" />Archive</label>
                        </td>

                    </tr>
                </g:if>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" class="university-size-1-2 university-button" value="Submit"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </g:uploadForm>
    </fieldset>
</div>


</body>
</html>