<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 6/11/14
  Time: 12:41 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
    <script>

    </script>
</head>

<body>
<div id="main">
    <div id="errorMsgForRollNo"></div>
    <g:form controller="feeDetails" action="savePostExamFee" name="postExamFee" id="postExamFee">
    <table>

        <tr>
            <td><label> Enter RollNumber</label></td>
            <td><input type="text" name="rollNumberInput" id="rollNumberInput" onchange="searchByRollNumber()" ></td>
        </tr>
        <tr>
            <td><label>Course</label></td>
            <td><input type="text" name="course" id="course" readonly></td>
        </tr>
        <tr>
            <td><label>Semester/ Term</label></td>
            <td><g:select name="semester" id="semester" from=""  class="many-to-one university-size-1-2"  disabled="true" onchange="clearSelectBox()" ></g:select></td>
        </tr>
        <tr>
            <td><label>Fee Type</label></td>
            <td><g:select name="postFeeType" id="postFeeType" from=""  class="many-to-one university-size-1-2" disabled="true" onchange="checkPreviousRecord()"></g:select></td>
        </tr>
        <tr>
            <td><input type="submit" value="Generate" id="savePostFee" disabled> </td>
            %{--<td><input type="text"></td>--}%
        </tr>
    </table>
    </g:form>
</div>

<div id="challanDiv" class="dialog" style="width: 320px;margin:auto;">
    <input type="button" id="print" value="Print" onclick="printFeeChallan('#feeChallanDiv')" style="text-align: center;">
    <div id="feeChallanDiv" style="font-family: Times New Roman, Times, serif;border:0px;font-style: normal;height: 300px;margin: auto;font-weight: bold;">
        %{--<div style="width: 100%; margin: 6px auto; display: inline-block;text-align: center;">----------------------------------------BANK COPY----------------------------------------</div>--}%

        <div style="border: 0px solid;">
            <div style="width:80%;margin:15px auto;font-size: 11px;font-weight: bold;letter-spacing:2px;">
                <div style="float: right;">
                    <lable>Challan. No.</lable>
                    <label id="challanNo" style=""></label>
                </div>
            </div>
            <div class="university-clear-both"></div>
            <div style="text-align: center;text-transform:capitalize;font-size: 9px;letter-spacing:2px;">
                <div>&nbsp;</div><div>&nbsp;</div>
                <div style="text-transform:capitalize;font-size: 9px;font-weight: bold;">State Bank of India</div>
                <div style="text-transform:capitalize;font-size: 9px;font-weight: bold;">Gauhati University Branch (CODE-<g:message code="default.Bank.code"/>)</div>
                <div style="text-transform:capitalize;font-size: 11px;font-weight: bolder;margin-top: 1px;margin-bottom: 1px;"><label style="border: 1px solid;padding-left: 5px;padding-right: 5px;">A/C No. <g:message code="default.Bank.AcNo"/></label></div>
                <div style="text-transform:capitalize;font-size: 9px;font-weight: bold;">Institute of Distance and Open Learning</div>
                <div style="text-transform:capitalize;font-size: 9px;font-weight: bold;">Gauhati University</div>
            </div>

            <div style="clear: both; margin-bottom: 10px;"></div>
            <table  style="width:80%;margin:auto;text-transform:capitalize;border: solid 0px black;letter-spacing:1px;font-family: Times New Roman, Times, serif;font-style: normal;font-weight: bold;">
                <tr><td style="width: 40%;font-size: 9px;padding-left: 5px;"><lable>Name:</lable></td><td style="width: 60%;font-size: 10px;"><label id="studentName"></label></td></tr>
                <tr><td  style="width: 40%;font-size: 9px;padding-left: 5px;"><lable>Roll No:</lable></td><td style="width: 60%;font-size: 10px;font-weight: bolder;letter-spacing:2px;"><label id="studentRollNo"></label></td></tr>
                <tr><td  style="width: 40%;font-size: 9px;padding-left: 5px;">Type Of Fee:</td><td style="width: 60%;font-size: 10px;"><label id="feeType"></label></td></tr>
                <tr><td  style="width: 40%;font-size: 9px;padding-left: 5px;">Term/Semester:</td><td style="width: 60%;font-size: 10px;"><label id="term"></label></td></tr>
                <tr><td  style="width: 40%;font-size: 9px;padding-left: 5px;"><lable>Amount:</lable></td><td style="width: 60%;font-size: 11px;font-weight: bolder;letter-spacing:2px;"><label id="amount"></label>
                    <label style="display: block" id="lateFee"></label>
                </td></tr>
                <tr><td  style="height:60px">&nbsp;</td><td  style="height:60px">&nbsp;</td></tr>
                <tr><td style="vertical-align: bottom;width: 40%;font-size: 9px;padding-left: 5px;">${new Date()}</td>
                    <td style="vertical-align: bottom;">
                        <div style="width:95%;margin:auto;text-align: right; bottom: 2px;font-size: 9px;"><label style="float:right;">Cashier's Signature</label></div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
 </div>
<script type="text/javascript">
    var amount = "${amount}"
    if(amount){
//        alert('if')
    var name ="${misFeeObject?.student?.firstName} ${misFeeObject?.student?.middleName} ${misFeeObject?.student?.lastName}"
//        alert(''+name)
    var rollNo ="${misFeeObject?.student?.rollNo}"
    var feeType =" ${misFeeObject?.feeType?.type} For ${program?program:''}"
    var term ="${misFeeObject?.semesterValue}"
    var challanNo ="${misFeeObject?.challanNo}"
    }
//    alert('this is the amount '+ amount)
    $(function() {
        $(".dialog").dialog({
            autoOpen: false,
            draggable: false,
            position: ['center', 0],
            width: 750,
            resizable: false,
            height: 550,
            modal: true,
            title: 'Fee Voucher',
            close: function (ev, ui) {
                $.unblockUI();
                window.open("/UniversityProject/feeDetails/postAdmissionFeeAtIdol",'_self',false)
            }
        });
        if(amount){
//            alert('amount')
        document.getElementById("postExamFee").reset();
        $('#studentName').text(''+name)
        $('#studentRollNo').text(''+rollNo)
        $('#challanNo').text(''+challanNo)
        $('#feeType').text(" "+feeType)
        $('#amount').text(''+amount)
        $('#term').text(''+term)
        $('#amount').text(''+amount)
        $('#challanDiv').dialog('open')
        }
    });


</script>
</body>
</html>