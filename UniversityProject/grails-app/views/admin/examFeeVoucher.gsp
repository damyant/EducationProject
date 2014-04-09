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
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Exam Fee Challan</h3>
        <g:if test="${params.rollNo == 'generated'}">
            <div class="message"><div class="university-status-message"><g:message
                    code="rollNo.Generated.message"/></div></div>
        </g:if>
        <g:form id="generateExamFeeVoucher" name="generateFeeVoucher" controller="admin" action="generateFeeVoucher">
        %{--<g:hiddenField name="studentId" id="studentId"/>--}%
        %{--<g:hiddenField name="pageType" id="pageType" value="Assign RollNo"/>--}%
            <label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label>
            <table class="inner" style="margin: auto;text-align: center; width: 70%">
                <tr>
                    <td class="university-size-1-3"><p>Enter Roll Number :<span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-2-3"><g:textField name="rollNo" id="rollNo"
                                                                 class="university-size-1-2" required="true"
                                                                 onkeypress="return isNumber(event)"/></td>
                </tr>

                <tr>
                    <td>
                        <p>
                            <label for="programDetail">
                                <g:message code="programFee.programDetail.label" default="Program Name"/> :<span
                                    class="university-obligatory">*</span>
                            </label>
                        </p>
                    </td>
                    <td>
                        <g:select id="programDetail" name="programDetail"
                                  from="${feeType}" optionKey="id"
                                  optionValue="type" class="many-to-one university-size-1-2"
                                  noSelection="['': 'Choose Type']" required="required"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p>
                            <label for="programDetail">
                                <g:message code="programFee.term.label" default="Program Name"/> :<span
                                    class="university-obligatory">*</span>
                            </label>

                        <p>
                    </td>
                    <td>
                        <g:select id="programDetail" name="programDetail"
                                  from="${feeType}" optionKey="id"
                                  optionValue="type" class="many-to-one university-size-1-2"
                                  noSelection="['': 'Choose Term']" required="required"/>
                    </td>

                </tr>

                <tr><td colspan="2" style="text-align: center; "><g:submitButton name="submit" class="university-button"
                                                                                 value="Submit" onclick="validate()"
                                                                                 style="margin-top: 15px;"></g:submitButton></td>
                </tr>
            </table>



            <table id="studentList" class="inner university-table-1-3">
                <thead></thead>
                <tbody></tbody>
            </table>
        </g:form>

    </fieldset>
</div>
</body>
</html>