<%--
  Created by IntelliJ IDEA.
  User: sonali
  Date: 27/3/14
  Time: 5:53 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <title>Bulk Fee Entry</title>
    <meta name="layout" content="main"/>
    <g:javascript src='dataEntry.js'/>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.base.css')}" type='text/css'>
    %{--<link rel='stylesheet' href="${resource(dir: 'css', file: 'jquery.ui.theme.css')}" type='text/css'>--}%
    <script>

    </script>

</head>

<body>
<div id="main">
<div>
    <fieldset class="form">
        <table class="inner" style="margin: auto;" id="fee-table">
                <tr>
                    <td style="width: 18%">
                        <label for="filterType">Select Criteria:</label>
                    </td>
                    <td style="width: 18%">
                        <g:select name="filter" class="university-size-1-1" id="filterType"
                                  from="${filterType}" optionKey="" optionValue=""
                                  noSelection="['null': ' Select filter']" onchange="enablecriteria(this)"/>
                    </td>

                    <td style="min-width: 18%">
                        <div id="programl" hidden="true">
                        <label for="programId">Select Programme:</label>
                        </div>
                    </td>

                    <td style="width: 18%">
                        <div id="programv" hidden="true">
                        <g:select name="programId" id="programId" class="university-size-1-1" from="${programList}"
                                  optionKey="id" optionValue="courseName" noSelection="['null': ' Select Programme']"
                                  onchange="getStudentsList()"  />
                   </div>
                    </td>

                    <td style="min-width: 18%">
                        <div id="studyCentrel" hidden="true">
                            <label for="studyCentre">Select Study Centre:</label>
                        </div>
                    </td>

                    <td style="width: 18%">
                        <div id="studyCentrev" hidden="true">
                            <g:select name="studyCentre" id="studyCentre" class="university-size-1-1" from="${studyCentre}"
                                      optionKey="id" optionValue="name" noSelection="['null': ' Select Study Centre']"
                                      onchange="getStudentsList()"  />
                        </div>

                    </td>


                    %{--<td style="min-width: 10%">--}%
                    %{--<div id="admissionDatel" hidden="true">--}%
                        %{--<label for="admissionDate">Select Admission Date</label>--}%
                    %{--</div>--}%
                %{--</td>--}%

                    %{--<td style="width: 33%">--}%
                        %{--<div id="admissionDatev" hidden="true">--}%
                            %{--<input type="text" name="admissionDate" maxlength="30" id="admissionDate" class="university-size-2-3"--}%
                                   %{--required="true"--}%
                                   %{--onchange="getStudentsList()"  />--}%
                        %{--</div>--}%
                    %{--</td>--}%


                    <td style="width: 10%"></td>
                </tr>
            </table><br/>
            <table id="studentList" class="inner university-table-1-3">
                <thead></thead>
                <tbody></tbody>
            </table>




        %{--</g:form>--}%



        %{--<div id="statusofApp" style="display: none">--}%



 <!--your content end-->
        <div id="msg"></div>
    </fieldset>
    <div id="dialog" title="Basic dialog">
        <form  id="createFeeDetail">
            <div id="responseMessage" class="university-status-message"></div>
            <div class="university-size-1-1" style="margin-left: 4px;">
                <label>
                    <h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory</h6>
                </label>
            </div>

            <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'Roll Number', 'error')} required university-size-1-1">
                <div class="university-size-1-3">
                    <label for="feeType">
                        <g:message code="feeDetails.rollno.label" default="Roll Number"/>
                        <span class="university-obligatory">*</span>
                    </label>
                </div>

                <div class="university-size-2-3">

                    <g:textField id="rollNo" name="rollNo"  class="many-to-one university-size-1-2" value=""/>
                    <g:hiddenField name="studentId" id="currentStudentId" />
                    <g:hiddenField name="nextStudentId" id="nextStudentId" />
                    <g:hiddenField name="previousStudentId" id="previousStudentId" />
                    <g:hiddenField name="currentStudent" id="currentStudent"/>


                </div>
            </div>

            <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'feeType', 'error')} required">
                <div class="university-size-1-3">
                    <label for="feeType">
                        <g:message code="feeDetails.feeType.label" default="Fee Type"/>
                        <span class="university-obligatory">*</span>
                    </label>
                </div>

                <div class="university-size-2-3">
                    <g:select id="feeType" name="feeType" from="${examinationproject.FeeType.list()}"
                              optionKey="id"
                              optionValue="type"
                              class="many-to-one university-size-1-2"/>
                </div>
            </div>

            <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentMode', 'error')} ">
                <div class="university-size-1-3">
                    <label for="paymentMode">
                        <g:message code="feeDetails.paymentMode.label" default="Payment Mode"/>
                        <span class="university-obligatory">*</span>
                    </label>
                </div>

                <div class="university-size-2-3">
                    <g:textField name="paymentMode" value="${feeDetailsInstance?.paymentMode}"
                                 class="university-size-1-2"/>
                </div>
            </div>

            <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftNumber', 'error')} ">
                <div class="university-size-1-3">
                    <label for="draftNumber">
                        <g:message code="feeDetails.draftNumber.label" default="Draft Number"/>
                        <span class="university-obligatory">*</span>
                    </label>
                </div>

                <div class="university-size-2-3">
                    <g:textField name="draftNumber" onkeypress="return isNumber(event)" value="${feeDetailsInstance?.draftNumber}"
                                 class="university-size-1-2"/>
                </div>
            </div>

            <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'paymentDate', 'error')} required">
                <div class="university-size-1-3">
                    <label for="paymentDate">
                        <g:message code="feeDetails.paymentDate.label" default="Payment Date"/>
                        <span class="university-obligatory">*</span>
                    </label>
                </div>

                <div class="university-size-2-3">
                    <g:textField name="paymentDate" maxlength="10" id="datePick1" class="university-size-1-2" value=""
                                />
                </div>
            </div>

            <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'draftDate', 'error')} required">
                <div class="university-size-1-3">
                    <label for="draftDate">
                        <g:message code="feeDetails.draftDate.label" default="Draft Date"/>
                        <span class="university-obligatory">*</span>
                    </label>
                </div>

                <div class="university-size-2-3">
                    <input type="text" name="draftDate" maxlength="10" id="datePick" class="university-size-1-2"
                           />
                </div>
            </div>

            <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'issuingBank', 'error')} ">
                <div class="university-size-1-3">
                    <label for="issuingBank">
                        <g:message code="feeDetails.issuingBank.label" default="Issuing Bank"/>
                        <span class="university-obligatory">*</span>
                    </label>
                </div>

                <div class="university-size-2-3">
                    <g:textField name="issuingBank" id="issuingBank" value="${feeDetailsInstance?.issuingBank}"
                                  class="university-size-1-2"/>
                </div>
            </div>

            <div class="fieldcontain ${hasErrors(bean: feeDetailsInstance, field: 'issuingBranch', 'error')} ">
                <div class="university-size-1-3">
                    <label for="issuingBranch">
                        <g:message code="feeDetails.issuingBranch.label" default="Issuing Branch"/>
                        <span class="university-obligatory">*</span>
                    </label>
                </div>

                <div class="university-size-2-3">
                    <g:textField name="issuingBranch" id="issuingBranch"
                                 value="${feeDetailsInstance?.issuingBranch}"
                                 class="university-size-1-2"/>
                </div>
            </div>

            <div class="fieldcontain">
                <div class="university-size-1-3">
                    &nbsp;
                </div>

                <div class="university-size-2-3">
                    <input type="button" name="create" class="save university-button" onclick="submitFeeDetail()"
                                    value='Save & Next'  />


        <input type="button" name="Next" id="next" style="display: none" class="save university-button" value="Next" onclick="nextStudent()"/>
        <input type="button" name="Back" id="back" style="display: none" class="save university-button" value='Back' onclick="previousStudent()"/>
                </div>
            </div>
        </form>
    </div>
    </div>

    </div>

</div>
</body>
</html>