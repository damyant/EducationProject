<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 7/11/14
  Time: 3:15 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
</head>
<script>
    $(document).ajaxStart(function () {
        $.blockUI({ css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 5,
            color: '#fff'
        } });
    }).ajaxStop($.unblockUI);
    $('#photoUploadForm').ready(function () {
        if(${rollNoList}){
            alert("here")
            alert(${rollNoList})
        }
    })
</script>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Bulk Photo Upload</h3>
        <g:form name="photoUploadForm" id="photoUploadForm" controller="photoUpload" action="uploadPhoto">
            <g:if test="${flash.message}">
                <div class="message" role="status"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
            <table class="inner">
                <tr><td class="university-size-1-3">Enter Directory Path:</td><td class="university-size-2-3"><input type="text" class="university-size-1-2" name="filePath" id="filePath"/></td></tr>
                <tr><td>Select Session:</td><td>
                    <select name="admissionYear" class="university-size-1-2" id="admissionYear">
                        <option value="">Select Session</option>
                        <g:each in="${sessionList}" var="year">
                            <option value="${year}">${year}-${year + 1}</option>
                        </g:each>
                    </select>
                </td></tr>
                <tr><td></td><td><input type="submit" value="Upload" class="university-button"/></td></tr>
            </table>
            <h4>Note:</h4>
            <ul>
                <li>The Directory Path is the Directory location where all session directory are created.</li>
                <li>Enter the Directory location as "/home/Photo/".</li>
                <li>The Image file extension supported are (.jpg / .png / .jpeg)</li>
                <li>Currently Total Student Without Photo :  ${studentListInst.size()}.</li>
            </ul>
        </g:form>
        <br/>
        %{--<table class="inner">--}%
            %{--<g:each in="${studentListInst}" var="inst">--}%
                %{--<tr><td style="text-align: center">${inst.applicationNo.substring(4)}</td></tr>--}%
            %{--</g:each>--}%
        %{--</table>--}%
    </fieldset>
</div>
</body>
</html>