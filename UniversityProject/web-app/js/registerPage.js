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

//function isNumber(evt) {
//
//    evt = (evt) ? evt : window.event;
//    var charCode = (evt.which) ? evt.which : evt.keyCode;
//    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
//        return false;
//    }
//    return true;
//}

$('#profile-image').on('click', function () {
//    alert("click")
    $('#profile-image-upload').click();
});


function readURL(input, type) {
    if (input.files && input.files[0]) {
        var FileUploadPath = $("#profileImage").val()
//        alert($('#imageValidate').val())
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
            if (type == 'picture2')
                reader.onload = function (e) {
                    $('#picture2')
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
            if($('#imageValidate').length>0){
                $('#imageValidate').val("uploded")
            }
//            alert($('#imageValidate').val())
        }
        else {
            if($('#imageValidate').length>0){
                $('#imageValidate').val("")
            }
            $("#profileImage").val('')
            $("#picture").attr('src', ' ')
            alert("Please upload an image of size less then 50kb and image Extension should be gif/png/bmp/jpeg/jpg.")
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

//function checkLastDate(t){
//    var data = $(t).val();
//    $.ajax({
//        type: "post",
//        url: url('student', 'checkLastDate', ''),
//        data: {applicationNo: data},
//        success: function (data) {
//        }
//    })
//}
function submitTempRegistration() {
    validate();
    var result = $('#tempEnrollment').valid()
    if (result) {
        $.ajax({
            type: "post",
            url: url('student', 'tempRegistration', ''),
            data: $("#tempEnrollment").serialize(),
            success: function (data) {
                document.getElementById("tempEnrollment").reset();
                //kuldeep's code start from here................................................
                $('#studentName').text(''+data.student.firstName+' '+(data.student.middleName? data.student.middleName:'')+' '+ data.student.lastName)
                $('#studentRollNo').text(''+data.student.rollNo)
                $('#challanNo').text(''+data.student.challanNo)
                $('#feeType').text('Admission Fee for '+data.courseName)
                $('#amount').text(''+data.programFeeAmount)
                if(data.lateFee>0)
                $('#lateFee').text('(with late fee '+data.lateFee+')')
//                var confirmOK = confirm("Do you want to Generate Challan for Roll No " + data.student.rollNo + " ?");
//                if(confirmOK){
                    $('#challanDiv').dialog('open')
//                }
//                else {
//                    alert('Student Registered Successfully & Roll No is ' + data.student.rollNo);
//                }

                //................................................................................................................
//                confirmGenerateChallan(data.rollNo);
            }
        });
    }
}
function confirmGenerateChallan(rollno) {
        $.ajax({
        type: "post",
        url: url('admin', 'checkFeeByRollNo', ''),
        data: {rollNo: rollno,feeType:0},
        success: function (data) {
            if (data.feeStatus) {
                var confirmOK = confirm("Do you want to Generate Challan for Roll No " + rollno + " ?");
                if (confirmOK) {

                    window.open('/UniversityProject/admin/generateFeeVoucher?rollNo=' + rollno+"&idol="+"idol&feeType="+"0");
//                        window.location.href = '/UniversityProject/admin/generateFeeVoucher?rollNo=' + rollno+"&idol="+"idol&feeType="+"0";
                }
                else {
                    alert('Student Registered Successfully & Roll No is ' + rollno);
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $("#errorMessage").text('Fees Not Yet Entered For the Programme and Roll No is ' + rollno)
        }
    });
}
function loadProgramFeeAmount(t){
    if($('#admissionFeeAmount').length>0){
    $('#admissionFeeAmount').val("");
    }
    if($('#ProgrammeNotExist').length>0){
    $('#ProgrammeNotExist').html("");
        $("#idolSubmitButton").prop("disabled",false)
    }

    var program=$(t).val();
//    alert(program)
    $.ajax({
        type: "post",
        url: url('admin', 'getFeeAmount', ''),
        data: {program: program},
        success: function (program) {
            if($('#admissionFeeAmount').length>0){
            $('#admissionFeeAmount').val(program.feeAmount);
            }
            if($('#ProgrammeNotExist').length>0){
                if(program.feeAmount==0){
                $('#ProgrammeNotExist').html("This Programme is not Available At this Study Center")
//                    $('#tempEnrollment').valid(false)
                    $("#idolSubmitButton").prop("disabled",true)
                }
                else{
                    $('#ProgrammeNotExist').html("");
                    $("#idolSubmitButton").prop("disabled",false)
                }
            }
        }
    });
}

//
    function printFeeChallan(elem){
        Popup1($(elem).html());
        location.reload();
    }
    function Popup1(data)
    {
        var mywindow = window.open('', 'fee voucher','height=500,width=550');
        mywindow.document.write('<html style="font-family: arial;"><head><title></title>');
        mywindow.document.write('<style type="text/css" media="print">')
        mywindow.document.write('@page{size: auto; margin: 0mm; }')
        mywindow.document.write('body{background-color:#FFFFFF;border: solid 0px black ;margin: 0px; }</style>')
        /*optional stylesheet*/ //mywindow.document.write('<link rel="stylesheet" href="main.css" type="text/css" />260');
        mywindow.document.write('</head><body style="border: 0px solid;font-size: 8px;height:270px">');
        mywindow.document.write(data);
        mywindow.document.write('</body></html>');
        mywindow.print();
        mywindow.close();
        return true;
    }


function enableDisableCheckbox(){
    if($('#registrationNo1').val()!=''){
        $('#isAppliedFor').prop('disabled',true)
    }
    else{
        $('#isAppliedFor').prop('disabled',false)
    }
}
function enableDisableTextBox(){
    if($('#isAppliedFor').is(':checked')){
        $('#registrationNo1').prop('disabled',true)
        $('#registrationNo2').prop('disabled',true)
    }
    else{
        $('#registrationNo1').prop('disabled',false)
        $('#registrationNo2').prop('disabled',false)
    }
}
function putOtherBank(){
    if($("#bankCheckBox").prop('checked')==true){
        $('#otherBank').prop('disabled',false)
        $('#otherBankBranch').prop('disabled',false)
        $('#bankName').prop('disabled',true)
        $('#branchLocation').prop('disabled',true)
        $('#otherBank').prop('hidden',false)
        $('#otherBankBranch').prop('hidden',false)
        $('#bankName').prop('hidden',true)
        $('#branchLocation').prop('hidden',true)
    }
    else{
        $('#bankName').prop('disabled',false)
        $('#branchLocation').prop('disabled',false)
        $('#otherBank').prop('disabled',true)
        $('#otherBankBranch').prop('disabled',true)
        $('#otherBank').prop('hidden',true)
        $('#otherBankBranch').prop('hidden',true)
        $('#bankName').prop('hidden',false)
        $('#branchLocation').prop('hidden',false)
    }
}
