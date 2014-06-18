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
    <script type="text/javascript" src="${resource(dir: 'js/jquery/timePicker', file: 'jquery.jqpagination.min.js')}"></script>
    <link rel='stylesheet' href="${resource(dir: 'css', file: 'jqpagination.css')}" type='text/css'/>

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
        <g:if test="${flash.message}">
            <div class="message"><div class="university-status-message">${flash.message}</div></div>
        </g:if>
        <div class="university-location-select">
            <div class="university-label-location-select">Select District:</div>
            <g:select name="districtList" id="district" optionKey="id" optionValue="districtName"
                      noSelection="['null': ' Select District']" value="${District?.findById(params?.district)?.id}" from="${districtList}" onchange="showStudyCenterListByDistrict()"/>
        </div>

        %{--<div id="cityList" class="university-location-select">--}%
            %{--<div class="university-label-location-select">Select City:</div>--}%
        %{--<g:if test="${params.city}">--}%

            %{--<g:select name="cityLocationList" id="city" optionKey="id" value="${City?.findById(params?.city)?.id}" optionValue="cityName"--}%
                      %{--noSelection="['null': ' Select City']" disabled="true" from="${cityList}" onchange="showStudyCenterList()"/>--}%
        %{--</g:if>--}%
        %{--<g:else>--}%
            %{--<g:select name="cityLocationList" id="city" optionKey="id" value="${City?.findById(params?.city)?.id}" optionValue="cityName"--}%
                      %{--noSelection="['null': ' Select City']" from="" onchange="showStudyCenterList()"/>--}%
        %{--</g:else>--}%
            <input type="hidden" value="${params.type}" id="ParameterType"/>
        %{--</div>--}%
        <table id="studyCenterTab" class="university-size-full-1-1">
            <thead>
            </thead>
            <tbody>
            </tbody>
        </table>
        <div style="text-align: center;visibility: hidden;" id="paginationDiv" class="university-size-full-1-1">
            <br/>

            <div class="pagination">
                <a href="#" class="first" data-action="first">&laquo;</a>
                <a href="#" class="previous" data-action="previous">&lsaquo;</a>
                <input type="text" readonly="readonly"/>
                <a href="#" class="next" data-action="next">&rsaquo;</a>
                <a href="#" class="last" data-action="last">&raquo;</a>
            </div>
        </div>
<g:if test="${params.district}">
    <script type="text/javascript">
        $(document).ready(function () {
            showStudyCenterListByDistrict()
        })
    </script>
</g:if>
        <div id="msgDiv"></div>
    </fieldset>
</div>
</body>
</html>








