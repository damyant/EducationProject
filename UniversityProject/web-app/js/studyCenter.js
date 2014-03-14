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
    var data = $('#district').val();

    $.ajax({
        type: "post",
        url: url('studyCenter', 'getCityList', ''),
        data: {data: data},
        success: function (data) {
            $("#city").empty().append('<option value="">Select City</option>')
            for (var i = 0; i <= data.length; i++) {
                $("#city").append('<option value="' + data[i].id + '">' + data[i].cityName + '</option>')
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

}

function showStudyCenterList(){

    var data = $('#city').val();
    var type = $('#ParameterType').val();
    $.ajax({
        type: "post",
        url: url('studyCenter', 'getStudyCenterList', ''),
        data: {data: data},
        success: function (data) {

            if(data.flag!="false"){
                $("#msgDiv").html("")
                $("#studyCenterTab thead tr").remove()
                if(type=='update'){
                    $("#studyCenterTab thead").append('<tr><th>Name</th><th>Address</th><th>Website URL</th><th></th></tr>')
                }
                else{
                    $("#studyCenterTab thead").append('<tr><th>Name</th><th>Address</th><th>Website URL</th></tr>')
                }
                $("#studyCenterTab tbody tr").remove()
              for (var i = 0; i <data.length; i++) {
                  if(type=='update'){

                      $("#studyCenterTab tbody").append('<tr><td>'+data[i].name+'</td><td>'+data[i].address+'</td><td>'+data[i].websiteUrl+'</td><td><div class="university-float-right"><input type="submit" value="Update" class="university-button" onclick="updateStudyCenter('+data[i].id+')"/><input type="button" onclick="deleteStudyCenter('+data[i].id+')"  value="Delete" class="university-button" /></div></td></tr>')

                  }
                  else{
                      $("#studyCenterTab tbody").append('<tr><td>'+data[i].name+'</td><td>'+data[i].address+'</td><td>'+data[i].websiteUrl+'</td></tr>')
                  }
            }
          }
          else{
                $("#studyCenterTab tbody tr").remove()
                $("#msgDiv").html("No Study Center found")
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}



function updateStudyCenter(){

    window.location.href = '/UniversityProject/studyCenter/createNewStudyCenter';
}
function deleteStudyCenter(studyCenterId){
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
function updateStudyCenter(studyCenterId){
    var data = studyCenterId
    window.location.href = '/UniversityProject/studyCenter/createNewStudyCenter?studyCenterId='+data+'&type=edit';

}
