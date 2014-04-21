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
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'jquery/jquery.js')}'></script>
    <g:javascript src='validate.js'/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'examinationCentre.js')}"></script>
    <g:javascript src='studyCenter.js'/>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'registerPage.js')}"></script>--}%
</head>

<body>
<div id="main">
    <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
    </g:if>
    <div id="msg" class="university-status-message"></div>
    <form id="examinationCenterForm" name="examinationCenterForm">

        <div class="university-location-select">
            <div class="university-label-location-select">
                <label><g:message code="default.createStudy.district"/></label></div>
            <g:select name="district" id="district" optionKey="id" value="${studyCentreInstance?.city?.district?.id}"
                      class="university-size-1-3" onchange="showCityList()" optionValue="districtName"
                      from="${districtList}" noSelection="['': ' Select District']"/>
        </div>

        <div id="cityList" class="university-location-select">
            <div class="university-label-location-select">
                <label><g:message code="default.createStudy.city"/></label></div>
            <g:select name="city" id="city" optionKey="id" value="${studyCentreInstance?.city?.id}"
                      class="university-size-1-3" optionValue="cityName"
                      from="${City.findAllByDistrict(District.get(studyCentreInstance?.city?.district?.id))}"
                      noSelection="['': ' Select City']"/>
        </div>

        <div id="VenueDiv" class="middleDiv">
        </div>
        <label><h6>All [<span class="university-obligatory">*</span>] marked fields are Mandatory.</h6></label>
        <table style="border: none">

            <tr>
                <td colspan="4">

                    <input type="button" id="submitButton" value="Submit" onclick="validateAndSubmitForm()" class="${classs} buttonCss">

                    <input type="reset" value="Cancel" onclick="reset1()" class="${classs} buttonCss"/>
                    <input type="hidden" id="totalIndex" value=""/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>