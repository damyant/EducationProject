<%--
  Created by IntelliJ IDEA.
  User: Damyant
  Date: 2/7/14
  Time: 10:37 AM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Create Examination Center</title>
    <script type='text/javascript' charset='utf-8' src='${resource(dir: 'js', file: 'jquery.js')}'></script>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'create.css')}" type="text/css">
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation-en.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'validation-engine.js')}"></script>
    <link rel="stylesheet" href="${resource(dir:'css', file:'validationEngine.css')}" type="text/css">

</head>

<body>
<div id="main">
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div id="msg"> </div>
    <form id="formID1" name="formID1">


           <table class="university-table-1-2">
               <tr>
               <td>
           <div>
                <label><g:message code="default.createExam.location"/></label>
                <select name="location" >
                    <option value="0">select location</option>
                    <option value="Noida">Noida</option>
                    <option value="Guahati">Guahati</option>
                    <option value="Golaghat">Golaghat</option>
                    <option value="Jaipur">Jaipur</option>
                </select >
           </div>
                </td>
               </tr>
        <tr>
               <td>
        <div  id="VenueDiv" class="middleDiv">
        </div>
        </tr>
               </td>
           </table>
                  <div>
                    <input type="submit" value="Submit" onclick="submitForm()" class="university-button university-margin-top" >
                    <input type="button" value="Cancel" onclick="reset1()" class="university-button university-margin-top"/>
                  </div>

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
                        '<label>Name</label>' +
                        '<input type="text" style="" placeholder="Name of Examination Centre"  class="validate[required] text-input "   style="" name="examinationCentreName" id="examinationCentreName' + index + '" "/></div>'+
                        '<div class="Venue"><label>Centre Code</label>' +
                        '<input type="text"  placeholder="Code of Examination Centre" type="text" style="" class="validate[required] text-input "  name="examinationCentreCode" id="examinationCentreCode' + index + '" />' +
                        '</div>');
        $('#VenueDiv' + index).append(
                '<div class="Venue">' +
                        '<label>Capacity</label>' +
                        '<input type="text" style="" placeholder="Capacity of Examination Centre"  class="validate[required] text-input "   style="" name="examinationCentreCapacity" id="examinationCentreCapacity' + index + '" "/></div>'+
                        '<div class="Venue"><label>Incharge</label>' +
                        '<input type="text" style="" placeholder="In charge of Examination Centre"  class="validate[required] text-input "   style="" name="examinationCentreIncharge" id="examinationCentreIncharge' + index + '" "/>' +
                '</div>');
        $('#VenueDiv' + index).append(
                '<div class="Venue">' +
                        '<label>Contact No</label>' +
                        '<input type="text"  placeholder="Contact No of Examination Centre"  style="" class="validate[required] text-input "  name="examinationCentreContactNo" id="examinationCentreContactNo' + index + '" /></div>' +
                        '<div class="Venue"><label style="vertical-align: top">Address</label>' +
                        '<textarea style="margin-left: 50px; width: 250px" rows="4" cols="4" placeholder="Address of Examination Centre"  class="validate[required] text-input "   style="" name="examinationCentreAddress" id="examinationCentreAddress' + index + '" "/>' +
                        '</div>');

//        $('#accountHeadId' + index).html('');



        if(index==1){
            $('#VenueDiv' + index).append('<div class="addButton"> <input type="button" class="buttonClass" onclick="AccountHeadDiv()" value="+" style="color: red;" id="removeButton' + index + '"  ></div></div>');
        }
        else{
            $('#VenueDiv' + index).append(
                    '<div class="addButton"> <input type="button"  class="buttonClass" value="-" onclick="removeAccountHead(\'' + index + '\')"/ style="color: red;float: right" id="removeButton' + index + '"  ></div></div>');

        }

    }
    function removeAccountHead(index) {


        $('#VenueDiv' + index).remove();
    }


    function submitForm() {

        jQuery("#formID1").validationEngine('attach', {
            onValidationComplete: function(form, status){
               if (status == true) {
                        $.ajax({
                            type:"post",
                            url:'${createLink(controller: 'examinationCenter', action: 'saveExaminationCentre')}',
                            async: false,
                            data:$('#formID1').serialize() ,
                            success:function (response) {
                                $('div#msg').html(response);
                                reset1()

                            }
                            ,error:function(XMLHttpRequest, textStatus, errorThrown) {
                                console.log("response in error")
                            }
                        });
                    }
            }
        });

    }



</script>
</body>
</html>