function uploadImage(input, type) {
    $("#profile-image-upload").change(function () {
        //  var iSize = ($("#profile-image-upload")[0].files[0].size / 1024);

        if (iSize <= 50) {

        }


    });
}


$('#profile-image').on('click', function () {
//    alert("click")
    $('#profile-image-upload').click();
});


function readURL(input, type) {
    $('#imageValidate').val("")
    if (input.files && input.files[0]){
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
                        .width(150)
                        .height(150);
                };
            if ($('#imageValidate').length > 0) {
                $('#imageValidate').val("uploded")
            }
            reader.readAsDataURL(input.files[0]);
        }
        else {
            $('#imageValidate').val("")
            $("#profileImage").val('')
            $("#picture").attr('src', ' ')
            $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>Please upload an image of size less then 50kb and image Extension should be gif/png/bmp/jpeg/jpg.</p></div>").dialog({
                title: "Sorry",
                resizable: false,
                modal: true,
                buttons: {
                    "Ok": function () {
                        $(this).dialog("close");
                    }
                }
            });
        }
    }
}



function readURL1(input, type) {
    $('#imageValidate').val("")
    if (input.files && input.files[0]){
        var FileUploadPath = $("#profileImage").val()
        var Extension = FileUploadPath.substring(FileUploadPath.lastIndexOf('.') + 1).toLowerCase();
//        var imgkbytes = Math.round(parseInt(input.files[0].size) / 1024)
//        if (imgkbytes <= 100 && (Extension == "gif" || Extension == "png" || Extension == "bmp" || Extension == "jpeg" || Extension == "jpg"|| Extension=="pdf" || Extension=="docx")) {
            if (Extension == "gif" || Extension == "png" || Extension == "bmp" || Extension == "jpeg" || Extension == "jpg"|| Extension=="pdf" || Extension=="docx") {

                var reader = new FileReader();
            if (type == 'picture')
                reader.onload = function (e) {
                    $('#picture')
                        .attr('src', e.target.result)
                        .width(380)
                        .height(200);
                };
            if (type == 'picture1')
                reader.onload = function (e) {
                    $('#picture1')
                        .attr('src', e.target.result)
                        .width(380)
                        .height(200);
                };
            if (type == 'picture2')
                reader.onload = function (e) {
                    $('#picture2')
                        .attr('src', e.target.result)
                        .width(380)
                        .height(200);
                };
            if (type == 'signature')
                reader.onload = function (e) {
                    $('#signature')
                        .attr('src', e.target.result)
                        .width(380)
                        .height(150);
                };
            if ($('#imageValidate').length > 0) {
                $('#imageValidate').val("uploded")
            }
            reader.readAsDataURL(input.files[0]);
        }
        else {
            $('#imageValidate').val("")
            $("#profileImage").val('')
            $("#picture").attr('src', ' ')
            $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>Please upload an image of size less then 100kb and image Extension should be gif/png/bmp/jpeg/jpg.</p></div>").dialog({
                title: "Sorry",
                resizable: false,
                modal: true,
                buttons: {
                    "Ok": function () {
                        $(this).dialog("close");
                    }
                }
            });
        }
    }
}

