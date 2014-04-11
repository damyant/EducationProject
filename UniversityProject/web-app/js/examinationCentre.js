/**
 * Created by shweta on 4/9/14.
 */

var index = 0;
var flag=false;


$(document).ready(function () {

    examinationCenterDiv();

        $('#submitButton').click(function(){
            for( var i=1;i<=index;i++){
              var msg=  $("#errorMsg"+i).text()

               if(msg.length>0){
                   flag=true;
                   break;
               }
            }

            if(flag==true){

            }
            else{

                $("#examinationCenterForm").submit()
            }
        })


});

function examinationCenterDiv() {
    index++;

    $('#VenueDiv').append('<div style="border: 1px solid lightgray"  class="middleDiv" id="VenueDiv' + index + '"  ></div>');


    $('#VenueDiv' + index).append(
        '<div class="Venue">' +
            '<label>Name <span class="university-obligatory">*</span></label>' +
            '<input type="text" style="" class="validate[required, custom[onlyLetterSp],minSize[3],maxSize[50]] text-input " onkeypress="return onlyAlphabets(event,this);"  style="" name="examinationCentreName" id="examinationCentreName' + index + '" "/></div>' +
            '<div class="Venue"><label>Centre Code <span class="university-obligatory">*</span></label>' +
            '<input type="text"  type="text" style="" onchange="checkExamCenter(index)" onkeypress="return isNumber(event)" class="validate[required,custom[number],minSize[1],maxSize[10]] text-input "  name="examinationCentreCode" id="examinationCentreCode' + index + '" />' +
            '<label class="error1" id="errorMsg'+index+'"></label></div>');
    $('#VenueDiv' + index).append(
        '<div class="Venue">' +
            '<label>Capacity <span class="university-obligatory">*</span></label>' +
            '<input type="text" style="" onkeypress="return isNumber(event)" class="validate[required] text-input "   style="" name="examinationCentreCapacity" id="examinationCentreCapacity' + index + '" "/></div>' +
            '<div class="Venue"><label>Incharge Name <span class="university-obligatory">*</span></label>' +
            '<input type="text" style="" class="validate[required, custom[onlyLetterSp]] text-input " onkeypress="return onlyAlphabets(event,this);"  style="" name="examinationCentreIncharge" id="examinationCentreIncharge' + index + '" "/>' +
            '</div>');
    $('#VenueDiv' + index).append(
        '<div class="Venue">' +
            '<label>Contact No <span class="university-obligatory">*</span></label>' +
            '<input type="text"  onkeypress="return isNumber(event)" style="" class="validate[required] text-input " maxlength="10" name="examinationCentreContactNo" id="examinationCentreContactNo' + index + '"  /></div>' +
            '<div class="Venue"><label style="vertical-align: top">Address <span class="university-obligatory">*</span></label>' +
            '<textarea style="margin-left: 50px; width: 250px" rows="4" cols="4" class="validate[required] text-input "   style="" name="examinationCentreAddress" id="examinationCentreAddress' + index + '" "/>' +
            '</div>');

//        $('#accountHeadId' + index).html('');


    if (index == 1) {
        $('#VenueDiv' + index).append('<div class="addButton"> <input type="button" class="buttonClass" onclick="examinationCenterDiv()" value="+" style="color: red; margin-left: 95%;" id="removeButton' + index + '"  ></div></div>');
    }
    else {
        $('#VenueDiv' + index).append(
            '<div class="addButton"> <input type="button"  class="buttonClass" value="-" onclick="removeExaminationCentre(\'' + index + '\')"/ style="color: red;margin-left: 95%" id="removeButton' + index + '"  ></div></div>');

    }

}
function removeExaminationCentre(index) {


    $('#VenueDiv' + index).remove();
}


function submitForm() {
    var location = $("#location").val();
    if (location == 0) {
        alert("Please Select Location of examination centre");
    }
    else {
        jQuery("#submitButton").validationEngine('attach', {
            onValidationComplete: function (form, status) {
                if (status == true) {
                    $.ajax({
                        type: "post",

                        url: url('examinationCenter', 'saveExaminationCentre', ''),
                        async: false,
                        data: $('#formID1').serialize(),
                        success: function (response) {
                            reset1()
                            $('div#msg').html(response);


                        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                            console.log("response in error")
                        }
                    });
                }
            }
        });
    }

}
function checkLocation() {
    var location = $("#location").val();
    if (location == 0) {
        alert("Please Select Location of examination centre");
        return false;
    }
    else {
        return true;
    }


}


function isNumber(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function checkExamCenter(currentIndex){

        var data = $('#examinationCentreCode'+index).val();

    $.ajax({
        type: "post",
        url: url('examinationCenter', 'checkCenterCode', ''),
        data: {centerCode: data},
        success: function (data) {

            if(data.centerCode=="true"){
                $('#errorMsg'+currentIndex).text("Center Code is already registered")
               }
            else{
                $('#errorMsg'+currentIndex).text("")
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
//

}

