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
    <g:set var="entityName" value="${message(code: 'bank.label', default: 'Bank')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>View Bank List</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <table class="university-size-1-1 university-text-align-centre"  style="margin: auto;border: 1px solid beige">
            <tr>
                <th>Marks Type Name</th>
                <th>&nbsp;</th>
            </tr>
            <g:each in="${marksTypeList}" var="marksTypeInstance">
                <tr>
                    <td>${fieldValue(bean: marksTypeInstance, field: "marksTypeName")}</td>
                    <td>
                        <div class="university-text-align-centre">
                            <input type="submit" value="Update" class="university-button"
                                   onclick="updateMarksType(${marksTypeInstance.id})"/>
                            <input type="button" onclick="deleteMarksType(${marksTypeInstance.id})" value="Delete"
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
