<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 6/20/14
  Time: 3:09 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='registerPage.js'/>
    <title></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Custom Challan</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <form  id="customChallanSave" name="customChallanSave">
            <div style="margin-left: 10px;"><label><h6>All fields are Mandatory.</h6></label></div>
            <table class="inner university-size-full-1-1">
                <tr>
                    <td class="university-size-1-4">Name</td>
                    <td class="university-size-3-4"><input type="text" class="university-size-1-2" maxlength="60" name="challanName" id="challanName"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Type of Payment</td>
                    <td class="university-size-3-4"><input type="text" class="university-size-1-2" maxlength="60" name="typeOfFee" id="typeOfFee"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4">Amount</td>
                    <td class="university-size-3-4"><input type="text" class="university-size-1-2" onkeypress="return isNumber(event)" maxlength="15" name="amount" id="amount"></td>
                </tr>
                <tr>
                    <td class="university-size-1-4"></td>
                    <td class="university-size-3-4"><input type="button" class="university-size-1-4 university-button" onclick="saveCustomChallan()" value="Submit"/>&nbsp;<input type="reset" class="university-size-1-4 university-button" value="Reset"/></td>
                </tr>
            </table>
        </form>
    </fieldset>
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
                    <tr><td style="width: 40%;font-size: 9px;padding-left: 5px;"><lable>Name:</lable></td><td style="width: 60%;font-size: 10px;"><label id="cName"></label></td></tr>
                    <tr><td  style="width: 40%;font-size: 9px;padding-left: 5px;">Type Of Fee:</td><td style="width: 60%;font-size: 10px;"><label id="feeType"></label></td></tr>
                    <tr><td  style="width: 40%;font-size: 9px;padding-left: 5px;"><lable>Amount:</lable></td><td style="width: 60%;font-size: 11px;font-weight: bolder;letter-spacing:2px;">Rs <label id="feeAmount"></label></td></tr>
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
</div>
<script type="text/javascript">
    $(function () {

        $(".dialog").dialog({
            autoOpen: false,
            draggable: false,
            position: ['center', 0],
            width: 750,
            resizable: false,
            height: 550,
            modal: true,
            title: 'Custom Challan',
            close: function (ev, ui) {
                $.unblockUI();
            }

        });
    });

</script>

</body>
</html>