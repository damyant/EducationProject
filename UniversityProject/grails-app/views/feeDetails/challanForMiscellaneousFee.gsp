<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 5/5/2014
  Time: 11:21 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <g:javascript src='admitCard.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
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
            <h3>Generate Miscellaneous Fee Challan</h3>
        <g:form name="challanForStudyCenter" id="challanForStudyCenter" controller="feeDetails" action="generateChallanForMiscellaneousFee">
            <g:hiddenField name="studentListId" id="studentListId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
            <table class="inner university-size-full-1-1" style="margin: auto">
                <tr><td><label>Select Fee Catagory</label></td>
                    <td>
                        <g:select name="feeCategory" class="university-size-1-1" id="feeCategory" optionKey="id"
                                  optionValue="type"
                                  from="${miscFeeType}" noSelection="['': ' Select Fee Category']"
                                  onchange="enableAll()"/>
                    </td>
                    <td  style="text-align: center;"></td>
                    <td></td>
                </tr>
                <tr><td><label>Select Program Catagory</label></td>
                    <td>
                        <g:select name="programCategory" disabled="true" class="university-size-1-1" id="programCategory" optionKey="id"
                                  optionValue="type"
                                  from="${programCategory}" noSelection="['': ' Select Program Category']"
                                  onchange="loadProgram(this)"/>
                    </td>
                    <td  style="text-align: center;"></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="university-size-1-4"><label>Select a Program</label></td>
                    <td class="university-size-1-4">
                        <g:select name="programList" disabled="true" class="university-size-1-1" id="programList" optionKey="id"
                                  optionValue="courseName"
                                  from="" noSelection="['': ' Select Program']"
                                  onchange="getSemester(this)"/>
                    </td>
                    <td class="university-size-1-4" style="text-align: center;">OR</td>
                    <td class="university-size-1-4"><input type="checkbox" id="allProgram" name="allProgram" disabled="true"/><label for="allProgram">All Programs</label></td>
                </tr>
                <tr><td><label>Select a Term</label></td>
                    <td>

                        <select name="semesterList" class="university-size-1-1" disabled="true" id="semesterList" >
                            <option value="">Select Semester</option>
                        </select>
                    </td>
                    <td  style="text-align: center;"></td>
                    <td></td>
                </tr>
            </table>
            <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1"> <input type="button" value="Show Students" onclick="populateStudentListForMiscFee()" class="ui-button university-size-1-4" style="margin: auto;"></div>
            <table id="studyCenterFeeEntryTable" class="university-size-full-1-1" style="margin: auto;border:1px solid #dddddd; " hidden="true">
                <thead>
                <tr>
                    %{--<th style="width: 10%;padding-left: 10px;"><input type="checkbox" name="selectAll" id="selectAll"><label for="selectAll">Select All</label></th>--}%
                    <th  style="width: 10%;">Serial No</th>
                    <th style="width: 26.6%;">Roll No</th>
                    <th style="width: 26.6%;">Student Name</th>
                    <th style="width: 26.6%;">Amount </th>
                    <th style="width: 10%;">Semester</th>
                </tr>
                </thead>
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
            <br/>
            <div class="university-size-1-2"  style="margin: 5px auto;width:98%;text-align: center;vertical-align: middle; border: 1px solid #BDBDBD; padding: 0.5%;border-radius: 4px;" id="rangeRadioButtons" hidden="hidden">
                <div class="university-size-1-3 university-display-inline"><input type="radio" id="rangeEntry" name="entry" value="Range"> <label for="rangeEntry">Generate challan By Range</label> </div>
                <div class="university-size-1-3 university-display-inline"><input type="radio" id="individualEntry" name="entry" value="Range"> <label for="individualEntry">Generate challan Individually</label></div>
            </div>
            <br/>
            <table id="paymentDetails" class="university-size-full-1-1" style="margin: auto;border:1px solid #dddddd;visibility: hidden " >

            </table>
            <br/>
            <div style="width:100%;margin:auto; text-align: center;">
                <input type="button" class="university-size-1-3 ui-button" id="generateFeeChallan" onclick="generateChallanForRange()" value="Generate Fee Challan" style="visibility: hidden;"/>
            </div>
        </g:form>
    </fieldset>
    <script>
        $('#generateFeeChallan').click(function(){
            if($('#serialNoTo').val()!="") {
                setTimeout(function () {
//                    $('#challanForStudyCenter')[0].reset();
                    $('#studyCenterFeeEntryTable').attr('hidden', true);
                    $('#rangeRadioButtons').attr('hidden', true);
                    document.getElementById("paginationDiv").style.visibility = "hidden";
                    document.getElementById("generateFeeChallan").style.visibility = "hidden";
                    document.getElementById("paymentDetails").style.visibility = "hidden";
                    populateStudentListForMiscFee()
                }, 5000)
            }
        })
    </script>
</div>
</body>
</html>