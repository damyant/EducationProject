<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/21/2014
  Time: 11:27 AM
--%>
<%@ page import="examinationproject.City; examinationproject.StudyCenter" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Students List</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
    <script>

    </script>
</head>

<body>
    <div id="main">
        <fieldset class="form">
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
            <table class="university-size-full-1-1 inner" style="margin: auto;">
                <tr>
                    <td class="university-size-1-5">
                        <label for="studyCenter">Select Study Center</label>
                    </td>
                    <td class="university-size-1-5">
                        <g:select name="studyCenter" class="university-size-1-1" id="studyCenter"
                                  from="${studyCenterList}" optionKey="id" optionValue="name"
                                  noSelection="['null': ' Select Study Center']" onchange="enableProgram(this)"/>
                    </td>
                    <td class="university-size-1-5">
                    </td>
                    <td class="university-size-1-5">
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-5">
                        <label for="programId">Select Program</label>
                    </td>
                    <td class="university-size-1-5">
                        <g:select name="programId" id="programId" class="university-size-1-1" from="${programList}"
                                  optionKey="id" optionValue="courseName" noSelection="['null': ' Select Program']"
                                  onchange="generateStudentsList()" disabled="true"/>
                    </td>
                    <td class="university-size-1-5">
                        <label for="programId">Enter Roll Number</label>
                    </td>
                    <td class="university-size-1-5">
                        <g:textField name="rollNo" id="StudentRollNo" class="university-size-1-1" />
                    </td>

                    <td class="university-size-1-5">
                       <input type="button" name="view" id="rollNo" value="Update" class="university-size-1-1" onclick="viewStudentByRollNo()" />
                    </td>



                </tr>
            </table>
            <table id="studentList" class="inner university-size-full-1-1 university-table-bg">
                <thead></thead>
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
            <div id="msg"></div>
            <div></div>
        </fieldset>
    </div>
<script>
    $(document).ready(function () {
   $('#studyCenter').val('')
   $('#programId').val('')
//        alert($('#studentList tbody tr').length)

    })

</script>
</body>
</html>