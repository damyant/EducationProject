//
//jQuery(function($) {
//
//
//    $("a.newAdmitCardPopup").click(function() {
//        loading(); // loading
//        setTimeout(function(){ // then show popup, deley in .5 second
//            loadPopup(); // function show popup
//        }, 500); // .5 second
//        return false;
//    });
//
//
//
//
//    /* event for close the popup */
//    $("div.close").hover(
//        function() {
//            $('span.ecs_tooltip').show();
//        },
//        function () {
//            $('span.ecs_tooltip').hide();
//        }
//    );
//
//    $("div.close").click(function() {
//        disablePopup();  // function close pop up
//    });
//
//    $(this).keyup(function(event) {
//        if (event.which == 27) { // 27 is 'Ecs' in the keyboard
//            disablePopup();  // function close pop up
//        }
//    });
//
//    $("div#admitCardBackgroundPopup").click(function() {
//        disablePopup();  // function close pop up
//    });
//
//    $('a.livebox').click(function() {
//        alert('Hello World!');
//        return false;
//    });
//
//
//
//    /************** start: functions. **************/
//    function loading() {
//        $("div.loader").show();
//    }
//    function closeloading() {
//        $("div.loader").fadeOut('normal');
//    }
//
//    var popupStatus = 0; // set value
//
//    function loadPopup() {
//        if(popupStatus == 0) { // if value is 0, show popup
//            closeloading(); // fadeout loading
//            $("#admitCardToPopup").fadeIn(0500); // fadein popup div
//            $("#admitCardBackgroundPopup").css("opacity", "0.7"); // css opacity, supports IE7, IE8
//            $("#admitCardBackgroundPopup").fadeIn(0001);
//            popupStatus = 1; // and set value to 1
//        }
//    }
//
//    function disablePopup() {
//        if(popupStatus == 1) { // if value is 1, close popup
//            $("#admitCardToPopup").fadeOut("normal");
//            $("#admitCardBackgroundPopup").fadeOut("normal");
//            popupStatus = 0;  // and set value to 0
//        }
//    }
//
//    /************** end: functions. **************/
//}); // jQuery End
//;

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
        }

    })

}
function showStudentInfo(){
    document.getElementById("admitCardTab").style.display = "block";
    document.getElementById("subjectTab").style.display = "block";
$("#admitCardTab ").append('<tr><th>Roll Number</th><th>Name of the Student</th><th>Select Student</th><th></th></tr>')
 $('#subjectTab').append('<tr><th>Subject</th><th>Subject-Code</th><th>Time Of Exam</th><th>Date Of Exam</th></tr>')
}

function showExamCentreList() {
    var data = $('#city').val();
    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getExamCentreList', ''),
        data: {data: data},
        success: function (data) {
            $("#examCenterList").empty().append('<option value="">Select Examination Venue</option>')
            for (var i = 0; i < data.name.length; i++) {
                $("#examCenterList").append('<option value="' + data.id[i] + '">' + data.name[i] + '</option>')
//                $("#examCenterList").multiselect();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}


function showExamVenueList()  {
    var data = $('#city').val();
    $('#CentreForExamVenue').html($('#city option:selected').text());
    $.ajax({
        type: "post",
        url: url('examinationCenter', 'getExamCentreList', ''),
        data: {data: data},
        success: function (data) {
            $("#examCenterList").empty().append('')
            for (var i = 0; i < data.name.length; i++) {
                $("#examCenterList").append('<option value="' + data.id[i] + '">' + data.name[i] + '</option>')
                $("#moveButton").css("visibility", 'visible');
                $("#movetoSelect").css("visibility", 'visible');
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
//            alert(text1);
//            $('#semester'+j+' option').filter(function() {
//                //may want to use $.trim in here
//                return $(this).val() == text1;
//            }).attr('selected', true);
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
//        $('#semester'+j+' option').filter(function() {
//            return $(this).val() == text2;
//        }).attr('selected', true);
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
    $('#courseForExamVenue').html($(t).find(":selected").text());
}



