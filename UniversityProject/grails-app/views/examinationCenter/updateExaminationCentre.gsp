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
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'jquery/jquery.min.js')}'></script>

</head>

<body>
<div id="main">
    <div>
        <div class="university-location-select">
            <div class="university-label-location-select">
                <label><g:message code="default.createStudy.district"/></label>
            </div>
            <g:select name="district" id="district" optionKey="id" value="${studyCentreInstance?.city?.district?.id}"
                      class="university-size-1-3" onchange="showCityList()" optionValue="districtName"
                      from="${District.findAll()}" noSelection="['': ' Select District']"/>
        </div>

        <div class="university-location-select">
            <div class="university-label-location-select">
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
</div>
<script>
    function showList() {
        var data = $('#city').val();
        jQuery("#centreListTable").css({display: "block"});
        $.ajax({
            type: "post",
            url: '${createLink(controller: 'examinationCenter', action: 'getCentreList')}',

            data: {data: data, edit: 'edit'},
            success: function (response) {   //

                $("div#centreList").html(response)

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
</script>
</body>
</html>