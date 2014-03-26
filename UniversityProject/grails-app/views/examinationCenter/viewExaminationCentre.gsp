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
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery/jquery-1.7.1.js')}"></script>
    <g:javascript src='studyCenter.js'/>
    <title>Create Examination Center</title>
</head>

<body>
<div id="main">
     <div>
         <div class="university-location-select">
             <div class="university-label-location-select">
             <label><g:message code="default.createStudy.district"/></label></div>
             <g:select name="district" id="district" optionKey="id" value="${studyCentreInstance?.city?.district?.id}" class="university-size-1-3" onchange="showCityList()" optionValue="districtName" from="${District.findAll()}" noSelection="['':' Select District']" />

         </div>
         <div class="university-location-select">
             <div class="university-label-location-select">
         <label><g:message code="default.createStudy.city"/></label></div>
         <g:select name="city" id="city" optionKey="id" value="${studyCentreInstance?.city?.id}" class="university-size-1-3"  optionValue="cityName" from="${City.findAllByDistrict(District.get(studyCentreInstance?.city?.district?.id))}" onchange="showList()" noSelection="['':' Select City']"/>
        </div>
     </div>

<div id="centreList" style="text-align: center; width: 100%">
</div>
</div>
<script>
    function showList(){
        var data = $('#city').val();
        jQuery("#centreListTable").css({display: "block"});
        $.ajax({
            type:"post",
            url:'${createLink(controller: 'examinationCenter', action: 'getCentreList')}',
            data:{data:data},
//            contentType: "application/json; charset=utf-8",
//            dataType: "json",
            success:function (response) {
                console.log("<><><><><><><><> "+response)
                $("div#centreList").html(response)


            } ,
            error:function(XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }
</script>
</body>
</html>