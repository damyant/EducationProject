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
</head>

<body>
<div id="main">
    <g:if test="${params.rollNo == 'generated'}">
        <div class="message"><div class="university-status-message"><g:message code="rollNo.Generated.message"/></div>
        </div>
    </g:if>
    <g:form id="generateFeeVoucher" name="generateFeeVoucher" controller="admin" action="generateFeeVoucher">
    %{--<g:hiddenField name="studentId" id="studentId"/>--}%
    %{--<g:hiddenField name="pageType" id="pageType" value="Assign RollNo"/>--}%
        <table class="inner" style="margin: auto;">
            <tr>
                <td>Enter Roll No.:</td><td><g:textField name="rollNo" id="rollNo"
                                                         onkeypress="return isNumber(event)"/></td>
            </tr>

            <tr><td>
                <div class="university-size-1-3"><label for="programDetail">
                    <g:message code="programFee.programDetail.label" default="Program Name"/><span
                            class="required-indicator">*</span>
                </label>
                </div>
            </td>
                <td>

                    <g:if test="${params.error == "error"}">
                        Roll Number does Not belongs to IDOL

                    </g:if>
                    <div class="university-size-2-3">
                        <g:select id="type" name="feeType"
                                  from="${feeType}" optionKey="id"
                                  optionValue="type" class="many-to-one university-size-1-2"
                                  noSelection="['': 'Choose Type']"/>
                    </div>
                </td>

            </tr>

            <tr><td><g:submitButton name="submit" value="Submit"></g:submitButton></td></tr>
        </table>



        <table id="studentList" class="inner university-table-1-3">
            <thead></thead>
            <tbody></tbody>
        </table>
    </g:form>
    <div id="msg"></div>
</div>
</body>
</html>