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
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'create.css')}" type="text/css">
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery/validation-en.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'jquery/validation-engine.js')}"></script>
    <link rel="stylesheet" href="${resource(dir:'css', file:'validationEngine.css')}" type="text/css">
    <g:javascript src='studyCenter.js'/>
    %{--<script type="text/javascript" src="${resource(dir: 'js', file: 'registerPage.js')}"></script>--}%
</head>

<body>
<div id="main">
    <g:if test="${flash.message}">
        <div class="message"><div class="university-status-message">${flash.message}</div></div>
    </g:if>
    <div id="msg"> </div>
    <form id="formID1" name="formID1">



        <div class="university-location-select">
            <div class="university-label-location-select">
            <label><g:message code="default.createStudy.district"/></label></div>
                <g:select name="district" id="district" optionKey="id" value="${studyCentreInstance?.city?.district?.id}" class="university-size-1-3" onchange="showCityList()" optionValue="districtName" from="${District.findAll()}" noSelection="['':' Select District']" />
        </div>
        <div id="cityList" class="university-location-select">
            <div class="university-label-location-select">
        <label><g:message code="default.createStudy.city"/></label></div>
            <g:select name="city" id="city" optionKey="id" value="${studyCentreInstance?.city?.id}" class="university-size-1-3"  optionValue="cityName" from="${City.findAllByDistrict(District.get(studyCentreInstance?.city?.district?.id))}" noSelection="['':' Select City']"/>
        </div>

        <div  id="VenueDiv" class="middleDiv">
        </div>
        <table style="border: none">


            <tr>
                <td colspan="4">
                    <input type="submit" value="Submit" onclick="submitForm()" class="${classs} buttonCss" >
                    <input type="button" value="Cancel" onclick="reset1()" class="${classs} buttonCss"/>

                </td>
            </tr>
        </table>
    </form>
</div>
<script>
    function isNumber(evt) {

        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        }
        return true;
    }
    var index=0;


    $(document).ready(function () {
        AccountHeadDiv() ;
    });

    function AccountHeadDiv() {
        index++;

        $('#VenueDiv').append('<div style="border: 1px solid lightgray"  class="middleDiv" id="VenueDiv' + index + '"  ></div>');




        $('#VenueDiv' + index).append(
                '<div class="Venue">' +
                        '<label>Name *</label>' +
                        '<input type="text" style="" class="validate[required] text-input "   style="" name="examinationCentreName" id="examinationCentreName' + index + '" "/></div>'+
                        '<div class="Venue"><label>Centre Code *</label>' +
                        '<input type="text"  type="text" style="" onkeypress="return isNumber(event)" class="validate[required] text-input "  name="examinationCentreCode" id="examinationCentreCode' + index + '" />' +
                        '</div>');
        $('#VenueDiv' + index).append(
                '<div class="Venue">' +
                        '<label>Capacity *</label>' +
                        '<input type="text" style="" onkeypress="return isNumber(event)" class="validate[required] text-input "   style="" name="examinationCentreCapacity" id="examinationCentreCapacity' + index + '" "/></div>'+
                        '<div class="Venue"><label>Incharge Name *</label>' +
                        '<input type="text" style="" class="validate[required] text-input "   style="" name="examinationCentreIncharge" id="examinationCentreIncharge' + index + '" "/>' +
                        '</div>');
        $('#VenueDiv' + index).append(
                '<div class="Venue">' +
                        '<label>Contact No *</label>' +
                        '<input type="text"  onkeypress="return isNumber(event)" style="" class="validate[required] text-input "  name="examinationCentreContactNo" id="examinationCentreContactNo' + index + '"  /></div>' +
                        '<div class="Venue"><label style="vertical-align: top">Address *</label>' +
                        '<textarea style="margin-left: 50px; width: 250px" rows="4" cols="4" class="validate[required] text-input "   style="" name="examinationCentreAddress" id="examinationCentreAddress' + index + '" "/>' +
                        '</div>');

//        $('#accountHeadId' + index).html('');



        if(index==1){
            $('#VenueDiv' + index).append('<div class="addButton"> <input type="button" class="buttonClass" onclick="AccountHeadDiv()" value="+" style="color: red; margin-left: 95%;" id="removeButton' + index + '"  ></div></div>');
        }
        else{
            $('#VenueDiv' + index).append(
                    '<div class="addButton"> <input type="button"  class="buttonClass" value="-" onclick="removeAccountHead(\'' + index + '\')"/ style="color: red;margin-left: 95%" id="removeButton' + index + '"  ></div></div>');

        }

    }
    function removeAccountHead(index) {


        $('#VenueDiv' + index).remove();
    }


    function submitForm() {
        var location= $("#location").val();
        if(location==0){
            alert("Please Select Location of examination centre");
        }
        else{
            jQuery("#formID1").validationEngine('attach', {
                onValidationComplete: function(form, status){
                    if (status == true) {
                        $.ajax({
                            type:"post",
                            url:'${createLink(controller: 'examinationCenter', action: 'saveExaminationCentre')}',
                            async: false,
                            data:$('#formID1').serialize() ,
                            success:function (response) {
                                reset1()
                                $('div#msg').html(response);


                            }
                            ,error:function(XMLHttpRequest, textStatus, errorThrown) {
                                console.log("response in error")
                            }
                        });
                    }
                }
            });
        }

    }
    function checkLocation(){
        var location= $("#location").val();
        if(location==0){
            alert("Please Select Location of examination centre");
            return false;
        }
        else{
            return true;
        }
    }



</script>
</body>
</html>