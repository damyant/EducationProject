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
<script>
    $(document).ajaxStart(function(){
        $.blockUI({ css: {
            border: 'none',
            padding: '15px',
            backgroundColor: '#000',
            '-webkit-border-radius': '10px',
            '-moz-border-radius': '10px',
            opacity: 5,
            color: '#fff'
        } });
    }).ajaxStop($.unblockUI);
</script>
<div id="main">
    <fieldset class="form">
        <div>

            <div class="university-location-select">
                <div class="university-label-location-select">
                    <label><g:message code="default.createStudy.district"/><span class="university-obligatory">*</span></label></div>
                <g:select name="district" id="district" onselect="" optionKey="id" value=""
                          class="university-size-1-3" onchange="showExamCenterList(),clearErrorMsg(this)" optionValue="districtName"
                          from="${districtList}" noSelection="['': ' Select District']"/><label id="districtError" class="error5" >&nbsp;</label>
            </div>

            <div id="cityList" class="university-location-select">
                <div class="university-label-location-select">
                    <label><g:message code="default.createExamVenue.centre"/><span class="university-obligatory">*</span></label></div>
                <g:select name="examinationCentre" id="examinationCentre" optionKey="id" value=""
                          class="university-size-1-3" optionValue="cityName" onchange="showList()"
                          from=""
                          noSelection="['': ' Select Examination Centre']"/><label id="cityError" class="error5" >&nbsp;</label>
            </div>
        </div>

        <div id="centreList" style="text-align: center; width: 100%;margin-top: 10px;">
        </div>
    </fieldset>
</div>
<script>

</script>
</body>
</html>