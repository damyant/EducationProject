<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 3/26/14
  Time: 10:24 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Generate Fee Voucher</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript">

    </script>
         %{--${params}--}%
    <script type="text/javascript">
         $(function () {
            $("#rollNo").blur(function () {
                var rollNo = $("#rollNo").val()
                var feeType = 0
                    var url = "${createLink(controller:'admin', action:'checkFeeByRollNo')}"
                    $.getJSON(url, {rollNo: rollNo, feeType:feeType}, function (json) {
//                        alert(json.feeStatus)
                            if (json.feeStatus) {

                            $("#submit").prop('disabled', false);
//                            $("#feeType").prop('disabled', false);
                            $("#feeError").prop('hidden', true);

                        }else{
                                $('#errorMsg').show();
                            }
                    });
//                }
            })
         });
    </script>

</head>

<body>
<div id="main">
    <fieldset>
        <h3>Generate Pay-In-Slip</h3>
        <g:if test="${params.rollNo == 'generated'}">
            <div class="message"><div class="university-status-message"><g:message
                    code="rollNo.Generated.message"/></div>
            </div>
        </g:if>

            <div class="university-status-message" id="errorMsg" hidden="hidden">Roll Number does not belongs to IDOL</div>



        <g:form id="generateFeeVoucher" name="generateFeeVoucher" controller="admin" action="generateFeeVoucher">
        %{--<g:hiddenField name="studentId" id="studentId"/>--}%
        %{--<g:hiddenField name="pageType" id="pageType" value="Assign RollNo"/>--}%
            <div style="margin-left: 10px;"><label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label></div>
            <table class="linner" style="margin: auto;text-align: center; width: 100%">
                <tr>
                    <td class="university-size-1-3">
                        <p>Enter Roll Number:<span class="university-obligatory">*</span></p>
                    </td>
                    <td class="university-size-2-3">
                        <g:if test="${params.rollNo}">
                            <g:textField name="rollNo" id="rollNo" class="university-size-1-3" value="${params.rollNo}"
                                         onkeypress="return isNumber(event)" readonly="readonly"/>
                        </g:if>
                        <g:else>
                             <g:textField name="rollNo" id="rollNo" class="university-size-1-3"
                                     onkeypress="return isNumber(event)"/><label id="feeError" class="error"></label>
                        </g:else>

                    </td>
                </tr>

                <tr><td>
                    <p><label for="feeType">
                        <g:message code="programFee.programDetail.label" default="Program Name"/>:<span
                                class="university-obligatory">*</span>
                    </label></p>
                </td>
                    <td>
                        <g:if test="${params.rollNo}">
                            <g:select id="feeType" name="feeType"
                                      from="${selectFeeType}" optionKey="id"
                                      optionValue="type" class="many-to-one university-size-1-3"
                                      readonly=""/>
                        </g:if>
                        <g:else>
                            <g:select id="feeType" name="feeType"
                                      from="" optionKey="id"
                                      optionValue="type" class="many-to-one university-size-1-3"
                                      readonly="true"/>
                        </g:else>

                    </td>

                </tr>

                <tr><td colspan="2" style="text-align: center; ">
                    <g:if test="${params.rollNo}">
                        <input type="button" name="submit" id="submit"  class="university-button"  value="Submit"   style="margin-top: 15px;"/>
                    </g:if>
                    <g:else>
                        <input type="button" name="submit" id="submit"  class="university-button"  value="Submit"  disabled="disabled" style="margin-top: 15px;"/>
                    </g:else>
                    </td>
                </tr>
            </table>

        </g:form>
    </fieldset>
</div>

</body>
</html>