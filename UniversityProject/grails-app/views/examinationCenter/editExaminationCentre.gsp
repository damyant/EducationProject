<%--
  Created by IntelliJ IDEA.
  User: Damyant
  Date: 2/7/14
  Time: 10:37 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Examination Center</title>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'jquery.min.js')}'></script>

</head>

<body>
<div id="main">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${examinationCentreInstance}">
        <div class="errors">
            <g:renderErrors bean="${examinationCentreInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form controller="examinationCenter" action="updateCentre" method="post">
        <g:hiddenField name="id" value="${examinationCentreInstance?.id}"/>
        <table>

            <tr>
                <td><label><g:message code="default.createExam.location"/></label></td>
                <td><select name="location">
                    <option value="${examinationCentreInstance.location}">${examinationCentreInstance.location}</option>
                    <option value="Noida">Noida</option>
                    <option value="Guahati">Guahati</option>
                    <option value="Golaghat">Golaghat</option>
                    <option value="Jaipur">Jaipur</option>
                </select ></td>
            </tr>

            <tr>
                <td><label><g:message code="default.createExam.nameOfCenter"/></label></td>
                <td><input type="text" name="centreName" value="${examinationCentreInstance?.name}"/></td>
            </tr>

            <tr>
                <td><label><g:message code="default.createExam.contactNo"/></label></td>
                <td><input type="text" name="contactNo" maxlength="" value="${examinationCentreInstance?.contactNo}"/></td>
            </tr>
            <tr>
                <td><label><g:message code="default.createExam.address"/></label></td>
                <td><input type="text" name="address" maxlength="" value="${examinationCentreInstance?.address}"/></td>
            </tr>


            %{--<tr>--}%
                %{--<td><label><g:message code="default.createExam.nOfRooms" /></label></td>--}%
                %{--<td><input type="text" name="rooms" value="${examinationCentreInstance?.rooms}"/></td>--}%
            %{--</tr>--}%


            <tr>
                <td><label><g:message code="default.createExam.studentCapacity" /></label></td>
                <td><input type="text" name="capacity" value="${examinationCentreInstance?.capacity}"/></td>
            </tr>

            <tr>
                <td><input type="submit" value="<g:message code="default.button.update"/>"></td>
                <td><input type="button" value="<g:message code="default.button.cancel"/>"></td>
            </tr>

        </table>

    </g:form>
</div>
</body>
</html>