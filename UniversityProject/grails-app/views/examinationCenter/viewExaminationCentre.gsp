<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/10/14
  Time: 1:46 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="javax.validation.constraints.Null; examinationproject.City; examinationproject.District" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:javascript src='studyCenter.js'/>
    <g:javascript src='examinationCentre.js'/>
    <title>Create Examination Center</title>
</head>

<body>
<div id="main">
    <fieldset class="form">
        <div>
            <div class="university-location-select">
                <div class="university-label-location-select">
                    <label><g:message code="default.createStudy.district"/></label></div>
                <g:select name="district" id="district" optionKey="id"
                          value="${studyCentreInstance?.city?.district?.id}" class="university-size-1-3"
                          onchange="showCityList()" optionValue="districtName" from="${districtList}"
                          noSelection="['': ' Select District']"/>

            </div>

            <div class="university-location-select">
                <div class="university-label-location-select">
                    <label><g:message code="default.createStudy.city"/></label></div>
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
<script>

</script>
</body>
</html>