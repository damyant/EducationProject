
function uploadImage(input,type){
    $("#profile-image-upload").change(function ()
    {
      //  var iSize = ($("#profile-image-upload")[0].files[0].size / 1024);

        if(iSize<=50){

    }

//        if (iSize / 1024 > 1)
//        {
//            if (((iSize / 1024) / 1024) > 1)
//            {
//                iSize = (Math.round(((iSize / 1024) / 1024) * 100) / 100);
//               alert("Image size is "+iSize)
//               // $("#lblSize").html( iSize + "Gb");
//            }
//            else
//            {
//                iSize = (Math.round((iSize / 1024) * 100) / 100)
//                alert("Image size is "+iSize)
//                //$("#lblSize").html( iSize + "Mb");
//            }
//        }
//        else
//        {
//            iSize = (Math.round(iSize * 100) / 100)
//            alert("Image size is "+iSize)
//           // $("#lblSize").html( iSize  + "kb");
//        }
    });
}




function isNumber(evt) {

    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
    return false;
    }
return true;
}

$('#profile-image').on('click', function() {
//    alert("click")
    $('#profile-image-upload').click();
});


function readURL(input,type) {


    if (input.files && input.files[0]) {
        var reader = new FileReader();

        if(type=='picture')
            reader.onload = function (e) {
                $('#picture')
                    .attr('src', e.target.result)
                    .width(150)
                    .height(200);
            };
        if(type=='signature')
            reader.onload = function (e) {
                $('#signature')
                    .attr('src', e.target.result)
                    .width(250)
                    .height(80);
            };

//      if(input.files[0].si){
//
//      }
//
        reader.readAsDataURL(input.files[0]);
    }
}


jQuery(function($) {
    $( "#datePick" ).datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "mm/dd/yy"
    });

    $( "#datePick1" ).datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "mm/dd/yy"
    });
    $( "#admissionDate" ).datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "mm/dd/yy"
    });
});


