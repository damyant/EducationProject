<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/10/14
  Time: 1:46 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="examinationproject.City; examinationproject.District" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>View Study Center</title>
    <g:javascript src='studyCenter.js'/>
    <g:if test="${params.city}">
        <script type="text/javascript">
            $(document).ready(function () {
                showCityList()
                alert(${City?.findById(params?.city)?.id})
                $('#city').val(${City?.findById(params?.city)?.id})
            })
        </script>
    </g:if>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>--}%
</head>

<body>
<div id="main">
    <fieldset class="form">
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <div class="university-location-select">
            <div class="university-label-location-select">Select District:</div>
            <g:select name="districtList" id="district" optionKey="id" optionValue="districtName"
                      noSelection="['null': ' Select District']" value="${District?.findById(params?.district)?.id}" from="${districtList}" onchange="showCityList()"/>
        </div>

        <div id="cityList" class="university-location-select">
            <div class="university-label-location-select">Select City:</div>
        <g:if test="${params.city}">

            <g:select name="cityLocationList" id="city" optionKey="id" value="" optionValue="cityName"
                      noSelection="['null': ' Select City']" from="" onchange="showStudyCenterList()"/>
        </g:if>
        <g:else>
            <g:select name="cityLocationList" id="city" optionKey="id" value="${City?.findById(params?.city)?.id}" optionValue="cityName"
                      noSelection="['null': ' Select City']" from="" onchange="showStudyCenterList()"/>
        </g:else>
            <input type="hidden" value="${params.type}" id="ParameterType"/>
        </div>
        <table id="studyCenterTab" class="university-table-1-4">
            <thead>
            </thead>
            <tbody>
            </tbody>
        </table>
<g:if test="${params.city}">
    <script type="text/javascript">
        $(document).ready(function () {
            showStudyCenterList()
        })
    </script>
</g:if>
        <div id="msgDiv"></div>
    </fieldset>
</div>
</body>
</html>








