function uploadImage(input, type) {
    $("#profile-image-upload").change(function () {
        //  var iSize = ($("#profile-image-upload")[0].files[0].size / 1024);

        if (iSize <= 50) {

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

$('#profile-image').on('click', function () {
//    alert("click")
    $('#profile-image-upload').click();
});


function readURL(input, type) {
    if (input.files && input.files[0]) {
        var FileUploadPath = $("#profileImage").val()
        var Extension = FileUploadPath.substring(FileUploadPath.lastIndexOf('.') + 1).toLowerCase();
        var imgkbytes = Math.round(parseInt(input.files[0].size) / 1024)
        if (imgkbytes <= 50 && (Extension == "gif" || Extension == "png" || Extension == "bmp" || Extension == "jpeg" || Extension == "jpg")) {
            var reader = new FileReader();
            if (type == 'picture')
                reader.onload = function (e) {
                    $('#picture')
                        .attr('src', e.target.result)
                        .width(150)
                        .height(200);
                };
            if (type == 'picture1')
                reader.onload = function (e) {
                    $('#picture1')
                        .attr('src', e.target.result)
                        .width(150)
                        .height(200);
                };
            if (type == 'signature')
                reader.onload = function (e) {
                    $('#signature')
                        .attr('src', e.target.result)
                        .width(250)
                        .height(80);
                };
        }
        else {
            $("#profileImage").val('')
            $("#picture").attr('src', ' ')
            alert("Please upload an image of size less then 50kb and in allowed format")
        }

        reader.readAsDataURL(input.files[0]);
    }
}


jQuery(function ($) {
    $("#datePick").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "mm/dd/yy",
        maxDate: 0
    });

    $("#datePick1").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "mm/dd/yy",
        maxDate: 0
    });
    $("#admissionDate").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "mm/dd/yy",
        maxDate: 0
    });
});
function checkApplicationNumber(t) {

    var data = $(t).val();
    $.ajax({
        type: "post",
        url: url('student', 'checkApplicationNo', ''),
        data: {applicationNo: data},
        success: function (data) {

            if (data.applicationNo == "true") {
                $('#errorMsg').text("Application Number already Exist")
            }
            else {
                $('#errorMsg').text("")
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}
function submitTempRegistration() {
    validate();
    var result = $('#tempEnrollment').valid()
    if (result) {

        $.ajax({
            type: "post",
            url: url('student', 'tempRegistration', ''),
            data: $("#tempEnrollment").serialize(),
            success: function (data) {
//                alert(data.rollNo)
                document.getElementById("tempEnrollment").reset();
                confirmGenerateChallan(data.rollNo);
            }
        });
    }
}
function confirmGenerateChallan(rollno) {
alert("challan"+rollno)
        $.ajax({
        type: "post",
        url: url('admin', 'checkFeeByRollNo', ''),
        data: {rollNo: rollno,feeType:0},
        success: function (data) {
            if (data.feeStatus) {
                var confirmOK = confirm("Do you want to Generate Challan for Roll No " + rollno + " ?");
                if (confirmOK) {

//                    window.open('/UniversityProject/admin/generateFeeVoucher?rollNo=' + rollno+'&feeType=1');
                        window.location.href = '/UniversityProject/admin/generateFeeVoucher?rollNo=' + rollno+"&idol="+"idol&feeType="+"0";
                }
                else {
                    alert('Student Registered Successfully & Roll No is ' + rollno);
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $("#errorMessage").text('Fees Not Yet Entered For the Program and Roll No is ' + rollno)
        }
    });
}
function loadProgramFeeAmount(t){
    var program=$(t).val();
//    alert(program)
    $.ajax({
        type: "post",
        url: url('admin', 'getFeeAmount', ''),
        data: {program: program},
        success: function (program) {
            $('#admissionFeeAmount').val(program.feeAmount);
        }
    });
}
