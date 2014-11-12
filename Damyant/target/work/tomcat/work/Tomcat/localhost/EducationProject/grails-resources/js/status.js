/**
 * Created by sonali on 27/3/14.
 */


jQuery(function($) {


    $("a.statustopopup").click(function() {

//        alert("hi")

        loading(); // loading
        setTimeout(function(){ // then show popup, deley in .5 second
            loadPopup(); // function show popup
        }, 500); // .5 second
        return false;
    });


    $("#dob").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "mm/dd/yy",
        maxDate: 0
    });

    $("a.admitCardPopup").click(function() {

//        alert("hi")

        loading(); // loading
        setTimeout(function(){ // then show popup, deley in .5 second
            loadAdmitCardPopup(); // function show popup
        }, 500); // .5 second
        return false;
    });



    /* event for close the popup */
    $("div.close").hover(

        function() {
            $('span.ecs_tooltip').show();
        },
        function () {
            $('span.ecs_tooltip').hide();
        }
    );

    $("div.close").click(function() {
        disablePopup();  // function close pop up

    });

    $("div.closeAdmitCard").click(function() {
        disableAdmitCardPopup();
    });


//    $(this).keyup(function(event) {
//        if (event.which == 27) { // 27 is 'Ecs' in the keyboard
//            disablePopup();  // function close pop up
////            disableAdmitCardPopup();
//
//        }
//    });

    $(this).keyup(function(event) {
        if (event.which == 27) { // 27 is 'Ecs' in the keyboard
            disablePopup();  // function close pop up
           disableAdmitCardPopup();

        }
    });

    $("div#statusBackgroundPopup").click(function() {
        disablePopup();  // function close pop up
    });

    $("div#statusBackgroundPopup1").click(function() {
            disableAdmitCardPopup();
    });


    $('a.livebox').click(function() {
//        alert('Hello World!');
        return false;
    });



    /************** start: functions. **************/
    function loading() {
        $("div.loader").show();
    }
    function closeloading() {
        $("div.loader").fadeOut('normal');
    }

    var popupStatus = 0,admitPopupStatus=0 // set value

    function loadPopup() {

        if(popupStatus == 0) { // if value is 0, show popup
            closeloading(); // fadeout loading
            $("#statusToPopup").fadeIn(0500); // fadein popup div
            $("#statusBackgroundPopup").css("opacity", "0.7"); // css opacity, supports IE7, IE8
            $("#statusBackgroundPopup").fadeIn(0001);
            popupStatus = 1; // and set value to 1
        }
    }

    function loadAdmitCardPopup() {

        if(admitPopupStatus == 0) { // if value is 0, show popup
            closeloading(); // fadeout loading
            $("#admitCardPopup").fadeIn(0500); // fadein popup div
            $("#statusBackgroundPopup1").css("opacity", "0.7"); // css opacity, supports IE7, IE8
            $("#statusBackgroundPopup1").fadeIn(0001);
            admitPopupStatus = 1; // and set value to 1
        }
    }

    function disablePopup() {
        if(popupStatus == 1) { // if value is 1, close popup
            $("#statusToPopup").fadeOut("normal");
            $("#statusBackgroundPopup").fadeOut("normal");
            popupStatus = 0;  // and set value to 0
        }
    }

    function disableAdmitCardPopup() {
        if(admitPopupStatus == 1) { // if value is 1, close popup
            $("#admitCardPopup").fadeOut("normal");
            $("#statusBackgroundPopup1").fadeOut("normal");
            admitPopupStatus = 0;  // and set value to 0
        }
    }
    /************** end: functions. **************/
}); // jQuery End

//to show Status
function showStatus(){
   var data= $('#referenceNumber').val()
    $('#statusofApp').empty().html("")
    $.ajax({
        type: "post",
        url: url('student', 'showStatus', ''),
        data: {data: data},
        success: function (data) {
//            alert(data.response2)
            if(data.response1){
                $('#statusofApp').html("")
                document.getElementById("statusofApp").style.display = "block";
                if(data.response2){
                $('#statusofApp').append('<div>Approved : Roll No '+ data.response2+'.</div>')
                }else{
                $('#statusofApp').append('<div>Pending for Approval.</div>')
                }
            }            else{
                document.getElementById("statusofApp").style.display = "block";
                $('#statusofApp').append('<div>You have Entered Wrong Reference Number</div>')
            }
        }

    });

}

