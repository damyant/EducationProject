<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 19/5/14
  Time: 6:08 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add Courses</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Add Courses</h3>
<g:form controller="course" action="saveCourses" id="addCoursesFrmId" name="addCoursesFrmId" >
    <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
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
                          from="${programTypeList}" optionKey="id"
                          optionValue="type" class="many-to-one university-size-1-2"
                          noSelection="['': 'Choose Type']"/>
            </td>
        </tr>

        %{--<tr>--}%
            %{--<td class="university-size-1-3"><p>Course Code :<span class="university-obligatory">*</span>--}%
            %{--</p></td>--}%
            %{--<td class="university-size-2-3"><g:textField name="subjectCode" id="subjectCode"--}%
                                                         %{--class="university-size-1-2" required="true"--}%
                                                         %{--onkeypress="return isNumber(event)"--}%
            %{--/></td>--}%
        %{--</tr>--}%

        <tr>
            <td class="university-size-1-3"><p>Course Name <span class="university-obligatory">*</span>
            </p></td>
            <td class="university-size-2-3"><g:textField name="subjectName" id="subjectName"
                                                         class="university-size-1-2"
                                                         /></td>
        </tr>

        <tr><td colspan="2" style="text-align: center; ">
            <g:submitButton name="submit" class="university-button" value="Save" onclick="validate()"
                                                                         style="margin-top: 15px;"></g:submitButton></td>
        </tr>

    </table>

    </g:form>
    </fieldset>
    </div>
    </body>
    </html>