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
</head>

<body>
<div id="main">

    <fieldset class="form">
        <h3>Pay Admission Fee Entry</h3>
        <g:form controller="feeDetails" action="payChallanForStudyCenterStu" name="paychallanForStudyCenter" id="paychallanForStudyCenter">
        <table class="inner university-size-full-1-1" id="scStudnetList" style="margin: auto">
            <thead>
            <tr>
                <td class="university-size-1-4">Enter Challan Number</td>
                <td class="university-size-1-4"> <input type="text" name="searchChallanNo"id="searchChallanNo"   value=""/></td>
                <td class="university-size-1-4"><input type="button" value="Show Students" onclick="showListOfStudents()"/> </td>
                <td class="university-size-1-4"></td>
            </tr>
            </thead>
            <tbody></tbody>
        </table>

            <table id="studentPayList" class="university-size-full-1-1" style="visibility: hidden">
                <tr>
                    <td class="university-size-1-3">Payment Mode</td>
                    <td  class="university-size-2-3"><g:select name="paymentMode" class="university-size-1-2" id="paymentMode" optionKey="id"
                                  optionValue="paymentModeName"
                                  from="${paymentMode}" noSelection="['': ' Select Payment Mode']"
                                  onchange=""/></td>
                </tr>
                <tr>
                    <td  class="university-size-1-3">Payment Date</td>
                    <td  class="university-size-2-3"> <input type="text" name="paymentDate" maxlength="10" class="university-size-1-2" id="datepicker"
                                                             value=""></td>
                </tr>
                <tr>
                    <td>Bank</td>
                    <td><g:select name="bankName" class="university-size-1-2" id="bankName" optionKey="id"
                                  optionValue="bankName"
                                  from="${bankName}" noSelection="['': ' Select Bank']"
                                  onchange="loadBranch(this)"/></td>
                </tr>
                <tr>
                    <td>Bank</td>
                    <td><g:select name="branchLocation" class="university-size-1-2" id="branchLocation" optionKey=""
                                  optionValue=""
                                  from="" noSelection="['': ' Select Branch']"
                                  onchange=""/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" id="paySubmit" class="ui-button university-size-1-3" value="Pay" style="visibility: hidden"/> </td>
                </tr>
            </table>
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
                maxDate: 0
            });
        });
    });
</script>
</body>
</html>