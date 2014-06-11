<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 11/6/14
  Time: 1:51 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Single Marks Sheet Generation</title>
    <g:javascript src='postExamination.js'/>
</head>

<body>
<script>
    $(document).ajaxStart(function(){
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
</script>

<div id="main">
    <fieldset class="form">
        <h3>Single Marks Sheet Generation</h3>
        <g:form name="marksFoilId" id="marksFoilId" controller="postExamination" action="generateMarksFoilSheet">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
            <g:hiddenField name="btn"  id="btn" value=""/>
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>

            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>

            <table align="center" cellpadding="10" class="university-table-1-2 inner" style="width: 100%;margin: auto;" border="0">

                <tr>
                    <td style="align-self: center">
                        <g:select name="selectBox" id="selectBox" optionKey="id" class="university-size-1-2" value="" optionValue="" from=""  multiple="true" />
                    </td>
                </tr>
                <tr>
                    <td class="university-size-3-4" style="align-self: center">
                        <input type="text" class="university-size-1-3" id="" name=""/>
                    </td>
                </tr>
            </table>

            <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1">
                <input type="button" value="Save Data" class="ui-button university-size-1-4" style="margin: auto;">
            </div>

        </g:form>
        <div id="msgDiv"></div>

    </fieldset>
</div>
</body>
</html>