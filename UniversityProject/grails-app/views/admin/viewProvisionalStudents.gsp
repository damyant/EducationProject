<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 3/26/14
  Time: 10:24 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>View Provisional Students</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Assign Roll Number</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
    %{--<h3>Generate Roll Number</h3>--}%
        <g:if test="${params.rollNo == 'generated'}">
            <div class="message"><div class="university-status-message"><g:message
                    code="rollNo.Generated.message"/></div></div>
        </g:if>
        <g:form id="generateRollNo" name="generateRollNo">
            <g:hiddenField name="studentId" id="studentId"/>
            <g:hiddenField name="pageType" id="pageType" value="Assign RollNo"/>
            <table class="inner" style="margin: auto;">
                <tr>
                    <sec:ifAnyGranted roles="ROLE_GENERATE_ROLL_NO">
                        <g:hiddenField name="roleType" id="roleType" value="admin"/>
                        <td style="min-width: 10%">
                            <label for="programId">Select Program</label>
                        </td>
                        <td style="width: 30%">
                            <g:select name="programId" id="programId" class="university-size-1-1" from="${programList}"
                                      optionKey="id" optionValue="courseName" noSelection="['null': ' Select Program']"
                                      onchange="getStudents()"/>
                        </td>
                        <td  style="width: 30%"></td>
                        <td  style="width: 30%"></td>
                    </sec:ifAnyGranted>
                </tr>
            </table>

            <table id="studentList" class="inner university-table-1-3">
                <thead></thead>
                <tbody></tbody>
            </table>
            <table id="studentListButton" class="inner university-table-1-3">
                <tbody></tbody>
            </table>
            <div style="text-align: center;visibility: hidden;" id="paginationDiv" class="university-size-full-1-1">
                <br/>
                <div class="pagination">
                    <a href="#" class="first" data-action="first">&laquo;</a>
                    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                    <input type="text" readonly="readonly"/>
                    <a href="#" class="next" data-action="next">&rsaquo;</a>
                    <a href="#" class="last" data-action="last">&raquo;</a>
                </div>
            </div>
        </g:form>
        <div id="msg"></div>
    </fieldset>
</div>
</body>
</html>