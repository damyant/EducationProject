


$(document).ready(function () {
    var cnt=0;
    var maxCap=0;
    maxCap = $("#totalCapacity").val()
    $("input[name='student']").change(function () {
        var maxAllowed = 6;
        cnt = $("input[name='student']:checked").length;
        if(maxCap>0)
            $("#totalCapacity").val(maxCap-cnt);
        if (cnt>maxAllowed) {
            $("#totalCapacity").val(cnt-1);
            $(this).prop("checked", "");
            $("#totalCapacity").val(0);
            alert('You can select maximum ' + maxAllowed + ' Students only!!');
        }
    });
});



function getSemester(){
    var data = $('#programList').val();
    $.ajax({
        type: "post",
        url: url('admitCard', 'getSemesterList', ''),
        data: {data: data},
        success: function (data) {
            $("#semesterList").empty().append('data <option value="">Select Semester</option>')
            for (var i = 1; i <= data.totalSem; i++) {
                $("#semesterList").append('<option value="' + i + '">' + i + '</option>')
            }
            for (var i = 0; i < data.session.length; i++) {
                $("#SessionList").append('<option value="' + data.session[i].id + '">' + data.session[i].sessionOfProgram + '</option>')
            }


        }

    })

}
function showStudentInfo(){
    document.getElementById("admitCardTab").style.display = "block";
    document.getElementById("subjectTab").style.display = "block";
$("#admitCardTab ").append('<tr><th>Roll Number</th><th>Name of the Student</th><th>Select Student</th><th></th></tr>')
 $('#subjectTab').append('<tr><th>Subject</th><th>Subject-Code</th><th>Time Of Exam</th><th>Date Of Exam</th></tr>')
}

function showExamVenueList() {


    var data = $('#city').val();

    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getExamCentreList', ''),
        data: "city="+$('#city').val(),
        success: function (data) {
            $("#examCenterList").empty().append('data <option value="">Select Examination Venue</option>')
            for (var i = 0; i < data.name.length; i++) {
               $("#examCenterList").append('<option value="' + data.id[i] + '">' + data.name[i] + '</option>')
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function showExamVenueCapacity(){

    $.ajax({
        type: "post",
        url: url('admitCard', 'examVenueCapacity', ''),
        data: {examCenterId: $("#examCenterList").val()},
        success: function (data) {
        if(data.capacity){
            $('#totalCapacity').val(data.capacity)
        }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}


function getStudentsForAdmitCard(){

    $.ajax({
        type: "post",
        url: url('admitCard', 'getStudentsForAdmitCard', ''),
        data: $("#admitCardForm").serialize(),
        success: function (data) {
            $('#admitCardTab').find("tr:gt(0)").remove();
              if(data.length!=undefined){
                  var count=1;
                 for(var i=0;i<data.length;i++){
                       $('#admitCardTab').append('<tr><td><input name="studentCheckbox" type="checkbox" id='+data[i].id+'></td><td>'+count+'</td><td>'+data[i].rollNo+'</td><td>'+data[i].name+'</td></tr>')
                    ++count;
                }
            }
            else{
                  $('#admitCardTab').append('<tr><td colspan="4">No Students Found</td></tr>');
              }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}



function showExamVenueList1()  {
    $('#addExamCentre').empty();
    var data = $('#city').val();
    $('#CentreForExamVenue').html($('#city option:selected').text());
    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getExamCentreList', ''),
        data: $('#assignExamVenue').serialize(),
        success: function (data) {

            $("#examCenterList").empty().append('')
            for (var i = 0; i < data.name.length; i++) {
                $("#examCenterList").append('<option value="' + data.id[i] + '">' + data.name[i] + '</option>')
                $("#moveButton").css("visibility", 'visible');
                $("#movetoSelect").css("visibility", 'visible');
            }

            for(var j=0;j<data.assocaitedExamVenue.length;j++){

                $("#addExamCentre").append('<option value="' + data.assocaitedExamVenue[j].id + '">' + data.assocaitedExamVenue[j].name + '</option>')
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function addVenue(param){
//    alert('kiii')
    var venue=$(param).find(":selected").text()
    var centre=$('#city option:selected').text()
    var course=$('#programList option:selected').text()
    $("#examVenueList").append('<tr><td>'+course+'</td><td>'+centre+'</td><td>'+venue+'</td></tr>');
}

function addExamCenterToList() {
    var selectedValues = [];
    var nonSelected = [];
    var inList2;
    $('#examCenterList :selected').each(function (l, list1Selected) {
        selectedValues[l] = $(list1Selected).val();
        inList2 = false;
        $('#addExamCentre option').each(function (m, list2Selected) {
            nonSelected[m] = $(list2Selected).val();
            if (selectedValues[l] == nonSelected[m]) {
                inList2 = true;
            }
        });

        if (inList2 != true) {
            $('#addExamCentre').append("<option value='" + selectedValues[l] + "'>" + $(list1Selected).text() + "</option>");

            var text1 = $(list1Selected).val()
            $('#examCenterList option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#examCenterList option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        }

    });
}

function removeExamCenterFromList() {
    $('#addExamCentre option:selected').each(function () {
        $(this).remove();
        $('#addExamCentre option:not(selected)').each(function (k, semSelected) {
            var text2 = $(semSelected).val()
            $('#examCenterList option:selected').each(function (n, allsubSelected) {
                var text3 = $(allsubSelected).val()
                $('#examCenterList option').filter(function () {
                    return $(this).val() == text3;
                }).attr('selected', false);
            });
        });
    });
}

function setCourseLabel(t){
//    $('#assignExamVenue').reset();
    $('#courseForExamVenue').html($(t).find(":selected").text());
    $('#CentreForExamVenue').html('');
    $('#city').val('');
    $('#examCenterList').empty();
    $('#addExamCentre').empty();

}

function generateAdmitCard(){

    var selectedStudentList=[]
    if ($("input[name=studentCheckbox]:checked").length != 0) {
        $("input[name=studentCheckbox]:checked").each(function (i) {

            if ($(this).attr("checked", true)) {
                selectedStudentList[i] = $(this).attr("id");

            }

        })
        $("#studentList").val(selectedStudentList)
        $("#admitCardForm").submit();
//        studentsSelected(selectedStudentList)
        return true;

    }
    else {
        alert("Select the student first.");
        return false;
    }




}

function studentsSelected(selectedStudentList){

    alert("hi"+selectedStudentList)

    $.ajax({
        type: "post",
        url: url('admitCard', 'printAdmitCard', ''),
        data: "studentList="+selectedStudentList,
        success: function (data) {
            $('#admitCardTab').find("tr:gt(0)").remove();
            if(data.length!=undefined){
                var count=1;
                for(var i=0;i<data.length;i++){
                    $('#admitCardTab').append('<tr><td><input type="checkbox" id='+data[i].id+'></td><td>'+count+'</td><td>'+data[i].rollNo+'</td><td>'+data[i].name+'</td></tr>')
                    ++count;
                }
            }
            else{
                $('#admitCardTab').append('<tr><td colspan="4">No Students Found</td></tr>');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}









