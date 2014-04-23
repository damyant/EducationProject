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
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='examinationCentre.js'/>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'jquery/jquery.min.js')}'></script>

</head>

<body>
<div id="main">
    <fieldset class="form">
        <div>
            <g:if test="${flash.message}">
                <div class="message"><div class="university-status-message">${flash.message}</div></div>
            </g:if>
        <div class="university-location-select">
            <div class="university-label-location-select">
                <label><g:message code="default.createStudy.district"/></label>
            </div>
            <g:select name="district" id="district" optionKey="id" value="${studyCentreInstance?.city?.district?.id}"
                      class="university-size-1-3" onchange="showCityList()" optionValue="districtName"
                      from="${districtList}" noSelection="['': ' Select District']"/>
        </div>

        <div class="university-location-select">
            <div class="university-label-location-select">
                <g:hiddenField name="edit" id="edit" value=" ${edit}"></g:hiddenField>
                <label><g:message code="default.createStudy.city"/></label>
            </div>
            <g:select name="city" id="city" optionKey="id" value="${studyCentreInstance?.city?.id}"
                      class="university-size-1-3" optionValue="cityName"
                      from="${City.findAllByDistrict(District.get(studyCentreInstance?.city?.district?.id))}"
                      onchange="showList()" noSelection="['': ' Select City']"/>
        </div>

    </div>

    <div id="centreList" style="text-align: center; width: 100%">
    </div>
    </fieldset>
</div>

</body>
</html>