<%--
  Created by IntelliJ IDEA.
  User: Shweta
  Date: 31/3/14
  Time: 12:56 PM
--%>
<%@ page import="examinationproject.City" contentType="text/html;charset=UTF-8" %>
<%@ page import="examinationproject.ProgramDetail" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='admitCard.js'/>
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
        <h3>STUDENT ADMIT CARD</h3>
        <g:if test="${flash.message}">
            <div class="university-status-message"> <label class="error">${flash.message}</label></div>
        </g:if>
    <g:form name="admitCardForm" id="admitCardForm" controller="admitCard" action="printAdmitCard">
            <g:hiddenField name="studentList" id="studentList"/>
            <div>
                <table class="university-table-1-3 inner" style="width: 80%;margin-left: 20px;">
                    <tr>
                        <td><label>Select an Examination Centre</label></td>
                        <td>
                            <g:select name="examinationCentre" id="examinationCentre" optionKey="id" class="university-size-1-1"
                                      optionValue="cityName" from="${examinationCenterList}"
                                      noSelection="['': ' Select Exam Centre']"
                                      onchange="enableShowCandidate()"/>
                        </td>
                    </tr>
                    <tr>
                        <td><label>Select a Programme</label></td>
                        <td>
                            <g:select name="programList" class="university-size-1-1" optionKey="id"
                                      optionValue="courseName"
                                      from="${programList}" noSelection="['': ' Select Programme']"
                                      onchange="showExamVenueList(),loadProgramTerm(),getSession(this),enableShowCandidate()"/>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Select a Term</label></td>
                        <td>
                            <select name="programTerm" class="university-size-1-1" id="semesterList">
                                <option value="">Select Term</option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr><td><label>Select a Session</label></td>
                        <td>
                            <g:select name="programSession" from="" class="university-size-1-1" id="SessionList"
                                      onchange="enableShowCandidate()" noSelection="['': ' Select Session']"/>

                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><label>Select Examination Venue</label></td>
                        <td>
                            <g:select name="examinationVenue" class="university-size-1-1" id="examCenterList" from=""
                                      onchange="showExamVenueCapacity(),enableShowCandidate()"
                                      noSelection="['': ' Select Exam Venue']"/>
                        </td>

                        <td></td>
                    </tr>
                    <tr>
                        <td>
                            <div id="maxCapacityBox" hidden="">
                                <b><label>Maximum Capacity </label><input type="text" class="university-size-1-2"
                                                                       id="totalCapacity" style="text-align: center;" readonly/></b>
                            </div>
                        </td>
                        <td>
                            <input type="button" class="university-button" id="showCandidates" value="Show Candidates"
                                   onclick="getStudentsForAdmitCard()" disabled/>
                        </td>
                        <td>
                            <div id="remainingCapacityBox" hidden="">
                                <b><label>Available Capacity </label><input type="text" class="university-size-1-2"
                                                                         id="remainingCapacity" style="text-align: center;" readonly/></b>
                            </div>
                        </td>
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
                <input type="button" value="Download" onclick="generateAdmitCard()" class="university-button">
            </div>
        </g:form>
    </fieldset>
</div>
</body>
</html>