<%--
  Created by IntelliJ IDEA.
  User: sonali
  Date: 27/3/14
  Time: 5:53 PM
--%>


<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Bulk Fee Entry</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
    %{--<h3>Approved Student List</h3>--}%
        <g:form controller="admin" action="generateRollNo" id="generateRollNo" name="generateRollNo">
            <g:hiddenField name="studentId" id="studentId"/>
            <g:hiddenField name="pageType" id="pageType" value="Registered RollNo"/>
            <table class="inner" style="margin: auto;" id="fee-table">
                <tr>
                    <td style="min-width: 12%">
                        <label for="studyCenter">Select Criteria</label>
                    </td>
                    <td style="width: 18%">
                        <g:select name="filter" class="university-size-1-1" id="studyCenter"
                                  from="${filterType}" optionKey="" optionValue=""
                                  noSelection="['null': ' Select filter Center']" onchange="enablecriteria(this)"/>
                    </td>

                    <td style="min-width: 10%">
                        <div id="programl" hidden="true">
                        <label for="programId">Select Program</label>
                        </div>
                    </td>

                    <td style="width: 20%">
                        <div id="programv" hidden="true">
                        <g:select name="programId" id="programId" class="university-size-1-1" from="${programList}"
                                  optionKey="id" optionValue="courseName" noSelection="['null': ' Select Program']"
                                  onchange="getStudentsList()"  />
                   </div>
                    </td>

                    <td style="min-width: 10%">
                        <div id="studyCentrel" hidden="true">
                            <label for="programId">Select Study Centre</label>
                        </div>
                    </td>

                    <td style="width: 20%">
                        <div id="studyCentrev" hidden="true">
                            <g:select name="programId" id="studyCentre" class="university-size-1-1" from="${studyCentre}"
                                      optionKey="id" optionValue="name" noSelection="['null': ' Select Program']"
                                      onchange="getStudentsList()"  />
                        </div>
                    </td>

                    <td style="min-width: 10%">
                        <div id="admissionDatel" hidden="true">
                            <label for="programId">Select Admission Date</label>
                        </div>
                    </td>

                    <td style="width: 33%">
                        <div id="admissionDatev" hidden="true">
                            <input type="text" name="draftDate" maxlength="30" id="admissionDate" class="university-size-2-3"
                                    required="true"
                                      onchange="getStudentsList()"  />
                        </div>
                    </td>


                    <td style="width: 10%"></td>
                </tr>
            </table>
            <table id="studentList" class="inner university-table-1-3">
                <thead></thead>
                <tbody></tbody>
            </table>
        </g:form>
        <div id="msg"></div>
    </fieldset>
</div>
</body>
</html>