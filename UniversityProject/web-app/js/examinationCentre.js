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

//            $("#examinationCenterForm").submit()
        }
    })


});

function examinationCenterDiv() {
    index++;
    $('#VenueDiv').append('<div style="border: 1px solid lightgray"  class="middleDiv" id="VenueDiv' + index + '"  ></div>');


    $('#VenueDiv' + index).append(
            '<div class="Venue">' +
            '<label class="Venue-label">Venue Name <span class="university-obligatory">*</span></label>' +
            '<input type="text" style="" class="" onkeypress="return onlyAlphabets(event,this)"  onkeyup="clearErrorMsg(this)" maxlength="60" style="" name="examinationCentreName" id="examinationCentreName' + index + '" "/><label id="centerNameMsg' + index + '" class="error2" ></label></div>' +
            '<div class="Venue"><label class="Venue-label">Venue Code <span class="university-obligatory">*</span></label>' +
            '<input type="text"  type="text" style="" maxlength="6" onchange="checkExamCenter(index)" onkeypress="return isNumber(event)" onkeyup="clearErrorMsg(this)" class=""  name="examinationCentreCode" id="examinationCentreCode' + index + '" /><label id="centerCodeMsg' + index + '" class="error2" ></label>' +
            '<label class="error1" id="errorMsg' + index + '"></label></div>');
    $('#VenueDiv' + index).append(
            '<div class="Venue">' +
            '<label class="Venue-label">Capacity <span class="university-obligatory">*</span></label>' +
            '<input type="text" style="" onkeypress="return isNumber(event)" maxlength="7"  onkeyup="clearErrorMsg(this)" class=""   style="" name="examinationCentreCapacity" id="examinationCentreCapacity' + index + '" "/><label id="centerCapacityMsg' + index + '" class="error2" ></label></div>' +
            '<div class="Venue"><label class="Venue-label">Incharge Name <span class="university-obligatory">*</span></label>' +
            '<input type="text" style="" class="" maxlength="60" onkeypress="return onlyAlphabets(event,this)" onkeyup="clearErrorMsg(this)"  style="" name="examinationCentreIncharge" id="examinationCentreIncharge' + index + '" "/><label id="centerInchargeMsg' + index + '" class="error2" ></label>' +
            '</div>');
    $('#VenueDiv' + index).append(
            '<div class="Venue">' +
            '<label class="Venue-label">Contact No <span class="university-obligatory">*</span></label>' +
            '<input type="text"  onkeypress="return isNumber(event)" onkeyup="clearErrorMsg(this)" style="" class="" maxlength="10" name="examinationCentreContactNo" id="examinationCentreContactNo' + index + '"  /><label id="centerContactNoMsg' + index + '" class="error2" ></label></div>' +
            '<div class="Venue"><label style="vertical-align: top"  class="Venue-label">Address <span class="university-obligatory">*</span></label>' +
            '<textarea style="margin-left: 50px; width: 250px" rows="4" cols="4" class=""   style="" maxlength="100" name="examinationCentreAddress" onkeyup="clearErrorMsg(this)" id="examinationCentreAddress' + index + '" "/><label id="centerAddressMsg' + index + '" class="error2" ></label>' +
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
    debugger;
    $.ajax({

        type: "post",
        url: url('examinationCenter', 'saveExaminationCentre', ''),
        async: false,
        data: $('#examinationCenterForm').serialize(),
        success: function (response) {
            $("#studyCenterTab tbody tr").remove()
            document.getElementById("studyCentreForEV").style.visibility = "hidden"
            document.getElementById("examinationCenterForm").reset();
            $('div#msg').html(response);
            $("html, body").animate({ scrollTop: 0 }, "slow");

        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("response in error")
        }
    });
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
//function isNumber(evt) {
//
//    evt = (evt) ? evt : window.event;
//    var charCode = (evt.which) ? evt.which : evt.keyCode;
//    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
//        return false;
//    }
//    return true;
//}

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
}
function validateAndSubmitForm() {
    var index = $('#totalIndex').val();
    var bool = 'true';
    if ($('#district').val().length == 0) {
        $("#districtError").text("Please Select District")
        bool = 'false';
    }

    if ($('#examinationCentre').val().length == 0) {
        $("#cityError").text("Please Select Examination Centre")
        bool = 'false';
    }


    for (var i = 1; i <= index; i++) {
        if ($('#examinationCentreName' + i).val().length == 0) {
            $("#centerNameMsg" + i).text("Please Enter Examination Centre Name")
            bool = 'false';
        }

        if ($('#examinationCentreCode' + i).val().length == 0) {
            $("#centerCodeMsg" + i).text("Please Enter Examination Centre Code")
            bool = 'false';
        }

        if($("#errorMsg"+i).text().length>0){
            bool='false';
//            alert(bool)
        }

        if ($('#examinationCentreCapacity' + i).val().length == 0) {
            $("#centerCapacityMsg" + i).text("Please Enter Examination Centre Capacity")
            bool = 'false';
        }

        if ($('#examinationCentreIncharge' + i).val().length == 0) {
            $("#centerInchargeMsg" + i).text("Please Enter Examination Centre Incharge Name")
            bool = 'false';
        }

//        alert($('#examinationCentreContactNo' + i).val().length)
        if ($('#examinationCentreContactNo' + i).val().length == 0 || $('#examinationCentreContactNo' + i).val().length < 10) {
            $("#centerContactNoMsg" + i).text("Please Enter Exam Centre Contact Number(Min 10 Character Long)")
            bool = 'false';

        }


        if ($('#examinationCentreAddress' + i).val().length == 0) {
            $("#centerAddressMsg" + i).text("Please Enter Examination Centre Address")
            bool = 'false';
//            alert(bool)
        }
//        else {
//            bool = 'true'
//        }

    }
//    alert("value of boool=="+bool)
    if (bool == 'true') {
        submitForm();
    }

}
function clearErrorMsg(t) {
    $(t).next("label").text("");
    if($('#removeButton1').length>0) {
        $('#removeButton1').prop('disabled', false)
    }
}


function showList() {

    jQuery("#centreListTable").css({display: "block"});
    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getCentreList', ''),
        data: {examinationCentre: $('#examinationCentre').val(), edit: $('#edit').val()},
//            contentType: "application/json; charset=utf-8",
//            dataType: "json",
        success: function (response) {
            console.log("<><><><><><><><> " + response)
            $("div#centreList").html(response)


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function showListOfStudyCenter(t) {

    var data = $(t).val();
    $.ajax({
        type: "post",
        url: url('studyCenter', 'getStudyCenterForECList', ''),
        data: {data: data},
        success: function (data) {

            if (data.flag != "false") {
                document.getElementById("studyCentreForEV").style.visibility = "visible";
                $("#msgDiv").html("")
                $("#studyCenterTab thead tr").remove()
                $("#studyCenterTab thead").append('<tr><th style="width:30%;">Name</th><th style="width: 25%;">Address</th><th style="width: 30%;">Website URL</th><th style="width: 15%;">Select One Option</th></tr>')
                $("#studyCenterTab tbody tr").remove()
                for (var i = 0; i < data.length; i++) {
                        $("#studyCenterTab tbody").append('<tr><td>' + data[i].name + '</td><td>' + data[i].address + '</td><td>' + data[i].websiteUrl + '</td><td><input type="radio" name="Select" onchange="loadValues(' + data[i].id + ')" value="' + data[i].id + '" id="select'+i+'"/></td></tr>')
                }
            }
            else {
                document.getElementById("studyCentreForEV").style.visibility = "hidden";
                $("#studyCenterTab tbody tr").remove()
                $("#msgDiv").html("No Study Center Available or All Study Center are converted to Examination Venue")
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}


function loadValues(data){
    $.ajax({
        type: "post",
        url: url('studyCenter', 'getStudyCenterDetails', ''),
        data: {centreId: data},
        success: function (data) {
            $('#examinationCentreName1').val(data.studyCenterInst.name)
            $('#examinationCentreContactNo1').val(data.studyCenterInst.phoneNoOfHeadIns)
            $('#examinationCentreIncharge1').val(data.studyCenterInst.nameOfHeadIns)
            $('#examinationCentreAddress1').val(data.studyCenterInst.address)
            $('#removeButton1').prop('disabled', true)
        }
    })
}