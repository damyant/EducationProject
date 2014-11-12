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
    <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
    </g:if>
    <fieldset class="form">
        <div>

            <div class="university-location-select">
                <div class="university-label-location-select">
                    <label><g:message code="default.createStudy.district"/><span class="university-obligatory">*</span></label></div>
                <g:select name="district" id="district" onselect="" optionKey="id" value=""
                          class="university-size-1-3" onchange="showExamCenterList(),clearErrorMsg(this)" optionValue="districtName"
                          from="${districtList}" noSelection="['': ' Select District']"/><label id="districtError" class="error5" >&nbsp;</label>
            </div>

        <div class="university-location-select">
            <div class="university-label-location-select">
                <g:hiddenField name="edit" id="edit" value=" ${edit}"></g:hiddenField>
                <label><g:message code="default.createExamVenue.centre"/><span class="university-obligatory">*</span></label>
            </div>
            <g:select name="examinationCentre" id="examinationCentre" optionKey="id" value=""
                      class="university-size-1-3" optionValue="cityName" onchange="showList()"
                      from=""
                      noSelection="['': ' Select Examination Centre']"/><label id="cityError" class="error5" >&nbsp;</label>
        </div>

    </div>

    <div id="centreList" style="text-align: center; width: 100%">
    </div>
    </fieldset>
</div>

</body>
</html>