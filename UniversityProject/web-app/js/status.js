/**
 * Created by sonali on 27/3/14.
 */


jQuery(function($) {


    $("a.statustopopup").click(function() {

        loading(); // loading
        setTimeout(function(){ // then show popup, deley in .5 second
            loadPopup(); // function show popup
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

    $(this).keyup(function(event) {
        if (event.which == 27) { // 27 is 'Ecs' in the keyboard
            disablePopup();  // function close pop up
        }
    });

    $("div#statusBackgroundPopup").click(function() {
        disablePopup();  // function close pop up
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

    var popupStatus = 0; // set value

    function loadPopup() {

        if(popupStatus == 0) { // if value is 0, show popup
            closeloading(); // fadeout loading
            $("#statusToPopup").fadeIn(0500); // fadein popup div
            $("#statusBackgroundPopup").css("opacity", "0.7"); // css opacity, supports IE7, IE8
            $("#statusBackgroundPopup").fadeIn(0001);
            popupStatus = 1; // and set value to 1
        }
    }

    function disablePopup() {
        if(popupStatus == 1) { // if value is 1, close popup
            $("#statusToPopup").fadeOut("normal");
            $("#statusBackgroundPopup").fadeOut("normal");
            popupStatus = 0;  // and set value to 0
        }
    }
    /************** end: functions. **************/
}); // jQuery End

//to show Status
function showStatus(){
   var data= $('#referenceNumber').val()
    $('#statusofApp').html("")
    $.ajax({
        type: "post",
        url: url('student', 'showStatus', ''),
        data: {data: data},
        success: function (data) {
//            alert(data.response2)
            if(data.response1){
                document.getElementById("statusofApp").style.display = "block";
                if(data.response2!=0){
                $('#statusofApp').append('<div>Status of Application is '+data.response1 +' with Roll Number '+ data.response2+'</div>')
                }else{
                $('#statusofApp').append('<div>Status of Application is '+data.response1 +' whose Roll Number not Generated yet </div>')
                }
            }            else{
                document.getElementById("statusofApp").style.display = "block";
                $('#statusofApp').append('<div>You have Entered Wrong Reference Number</div>')
            }
        }

    });

}

