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
</head>

<body>
<div id="main">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
<g:form controller="examinationCenter" action="saveNewCentre" method="post">

    <table>

        <tr>
            <td><label><g:message code="default.createExam.location"/></label></td>
            <td><select name="location" >
                <option value="0">select loaction</option>
                <option value="Noida">Noida</option>
                <option value="Guahati">Guahati</option>
                <option value="Golaghat">Golaghat</option>
                <option value="Jaipur">Jaipur</option>
            </select ></td>
        </tr>

        <tr>
            <td><label><g:message code="default.createExam.nameOfCenter"/></label></td>
            <td><input type="text" name="centreName"/></td>
        </tr>

        <tr>
            <td><label><g:message code="default.createExam.contactNo"/></label></td>
            <td><input type="text" name="contactNo" maxlength=""  onkeypress="return isNumber(event)"/></td>
        </tr>
        <tr>
            <td><label><g:message code="default.createExam.address"/></label></td>
            <td><input type="text" name="address" maxlength=""/></td>
        </tr>


        <tr>
            <td><label><g:message code="default.createExam.nOfRooms"/></label></td>
            <td><input type="text" name="rooms" onkeypress="return isNumber(event)"/></td>
        </tr>


        <tr>
            <td><label><g:message code="default.createExam.studentCapacity"/></label></td>
            <td><input type="text" name="capacity" onkeypress="return isNumber(event)"/></td>
        </tr>

        <tr>
            <td><input type="submit" value="<g:message code="default.button.create"/>"></td>
            <td><input type="button" value="<g:message code="default.button.clear"/>"></td>
        </tr>

        %{--<tr>--}%
            %{--<td><a href="#" class="a_demo_one">--}%
                %{--Click me!--}%
            %{--</a></td>--}%
        %{--</tr>--}%

    </table>

</g:form>
    </div>
<script>
    function isNumber(evt) {

        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        }
        return true;
    }
</script>
</body>
</html>