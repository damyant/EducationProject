<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 8/1/2014
  Time: 6:06 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'dataEntry.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
</head>

<body>
<div id="main">

    <fieldset class="form">

        <g:if test="${flash.message}">
            <div class="message" role="status"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
            <table class="inner university-size-full-1-1" style="margin: auto">
                <thead>
                <tr>
                    <td class="university-size-1-4">Enter Date</td>
                    <td class="university-size-1-4"><input type="text" class="university-size-3-4" name="customChallanDate" id="customChallanDate"
                                                           value=""/></td>
                    <td class="university-size-1-4"><input type="button" value="Show Custom Challan" class="university-button"
                                                           onclick="showCustomChallanByDate()"/></td>
                    <td class="university-size-1-4"></td>
                </tr>
                </thead>
            </table>
            <div class="university-status-message"><div id="errorMessage"></div></div>
            <table class="inner university-size-full-1-1" id="showCustomChallanList" style="margin: auto">
                <thead></thead>
                <tbody></tbody>
            </table>
            <div style="text-align: center;visibility: hidden;" id="paginationDiv" class="university-size-full-1-1">
                <br/>

                <div class="pagination">
                    <a href="#" class="first" data-action="first">&laquo;</a>
                    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                    <input type="text" readonly="readonly"/>
                    <a href="#" class="next" data-action="next">&rsaquo;</a>
                    <a href="#" class="last" data-action="last">&raquo;</a>
                </div>
            </div>
            <div class="university-status-message"><div id="msgDiv"></div></div>
    </fieldset>
</div>
<script>
    $(function () {
        $(function () {
            $("#customChallanDate").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: "dd/mm/yy",
                maxDate: 0
            });
        });
    });
</script>
</body>
</html>