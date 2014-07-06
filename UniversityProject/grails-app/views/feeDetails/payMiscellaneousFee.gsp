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
<sec:ifAnyGranted roles="ROLE_STUDY_CENTRE">
    <script type="text/javascript">
        $(document).ready(function(){
//            $('#paymentMode option [value="1"]').hide()
            $(document.getElementById('paymentMode').options).each(function(index, option) {
                if( option.value == "1" ) {
                    option.hidden = true; // not fully compatible. option.style.display = 'none'; would be an alternative or $(option).hide();
                }
            });
        })
    </script>
</sec:ifAnyGranted>
<div id="main">
    <fieldset class="form">
        <h3>Pay Miscellaneous Fee Entry</h3>
        <g:form controller="feeDetails" action="payMiscFeeChallan" name="paychallanForStudyCenter" id="paychallanForStudyCenter">
            <table class="inner university-size-full-1-1" style="margin: auto">
                <thead>
                <tr>
                    <td class="university-size-1-4">Enter Challan Number</td>
                    <td class="university-size-1-4"><input type="text" name="searchChallanNo" id="searchChallanNo" value=""/></td>
                    <td class="university-size-1-4"><input type="button" value="Show Students" onclick="showMiscFeeListOfStudents()"/></td>
                    <td class="university-size-1-4"></td>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
            <table class="inner university-size-full-1-1" id="scStudnetList" style="margin: auto">
                <thead>
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
            <table id="studentPayList" class="university-size-full-1-1" style="visibility: hidden">
                <tr>
                    <td class="university-size-1-3">Payment Mode</td>
                    <td class="university-size-2-3"><g:select name="paymentMode" class="university-size-1-2" id="paymentMode" optionKey="id" optionValue="paymentModeName" from="${paymentMode}" noSelection="['': ' Select Payment Mode']" onchange="loadPayInSlipDetails(this)"/></td>
                </tr>
                <tr>
                    <td class="university-size-1-3">Payment Date</td>
                    <td class="university-size-2-3"><input type="text" name="paymentDate" maxlength="10" class="university-size-1-2" id="datepicker" value=""></td>
                </tr>
                <tr>
                    <td class="university-size-1-3">Payment Ref. No</td>
                    <td class="university-size-2-3"><input type="text" name="paymentReferenceNumber" maxlength="10" onkeypress="return isNumber(event)" class="university-size-1-2" id="paymentReferenceNumber" value=""></td>
                </tr>
                <tr>
                    <td>Bank</td>
                    <td><g:select name="bankName" class="university-size-1-2" id="bankName" optionKey="id" optionValue="bankName" from="${bankName}" noSelection="['': ' Select Bank']" onchange="loadBranch(this)"/></td>
                </tr>
                <tr>
                    <td>Bank</td>
                    <td><g:select name="branchLocation" class="university-size-1-2" id="branchLocation" optionKey="" optionValue="" from="" noSelection="['': ' Select Branch']" onchange=""/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" id="paySubmit" class="ui-button university-size-1-3" value="Pay"  onclick="checkValidation()" style="visibility: hidden"/></td>
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
    $('#paySubmit').click(function(){
//        alert("--------")
        if($('#paychallanForStudyCenter').valid()) {
            setTimeout(function () {
//            alert("fddfdfd")
                $('#paychallanForStudyCenter')[0].reset();
                document.getElementById("scStudnetList").style.visibility = "hidden";//
                document.getElementById("paySubmit").style.visibility = "hidden";
                document.getElementById("paginationDiv").style.visibility = "hidden";
                document.getElementById("studentPayList").style.visibility = "hidden";
                document.getElementById("payClear").style.visibility = "hidden";
            }, 5000)
        }
    })
</script>
</body>
</html>