<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 5/5/2014
  Time: 2:24 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Approve Pay-In-Slip</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <g:form id="approvePayInSlip" name="approvePayInSlip" controller="admin" action="approveFeeForStudents">

        <table class="inner university-size-full-1-1">
            <tr>
                  <td class="university-size-1-4">Pay-In-Slip Challan No</td>
                <td class="university-size-1-4"><input type="text" class="university-size-1-1" name="payInSlipNo" id="payInSlipNo"></td>
                <td class="university-size-1-2"><input type="button" value="Show Students" onclick="populateChallanDetail()"/>  <div id="error" class="error5" hidden="hidden">No Student Found </div></td>
            </tr>

        </table>


          <table id="allStudentList" class="inner">
            <tbody>

            </tbody>


        </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>