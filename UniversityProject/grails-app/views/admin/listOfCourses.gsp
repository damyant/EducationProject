<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 21/5/14
  Time: 4:47 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title></title>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'admin.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>List of Courses</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}<label  id="statusMessage"></label></div></div>
        </g:if>
        <table class="inner" style="margin: auto; width: 100%">
            <tr>
                <td>
                    <p>
                        <label for="programTypeId">
                            <g:message code="addCourses.selectCategory.label" default="Select Course Category"/> <span
                                class="university-obligatory">*</span>
                        </label>
                    </p>
                </td>
                <td>
                    <g:select id="programTypeId" name="programTypeId"
                              from="${programTypeList}" optionKey="id" onchange="loadProgramList(this)"
                              optionValue="type" class="many-to-one university-size-1-2"
                              noSelection="['': 'Choose Type']"/>
                </td>
            </tr>
            </table>
        <div class="university-status-message" id="errorMsg"></div>
            <table style="visibility: hidden" class="inner university-size-full-1-1" id="courseTable">
                <thead>
                <tr>
                    <th>Serial No</th><th>Course Name</th><th>Session Of Course</th><th></th>
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
        <g:form controller="admin" action="deleteCourse" id="deleteCityInst" name="deleteCityInst">
            <input type="hidden" value="" id="deleteCityId" name="courseId">
        </g:form>

    </fieldset>
</div>
</body>
</html>