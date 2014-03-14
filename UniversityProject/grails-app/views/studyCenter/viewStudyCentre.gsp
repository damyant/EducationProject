<%--
  Created by IntelliJ IDEA.
  User: shweta
  Date: 2/10/14
  Time: 1:46 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="examinationproject.District" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>View Study Center</title>
    <g:javascript src='studyCenter.js'/>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'studyCenter.js')}"></script>--}%
</head>

<body>
<div id="main">
    <div class="university-location-select">
        <div class="university-label-location-select"> Select The District:</div>
        <g:select name="districtList" id="district" optionKey="id" optionValue="districtName" noSelection="['null':' Select District']" from="${District.findAll()}" onchange="showCityList()"/>
    </div>
    <div id="cityList" class="university-location-select">
        <div class="university-label-location-select">Select the city:</div>
        <g:select name="cityLocationList" id="city" optionKey="id" optionValue="cityName"  noSelection="['null':' Select City']" from="" onchange="showStudyCenterList()" />
        <input type="hidden" value="${params.type}" id="ParameterType"/>
    </div>
    <g:if test="${params.type=='update'}">
        <table id="studyCenterTab" class="university-table-1-4">
    </g:if>
    <g:else>
        <table id="studyCenterTab" class="university-table-1-3">
    </g:else>
          <thead>
          </thead>
          <tbody>
          </tbody>
      </table>
    <div id="msgDiv"></div>
</div>
</body>
</html>








