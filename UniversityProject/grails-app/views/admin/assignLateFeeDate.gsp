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
    <g:javascript src='dataEntry.js'/>

</head>
<body>
<div id="main">

    <fieldset class="form">
        <h3>Assign Admission Fee Date</h3>
        <g:form name="assignLateFeeDate" id="assignLateFeeDate" controller="admin" action="saveLateFeeDate">
            <g:hiddenField name="studentListId" id="programId" value="" />
            <input type="hidden" name="paramType" id="paramType" value="${params?.type}"/>
            <table class="inner university-size-full-1-1" style="margin: auto">
                <tr><td><label>Select Program Category</label></td>
                    <td>
                        <g:select name="programCategory" class="university-size-2-3" id="programCategory" optionKey="id"
                                  optionValue="type"
                                  from="${programCategory}" noSelection="['': ' Select Program Category']"
                                  onchange="filterProgram(this)"/>
                    </td>
                    <td  style="text-align: center;"></td>
                    <td></td>
                </tr>
                <tr>
                    <td class="university-size-1-3"><label>Select a Program</label></td>
                    <td class="university-size-2-3">
                        <div id="courseListDiv" class="mainContent3 university-scrollable-y university-size-2-3" style="border: 1px solid #dddddd;">
                        <table id="courseList" class="inner university-size-full-1-1" >
                            <thead></thead>
                            <tbody></tbody>
                        </table>
                        </div>
                    </td>
                    </tr>
                    <tr><td><label>Assign Examination Date</label></td>
                    <td><input type="text" name="lateFeeDate" maxlength="10" disabled onkeypress="disableKeyInput(this)" onchange="loadAssignDate(this)" class="university-size-1-2" id="datepicker"
                               value=""></td>
                    </tr>
            </table>

            <div style="text-align: center; margin: 10px auto;" class="university-size-full-1-1"> <g:submitButton name="submit" value="Assign LateFee Date" onclick="checkValidation()" class="ui-button university-size-1-4" style="margin: auto;"/></div>

            <br/>
            </table>
            <br/>

        </g:form>
    </fieldset>

</div>
<script>

    $(function () {
        $(function () {
            $("#datepicker").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "mm/dd/yy",
                minDate: 0
            });

        });
    });
</script>
</body>
</html>