/**
 * Created by shweta on 3/11/14.
 */

//$(document).ready(function(){
//    $('#districtlocation').click(function() {
//          alert("hey");
//    });
//
//});
function histryBack(district, type){
//    alert(type)
    if(type=='update') {
        window.open("/UniversityProject/studyCenter/viewStudyCentre?type=" + type + "&district=" + district, '_self', false)
    }
    else{
        window.open("/UniversityProject/studyCenter/viewStudyCentre?district=" + district, '_self', false)
    }
}
function showCityList() {
    var data;
    data = $('#district').val();
    if (data) {
    }
    else {
        data = $('#districtCumulative').val()
//        alert("in else "+ data)
    }

    if (data) {
        $.ajax({
            type: "post",
            url: url('studyCenter', 'getCityList', ''),
            data: {data: data},
            success: function (data) {
                $("#city").prop("disabled", false)

                $("#city").empty().append('<option value="">Select City</option>');

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
    else {
        $("#city").empty().append('<option value="">Select City</option>');
        $("#examinationCentre").empty().append('<option value=""> Select Examination Venue</option>')
        $('#programRow').hide()
    }

}
function showExamCenterList() {
    var data = $('#district').val();
    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getExamCenterName', ''),
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

function showStudyCenterListByDistrict() {
    var data = $('#district').val();
    var type = $('#ParameterType').val();
    $.ajax({
        type: "post",
        url: url('studyCenter', 'getDistrictStudyCenterList', ''),
        data: {data: data},
        success: function (data) {
//            var count=1
            if (data.flag != "false") {
                $("#msgDiv").html("")
                $("#studyCenterTab thead tr").remove()
                $("#studyCenterTab thead").append('<tr><th class="university-size-1-5">Name</th><th  class="university-size-1-5">Address</th><th  class="university-size-1-5">City</th><th  class="university-size-1-5">Website URL</th><th  class="university-size-1-5"></th></tr>')
                $("#studyCenterTab tbody tr").remove()
                for (var i = 0; i < data.result.length; i++) {
                    if(data.result[i].length!=0) {
                        for (var j = 0; j < data.result[i].length; j++) {
//                            count++
                            if (type == 'update') {
                                $("#studyCenterTab tbody").append('<tr><td>' + data.result[i][j].name + '</td><td>' + data.result[i][j].address + '</td><td>' + data.cityList[i].cityName + '</td><td>' + data.result[i][j].websiteUrl + '</td><td><div class="university-float-right"><input type="submit" value="Update" class="university-button" onclick="updateStudyCenter(' + data.result[i][j].id + ')"/><input type="button" onclick="deleteStudyCenter('+ data.result[i][j].id +')"  value="Delete" class="university-button" /></div></td></tr>')
                            }
                            else {
                                $("#studyCenterTab tbody").append('<tr><td>' + data.result[i][j].name + '</td><td>' + data.result[i][j].address + '</td><td>' + data.cityList[i].cityName + '</td><td>' + data.result[i][j].websiteUrl + '</td><td><div class="university-float-right"><input type="submit" value="View" class="university-button" onclick="viewStudyCenter(' + data.result[i][j].id + ')"/></td></tr>')
                            }
                        }
                    }
                }
//                alert(count)
                document.getElementById("paginationDiv").style.visibility = "visible";
                $table_rows = $('#studyCenterTab tbody tr');

                var table_row_limit = 5;

                var page_table = function(page) {

                    // calculate the offset and limit values
                    var offset = (page - 1) * table_row_limit,
                        limit = page * table_row_limit;

                    // hide all table rows
                    $table_rows.hide();

                    // show only the n rows
                    $table_rows.slice(offset, limit).show();

                }
                var pageNo=0

//                alert($table_rows.length)
                if($table_rows.length % table_row_limit){
                    pageNo=parseInt(parseInt($table_rows.length) / table_row_limit)+1
                }
                else{
                    pageNo=parseInt($table_rows.length / table_row_limit)
                }
//                alert(5%5)
                $('.pagination').jqPagination({
                    max_page: pageNo,
                    paged: page_table
                });
                page_table(1);
            }
            else {
                $("#studyCenterTab tbody tr").remove()
                $("#msgDiv").html("No Study Center found")
                document.getElementById("paginationDiv").style.visibility = "hidden";
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
    var data = $(t).val();
    if (data) {
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
    else {
        $("#examinationCentre").empty().append('<option value=""> Select Examination Venue</option>')
        $('#programRow').hide()
    }

}


function showProgrammeList() {
    var data = $('#examinationCentre').val()
    if (data) {
        $.ajax({
            type: 'post',
            url: url('examinationCenter', 'getProgrammeList', ''),
            data: {data: data},
            success: function (data) {

                if (data.length > 0) {
                    $('#submit').css('display', 'inline')
                }
                $("#programList").empty().append('<option value=""> Select Programmes</option>')
                $("#programList").append('<option value="allProgram"> All Programmes</option>')
                for (var i = 0; i < data.length; i++) {
                    $("#programList").append('<option value="' + data[i].id + '">' + data[i].courseName + '</option>')
                }
                $('#programRow').show()
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {

            }
        });
    }
    else {
        $("#programList").empty().append('<option value=""> Select Programmes</option>')
        $("#programList").append('<option value="allProgram"> All Programmes</option>')
        $('#programRow').hide()
    }


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




