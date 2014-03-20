<%--
  Created by IntelliJ IDEA.
  User: chandan
  Date: 20/3/14
  Time: 4:11 PM
--%>

<%@ page import="examinationproject.District" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>View Course</title>
        %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>--}%
    </head>

    <body>
    <div id="main">
        <g:if test="${params.type == 'update'}">
            <table id="courseTab" class="university-table-1-4">
        </g:if>
        <g:else>
            <table id="courseTab" class="university-table-1-3">
        </g:else>
        <thead>
        <tr>
            <th>1</th>
            <th>2</th>
            <th>3</th>
            <g:if test="${params.type == 'update'}">
                <th>4</th>
            </g:if>
        </tr>
        </thead>
        <tbody>
        <g:if test="${params.type == 'update'}">
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td><div class="university-float-right">
                    <input type="submit" value="Update" class="university-button"/>
                    <input type="button" value="Delete"  class="university-button"/>
                </div></td>
            </tr>
        </g:if>
        <g:else>
            <tr>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </g:else>
        </tbody>
    </table>
    </div>
    </body>
</html>