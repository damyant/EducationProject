


$(document).ready(function () {
    var cnt=0;
    var maxCap=0;
    $("input[name='student']").change(function () {
        var maxAllowed = 4;
        cnt = $("input[name='student']:checked").length;
        maxCap = $("input[name='capacity']").val()
        if(maxCap>0)
        $("input[name='capacity']").val(maxCap-cnt);
        if (cnt>maxAllowed) {
            $("input[name='capacity']").val(cnt-1);
            $(this).prop("checked", "");
            alert('You can select maximum ' + maxAllowed + ' Students only!!');
        }
    });
});


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
            $("#examCenterList").empty().append('data <option value="">Select Examination Venue</option>')
            for (var i = 0; i < data.name.length; i++) {
                $("#examCenterList").append('<option value="' + data.id[i] + '">' + data.name[i] + '</option>')
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}





