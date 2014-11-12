<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 5/2/2014
  Time: 1:18 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="examinationproject.Bank" %>
<html>
<head>
    <meta name="layout" content="main">
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'bank.js')}'></script>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>District List</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <table class="university-size-1-1 university-text-align-centre"  style="margin: auto;border: 1px solid beige">
            <tr>
                <th>District Name</th>
                <th>&nbsp;</th>
            </tr>
            <g:each in="${districtInstanceList}" var="districtInstance">
                <tr>
                    <td>${fieldValue(bean: districtInstance, field: "districtName")}</td>
                    <td>
                        <div class="university-text-align-centre">
                            <input type="submit" value="Update" class="university-button"
                                   onclick="updateDistrict(${districtInstance.id})"/>
                            <input type="button" onclick="deleteDistrict(${districtInstance.id})" value="Delete"
                                   class="university-button"/>
                        </div>
                    </td>
                </tr>
            </g:each>
        </table>

    </fieldset>
</div>
</body>
</html>
