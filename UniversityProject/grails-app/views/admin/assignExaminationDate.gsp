<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 4/11/2014
  Time: 4:57 PM
--%>

<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Assign Examination Date</title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'jquery.ui.timepicker.js')}"></script>
    <script type="text/javascript"
            src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.ui.core.min.js')}"></script>
    <script type="text/javascript"
            src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.ui.tabs.min.js')}"></script>
    <script type="text/javascript"
            src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.ui.widget.min.js')}"></script>

    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.timepicker.css')}" type='text/css'>
</head>

<body>
<div id="main">

    <fieldset class="form">
        <h3>Assign Examination Date & Time</h3>
        <div class="university-status-message"><div id="successMessage">&nbsp;</div></div>
        <form id="assignDate">
            <table class="inner" style="width: 100%;margin: auto">
                <tr>
                    <td class="university-size-1-4"><label>Select a Programme</label></td>
                    <td class="university-size-1-4">
                        <g:select name="programList" id="programList" class="university-size-1-1" optionKey="id"
                                  optionValue="courseName"
                                  from="${programList}" noSelection="['': ' Select Programme']"
                                  onchange="getSemester(this)"/>
                    </td>

                    <td class="university-size-1-2"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4"><label>Select a Session</label></td>
                    <td class="university-size-1-4">
                        <g:select name="SessionList" id="SessionList" class="university-size-1-1"
                                  from="" noSelection="['': ' Select Session']"
                                  onchange="setType()"/>
                    </td>
                </tr>
                <tr>
                    <td class="university-size-1-4"><label>Select a Session Type</label></td>
                    <td class="university-size-1-4">
                     <select name="sessionType" id="sessionType" class="university-size-1-1"  onchange="getSemesterAndSubjectList()">
                         <option value="0">Select Session Type</option>
                         <option value="1">July</option>
                         <option value="2">January</option>
                     </select>
                    </td>
                </tr>
            </table>

            <table style="width: 100%;margin: auto" id="subjectList" class="inner">

            </table>

            <div class="university-status-message"><h5><div id="msgDiv"></div></h5></div>
        </form>
    </fieldset>
</div>
</body>
</html>
