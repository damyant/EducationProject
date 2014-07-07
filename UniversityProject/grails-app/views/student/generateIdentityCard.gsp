<%--
  Created by IntelliJ IDEA.
  User: Chandan
  Date: 31/3/14
  Time: 12:56 PM
--%>
<%@ page import="examinationproject.City" contentType="text/html;charset=UTF-8" %>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
</head>
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
<body>
<div id="main">
    <fieldset>
        <h3>Generate Identity Card</h3>
        <g:if test="${flash.message}">
            <div class="university-status-message"> <label class="error">${flash.message}</label></div>
        </g:if>
        <g:form name="identityCardForm" id="identityCardForm" controller="student" action="printIdentityCard">
            <g:hiddenField name="studentList" id="studentList"/>
            <div>
                <table class="university-table-1-3 inner" style="width: 80%;margin-left: 20px;">

                    <tr>
                        <td><label>Select a Programme</label></td>
                        <td>
                            <g:select name="programList" class="university-size-1-1" optionKey="id"
                                      optionValue="courseName"
                                      from="${programList}" noSelection="['': ' Select Programme']"
                                      onchange="enableShowCandidateIdentity()"/>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Select a Session</label></td>
                        <td>
                            <select name="admissionYear" class="university-size-1-1" id="admissionYear" onchange="enableShowCandidateIdentity()">
                                <option value="">Select Session</option>
                                <g:each in="${sessionList}" var="year">
                                    <option value="${year}">${year}-${year+1}</option>
                                </g:each>
                            </select>
                        </td>
                        <td></td>
                    </tr>

                    <tr>
                        <td></td>
                        <td>
                            <input type="button" class="university-button" id="showCandidates" value="Show Candidates"
                                   onclick="getStudentsForIdentityCard()" disabled/>
                        </td>
                        <td>   </td>
                    </tr>
                </table>
            </div>
            <div class="university-status-message"><label id="showErrorMessage" hidden=""></label></div>
            <div id="studentListTable" class="university-List-View university-scrollable-y" hidden="">
                <table id="admitCardTab" class="inner" style="width:100%;margin:auto">
                    <thead>
                    <tr>
                        <th style="width: 17%;padding-left: 10px;">
                            <input type="checkbox" id="selectAll" name="selectAll"/> Select All
                        </th>
                        <th style="width: 27%;">Sr. No.</th>
                        <th style="width: 27%;">Roll No.</th>
                        <th style="width: 29%;">Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
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
            <div id="studentListPrint"
                 style="margin: 5px auto;width:94%;text-align: center;vertical-align: middle; border: 1px solid #BDBDBD; padding: 0.5%;border-radius: 4px;"
                 hidden="">

                <label class="university-left-right-margin">
                    Download Range
                </label>
                <label class="university-left-margin" style="color: #000; font-size: 17px;"><b>From</b></label>
                <input type="text" name="from" id="from" placeholder="Enter SrNo" width="7" onclick="this.value = ''" onkeypress="return isNumber(event)"
                       style="width: 80px;margin: auto 20px;text-align: center;border-radius: 2px;">
                <label class="university-left-right-margin" style="color: #000;font-size: 17px;"><b>To</b></label>
                <input type="text" name="to" id="to" class="university-left-right-margin" placeholder="Enter SrNo" width="7" onclick="this.value = ''"
                       onkeypress="return isNumber(event)"
                       style="width: 80px;margin: auto 20px;text-align: center;border-radius: 2px;">

            </div>

            <div id="studentListPrintButton" style="margin: 10px auto;width:94%;text-align: center;" hidden="">
                <input type="button" value="Download" onclick="generateIdentityCard()" class="university-button">
            </div>
        </g:form>
    </fieldset>
</div>
</body>
</html>