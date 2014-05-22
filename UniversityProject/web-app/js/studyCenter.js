/**
 * Created by shweta on 3/11/14.
 */

//$(document).ready(function(){
//    $('#districtlocation').click(function() {
//          alert("hey");
//    });
//
//});


function showCityList() {
//    alert('in show city list')
    var data;
    data = $('#district').val();
//    alert("----------------"+data)
    if(data){

    }
    else{
        data=$('#districtCumulative').val()
//        alert("in else "+ data)
    }




    $.ajax({
        type: "post",
        url: url('studyCenter', 'getCityList', ''),
        data: {data: data},
        success: function (data) {
            $("#city").empty().append('<option value="">Select Examination Centre</option>');

            $("#examCityCumulative").empty().append('<option value="">Select Examination Centre</option>');
            for (var i = 0; i < data.length; i++) {

                $("#city").append('<option value="' + data[i].id + '">' + data[i].cityName + '</option>');

                $("#examCityCumulative").append('<option value="' + data[i].id + '">' + data[i].cityName + '</option>')
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

}
function showExamCenterList() {
    var data = $('#district').val();

    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getExamCenterList', ''),
        data: {data: data},
        success: function (data) {
            $("#examinationCentre").empty().append('<option value="">Select Examination Centre</option>')
            for (var i = 0; i < data.length; i++) {
                $("#examinationCentre").append('<option value="' + data[i].id + '">' + data[i].cityName + '</option>')
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

}

function showStudyCenterList() {

    var data = $('#city').val();
    var type = $('#ParameterType').val();
    $.ajax({
        type: "post",
        url: url('studyCenter', 'getStudyCenterList', ''),
        data: {data: data},
        success: function (data) {

            if (data.flag != "false") {
                $("#msgDiv").html("")
                $("#studyCenterTab thead tr").remove()
                $("#studyCenterTab thead").append('<tr><th>Name</th><th>Address</th><th>Website URL</th><th></th></tr>')
                $("#studyCenterTab tbody tr").remove()
                for (var i = 0; i < data.length; i++) {
                    if (type == 'update') {

                        $("#studyCenterTab tbody").append('<tr><td>' + data[i].name + '</td><td>' + data[i].address + '</td><td>' + data[i].websiteUrl + '</td><td><div class="university-float-right"><input type="submit" value="Update" class="university-button" onclick="updateStudyCenter(' + data[i].id + ')"/><input type="button" onclick="deleteStudyCenter(' + data[i].id + ')"  value="Delete" class="university-button" /></div></td></tr>')

                    }
                    else {
                        $("#studyCenterTab tbody").append('<tr><td>' + data[i].name + '</td><td>' + data[i].address + '</td><td>' + data[i].websiteUrl + '</td><td><div class="university-float-right"><input type="submit" value="View" class="university-button" onclick="viewStudyCenter(' + data[i].id + ')"/></td></tr>')
                    }
                }
            }
            else {
                $("#studyCenterTab tbody tr").remove()
                $("#msgDiv").html("No Study Center found")
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}



function updateStudyCenter() {

    window.location.href = '/UniversityProject/studyCenter/createNewStudyCenter';
}
function deleteStudyCenter(studyCenterId) {
    var result = confirm("Are you sure you want to delete this item?", "Confirm Delete");
    if (result == true) {
        var data = studyCenterId;
        $.ajax({
            type: "post",
            url: url('studyCenter', 'deleteStudyCenter', ''),
            data: {data: data},
            success: function (data) {
                //document.location.reload();
                showStudyCenterList()
            }
        });
    }
}
function updateStudyCenter(studyCenterId) {
    var data = studyCenterId
    window.location.href = '/UniversityProject/studyCenter/createNewStudyCenter?studyCenterId=' + data + '&type=edit';

}
function viewStudyCenter(studyCenterId) {
    var data = studyCenterId
    window.location.href = '/UniversityProject/studyCenter/createNewStudyCenter?studyCenterId=' + data + '&type=view';

}
function showCentreList(t) {
//    alert('now getting centres'+ t)
    var data = $(t).val();
    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getExaminationVenueList', ''),
        data: {data: data},
        success: function (data) {

            $("#examinationCentre").empty().append('<option value=""> Select Examination Venue</option>')
            $("#examinationCentreCumulative").empty().append('<option value=""> Select Examination Venue</option>')
            for (var i = 0; i < data.length; i++) {
                $("#examinationCentre").append('<option value="' + data[i].id + '">' + data[i].name + '</option>')
                $("#examinationCentreCumulative").append('<option value="' + data[i].id + '">' + data[i].name + '</option>')
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

}


function showProgrammeList(){
    var data= $('#examinationCentre').val()

    $.ajax({
        type:'post',
        url:url('examinationCenter','getProgrammeList',''),
        data:{data:data},
        success:function(data){

            if(data.length>0){
                $('#submit').css('display','inline')
            }
            $("#programList").empty().append('<option value=""> Select Programmes</option>')
            $("#programList").append('<option value="allProgram"> All Programmes</option>')
            for (var i = 0; i < data.length; i++) {
                $("#programList").append('<option value="' + data[i].id + '">' + data[i].courseName + '</option>')
            }
            $('#programRow').show()
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){

        }
    });

}




function checkStudyCenter() {

    var data = $('#centerCode').val();
    $.ajax({
        type: "post",
        url: url('studyCenter', 'checkCenterCode', ''),
        data: {centerCode: data},
        success: function (data) {

            if (data.centerCode == "true") {
                $('#errorMsg').text("Center Code is already registered")
            }
            else {
                $('#errorMsg').text("")
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
//

}




