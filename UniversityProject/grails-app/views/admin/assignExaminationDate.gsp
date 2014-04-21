<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/11/2014
  Time: 4:57 PM
--%>
<%@ page import="examinationproject.ExaminationCentre; examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Assign Examination Date</title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'jquery.ui.timepicker.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.ui.core.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.ui.tabs.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.ui.widget.min.js')}"></script>

    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.timepicker.css')}" type='text/css'>
</head>

<body>
<div id="main">
    <div class="university-status-message"><div id="successMessage">&nbsp;</div></div>
    <fieldset class="form">
        <form id="assignDate">
        <table class="inner" style="width: 95%;margin: auto">
            <tr>
                <td class="university-size-1-4"><label>Select a Course</label></td>
                <td class="university-size-1-4">
                    <g:select name="programList" id="programList" class="university-size-1-1" optionKey="id"
                              optionValue="courseName"
                              from="${programList}" noSelection="['': ' Select Program']"
                              onchange="getSemesterAndSubjectList()"/>
                </td>
                <td class="university-size-1-2"></td>
            </tr>
        </table>

            <table style="width: 95%;margin: auto" id="subjectList" class="inner">

            </table>
            <div class="university-status-message"><h3><div id="msgDiv"></div></h3></div>
        </form>
    </fieldset>
</div>
<script type="text/javascript">
    $(document).ready(function() {

    });
</script>
</body>
</html>
