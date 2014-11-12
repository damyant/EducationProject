<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 5/2/2014
  Time: 1:18 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>View Fee Types</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">aaaaaaaa</div></div>
        </g:if>
        <table class="university-size-1-1 university-text-align-centre" style="margin: auto;border: 1px solid beige">
            <tr>
                <th>Fee Type Name</th>
                <th>&nbsp;</th>
            </tr>
            <g:each in="${feeTypeList}" var="fee">
                <tr>
                    <td>${fee.type}</td>
                    <td>
                        <div class="university-text-align-centre">
                            <input type="submit" value="Update" class="university-button"
                                   onclick="updateFeeType(${fee.id})"/>
                            <input type="button" onclick="deleteFeeType(${fee.id})" value="Delete"
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