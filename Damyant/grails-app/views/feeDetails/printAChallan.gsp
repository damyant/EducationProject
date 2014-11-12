<%--
  Created by IntelliJ IDEA.
  User: IDOL_2
  Date: 9/11/14
  Time: 10:26 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
</head>

<body>
<div id="main">

    <fieldset class="form">
        <h3></h3>

        <g:if test="${flash.message}">
            <div class="message" role="status"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:form controller="feeDetails" action="printTheChallan" name="printTheChallan" id="printTheChallan">
            <table class="inner university-size-full-1-1" style="margin: auto">
                <tbody>
                <tr>
                    <td class="university-size-1-4">Enter Challan Number</td>
                    <td class="university-size-1-4"><input type="text" name="challanNo" id="challanNo"
                                                           value=""/></td>
                    <td class="university-size-1-4"><input type="submit" value="Print"
                                                           class="university-size-1-3 university-button"/></td>
                    <td class="university-size-1-4"></td>
                </tr>
                </tbody>
            </table>
        </g:form>
    </fieldset>
</div>
</body>
</html>