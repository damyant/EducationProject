<%--
  Created by IntelliJ IDEA.
  User: Digvijay
  Date: 21/5/14
  Time: 4:47 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Update Courses</title>
    <meta name="layout" content="main"/>
    <g:javascript src='admin.js'/>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Update Course</h3>
        <g:form id="updateCoursesFrmId" name="" controller="course" action="updateCourses">
            <table class="inner" style="margin: auto;text-align: center; width: 70%">
                <tr>
                    <td>
                        <p>
                            <label for="programTypeId">
                                <g:message code="addCourses.selectCategory.label" default="Select Course Category"/> :<span
                                    class="university-obligatory">*</span>
                            </label>
                        </p>
                    </td>
                    <td>
                        <g:select id="programTypeId" name="programTypeId"
                                  from="${programTypeList}" optionKey="id"
                                  optionValue="type" class="many-to-one university-size-1-2"
                                  noSelection="['': 'Choose Type']" required="required"/>
                    </td>
                </tr>

                <tr>
                    <td class="university-size-1-3"><p>Course Code :<span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-2-3"><g:textField name="subjectCode" id="subjectCode"
                                                                 class="university-size-1-2" required="true"
                                                                 onkeypress="return isNumber(event)"
                    /></td>
                </tr>

                <tr>
                    <td class="university-size-1-3"><p>Course Name :<span class="university-obligatory">*</span>
                    </p></td>
                    <td class="university-size-2-3"><g:textField name="subjectName" id="subjectName"
                                                                 class="university-size-1-2" required="true"
                    /></td>
                </tr>

                <tr><td colspan="2" style="text-align: center; ">
                    <g:submitButton name="submit" class="university-button"
                                    value="Save" onclick="validate()"
                                    style="margin-top: 15px;"></g:submitButton></td>
                </tr>

            </table>

        </g:form>
    </fieldset>
</div>
</body>
</html>