function previewWindow(){

    var FileUploadPath = $("#profileImage").val()
    window.open(FileUploadPath,'1387538381256','width=1000,height=700,toolbar=0,menubar=0,location=0,status=1,scrollbars=1,resizable=1,left=0,top=0');
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
    var programme = $('#programId').val()
    $.ajax({
        type: "post",
        url: url('student', 'checkApplicationNo', ''),
        data: {applicationNo: data, programme: programme},
        success: function (data) {

            if (data.applicationNo == "true") {
                $('#errorMsg').text("Already Exist against Roll No "+data.rollNo)
               if($('#idolSubmitButton').length>0) {
                   $('#idolSubmitButton').attr("disabled", true)
               }
                if($('#submitButton').length>0){
                    $('#submitButton').attr("disabled", true)
                }
            }
            else {
                $('#errorMsg').text("")
                if($('#idolSubmitButton').length>0) {
                    $('#idolSubmitButton').attr("disabled", false)
                }
                if($('#submitButton').length>0){
                    $('#submitButton').attr("disabled", false)
                }
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function submitTempRegistration() {
    $('#idolSubmitButton').attr("hidden", "true");
    validate();
    var result = $('#tempEnrollment').valid()
    if (result) {
        $.ajax({
            type: "post",
            url: url('student', 'tempRegistration', ''),
            data: $("#tempEnrollment").serialize(),
            success: function (data) {
                $('#idolSubmitButton').attr("hidden", "false");
                $('#studentName').text('')
                $('#studentRollNo').text('')
                $('#challanNo').text('')
                $('#feeType').text('')
                $('#amount').text('')
                $('#lateFee').text('')
                document.getElementById("tempEnrollment").reset();
                //kuldeep's code start from here................................................
                $('#studentName').text('' + data.student.firstName + ' ' + (data.student.middleName ? data.student.middleName : '') + ' ' + data.student.lastName)
                $('#studentRollNo').text('' + data.student.rollNo)
                $('#challanNo').text('' + data.student.challanNo)
                $('#feeType').text('Course Fee - ' + data.student.semester)
                $('#amount').text('' + data.programFeeAmount)
                $('#feeInWord').text('(' + inWords(data.programFeeAmount)+" only)")
                //                console.log(data.lateFee)
                if (data.lateFee > 0)
                    $('#lateFee').text('(with late fee ' + data.lateFee + ')')
                $('#challanDiv').dialog('open')

            }
        });
    }
}
var a = ['', 'one ', 'two ', 'three ', 'four ', 'five ', 'six ', 'seven ', 'eight ', 'nine ', 'ten ', 'eleven ', 'twelve ', 'thirteen ', 'fourteen ', 'fifteen ', 'sixteen ', 'seventeen ', 'eighteen ', 'nineteen '];
var b = ['', '', 'twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];

function inWords(num) {
    if ((num = num.toString()).length > 9) return 'overflow';
    n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
    if (!n) return;
    var str = '';
    str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'crore ' : '';
    str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'lakh ' : '';
    str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'thousand ' : '';
    str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'hundred ' : '';
    str += (n[5] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + '' : '';

    return str
}
function confirmGenerateChallan(rollno) {
    $.ajax({
        type: "post",
        url: url('admin', 'checkFeeByRollNo', ''),
        data: {rollNo: rollno, feeType: 0},
        success: function (data) {
            if (data.feeStatus) {
                var confirmOK = confirm("Do you want to Generate Challan for Roll No " + rollno + " ?");
                if (confirmOK) {

                    window.open('/UniversityProject/admin/generateFeeVoucher?rollNo=' + rollno + "&idol=" + "idol&feeType=" + "0");
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
function enableApplicationNo() {
    $("#applicationNo").prop("disabled", false)
}
function checkCourseCodeLength(t) {
    var program = $(t).val();
    $.ajax({
        type: "post",
        url: url('admin', 'getCourseCodeLength', ''),
        data: {program: program},
        success: function (program) {
            if (program.status) {
                $('#courseCodeLength').text("");
                if ($("#submitButton").length > 0) {
                    $("#submitButton").prop("disabled", false)
                }
                if ($("#idolSubmitButton").length > 0) {
                    $("#idolSubmitButton").prop("disabled", false)
                }
            }
            else {
                $('#courseCodeLength').text("Error in Programme Code")
                if ($("#submitButton").length > 0) {
                    $("#submitButton").prop("disabled", true)
                }
                if ($("#idolSubmitButton").length > 0) {
                    $("#idolSubmitButton").prop("disabled", true)
                }
            }
        }
    })
}
function loadProgramFeeAmount(t) {
    $('#ProgrammeNotExist').html("");
    $("#idolSubmitButton").prop("disabled", false)
    if ($('#admissionFeeAmount').length > 0) {
        $('#admissionFeeAmount').val("");
    }
    if ($('#ProgrammeNotExist').length > 0) {
        $('#ProgrammeNotExist').html("");
        $("#idolSubmitButton").prop("disabled", false)
    }


    var program = $(t).val();
    if (program == '19' || program == '21'|| program == '25'|| program == '26') {
        $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>Admission to this Programme will be done through Entrance Examination only.</p><p>For more details, please contact <b>idol.gauhatiuniversity@gmail.com</b> . <p></p></div>").dialog({
            title: "Sorry",
            resizable: false,
            modal: true,
            buttons: {
                "Ok": function () {
                    $(this).dialog("close");
                }
            }
        });
    } else {
//    alert(program)
        $.ajax({
            type: "post",
            url: url('admin', 'getFeeAmount', ''),
            data: {program: program},
            success: function (program) {
                if ($('#admissionFeeAmount').length > 0) {
                    $('#admissionFeeAmount').val(program.feeAmount);
                }
                if (program.feeAmount == 0) {
                    $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>This programme is not Avaliable at IDOL.</p><p>For admission to this program Contact IDOL Authorized Study Center.</p></div>").dialog({
                        title: "Sorry",
                        resizable: false,
                        modal: true,
                        buttons: {
                            "Ok": function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                    $("#idolSubmitButton").prop("disabled", true)
                    $("#submitButton").prop("disabled", true)

                }
            }
        });
    }
}
function loadProgramFeeAmountAtIdol(t) {
    if ($('#admissionFeeAmount').length > 0) {
        $('#admissionFeeAmount').val("");
    }
    if ($('#ProgrammeNotExist').length > 0) {
        $('#ProgrammeNotExist').html("");
        $("#idolSubmitButton").prop("disabled", false)
    }


    var program = $(t).val();
//    alert(program)
    $.ajax({
        type: "post",
        url: url('admin', 'getFeeAmount', ''),
        data: {program: program},
        success: function (program) {
            if ($('#admissionFeeAmount').length > 0) {
                $('#admissionFeeAmount').val(program.feeAmount);
            }
            if ($('#ProgrammeNotExist').length > 0) {
                if (program.feeAmount == 0) {
                    $("<div></div>").html("<div style='text-align: justify;font-size: 12px;'><p>This programme is not Avaliable at IDOL Study Centre.</p><p>For admission to this program Contact IDOL Authorized Study Center.</p></div>").dialog({
                        title: "Sorry",
                        resizable: false,
                        modal: true,
                        buttons: {
                            "Ok": function () {
                                $(this).dialog("close");
                            }
                        }
                    });
//                    $('#tempEnrollment').valid(false)
                    $("#idolSubmitButton").prop("disabled", true)
                } else {
                    $('#ProgrammeNotExist').html("");
                    $("#idolSubmitButton").prop("disabled", false)
                }

            }
        }
    });
}

//
function printFeeChallan(elem) {
    Popup1($(elem).html());
    location.reload();
}
function printHomeAssignment(elem) {
    Popup2($(elem).html());
    window.open("/UniversityProject/homeAssignment/submitHomeAssignment", '_self', false)
}
function Popup1(data) {
    var mywindow = window.open('', 'fee voucher', 'height=500,width=550');
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
function Popup2(data) {
    var mywindow = window.open('', 'Home Assignment', 'height=400,width=300');
    mywindow.document.write('<html style="font-family: arial;"><head><title></title>');
    mywindow.document.write('<style type="text/css" media="print">')
    mywindow.document.write('@page{size: auto; margin: 0mm; }')
    mywindow.document.write('body{background-color:#FFFFFF;border: solid 0px black ;margin-bottom:50px }</style>')
    /*optional stylesheet*/ //mywindow.document.write('<link rel="stylesheet" href="main.css" type="text/css" />260');
    mywindow.document.write('</head><body style="border: 0px solid;font-size: 10px;height:270px">');
    mywindow.document.write(data);
    mywindow.document.write('</body></html>');
    mywindow.print();
    mywindow.close();
    return true;
}


function enableDisableCheckbox() {
    if ($('#registrationNo1').val() != '') {
        $('#isAppliedFor').prop('disabled', true)
        $('#regNoCheck').val($('#registrationNo1').val())
    }
    else {
        $('#isAppliedFor').prop('disabled', false)
        $('#regNoCheck').prop('checked', false)
        $('#regNoCheck').val('')
    }
}
function enableDisableTextBox() {
    if ($('#isAppliedFor').is(':checked')) {
        $('#registrationNo1').prop('disabled', true)
        $('#registrationNo2').prop('disabled', true)
        $('#regNoCheck').val('Y')
    }
    else {
        $('#registrationNo1').prop('disabled', false)
        $('#registrationNo2').prop('disabled', false)
        $('#regNoCheck').prop('checked', false)
        $('#regNoCheck').val('')
    }
}
function putOtherBank() {
    if ($("#bankCheckBox").prop('checked') == true) {
        $('#otherBank').prop('disabled', false)
        $('#otherBankBranch').prop('disabled', false)
        $('#bankName').prop('disabled', true)
        $('#branchLocation').prop('disabled', true)
        $('#otherBank').prop('hidden', false)
        $('#otherBankBranch').prop('hidden', false)
        $('#bankName').prop('hidden', true)
        $('#branchLocation').prop('hidden', true)
    }
    else {
        $('#bankName').prop('disabled', false)
        $('#branchLocation').prop('disabled', false)
        $('#otherBank').prop('disabled', true)
        $('#otherBankBranch').prop('disabled', true)
        $('#otherBank').prop('hidden', true)
        $('#otherBankBranch').prop('hidden', true)
        $('#bankName').prop('hidden', false)
        $('#branchLocation').prop('hidden', false)
    }
}
    function putOtherBranch() {
        if ($("#bankCheckBox").prop('checked') == true) {
            $('#otherBankBranch').prop('disabled', false)
            $('#branchLocation').prop('disabled', true)
            $('#otherBankBranch').prop('hidden', false)
            $('#branchLocation').prop('hidden', true)
        }
        else {
            $('#branchLocation').prop('disabled', false)
            $('#otherBankBranch').prop('disabled', true)
            $('#otherBankBranch').prop('hidden', true)
            $('#branchLocation').prop('hidden', false)
        }
}

