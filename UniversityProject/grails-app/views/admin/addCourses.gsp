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

    <script type="text/javascript">
        $(window).bind("load", function () {

            if(${updateMode==true}){
           fillCourseInfoUpdate("${courseSession?.sessionOfSubject}")
            }
        })

    </script>
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
                          from="${programTypeList}" optionKey="id" value="${courseList?.programTypeId?.id}"
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
            <td class="university-size-2-3"><g:textField name="subjectName" id="subjectName" maxlength="150" value="${courseList?.subjectName}"
                                                         class="university-size-1-2"/>
                <input type="hidden" value="${courseList?.id}" name="subjectId"/>

            </td>
        </tr>
        <tr>
            <td class="university-size-1-3"><p>Course Code <span class="university-obligatory">*</span>
            </p></td>
            <td class="university-size-2-3"><g:textField name="subjectCode" id="subjectCode" maxlength="6"  value="${courseList?.subjectCode}"
                                                         onchange="checkSubjectCode()" class="university-size-1-2"/>
                <label id="errorMsg" class="error1"></label>

            </td>
        </tr>
        <tr>
            <td class="university-size-1-3"><p>Course Session <span class="university-obligatory">*</span>
            </p></td>
            <td class="university-size-2-3">
                <select id="sessionOfCourse" name="session" class="university-size-1-2">
                    <option value="0">Select Session</option>
                    <g:each in="${subjectSessions}" var="session">
                        <option value="${session.sessionOfProgram}">${session.sessionOfProgram}</option>
                    </g:each>
                </select>
            </td>
        </tr>
        <tr>
            <td class="university-size-1-3"><p>Alias Code <span class="university-obligatory">*</span>
            </p></td>
            <td class="university-size-2-3"><g:textField name="aliasCode" id="aliasCode" maxlength="6" value="${courseList?.aliasCode}"
                                                         onchange="checkAliasCode()" class="university-size-1-2"/>
                <label id="errorMsg1" class="error1"></label>

            </td>
        </tr>
        <tr>
            <td class="university-size-1-3"><p>Credit Points <span class="university-obligatory">*</span>
            </p></td>
            <td class="university-size-2-3"><g:textField name="creditPoints" id="creditPoints" onkeypress="return isNumber(event)" maxlength="4"  value="${courseList?.creditPoints}"
                                                         class="university-size-1-2"/>

            </td>
        </tr>

        <g:each in="${marksTypeList}" var="marksType" status="i">
            <g:set var="counter" value="${i+1}" />

           <tr>
               <td>${marksType.marksTypeName}</td></tr>
            <tr><td>Min Passing Marks</td><td><g:textField name="minPassingMarks" maxlength="3" onkeypress="return isNumber(event)" id="${marksType.marksTypeName}"
                                                           value="${marksMap.get('key'+ counter)?.minPassingMarks}"
                                                           /></td>
               <td>Total Marks</td><td class="university-size-2-3"><g:textField name="totalMarks" maxlength="3" onkeypress="return isNumber(event)" id="${marksType.marksTypeName}"
                                                            value="${marksMap.get('key'+ counter)?.marks}"
                                                            />
               </td>

           </tr>


            %{--<g:javascript>--}%
                     %{--feeTypeList.push(${fee?.id})--}%

            %{--</g:javascript>--}%

        </g:each>


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