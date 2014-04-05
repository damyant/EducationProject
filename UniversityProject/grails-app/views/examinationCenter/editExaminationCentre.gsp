<%--
  Created by IntelliJ IDEA.
  User: Damyant
  Date: 2/7/14
  Time: 10:37 AM
--%>

<%@ page import="javax.validation.constraints.Null; examinationproject.City; examinationproject.District" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Examination Center</title>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'jquery/jquery.min.js')}'></script>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
    </g:if>
    <g:hasErrors bean="${examinationCentreInstance}">
        <div class="errors">
            <g:renderErrors bean="${examinationCentreInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form controller="examinationCenter" action="updateCentre" method="post">
        <g:hiddenField name="id" value="${examinationCentreInstance?.id}"/>
        <label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label>
        <table class="inner">

            <tr>
                <td><label><g:message code="default.createStudy.district"/> <span class="university-obligatory">*</span>
                </label></td>
                <td>
                    <g:select name="district" id="district" optionKey="id"
                              value="${examinationCentreInstance?.city?.district?.id}" class="university-size-1-3"
                              onchange="showCityList()" optionValue="districtName" from="${District.findAll()}"
                              noSelection="['': ' Select District']"/>
                </td>
            </tr>
            <tr>
                <td><label><g:message code="default.createStudy.city"/><span class="university-obligatory">*</span>
                </label></td>
                <td>
                    <g:select name="city" id="city" optionKey="id" value="${examinationCentreInstance?.city?.id}"
                              class="university-size-1-3" optionValue="cityName"
                              from="${City.findAllByDistrict(District.get(examinationCentreInstance?.city?.district?.id))}"
                              noSelection="['': ' Select City']"/>
                </td>
            </tr>

            <tr>
                <td><label><g:message code="default.createExam.nameOfCenter"/><span
                        class="university-obligatory">*</span></label></td>
                <td><input type="text" name="centreName" value="${examinationCentreInstance?.name}"
                           class="university-size-1-3"/></td>
            </tr>

            <tr>
                <td><label><g:message code="default.createExam.contactNo"/> <span class="university-obligatory">*</span>
                </label></td>
                <td><input type="text" class="university-size-1-3" name="contactNo" maxlength=""
                           value="${examinationCentreInstance?.contactNo}"/></td>
            </tr>
            <tr>
                <td><label><g:message code="default.createExam.address"/><span class="university-obligatory">*</span>
                </label></td>
                <td><input type="text" class="university-size-1-3" name="address" maxlength=""
                           value="${examinationCentreInstance?.address}"/></td>
            </tr>


            %{--<tr>--}%
            %{--<td><label><g:message code="default.createExam.nOfRooms" /></label></td>--}%
            %{--<td><input type="text" name="rooms" value="${examinationCentreInstance?.rooms}"/></td>--}%
            %{--</tr>--}%


            <tr>
            <td><label><g:message code="default.createExam.studentCapacity"/><span
                    class="university-obligatory">*</span></label></td>
            <td><input type="text" class="university-size-1-3" name="capacity"
                           value="${examinationCentreInstance?.capacity}"/></td>
            </tr>

            <tr>
                <td></td>
                <td><input type="submit" class="university-button" value="<g:message code="default.button.update"/>">
                </td>
            </tr>

        </table>

    </g:form>
    </fieldset>
</div>
</body>
</html>