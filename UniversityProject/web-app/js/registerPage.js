
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


        reader.readAsDataURL(input.files[0]);
    }
}

//jQuery(function($) {
//    $( "#datePick" ).datepicker({
//        changeMonth: true,
//        changeYear: true,
//        dateFormat: "mm/dd/yy"
//    });
//
//    $( "#datePick1" ).datepicker({
//        changeMonth: true,
//        changeYear: true,
//        dateFormat: "mm/dd/yy"
//    });
//});

//$('#inputDate').DatePicker({
//    format:'m/d/Y',
//    date: $('#inputDate').val(),
//    current: $('#inputDate').val(),
//    starts: 1,
//    position: 'r',
//    onBeforeShow: function(){
//        $('#inputDate').DatePickerSetDate($('#inputDate').val(), true);
//    },
//    onChange: function(formated, dates){
//        $('#inputDate').val(formated);
//        if ($('#closeOnSelect input').attr('checked')) {
//            $('#inputDate').DatePickerHide();
//        }
//    }
//});


