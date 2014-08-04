<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 6/18/14
  Time: 4:23 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
    <script type="text/javascript"
            src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
    <script>

    </script>
</head>

<body>
<div id="main">
    <fieldset class="form">

        <g:if test="${flash.message}">
            <div class="message" role="status"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="feeDetails" action="editChallanData" id="editChallanData" name="editChallanData">
        <table class="university-size-2-3 inner">
            <tr>
                <td>Enter Challan Number</td> <td><input type="text" maxlength="10" id="challanNoText" name="challanNoText"
                                                         class="university-size-1-1"></td><td><input type="button"
                                                                                                     value="Show"
                                                                                                     class="university-button"
                                                                                                     onclick="showChallanNumberStatus()">
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <td><input type="button" value="Edit"  class="university-button" onclick="editChallanDetails()"> </td>
                </sec:ifAnyGranted>
            </td>
            </tr>
        </table>

        <div id="errorMsg" class="university-status-message"></div>
        <table id="challanStatus" class="inner university-size-full-1-1">
            <thead></thead>
            <tbody></tbody>
        </table>
        <br/>
        <table class="university-size-3-4 CSSTableGenerator" id="challanStatusStaticData" style="margin: auto;">

        </table>

            <table class="university-size-3-4 inner" id="challanDataEdit" style="margin: auto;">

            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>