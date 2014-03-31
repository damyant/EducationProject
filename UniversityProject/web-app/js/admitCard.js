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
    var data=$('#program').val();
    $.ajax({
        type: "post",
        url: url('admitCard', 'getSemesterList', ''),
        data: {data: data},
        success: function (data) {
            $("#semesterList").empty().append('<option value="">Select Semester</option>')
            for (var i = 0; i <data.length; i++) {
                $("#semesterList").append('<option value="' + data[i].id + '">' + data[i] + '</option>')
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