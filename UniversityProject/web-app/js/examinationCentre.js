/**
 * Created by shweta on 4/9/14.
 */

var index = 0;
var flag = false;


$(document).ready(function () {

    examinationCenterDiv();

    $('#submitButton').click(function () {
        for (var i = 1; i <= index; i++) {
            var msg = $("#errorMsg" + i).text()

            if (msg.length > 0) {
                flag = true;
                break;
            }
        }

        if (flag == true) {

        }
        else {

            $("#examinationCenterForm").submit()
        }
    })


});

function examinationCenterDiv() {
    index++;
    $('#VenueDiv').append('<div style="border: 1px solid lightgray"  class="middleDiv" id="VenueDiv' + index + '"  ></div>');


    $('#VenueDiv' + index).append(
            '<div class="Venue">' +
            '<label class="Venue-label">Name <span class="university-obligatory">*</span></label>' +
            '<input type="text" style="" class="" onkeypress="return onlyAlphabets(event,this);"  style="" name="examinationCentreName" id="examinationCentreName' + index + '" "/><label id="centerNameMsg'+index+'" class="error2" ></label></div>' +
            '<div class="Venue"><label class="Venue-label">Centre Code <span class="university-obligatory">*</span></label>' +
            '<input type="text"  type="text" style="" onchange="checkExamCenter(index)" onkeypress="return isNumber(event)" class=""  name="examinationCentreCode" id="examinationCentreCode' + index + '" /><label id="centerCodeMsg'+index+'" class="error2" ></label>' +
            '<label class="error1" id="errorMsg' + index + '"></label></div>');
    $('#VenueDiv' + index).append(
            '<div class="Venue">' +
            '<label class="Venue-label">Capacity <span class="university-obligatory">*</span></label>' +
            '<input type="text" style="" onkeypress="return isNumber(event)" class=""   style="" name="examinationCentreCapacity" id="examinationCentreCapacity' + index + '" "/><label id="centerCapacityMsg'+index+'" class="error2" ></label></div>' +
            '<div class="Venue"><label class="Venue-label">Incharge Name <span class="university-obligatory">*</span></label>' +
            '<input type="text" style="" class="" onkeypress="return onlyAlphabets(event,this);"  style="" name="examinationCentreIncharge" id="examinationCentreIncharge' + index + '" "/><label id="centerInchargeMsg'+index+'" class="error2" ></label>' +
            '</div>');
    $('#VenueDiv' + index).append(
            '<div class="Venue">' +
            '<label class="Venue-label">Contact No <span class="university-obligatory">*</span></label>' +
            '<input type="text"  onkeypress="return isNumber(event)" style="" class="" maxlength="10" name="examinationCentreContactNo" id="examinationCentreContactNo' + index + '"  /><label id="centerContactNoMsg'+index+'" class="error2" ></label></div>' +
            '<div class="Venue"><label style="vertical-align: top"  class="Venue-label">Address <span class="university-obligatory">*</span></label>' +
            '<textarea style="margin-left: 50px; width: 250px" rows="4" cols="4" class=""   style="" name="examinationCentreAddress" id="examinationCentreAddress' + index + '" "/><label id="centerAddressMsg'+index+'" class="error2" ></label>' +
            '</div>');

//        $('#accountHeadId' + index).html('');


    if (index == 1) {
        $('#VenueDiv' + index).append('<div class="addButton"> <input type="button" class="buttonClass" onclick="examinationCenterDiv()" value="+" style="color: red; margin-left: 95%;" id="removeButton' + index + '"  ></div></div>');
    }
    else {
        $('#VenueDiv' + index).append(
                '<div class="addButton"> <input type="button"  class="buttonClass" value="-" onclick="removeExaminationCentre(\'' + index + '\')"/ style="color: red;margin-left: 95%" id="removeButton' + index + '"  ></div></div>');

    }
    $('#totalIndex').val(index);


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

function checkExamCenter(currentIndex) {

    var data = $('#examinationCentreCode' + index).val();
    $.ajax({
        type: "post",
        url: url('examinationCenter', 'checkCenterCode', ''),
        data: {centerCode: data},
        success: function (data) {

            if (data.centerCode == "true") {
                $('#errorMsg' + currentIndex).text("Center Code is already registered")
            }
            else {
                $('#errorMsg' + currentIndex).text("")
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
//

}
function validateAndSubmitForm() {
    var index = $('#totalIndex').val();
    var bool='true';
//   $("#examinationCenterForm").validate();
//    for (i = 1; i <= index; i++) {
//        $('#examinationCentreName' + i).rules("add", {
//            required: true,
//            textonly: true,
//            messages: {
//                required: "Please Enter Examination Centre Name",
//                textonly: "only Accepts Text"
//            }
//        });
//        $("#examinationCentreCode" + i).rules("add", {
//            required: true,
//            number: true,
//            messages: {
//                required: "Please Enter Examination Centre Name",
//                number: "only Accepts Number"
//            }
//        });
//    }
    for( var i=1;i<=index;i++){
//        alert($('#examinationCentreName'+i).val().length)
        if($('#examinationCentreName'+i).val().length==0){
            $("#centerNameMsg"+i).text("Please Enter Examination Centre Name")
            bool='false';
        }
        else if($('#examinationCentreCode'+i).val().length==0){
            $("#centerCodeMsg"+i).text("Please Enter Examination Centre Code")
            bool='false';
        }
        else if($('#examinationCentreCapacity'+i).val().length==0){
            $("#centerCapacityMsg"+i).text("Please Enter Examination Centre Capacity")
            bool='false';
        }
        else if($('#examinationCentreIncharge'+i).val().length==0){
            $("#centerInchargeMsg"+i).text("Please Enter Examination Centre Incharge Name")
            bool='false';
        }
        else if($('#examinationCentreContactNo'+i).val().length==0||$('#examinationCentreContactNo'+i).val().length<10){
            $("#centerContactNoMsg"+i).text("Please Enter Exam Centre Contact Number(Min 10 Character Long)")
            bool='false';
        }
        else if($('#examinationCentreAddress'+i).val().length==0){
            $("#centerAddressMsg"+i).text("Please Enter Examination Centre Address")
            bool='false';
        }
        else{
            bool='true'
        }

    }
    if(bool=='true'){
        submitForm();
    }

}

