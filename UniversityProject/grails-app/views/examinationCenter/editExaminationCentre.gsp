<%--
  Created by IntelliJ IDEA.
  User: Damyant
  Date: 2/7/14
  Time: 10:37 AM
--%>


<%@ page import="examinationproject.ExaminationVenue;javax.validation.constraints.Null; examinationproject.City; examinationproject.District" contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Examination Venue</title>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'jquery/jquery.min.js')}'></script>
    <g:javascript src='studyCenter.js'/>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <h3>Update Examination Venue</h3>
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <g:hasErrors bean="${examinationVenueInstance}">
            <div class="errors">
                <g:renderErrors bean="${examinationVenueInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <g:form controller="examinationCenter" action="updateCentre" method="post">
            <g:hiddenField name="id" value="${examinationVenueInstance?.id}"/>
            <label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label>
            <table class="inner">
                <tr>
                    <td class="university-size-1-3"><label><g:message code="default.createExam.nameOfCenter"/><span
                            class="university-obligatory">*</span></label></td>
                    <td class="university-size-2-3"><input type="text" name="centreName" maxlength="70" value="${examinationVenueInstance?.name}"
                               class="university-size-1-3"/></td>
                </tr>

                <tr>
                    <td><label><g:message code="default.createExam.contactNo"/> <span
                            class="university-obligatory">*</span>
                    </label></td>
                    <td><input type="text" class="university-size-1-3" name="contactNo" maxlength="10"
                               value="${examinationVenueInstance?.contactNo}" onkeypress="return isNumber(event)"/></td>
                </tr>
                <tr>
                    <td><label><g:message code="default.createExam.address"/><span
                            class="university-obligatory">*</span>
                    </label></td>
                    <td><input type="text" class="university-size-1-3" name="address" maxlength="100"
                               value="${examinationVenueInstance?.address}"/></td>
                </tr>


                %{--<tr>--}%
                %{--<td><label><g:message code="default.createExam.nOfRooms" /></label></td>--}%
                %{--<td><input type="text" name="rooms" value="${examinationCentreInstance?.rooms}"/></td>--}%
                %{--</tr>--}%


                <tr>
                    <td><label><g:message code="default.createExam.studentCapacity"/><span
                            class="university-obligatory">*</span></label></td>
                    <td><input type="text" class="university-size-1-3" maxlength="7" name="capacity"
                               value="${examinationVenueInstance?.capacity}" onkeypress="return isNumber(event)"/></td>
                </tr>

                <tr>
                    <td></td>
                    <td><input type="submit" class="university-button"
                               value="<g:message code="default.button.update"/>">
                    </td>
                </tr>

            </table>

        </g:form>
    </fieldset>
</div>
</body>
</html>