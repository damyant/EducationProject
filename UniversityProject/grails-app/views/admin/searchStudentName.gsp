<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 6/11/2014
  Time: 3:51 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <g:javascript src='admin.js'/>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Search Student By Name</h3>
        <br/>
        <table class="inner university-size-full-1-1">
            <tr>
                <td class="university-size-1-7">Search By Name</td>
                <td class="university-size-1-4"><input type="search"  class="university-size-1-1" id="searchStudent" placeholder="Enter First / Middle / Last Name"/></td>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4"></td>
            </tr>
            <tr>
                <td class="university-size-1-7">Session</td>
                <td class="university-size-1-4">
                    <select id="session" name="programSession" class="university-size-1-1">
                        <option value="">Choose Session</option>
                        <g:each in="${sessionList}" var="session">
                            <option value="${session}">${session}-${session+1}</option>
                        </g:each>
                    </select>
                </td>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4"></td>
            </tr>
            <tr>
                <td class="university-size-1-7"></td>
                <td class="university-size-1-4"><input type="button" class="university-button" onclick="searchStudentList()" value="Search"/></td>
                <td class="university-size-1-4"></td>
                <td class="university-size-1-4"></td>
            </tr>
        </table>
        <table class="university-size-full-1-1 inner" id="studentListTable" style="visibility:hidden;">
            <thead>
                    <tr>
                        <th class="university-size-1-4">Name</th><th>Roll Number</th><th>Study Centre</th><th>Programme</th>
                    </tr>
            </thead>
            <tbody>

            </tbody>
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
    </fieldset>
</div>
</body>
</html>